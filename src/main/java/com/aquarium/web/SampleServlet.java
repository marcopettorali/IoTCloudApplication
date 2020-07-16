package com.aquarium.web;

import com.aquarium.lln_interface.Device;
import com.aquarium.lln_interface.RegisteredDevices;
import com.aquarium.lln_interface.Sensor;
import org.json.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class SampleServlet extends HttpServlet{


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //retrieve request parameters
        String deviceID = req.getParameter("id");
        if(deviceID == null) {
            resp.sendError(resp.SC_BAD_REQUEST);
            return;
        }

        String value = req.getParameter("value");
        if(value == null) {
            resp.sendError(resp.SC_BAD_REQUEST);
            return;
        }
        int valueInt = RequestHandler.parseInt(value, -1);
        if(valueInt < 10 || valueInt > 3600) {
            resp.sendError(resp.SC_BAD_REQUEST);
            return;
        }


        Device dev = RequestHandler.getOneDevice(deviceID);
        if(dev == null) {
            System.out.println("the device could not be found");
            resp.sendError(resp.SC_INTERNAL_SERVER_ERROR);
            return;
        }

        if(!(dev instanceof com.aquarium.lln_interface.Sensor)) {
            resp.sendError(resp.SC_BAD_REQUEST);
            return;
        }


        com.aquarium.lln_interface.Sensor sen = (com.aquarium.lln_interface.Sensor) dev;

        sen.setPeriod(valueInt);

        List<Device> linked_sensors = null;
        String[] splittedID = deviceID.split("_");
        int room = RequestHandler.parseInt(splittedID[0], -1);
        int id = RequestHandler.parseInt(splittedID[3], -1);

        if(sen.getMetric().equals("ph")) {
            linked_sensors = RegisteredDevices.query(null, room, "sensor", "nh3", id);
        }
        if(sen.getMetric().equals("nh3")) {
            linked_sensors = RegisteredDevices.query(null, room, "sensor", "ph", id);
        }

        if(linked_sensors != null) {
            for (Device device : linked_sensors) {
                Sensor s = (Sensor) device;
                System.out.println("HELLOO from the inside");
                s.setPeriod(valueInt);
            }
        }

        System.out.println("A new sampling rate has been set for device " + deviceID);

        HashMap<String, String> outParams = new HashMap<>();
        outParams.put("outcome", "good");
        outParams.put("newValue", value);

        JSONObject json = new JSONObject(outParams);

        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        out.print(json.toJSONString());
        out.flush();

    }
}
