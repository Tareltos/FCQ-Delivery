package by.tareltos.fcqdelivery.command.user;

import by.tareltos.fcqdelivery.command.Command;
import by.tareltos.fcqdelivery.command.CommandUtil;
import by.tareltos.fcqdelivery.command.PagePath;
import by.tareltos.fcqdelivery.entity.user.User;
import by.tareltos.fcqdelivery.receiver.ReceiverException;
import by.tareltos.fcqdelivery.receiver.UserReceiver;
import by.tareltos.fcqdelivery.validator.DataValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Properties;

/**
 * Class is used to obtain parameters from request,
 * send them into receiver and to return path to jsp page in controller.
 *
 * @autor Tarelko Vitali
 * @see Command
 */
public class CreateUserCommand implements Command {
    /**
     * The logger object, used to write logs
     *
     * @see org.apache.logging.log4j.Logger
     */
    private static final Logger LOGGER = LogManager.getLogger();
    /**
     * Parameter name in the request
     */
    private static final String LOCALE = "locale";
    /**
     * Parameter name in the request
     */
    private static final String MESSAGE = "message";
    /**
     * Parameter name in the request
     */
    private static final String EMAIL = "mail";
    /**
     * Properties file name
     */
    private static final String FILE_NAME = "mail";
    /**
     * The variable stores the name of the session attribute
     */
    private static final String LOGINED_USER = "loginedUser";
    /**
     * Parameter name in the request
     */
    private static final String FIRST_NAME = "fName";
    /**
     * Parameter name in the request
     */
    private static final String LAST_NAME = "lName";
    /**
     * Parameter name in the request
     */
    private static final String PHONE_PRM = "phone";
    /**
     * Parameter name in the request
     */
    private static final String ROLE_PRM = "role";
    /**
     * Variable used to determine the role of the manager
     */
    private static final String MANAGER_ROLE = "manager";
    /**
     * Variable used to determine the role of the customer
     */
    private static final String CUSTOMER_ROLE = "customer";
    private UserReceiver receiver;

    public CreateUserCommand(UserReceiver userReceiver) {
        receiver = userReceiver;
    }

    @Override
    public String execute(HttpServletRequest request) {
        Properties properties = new Properties();
        if (!CommandUtil.loadProperies(request, properties, FILE_NAME)) {
            return PagePath.PATH_INF_PAGE.getPath();
        }
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
            request.setAttribute("exception", e.getMessage());
            return PagePath.PATH_INF_PAGE.getPath();
        }
        if (CUSTOMER_ROLE.equals(loginedUser.getRole().getRole()) | MANAGER_ROLE.equals(loginedUser.getRole().getRole())) {
            LOGGER.log(Level.INFO, "This page only for Admin! Access denied, you do not have rights: userRole= " + loginedUser.getRole().getRole());
            request.setAttribute(MESSAGE, "accessDenied.text");
            return PagePath.PATH_INF_PAGE.getPath();
        }
        String email = request.getParameter(EMAIL);
        String fName = request.getParameter(FIRST_NAME);
        String lName = request.getParameter(LAST_NAME);
        String phone = request.getParameter(PHONE_PRM);
        String locale = request.getParameter(LOCALE);
        try {
            if (!receiver.checkEmail(email)) {
                request.setAttribute(MESSAGE, "userExist.text");
                return PagePath.PATH_INF_PAGE.getPath();
            }
        } catch (ReceiverException e) {
            LOGGER.log(Level.WARN, e.getMessage());
        }
        if (DataValidator.validateEmail(email) && DataValidator.validateName(fName) && DataValidator.validateName(lName) && DataValidator.validatePhone(phone)) {
            String role = request.getParameter(ROLE_PRM);
            try {
                receiver.createUser(email, fName, lName, phone, role, properties, locale);
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
