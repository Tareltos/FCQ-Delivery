package by.tareltos.fcqdelivery.command.impl;

import by.tareltos.fcqdelivery.command.Command;
import by.tareltos.fcqdelivery.entity.User;
import by.tareltos.fcqdelivery.receiver.UserReceiver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

public class SaveUserCommand implements Command {


    final static Logger LOGGER = LogManager.getLogger();
    private static final String FIRST_NAME_PRM = "fName";
    private static final String LAST_NAME_PRM = "lName";
    private static final String PHONE_PRM = "phone";
    private static final String PATH_USER_INFO_PAGE = "/jsp/userInfo.jsp";
    private static final String PATH_SINGIN_PAGE = "/jsp/singin.jsp";

    private UserReceiver receiver;

    public SaveUserCommand(UserReceiver userReceiver) {
        receiver = userReceiver;
    }

    @Override
    public String execute(HttpServletRequest request) throws IOException, SQLException, ClassNotFoundException {
        String page;
        String fname = request.getParameter(FIRST_NAME_PRM);
        String lname = request.getParameter(LAST_NAME_PRM);
        String phone = request.getParameter(PHONE_PRM);
        HttpSession session = request.getSession(true);
        User loginedUser = (User) session.getAttribute("loginedUser");
        if (loginedUser != null) {
            loginedUser.setFirstName(fname);
            loginedUser.setLastName(lname);
            loginedUser.setPhone(phone);
            if (receiver.updateUser(loginedUser)) {
                loginedUser = receiver.getUserForSession(loginedUser.getEmail());
                session.setAttribute("loginedUser", loginedUser);
                page = PATH_USER_INFO_PAGE;
            } else {
                request.setAttribute("errorLoginMessage", "ERROR");
                page = PATH_USER_INFO_PAGE;
            }
        } else {
            page = PATH_SINGIN_PAGE;
        }
        return page;
    }
}
