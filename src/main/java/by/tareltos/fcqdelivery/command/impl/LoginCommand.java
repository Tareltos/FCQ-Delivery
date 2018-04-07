package by.tareltos.fcqdelivery.command.impl;

import by.tareltos.fcqdelivery.command.Command;
import by.tareltos.fcqdelivery.command.PagePath;
import by.tareltos.fcqdelivery.receiver.ReceiverException;
import by.tareltos.fcqdelivery.receiver.UserReceiver;
import by.tareltos.fcqdelivery.validator.DataValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

public class LoginCommand implements Command {

    private static final String EMAIL_PRM = "mail";
    private static final String PASSWORD_PRM = "password";
    private UserReceiver receiver;

    public LoginCommand(UserReceiver userReceiver) {
        receiver = userReceiver;
    }

    public String execute(HttpServletRequest request) throws ReceiverException {

        String email = request.getParameter(EMAIL_PRM);
        String password = request.getParameter(PASSWORD_PRM);
        HttpSession session = request.getSession(true);

        if (DataValidator.validateEmail(email) && DataValidator.validatePassword(password)) {
            if (receiver.checkUser(email, password)) {
                session.setAttribute("loginedUser", receiver.getUserForSession(email));
                return PagePath.PATH_USER_INFO_PAGE.getPath();
            } else {
                request.setAttribute("errorLoginMessage", "Неверный пароль либо пользователь заблокирован");
                return PagePath.PATH_SINGIN_PAGE.getPath();
            }
        } else {
            request.setAttribute("errorLoginMessage", "Короткий пароль");
            return PagePath.PATH_SINGIN_PAGE.getPath();
        }
    }
}
