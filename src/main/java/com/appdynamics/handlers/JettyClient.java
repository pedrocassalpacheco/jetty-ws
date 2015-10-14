/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.appdynamics.handlers;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.client.api.ContentResponse;
import org.eclipse.jetty.client.HttpClient;

/**
 *
 * @author pedro.pacheco
 */
public class JettyClient extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        
        HttpClient downstream = new HttpClient();
        downstream.setFollowRedirects(true);
        try {
            downstream.start();
            ContentResponse reply = downstream.GET("http://localhost:8090");
            response.getWriter().println(reply.getContentAsString()); 
        } catch (InterruptedException | ExecutionException | TimeoutException ex) {
            Logger.getLogger(JettyClient.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(JettyClient.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                downstream.stop();
            } catch (Exception ex) {
                Logger.getLogger(JettyClient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
                             
    }
}
