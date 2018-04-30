package by.tareltos.fcqdelivery.command.application;

import by.tareltos.fcqdelivery.command.Command;
import by.tareltos.fcqdelivery.command.PagePath;
import by.tareltos.fcqdelivery.entity.application.Application;
import by.tareltos.fcqdelivery.entity.user.User;
import by.tareltos.fcqdelivery.receiver.ApplicationReceiver;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class FindApplicationsCommand implements Command {

    final static Logger LOGGER = LogManager.getLogger();
    private static final String LOGINED_USER_PRM = "loginedUser";
    private static final String CUSTOMER_ROLE = "customer";
    private static final String ADMIN_ROLE = "admin";
    private static final String CUSTOMER_EMAIL_PRM = "email";
    private static final String APPLICATION_STATUS_PRM = "status";
    private ApplicationReceiver receiver;

    public FindApplicationsCommand(ApplicationReceiver applicationReceiver) {
        this.receiver = applicationReceiver;
    }

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        User loginedUser = (User) session.getAttribute(LOGINED_USER_PRM);
        if (null == loginedUser) {
            return PagePath.PATH_SINGIN_PAGE.getPath();
        }
        if (ADMIN_ROLE.equals(loginedUser.getRole().getRole()) | CUSTOMER_ROLE.equals(loginedUser.getRole().getRole())) {
            LOGGER.log(Level.DEBUG, "This page only for Manager! Access denied, you do not have rights: userRole= " + loginedUser.getRole().getRole());
            request.setAttribute("message", "accessDenied.text");
            return PagePath.PATH_INF_PAGE.getPath();
        }
        String customerEmail = request.getParameter(CUSTOMER_EMAIL_PRM);
        LOGGER.log(Level.DEBUG, "Customer email" + customerEmail);
        String applicationStatus = request.getParameter(APPLICATION_STATUS_PRM);

        List<Application> appList = receiver.getSelectedApplications(customerEmail, applicationStatus);
        if (null == appList) {
            request.setAttribute("message", "dataNotFound.text");
            return PagePath.PATH_INF_PAGE.getPath();
        }
        request.setAttribute("appList", appList);
        return PagePath.PATH_APPLICATIONS_PAGE.getPath();
    }
}
