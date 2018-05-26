package by.tareltos.fcqdelivery.command.user;

import by.tareltos.fcqdelivery.command.Command;
import by.tareltos.fcqdelivery.command.PagePath;
import by.tareltos.fcqdelivery.entity.user.User;
import by.tareltos.fcqdelivery.receiver.ReceiverException;
import by.tareltos.fcqdelivery.util.DataValidator;
import org.apache.logging.log4j.Level;

import javax.servlet.http.HttpServletRequest;
import java.util.Properties;

import static by.tareltos.fcqdelivery.command.ParameterStore.*;

/**
 * Class is used to obtain parameters from request,
 * send them into receiver and to return path to jsp page in controller.
 *
 * @autor Tarelko Vitali
 * @see Command
 */
public class CreateUserCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        Properties properties = new Properties();
        if (!loadProperies(request, properties, FILE_NAME)) {
            return PagePath.PATH_INF_PAGE.getPath();
        }
        User loginedUser = getUser(request);
        if (null == loginedUser) {
            return PagePath.PATH_SINGIN_PAGE.getPath();
        }
        if (CUSTOMER_ROLE.equals(loginedUser.getRole().getRole()) | MANAGER_ROLE.equals(loginedUser.getRole().getRole())) {
            LOGGER.log(Level.INFO, "This page only for Admin! Access denied, you do not have rights: userRole= " + loginedUser.getRole().getRole());
            request.setAttribute(MESSAGE, "accessDenied.text");
            return PagePath.PATH_INF_PAGE.getPath();
        }
        String email = request.getParameter(EMAIL);
        String fName = request.getParameter(FIRST_NAME);
        String lName = request.getParameter(LAST_NAME);
        String phone = request.getParameter(PHONE);
        String locale = request.getParameter(LOCALE);
        try {
            if (!USER_RECEIVER.checkEmail(email)) {
                request.setAttribute(MESSAGE, "userExist.text");
                return PagePath.PATH_INF_PAGE.getPath();
            }
        } catch (ReceiverException e) {
            LOGGER.log(Level.WARN, e.getMessage());
        }
        if (DataValidator.validateEmail(email) && DataValidator.validateName(fName) && DataValidator.validateName(lName) && DataValidator.validatePhone(phone)) {
            String role = request.getParameter(ROLE);
            try {
                USER_RECEIVER.createUser(email, fName, lName, phone, role, properties, locale);
            } catch (ReceiverException e) {
                LOGGER.log(Level.WARN, e.getMessage());
            }
            request.setAttribute("method", "redirect");
            request.setAttribute("redirectUrl", "/users?action=get_users");
            return PagePath.PATH_INF_PAGE.getPath();
        } else {
            request.setAttribute(MESSAGE, "invalidData.text");
            return PagePath.PATH_INF_PAGE.getPath();
        }
    }
}
