package by.tareltos.fcqdelivery.command.application;

import by.tareltos.fcqdelivery.command.Command;
import by.tareltos.fcqdelivery.command.PagePath;
import by.tareltos.fcqdelivery.entity.application.Application;
import by.tareltos.fcqdelivery.entity.user.User;
import by.tareltos.fcqdelivery.receiver.ApplicationReceiver;
import by.tareltos.fcqdelivery.receiver.ReceiverException;
import by.tareltos.fcqdelivery.validator.DataValidator;
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
public class CompleteApplicationCommand implements Command {
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
     * Parameter name in the request
     */
    private static final String APPLICATION_ID = "id";
    /**
     * Variable used to determine the role of the manager
     */
    private static final String MANAGER_ROLE = "manager";
    /**
     * Variable used to determine the role of the admin
     */
    private static final String ADMIN_ROLE = "admin";
    /**
     * @see by.tareltos.fcqdelivery.receiver.ApplicationReceiver
     */
    private ApplicationReceiver receiver;


    public CompleteApplicationCommand(ApplicationReceiver receiver) {
        this.receiver = receiver;
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
        if (ADMIN_ROLE.equals(loginedUser.getRole().getRole()) | MANAGER_ROLE.equals(loginedUser.getRole().getRole())) {
            LOGGER.log(Level.INFO, "This page only for Customer! Access denied, you do not have rights: userRole= " + loginedUser.getRole().getRole());
            request.setAttribute("message", "accessDenied.text");
            return PagePath.PATH_INF_PAGE.getPath();
        }
        String applicationId = request.getParameter(APPLICATION_ID);
        if (DataValidator.validateApplicationId(applicationId)) {
            try {
                if (receiver.completeApplication(applicationId)) {
                    Application application = receiver.getApplication(applicationId);
                    request.setAttribute("application", application);
                    return PagePath.PATH_APPLICATION_INFO_PAGE.getPath();
                } else {
                    request.setAttribute("message", "error.text");
                    return PagePath.PATH_INF_PAGE.getPath();
                }
            } catch (ReceiverException e) {
                LOGGER.log(Level.ERROR, e.getMessage());
                request.setAttribute("exception", "Application is not complete: " + e.getMessage());
            }
        } else {
            request.setAttribute("message", "invalidData.text");
            return PagePath.PATH_INF_PAGE.getPath();
        }
        return PagePath.PATH_INF_PAGE.getPath();
    }
}
