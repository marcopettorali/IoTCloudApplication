package com.aquarium.web;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(
        name = "homeservlet",
        urlPatterns = "/home"
)
public class HomeServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        int tanks = 20;
        int indoor = 11;
        int oxygen = 20;
        int ph = 20;
        int valves = 43;
        int thermo = 32;

        //FIXME: these values must be retrieved from Californium status
        req.setAttribute("tanks", tanks);
        req.setAttribute("indoorTanks", indoor);
        req.setAttribute("outdoorTanks", tanks - indoor);
        req.setAttribute("oxygenSensors", oxygen);
        req.setAttribute("oxygenActuators", oxygen);
        req.setAttribute("phSensors", ph);
        req.setAttribute("waterValves", valves);
        req.setAttribute("tempSensors", thermo);
        req.setAttribute("thermoregulators", thermo);

        req.setAttribute("warnings", null);

        RequestDispatcher view = req.getRequestDispatcher("home.jsp");
        view.forward(req, resp);

    }

}
