package by.tareltos.fcqdelivery.command.courierCommand;

import by.tareltos.fcqdelivery.command.Command;
import by.tareltos.fcqdelivery.command.PagePath;
import by.tareltos.fcqdelivery.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


public class CreateCourierFormCommand implements Command {

    private static final String LOGINED_USER_PRM = "loginedUser";


    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute(LOGINED_USER_PRM);
        if (null == user) {
            return PagePath.PATH_SINGIN_PAGE.getPath();
        }
        if ("manager".equals(user.getRole().getRole())) {
            return PagePath.PATH_NEW_COURIER_FORM.getPath();
        } else {
            request.setAttribute("errorMessage", "У вас нет доступа к этой странице");
            return PagePath.PATH_INF_PAGE.getPath();
        }
    }
}
