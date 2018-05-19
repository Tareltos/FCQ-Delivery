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
    /**
     * The logger object, used to write logs
     *
     * @see org.apache.logging.log4j.Logger
     */
    private static final Logger LOGGER = LogManager.getLogger();
    /**
     * The variable stores the name of the session attribute
     */
    private static final String LOGINED_USER = "loginedUser";

    public LogoutCommand() {
    }

    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        session.setAttribute(LOGINED_USER, null);
        LOGGER.log(Level.DEBUG, "Kill user in session!");
        return PagePath.PATH_SINGIN_PAGE.getPath();
    }
}
