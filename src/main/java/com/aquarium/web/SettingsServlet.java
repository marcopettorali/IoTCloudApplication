package com.aquarium.web;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(
        name = "settingsservlet",
        urlPatterns = "/settings"
)
public class SettingsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //TODO retrieve global parameters from the server

        RequestDispatcher view = req.getRequestDispatcher("settings.jsp");
        view.forward(req, resp);

    }

}
