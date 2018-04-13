package by.tareltos.fcqdelivery.command.application;

import by.tareltos.fcqdelivery.command.Command;
import by.tareltos.fcqdelivery.command.CommandException;
import by.tareltos.fcqdelivery.command.PagePath;
import by.tareltos.fcqdelivery.entity.application.Application;
import by.tareltos.fcqdelivery.entity.courier.Courier;
import by.tareltos.fcqdelivery.entity.user.User;
import by.tareltos.fcqdelivery.receiver.ApplicationReceiver;
import by.tareltos.fcqdelivery.receiver.CourierReceiver;
import by.tareltos.fcqdelivery.receiver.ReceiverException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class GetApplicationsCommand implements Command {

    final static Logger LOGGER = LogManager.getLogger();
    private static final String LOGINED_USER_PRM = "loginedUser";
    private ApplicationReceiver receiver;


    public GetApplicationsCommand(ApplicationReceiver applicationReceiver) {
        this.receiver = applicationReceiver;
    }

    @Override
    public String execute(HttpServletRequest request) throws ReceiverException, CommandException {
        HttpSession session = request.getSession(true);
        User loginedUser = (User) session.getAttribute(LOGINED_USER_PRM);
        if (null == loginedUser) {
            LOGGER.log(Level.DEBUG, "Пользователь =null");
            return PagePath.PATH_SINGIN_PAGE.getPath();
        }
        if ("admin".equals(loginedUser.getRole().getRole())) {
            LOGGER.log(Level.DEBUG, "Нарушение прав доступа Пользователь: " + loginedUser.getRole().getRole());
            request.setAttribute("errorMessage", "У Вас нет прав доступа к этой странице");
            return PagePath.PATH_INF_PAGE.getPath();
        }

        List<Application> appList = receiver.getAllApplications(loginedUser.getEmail(), loginedUser.getRole());
        if (null == appList) {
            request.setAttribute("errorMessage", "Заявок не найдено");
            return PagePath.PATH_APPLICATIONS_PAGE.getPath();
        }
        request.setAttribute("appList", appList);
        return PagePath.PATH_APPLICATIONS_PAGE.getPath();
    }
}
