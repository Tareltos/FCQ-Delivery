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
/**
 * Class is used to obtain parameters from request,
 * send them into receiver and to return path to jsp page in controller.
 *
 * @autor Tarelko Vitali
 * @see Command
 */
public class CreateCourierCommand implements Command {
    /**
     * The logger object, used to write logs
     *
     * @see org.apache.logging.log4j.Logger
     */
    private static final Logger LOGGER = LogManager.getLogger();
    /**
     * Parameter name in the request
     */
    private static final String MESSAGE = "message";
    /**
     * The variable stores the name of the session attribute
     */
    private static final String LOGINED_USER = "loginedUser";
    /**
     * Parameter name in the request
     */
    private static final String CAR_NUMBER = "carNumber";
    /**
     * Parameter name in the request
     */
    private static final String CAR_PRODUCER = "carProducer";
    /**
     * Parameter name in the request
     */
    private static final String CAR_MODEL = "carModel";
    /**
     * Parameter name in the request
     */
    private static final String CAR_IMG = "files/img";
    /**
     * Parameter name in the request
     */
    private static final String DRIVER_EMAIL = "email";
    /**
     * Parameter name in the request
     */
    private static final String DRIVER_NAME = "name";
    /**
     * Parameter name in the request
     */
    private static final String DRIVER_PHONE = "phone";
    /**
     * Parameter name in the request
     */
    private static final String CARGO = "cargo";
    /**
     * Parameter name in the request
     */
    private static final String TAX = "tax";
    /**
     * Parameter name in the request
     */
    private static final String STATUS = "status";
    /**
     * Variable used to determine the role of the customer
     */
    private static final String CUSTOMER_ROLE = "customer";
    /**
     * Variable used to determine the role of the admin
     */
    private static final String ADMIN_ROLE = "admin";
    private CourierReceiver receiver;

    /**
     * Method returns the path to the jsp page
     *
     * @return return the path to the jsp page
     * @see by.tareltos.fcqdelivery.command.Command
     */
    public CreateCourierCommand(CourierReceiver receiver) {
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
            result = receiver.createCourier(carNumber, carProducer, carModel, carPhotoFullPath, driverName, driverPhone, driverEmail, maxCargo, tax, status);
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
