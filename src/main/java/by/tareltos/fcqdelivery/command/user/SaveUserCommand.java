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

public class SaveUserCommand implements Command {

    final static Logger LOGGER = LogManager.getLogger();
    private static final String MESSAGE_ATR = "message";
    private static final String LOGINED_USER_PRM = "loginedUser";
    private static final String FIRST_NAME_PRM = "fName";
    private static final String LAST_NAME_PRM = "lName";
    private static final String PHONE_PRM = "phone";
    private UserReceiver receiver;

    public SaveUserCommand(UserReceiver userReceiver) {
        receiver = userReceiver;
    }

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        User loginedUser = (User) session.getAttribute(LOGINED_USER_PRM);
        if (null == loginedUser) {
            return PagePath.PATH_SINGIN_PAGE.getPath();
        }
        String fname = request.getParameter(FIRST_NAME_PRM);
        String lname = request.getParameter(LAST_NAME_PRM);
        String phone = request.getParameter(PHONE_PRM);
        if (!DataValidator.validateName(fname) & !DataValidator.validateName(lname) & !DataValidator.validatePhone(phone)) {
            request.setAttribute(MESSAGE_ATR, "invalidData.text");
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
            session.setAttribute(LOGINED_USER_PRM, loginedUser);
            return PagePath.PATH_USER_INFO_PAGE.getPath();
        } else {
            request.setAttribute(MESSAGE_ATR, "error.text");
            return PagePath.PATH_INF_PAGE.getPath();
        }

    }
}
