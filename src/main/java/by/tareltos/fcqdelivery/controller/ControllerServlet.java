package by.tareltos.fcqdelivery.controller;

import by.tareltos.fcqdelivery.command.*;
import by.tareltos.fcqdelivery.dbconnection.ConnectionException;
import by.tareltos.fcqdelivery.dbconnection.ConnectionPool;
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

/**
 * Class controls the operation of the application,
 * makes a redirect or forward to the jsp page
 *
 * @autor Tarelko Vitali
 */
@WebServlet(name = "ControllerServlet", urlPatterns = {"/main", "/applications", "/info", "/singIn", "/doLogin", "/reset", "/doRegistration", "/logout", "/users", "/couriers", "/courierForm", "/saveCourier"})
@MultipartConfig
public class ControllerServlet extends HttpServlet {
    /**
     * The logger object, used to write logs
     *
     * @see org.apache.logging.log4j.Logger
     */
    private static final Logger LOGGER = LogManager.getLogger();
    /**
     * Parameter used to identify input command from request
     */
    private static final String ACTION = "action";
    /**
     * Parameter used to identify method from request and
     * than send redirect or do forward
     */
    private static final String METHOD = "method";
    /**
     * Parameter used to identify redirect action from request
     */
    private static final String REDIRECT = "redirect";
    /**
     * Parameter used to identify redirectUrl for redirect from request
     */
    private static final String REDIRECT_URL = "redirectUrl";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String action = request.getParameter(ACTION);
        LOGGER.log(Level.DEBUG, "Command is: " + action);
        Command command = CommandFactory.getInstance().getCommand(action);
        String page = null;
        if (null != command) {
            page = command.execute(request);
        }
        if (page == null) {
            page = PagePath.PATH_MAIN_PAGE.getPath();
        }
        if (REDIRECT.equals(request.getAttribute(METHOD))) {
            response.sendRedirect(String.valueOf(request.getAttribute(REDIRECT_URL))); //if idea
            // response.sendRedirect(request.getContextPath() + "/" + String.valueOf(request.getAttribute(REDIRECT_URL)));  //if tomcat
        } else {
            request.getRequestDispatcher(page).forward(request, response);
        }
    }

    @Override
    public void destroy() {
        try {
            LOGGER.log(Level.INFO, "Method destroy: Before Closing connection in pool");
            ConnectionPool.getInstance().closeAllConnections();
        } catch (ConnectionException e) {
            LOGGER.log(Level.ERROR, "Exception in closeAllConnection" + e.getMessage());
        }
        super.destroy();
    }
}