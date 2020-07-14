package com.aquarium.lln_interface;

import java.net.InetAddress;
import java.util.List;

public class Actuator extends Device {
    ResourceConnection resource;
    protected DoubleObserver observer;

    public Actuator(InetAddress address, int room, String type, String metric, Integer deviceId) {
        super(address, room, type, metric, deviceId);
        String observedResourceAddress = "coap://[" + address.getHostAddress() + "]/" + metric + "_actuator";
        this.resource = new ResourceConnection(observedResourceAddress);
        this.observer = new DoubleObserver(observedResourceAddress, 256, observedResourceAddress);
    }

    public String turnOn() {
        return resource.sendPutRequest("M=1");
    }

    public String turnOff() {
        return resource.sendPutRequest("M=0");
    }

    public String setLowThreshold(double value) {
        return resource.sendPutRequest("t_l=" + value);
    }

    public String setHighThreshold(double value) {
        return resource.sendPutRequest("t_h=" + value);
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

    public String getState() {
        return observer.getState();
    }
}
