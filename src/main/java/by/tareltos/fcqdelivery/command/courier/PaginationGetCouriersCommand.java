package by.tareltos.fcqdelivery.command.courier;

import by.tareltos.fcqdelivery.command.Command;
import by.tareltos.fcqdelivery.command.PagePath;
import by.tareltos.fcqdelivery.entity.courier.Courier;
import by.tareltos.fcqdelivery.entity.user.User;
import by.tareltos.fcqdelivery.receiver.ReceiverException;
import org.apache.logging.log4j.Level;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

import static by.tareltos.fcqdelivery.command.ParameterStore.*;

/**
 * Class is used to obtain parameters from request,
 * send them into receiver and to return path to jsp page in controller.
 *
 * @autor Tarelko Vitali
 * @see Command
 */
public class PaginationGetCouriersCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        User loginedUser = getUser(request);
        if (null == loginedUser) {
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
            courierList = COURIER_RECEIVER.getCouriers(firstRow, rowCount, loginedUser.getRole().getRole());
            if (courierList.isEmpty()) {
                request.setAttribute(MESSAGE, "dataNotFound.text");
                return PagePath.PATH_INF_PAGE.getPath();
            }
            int allCount = COURIER_RECEIVER.getCouriers("0", String.valueOf(COURIER_RECEIVER.getCouriers().size()), loginedUser.getRole().getRole()).size();
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
