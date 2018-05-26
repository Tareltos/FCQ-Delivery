package by.tareltos.fcqdelivery.command.user;

import by.tareltos.fcqdelivery.command.Command;
import by.tareltos.fcqdelivery.command.PagePath;
import by.tareltos.fcqdelivery.entity.user.User;
import by.tareltos.fcqdelivery.receiver.ReceiverException;
import by.tareltos.fcqdelivery.util.DataValidator;
import org.apache.logging.log4j.Level;
import static by.tareltos.fcqdelivery.command.ParameterStore.*;
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

    @Override
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
            result = USER_RECEIVER.checkUser(email, password);
        } catch (ReceiverException e) {
            LOGGER.log(Level.WARN, e.getMessage());
            request.setAttribute(MESSAGE, "dataNotFound.text");
            return PagePath.PATH_SINGIN_PAGE.getPath();
        }
        if (result) {
            User user;
            try {
                user = USER_RECEIVER.getUserForSession(email);
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

