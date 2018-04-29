package by.tareltos.fcqdelivery.command.application;

import by.tareltos.fcqdelivery.command.Command;
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

public class CalculatePriceAndSaveCommand implements Command {

    final static Logger LOGGER = LogManager.getLogger();
    private static final String LOGINED_USER_PRM = "loginedUser";
    private static final String APPLICATION_ID_PRM = "id";
    private static final String COURIER_ID_PRM = "courier";
    private static final String DISTANCE_PRM = "distance";
    private static final String MANAGER_ROLE = "manager";
    private ApplicationReceiver receiver;

    public CalculatePriceAndSaveCommand(ApplicationReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute(LOGINED_USER_PRM);
        if (null == user) {
            return PagePath.PATH_SINGIN_PAGE.getPath();
        }
        if (MANAGER_ROLE.equals(user.getRole().getRole())) {

            String appId = request.getParameter(APPLICATION_ID_PRM);
            String courierId = request.getParameter(COURIER_ID_PRM);
            String distance = request.getParameter(DISTANCE_PRM);// валидация!!!
            if (DataValidator.validateApplicationId(appId) & DataValidator.validateCarNumber(courierId) & DataValidator.validateDistance(distance)) {
                boolean result;
                try {
                    result = receiver.updateApplication(appId, courierId, distance);
                    if (result) {
                        request.setAttribute("method", "redirect");
                        request.setAttribute("redirectUrl", "/applications?action=get_applications");
                        return PagePath.PATH_APPLICATIONS_PAGE.getPath();
                    }

                } catch (ReceiverException e) {
                    LOGGER.log(Level.WARN, e.getMessage());
                    request.setAttribute("message", "Application is not cancelled: " + e.getMessage());
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
