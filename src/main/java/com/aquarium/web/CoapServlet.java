package com.aquarium.web;

import com.aquarium.lln_interface.Device;
import com.aquarium.lln_interface.DevicesResource;
import com.aquarium.lln_interface.ObservableResource;
import com.aquarium.lln_interface.RegisteredDevices;
import org.eclipse.californium.core.CoapServer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.InetAddress;


/**
 * This servlet is used to launch backend Coapserver
 */

@WebServlet (
        name="coapservlet",
        urlPatterns = "/coap",
        loadOnStartup = 1
)
public class CoapServlet extends HttpServlet{

    @Override
    public void init() {
        CoapServer server = new CoapServer();
        server.add(new DevicesResource());
        //server.add(new CoAPResourceExample("hello"));
        server.add(new ObservableResource("obs"));
        //populateFakeDevices();
        System.out.println("Rooms available: " + RegisteredDevices.countRooms());
        server.start();
    }

    private static void populateFakeDevices() {
        try {
            RegisteredDevices.insert(new Device(InetAddress.getByName("37.123.0.13"), 3, "sensor", "light", 1));
            RegisteredDevices.insert(new Device(InetAddress.getByName("27.122.0.13"), 3, "actuator", "ph", 1));
            RegisteredDevices.insert(new Device(InetAddress.getByName("17.121.0.13"), 2, "actuator", "temperature", 1));
            RegisteredDevices.insert(new Device(InetAddress.getByName("57.125.0.13"), 1, "sensor", "ph", 1));
            RegisteredDevices.insert(new Device(InetAddress.getByName("67.126.0.13"), 2, "sensor", "ph", 1));
            RegisteredDevices.insert(new Device(InetAddress.getByName("31.123.0.13"), 3, "sensor", "light", 1));
            RegisteredDevices.insert(new Device(InetAddress.getByName("21.122.0.13"), 3, "actuator", "ph", 1));
            RegisteredDevices.insert(new Device(InetAddress.getByName("11.121.0.13"), 1, "actuator", "light", 1));
            RegisteredDevices.insert(new Device(InetAddress.getByName("51.125.0.13"), 1, "actuator", "ph", 1));
            RegisteredDevices.insert(new Device(InetAddress.getByName("61.126.0.13"), 2, "sensor", "light", 1));
            RegisteredDevices.insert(new Device(InetAddress.getByName("32.123.0.13"), 2, "actuator", "temperature", 1));
            RegisteredDevices.insert(new Device(InetAddress.getByName("22.122.0.13"), 3, "actuator", "ph", 1));
            RegisteredDevices.insert(new Device(InetAddress.getByName("12.121.0.13"), 3, "sensor", "temperature", 1));
            RegisteredDevices.insert(new Device(InetAddress.getByName("52.125.0.13"), 1, "sensor", "temperature", 1));
            RegisteredDevices.insert(new Device(InetAddress.getByName("62.126.0.13"), 7, "sensor", "light", 1));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
