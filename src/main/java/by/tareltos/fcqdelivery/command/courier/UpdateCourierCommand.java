package by.tareltos.fcqdelivery.command.courier;

import by.tareltos.fcqdelivery.command.Command;
import by.tareltos.fcqdelivery.command.PagePath;
import by.tareltos.fcqdelivery.entity.user.User;
import by.tareltos.fcqdelivery.receiver.CourierReceiver;
import by.tareltos.fcqdelivery.receiver.ReceiverException;
import by.tareltos.fcqdelivery.validator.DataValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class UpdateCourierCommand implements Command {
    final static Logger LOGGER = LogManager.getLogger();
    private static final String MESSAGE_ATR = "message";
    private static final String LOGINED_USER_PRM = "loginedUser";
    private static final String CAR_NUMBER_PRM = "carNumber";
    private static final String CAR_PRODUCER_PRM = "carProducer";
    private static final String CAR_MODEL_PRM = "carModel";
    private static final String CAR_IMG_PRM = "files/img";
    private static final String DRIVER_EMAIL_PRM = "email";
    private static final String DRIVER_NAME_PRM = "name";
    private static final String DRIVER_PHONE_PRM = "phone";
    private static final String CARGO_PRM = "cargo";
    private static final String TAX_PRM = "tax";
    private static final String STATUS_PRM = "status";
    private static final String CUSTOMER_ROLE = "customer";
    private static final String ADMIN_ROLE = "admin";

    private CourierReceiver receiver;


    public UpdateCourierCommand(CourierReceiver receiver) {
        this.receiver = receiver;
    }

    @Override
    public String execute(HttpServletRequest request) {

        HttpSession session = request.getSession(true);
        User loginedUser = (User) session.getAttribute(LOGINED_USER_PRM);
        if (null == loginedUser) {
            LOGGER.log(Level.DEBUG, "User is null");
            return PagePath.PATH_SINGIN_PAGE.getPath();
        }
        if (ADMIN_ROLE.equals(loginedUser.getRole().getRole()) | CUSTOMER_ROLE.equals(loginedUser.getRole().getRole())) {
            LOGGER.log(Level.DEBUG, "This page only for Manager! Access denied, you do not have rights: userRole= " + loginedUser.getRole().getRole());
            request.setAttribute(MESSAGE_ATR, "accessDenied.text");
            return PagePath.PATH_INF_PAGE.getPath();
        }

        String carNumber = request.getParameter(CAR_NUMBER_PRM);
        String carProducer = request.getParameter(CAR_PRODUCER_PRM);
        LOGGER.log(Level.DEBUG, "Test JS-injection protection " +carProducer);
        String carModel = request.getParameter(CAR_MODEL_PRM);
        String carPhotoFullPath = request.getParameter(CAR_IMG_PRM);
        if (carPhotoFullPath == null) {
            carPhotoFullPath = "noImg.png";
        }
        String driverName = request.getParameter(DRIVER_NAME_PRM);
        String driverEmail = request.getParameter(DRIVER_EMAIL_PRM);
        String driverPhone = request.getParameter(DRIVER_PHONE_PRM);
        String status = request.getParameter(STATUS_PRM);
        int maxCargo = Integer.parseInt(request.getParameter(CARGO_PRM));
        double tax = Double.parseDouble(request.getParameter(TAX_PRM));
        if (!DataValidator.validateCarNumber(carNumber) & !DataValidator.validateCarProducer(carProducer)
                & !DataValidator.validateCarModel(carModel) & !DataValidator.validatePhone(driverPhone)
                & !DataValidator.validateName(driverName) & !DataValidator.validateEmail(driverEmail)
                & !DataValidator.validateCargo(maxCargo) & !DataValidator.validateTax(tax)
                & !DataValidator.validateStatus(status)) {
            request.setAttribute(MESSAGE_ATR, "invalidData.text");
            return PagePath.PATH_INF_PAGE.getPath();
        }
        boolean result;
        try {
            result = receiver.updateCourier(carNumber, carProducer, carModel, carPhotoFullPath, driverName, driverPhone, driverEmail, maxCargo, tax, status);
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
        request.setAttribute(MESSAGE_ATR, "failedToSaveCourier.text");
        return PagePath.PATH_INF_PAGE.getPath();

    }
}
