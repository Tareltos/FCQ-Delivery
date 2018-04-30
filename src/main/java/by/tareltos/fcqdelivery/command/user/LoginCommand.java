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

public class LoginCommand implements Command {

    final static Logger LOGGER = LogManager.getLogger();
    private static final String LOGINED_USER_PRM = "loginedUser";
    private static final String MESSAGE_ATR = "message";
    private static final String EMAIL_PRM = "mail";
    private static final String PASSWORD_PRM = "password";
    private UserReceiver receiver;

    public LoginCommand(UserReceiver userReceiver) {
        receiver = userReceiver;
    }

    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        String email = request.getParameter(EMAIL_PRM);
        String password = request.getParameter(PASSWORD_PRM);

        if (!DataValidator.validateEmail(email) & !DataValidator.validatePassword(password)) {
            request.setAttribute(MESSAGE_ATR, "invalidData.text");
            return PagePath.PATH_SINGIN_PAGE.getPath();
        }
        boolean result;
        try {
            result = receiver.checkUser(email, password);
        } catch (ReceiverException e) {
            LOGGER.log(Level.WARN, e.getMessage());
            request.setAttribute(MESSAGE_ATR, "dataNotFound.text");
            return PagePath.PATH_SINGIN_PAGE.getPath();
        }
        if (result) {
            User user;
            try {
                user = receiver.getUserForSession(email);
            } catch (ReceiverException e) {
                LOGGER.log(Level.WARN, e.getMessage());
                request.setAttribute(MESSAGE_ATR, "getUserForSessionExc.text");
                return PagePath.PATH_SINGIN_PAGE.getPath();
            }
            session.setAttribute(LOGINED_USER_PRM, user);
            return PagePath.PATH_USER_INFO_PAGE.getPath();
        } else {
            request.setAttribute(MESSAGE_ATR, "errorInEmailOrPassword.text");
            return PagePath.PATH_SINGIN_PAGE.getPath();
        }
    }

}

