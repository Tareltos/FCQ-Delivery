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

public class CreateUserCommand implements Command {

    final static Logger LOGGER = LogManager.getLogger();
    /**
     * Parameter name in the request
     */
    private static final String LOCALE = "locale";
    private static final String MESSAGE_ATR = "message";
    private static final String EMAIL_PRM = "mail";
    private static final String FILE_NAME = "mail";
    private static final String LOGINED_USER_PRM = "loginedUser";
    private static final String FIRST_NAME_PRM = "fName";
    private static final String LAST_NAME_PRM = "lName";
    private static final String PHONE_PRM = "phone";
    private static final String ROLE_PRM = "role";
    private static final String MANAGER_ROLE = "manager";
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
        User loginedUser = (User) session.getAttribute(LOGINED_USER_PRM);
        if (null == loginedUser) {
            return PagePath.PATH_SINGIN_PAGE.getPath();
        }
        try {
            if (!receiver.checkUserStatus(loginedUser.getEmail())) {
                session.setAttribute(LOGINED_USER_PRM, null);
                request.setAttribute(MESSAGE_ATR, "blockedUser.text");
                return PagePath.PATH_SINGIN_PAGE.getPath();
            }
        } catch (ReceiverException e) {
            LOGGER.log(Level.WARN, e.getMessage());
            request.setAttribute("exception", e.getMessage());
            return PagePath.PATH_INF_PAGE.getPath();
        }
        if (CUSTOMER_ROLE.equals(loginedUser.getRole().getRole()) | MANAGER_ROLE.equals(loginedUser.getRole().getRole())) {
            LOGGER.log(Level.INFO, "This page only for Admin! Access denied, you do not have rights: userRole= " + loginedUser.getRole().getRole());
            request.setAttribute(MESSAGE_ATR, "accessDenied.text");
            return PagePath.PATH_INF_PAGE.getPath();
        }
        String email = request.getParameter(EMAIL_PRM);
        String fName = request.getParameter(FIRST_NAME_PRM);
        String lName = request.getParameter(LAST_NAME_PRM);
        String phone = request.getParameter(PHONE_PRM);
        String locale = request.getParameter(LOCALE);
        try {
            if (!receiver.checkEmail(email)) {
                request.setAttribute(MESSAGE_ATR, "userExist.text");
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
            request.setAttribute(MESSAGE_ATR, "invalidData.text");
            return PagePath.PATH_INF_PAGE.getPath();
        }
    }
}
