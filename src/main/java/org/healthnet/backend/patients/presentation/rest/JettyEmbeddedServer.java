package org.healthnet.backend.patients.presentation.rest;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandler;

public class JettyEmbeddedServer {
    private final Server server;

    public JettyEmbeddedServer(int port, ContextHandler contextHandler) {
        server = new org.eclipse.jetty.server.Server(port);
        server.setHandler(contextHandler);
    }

    public void start() throws Exception {
        server.start();
    }

    public void stop() throws Exception {
        server.stop();
    }
}
