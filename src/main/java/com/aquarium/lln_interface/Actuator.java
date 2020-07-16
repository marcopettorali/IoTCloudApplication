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

    public void turnOn() {
        resource.sendPutRequest("M=1");
    }

    public void turnOff() {
        resource.sendPutRequest("M=0");
    }

    public void setLowThreshold(double value) {
        resource.sendPutRequest("t_l=" + value);
    }

    public void setHighThreshold(double value) {
        resource.sendPutRequest("t_h=" + value);
    }

    public void setLowThresholdWithMetric(double value, String metric) {
        resource.sendPutRequest("t_l_" + metric + "=" + value);
    }

    public void setHighThresholdWithMetric(double value, String metric) {
        resource.sendPutRequest("t_h_" + metric + "=" + value);
    }

    public List<Double> getDataSince(long date) {
        return observer.getDataSince(date);
    }

    public String getState() {
        return observer.getState();
    }
}
