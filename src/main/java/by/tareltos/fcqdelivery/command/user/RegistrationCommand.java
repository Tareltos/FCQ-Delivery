package by.tareltos.fcqdelivery.command.user;

import by.tareltos.fcqdelivery.command.Command;
import by.tareltos.fcqdelivery.command.CommandUtil;
import by.tareltos.fcqdelivery.command.PagePath;
import by.tareltos.fcqdelivery.receiver.ReceiverException;
import by.tareltos.fcqdelivery.receiver.UserReceiver;
import by.tareltos.fcqdelivery.validator.DataValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Properties;

public class RegistrationCommand implements Command {
    final static Logger LOGGER = LogManager.getLogger();
    /**
     * Parameter name in the request
     */
    private static final String LOCALE = "locale";
    private static final String FILE_NAME = "mail";
    private static final String MESSAGE_ATR = "message";
    private static final String EMAIL_PRM = "mail";
    private static final String FIRST_NAME_PRM = "fName";
    private static final String LAST_NAME_PRM = "lName";
    private static final String PHONE_PRM = "phone";
    private static final String CUSTOMER_ROLE = "customer";
    private UserReceiver receiver;

    public RegistrationCommand(UserReceiver userReceiver) {
        receiver = userReceiver;
    }

    @Override
    public String execute(HttpServletRequest request) {
        Properties properties = new Properties();
        if (!CommandUtil.loadProperies(request, properties, FILE_NAME)) {
            return PagePath.PATH_INF_PAGE.getPath();
        }
        String email = request.getParameter(EMAIL_PRM);
        boolean result;
        try {
            result = receiver.checkEmail(email);
        } catch (ReceiverException e) {
            LOGGER.log(Level.WARN, e.getMessage());
            request.setAttribute(MESSAGE_ATR, "checkEmailExc.text");
            return PagePath.PATH_SINGIN_PAGE.getPath();
        }
        if (!result) {
            request.setAttribute(MESSAGE_ATR, "userExist.text");
            return PagePath.PATH_SINGIN_PAGE.getPath();
        }

        String fname = request.getParameter(FIRST_NAME_PRM);
        String lname = request.getParameter(LAST_NAME_PRM);
        String phone = request.getParameter(PHONE_PRM);
        String locale = request.getParameter(LOCALE);
        if (!DataValidator.validateEmail(email) & !DataValidator.validateName(fname)
                & !DataValidator.validateName(lname) & !DataValidator.validatePassword(phone)) {
            request.setAttribute(MESSAGE_ATR, "invalidData.text");
            return PagePath.PATH_SINGIN_PAGE.getPath();
        }
        boolean createUserResult;

        try {
            createUserResult = receiver.createUser(email, fname, lname, phone, CUSTOMER_ROLE, properties, locale);
        } catch (ReceiverException e) {
            LOGGER.log(Level.WARN, e.getMessage());
            request.setAttribute(MESSAGE_ATR, "error.text");
            return PagePath.PATH_SINGIN_PAGE.getPath();
        }
        if (createUserResult) {
            request.setAttribute(MESSAGE_ATR, "successfulRegResult.text");
            return PagePath.PATH_SINGIN_PAGE.getPath();
        } else {
            request.setAttribute(MESSAGE_ATR, "failedRegResult.text");
            return PagePath.PATH_SINGIN_PAGE.getPath();
        }
    }
}
