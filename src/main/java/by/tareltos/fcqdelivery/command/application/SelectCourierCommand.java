package by.tareltos.fcqdelivery.command.application;

import by.tareltos.fcqdelivery.command.Command;
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
import java.util.List;

public class SelectCourierCommand implements Command {

    final static Logger LOGGER = LogManager.getLogger();
    private static final String LOGINED_USER_PRM = "loginedUser";
    private static final String APPLICATION_ID_PRM = "id";
    private ApplicationReceiver receiver;


    public SelectCourierCommand(ApplicationReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        User loginedUser = (User) session.getAttribute(LOGINED_USER_PRM);
        if (null == loginedUser) {
            LOGGER.log(Level.DEBUG, "Пользователь =null");
            return PagePath.PATH_SINGIN_PAGE.getPath();
        }
        if ("admin".equals(loginedUser.getRole().getRole()) | "customer".equals(loginedUser.getRole().getRole())) {   //в константы!!!!
            LOGGER.log(Level.DEBUG, "This page only for Manager! \n Access denied, you do not have rights: userRole= " + loginedUser.getRole().getRole());
            request.setAttribute("message", "accessDenied.text");
            return PagePath.PATH_INF_PAGE.getPath();
        }
        String appId = request.getParameter(APPLICATION_ID_PRM);
        try {
            Application application = receiver.getApplication(appId);
            List<Courier> couriers = receiver.getCourierForAppointment();    // проверки!!!!
            request.setAttribute("application", application);
            request.setAttribute("couriers", couriers);
            return PagePath.PATH_SELECT_COURIER_PAGE.getPath();
        } catch (ReceiverException e) {
            LOGGER.log(Level.WARN, e.getMessage());
            request.setAttribute("message", e.getMessage());
        }
        return PagePath.PATH_INF_PAGE.getPath();
    }
}
