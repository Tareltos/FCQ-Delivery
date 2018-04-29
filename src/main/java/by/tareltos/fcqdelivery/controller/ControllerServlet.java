package by.tareltos.fcqdelivery.controller;

import by.tareltos.fcqdelivery.command.*;
import by.tareltos.fcqdelivery.receiver.ReceiverException;
import com.sun.org.apache.regexp.internal.RE;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "ControllerServlet", urlPatterns = {"/main", "/applications", "/info", "/singIn", "/doLogin", "/reset", "/doRegistration", "/logout", "/users", "/couriers", "/courierForm", "/saveCourier"})
@MultipartConfig
public class ControllerServlet extends HttpServlet {
    final static Logger LOGGER = LogManager.getLogger();
    private final String ACTION_PRM = "action";
    private final String REDIRECT_PRM = "redirect";
    private final String METHOD_PRM = "method";
    private final String REDIRECT_URL_PRM = "redirectUrl";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter(ACTION_PRM);
        LOGGER.log(Level.DEBUG, "Command is: " + action);
        Command command = CommandFactory.getInstance().getCommand(action);
        String page = null;
        if (null != command) {
            page = command.execute(request);
        }
        if (page == null) {
            page = PagePath.PATH_MAIN_PAGE.getPath();
        }
        if (REDIRECT_PRM.equals(request.getAttribute(METHOD_PRM))) {
            response.sendRedirect(String.valueOf(request.getAttribute(REDIRECT_URL_PRM)));
        } else {
            request.getRequestDispatcher(page).forward(request, response);
        }
    }
}