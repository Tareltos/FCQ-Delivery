package by.tareltos.fcqdelivery.command.courier;

import by.tareltos.fcqdelivery.command.Command;
import by.tareltos.fcqdelivery.command.PagePath;
import by.tareltos.fcqdelivery.entity.courier.Courier;
import by.tareltos.fcqdelivery.entity.user.User;
import by.tareltos.fcqdelivery.receiver.CourierReceiver;
import by.tareltos.fcqdelivery.receiver.ReceiverException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

public class PaginationGetCouriersCommand implements Command {

    final static Logger LOGGER = LogManager.getLogger();
    private static final String LOGINED_USER_PRM = "loginedUser";
    private static String FIRST_ROW_PRM = "firstRow";
    private static String ROW_COUNT_PRM = "rowCount";
    private CourierReceiver receiver;


    public PaginationGetCouriersCommand(CourierReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        User loginedUser = (User) session.getAttribute(LOGINED_USER_PRM);
        if (null == loginedUser) {
            LOGGER.log(Level.DEBUG, "Пользователь =null");
            return PagePath.PATH_SINGIN_PAGE.getPath();
        }
        if ("admin".equals(loginedUser.getRole().getRole())) {
            LOGGER.log(Level.DEBUG, "Нарушение прав доступа Пользователь: " + loginedUser.getRole().getRole());
            request.setAttribute("errorMessage", "У Вас нет прав доступа к этой странице");
            return PagePath.PATH_INF_PAGE.getPath();
        }
        String firstRow = request.getParameter(FIRST_ROW_PRM);
        String rowCount = request.getParameter(ROW_COUNT_PRM);// validation
        List<Courier> courierList = null;
        try {
            courierList = receiver.getCouriers(firstRow, rowCount, loginedUser.getRole().getRole());
        } catch (ReceiverException e) {
            e.printStackTrace();
        }
        if (null == courierList) {
            request.setAttribute("errorMessage", "Курьеров не найдено");
            return PagePath.PATH_INF_PAGE.getPath();
        }
        int allCount = 0;
        try {
            allCount = receiver.getCouriers("0", String.valueOf(receiver.getCouriers().size()), loginedUser.getRole().getRole()).size();

        } catch (ReceiverException e) {
            e.printStackTrace();
        }
        request.setAttribute("firstRow", firstRow);
        request.setAttribute("rowCount", rowCount);
        request.setAttribute("allCount", allCount);
        request.setAttribute("courierList", courierList);
        return PagePath.PATH_COURIERS_PAGE.getPath();
    }
}
