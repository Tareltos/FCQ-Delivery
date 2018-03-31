package by.tareltos.fcqdelivery.command.impl;

import by.tareltos.fcqdelivery.command.Command;
import by.tareltos.fcqdelivery.entity.User;
import by.tareltos.fcqdelivery.receiver.UserReceiver;
import by.tareltos.fcqdelivery.util.EmailSender;
import by.tareltos.fcqdelivery.validator.DataValidator;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Properties;

public class ResetPasswordCommand implements Command {

    private static final String EMAIL_PRM = "mail";
    private static final String PATH_SINGIN_PAGE = "/jsp/singin.jsp";
    private UserReceiver receiver;


    public ResetPasswordCommand(UserReceiver userReceiver) {
        receiver = userReceiver;
    }

    @Override
    public String execute(HttpServletRequest request) throws IOException {
        Properties properties = new Properties();
        ServletContext context = request.getServletContext();
        String filename = context.getInitParameter("mail");
        properties.load(context.getResourceAsStream(filename));
        String email = request.getParameter(EMAIL_PRM);
        if (DataValidator.validateEmail(email)) {
            if (receiver.resetUserPassword(email, properties)) {
                request.setAttribute("successfulMsg", "Пароль обновлен, проверьте почту");
            } else {
                request.setAttribute("errorLoginMessage", "Пользователя не существует");
            }
        } else {
            request.setAttribute("errorLoginMessage", "Неправильный Email");
        }
        return PATH_SINGIN_PAGE;
    }
}
