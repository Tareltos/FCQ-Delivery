package by.tareltos.fcqdelivery.command.impl;

import by.tareltos.fcqdelivery.command.Command;
import by.tareltos.fcqdelivery.entity.User;
import by.tareltos.fcqdelivery.receiver.UserReceiver;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class ChangeUserStatusCommand implements Command {
    final static Logger LOGGER = LogManager.getLogger();

    private static final String EMAIL_PRM = "mail";
    private static final String PATH_INF_PAGE = "/jsp/inf.jsp";
    private static final String PATH_SINGIN_PAGE = "/jsp/singin.jsp";
    private UserReceiver receiver;


    public ChangeUserStatusCommand(UserReceiver userReceiver) {
        receiver = userReceiver;
    }

    @Override
    public String execute(HttpServletRequest request) throws IOException, SQLException, ClassNotFoundException {

        HttpSession session = request.getSession(true);
        User admin = (User) session.getAttribute("loginedUser");
        if (null != admin) {
            String email = request.getParameter(EMAIL_PRM);
            LOGGER.log(Level.DEBUG, email);
            if (!receiver.checkEmail(email) && receiver.changeUserStatus(email)) {
                request.setAttribute("successfulMsg", "Статус успешно изменен!");
                return PATH_INF_PAGE;
            } else {
                request.setAttribute("errorMessage", "Статус Пользователя не изменен");
                return PATH_INF_PAGE;
            }

        } else {
            return PATH_SINGIN_PAGE;
        }
    }
}
