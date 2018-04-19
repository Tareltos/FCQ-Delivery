package by.tareltos.fcqdelivery.command.application;

import by.tareltos.fcqdelivery.command.Command;
import by.tareltos.fcqdelivery.command.CommandException;
import by.tareltos.fcqdelivery.command.PagePath;
import by.tareltos.fcqdelivery.entity.user.User;
import by.tareltos.fcqdelivery.receiver.ApplicationReceiver;
import by.tareltos.fcqdelivery.receiver.ReceiverException;
import by.tareltos.fcqdelivery.repository.RepositoryException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.SQLException;

public class DoPaymentApplicationCommand implements Command {

    final static Logger LOGGER = LogManager.getLogger();
    private static final String LOGINED_USER_PRM = "loginedUser";
    private static final String APPLICATION_ID_PRM = "id";
    private static final String CARD_NUMBER_PRM = "cardNumber";
    private static final String EXPIRATION_MOUNTH_PRM = "expirationMonth";
    private static final String EXPIRATION_YEAR_PRM = "expirationYear";
    private static final String OWNER_PRM = "owner";
    private static final String CSV_PRM = "csv";
    private ApplicationReceiver receiver;

    public DoPaymentApplicationCommand(ApplicationReceiver receiver) {
        this.receiver = receiver;
    }

    public String execute(HttpServletRequest request) throws ReceiverException, CommandException, SQLException {
        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute(LOGINED_USER_PRM);
        if (null == user) {
            return PagePath.PATH_SINGIN_PAGE.getPath();
        }
        if ("customer".equals(user.getRole().getRole())) {
            String appId = request.getParameter(APPLICATION_ID_PRM);
            String cardNumber = request.getParameter(CARD_NUMBER_PRM);
            String expMonth = request.getParameter(EXPIRATION_MOUNTH_PRM);
            String expYear = request.getParameter(EXPIRATION_YEAR_PRM);
            String owner = request.getParameter(OWNER_PRM);
            String csv = request.getParameter(CSV_PRM);

            // нужна валидация полей!!!!
            boolean result;
            result = receiver.payForApplication(appId, cardNumber, expMonth, expYear, owner, csv);
            if (result) {
                request.setAttribute("method", "redirect");
                request.setAttribute("redirectUrl", "/applications?action=get_applications");
                return PagePath.PATH_APPLICATIONS_PAGE.getPath();
            }
            request.setAttribute("errorMessage", "Оплата не выполнена");
            return PagePath.PATH_INF_PAGE.getPath();
        } else {
            request.setAttribute("errorMessage", "У вас нет доступа к этой странице");
            return PagePath.PATH_INF_PAGE.getPath();
        }


    }
}
