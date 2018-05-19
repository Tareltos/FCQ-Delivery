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

/**
 * Class is used to obtain parameters from request,
 * send them into receiver and to return path to jsp page in controller.
 *
 * @autor Tarelko Vitali
 * @see Command
 */
public class PaginationGetCouriersCommand implements Command {
    /**
     * The logger object, used to write logs
     *
     * @see org.apache.logging.log4j.Logger
     */
    private static final Logger LOGGER = LogManager.getLogger();
    /**
     * The variable stores the name of the session attribute
     */
    private static final String LOGINED_USER = "loginedUser";
    /**
     * Parameter name in the request
     */
    private static final String MESSAGE = "message";
    /**
     * Parameter name in the request
     */
    private static final String FIRST_ROW = "firstRow";
    /**
     * Parameter name in the request
     */
    private static final String ROW_COUNT = "rowCount";
    /**
     * Variable used to determine the role of the admin
     */
    private static final String ADMIN_ROLE = "admin";

    private CourierReceiver receiver;

    public PaginationGetCouriersCommand(CourierReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public String execute(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        User loginedUser = (User) session.getAttribute(LOGINED_USER);
        if (null == loginedUser) {
            LOGGER.log(Level.DEBUG, "User is null");
            return PagePath.PATH_SINGIN_PAGE.getPath();
        }
        if (ADMIN_ROLE.equals(loginedUser.getRole().getRole())) {
            LOGGER.log(Level.DEBUG, "This page only for Manager and Customer! Access denied, you do not have rights: userRole= " + loginedUser.getRole().getRole());
            request.setAttribute(MESSAGE, "accessDenied.text");
            return PagePath.PATH_INF_PAGE.getPath();
        }
        String firstRow = request.getParameter(FIRST_ROW);
        String rowCount = request.getParameter(ROW_COUNT);

        List<Courier> courierList;
        try {
            courierList = receiver.getCouriers(firstRow, rowCount, loginedUser.getRole().getRole());
            if (courierList.isEmpty()) {
                request.setAttribute(MESSAGE, "dataNotFound.text");
                return PagePath.PATH_INF_PAGE.getPath();
            }
            int allCount = receiver.getCouriers("0", String.valueOf(receiver.getCouriers().size()), loginedUser.getRole().getRole()).size();
            request.setAttribute("courierList", courierList);
            request.setAttribute("firstRow", firstRow);
            request.setAttribute("rowCount", rowCount);
            request.setAttribute("allCount", allCount);
            return PagePath.PATH_COURIERS_PAGE.getPath();
        } catch (ReceiverException e) {
            LOGGER.log(Level.WARN, e.getMessage());
            request.setAttribute("exception", e.getMessage());
            return PagePath.PATH_INF_PAGE.getPath();
        }
    }
}
