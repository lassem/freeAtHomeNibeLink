package no.lamatech.abb.freeathome;

import de.fuerstenau.buildconfig.BuildConfig;
import io.reactivex.Completable;
import io.reactivex.Single;
import no.lamatech.abb.freeathome.model.Project;
import no.lamatech.abb.freeathome.sysap.SysApService;
import no.lamatech.abb.freeathome.sysap.model.Settings;
import no.lamatech.abb.freeathome.update.Update;
import no.lamatech.abb.freeathome.update.UpdateEvent;
import no.lamatech.abb.freeathome.update.UpdateManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rocks.xmpp.addr.Jid;
import rocks.xmpp.core.XmppException;
import rocks.xmpp.core.session.*;
import rocks.xmpp.core.stanza.IQEvent;
import rocks.xmpp.core.stanza.MessageEvent;
import rocks.xmpp.core.stanza.PresenceEvent;
import rocks.xmpp.core.stanza.model.Presence;
import rocks.xmpp.extensions.pubsub.PubSubManager;
import rocks.xmpp.extensions.pubsub.model.Item;
import rocks.xmpp.extensions.pubsub.model.event.Event;
import rocks.xmpp.extensions.rpc.RpcManager;
import rocks.xmpp.extensions.rpc.model.Value;
import rocks.xmpp.im.subscription.PresenceManager;
import rocks.xmpp.util.concurrent.AsyncResult;

import java.io.IOException;
import java.util.Locale;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class XmppService {
    private static final Logger log = Logger.getLogger(XmppService.class.getSimpleName());
    private static final String BUSCH_JAEGER_DE = "busch-jaeger.de";
    private static final Jid MRHA_RPC_JID = Jid.of("mrha@" + BUSCH_JAEGER_DE + "/rpc");
    private static final String DIGEST_MD5 = "DIGEST-MD5";
    private static final Locale LOCALE = Locale.forLanguageTag("nb-NO");
    private static final String RESOURCE_ID = String.format("%x", new Random(System.currentTimeMillis()).nextInt(Integer.MAX_VALUE));
    private final PresenceManager presenceManager;
    private final RpcManager rpcManager;
    private final XmppClient client;
    private final ProjectManager projectManager;

    @Autowired
    public XmppService(ConnectionConfiguration connectionConfiguration, SysApService sysApService, ProjectManager projectManager) throws XmppException {
        this.projectManager = projectManager;
        XmppSessionConfiguration sessionConfiguration = XmppSessionConfiguration.builder()
                .language(LOCALE)
                .debugger(XmppLoggerDebugger.class)
                .authenticationMechanisms(DIGEST_MD5)
                .initialPresence(null)
                .extensions(
                        Extension.of("http://abb.com/protocol/update", null, true, true, Update.class),
                        Extension.of("http://abb.com/protocol/update", null, true, Update.class))
                .build();

        client = XmppClient.create(BUSCH_JAEGER_DE, sessionConfiguration, connectionConfiguration);

        client.addSessionStatusListener(this::onSessionStatusEvent);

        client.addInboundMessageListener(this::onInboundMessageEvent);
        client.addOutboundMessageListener(this::onOutboundMessageEvent);
        client.addInboundIQListener(this::onInboundIQEvent);
        client.addInboundPresenceListener(this::onInboundPresenceEvent);

        PubSubManager pubSubManager = client.getManager(PubSubManager.class);
        pubSubManager.setEnabled(true);

        presenceManager = client.getManager(PresenceManager.class);

        UpdateManager updateManager = client.getManager(UpdateManager.class);
        updateManager.setEnabled(true);
        updateManager.addUpdateListener(this::onUpdateEvent);

        rpcManager = client.getManager(RpcManager.class);

        client.connect(Jid.of(BUSCH_JAEGER_DE));

        sysApService.getSettings()
                .flatMap(settings -> getJid(settings, BuildConfig.freeAtHomeUsername))
                .doOnSuccess(jid -> client.login(jid.substring(0, jid.indexOf("@")), BuildConfig.freeAtHomePassword, RESOURCE_ID))
                .doOnSuccess(this::requestSubscriptionToRpc)
                .doOnSuccess(this::announcePresence)
                .flatMap(k -> getAll())
                .subscribe(result -> {},
                        throwable -> log.log(Level.INFO, "login failed", throwable));
    }

    private void announcePresence(String from) {
        client.sendPresence(new Presence());
    }

    private void requestSubscriptionToRpc(String from) {
        Presence presence = new Presence(MRHA_RPC_JID, Presence.Type.SUBSCRIBE, null, null, null, null, Jid.of(from), null, null, null);
        client.sendPresence(presence);
    }

    private Single<String> getJid(Settings settings, String username) {
        return settings.users
                .stream()
                .filter(user -> username.equalsIgnoreCase(user.name))
                .map(user -> user.jid)
                .findFirst()
                .map(Single::just)
                .orElse(Single.error(new IllegalStateException("Unable to resolve jid of user: " + username)));
    }

    private Single<String> exec(String command, String arg1, String arg2) {
        AsyncResult<Value> call = rpcManager.call(MRHA_RPC_JID, "RemoteInterface.exec", Value.of(command));
        return Single.fromFuture(call).map(Value::getAsString);
    }

    private Single<Project> getAll() {
        AsyncResult<Value> result = rpcManager.call(MRHA_RPC_JID, "RemoteInterface.getAll", Value.of(LOCALE.toLanguageTag()), Value.of(4), Value.of(0), Value.of(0));
        return Single.fromFuture(result).map(Value::getAsString).map(projectManager::createWithGetAll);
    }

    private Completable setDataPoint(String address, String value) {
        AsyncResult<Value> result = rpcManager.call(MRHA_RPC_JID, "RemoteInterface.setDatapoint", Value.of(address), Value.of(value));
        return Completable.fromFuture(result);
    }

    private void onSessionStatusEvent(SessionStatusEvent sessionStatusEvent) {

    }

    private void onInboundPresenceEvent(PresenceEvent presenceEvent) {
        Presence presence = presenceEvent.getPresence();
        presenceManager.approveSubscription(presence.getFrom());
    }

    private void onInboundIQEvent(IQEvent iqEvent) {
    }

    private void onOutboundMessageEvent(MessageEvent messageEvent) {

    }

    private void onInboundMessageEvent(MessageEvent messageEvent) {
        Event event = messageEvent.getMessage().getExtension(Event.class);
        if (event == null) return;
        if (Update.NAMESPACE.equals(event.getNode())) {
            for (Item item : event.getItems()) {
//                log.info(item.getPayload().toString());

                if (item.getPayload() instanceof Update) {
                    Update update = (Update) item.getPayload();
//                    log.info(update.getData());

                    Project merged = null;
                    try {
                        // Deserialization is currently broken if deviceId is missing, which it is on all update events.
                        String hacked = update.getData().replace("<device ser", "<device deviceId=\"FFFF\" ser");
                        merged = projectManager.update(hacked);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }

    }

    private void onUpdateEvent(UpdateEvent updateEvent) {

    }
}
