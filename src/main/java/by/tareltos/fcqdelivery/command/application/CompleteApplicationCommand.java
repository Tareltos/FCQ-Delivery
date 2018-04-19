package by.tareltos.fcqdelivery.command.application;

import by.tareltos.fcqdelivery.command.Command;
import by.tareltos.fcqdelivery.command.CommandException;
import by.tareltos.fcqdelivery.command.PagePath;
import by.tareltos.fcqdelivery.entity.application.Application;
import by.tareltos.fcqdelivery.entity.courier.Courier;
import by.tareltos.fcqdelivery.entity.user.User;
import by.tareltos.fcqdelivery.receiver.ApplicationReceiver;
import by.tareltos.fcqdelivery.receiver.ReceiverException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;

public class CompleteApplicationCommand implements Command {

    final static Logger LOGGER = LogManager.getLogger();
    private static final String LOGINED_USER_PRM = "loginedUser";
    private static final String APPLICATION_ID_PRM = "id";
    private ApplicationReceiver receiver;


    public CompleteApplicationCommand(ApplicationReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public String execute(HttpServletRequest request) throws ReceiverException, CommandException, SQLException {
        HttpSession session = request.getSession(true);
        User loginedUser = (User) session.getAttribute(LOGINED_USER_PRM);
        if (null == loginedUser) {
            LOGGER.log(Level.DEBUG, "Пользователь = null");
            return PagePath.PATH_SINGIN_PAGE.getPath();
        }
        if ("admin".equals(loginedUser.getRole().getRole()) | "manager".equals(loginedUser.getRole().getRole())) {   //в константы!!!!
            LOGGER.log(Level.DEBUG, "Нарушение прав доступа Пользователь: " + loginedUser.getRole().getRole());
            request.setAttribute("errorMessage", "У Вас нет прав доступа к этой странице");
            return PagePath.PATH_INF_PAGE.getPath();
        }

        String applicationId = request.getParameter(APPLICATION_ID_PRM);
        if (receiver.completeApplication(applicationId)) {
            Application application = receiver.getApplication(applicationId);

            request.setAttribute("application", application);
            return PagePath.PATH_APPLICATION_INFO_PAGE.getPath();
        }
        request.setAttribute("errorMessage", "Заявка не закрыта");
        return PagePath.PATH_INF_PAGE.getPath();


    }
}