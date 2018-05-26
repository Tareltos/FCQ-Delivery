package by.tareltos.fcqdelivery.command.courier;

import by.tareltos.fcqdelivery.command.Command;
import by.tareltos.fcqdelivery.command.PagePath;
import by.tareltos.fcqdelivery.entity.user.User;
import by.tareltos.fcqdelivery.receiver.ReceiverException;
import by.tareltos.fcqdelivery.util.DataValidator;
import org.apache.logging.log4j.Level;;

import javax.servlet.http.HttpServletRequest;

import static by.tareltos.fcqdelivery.command.ParameterStore.*;

/**
 * Class is used to obtain parameters from request,
 * send them into receiver and to return path to jsp page in controller.
 *
 * @autor Tarelko Vitali
 * @see Command
 */
public class CreateCourierCommand implements Command {

    @Override
    public String execute(HttpServletRequest request) {
        User loginedUser = getUser(request);
        if (null == loginedUser) {
            return PagePath.PATH_SINGIN_PAGE.getPath();
        }
        if (ADMIN_ROLE.equals(loginedUser.getRole().getRole()) | CUSTOMER_ROLE.equals(loginedUser.getRole().getRole())) {
            LOGGER.log(Level.DEBUG, "This page only for Manager! Access denied, you do not have rights: userRole= " + loginedUser.getRole().getRole());
            request.setAttribute(MESSAGE, "accessDenied.text");
            return PagePath.PATH_INF_PAGE.getPath();
        }
        String carNumber = request.getParameter(CAR_NUMBER);
        String carProducer = request.getParameter(CAR_PRODUCER);
        String carModel = request.getParameter(CAR_MODEL);
        String carPhotoFullPath = request.getParameter(CAR_IMG);
        if (carPhotoFullPath == "") {
            carPhotoFullPath = "noImg.png";
        }
        String driverName = request.getParameter(DRIVER_NAME);
        String driverEmail = request.getParameter(DRIVER_EMAIL);
        String driverPhone = request.getParameter(DRIVER_PHONE);
        String status = request.getParameter(STATUS);
        int maxCargo = Integer.parseInt(request.getParameter(CARGO));
        double tax = Double.parseDouble(request.getParameter(TAX));
        if (!DataValidator.validateCarNumber(carNumber) & !DataValidator.validateCarProducer(carProducer)
                & !DataValidator.validateCarModel(carModel) & !DataValidator.validatePhone(driverPhone)
                & !DataValidator.validateName(driverName) & !DataValidator.validateEmail(driverEmail)
                & !DataValidator.validateCargo(maxCargo) & !DataValidator.validateTax(tax)
                & !DataValidator.validateStatus(status)) {
            request.setAttribute(MESSAGE, "invalidData.text");
            return PagePath.PATH_INF_PAGE.getPath();
        }
        boolean result;
        try {
            result = COURIER_RECEIVER.createCourier(carNumber, carProducer, carModel, carPhotoFullPath, driverName, driverPhone, driverEmail, maxCargo, tax, status);
        } catch (ReceiverException e) {
            LOGGER.log(Level.WARN, e.getMessage());
            request.setAttribute("exception", e.getMessage());
            return PagePath.PATH_INF_PAGE.getPath();
        }
        if (result) {
            request.setAttribute("method", "redirect");
            request.setAttribute("redirectUrl", "/couriers?action=get_couriers_pg&firstRow=0&rowCount=3");
            return PagePath.PATH_COURIERS_PAGE.getPath();
        }
        request.setAttribute(MESSAGE, "failedToSaveCourier.text");
        return PagePath.PATH_INF_PAGE.getPath();
    }

}
