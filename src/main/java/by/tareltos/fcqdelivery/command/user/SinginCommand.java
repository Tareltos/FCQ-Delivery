package by.tareltos.fcqdelivery.command.user;

import by.tareltos.fcqdelivery.command.Command;
import by.tareltos.fcqdelivery.command.PagePath;
import by.tareltos.fcqdelivery.entity.user.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SinginCommand implements Command {


    @Override
    public String execute(HttpServletRequest request) {

        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute("loginedUser");
        if (user != null) {
            return PagePath.PATH_USER_INFO_PAGE.getPath();
        }
        return PagePath.PATH_SINGIN_PAGE.getPath();
    }
}
