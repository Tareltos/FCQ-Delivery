package by.tareltos.fcqdelivery.command.application;

import by.tareltos.fcqdelivery.command.Command;
import by.tareltos.fcqdelivery.command.PagePath;
import by.tareltos.fcqdelivery.entity.application.Application;
import by.tareltos.fcqdelivery.entity.user.User;
import by.tareltos.fcqdelivery.receiver.ReceiverException;
import by.tareltos.fcqdelivery.util.DataValidator;
import org.apache.logging.log4j.Level;

import javax.servlet.http.HttpServletRequest;

import static by.tareltos.fcqdelivery.command.ParameterStore.*;

/**
 * Class is used to obtain parameters from request,
 * send them into receiver and to return path to jsp page in controller.
 *
 * @autor Tarelko Vitali
 * @see Command
 */
public class CompleteApplicationCommand implements Command {

    /**
     * Method returns the path to the jsp page
     *
     * @return return the path to the jsp page
     * @see by.tareltos.fcqdelivery.command.Command
     */
    @Override
    public String execute(HttpServletRequest request) {
        User loginedUser = getUser(request);
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
                if (APPLICATION_RECEIVER.completeApplication(applicationId)) {
                    Application application = APPLICATION_RECEIVER.getApplication(applicationId);
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
