package com.aquarium.lln_interface;

import java.net.InetAddress;
import java.util.List;

public class Sensor extends Device {

    public Sensor(InetAddress address, int room, String type, String metric, Integer deviceId) {
        super(address, room, type, metric, deviceId);
    }

    public List<Double> getDataSince(long date) {
        return observer.getDataSince(date);
    }

}
