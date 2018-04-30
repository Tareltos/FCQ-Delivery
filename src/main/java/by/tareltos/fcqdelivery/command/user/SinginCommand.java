package by.tareltos.fcqdelivery.command.user;

import by.tareltos.fcqdelivery.command.Command;
import by.tareltos.fcqdelivery.command.PagePath;
import by.tareltos.fcqdelivery.entity.user.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SinginCommand implements Command {

    private static final String LOGINED_USER_PRM = "loginedUser";

    public SinginCommand() {
    }

    @Override
    public String execute(HttpServletRequest request) {

        HttpSession session = request.getSession(true);
        User loginedUser = (User) session.getAttribute(LOGINED_USER_PRM);
        if (loginedUser != null) {
            return PagePath.PATH_USER_INFO_PAGE.getPath();
        }
        return PagePath.PATH_SINGIN_PAGE.getPath();
    }
}
