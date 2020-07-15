package com.aquarium.web;

import com.aquarium.lln_interface.Device;
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
            System.out.println("the device could not be found");
            resp.sendError(resp.SC_INTERNAL_SERVER_ERROR);
            return;
        }


        List<Double> lastValues;
        boolean isPH = false;
        boolean isNH3 = false;
        if(dev instanceof com.aquarium.lln_interface.Sensor) {
            com.aquarium.lln_interface.Sensor t_dev = (com.aquarium.lln_interface.Sensor) dev;
            lastValues = t_dev.getDataSince(new Date().getTime() - DATA_AGE);
            if(t_dev.getMetric().equals("ph"))
                isPH = true;
            if(t_dev.getMetric().equals("nh3"))
                isNH3 = true;
        }
        else {
            lastValues = ((com.aquarium.lln_interface.Actuator) dev).getDataSince(new Date().getTime() - DATA_AGE);
        }


        JSONArray jsArray = new JSONArray(lastValues);
        HashMap<String, String> outParams = new HashMap<>();
        outParams.put("values", jsArray.toString());
        outParams.put("outcome", "good");

        if(isPH || isNH3) {
            String[] tmpID = deviceID.split("_");
            tmpID[1] =  isPH ? "nh3" : "ph";
            String id_linked = String.join("_", tmpID);
            Device dev2 = RequestHandler.getOneDevice(id_linked);
            if(dev2 instanceof com.aquarium.lln_interface.Sensor) {
                com.aquarium.lln_interface.Sensor t_dev2 = (com.aquarium.lln_interface.Sensor) dev2;
                List<Double> lastValues2 = t_dev2.getDataSince(new Date().getTime() - DATA_AGE);
                jsArray = new JSONArray(lastValues2);
                outParams.put("values_linked", jsArray.toString());
            }
        }
        /*

        double[] lastValues = {0.0, 2.1, 5.4, 7.1, 10.0, 23.4};
        double[] lastValues2 = {8.0, 6.1, 8.4, 1.1, 20.0, 13.4};
        JSONArray jsArray = new JSONArray(lastValues);
        JSONArray jsArray2 = new JSONArray(lastValues2);
        HashMap<String, String> outParams = new HashMap<>();
        outParams.put("values", jsArray.toString());
        outParams.put("values2", jsArray2.toString());
        outParams.put("outcome", "good");

         */

        JSONObject json = new JSONObject(outParams);

        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        out.print(json.toJSONString());
        out.flush();

    }
}
