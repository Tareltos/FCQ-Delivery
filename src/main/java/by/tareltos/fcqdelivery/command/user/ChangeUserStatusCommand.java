package by.tareltos.fcqdelivery.command.user;

import by.tareltos.fcqdelivery.command.Command;
import by.tareltos.fcqdelivery.command.PagePath;
import by.tareltos.fcqdelivery.entity.user.User;
import by.tareltos.fcqdelivery.receiver.ReceiverException;
import by.tareltos.fcqdelivery.util.DataValidator;
import org.apache.logging.log4j.Level;
import static by.tareltos.fcqdelivery.command.ParameterStore.*;

import javax.servlet.http.HttpServletRequest;

public class ChangeUserStatusCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
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
        if (DataValidator.validateEmail(email)) {
            try {
                if (!USER_RECEIVER.checkEmail(email) && USER_RECEIVER.changeUserStatus(email)) {
                    request.setAttribute("method", "redirect");
                    request.setAttribute("redirectUrl", "/users?action=get_users");
                    return PagePath.PATH_INF_PAGE.getPath();
                } else {
                    request.setAttribute(MESSAGE, "error.text");
                    return PagePath.PATH_INF_PAGE.getPath();
                }
            } catch (ReceiverException e) {
                LOGGER.log(Level.WARN, e.getMessage());
            }
        }
        request.setAttribute(MESSAGE, "invalidData.text");
        return PagePath.PATH_INF_PAGE.getPath();
    }
}
