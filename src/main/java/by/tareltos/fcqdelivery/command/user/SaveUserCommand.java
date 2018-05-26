package by.tareltos.fcqdelivery.command.user;

import by.tareltos.fcqdelivery.command.Command;
import by.tareltos.fcqdelivery.command.PagePath;
import by.tareltos.fcqdelivery.entity.user.User;
import by.tareltos.fcqdelivery.receiver.ReceiverException;
import by.tareltos.fcqdelivery.util.DataValidator;
import org.apache.logging.log4j.Level;

import javax.servlet.http.HttpServletRequest;

import static by.tareltos.fcqdelivery.command.ParameterStore.*;

/**
 * Class is used to obtain parameters from request,
 * send them into receiver and to return path to jsp page in controller.
 *
 * @autor Tarelko Vitali
 * @see Command
 */
public class SaveUserCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        User loginedUser = getUser(request);
        if (null == loginedUser) {
            return PagePath.PATH_SINGIN_PAGE.getPath();
        }
        String fname = request.getParameter(FIRST_NAME);
        String lname = request.getParameter(LAST_NAME);
        String phone = request.getParameter(PHONE);
        if (!DataValidator.validateName(fname) & !DataValidator.validateName(lname) & !DataValidator.validatePhone(phone)) {
            request.setAttribute(MESSAGE, "invalidData.text");
            return PagePath.PATH_INF_PAGE.getPath();
        }
        loginedUser.setFirstName(fname);
        loginedUser.setLastName(lname);
        loginedUser.setPhone(phone);
        boolean result;
        try {
            result = USER_RECEIVER.updateUser(loginedUser);
        } catch (ReceiverException e) {
            LOGGER.log(Level.WARN, e.getMessage());
            request.setAttribute("exception", e.getMessage());
            return PagePath.PATH_INF_PAGE.getPath();
        }
        if (result) {
            try {
                loginedUser = USER_RECEIVER.getUserForSession(loginedUser.getEmail());
            } catch (ReceiverException e) {
                LOGGER.log(Level.WARN, e.getMessage());
            }
            request.getSession().setAttribute(LOGINED_USER, loginedUser);
            return PagePath.PATH_USER_INFO_PAGE.getPath();
        } else {
            request.setAttribute(MESSAGE, "error.text");
            return PagePath.PATH_INF_PAGE.getPath();
        }

    }
}
