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
public class DoPaymentApplicationCommand implements Command {
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
    private static final String CARD_NUMBER = "cardNumber";
    /**
     * Parameter name in the request
     */
    private static final String EXPIRATION_MOUNTH = "expirationMonth";
    /**
     * Parameter name in the request
     */
    private static final String EXPIRATION_YEAR = "expirationYear";
    /**
     * Parameter name in the request
     */
    private static final String OWNER = "owner";
    /**
     * Properties file name for emailSender
     */
    private static final String FILE_NAME = "mail";
    /**
     * Parameter name in the request
     */
    private static final String CSV = "csv";
    /**
     * Parameter name in the request
     */
    private static final String LOCALE = "locale";
    /**
     * Variable used to determine the role of the manager
     */
    private static final String MANAGER_ROLE = "manager";
    /**
     * Variable used to determine the role of the admin
     */
    private static final String ADMIN_ROLE = "admin";
    /**
     * @see by.tareltos.fcqdelivery.receiver.ApplicationReceiver
     */
    private ApplicationReceiver receiver;

    public DoPaymentApplicationCommand(ApplicationReceiver receiver) {
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
        User loginedUser = (User) session.getAttribute(LOGINED_USER);
        if (null == loginedUser) {
            return PagePath.PATH_SINGIN_PAGE.getPath();
        }
        if (ADMIN_ROLE.equals(loginedUser.getRole().getRole()) | MANAGER_ROLE.equals(loginedUser.getRole().getRole())) {
            LOGGER.log(Level.INFO, "This page only for Customer! Access denied, you do not have rights: userRole= " + loginedUser.getRole().getRole());
            request.setAttribute("message", "accessDenied.text");
            return PagePath.PATH_INF_PAGE.getPath();
        }
        String appId = request.getParameter(APPLICATION_ID);
        String cardNumber = request.getParameter(CARD_NUMBER);
        String expMonth = request.getParameter(EXPIRATION_MOUNTH);
        String expYear = request.getParameter(EXPIRATION_YEAR);
        String owner = request.getParameter(OWNER);
        String csv = request.getParameter(CSV);
        String locale = request.getParameter(LOCALE);
        if (DataValidator.validateApplicationId(appId) & DataValidator.validateCardNumber(cardNumber) &
                DataValidator.validateExpirationMonth(expMonth) & DataValidator.validateExpirationYear(expYear) &
                DataValidator.validateOwner(owner) & DataValidator.validateCsv(csv)) {
            try {
                boolean result;
                result = receiver.payForApplication(appId, cardNumber, expMonth, expYear, owner, csv, properties, locale);
                if (result) {
                    request.setAttribute("method", "redirect");
                    request.setAttribute("redirectUrl", "/applications?action=get_applications");
                    return PagePath.PATH_APPLICATIONS_PAGE.getPath();
                } else {
                    request.setAttribute("message", "paymentError.text");
                    return PagePath.PATH_INF_PAGE.getPath();
                }
            } catch (ReceiverException e) {
                LOGGER.log(Level.ERROR, e.getMessage());
                request.setAttribute("exception", "Payment is not accepted: " + e.getMessage());
            }
        }
        request.setAttribute("message", "invalidData.text");
        return PagePath.PATH_INF_PAGE.getPath();

    }
}
