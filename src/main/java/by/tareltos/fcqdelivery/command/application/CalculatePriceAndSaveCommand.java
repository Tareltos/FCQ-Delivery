package by.tareltos.fcqdelivery.command.application;

import by.tareltos.fcqdelivery.command.Command;
import by.tareltos.fcqdelivery.command.CommandUtil;
import by.tareltos.fcqdelivery.command.PagePath;
import by.tareltos.fcqdelivery.entity.user.User;
import by.tareltos.fcqdelivery.receiver.ReceiverException;
import by.tareltos.fcqdelivery.util.DataValidator;
import org.apache.logging.log4j.Level;
import javax.servlet.http.HttpServletRequest;
import java.util.Properties;

import static by.tareltos.fcqdelivery.command.ParameterStore.*;

/**
 * Class is used to obtain parameters from request,
 * send them into receiver and to return path to jsp page in controller.
 *
 * @autor Tarelko Vitali
 * @see Command
 */
public class CalculatePriceAndSaveCommand implements Command {

    /**
     * Method returns the path to the jsp page
     *
     * @return return the path to the jsp page
     * @see by.tareltos.fcqdelivery.command.Command
     */
    @Override
    public String execute(HttpServletRequest request) {
        Properties properties = new Properties();
        if (!CommandUtil.loadProperies(request, properties, FILE_NAME)) {
            return PagePath.PATH_INF_PAGE.getPath();
        }
        User loginedUser = getUser(request);
        if (null == loginedUser) {
            return PagePath.PATH_SINGIN_PAGE.getPath();
        }
        if (MANAGER_ROLE.equals(loginedUser.getRole().getRole())) {

            String appId = request.getParameter(APPLICATION_ID);
            String courierId = request.getParameter(COURIER_ID);
            String distance = request.getParameter(DISTANCE);
            String locale = request.getParameter(LOCALE);
            if (DataValidator.validateApplicationId(appId) & DataValidator.validateCarNumber(courierId) & DataValidator.validateDistance(distance)) {
                boolean result;
                try {
                    result = APPLICATION_RECEIVER.updateApplication(appId, courierId, distance, properties, locale);
                    if (result) {
                        request.setAttribute("method", "redirect");
                        request.setAttribute("redirectUrl", "/applications?action=get_applications");
                        return PagePath.PATH_APPLICATIONS_PAGE.getPath();
                    }

                } catch (ReceiverException e) {
                    LOGGER.log(Level.ERROR, e.getMessage());
                    request.setAttribute("exception", "Application is not saved: " + e.getMessage());
                }
            } else {
                request.setAttribute("message", "invalidData.text");
                return PagePath.PATH_INF_PAGE.getPath();
            }
        } else {
            request.setAttribute("message", "accessDenied.text");
            return PagePath.PATH_INF_PAGE.getPath();
        }
        return PagePath.PATH_INF_PAGE.getPath();
    }
}
