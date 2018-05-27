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
import by.tareltos.fcqdelivery.repository.impl.UserRepository;
import by.tareltos.fcqdelivery.specification.account.AccountByCadDetailsSpecification;
import by.tareltos.fcqdelivery.specification.application.*;
import by.tareltos.fcqdelivery.specification.courier.CourierByRegNumberSpecification;
import by.tareltos.fcqdelivery.specification.courier.CourierByStatusSpecification;
import by.tareltos.fcqdelivery.util.EmailSender;
import by.tareltos.fcqdelivery.util.MessageManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Properties;

/**
 * The class serves to implement the logic of the application
 * processing parameters from the command
 * and transfer them to the receiver.
 *
 * @autor Tarelko Vitali
 */
public class ApplicationReceiver {
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
     * Parameter is used to determine the application with what status to search in the database
     */
    private static final String ALL_APPLICATION_STATUS = "all";
    /**
     * Object for work with application table in the database
     *
     * @see by.tareltos.fcqdelivery.repository.impl.ApplicationRepository
     */
    private ApplicationRepository repository = ApplicationRepository.getInstance();
    /**
     * Object for work with courier table in the database
     *
     * @see by.tareltos.fcqdelivery.repository.impl.CourierRepository
     */
    private CourierRepository courierRepository = CourierRepository.getInstance();
    /**
     * Object for work with account table in the database
     *
     * @see by.tareltos.fcqdelivery.repository.impl.AccountRepository
     */
    private AccountRepository accountRepository = AccountRepository.getInstance();
    /**
     * Object for work with user table in the database
     *
     * @see by.tareltos.fcqdelivery.repository.impl.UserRepository
     */
    private UserRepository userRepository = UserRepository.getInstance();

    private static  ApplicationReceiver instance = new ApplicationReceiver();

    public static ApplicationReceiver getInstance(){
        return instance;
    }

    private ApplicationReceiver() {
    }

    /**
     * Method is used to obtain a list of applications
     *
     * @param email used to select customer requests by email if user role is customer
     * @param role  used to identify role of user which trying to obtain applications
     * @return List of all applications in database, if user role manager. Otherwise all applications by email prm.
     * @throws ReceiverException if a RepositoryException was caught
     */
    public List<Application> getAllApplications(String email, UserRole role) {
        List<Application> resultList = null;
        try {
            if (role.equals(UserRole.MANAGER)) {
                resultList = repository.query(new AllApplicationSpecification());
                LOGGER.log(Level.DEBUG, "Application List size:" + resultList.size());
            } else {
                resultList = repository.query(new ApplicationByOwnerSpecification(email));
                LOGGER.log(Level.DEBUG, "Application List size:" + resultList.size());
            }
            return resultList;
        } catch (RepositoryException e) {
            new ReceiverException("Exception in getAllApplication method", e);
        }
        return resultList;
    }

