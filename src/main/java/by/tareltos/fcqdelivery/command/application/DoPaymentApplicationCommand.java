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

public class DoPaymentApplicationCommand implements Command {

    final static Logger LOGGER = LogManager.getLogger();
    private static final String LOGINED_USER_PRM = "loginedUser";
    private static final String APPLICATION_ID_PRM = "id";
    private static final String CARD_NUMBER_PRM = "cardNumber";
    private static final String EXPIRATION_MOUNTH_PRM = "expirationMonth";
    private static final String EXPIRATION_YEAR_PRM = "expirationYear";
    private static final String OWNER_PRM = "owner";
    private static final String CSV_PRM = "csv";
    private static final String MANAGER_ROLE = "manager";
    private static final String ADMIN_ROLE = "admin";
    private ApplicationReceiver receiver;

    public DoPaymentApplicationCommand(ApplicationReceiver receiver) {
        this.receiver = receiver;
    }

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
        String appId = request.getParameter(APPLICATION_ID_PRM);
        String cardNumber = request.getParameter(CARD_NUMBER_PRM);
        String expMonth = request.getParameter(EXPIRATION_MOUNTH_PRM);
        String expYear = request.getParameter(EXPIRATION_YEAR_PRM);
        String owner = request.getParameter(OWNER_PRM);
        String csv = request.getParameter(CSV_PRM);
        if (DataValidator.validateApplicationId(appId) & DataValidator.validateCardNumber(cardNumber) &
        DataValidator.validateExpirationMonth(expMonth) & DataValidator.validateExpirationYear(expYear) &
        DataValidator.validateOwner(owner) & DataValidator.validateCsv(csv)) {
            try {
                boolean result;
                result = receiver.payForApplication(appId, cardNumber, expMonth, expYear, owner, csv);
                if (result) {
                    request.setAttribute("method", "redirect");
                    request.setAttribute("redirectUrl", "/applications?action=get_applications");
                    return PagePath.PATH_APPLICATIONS_PAGE.getPath();
                } else {
                    request.setAttribute("message", "paymentError.text");
                    return PagePath.PATH_INF_PAGE.getPath();
                }
            } catch (ReceiverException e) {
                LOGGER.log(Level.WARN, e.getMessage());
                request.setAttribute("message", "error.text");
            }
        }
        request.setAttribute("message", "invalidData.text");
        return PagePath.PATH_INF_PAGE.getPath();

    }
}
