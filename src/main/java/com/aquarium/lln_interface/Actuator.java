package com.aquarium.lln_interface;

import java.net.InetAddress;
import java.util.List;

public class Actuator extends Device {
    ResourceConnection resource;

    public Actuator(InetAddress address, int room, String type, String metric, Integer deviceId) {
        super(address, room, type, metric, deviceId);
        String observedResourceAddress = "coap://[" + address.getHostAddress() + "]/" + metric + "_actuator";
        this.resource = new ResourceConnection(observedResourceAddress);
    }

    public String sendGetRequest(String[] params) {
        return resource.sendGetRequest(params);
    }

    public String sendPostRequest(String jsonString) {
        return resource.sendPostRequest(jsonString);
    }

    public String sendPutRequest(String jsonString) {
        return resource.sendPutRequest(jsonString);
    }

    public List<Double> getDataSince(long date) {
        return observer.getDataSince(date);
    }
}
