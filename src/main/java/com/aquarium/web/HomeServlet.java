package com.aquarium.web;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.aquarium.lln_interface.*;

@WebServlet(
        name = "homeservlet",
        urlPatterns = "/home"
)
public class HomeServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int tanks = RegisteredDevices.countRooms();
        int oxygen_s = RegisteredDevices.query(null, null, "sensor", "oxygen", null).size();
        int oxygen_a = RegisteredDevices.query(null, null, "actuator", "oxygen", null).size();
        int ph = RegisteredDevices.query(null, null, "sensor", "ph", null).size();
        int valves = RegisteredDevices.query(null, null, "actuator", "water", null).size();
        int thermo = RegisteredDevices.query(null, null, "sensor", "temperature", null).size();

        Map<String, String> warnings = new HashMap<>();

        List<Device> allDevices = RegisteredDevices.query(null, null, null, null, null);
        for(Device dev: allDevices) {
            if(dev.getType().equals("sensor")){
                Sensor sen = (Sensor) dev;
                if(sen.getState() != null && !sen.getState().equals("WORKING")) {
                    warnings.put("Tank " + dev.getRoom(), sen.getState());
                }
            }else if(dev.getType().equals("actuator")){
                Actuator sen = (Actuator) dev;
                if(sen.getState() != null && !sen.getState().equals("WORKING")) {
                    warnings.put("Tank " + dev.getRoom(), sen.getState());
                }
            }

        }
        if (warnings.isEmpty())
            warnings = null;

        req.setAttribute("tanks", tanks);
        req.setAttribute("oxygenSensors", oxygen_s);
        req.setAttribute("oxygenActuators", oxygen_a);
        req.setAttribute("phSensors", ph);
        req.setAttribute("waterValves", valves);
        req.setAttribute("tempSensors", thermo);
        req.setAttribute("thermoregulators", thermo);
        req.setAttribute("warnings", warnings);

        RequestDispatcher view = req.getRequestDispatcher("home.jsp");
        view.forward(req, resp);

    }

}
