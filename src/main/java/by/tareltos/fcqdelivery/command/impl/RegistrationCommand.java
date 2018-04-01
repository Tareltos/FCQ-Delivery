package by.tareltos.fcqdelivery.command.impl;

import by.tareltos.fcqdelivery.command.Command;
import by.tareltos.fcqdelivery.entity.User;
import by.tareltos.fcqdelivery.entity.UserRole;
import by.tareltos.fcqdelivery.receiver.UserReceiver;
import by.tareltos.fcqdelivery.util.PasswordGenerator;
import by.tareltos.fcqdelivery.validator.DataValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Properties;

public class RegistrationCommand implements Command {
    final static Logger LOGGER = LogManager.getLogger();
    private static final String EMAIL_PRM = "mail";
    private static final String FIRST_NAME_PRM = "fName";
    private static final String LAST_NAME_PRM = "lName";
    private static final String PHONE_PRM = "phone";
    private static final String PATH_SINGIN_PAGE = "/jsp/singin.jsp";
    private static final String CUSTOMER_ROLE = "customer";
    private UserReceiver receiver;

    public RegistrationCommand(UserReceiver userReceiver) {
        receiver = userReceiver;
    }

    @Override
    public String execute(HttpServletRequest request) throws IOException {
        Properties properties = new Properties();
        ServletContext context = request.getServletContext();
        String filename = context.getInitParameter("mail");
        properties.load(context.getResourceAsStream(filename));
        String email = request.getParameter(EMAIL_PRM);
        String fname = request.getParameter(FIRST_NAME_PRM);
        String lname = request.getParameter(LAST_NAME_PRM);
        String phone = request.getParameter(PHONE_PRM);
        if (DataValidator.validateEmail(email) && !receiver.checkEmail(email)) {
            request.setAttribute("errorLoginMessage", "Пользователя уже существует. Воспользуйтесь восстановлением пароля!");
            return PATH_SINGIN_PAGE;
        }
        if (DataValidator.validateEmail(email) && DataValidator.validateName(fname)
                && DataValidator.validateName(lname) && DataValidator.validatePassword(phone)) {
            boolean result = receiver.createUser(email, fname, lname, phone, CUSTOMER_ROLE, properties);
            if (result) {
                request.setAttribute("successfulMsg", "Ругистрация завершена, пароль выслан на почту.");
                return PATH_SINGIN_PAGE;
            } else {
                request.setAttribute("errorLoginMessage", "Пользователь не сохранен, попробуйте еще раз!");
                return PATH_SINGIN_PAGE;
            }
        } else {
            request.setAttribute("errorLoginMessage", "Введены некорректные данные!");
            return PATH_SINGIN_PAGE;
        }

    }
}
