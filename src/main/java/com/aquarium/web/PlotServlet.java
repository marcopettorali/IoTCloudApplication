package com.aquarium.web;

import com.aquarium.lln_interface.Device;
import org.json.JSONArray;
import org.json.simple.JSONObject;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class PlotServlet extends HttpServlet{
    public final long DATA_AGE = 7200000;


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //retrieve request parameters
        String deviceID = req.getParameter("id");
        if(deviceID == null) {
            resp.sendError(resp.SC_BAD_REQUEST);
            return;
        }

        Device dev = RequestHandler.getOneDevice(deviceID);
        if(dev == null) {
            RequestDispatcher view = req.getRequestDispatcher("index.html");
            view.forward(req, resp);
            return;
        }

        List<Double> lastValues;
        boolean isPH_NH3 = false;
        if(dev instanceof com.aquarium.lln_interface.Sensor) {
            com.aquarium.lln_interface.Sensor t_dev = (com.aquarium.lln_interface.Sensor) dev;
            lastValues = t_dev.getDataSince(new Date().getTime() - DATA_AGE);
            if(t_dev.getMetric().equals("ph") || t_dev.getMetric().equals("nh3"))
                isPH_NH3 = true;
        }
        else {
            lastValues = ((com.aquarium.lln_interface.Actuator) dev).getDataSince(new Date().getTime() - DATA_AGE);
        }


        JSONArray jsArray = new JSONArray(lastValues);
        HashMap<String, String> outParams = new HashMap<>();
        outParams.put("values", jsArray.toString());
        outParams.put("outcome", "good");

        if(isPH_NH3) {
            String id_linked = req.getParameter("id2");
            if(id_linked != null) {
                Device dev2 = RequestHandler.getOneDevice(id_linked);
                if(dev2 instanceof com.aquarium.lln_interface.Sensor) {
                    com.aquarium.lln_interface.Sensor t_dev2 = (com.aquarium.lln_interface.Sensor) dev2;
                    List<Double> lastValues2 = t_dev2.getDataSince(new Date().getTime() - DATA_AGE);
                    jsArray = new JSONArray(lastValues2);
                    outParams.put("values_linked", jsArray.toString());
                }
            }
        }

        JSONObject json = new JSONObject(outParams);

        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        out.print(json.toJSONString());
        out.flush();

    }
}
