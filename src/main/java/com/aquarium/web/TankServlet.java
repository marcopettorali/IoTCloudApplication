package com.aquarium.web;

import com.aquarium.lln_interface.Device;
import com.aquarium.lln_interface.RegisteredDevices;
import com.aquarium.web.model.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class TankServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        int tankID = RequestHandler.parseInt ( req.getParameter("id"), -1);
        if(tankID == -1) {
            System.out.println("The tank id inserted does not exist.");
            RequestDispatcher view = req.getRequestDispatcher("index.html");
            view.forward(req, resp);
            return;
        }

        List<Device> acts = RegisteredDevices.query(null, tankID, "actuator", null, null);
        List<Device> sens = RegisteredDevices.query(null, tankID, "sensor", null, null);

        if(sens.size() == 0) {
            System.err.println("This tank does not contain sensors.");
            RequestDispatcher view = req.getRequestDispatcher("index.html");
            view.forward(req, resp);
            return;
        }

        ArrayList<Sensor> sensors = new ArrayList<>();
        ArrayList<Actuator> actuators = new ArrayList<>();

        for(Device sensor: sens) {
            Sensor s_temp = new Sensor((com.aquarium.lln_interface.Sensor) sensor);
            sensors.add(s_temp);
        }

        for(Device actuator: acts) {
            Actuator a_temp = new Actuator((com.aquarium.lln_interface.Actuator) actuator);
            actuators.add(a_temp);
        }

        for(Sensor s_model: sensors) {
            Actuator.ActuatorDescriptor act_type = s_model.getLinkedActuator();
            for(Actuator a_model: actuators) {
                if(a_model.getClassDescriptor() == act_type) {
                    s_model.addActuator(a_model);
                }
            }
        }

        Tank myTank = new Tank(tankID, "Tank_" + tankID, sensors);

        /*
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

         */

        req.setAttribute("tank", myTank);

        RequestDispatcher view = req.getRequestDispatcher("tank.jsp");
        view.forward(req, resp);

    }

}
