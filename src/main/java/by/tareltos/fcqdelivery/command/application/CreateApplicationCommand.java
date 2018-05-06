package by.tareltos.fcqdelivery.command.application;

import by.tareltos.fcqdelivery.command.Command;
import by.tareltos.fcqdelivery.command.PagePath;
import by.tareltos.fcqdelivery.entity.user.User;
import by.tareltos.fcqdelivery.receiver.ApplicationReceiver;
import by.tareltos.fcqdelivery.receiver.ReceiverException;
import by.tareltos.fcqdelivery.repository.RepositoryException;
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
public class CreateApplicationCommand implements Command {
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
    private static final String START_POINT = "start";
    /**
     * Parameter name in the request
     */
    private static final String FINISH_POINT = "finish";
    /**
     * Parameter name in the request
     */
    private static final String COMMENT = "comment";
    /**
     * Parameter name in the request
     */
    private static final String DATE = "date";
    /**
     * Parameter name in the request
     */
    private static final String WEIGHT = "weight";
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

    public CreateApplicationCommand(ApplicationReceiver receiver) {
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
            LOGGER.log(Level.WARN, "This page only for Customer! Access denied, you do not have rights: userRole= " + loginedUser.getRole().getRole());
            request.setAttribute("message", "accessDenied.text");
            return PagePath.PATH_INF_PAGE.getPath();
        }
        String startPoint = request.getParameter(START_POINT);
        String finishPoint = request.getParameter(FINISH_POINT);
        String date = request.getParameter(DATE);
        String comment = request.getParameter(COMMENT);
        String weight = request.getParameter(WEIGHT);
        if (DataValidator.validateStartPoint(startPoint) & DataValidator.validateFinishPoint(finishPoint) &
                DataValidator.validateDate(date) & DataValidator.validateComment(comment) &
                DataValidator.validateCargo(Integer.parseInt(weight))) {
            boolean result;
            try {
                result = receiver.createNewApplication(loginedUser, startPoint, finishPoint, date, comment, weight);
                if (result) {
                    request.setAttribute("method", "redirect");
                    request.setAttribute("redirectUrl", "/applications?action=get_applications");
                    return PagePath.PATH_APPLICATIONS_PAGE.getPath();
                }
            } catch (ReceiverException e) {
                LOGGER.log(Level.ERROR, e.getMessage());
                request.setAttribute("exception", "Application is not created: " + e.getMessage());
                return PagePath.PATH_INF_PAGE.getPath();
            }
            request.setAttribute("message", "error.text");
        }
        request.setAttribute("message", "invalidData.text");
        return PagePath.PATH_INF_PAGE.getPath();
    }
}
