package by.tareltos.fcqdelivery.controller;

import by.tareltos.fcqdelivery.command.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ControllerServlet", urlPatterns = {"/main", "/singIn", "/doLogin", "/reset"})
public class ControllerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");
        Command command = CommandFactory.getInstance().getCommand(action);
        String page = command.execute(request);
        if (page == null) {
            page = "/jsp/main.jsp";
        }

        request.getRequestDispatcher(page).forward(request, response);
    }
}
