package com.aquarium.lln_interface;

import org.eclipse.californium.core.*;
import org.eclipse.californium.core.coap.*;
import org.eclipse.californium.core.server.resources.*;
import org.json.simple.*;
import org.json.simple.parser.*;

import java.net.*;
import java.util.*;

public class DevicesResource extends CoapResource {
    private String metric;

    public DevicesResource() {
        super("devices");
    }

    public void handleGET(CoapExchange exchange) {
        Response response = new Response(CoAP.ResponseCode.CONTENT);
        response.getOptions().setContentFormat(MediaTypeRegistry.APPLICATION_JSON);
        String address = exchange.getQueryParameter("a");
        String roomStr = exchange.getQueryParameter("r");
        String type = exchange.getQueryParameter("t");
        String metric = exchange.getQueryParameter("m");
        String deviceIdStr = exchange.getQueryParameter("n");
        Integer room = null;
        Integer deviceId = null;
        if (roomStr != null) {
            room = Integer.parseInt(roomStr);
        }
        if (deviceId != null) {
            deviceId = Integer.parseInt(deviceIdStr);
        }
        List<Device> devices = RegisteredDevices.query(address, room, type, metric, deviceId);
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        if (!devices.isEmpty()) {
            for (Device d : devices) {
                JSONObject jsonDevice = new JSONObject();
                jsonDevice.put("a", d.getAddress().getHostAddress());
                jsonArray.add(jsonDevice);
            }
        }
        jsonObject.put("devices", jsonArray);
        response.setPayload(jsonObject.toJSONString());
        System.out.println("RESPONSE = " + jsonObject.toJSONString());

        exchange.respond(response);
    }

    public void handlePOST(CoapExchange exchange) {
        try {
            System.out.println("Payload = " + exchange.getRequestText() + ".");
            JSONObject jsonObject = (JSONObject) JSONValue.parseWithException(exchange.getRequestText());
            System.out.println("Parsed json " + jsonObject.toJSONString() + ".");
            InetAddress deviceAddress = exchange.getSourceAddress();
            int room = new Long((long) jsonObject.get("r")).intValue();
            String type = (String) jsonObject.get("t");
            String metric = (String) jsonObject.get("m");
            int deviceId;
            Object deviceIdStr = (Object) jsonObject.get("n");
            if (deviceIdStr == null) {
                deviceId = -1;
            } else {
                deviceId = new Long((long) jsonObject.get("n")).intValue();
            }
            Device device = null;
            if (type.equals("sensor")) {
                device = new Sensor(deviceAddress, room, type, metric, deviceId);
            } else if (type.equals("actuator")) {
                device = new Actuator(deviceAddress, room, type, metric, deviceId);
            }
            if (RegisteredDevices.query(null, room, type, metric, deviceId).size() != 0) {
                System.out.println("A " + device + " already exists in the network");
                List<Device> allDevices = RegisteredDevices.query(null, room, type, metric, deviceId);
                for (Device dev : allDevices) {
                    if (dev.getType().equals("sensor")) {
                        Sensor sen = (Sensor) dev;
                        sen.resetObserver();
                    } else if (dev.getType().equals("actuator")) {
                        Actuator act = (Actuator) dev;
                        act.resetObserver();
                    }
                }
            } else {
                RegisteredDevices.insert(device);
                System.out.println("A new " + device + " has just been inserted!");
            }
            RegisteredDevices.print();
            exchange.respond(CoAP.ResponseCode.CREATED);
        } catch (Exception e) {
            exchange.respond(CoAP.ResponseCode.BAD_REQUEST);
            e.printStackTrace();
        }
    }

    public void handleDELETE(CoapExchange exchange) {
        try {
            System.out.println("Payload = " + exchange.getRequestText() + ".");
            JSONObject jsonObject = (JSONObject) JSONValue.parseWithException(exchange.getRequestText());
            System.out.println("Parsed json " + jsonObject.toJSONString() + ".");
            InetAddress deviceAddress = exchange.getSourceAddress();
            int room = new Long((long) jsonObject.get("r")).intValue();
            String type = (String) jsonObject.get("t");
            String metric = (String) jsonObject.get("m");
            int deviceId;
            Object deviceIdStr = (Object) jsonObject.get("n");
            if (deviceIdStr == null) {
                deviceId = -1;
            } else {
                deviceId = new Long((long) jsonObject.get("n")).intValue();
            }
            Device device = RegisteredDevices.query(deviceAddress.getHostAddress(), room, type, metric, deviceId).get(0);
            RegisteredDevices.remove(device);
            System.out.println("The " + device + " has disconnected.");
            RegisteredDevices.print();
            exchange.respond(CoAP.ResponseCode.DELETED);
        } catch (ParseException e) {
            exchange.respond(CoAP.ResponseCode.BAD_REQUEST);
            e.printStackTrace();
        } catch (Exception e) {
            exchange.respond(CoAP.ResponseCode.INTERNAL_SERVER_ERROR);
            e.printStackTrace();
        }
    }
}
