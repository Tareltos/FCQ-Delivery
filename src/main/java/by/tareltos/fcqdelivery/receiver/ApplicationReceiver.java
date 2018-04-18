package by.tareltos.fcqdelivery.receiver;

import by.tareltos.fcqdelivery.entity.application.Application;
import by.tareltos.fcqdelivery.entity.application.ApplicationStatus;
import by.tareltos.fcqdelivery.entity.courier.Courier;
import by.tareltos.fcqdelivery.entity.user.User;
import by.tareltos.fcqdelivery.entity.user.UserRole;
import by.tareltos.fcqdelivery.repository.RepositoryException;
import by.tareltos.fcqdelivery.repository.impl.ApplicationRepository;
import by.tareltos.fcqdelivery.repository.impl.CourierRepository;
import by.tareltos.fcqdelivery.specification.impl.*;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

public class ApplicationReceiver {

    final static Logger LOGGER = LogManager.getLogger();
    private ApplicationRepository repository = new ApplicationRepository();
    private CourierRepository courierRepository = new CourierRepository();

    public List<Application> getAllApplications(String email, UserRole role) {
        List<Application> resultList = null;
        try {
            if (role.equals(UserRole.MANAGER)) {
                LOGGER.log(Level.DEBUG, "Application HERE");
                resultList = repository.query(new AllApplicationSpecification());
                LOGGER.log(Level.DEBUG, "Application List size:" + resultList.size());
            } else {
                LOGGER.log(Level.DEBUG, "Application HERE111");
                resultList = repository.query(new ApplicationByOwnerSpecification(email));
                LOGGER.log(Level.DEBUG, "Application List size:" + resultList.size());
            }
            return resultList;
        } catch (RepositoryException e) {
            new ReceiverException("Exception in getAllApplication method", e);
        }
        return resultList;
    }

    public boolean createNewApplication(User owner, String startPoint, String finishPoint, String date, String comment, String weight) throws RepositoryException {

        Application application = new Application();
        application.setOwner(owner);
        application.setStartPoint(startPoint);
        application.setFinishPoint(finishPoint);
        application.setDeliveryDate(date);
        application.setComment(comment);
        application.setCargo(Integer.parseInt(weight));
        application.setStatus(ApplicationStatus.NEW);
        try {
            return repository.add(application);
        } catch (RepositoryException e) {
            throw new RepositoryException("Exception", e);
        }
    }

    public Application getApplication(String id) throws ReceiverException {
        try {
            Application application = repository.query(new ApplicationByIdSpecification(id)).get(0);
            return application;
        } catch (RepositoryException e) {
            throw new ReceiverException("Exception", e);
        }

    }

    public List<Courier> getCourierForAppointment() {
        try {
            return courierRepository.query(new AllCourierSpecification());
        } catch (RepositoryException e) {
            new ReceiverException("Exception", e);
        } catch (SQLException e) {
            //!!!!!!!!!!!!!!!!!!
        }
        return null;
    }

    public boolean updateApplication(String appId, String courierId, String distance) throws ReceiverException {
        try {
            Application application = repository.query(new ApplicationByIdSpecification(appId)).get(0);
            Courier courier = (Courier) courierRepository.query(new CourierByRegNumberSpecification(courierId)).get(0);
            application.setCourier(courier);
            application.setPrice(courier.getKmTax() * Integer.parseInt(distance));
            application.setStatus(ApplicationStatus.WAITING);
            return repository.update(application);
        } catch (RepositoryException e) {
            throw new ReceiverException("Exception", e);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean confirmApplication(String appId) throws ReceiverException {
        try {
            Application application = repository.query(new ApplicationByIdSpecification(appId)).get(0);
            application.setStatus(ApplicationStatus.CONFIRMED);
            return repository.update(application);
        } catch (RepositoryException e) {
            throw new ReceiverException("Exception", e);
        }

    }
}
