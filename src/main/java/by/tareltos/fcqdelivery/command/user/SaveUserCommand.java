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
public class SaveUserCommand implements Command {
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
    private static final String PHONE = "phone";
    private UserReceiver receiver;

    public SaveUserCommand(UserReceiver userReceiver) {
        receiver = userReceiver;
    }

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        User loginedUser = (User) session.getAttribute(LOGINED_USER);
        try {
            if (!receiver.checkUserStatus(loginedUser.getEmail())) {
                session.setAttribute(LOGINED_USER, null);
                LOGGER.log(Level.WARN, "User is blocked");
                request.setAttribute(MESSAGE, "blockedUser.text");
                return PagePath.PATH_SINGIN_PAGE.getPath();
            }
        } catch (ReceiverException e) {
            request.setAttribute("exception", e.getMessage());
            return PagePath.PATH_INF_PAGE.getPath();
        }
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
            result = receiver.updateUser(loginedUser);
        } catch (ReceiverException e) {
            LOGGER.log(Level.WARN, e.getMessage());
            request.setAttribute("exception", e.getMessage());
            return PagePath.PATH_INF_PAGE.getPath();
        }
        if (result) {
            try {
                loginedUser = receiver.getUserForSession(loginedUser.getEmail());
            } catch (ReceiverException e) {
                LOGGER.log(Level.WARN, e.getMessage());
            }
            session.setAttribute(LOGINED_USER, loginedUser);
            return PagePath.PATH_USER_INFO_PAGE.getPath();
        } else {
            request.setAttribute(MESSAGE, "error.text");
            return PagePath.PATH_INF_PAGE.getPath();
        }

    }
}
