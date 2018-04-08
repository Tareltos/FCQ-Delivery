package by.tareltos.fcqdelivery.controller;

import by.tareltos.fcqdelivery.command.*;
import by.tareltos.fcqdelivery.entity.Courier;
import by.tareltos.fcqdelivery.receiver.ReceiverException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "ControllerServlet", urlPatterns = {"/main", "/singIn", "/doLogin", "/reset", "/doRegistration", "/logout", "/users", "/couriers", "/courierForm"})
public class ControllerServlet extends HttpServlet {
    final static Logger LOGGER = LogManager.getLogger();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter("action");
        LOGGER.log(Level.DEBUG, "Command is: " + action);
        Command command = CommandFactory.getInstance().getCommand(action);
        String page = null;
        if (null != command) {
            try {
                page = command.execute(request);
            } catch (ReceiverException e) {
                e.printStackTrace();
            } catch (CommandException e) {
                e.printStackTrace();
            }

        }
        if (page == null) {
            page = "index.jsp";
        }
        request.getRequestDispatcher(page).forward(request, response);
    }
}
