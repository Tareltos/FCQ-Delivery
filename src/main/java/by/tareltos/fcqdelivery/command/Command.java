package by.tareltos.fcqdelivery.command;

import by.tareltos.fcqdelivery.entity.user.User;
import by.tareltos.fcqdelivery.receiver.ApplicationReceiver;
import by.tareltos.fcqdelivery.receiver.CourierReceiver;
import by.tareltos.fcqdelivery.receiver.UserReceiver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static by.tareltos.fcqdelivery.command.ParameterStore.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Properties;

/**
 * The interface is used to obtain path to jsp page
 *
 * @autor Tarelko Vitali
 */
public interface Command {
    /**
     * The logger object, used to write logs
     *
     * @see org.apache.logging.log4j.Logger
     */
    Logger LOGGER = LogManager.getLogger();
    UserReceiver USER_RECEIVER = UserReceiver.getInstance();
    ApplicationReceiver APPLICATION_RECEIVER = ApplicationReceiver.getInstance();
    CourierReceiver COURIER_RECEIVER = CourierReceiver.getInstance();


    /**
     * Method returns the path to the jsp page
     *
     * @param request servlet request parameter
     * @return return the path to the jsp page
     */
    String execute(HttpServletRequest request);


    /**
     * @param req HttpServletRequest request
     * @return object of user from current session
     */
    default User getUser(HttpServletRequest req) {
        return (User) req.getSession().getAttribute(LOGINED_USER);
    }

    default boolean loadProperies(HttpServletRequest request, Properties properties, String fileNamePrm) {
        ServletContext context = request.getServletContext();
        String filename = context.getInitParameter(fileNamePrm);
        try {
            properties.load(context.getResourceAsStream(filename));
            return true;
        } catch (IOException e) {
            request.setAttribute("exception", "Failed to load data to send password to email. Please, try again");
            return false;
        }
    }
}