package com.tatsuyaoiw;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

@Slf4j
public class Main {

    public static void main(String[] args) {
        log.info("Initializing the application...");

        // The port that we should run on can be set into an environment variable
        // Look for that variable and default to 8080 if it isn't there.
        String webPort = System.getenv("PORT");
        if (webPort == null || webPort.isEmpty()) {
            webPort = "8080";
        }

        final Server server = new Server(Integer.valueOf(webPort));

        ServletContextHandler context = new ServletContextHandler();
        server.setHandler(context);

        ServletHolder servletHolder = context.addServlet(ServletContainer.class, "/*");
        servletHolder.setInitOrder(1);
        servletHolder.setInitParameter("jersey.config.server.provider.packages", "com.tatsuyaoiw");

        try {
            server.start();
            server.join();
        } catch (Exception e) {
            log.error("Failed to initialize the application", e);
        } finally {
            server.destroy();
        }
    }
}
