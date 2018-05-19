package by.tareltos.fcqdelivery.command.user;

import by.tareltos.fcqdelivery.command.Command;
import by.tareltos.fcqdelivery.command.PagePath;

import javax.servlet.http.HttpServletRequest;

/**
 * Class is used to obtain parameters from request,
 * send them into receiver and to return path to jsp page in controller.
 *
 * @autor Tarelko Vitali
 * @see Command
 */
public class MainCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        return PagePath.PATH_MAIN_PAGE.getPath();
    }
}
