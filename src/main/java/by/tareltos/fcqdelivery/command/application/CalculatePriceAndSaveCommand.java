package by.tareltos.fcqdelivery.command.application;

import by.tareltos.fcqdelivery.command.Command;
import by.tareltos.fcqdelivery.command.CommandUtil;
import by.tareltos.fcqdelivery.command.PagePath;
import by.tareltos.fcqdelivery.entity.user.User;
import by.tareltos.fcqdelivery.receiver.ApplicationReceiver;
import by.tareltos.fcqdelivery.receiver.ReceiverException;
import by.tareltos.fcqdelivery.validator.DataValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Properties;

/**
 * Class is used to obtain parameters from request,
 * send them into receiver and to return path to jsp page in controller.
 *
 * @autor Tarelko Vitali
 * @see Command
 */
public class CalculatePriceAndSaveCommand implements Command {
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
     * Parameter name in the request
     */
    private static final String COURIER_ID = "courier";
    /**
     * Properties file name for emailSender
     */
    private static final String FILE_NAME = "mail";
    /**
     * Parameter name in the request
     */
    private static final String DISTANCE = "distance";
    /**
     * Parameter name in the request
     */
    private static final String LOCALE = "locale";
    /**
     * Variable used to determine the role of the manager
     */
    private static final String MANAGER_ROLE = "manager";
    /**
     * @see by.tareltos.fcqdelivery.receiver.ApplicationReceiver
     */
    private ApplicationReceiver receiver;

    /**
     * @see by.tareltos.fcqdelivery.receiver.ApplicationReceiver
     */
    public CalculatePriceAndSaveCommand(ApplicationReceiver receiver) {
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
        Properties properties = new Properties();
        if (!CommandUtil.loadProperies(request, properties, FILE_NAME)) {
            return PagePath.PATH_INF_PAGE.getPath();
        }
        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute(LOGINED_USER);
        if (null == user) {
            return PagePath.PATH_SINGIN_PAGE.getPath();
        }
        if (MANAGER_ROLE.equals(user.getRole().getRole())) {

            String appId = request.getParameter(APPLICATION_ID);
            String courierId = request.getParameter(COURIER_ID);
            String distance = request.getParameter(DISTANCE);
            String locale = request.getParameter(LOCALE);
            if (DataValidator.validateApplicationId(appId) & DataValidator.validateCarNumber(courierId) & DataValidator.validateDistance(distance)) {
                boolean result;
                try {
                    result = receiver.updateApplication(appId, courierId, distance, properties, locale);
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
