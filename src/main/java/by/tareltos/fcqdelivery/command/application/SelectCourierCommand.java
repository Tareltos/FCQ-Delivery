package by.tareltos.fcqdelivery.command.application;

import by.tareltos.fcqdelivery.command.Command;
import by.tareltos.fcqdelivery.command.PagePath;
import by.tareltos.fcqdelivery.entity.application.Application;
import by.tareltos.fcqdelivery.entity.courier.Courier;
import by.tareltos.fcqdelivery.entity.user.User;
import by.tareltos.fcqdelivery.receiver.ReceiverException;
import by.tareltos.fcqdelivery.util.DataValidator;
import org.apache.logging.log4j.Level;

import static by.tareltos.fcqdelivery.command.ParameterStore.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Class is used to obtain parameters from request,
 * send them into receiver and to return path to jsp page in controller.
 *
 * @autor Tarelko Vitali
 * @see Command
 */
public class SelectCourierCommand implements Command {
    @Override
    public String execute(HttpServletRequest request) {
        User loginedUser = getUser(request);
        if (null == loginedUser) {
            return PagePath.PATH_SINGIN_PAGE.getPath();
        }
        if (ADMIN_ROLE.equals(loginedUser.getRole().getRole()) | CUSTOMER_ROLE.equals(loginedUser.getRole().getRole())) {
            LOGGER.log(Level.WARN, "This page only for Manager! Access denied, you do not have rights: userRole= " + loginedUser.getRole().getRole());
            request.setAttribute("message", "accessDenied.text");
            return PagePath.PATH_INF_PAGE.getPath();
        }
        String appId = request.getParameter(APPLICATION_ID);
        if (DataValidator.validateApplicationId(appId)) {
            try {
                Application application = APPLICATION_RECEIVER.getApplication(appId);
                List<Courier> couriers = APPLICATION_RECEIVER.getCourierForAppointment();    // проверки!!!!
                request.setAttribute("application", application);
                request.setAttribute("couriers", couriers);
                return PagePath.PATH_SELECT_COURIER_PAGE.getPath();
            } catch (ReceiverException e) {
                LOGGER.log(Level.ERROR, e.getMessage());
                request.setAttribute("exception", "Can not obtain data from database: " + e.getMessage());
                return PagePath.PATH_INF_PAGE.getPath();
            }
        }
        request.setAttribute("message", "error.text");
        return PagePath.PATH_INF_PAGE.getPath();
    }
}
