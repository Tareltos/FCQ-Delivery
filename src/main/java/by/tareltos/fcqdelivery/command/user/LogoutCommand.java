package by.tareltos.fcqdelivery.command.user;

import by.tareltos.fcqdelivery.command.Command;
import by.tareltos.fcqdelivery.command.PagePath;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Class is used to obtain parameters from request,
 * send them into receiver and to return path to jsp page in controller.
 *
 * @autor Tarelko Vitali
 * @see Command
 */
public class LogoutCommand implements Command {

    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        session.invalidate();
        LOGGER.log(Level.DEBUG, "Invalidate session!");
        return PagePath.PATH_SINGIN_PAGE.getPath();
    }
}
