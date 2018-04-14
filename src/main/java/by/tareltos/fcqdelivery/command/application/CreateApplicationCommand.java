package by.tareltos.fcqdelivery.command.application;

import by.tareltos.fcqdelivery.command.Command;
import by.tareltos.fcqdelivery.command.CommandException;
import by.tareltos.fcqdelivery.command.PagePath;
import by.tareltos.fcqdelivery.entity.user.User;
import by.tareltos.fcqdelivery.receiver.ApplicationReceiver;
import by.tareltos.fcqdelivery.receiver.ReceiverException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

public class CreateApplicationCommand implements Command {

    private static final String LOGINED_USER_PRM = "loginedUser";



    public CreateApplicationCommand(ApplicationReceiver applicationReceiver) {
    }

    @Override
    public String execute(HttpServletRequest request) throws ReceiverException, CommandException, SQLException {
        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute(LOGINED_USER_PRM);
        if (null == user) {
            return PagePath.PATH_SINGIN_PAGE.getPath();
        }
        if ("customer".equals(user.getRole().getRole())) {




            request.setAttribute("method", "redirect");
            request.setAttribute("redirectUrl", "/applications?action=get_applications");
            return PagePath.PATH_APPLICATIONS_PAGE.getPath();
        } else {
            request.setAttribute("errorMessage", "У вас нет доступа к этой странице");
            return PagePath.PATH_INF_PAGE.getPath();
        }
    }
}
