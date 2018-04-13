package by.tareltos.fcqdelivery.command.user;

import by.tareltos.fcqdelivery.command.Command;
import by.tareltos.fcqdelivery.command.PagePath;
import by.tareltos.fcqdelivery.entity.User;
import by.tareltos.fcqdelivery.receiver.ReceiverException;
import by.tareltos.fcqdelivery.receiver.UserReceiver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class AllUsersCommand implements Command {

    final static Logger LOGGER = LogManager.getLogger();
    private static final String LOGINED_USER_PRM = "loginedUser";
    private UserReceiver receiver;

    public AllUsersCommand(UserReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public String execute(HttpServletRequest request) throws ReceiverException {

        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute(LOGINED_USER_PRM);
        if (receiver.checkUserStatus(user.getEmail())) {
            if ("admin".equals(user.getRole().getRole())) {  //в константы!!!!!
                List<User> list = receiver.getAllUsers();
                if (!list.isEmpty()) {
                    request.setAttribute("userList", list);
                    return PagePath.PATH_USERS_PAGE.getPath();
                } else {
                    request.setAttribute("errorMessage", "Пользователи не найдены"); //в константы!!!!
                    return PagePath.PATH_USERS_PAGE.getPath();
                }

            } else {
                request.setAttribute("errorMessage", "У вас нет доступа к этой странице");// engl/
                return PagePath.PATH_INF_PAGE.getPath();
            }
        } else {
            session.setAttribute(LOGINED_USER_PRM, null);
            return PagePath.PATH_SINGIN_PAGE.getPath();
        }
    }
}
