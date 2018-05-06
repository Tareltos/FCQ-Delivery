package by.tareltos.fcqdelivery.command.application;

import by.tareltos.fcqdelivery.command.Command;
import by.tareltos.fcqdelivery.command.PagePath;
import by.tareltos.fcqdelivery.entity.application.Application;
import by.tareltos.fcqdelivery.entity.courier.Courier;
import by.tareltos.fcqdelivery.entity.user.User;
import by.tareltos.fcqdelivery.receiver.ApplicationReceiver;
import by.tareltos.fcqdelivery.receiver.ReceiverException;
import by.tareltos.fcqdelivery.validator.DataValidator;
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
public class SelectCourierCommand implements Command {
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
     * Variable used to determine the role of the customer
     */
    private static final String CUSTOMER_ROLE = "customer";
    /**
     * Variable used to determine the role of the admin
     */
    private static final String ADMIN_ROLE = "admin";
    /**
     * @see by.tareltos.fcqdelivery.receiver.ApplicationReceiver
     */
    private ApplicationReceiver receiver;


    public SelectCourierCommand(ApplicationReceiver receiver) {
        this.receiver = receiver;
    }

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
        String appId = request.getParameter(APPLICATION_ID);
        if (DataValidator.validateApplicationId(appId)) {
            try {
                Application application = receiver.getApplication(appId);
                List<Courier> couriers = receiver.getCourierForAppointment();    // проверки!!!!
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
