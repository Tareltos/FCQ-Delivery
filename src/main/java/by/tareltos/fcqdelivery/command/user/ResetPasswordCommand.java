package by.tareltos.fcqdelivery.command.user;

import by.tareltos.fcqdelivery.command.Command;
import by.tareltos.fcqdelivery.command.PagePath;
import by.tareltos.fcqdelivery.receiver.ReceiverException;
import by.tareltos.fcqdelivery.receiver.UserReceiver;
import by.tareltos.fcqdelivery.validator.DataValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Properties;

/**
 * Class is used to obtain parameters from request,
 * send them into receiver and to return path to jsp page in controller.
 *
 * @autor Tarelko Vitali
 * @see Command
 */
public class ResetPasswordCommand implements Command {
    /**
     * The logger object, used to write logs
     *
     * @see org.apache.logging.log4j.Logger
     */
    private static final Logger LOGGER = LogManager.getLogger();
    /**
     * Parameter name in the request
     */
    private static final String MESSAGE = "message";
    /**
     * Properties file name
     */
    private static final String FILE_NAME = "mail";
    /**
     * Parameter name in the request
     */
    private static final String EMAIL = "mail";
    private UserReceiver receiver;


    public ResetPasswordCommand(UserReceiver userReceiver) {
        receiver = userReceiver;
    }

    @Override
    public String execute(HttpServletRequest request) {
        Properties properties = new Properties();
        ServletContext context = request.getServletContext();
        String filename = context.getInitParameter(FILE_NAME);
        try {
            properties.load(context.getResourceAsStream(filename));
        } catch (IOException e) {
            LOGGER.log(Level.WARN, e.getMessage());
            request.setAttribute("exception", "Failed to load data to send password to email. Please, try again");
            return PagePath.PATH_INF_PAGE.getPath();
        }

        String email = request.getParameter(EMAIL);
        if (!DataValidator.validateEmail(email)) {
            request.setAttribute(MESSAGE, "invalidData.text");
            return PagePath.PATH_SINGIN_PAGE.getPath();
        }
        boolean result;
        try {
            result = receiver.resetUserPassword(email, properties);
        } catch (ReceiverException e) {
            LOGGER.log(Level.WARN, e.getMessage());
            request.setAttribute(MESSAGE, "passwordUpdateError.text");
            return PagePath.PATH_SINGIN_PAGE.getPath();
        }
        if (result) {
            request.setAttribute(MESSAGE, "updatePassword.text");
        } else {
            request.setAttribute(MESSAGE, "userNotFound.text");
        }
        return PagePath.PATH_SINGIN_PAGE.getPath();

    }
}
