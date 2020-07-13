package com.aquarium.lln_interface;

import java.net.InetAddress;
import java.util.List;

public class Actuator extends Device {
    ResourceConnection resource;
    private DoubleObserver observer;

    public Actuator(InetAddress address, int room, String type, String metric, Integer deviceId) {
        super(address, room, type, metric, deviceId);
        String observedResourceAddress = "coap://[" + address.getHostAddress() + "]/" + metric + "_actuator";
        this.resource = new ResourceConnection(observedResourceAddress);
        this.observer = new DoubleObserver(observedResourceAddress, 256);
    }

    public String sendGetRequest(String[] params) {
        return resource.sendGetRequest(params);
    }

    public void sendPostRequest(String jsonString) {
        resource.sendPostRequest(jsonString);
    }

    public void sendPutRequest(String jsonString) {
        resource.sendPutRequest(jsonString);
    }

    public List<Double> getDataSince(long date) {
        return observer.getDataSince(date);
    }

    public String getState() {
        return observer.getState();
    }
}