    /**
     * Method is used to create new application
     *
     * @param owner       -logined customer, which trying to create application
     * @param startPoint  -loading point
     * @param finishPoint -unloading point
     * @param date        -delivery date
     * @param comment     -comment to the application
     * @param weight      -cargo weight
     * @return true if the application was successfully created and recorded in the database. Otherwise false
     * @throws ReceiverException if a RepositoryException was caught
     * @see by.tareltos.fcqdelivery.entity.application.Application
     */
    public boolean createNewApplication(User owner, String startPoint, String finishPoint, String date, String comment, String weight) throws ReceiverException {
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
            throw new ReceiverException("Exception in createNewApplication method", e);
        }
    }

    /**
     * Method is used to obtain application by primary key
     *
     * @param id - primary key of application in database
     * @return Application.class object if application exist in database. Otherwise null
     * @throws ReceiverException if a RepositoryException was caught
     * @see by.tareltos.fcqdelivery.entity.application.Application
     */
    public Application getApplication(String id) throws ReceiverException {
        try {
            List<Application> applications = repository.query(new ApplicationByIdSpecification(id));
            if (applications.isEmpty()) {
                return null;
            }
            Application application = applications.get(0);
            return application;
        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in getApplication method", e);
        }

    }

    /**
     * Method is used to obtain a list of couriers with active status
     *
     * @return List of couriers with active status
     * @throws ReceiverException if a ReceiverException was caught
     * @see by.tareltos.fcqdelivery.entity.courier.Courier
     */
    public List<Courier> getCourierForAppointment() {
        try {
            return courierRepository.query(new CourierByStatusSpecification(ACTIVE_COURIER_STATUS));
        } catch (RepositoryException e) {
            new ReceiverException("Exception in getCourierForAppointment method", e);
        }
        return null;
    }

    /**
     * Method is used to update existing application in database, set courier ind price,
     * the price calculated from the courier tariff and distance parameter.
     *
     * @param appId      -primary key of application in database
     * @param courierId  -primary key of courier in database
     * @param distance   -parameter that is used to calculate the price
     * @param properties
     * @param locale
     * @return true if the application was successfully updated. Otherwise false
     * @throws ReceiverException if a RepositoryException was caught
     * @see by.tareltos.fcqdelivery.entity.application.Application
     * @see by.tareltos.fcqdelivery.entity.courier.Courier
     */
    public boolean updateApplication(String appId, String courierId, String distance, Properties properties, String locale) throws ReceiverException {
        try {
            Application application = repository.query(new ApplicationByIdSpecification(appId)).get(0);
            Courier courier = courierRepository.query(new CourierByRegNumberSpecification(courierId)).get(0);
            application.setCourier(courier);
            double price = new BigDecimal(courier.getKmTax() * Integer.parseInt(distance)).setScale(2, RoundingMode.UP).doubleValue();
            application.setPrice(price);
            application.setStatus(ApplicationStatus.WAITING);
            String mailText = MessageManager.getProperty("applicationInfo", locale) + application.getPrice();
            EmailSender.sendMail(application.getOwner().getEmail(), "FCQ-Delivery Info", mailText, properties);
            return repository.update(application);
        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in updateApplication method", e);
        }
    }

    /**
     * Method is used to pay for the application
     *
     * @param appId      -primary key of application in database
     * @param cardNumber - number of credit/debit card
     * @param expMonth   - expiration month of card
     * @param expYear    - expiration year of card
     * @param owner      - card owner's first and last name
     * @param csv        - csv code of card
     * @param properties
     * @param locale
     * @return true if payment was successful. Otherwise false
     * @throws ReceiverException if a RepositoryException was caught
     * @see by.tareltos.fcqdelivery.entity.application.Application
     * @see by.tareltos.fcqdelivery.entity.account.Account
     */
    public boolean payForApplication(String appId, String cardNumber, String expMonth, String expYear, String owner, String csv, Properties properties, String locale) throws ReceiverException {
        try {
            Application application = repository.query(new ApplicationByIdSpecification(appId)).get(0);
            String ownerData[] = owner.split(" ");// в константы
            String fName = ownerData[0];
            String lName = ownerData[1];
            List<Account> accounts = accountRepository.query(new AccountByCadDetailsSpecification(cardNumber, expMonth, expYear, fName, lName, csv));
            if (accounts.size() == 1) {
                Account account = accountRepository.query(new AccountByCadDetailsSpecification(cardNumber, expMonth, expYear, fName, lName, csv)).get(0);
                doPayment(account, application.getPrice());
                application.setStatus(ApplicationStatus.CONFIRMED);
                String emailText = MessageManager.getProperty("emailForDriver", locale);
                EmailSender.sendMail(application.getCourier().getDriverEmail(), "FCQ-Delivery Info", preparedMessage(emailText, application), properties);
                return (repository.update(application) & accountRepository.update(account));
            } else {
                return false;
            }
        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in payForApplication method", e);
        }
    }

    /**
     * Method is used to set status "delivered" for the application
     *
     * @param applicationId -primary key of application in database
     * @return true if changing was successful. Otherwise false
     * @throws ReceiverException if a RepositoryException was caught
     * @see by.tareltos.fcqdelivery.entity.application.Application
     */
    public boolean completeApplication(String applicationId) throws ReceiverException {
        try {
            Application application = repository.query(new ApplicationByIdSpecification(applicationId)).get(0);
            application.setStatus(ApplicationStatus.DELIVERED);
            return repository.update(application);
        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in completeApplication method", e);
        }
    }

    /**
     * Method is used to delete application
     *
     * @param applicationId -primary key of application in database
     * @return true if deleting was successful. Otherwise false
     * @throws ReceiverException if a RepositoryException was caught and if application status not NEW
     * @see by.tareltos.fcqdelivery.entity.application.Application
     */
    public boolean deleteApplication(String applicationId) throws ReceiverException {
        try {
            Application application = repository.query(new ApplicationByIdSpecification(applicationId)).get(0);
            if (ApplicationStatus.NEW.equals(application.getStatus())) {
                return repository.remove(application);
            }
            throw new ReceiverException("Can only delete orders with NEW status");
        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in deleteApplication method", e);
        }
    }

    /**
     * Method is used to set status "canceled" for the application
     *
     * @param applicationId -primary key of application in database
     * @param reason        - reason for canceling the application
     * @return true if changing was successful. Otherwise false
     * @throws ReceiverException if a RepositoryException was caught
     * @see by.tareltos.fcqdelivery.entity.application.Application
     */
    public boolean cancelApplication(String applicationId, String reason) throws ReceiverException {
        try {
            Application application = repository.query(new ApplicationByIdSpecification(applicationId)).get(0);
            application.setCancelationReason(reason);
            application.setStatus(ApplicationStatus.CANCELED);
            return repository.update(application);
        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in cancelApplication method", e);
        }
    }

    /**
     * Method is used to obtain list of filtered applications
     *
     * @param customerEmail
     * @param applicationStatus
     * @return list of filtered applications
     * @throws ReceiverException if a RepositoryException was caught
     * @see by.tareltos.fcqdelivery.entity.application.Application
     */
    public List<Application> getSelectedApplications(String customerEmail, String applicationStatus) {
        List<Application> resultList = null;
        try {
            if ("".equals(customerEmail) && !applicationStatus.equals(ALL_APPLICATION_STATUS)) {
                resultList = repository.query(new ApplicationByStatusSpecification(applicationStatus));
                LOGGER.log(Level.DEBUG, "Application List size:" + resultList.size());
                return resultList;
            }
            if (customerEmail.equals("") && applicationStatus.equals(ALL_APPLICATION_STATUS)) {
                resultList = repository.query(new AllApplicationSpecification());
                LOGGER.log(Level.DEBUG, "Application List size:" + resultList.size());
                return resultList;
            }
            if (applicationStatus.equals(ALL_APPLICATION_STATUS)) {
                resultList = repository.query(new ApplicationByOwnerSpecification(customerEmail));
                LOGGER.log(Level.DEBUG, "Application List size:" + resultList.size());
                return resultList;
            } else {
                resultList = repository.query(new ApplicationByOwnerAndStatusSpecification(customerEmail, applicationStatus));
                LOGGER.log(Level.DEBUG, "Application List size:" + resultList.size());
                return resultList;
            }
        } catch (RepositoryException e) {
            new ReceiverException("Exception in getSelectedApplications method", e);
        }
        return resultList;

    }

    /**
     * Method is used to check account balance and do payment
     *
     * @throws ReceiverException if account balance < price
     * @see by.tareltos.fcqdelivery.entity.application.Application
     */
    private Account doPayment(Account account, double price) throws ReceiverException {
        if (account.getBalance() >= price) {
            account.setBalance(account.getBalance() - price);
            return account;
        } else {
            throw new ReceiverException("Insufficient funds");
        }
    }

    private String preparedMessage(String text, Application application) {
        return String.format(text, application.getCourier().getDriverName(), application.getStartPoint(), application.getFinishPoint(), application.getDeliveryDate(), application.getOwner().getEmail() + " phone: " + application.getOwner().getPhone());
    }

}
