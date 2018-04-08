package by.tareltos.fcqdelivery.receiver;

import by.tareltos.fcqdelivery.entity.Courier;
import by.tareltos.fcqdelivery.entity.CourierStatus;
import by.tareltos.fcqdelivery.repository.RepositoryException;
import by.tareltos.fcqdelivery.repository.impl.CourierRepository;
import by.tareltos.fcqdelivery.specification.impl.AllCourierSpecification;
import by.tareltos.fcqdelivery.specification.impl.CourierByRegNumberSpecification;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

public class CourierReceiver {

    final static Logger LOGGER = LogManager.getLogger(UserReceiver.class);
    private CourierRepository repository = new CourierRepository();

    public List<Courier> getCouriers() {
        List<Courier> courierList = null;
        try {
            courierList = repository.query(new AllCourierSpecification());
            LOGGER.log(Level.DEBUG, "Found different couriers: " + courierList.size());
        } catch (SQLException e) {
            new ReceiverException("Exception in getCourier method", e);
        }
        return courierList;
    }

    public boolean createCourier(String carNumber, String carProducer, String carModel, String carPhotoPath, String driverName, String driverPhone, String driverEmail, int maxCargo, double tax, String status) throws ReceiverException {
        CourierStatus courierStatus;
        if ("active".equals(status)) {
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

    public Courier getCourier(String carNumber) throws ReceiverException {
        Courier courier;
        try {
            List<Courier> list = repository.query(new CourierByRegNumberSpecification(carNumber));
            courier = list.get(0);
            return courier;
        } catch (SQLException e) {
            throw new ReceiverException("Exception in getCourier ByNumber");
        }
    }
}
