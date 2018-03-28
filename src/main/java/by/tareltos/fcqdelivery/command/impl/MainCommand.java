package by.tareltos.fcqdelivery.command.impl;

import by.tareltos.fcqdelivery.command.Command;

import javax.servlet.http.HttpServletRequest;

public class MainCommand implements Command {

    private static final String PATH_MAIN_PAGE = "/index.jsp";

    @Override
    public String execute(HttpServletRequest request) {
        return PATH_MAIN_PAGE;
    }
}
