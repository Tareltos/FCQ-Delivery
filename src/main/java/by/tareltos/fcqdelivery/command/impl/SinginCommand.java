package by.tareltos.fcqdelivery.command.impl;

import by.tareltos.fcqdelivery.command.Command;

import javax.servlet.http.HttpServletRequest;

public class SinginCommand implements Command {

    private static final String PATH_SINGIN_PAGE = "/jsp/singin.jsp";

    @Override
    public String execute(HttpServletRequest request) {
        return PATH_SINGIN_PAGE;
    }
}
