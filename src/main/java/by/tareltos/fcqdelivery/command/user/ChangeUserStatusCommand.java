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

public class ChangeUserStatusCommand implements Command {
    final static Logger LOGGER = LogManager.getLogger();
    private static final String MESSAGE_ATR = "message";
    private static final String LOGINED_USER_PRM = "loginedUser";
    private static final String MANAGER_ROLE = "manager";
    private static final String CUSTOMER_ROLE = "customer";
    private static final String EMAIL_PRM = "mail";
    private UserReceiver receiver;


    public ChangeUserStatusCommand(UserReceiver userReceiver) {
        receiver = userReceiver;
    }

    @Override
    public String execute(HttpServletRequest request) {

        HttpSession session = request.getSession(true);
        User loginedUser = (User) session.getAttribute(LOGINED_USER_PRM);
        if (null == loginedUser) {
            return PagePath.PATH_SINGIN_PAGE.getPath();
        }
        try {
            if (!receiver.checkUserStatus(loginedUser.getEmail())) {
                LOGGER.log(Level.WARN, "User is blocked");
                session.setAttribute(LOGINED_USER_PRM, null);
                request.setAttribute(MESSAGE_ATR, "blockedUser.text");
                return PagePath.PATH_SINGIN_PAGE.getPath();
            }
        } catch (ReceiverException e) {
            request.setAttribute("exception", e.getMessage());
            return PagePath.PATH_INF_PAGE.getPath();
        }
        if (CUSTOMER_ROLE.equals(loginedUser.getRole().getRole()) | MANAGER_ROLE.equals(loginedUser.getRole().getRole())) {
            LOGGER.log(Level.INFO, "This page only for Admin! Access denied, you do not have rights: userRole= " + loginedUser.getRole().getRole());
            request.setAttribute(MESSAGE_ATR, "accessDenied.text");
            return PagePath.PATH_INF_PAGE.getPath();
        }
        try {
            if (!receiver.checkUserStatus(loginedUser.getEmail())) {
                session.setAttribute(LOGINED_USER_PRM, null);
                return PagePath.PATH_SINGIN_PAGE.getPath();
            }
        } catch (ReceiverException e) {
            LOGGER.log(Level.WARN, e.getMessage());
            request.setAttribute("exception", e.getMessage());
            return PagePath.PATH_INF_PAGE.getPath();
        }
        String email = request.getParameter(EMAIL_PRM);
        if (DataValidator.validateEmail(email)) {
            try {
                if (!receiver.checkEmail(email) && receiver.changeUserStatus(email)) {
                    request.setAttribute("method", "redirect");
                    request.setAttribute("redirectUrl", "/users?action=get_users");
                    return PagePath.PATH_INF_PAGE.getPath();
                } else {
                    request.setAttribute(MESSAGE_ATR, "error.text");
                    return PagePath.PATH_INF_PAGE.getPath();
                }
            } catch (ReceiverException e) {
                LOGGER.log(Level.WARN, e.getMessage());
            }
        }
        request.setAttribute(MESSAGE_ATR, "invalidData.text");
        return PagePath.PATH_INF_PAGE.getPath();
    }
}
