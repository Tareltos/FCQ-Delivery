package by.tareltos.fcqdelivery.command.application;

import by.tareltos.fcqdelivery.command.Command;
import by.tareltos.fcqdelivery.command.CommandException;
import by.tareltos.fcqdelivery.command.PagePath;
import by.tareltos.fcqdelivery.entity.user.User;
import by.tareltos.fcqdelivery.receiver.ApplicationReceiver;
import by.tareltos.fcqdelivery.receiver.ReceiverException;
import by.tareltos.fcqdelivery.repository.RepositoryException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

public class CreateApplicationCommand implements Command {
    final static Logger LOGGER = LogManager.getLogger();
    private static final String LOGINED_USER_PRM = "loginedUser";
    private static final String START_POINT_PRM = "start";
    private static final String FINISH_POINT_PRM = "finish";
    private static final String COMMENT_PRM = "comment";
    private static final String DATE_PRM = "date";
    private static final String WEIGHT_PRM = "weight";
    private ApplicationReceiver receiver;

    public CreateApplicationCommand(ApplicationReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public String execute(HttpServletRequest request) throws CommandException {
        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute(LOGINED_USER_PRM);
        if (null == user) {
            return PagePath.PATH_SINGIN_PAGE.getPath();
        }
        if ("customer".equals(user.getRole().getRole())) {

            String startPoint = request.getParameter(START_POINT_PRM);
            String finishPoint = request.getParameter(FINISH_POINT_PRM);
            String date = request.getParameter(DATE_PRM);
            String comment = request.getParameter(COMMENT_PRM);
            String weight = request.getParameter(WEIGHT_PRM);
            // нужна валидация полей!!!!
            boolean result;
            try {
                result = receiver.createNewApplication(user, startPoint, finishPoint, date, comment, weight);
            } catch (RepositoryException e) {
                throw new CommandException("Exception", e);
            }
            if (result) {
                request.setAttribute("method", "redirect");
                request.setAttribute("redirectUrl", "/applications?action=get_applications");
                return PagePath.PATH_APPLICATIONS_PAGE.getPath();
            }
            request.setAttribute("errorMessage", "Заявка не сформирована");
            return PagePath.PATH_INF_PAGE.getPath();
        } else {
            request.setAttribute("errorMessage", "У вас нет доступа к этой странице");
            return PagePath.PATH_INF_PAGE.getPath();
        }
    }
}
