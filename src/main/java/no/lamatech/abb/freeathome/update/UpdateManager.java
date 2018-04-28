/**
 * Copyright (c) 2014-2018 by the respective copyright holders.
 * <p>
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package no.lamatech.abb.freeathome.update;

import rocks.xmpp.core.session.Manager;
import rocks.xmpp.core.session.XmppSession;
import rocks.xmpp.core.stanza.MessageEvent;
import rocks.xmpp.core.stanza.model.Message;
import rocks.xmpp.extensions.pubsub.PubSubManager;
import rocks.xmpp.extensions.pubsub.PubSubService;
import rocks.xmpp.extensions.pubsub.model.Item;
import rocks.xmpp.extensions.pubsub.model.event.Event;
import rocks.xmpp.util.XmppUtils;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.Consumer;

/**
 * XML scheme extension for XMPP update events: abb.com.protocol.update
 * Implementation of update element in the http://abb.com/protocol/update namespace
 * as XEP-0163: Personal Eventing Protocol
 * based on https://sco0ter.bitbucket.io/babbler/xep/pep.html
 *
 *
 * <update xmlns='http://abb.com/protocol/update'>
 * <data>
 * DATA
 * </data>
 * </update>
 *
 * @author ruebox
 * @see <a href="http://xmpp.org/extensions/xep-0163.html">XEP-0163: Personal Eventing Protocol</a>
 */
public final class UpdateManager extends Manager {

    private final Set<Consumer<UpdateEvent>> updateListeners = new CopyOnWriteArraySet<>();

    private final Consumer<MessageEvent> messageListener;

    private UpdateManager(XmppSession xmppSession) {
        super(xmppSession, true);

        messageListener = e -> {
            Message message = e.getMessage();
            Event event = message.getExtension(Event.class);
            if (event != null) {
                for (Item item : event.getItems()) {
                    Object payload = item.getPayload();
                    if (payload instanceof Update) {
                        // Notify the listeners about the reception.
                        XmppUtils.notifyEventListeners(updateListeners,
                                new UpdateEvent(UpdateManager.this, (Update) payload, message.getFrom()));
                    }
                }
            }
        };
    }

    @Override
    protected void onEnable() {
        super.onEnable();
        xmppSession.addInboundMessageListener(messageListener);
    }

    @Override
    protected void onDisable() {
        super.onDisable();
        xmppSession.removeInboundMessageListener(messageListener);
    }

    public void publish(Update update) {
        PubSubService pepService = xmppSession.getManager(PubSubManager.class).createPersonalEventingService();
        pepService.node(Update.NAMESPACE).publish(update);
    }

    public void addUpdateListener(Consumer<UpdateEvent> updateListener) {
        updateListeners.add(updateListener);
    }

    public void removeUpdateListener(Consumer<UpdateEvent> updateListener) {
        updateListeners.remove(updateListener);
    }

    @Override
    protected void dispose() {
        updateListeners.clear();
    }
}
