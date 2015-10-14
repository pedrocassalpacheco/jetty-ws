/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appdynamics.server;
 
import com.appdynamics.handlers.ApacheClient;
import com.appdynamics.handlers.JettyClient;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;

/**
 *
 * @author pedro.pacheco
 */
public class EmbeddedServer {

    public static void main(String[] args) {
        try {
            // The EmbeddedServer
            Server server = new Server();

            // HTTP connector
            ServerConnector http = new ServerConnector(server);
            http.setHost("localhost");
            http.setPort(8080);
            http.setIdleTimeout(30000);

            // Set the connector
            server.addConnector(http);

            ServletHandler handler = new ServletHandler();
            server.setHandler(handler);
        
            handler.addServletWithMapping(JettyClient.class, "/jetty");
            handler.addServletWithMapping(ApacheClient.class, "/apache");
            
            // Start the server
            server.start();
            server.join();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
