package com.aquarium.web;

import com.aquarium.web.model.Tank;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(
        name = "tanklistservlet",
        urlPatterns = "/tanklist"
)
public class TankListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ArrayList<Tank> tanks = new ArrayList<>();
        Tank tank1 = new Tank("idtank1", "Shark tank", true);
        Tank tank2 = new Tank("idTank2", "Jellyfish tank", true);
        Tank tank3 = new Tank("idTank2", "Marcorella tank", true);
        tanks.add(tank1);
        tanks.add(tank2);
        tanks.add(tank3);

        //FIXME: these values must be retrieved from Californium status
        req.setAttribute("tanks", tanks);

        RequestDispatcher view = req.getRequestDispatcher("tankList.jsp");
        view.forward(req, resp);

    }

}
