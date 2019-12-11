package org.healthnet.backend.patients.presentation.rest;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.Servlet;

public class JettyEmbeddedServer {
    private final Server server;

    public JettyEmbeddedServer(int port, Servlet servlet) {
        server = new org.eclipse.jetty.server.Server(port);
        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        ServletHolder servletHolder = new ServletHolder("application", servlet);
        contextHandler.addServlet(servletHolder, "/patients");
        server.setHandler(contextHandler);
    }

    public void start() throws Exception {
        server.start();
    }

    public void stop() throws Exception {
        server.stop();
    }
}
