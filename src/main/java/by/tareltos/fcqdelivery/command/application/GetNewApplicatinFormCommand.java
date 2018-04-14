package by.tareltos.fcqdelivery.command.application;

import by.tareltos.fcqdelivery.command.Command;
import by.tareltos.fcqdelivery.command.PagePath;
import by.tareltos.fcqdelivery.entity.user.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class GetNewApplicatinFormCommand  implements Command{

    private static final String LOGINED_USER_PRM = "loginedUser";

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute(LOGINED_USER_PRM);
        if (null == user) {
            return PagePath.PATH_SINGIN_PAGE.getPath();
        }
        if ("customer".equals(user.getRole().getRole())) {
            return PagePath.PATH_NEWAPP_FORM_PAGE.getPath();
        } else {
            request.setAttribute("errorMessage", "У вас нет доступа к этой странице");
            return PagePath.PATH_INF_PAGE.getPath();
        }
    }

}
