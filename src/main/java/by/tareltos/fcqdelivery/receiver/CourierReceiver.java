package by.tareltos.fcqdelivery.receiver;

import by.tareltos.fcqdelivery.entity.courier.Courier;
import by.tareltos.fcqdelivery.entity.courier.CourierStatus;
import by.tareltos.fcqdelivery.repository.RepositoryException;
import by.tareltos.fcqdelivery.repository.impl.CourierRepository;
import by.tareltos.fcqdelivery.specification.courier.AllCourierSpecification;
import by.tareltos.fcqdelivery.specification.courier.CourierByRegNumberSpecification;
import by.tareltos.fcqdelivery.specification.courier.PaginationCourierSpecification;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

public class CourierReceiver {

    final static Logger LOGGER = LogManager.getLogger();
    private CourierRepository repository = new CourierRepository();

    public List<Courier> getCouriers() {
        List<Courier> courierList = null;
        try {
            courierList = repository.query(new AllCourierSpecification());
            LOGGER.log(Level.DEBUG, "Found different couriers: " + courierList.size());
        } catch (RepositoryException e) {
            new ReceiverException("Exception in getCourier method", e);
        } catch (SQLException e) {
            e.printStackTrace();
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

    public Courier getCourier(String carNumber) throws ReceiverException, SQLException {
        Courier courier;
        try {
            List<Courier> list = repository.query(new CourierByRegNumberSpecification(carNumber));
            courier = list.get(0);
            return courier;
        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in getCourier ByNumber");
        }
    }

    public boolean updateCourier(String carNumber, String carProducer, String carModel, String carPhotoPath, String driverName, String driverPhone, String driverEmail, int maxCargo, double tax, String status) throws ReceiverException {
        CourierStatus courierStatus;
        if ("active".equals(status)) {
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

    public List<Courier> getCouriers(String firstRow, String rowCount) {
        List<Courier> courierList = null;
        try {
            int fRow = Integer.parseInt(firstRow);
            int rCount = Integer.parseInt(rowCount);
            courierList = repository.query(new PaginationCourierSpecification(fRow, rCount));
            LOGGER.log(Level.DEBUG, "Found different couriers: " + courierList.size());
        } catch (RepositoryException e) {
            new ReceiverException("Exception in getCourier method", e);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courierList;
    }
}
