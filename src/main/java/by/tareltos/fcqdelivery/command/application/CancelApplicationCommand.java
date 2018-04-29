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

public class CancelApplicationCommand implements Command {

    final static Logger LOGGER = LogManager.getLogger();
    private static final String LOGINED_USER_PRM = "loginedUser";
    private static final String APPLICATION_ID_PRM = "id";
    private static final String REASON_PRM = "reason";
    private static final String MANAGER_ROLE = "manager";
    private static final String ADMIN_ROLE = "admin";
    private ApplicationReceiver receiver;


    public CancelApplicationCommand(ApplicationReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        User loginedUser = (User) session.getAttribute(LOGINED_USER_PRM);
        if (null == loginedUser) {
            LOGGER.log(Level.DEBUG, "User is null");
            return PagePath.PATH_SINGIN_PAGE.getPath();
        }
        if (ADMIN_ROLE.equals(loginedUser.getRole().getRole()) | MANAGER_ROLE.equals(loginedUser.getRole().getRole())) {
            LOGGER.log(Level.DEBUG, "This page only for Customer! Access denied, you do not have rights: userRole= " + loginedUser.getRole().getRole());
            request.setAttribute("message", "accessDenied.text");
            return PagePath.PATH_INF_PAGE.getPath();
        }
        String applicationId = request.getParameter(APPLICATION_ID_PRM);
        String reason = request.getParameter(REASON_PRM);
        if (DataValidator.validateApplicationId(applicationId) & DataValidator.validateReasonOfCancel(reason)) {
            try {
                if (receiver.cancelApplication(applicationId, reason)) {
                    Application application = receiver.getApplication(applicationId);
                    request.setAttribute("application", application);
                    return PagePath.PATH_APPLICATION_INFO_PAGE.getPath();
                }
            } catch (ReceiverException e) {
                LOGGER.log(Level.WARN, e.getMessage());
            }
        }
        request.setAttribute("message", "invalidData.text");
        return PagePath.PATH_INF_PAGE.getPath();
    }
}
