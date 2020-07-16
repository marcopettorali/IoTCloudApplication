package com.aquarium.web;

import com.aquarium.lln_interface.Actuator;
import com.aquarium.lln_interface.Device;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

public class ThresholdServlet extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String actuatorID = req.getParameter("id");
        if(actuatorID == null) {
            System.out.println("Client did not send an id");
            resp.sendError(resp.SC_BAD_REQUEST);
            return;
        }

        String threshold = req.getParameter("threshold");
        if(threshold == null || !(threshold.equals("high") || threshold.equals("low"))) {
            System.out.println("Client did not send a valid threshold specifier");
            resp.sendError(resp.SC_BAD_REQUEST);
            return;
        }

        double value = -1;
        String tmpValue = req.getParameter("value");
        if(tmpValue != null) {
            value = RequestHandler.parseDouble(tmpValue, -1);
        }
        if(value < 0) {
            System.out.println("Client did not send a valid value");
            resp.sendError(resp.SC_BAD_REQUEST);
            return;
        }

        Device dev = RequestHandler.getOneDevice(actuatorID);
        if(dev == null || !dev.getType().equals("actuator")) {
            System.out.println("Client did not send a valid identifier");
            resp.sendError(resp.SC_BAD_REQUEST);
            return;
        }

        Actuator act = (Actuator) dev;

        if(act.getMetric().equals("water")) {

            String metric = req.getParameter("metric");
            if(metric == null || !(metric.equals("ph") || metric.equals("nh3"))) {
                System.out.println("Client did not send a valid metric");
                resp.sendError(resp.SC_BAD_REQUEST);
                return;
            }

            if(threshold.equals("high")) {
                act.setHighThresholdWithMetric(value, metric);
            }
            else {
                act.setLowThresholdWithMetric(value, metric);
            }

        }

        else {
            if (threshold.equals("high")) {
                act.setHighThreshold(value);
            } else {
                act.setLowThreshold(value);
            }
        }


        HashMap<String, String> outParams = new HashMap<>();
        outParams.put("id", actuatorID);
        outParams.put("newValue", "" + value);
        outParams.put("threshold", threshold);
        JSONObject json = new JSONObject(outParams);


        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        out.print(json.toJSONString());
        out.flush();
    }


}
