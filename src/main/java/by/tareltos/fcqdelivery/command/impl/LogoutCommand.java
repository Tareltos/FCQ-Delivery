package by.tareltos.fcqdelivery.command.impl;

import by.tareltos.fcqdelivery.command.Command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LogoutCommand implements Command {
    private static final String PATH_SINGIN_PAGE = "/jsp/singin.jsp";


    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        session.setAttribute("loginedUser", null);
        return PATH_SINGIN_PAGE;
    }
}
