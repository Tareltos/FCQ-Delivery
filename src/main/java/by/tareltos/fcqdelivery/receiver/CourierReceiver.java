package by.tareltos.fcqdelivery.receiver;

import by.tareltos.fcqdelivery.entity.courier.Courier;
import by.tareltos.fcqdelivery.entity.courier.CourierStatus;
import by.tareltos.fcqdelivery.repository.RepositoryException;
import by.tareltos.fcqdelivery.repository.impl.CourierRepository;
import by.tareltos.fcqdelivery.repository.impl.UserRepository;
import by.tareltos.fcqdelivery.specification.courier.AllCourierSpecification;
import by.tareltos.fcqdelivery.specification.courier.CourierByRegNumberSpecification;
import by.tareltos.fcqdelivery.specification.courier.PaginationCourierByStatusSpecification;
import by.tareltos.fcqdelivery.specification.courier.PaginationCourierSpecification;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

/**
 * The class serves to implement the logic of the application
 * processing parameters from the command
 * and transfer them to the receiver.
 *
 * @autor Tarelko Vitali
 */
public class CourierReceiver {
    /**
     * The logger object, used to write logs
     *
     * @see org.apache.logging.log4j.Logger
     */
    private static final Logger LOGGER = LogManager.getLogger();
    /**
     * Parameter used to identify current courier status
     */
    private static final String ACTIVE_COURIER_STATUS = "active";
    /**
     * Object for work with courier table in the database
     *
     * @see by.tareltos.fcqdelivery.repository.impl.CourierRepository
     */
    private CourierRepository repository = new CourierRepository();
    /**
     * Object for work with user table in the database
     *
     * @see by.tareltos.fcqdelivery.repository.impl.UserRepository
     */
    private UserRepository userRepository = new UserRepository();

    /**
     * Method is used to obtain list of couriers
     *
     * @throws ReceiverException if a RepositoryException was caught
     * @see by.tareltos.fcqdelivery.entity.courier.Courier
     */
    public List<Courier> getCouriers() throws ReceiverException {
        try {
            return repository.query(new AllCourierSpecification());
        } catch (RepositoryException e) {
            new ReceiverException("Exception in getCourier method", e);
        }
        throw new ReceiverException("Couriers are not found");
    }

    /**
     * Method is used to create new courier
     *
     * @return true if the courier was successfully created and recorded in the database. Otherwise false
     * @throws ReceiverException if a RepositoryException was caught
     * @see by.tareltos.fcqdelivery.entity.application.Application
     */
    public boolean createCourier(String carNumber, String carProducer, String carModel, String carPhotoPath, String driverName, String driverPhone, String driverEmail, int maxCargo, double tax, String status) throws ReceiverException {
        CourierStatus courierStatus;
        if (ACTIVE_COURIER_STATUS.equals(status)) {
            courierStatus = CourierStatus.ACTIVE;
        } else {
            courierStatus = CourierStatus.BLOCKED;
        }
        Courier newCourier = new Courier(carNumber, carProducer, carModel, driverPhone, driverName, driverEmail, maxCargo, tax, carPhotoPath, courierStatus);
        try {
            return repository.add(newCourier);
        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in createCourier method", e);
        }
    }

    /**
     * Method is used to obtain  courier by primary key
     *
     * @param carNumber -primary key of courier in database
     * @return Object of Courier.class
     * @throws ReceiverException if a RepositoryException was caught
     * @see by.tareltos.fcqdelivery.entity.courier.Courier
     */
    public Courier getCourier(String carNumber) throws ReceiverException {
        try {
            List<Courier> courierList = repository.query(new CourierByRegNumberSpecification(carNumber));
            if (courierList.isEmpty()) {
                LOGGER.log(Level.INFO, "Couriers are not found");
                throw new ReceiverException("Couriers are not found");
            }
            if (courierList.size() > 1) {
                LOGGER.log(Level.INFO, "Found : " + courierList.size() + " couriers, must be 1");
                throw new ReceiverException("Found : " + courierList.size() + " couriers, must be 1");
            }
            return courierList.get(0);
        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in getCourier ByNumber");
        }
    }

    /**
     * Method is used to update courier in database
     *
     * @throws ReceiverException if a RepositoryException was caught
     * @see by.tareltos.fcqdelivery.entity.courier.Courier
     */
    public boolean updateCourier(String carNumber, String carProducer, String carModel, String carPhotoPath, String driverName, String driverPhone, String driverEmail, int maxCargo, double tax, String status) throws ReceiverException {
        CourierStatus courierStatus;
        if (ACTIVE_COURIER_STATUS.equals(status)) {
            courierStatus = CourierStatus.ACTIVE;
        } else {
            courierStatus = CourierStatus.BLOCKED;
        }
        Courier newCourier = new Courier(carNumber, carProducer, carModel, driverPhone, driverName, driverEmail, maxCargo, tax, carPhotoPath, courierStatus);
        try {
            return repository.update(newCourier);
        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in createCourier method", e);
        }
    }

    /**
     * Method is used to obtain list of couriers for pagination
     *
     * @throws ReceiverException if a RepositoryException was caught
     * @see by.tareltos.fcqdelivery.entity.courier.Courier
     */
    public List<Courier> getCouriers(String firstRow, String rowCount, String userRole) throws ReceiverException {
        List<Courier> courierList = null;
        try {
            int fRow = Integer.parseInt(firstRow);
            int rCount = Integer.parseInt(rowCount);
            switch (userRole) {
                case "manager":
                    courierList = repository.query(new PaginationCourierSpecification(fRow, rCount));
                    LOGGER.log(Level.DEBUG, "Found different couriers: " + courierList.size());
                    break;
                case "customer":
                    courierList = repository.query(new PaginationCourierByStatusSpecification(ACTIVE_COURIER_STATUS, fRow, rCount));
                    LOGGER.log(Level.DEBUG, "Found different couriers: " + courierList.size());
                    break;
            }
        } catch (RepositoryException e) {
            new ReceiverException("Exception in getCourier method", e);
        }
        if (courierList.isEmpty()) {
            throw new ReceiverException("Couriers are not found");
        }
        return courierList;
    }
    /**
     * Method is used to check user status
     *
     * @param email -primary key of user in database
     * @return true if user status is active. Otherwise false
     * @throws ReceiverException if a RepositoryException was caught and if user not found
     * @see by.tareltos.fcqdelivery.entity.user.User
     */
    public boolean checkUserStatus(String email) throws ReceiverException {
        return CheckUserStatusUtil.checkUserStatus(email, userRepository);
    }


}

