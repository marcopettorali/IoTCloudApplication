package com.aquarium.web;


import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet (
        name="actuatorservlet",
        urlPatterns = "/actuator"
)
public class ActuatorServlet extends HttpServlet{

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //retrieve request parameters
        String temp = req.getParameter("id");
        if(temp == null) {
            resp.sendError(resp.SC_BAD_REQUEST);
            return;
        }
        String actuatorID = temp;

        temp = req.getParameter("value");
        if(temp == null) {
            resp.sendError(resp.SC_BAD_REQUEST);
            return;
        }
        String value = temp;

        //TODO: check the real outcome and decide response format
        JSONObject json = new JSONObject();
        json.put("id", actuatorID);
        json.put("outcome", "good");


        PrintWriter out = resp.getWriter();
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        out.print(json.toJSONString());
        out.flush();
    }


}
