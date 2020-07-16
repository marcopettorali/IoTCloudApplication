package com.aquarium.lln_interface;

import java.net.InetAddress;
import java.util.List;

public class Sensor extends Device {
    protected DoubleObserver observer;
    ResourceConnection resource;

    public Sensor(InetAddress address, int room, String type, String metric, Integer deviceId) {
        super(address, room, type, metric, deviceId);
        String observedResourceAddress = "coap://[" + address.getHostAddress() + "]/" + metric;
        this.observer = new DoubleObserver(observedResourceAddress, 256, observedResourceAddress);
        this.resource = new ResourceConnection(observedResourceAddress);
    }

    public void setPeriod(double value) {
        resource.sendPutRequest("P=" + value);
    }

    public List<Double> getDataSince(long date) {
        return observer.getDataSince(date);
    }

    public String getState() {
        return observer.getState();
    }
}
