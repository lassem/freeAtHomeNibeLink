package no.lamatech.abb.freeathome;

import rocks.xmpp.core.session.XmppSession;
import rocks.xmpp.core.session.debug.XmppDebugger;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Logger;

public class XmppLoggerDebugger implements XmppDebugger {
    private static final Logger logger = Logger.getLogger(XmppLoggerDebugger.class.getName());

    public XmppLoggerDebugger() {
    }

    @Override
    public void initialize(XmppSession xmppSession) {

    }

    @Override
    public void writeStanza(String xml, Object streamElement) {
        logger.info("OUT: " + xml);
    }

    @Override
    public void readStanza(String xml, Object streamElement) {
        logger.info("IN:  " + xml);
    }

    @Override
    public OutputStream createOutputStream(OutputStream outputStream) {
        return null;
    }

    @Override
    public InputStream createInputStream(InputStream inputStream) {
        return null;
    }
}
