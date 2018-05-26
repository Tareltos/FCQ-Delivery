package by.tareltos.fcqdelivery.command.application;

import by.tareltos.fcqdelivery.command.Command;
import by.tareltos.fcqdelivery.command.CommandUtil;
import by.tareltos.fcqdelivery.command.PagePath;
import by.tareltos.fcqdelivery.entity.user.User;
import by.tareltos.fcqdelivery.receiver.ReceiverException;
import by.tareltos.fcqdelivery.util.DataValidator;
import org.apache.logging.log4j.Level;

import static by.tareltos.fcqdelivery.command.ParameterStore.*;

import javax.servlet.http.HttpServletRequest;
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
        User loginedUser = getUser(request);
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
                result = APPLICATION_RECEIVER.payForApplication(appId, cardNumber, expMonth, expYear, owner, csv, properties, locale);
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
            }
        }
        request.setAttribute("message", "invalidData.text");
        return PagePath.PATH_INF_PAGE.getPath();

    }
}
