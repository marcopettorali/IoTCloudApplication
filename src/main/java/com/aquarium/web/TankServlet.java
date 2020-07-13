package com.aquarium.web;

import com.aquarium.web.model.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet (
        name="tankservlet",
        urlPatterns = "/tank"
)
public class TankServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //TODO: use this attribute to retrieve the real tank from Californium
        String tankID = (String) req.getAttribute("id");

        ArrayList<Sensor> fixedSensors = new ArrayList<>();

        Actuator tempAct = new Actuator("ox_act_1", 0, "OFF", Actuator.ActuatorDescriptor.OXYGENATOR);
        fixedSensors.add(new Sensor("ox_sens_1", 12, "Active", Sensor.SensorDescriptor.OXYGEN, tempAct));

        tempAct = new Actuator("temp_act_1", 0, "OFF", Actuator.ActuatorDescriptor.THERMO_REGULATOR);
        fixedSensors.add(new Sensor("temp_sens_1", 24, "Active", Sensor.SensorDescriptor.TEMPERATURE, tempAct));

        tempAct = new Actuator("light_act_1", 1, "ON", Actuator.ActuatorDescriptor.LIGHT);
        fixedSensors.add(new Sensor("light_sens_1", 18, "Active", Sensor.SensorDescriptor.LIGHT_INTENSITY, tempAct));

        tempAct = new Actuator("valve_act_1", 0, "OFF", Actuator.ActuatorDescriptor.WATER_CHANGE);
        fixedSensors.add(new Sensor("phnh3_sens_1", 7, "Active", Sensor.SensorDescriptor.PH_NH3, tempAct));
        fixedSensors.get(fixedSensors.size() - 1).setCurrentValue2((float)2.3);


        Tank fixed = new Tank("Tank_1", "Sharks tank", false, fixedSensors);

        req.setAttribute("tank", fixed);

        RequestDispatcher view = req.getRequestDispatcher("tank.jsp");
        view.forward(req, resp);

    }

}
