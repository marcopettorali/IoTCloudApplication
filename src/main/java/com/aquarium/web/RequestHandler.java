package com.aquarium.web;

import com.aquarium.lln_interface.Device;
import com.aquarium.lln_interface.RegisteredDevices;

import java.util.List;

public class RequestHandler {
    public static Device getOneDevice(String id) {

        int room_id;
        String metric;
        int deviceID;
        String type;


        String[] parse = id.split("_"); //e.g. "1_luminosity_a_0"
        if(parse.length < 4) {
            System.err.println("Error: Wrong device ID format");
            return null;
        }

        room_id = parseInt(parse[0], -2);
        metric = parse[1];
        type = parse[2].equals("a") ? "actuator" : "sensor";
        deviceID = parseInt(parse[3], -2);

        if(room_id == -2 || deviceID == -2) {
            System.err.println("Error: Wrong room or device ID");
            return null;
        }

        List<Device> tmpList = RegisteredDevices.query(null, room_id, type, metric, deviceID);
        if (tmpList.size() != 1) { //
            System.err.println("Error: "+ tmpList.size() +" devices with this ID");
            return null;
        }

        return tmpList.get(0);
    }

    public static int parseInt(String s, int defaultValue) {
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return defaultValue;
        }

    }

}
