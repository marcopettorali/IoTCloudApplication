package com.aquarium.web;

import com.aquarium.lln_interface.Device;
import com.aquarium.lln_interface.RegisteredDevices;
import com.aquarium.web.model.Tank;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(
        name = "tanklistservlet",
        urlPatterns = "/tanklist"
)
public class TankListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        ArrayList<Tank> tanks = new ArrayList<>();

        for (Device d : RegisteredDevices.query(null, null, null, null, null)) {
            Tank curr_tank = null;

            for (Tank t : tanks) {
                if (t.getIdentifier() == d.getRoom()) {
                    curr_tank = t;
                    break;
                }
            }

            if(curr_tank == null) {
                curr_tank = new Tank(d.getRoom(), "Tank_" + d.getRoom());
                tanks.add(curr_tank);
            }

        }

        /*
        ArrayList<Tank> tanks = new ArrayList<>();
        Tank tank1 = new Tank("idtank1", "Shark tank", true);
        Tank tank2 = new Tank("idTank2", "Jellyfish tank", true);
        Tank tank3 = new Tank("idTank2", "Marcorella tank", true);
        tanks.add(tank1);
        tanks.add(tank2);
        tanks.add(tank3);

         */

        req.setAttribute("tanks", tanks);

        RequestDispatcher view = req.getRequestDispatcher("tankList.jsp");
        view.forward(req, resp);

    }

}
