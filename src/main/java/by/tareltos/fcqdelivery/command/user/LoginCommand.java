package by.tareltos.fcqdelivery.command.user;

import by.tareltos.fcqdelivery.command.Command;
import by.tareltos.fcqdelivery.command.PagePath;
import by.tareltos.fcqdelivery.entity.user.User;
import by.tareltos.fcqdelivery.receiver.ReceiverException;
import by.tareltos.fcqdelivery.receiver.UserReceiver;
import by.tareltos.fcqdelivery.validator.DataValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Class is used to obtain parameters from request,
 * send them into receiver and to return path to jsp page in controller.
 *
 * @autor Tarelko Vitali
 * @see Command
 */
public class LoginCommand implements Command {
    /**
     * The logger object, used to write logs
     *
     * @see org.apache.logging.log4j.Logger
     */
    private static final Logger LOGGER = LogManager.getLogger();
    /**
     * The variable stores the name of the session attribute
     */
    private static final String LOGINED_USER = "loginedUser";
    /**
     * Parameter name in the request
     */
    private static final String MESSAGE = "message";
    /**
     * Parameter name in the request
     */
    private static final String EMAIL = "mail";
    /**
     * Parameter name in the request
     */
    private static final String PASSWORD = "password";
    private UserReceiver receiver;

    public LoginCommand(UserReceiver userReceiver) {
        receiver = userReceiver;
    }

    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        String email = request.getParameter(EMAIL);
        String password = request.getParameter(PASSWORD);

        if (!DataValidator.validateEmail(email) & !DataValidator.validatePassword(password)) {
            request.setAttribute(MESSAGE, "invalidData.text");
            return PagePath.PATH_SINGIN_PAGE.getPath();
        }
        boolean result;
        try {
            result = receiver.checkUser(email, password);
        } catch (ReceiverException e) {
            LOGGER.log(Level.WARN, e.getMessage());
            request.setAttribute(MESSAGE, "dataNotFound.text");
            return PagePath.PATH_SINGIN_PAGE.getPath();
        }
        if (result) {
            User user;
            try {
                user = receiver.getUserForSession(email);
            } catch (ReceiverException e) {
                LOGGER.log(Level.WARN, e.getMessage());
                request.setAttribute(MESSAGE, "getUserForSessionExc.text");
                return PagePath.PATH_SINGIN_PAGE.getPath();
            }
            session.setAttribute(LOGINED_USER, user);
            return PagePath.PATH_USER_INFO_PAGE.getPath();
        } else {
            request.setAttribute(MESSAGE, "errorInEmailOrPassword.text");
            return PagePath.PATH_SINGIN_PAGE.getPath();
        }
    }

}

