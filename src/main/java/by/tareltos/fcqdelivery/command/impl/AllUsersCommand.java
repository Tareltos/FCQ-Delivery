package by.tareltos.fcqdelivery.command.impl;

import by.tareltos.fcqdelivery.command.Command;
import by.tareltos.fcqdelivery.entity.User;
import by.tareltos.fcqdelivery.receiver.UserReceiver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AllUsersCommand implements Command {

    final static Logger LOGGER = LogManager.getLogger();
    private static final String LOGINED_USER_PRM= "loginedUser";
    private static final String PATH_USERS_PAGE = "/jsp/users.jsp";
    private UserReceiver receiver;

    public AllUsersCommand(UserReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public String execute(HttpServletRequest request) throws IOException, SQLException, ClassNotFoundException {
        String page;
        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute(LOGINED_USER_PRM);
        if ("admin".equals(user.getRole().getRole())) {
            List<User> list = receiver.getAllUsers();
            if (!list.isEmpty()) {
                request.setAttribute("userList", list);
                page = PATH_USERS_PAGE;
            } else {
                request.setAttribute("errorMessage", "Пользователи не найдены");
                page = PATH_USERS_PAGE;
            }

        } else {
            request.setAttribute("errorMessage", "У вас нет доступа к этой странице");
            page = PATH_USERS_PAGE;
        }
        return page;
    }
}
