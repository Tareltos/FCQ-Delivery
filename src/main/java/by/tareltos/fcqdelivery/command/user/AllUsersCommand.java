package by.tareltos.fcqdelivery.command.user;

import by.tareltos.fcqdelivery.command.Command;
import by.tareltos.fcqdelivery.command.PagePath;
import by.tareltos.fcqdelivery.entity.user.User;
import by.tareltos.fcqdelivery.receiver.ReceiverException;
import by.tareltos.fcqdelivery.receiver.UserReceiver;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
/**
 * Class is used to obtain parameters from request,
 * send them into receiver and to return path to jsp page in controller.
 *
 * @autor Tarelko Vitali
 * @see Command
 */
public class AllUsersCommand implements Command {
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
     * The variable stores the name of the session attribute
     */
    private static final String LOGINED_USER = "loginedUser";
    /**
     * Variable used to determine the role of the manager
     */
    private static final String MANAGER_ROLE = "manager";
    /**
     * Variable used to determine the role of the customer
     */
    private static final String CUSTOMER_ROLE = "customer";
    private UserReceiver receiver;

    public AllUsersCommand(UserReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public String execute(HttpServletRequest request) {

        HttpSession session = request.getSession(true);
        User loginedUser = (User) session.getAttribute(LOGINED_USER);
        if (null == loginedUser) {
            return PagePath.PATH_SINGIN_PAGE.getPath();
        }
        try {
            if (!receiver.checkUserStatus(loginedUser.getEmail())) {
                session.setAttribute(LOGINED_USER, null);
                request.setAttribute(MESSAGE, "blockedUser.text");
                return PagePath.PATH_SINGIN_PAGE.getPath();
            }
        } catch (ReceiverException e) {
            LOGGER.log(Level.WARN, e.getMessage());
            request.setAttribute("exception", e.getMessage());
            return PagePath.PATH_INF_PAGE.getPath();
        }
        if (CUSTOMER_ROLE.equals(loginedUser.getRole().getRole()) | MANAGER_ROLE.equals(loginedUser.getRole().getRole())) {
            LOGGER.log(Level.INFO, "This page only for Admin! Access denied, you do not have rights: userRole= " + loginedUser.getRole().getRole());
            request.setAttribute(MESSAGE, "accessDenied.text");
            return PagePath.PATH_INF_PAGE.getPath();
        }
        List<User> users;
        try {
            users = receiver.getAllUsers();
        } catch (ReceiverException e) {
            LOGGER.log(Level.WARN, e.getMessage());
            request.setAttribute("exception", e.getMessage());
            return PagePath.PATH_INF_PAGE.getPath();
        }
        if (null != users & !users.isEmpty()) {
            request.setAttribute("userList", users);
            return PagePath.PATH_USERS_PAGE.getPath();
        } else {
            request.setAttribute(MESSAGE, "dataNotFound.text");
            return PagePath.PATH_INF_PAGE.getPath();
        }
    }
}
