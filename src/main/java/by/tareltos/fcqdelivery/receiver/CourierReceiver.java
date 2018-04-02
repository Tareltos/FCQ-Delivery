package by.tareltos.fcqdelivery.receiver;

import by.tareltos.fcqdelivery.entity.Courier;
import by.tareltos.fcqdelivery.repository.impl.CourierRepository;
import by.tareltos.fcqdelivery.specification.impl.AllCourierSpecification;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

public class CourierReceiver {

    final static Logger LOGGER = LogManager.getLogger(UserReceiver.class);
    final static CourierRepository REPOSITORY = new CourierRepository();

    public List<Courier> getCouriers() {
        List<Courier> courierList = null;
        try {
            courierList = REPOSITORY.query(new AllCourierSpecification());
            LOGGER.log(Level.DEBUG, "Found different couriers: " + courierList.size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courierList;
    }
}
