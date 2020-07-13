package lln_interface;

import org.eclipse.californium.core.*;

import java.net.*;

public class MainCaliforniumIoT {

    private static void populateFakeDevices() {
        try {
            RegisteredDevices.insert(new Device(InetAddress.getByName("37.123.0.13"), 3, "sensor", "light", 1));
            RegisteredDevices.insert(new Device(InetAddress.getByName("27.122.0.13"), 3, "actuator", "ph", -1));
            RegisteredDevices.insert(new Device(InetAddress.getByName("17.121.0.13"), 2, "actuator", "temperature", 1));
            RegisteredDevices.insert(new Device(InetAddress.getByName("57.125.0.13"), 1, "sensor", "ph", -1));
            RegisteredDevices.insert(new Device(InetAddress.getByName("67.126.0.13"), 2, "sensor", "ph", -1));
            RegisteredDevices.insert(new Device(InetAddress.getByName("31.123.0.13"), 3, "sensor", "light", -1));
            RegisteredDevices.insert(new Device(InetAddress.getByName("21.122.0.13"), 3, "actuator", "ph", -1));
            RegisteredDevices.insert(new Device(InetAddress.getByName("11.121.0.13"), 1, "actuator", "light", -1));
            RegisteredDevices.insert(new Device(InetAddress.getByName("51.125.0.13"), 1, "actuator", "ph", -1));
            RegisteredDevices.insert(new Device(InetAddress.getByName("61.126.0.13"), 2, "sensor", "light", -1));
            RegisteredDevices.insert(new Device(InetAddress.getByName("32.123.0.13"), 2, "actuator", "temperature", -1));
            RegisteredDevices.insert(new Device(InetAddress.getByName("22.122.0.13"), 3, "actuator", "ph", -1));
            RegisteredDevices.insert(new Device(InetAddress.getByName("12.121.0.13"), 3, "sensor", "temperature", -1));
            RegisteredDevices.insert(new Device(InetAddress.getByName("52.125.0.13"), 1, "sensor", "temperature", -1));
            RegisteredDevices.insert(new Device(InetAddress.getByName("62.126.0.13"), 7, "sensor", "light", -1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        CoapServer server = new CoapServer();
        server.add(new DevicesResource());
        //serv.add(new CoAPResourceExample("hello"));
        //serv.add(new lln_interface.ObservableResource("obs"));
        //populateFakeDevices();
        System.out.println("Rooms available: " + RegisteredDevices.countRooms());
        server.start();

    }
}
