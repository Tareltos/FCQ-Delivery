package by.tareltos.fcqdelivery.command.user;

import by.tareltos.fcqdelivery.command.Command;
import by.tareltos.fcqdelivery.command.PagePath;
import by.tareltos.fcqdelivery.entity.user.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Class is used to obtain parameters from request,
 * send them into receiver and to return path to jsp page in controller.
 *
 * @autor Tarelko Vitali
 * @see Command
 */
public class SinginCommand implements Command {
    /**
     * The variable stores the name of the session attribute
     */
    private static final String LOGINED_USER = "loginedUser";

    @Override
    public String execute(HttpServletRequest request) {

        HttpSession session = request.getSession(true);
        User loginedUser = (User) session.getAttribute(LOGINED_USER);
        if (loginedUser != null) {
            return PagePath.PATH_USER_INFO_PAGE.getPath();
        }
        return PagePath.PATH_SINGIN_PAGE.getPath();
    }
}
