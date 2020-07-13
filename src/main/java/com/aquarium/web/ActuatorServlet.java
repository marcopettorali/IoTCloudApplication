package com.aquarium.web;


import com.aquarium.lln_interface.Actuator;
import com.aquarium.lln_interface.Device;
import com.aquarium.lln_interface.RegisteredDevices;
import org.json.simple.JSONObject;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet (
        name="actuatorservlet",
        urlPatterns = "/actuator"
)
public class ActuatorServlet extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //retrieve request parameters
        String actuatorID = req.getParameter("id");
        if(actuatorID == null) {
            resp.sendError(resp.SC_BAD_REQUEST);
            return;
        }

        int room_id;
        String metric;
        int deviceID;
        String type;

        String[] parse = actuatorID.split("_");
        if(parse.length < 3) {
            System.err.println("Error: Wrong actuator ID format");
            RequestDispatcher view = req.getRequestDispatcher("index.html");
            view.forward(req, resp);
            return;
        }

        room_id = Integer.getInteger(parse[0], -1);
        metric = parse[1];
        type = "actuator";
        deviceID = Integer.getInteger(parse[0], -1);

        if(room_id == -1 || deviceID == -1) {
            System.err.println("Error: Wrong room or device ID");
            RequestDispatcher view = req.getRequestDispatcher("index.html");
            view.forward(req, resp);
            return;
        }

        List<Device> tmpList = RegisteredDevices.query(null, room_id, type, metric, deviceID);
        if (tmpList.size() != 1) { //
            System.err.println("Error: actuator could not be retrieved or many actuators with same ID");
            RequestDispatcher view = req.getRequestDispatcher("index.html");
            view.forward(req, resp);
            return;
        }

        com.aquarium.lln_interface.Actuator actuator = (Actuator) tmpList.get(0);

        //just an empty put request will trigger the actuator
        String response = actuator.sendPutRequest("");
        if(response == null) {
            System.err.println("Could not receive put response from the device");
            RequestDispatcher view = req.getRequestDispatcher("index.html");
            view.forward(req, resp);
            return;
        }

        JSONObject json = new JSONObject();
        json.put("id", actuatorID);
        json.put("outcome", "good");
        json.put("response", response);


        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        out.print(json.toJSONString());
        out.flush();
    }


}
