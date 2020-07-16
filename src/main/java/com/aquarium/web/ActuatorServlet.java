package com.aquarium.web;


import com.aquarium.lln_interface.Actuator;
import com.aquarium.lln_interface.Device;
import org.json.simple.JSONObject;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;


public class ActuatorServlet extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //retrieve request parameters
        String actuatorID = req.getParameter("id");
        if(actuatorID == null) {
            resp.sendError(resp.SC_BAD_REQUEST);
            return;
        }


        String value = req.getParameter("value");
        if(value == null) {
            resp.sendError(resp.SC_BAD_REQUEST);
            return;
        }

        int val = -1;
        if(value.equals("ON")) {
            val = 1;
        }
        if(value.equals("OFF")) {
            val = 0;
        }
        if(val == -1) {
            resp.sendError(resp.SC_BAD_REQUEST);
            return;
        }

        System.out.println("Setting the actuator " + actuatorID + " to " + value);
        Device dev = RequestHandler.getOneDevice(actuatorID);
        if(dev == null) {
            RequestDispatcher view = req.getRequestDispatcher("index.html");
            view.forward(req, resp);
            return;
        }

        com.aquarium.lln_interface.Actuator actuator = (Actuator) dev;

        if(val == 1) {
            actuator.turnOn();
        }
        else {
            actuator.turnOff();
        }

        String outcome = val == 1 ? "ON" : "OFF";

        HashMap<String, String> outParams = new HashMap<>();
        outParams.put("id", actuatorID);
        outParams.put("response", outcome);
        JSONObject json = new JSONObject(outParams);


        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        out.print(json.toJSONString());
        out.flush();
    }


}
