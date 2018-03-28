package by.tareltos.fcqdelivery.command.impl;

import by.tareltos.fcqdelivery.command.Command;
import by.tareltos.fcqdelivery.receiver.UserReceiver;
import by.tareltos.fcqdelivery.validator.DataValidator;

import javax.servlet.http.HttpServletRequest;

public class ResetPasswordCommand implements Command {

    private static final String EMAIL_PRM = "mail";
    private static final String PATH_SINGIN_PAGE = "/jsp/singin.jsp";
    private UserReceiver receiver;


    public ResetPasswordCommand(UserReceiver userReceiver) {
        receiver = userReceiver;
    }

    @Override
    public String execute(HttpServletRequest request) {
        String email = request.getParameter(EMAIL_PRM);
        if (DataValidator.validateEmail(email)) {
            if (receiver.resetUserPassword(email)) {
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
