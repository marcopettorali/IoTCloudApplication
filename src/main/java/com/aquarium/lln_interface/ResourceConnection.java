package com.aquarium.lln_interface;

import org.eclipse.californium.core.*;
import org.eclipse.californium.core.coap.*;

public class ResourceConnection {
    String resourceAddress;
    CoapClient client;

    public ResourceConnection(String resourceAddress) {
        this.resourceAddress = resourceAddress;
        client = new CoapClient(resourceAddress);
    }

    public void shutdown(){
        client.shutdown();
    }
    
    public String sendGetRequest(String[] params) {
        Request req = new Request(CoAP.Code.GET);
        req.getOptions().setAccept(MediaTypeRegistry.APPLICATION_JSON);
        for (String param : params) {
            req.getOptions().addUriQuery(param);
        }
        CoapResponse resp = client.advanced(req);
        return resp.getResponseText();
    }


    public String sendPostRequest(String jsonString) {
        CoapResponse resp = client.post(jsonString, MediaTypeRegistry.APPLICATION_JSON);
        return resp.getResponseText();
    }

    public String sendPutRequest(String jsonString) {
        CoapResponse resp = client.put(jsonString, MediaTypeRegistry.APPLICATION_JSON);
        return resp.getResponseText();
    }
}