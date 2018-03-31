package by.tareltos.fcqdelivery.command.impl;

import by.tareltos.fcqdelivery.command.Command;
import by.tareltos.fcqdelivery.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class SinginCommand implements Command {

    private static final String PATH_SINGIN_PAGE = "/jsp/singin.jsp";
    private static final String PATH_USER_INFO_PAGE = "/jsp/userInfo.jsp";

    @Override
    public String execute(HttpServletRequest request) {

        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute("loginedUser");
        if (user != null) {
            return PATH_USER_INFO_PAGE;
        }
        return PATH_SINGIN_PAGE;
    }
}
