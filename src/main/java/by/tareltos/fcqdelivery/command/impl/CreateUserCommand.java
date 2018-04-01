package by.tareltos.fcqdelivery.command.impl;

import by.tareltos.fcqdelivery.command.Command;
import by.tareltos.fcqdelivery.entity.User;
import by.tareltos.fcqdelivery.receiver.UserReceiver;
import by.tareltos.fcqdelivery.validator.DataValidator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;

public class CreateUserCommand implements Command {

    final static Logger LOGGER = LogManager.getLogger();
    private static final String EMAIL_PRM = "mail";
    private static final String FIRST_NAME_PRM = "fName";
    private static final String LAST_NAME_PRM = "lName";
    private static final String PHONE_PRM = "phone";
    private static final String ROLE_PRM = "role";
    private static final String PATH_INF_PAGE = "/jsp/inf.jsp";
    private static final String PATH_SINGIN_PAGE = "/jsp/singin.jsp";
    private UserReceiver receiver;

    public CreateUserCommand(UserReceiver userReceiver) {
        receiver = userReceiver;
    }

    @Override
    public String execute(HttpServletRequest request) throws IOException, SQLException, ClassNotFoundException {
        Properties properties = new Properties();
        ServletContext context = request.getServletContext();
        String filename = context.getInitParameter("mail");
        properties.load(context.getResourceAsStream(filename));
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
                return PATH_INF_PAGE;
            } else {
                request.setAttribute("errorMessage", "Пользователь с таким Email существует");
                return PATH_INF_PAGE;
            }

        } else {
            return PATH_SINGIN_PAGE;
        }

    }
}
