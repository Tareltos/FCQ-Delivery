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

public class CreateApplicationCommand implements Command {
    final static Logger LOGGER = LogManager.getLogger();
    private static final String LOGINED_USER_PRM = "loginedUser";
    private static final String START_POINT_PRM = "start";
    private static final String FINISH_POINT_PRM = "finish";
    private static final String COMMENT_PRM = "comment";
    private static final String DATE_PRM = "date";
    private static final String WEIGHT_PRM = "weight";
    private static final String MANAGER_ROLE = "manager";
    private static final String ADMIN_ROLE = "admin";
    private ApplicationReceiver receiver;

    public CreateApplicationCommand(ApplicationReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        User loginedUser = (User) session.getAttribute(LOGINED_USER_PRM);
        if (null == loginedUser) {
            return PagePath.PATH_SINGIN_PAGE.getPath();
        }
        if (ADMIN_ROLE.equals(loginedUser.getRole().getRole()) | MANAGER_ROLE.equals(loginedUser.getRole().getRole())) {
            LOGGER.log(Level.INFO, "This page only for Customer! Access denied, you do not have rights: userRole= " + loginedUser.getRole().getRole());
            request.setAttribute("message", "accessDenied.text");
            return PagePath.PATH_INF_PAGE.getPath();
        }
        String startPoint = request.getParameter(START_POINT_PRM);
        String finishPoint = request.getParameter(FINISH_POINT_PRM);
        String date = request.getParameter(DATE_PRM);
        String comment = request.getParameter(COMMENT_PRM);
        String weight = request.getParameter(WEIGHT_PRM);
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
                LOGGER.log(Level.WARN, e);
            }
            request.setAttribute("message", "error.text");
            return PagePath.PATH_INF_PAGE.getPath();
        }
        request.setAttribute("message", "invalidData.text");
        return PagePath.PATH_INF_PAGE.getPath();
    }
}
