package com.aquarium.lln_interface;

import java.net.InetAddress;
import java.util.List;

public class Sensor extends Device {
    protected DoubleObserver observer;

    public Sensor(InetAddress address, int room, String type, String metric, Integer deviceId) {
        super(address, room, type, metric, deviceId);
        String observedResourceAddress = "coap://[" + address.getHostAddress() + "]/" + metric;
        this.observer = new DoubleObserver(observedResourceAddress, 256, observedResourceAddress);
    }

    public List<Double> getDataSince(long date) {
        return observer.getDataSince(date);
    }

    public String getState() {
        return observer.getState();
    }
}
