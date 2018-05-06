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

/**
 * Class is used to obtain parameters from request,
 * send them into receiver and to return path to jsp page in controller.
 *
 * @autor Tarelko Vitali
 * @see Command
 */
public class FindApplicationsCommand implements Command {
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
    /**
     * Variable used to determine the role of the customer
     */
    private static final String CUSTOMER_ROLE = "customer";
    /**
     * Variable used to determine the role of the admin
     */
    private static final String ADMIN_ROLE = "admin";
    /**
     * Parameter name in the request
     */
    private static final String CUSTOMER_EMAIL = "email";
    /**
     * Parameter name in the request
     */
    private static final String APPLICATION_STATUS = "status";
    /**
     * @see by.tareltos.fcqdelivery.receiver.ApplicationReceiver
     */
    private ApplicationReceiver receiver;

    public FindApplicationsCommand(ApplicationReceiver applicationReceiver) {
        this.receiver = applicationReceiver;
    }

    /**
     * Method returns the path to the jsp page
     *
     * @return return the path to the jsp page
     * @see by.tareltos.fcqdelivery.command.Command
     */
    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        User loginedUser = (User) session.getAttribute(LOGINED_USER);
        if (null == loginedUser) {
            return PagePath.PATH_SINGIN_PAGE.getPath();
        }
        if (ADMIN_ROLE.equals(loginedUser.getRole().getRole()) | CUSTOMER_ROLE.equals(loginedUser.getRole().getRole())) {
            LOGGER.log(Level.WARN, "This page only for Manager! Access denied, you do not have rights: userRole= " + loginedUser.getRole().getRole());
            request.setAttribute("message", "accessDenied.text");
            return PagePath.PATH_INF_PAGE.getPath();
        }
        String customerEmail = request.getParameter(CUSTOMER_EMAIL);
        LOGGER.log(Level.DEBUG, "Customer email" + customerEmail);
        String applicationStatus = request.getParameter(APPLICATION_STATUS);

        List<Application> appList = receiver.getSelectedApplications(customerEmail, applicationStatus);
        if (null == appList) {
            request.setAttribute("message", "dataNotFound.text");
            return PagePath.PATH_INF_PAGE.getPath();
        }
        request.setAttribute("appList", appList);
        return PagePath.PATH_APPLICATIONS_PAGE.getPath();
    }
}
