package com.appdynamics.handlers;

import java.io.IOException;
import java.net.URI;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.http.HttpEntity;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author pedro.pacheco
 */
public class ApacheClient extends HttpServlet {

    private static final String COLLECTIONPARAM = "collection";
    private static final String SERVICEURL      = "http://localhost:8090";
    private static final String DATASERVICE     = "mongo";
    private static final Logger LOG = Logger.getLogger(ApacheClient.class.getName());
    
    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        LOG.info("Starting downstream call");
        
        String collection = request.getParameter(COLLECTIONPARAM);
        LOG.log(Level.INFO, "Collection ={0}", collection);
        
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
       
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpGet localRequest = new HttpGet(String.format("%s/%s?%s=%s", SERVICEURL, DATASERVICE, COLLECTIONPARAM, collection));   
       
        LOG.log(Level.INFO, "Calling downstream server {0}", localRequest.getURI().toString());
        
        HttpResponse localResponse = client.execute(localRequest);
        HttpEntity entity = localResponse.getEntity();
                
        // Writes back to the initial HTTP request
        response.getWriter().println(EntityUtils.toString(entity));
    }
    
}

