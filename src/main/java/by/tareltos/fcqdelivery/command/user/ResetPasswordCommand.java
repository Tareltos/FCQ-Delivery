package by.tareltos.fcqdelivery.command.user;

import by.tareltos.fcqdelivery.command.Command;
import by.tareltos.fcqdelivery.command.PagePath;
import by.tareltos.fcqdelivery.receiver.ReceiverException;
import by.tareltos.fcqdelivery.receiver.UserReceiver;
import by.tareltos.fcqdelivery.validator.DataValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Properties;

public class ResetPasswordCommand implements Command {

    final static Logger LOGGER = LogManager.getLogger();
    private static final String MESSAGE_ATR = "message";
    private static final String FILE_NAME = "mail";
    private static final String EMAIL_PRM = "mail";
    private UserReceiver receiver;


    public ResetPasswordCommand(UserReceiver userReceiver) {
        receiver = userReceiver;
    }

    @Override
    public String execute(HttpServletRequest request) {
        Properties properties = new Properties();
        ServletContext context = request.getServletContext();
        String filename = context.getInitParameter(FILE_NAME);
        try {
            properties.load(context.getResourceAsStream(filename));
        } catch (IOException e) {
            LOGGER.log(Level.WARN, e.getMessage());
            request.setAttribute("exception", "Failed to load data to send password to email. Please, try again");
            return PagePath.PATH_INF_PAGE.getPath();
        }

        String email = request.getParameter(EMAIL_PRM);
        if (!DataValidator.validateEmail(email)) {
            request.setAttribute(MESSAGE_ATR, "invalidData.text");
            return PagePath.PATH_SINGIN_PAGE.getPath();
        }
        boolean result;
        try {
            result = receiver.resetUserPassword(email, properties);
        } catch (ReceiverException e) {
            LOGGER.log(Level.WARN, e.getMessage());
            request.setAttribute(MESSAGE_ATR, "passwordUpdateError.text");
            return PagePath.PATH_SINGIN_PAGE.getPath();
        }
        if (result) {
            request.setAttribute(MESSAGE_ATR, "updatePassword.text");
        } else {
            request.setAttribute(MESSAGE_ATR, "userNotFound.text");
        }
        return PagePath.PATH_SINGIN_PAGE.getPath();

    }
}
