package com.aquarium.lln_interface;

import java.net.*;

public class Device {
    private final InetAddress address;
    private final int room;
    private final String type;
    private final String metric;
    private final int deviceId;

    public Device(InetAddress address, int room, String type, String metric, Integer deviceId) {
        this.address = address;
        this.room = room;
        this.type = type;
        this.metric = metric;
        this.deviceId = deviceId;
    }

    public InetAddress getAddress() {
        return address;
    }

    public int getRoom() {
        return room;
    }

    public String getType() {
        return type;
    }

    public String getMetric() {
        return metric;
    }

    public int getDeviceId() {
        return deviceId;
    }

    @Override
    public String toString() {
        String ret = metric + " " + type + " in room " + room + "@" + address.getHostAddress();
        if (deviceId != -1) {
            ret += " (id = " + deviceId + ")";
        }
        return ret;
    }
}
