package by.tareltos.fcqdelivery.receiver;

import by.tareltos.fcqdelivery.entity.account.Account;
import by.tareltos.fcqdelivery.entity.application.Application;
import by.tareltos.fcqdelivery.entity.application.ApplicationStatus;
import by.tareltos.fcqdelivery.entity.courier.Courier;
import by.tareltos.fcqdelivery.entity.user.User;
import by.tareltos.fcqdelivery.entity.user.UserRole;
import by.tareltos.fcqdelivery.repository.RepositoryException;
import by.tareltos.fcqdelivery.repository.impl.AccountRepository;
import by.tareltos.fcqdelivery.repository.impl.ApplicationRepository;
import by.tareltos.fcqdelivery.repository.impl.CourierRepository;
import by.tareltos.fcqdelivery.specification.account.AccountByCadDetailsSpecification;
import by.tareltos.fcqdelivery.specification.application.AllApplicationSpecification;
import by.tareltos.fcqdelivery.specification.application.ApplicationByIdSpecification;
import by.tareltos.fcqdelivery.specification.application.ApplicationByOwnerSpecification;
import by.tareltos.fcqdelivery.specification.courier.AllCourierSpecification;
import by.tareltos.fcqdelivery.specification.courier.CourierByRegNumberSpecification;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

public class ApplicationReceiver {

    final static Logger LOGGER = LogManager.getLogger();
    private ApplicationRepository repository = new ApplicationRepository();
    private CourierRepository courierRepository = new CourierRepository();
    private AccountRepository accountRepository = new AccountRepository();

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
        }
    }

    public boolean payForApplication(String appId, String cardNumber, String expMonth, String expYear, String owner, String csv) throws ReceiverException {
        try {
            Application application = repository.query(new ApplicationByIdSpecification(appId)).get(0);

            String ownerData[] = owner.split(" ");// в константы
            String fName = ownerData[0];
            String lName = ownerData[1];
            Account account = accountRepository.query(new AccountByCadDetailsSpecification(cardNumber, expMonth, expYear, fName, lName, csv)).get(0);
            if (account != null) {
                doPayment(account, application.getPrice());
                application.setStatus(ApplicationStatus.CONFIRMED);
                return (repository.update(application) & accountRepository.update(account));
            } else {
                return false;
            }
        } catch (RepositoryException e) {
            throw new ReceiverException("Exception", e);
        }
    }

    public boolean completeApplication(String applicationId) throws ReceiverException {
        try {
            Application application = repository.query(new ApplicationByIdSpecification(applicationId)).get(0);
            application.setStatus(ApplicationStatus.DELIVERED);
            return repository.update(application);
        } catch (RepositoryException e) {
            throw new ReceiverException("Exception", e);
        }


    }

    public boolean deleteApplication(String applicationId) throws ReceiverException {
        try {
            Application application = repository.query(new ApplicationByIdSpecification(applicationId)).get(0);
            if (ApplicationStatus.NEW.equals(application.getStatus())) {
                return repository.remove(application);
            }
            throw new ReceiverException("Можно удалять только заявки со статусом NEW");
        } catch (RepositoryException e) {
            throw new ReceiverException("Exception", e);
        }
    }

    public boolean cancelApplication(String applicationId, String reason) throws ReceiverException {
        try {
            Application application = repository.query(new ApplicationByIdSpecification(applicationId)).get(0);
            application.setCancelationReason(reason);
            application.setStatus(ApplicationStatus.CANCELED);
            return repository.update(application);
        } catch (RepositoryException e) {
            throw new ReceiverException("Exception", e);
        }
    }

    private Account doPayment(Account account, double price) throws ReceiverException {
        if (account.getBalance() >= price) {
            account.setBalance(account.getBalance() - price);
            return account;
        } else {
            throw new ReceiverException("Недостаточно стредств");
        }
    }


}
