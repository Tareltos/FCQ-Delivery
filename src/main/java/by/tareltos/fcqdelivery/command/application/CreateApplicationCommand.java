package by.tareltos.fcqdelivery.command.application;

import by.tareltos.fcqdelivery.command.Command;
import by.tareltos.fcqdelivery.command.PagePath;
import by.tareltos.fcqdelivery.entity.user.User;
import by.tareltos.fcqdelivery.receiver.ReceiverException;
import by.tareltos.fcqdelivery.util.DataValidator;
import org.apache.logging.log4j.Level;

import static by.tareltos.fcqdelivery.command.ParameterStore.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Class is used to obtain parameters from request,
 * send them into receiver and to return path to jsp page in controller.
 *
 * @autor Tarelko Vitali
 * @see Command
 */
public class CreateApplicationCommand implements Command {

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
                result = APPLICATION_RECEIVER.createNewApplication(loginedUser, startPoint, finishPoint, date, comment, weight);
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
