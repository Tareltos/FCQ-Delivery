package by.tareltos.fcqdelivery.command.user;

import by.tareltos.fcqdelivery.command.Command;
import by.tareltos.fcqdelivery.command.CommandException;
import by.tareltos.fcqdelivery.command.PagePath;
import by.tareltos.fcqdelivery.entity.user.User;
import by.tareltos.fcqdelivery.receiver.ReceiverException;
import by.tareltos.fcqdelivery.receiver.UserReceiver;
import by.tareltos.fcqdelivery.validator.DataValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Properties;

public class CreateUserCommand implements Command {

    final static Logger LOGGER = LogManager.getLogger();
    private static final String EMAIL_PRM = "mail";
    private static final String FIRST_NAME_PRM = "fName";
    private static final String LAST_NAME_PRM = "lName";
    private static final String PHONE_PRM = "phone";
    private static final String ROLE_PRM = "role";
    private UserReceiver receiver;

    public CreateUserCommand(UserReceiver userReceiver) {
        receiver = userReceiver;
    }

    @Override
    public String execute(HttpServletRequest request) throws ReceiverException, CommandException {
        Properties properties = new Properties();
        ServletContext context = request.getServletContext();
        String filename = context.getInitParameter("mail");
        try {
            properties.load(context.getResourceAsStream(filename));
        } catch (IOException e) {
            throw new CommandException("Exception in reading mail property", e);
        }
        HttpSession session = request.getSession(true);
        User admin = (User) session.getAttribute("loginedUser");
        if (null != admin) {
            String email = request.getParameter(EMAIL_PRM);
            String fName = request.getParameter(FIRST_NAME_PRM);
            String lName = request.getParameter(LAST_NAME_PRM);
            String phone = request.getParameter(PHONE_PRM);
            if (receiver.checkEmail(email) && DataValidator.validateEmail(email) && DataValidator.validateName(fName) && DataValidator.validateName(lName) && DataValidator.validatePhone(phone)) {
                String role = request.getParameter(ROLE_PRM);
                receiver.createUser(email, fName, lName, phone, role, properties);
                request.setAttribute("successfulMsg", "Пользователь успешно создань, пароль отправлен на почту!");
                request.setAttribute("method", "redirect");
                request.setAttribute("redirectUrl", "/users?action=get_users");
                return PagePath.PATH_INF_PAGE.getPath();
            } else {
                request.setAttribute("errorMessage", "Пользователь с таким Email существует");
                request.setAttribute("method", "redirect");
                request.setAttribute("redirectUrl", "/users?action=get_users");
                return PagePath.PATH_INF_PAGE.getPath();
            }

        } else {
            return PagePath.PATH_SINGIN_PAGE.getPath();
        }

    }
}
