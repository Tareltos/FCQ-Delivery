package by.tareltos.fcqdelivery.command.user;

import by.tareltos.fcqdelivery.command.Command;
import by.tareltos.fcqdelivery.command.CommandUtil;
import by.tareltos.fcqdelivery.command.PagePath;
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
public class RegistrationCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        Properties properties = new Properties();
        if (!CommandUtil.loadProperies(request, properties, FILE_NAME)) {
            return PagePath.PATH_INF_PAGE.getPath();
        }
        String email = request.getParameter(EMAIL);
        boolean result;
        try {
            result = USER_RECEIVER.checkEmail(email);
        } catch (ReceiverException e) {
            LOGGER.log(Level.WARN, e.getMessage());
            request.setAttribute(MESSAGE, "checkEmailExc.text");
            return PagePath.PATH_SINGIN_PAGE.getPath();
        }
        if (!result) {
            request.setAttribute(MESSAGE, "userExist.text");
            return PagePath.PATH_SINGIN_PAGE.getPath();
        }

        String fname = request.getParameter(FIRST_NAME);
        String lname = request.getParameter(LAST_NAME);
        String phone = request.getParameter(PHONE);
        String locale = request.getParameter(LOCALE);
        if (!DataValidator.validateEmail(email) & !DataValidator.validateName(fname)
                & !DataValidator.validateName(lname) & !DataValidator.validatePassword(phone)) {
            request.setAttribute(MESSAGE, "invalidData.text");
            return PagePath.PATH_SINGIN_PAGE.getPath();
        }
        boolean createUserResult;

        try {
            createUserResult = USER_RECEIVER.createUser(email, fname, lname, phone, CUSTOMER_ROLE, properties, locale);
        } catch (ReceiverException e) {
            LOGGER.log(Level.WARN, e.getMessage());
            request.setAttribute(MESSAGE, "error.text");
            return PagePath.PATH_SINGIN_PAGE.getPath();
        }
        if (createUserResult) {
            request.setAttribute(MESSAGE, "successfulRegResult.text");
            return PagePath.PATH_SINGIN_PAGE.getPath();
        } else {
            request.setAttribute(MESSAGE, "failedRegResult.text");
            return PagePath.PATH_SINGIN_PAGE.getPath();
        }
    }
}
