package com.aquarium.web;

import com.aquarium.lln_interface.*;
import org.eclipse.californium.core.CoapServer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.net.InetAddress;
import java.net.InetAddress;


public class CoapLauncher implements ServletContextListener {

    private static CoapServer coapServer = null;


    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        if (coapServer != null) {
            System.out.println("Destroying Coap Server...");
            coapServer.destroy();
        }
    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {

        System.out.println("Launching Coap Server...");
        CoapServer server = new CoapServer();
        server.add(new DevicesResource());
        //server.add(new CoAPResourceExample("hello"));
        //server.add(new ObservableResource("obs"));
        populateFakeDevices();
        coapServer = server;
        System.out.println("Rooms available: " + RegisteredDevices.countRooms());
        server.start();
    }

    private static void populateFakeDevices() {
        try {
            RegisteredDevices.insert(new Sensor(InetAddress.getByName("localhost"), 3, "sensor", "ph", 1));
            RegisteredDevices.insert(new Sensor(InetAddress.getByName("localhost"), 3, "sensor", "nh3", 1));
            RegisteredDevices.insert(new Actuator(InetAddress.getByName("localhost"), 3, "actuator", "water", 1));
            RegisteredDevices.insert(new Sensor(InetAddress.getByName("localhost"), 1, "sensor", "oxygen", 1));
            RegisteredDevices.insert(new Actuator(InetAddress.getByName("localhost"), 1, "actuator", "oxygen", 1));
            RegisteredDevices.insert(new Sensor(InetAddress.getByName("localhost"), 3, "sensor", "luminosity", 1));
            RegisteredDevices.insert(new Actuator(InetAddress.getByName("localhost"), 3, "actuator", "luminosity", 1));
            RegisteredDevices.insert(new Sensor(InetAddress.getByName("localhost"), 1, "sensor", "luminosity", 1));
            RegisteredDevices.insert(new Actuator(InetAddress.getByName("localhost"), 1, "actuator", "luminosity", 2));
            RegisteredDevices.insert(new Sensor(InetAddress.getByName("localhost"), 2, "sensor", "temperature", 1));
            RegisteredDevices.insert(new Actuator(InetAddress.getByName("localhost"), 2, "actuator", "temperature", 1));
            RegisteredDevices.insert(new Sensor(InetAddress.getByName("localhost"), 3, "sensor", "oxygen", 1));
            RegisteredDevices.insert(new Actuator(InetAddress.getByName("localhost"), 3, "actuator", "oxygen", 1));
            RegisteredDevices.insert(new Sensor(InetAddress.getByName("localhost"), 3, "sensor", "temperature", 1));
            RegisteredDevices.insert(new Actuator(InetAddress.getByName("localhost"), 3, "actuator", "temperature", 1));
            RegisteredDevices.insert(new Sensor(InetAddress.getByName("localhost"), 4, "sensor", "luminosity", 1));
            RegisteredDevices.insert(new Actuator(InetAddress.getByName("localhost"), 4, "actuator", "luminosity", 1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

