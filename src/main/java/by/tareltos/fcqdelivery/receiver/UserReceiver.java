package by.tareltos.fcqdelivery.receiver;

import by.tareltos.fcqdelivery.entity.user.User;
import by.tareltos.fcqdelivery.entity.user.UserRole;
import by.tareltos.fcqdelivery.entity.user.UserStatus;
import by.tareltos.fcqdelivery.repository.RepositoryException;
import by.tareltos.fcqdelivery.repository.impl.UserRepository;
import by.tareltos.fcqdelivery.specification.user.AllUserSpecification;
import by.tareltos.fcqdelivery.specification.user.UserByEmailAndPassowordSpecification;
import by.tareltos.fcqdelivery.specification.user.UserByEmailSpecification;
import by.tareltos.fcqdelivery.util.EmailSender;
import by.tareltos.fcqdelivery.util.MessageManager;
import by.tareltos.fcqdelivery.util.PasswordUtil;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Properties;

/**
 * The class serves to implement the logic of the application
 * processing parameters from the command
 * and transfer them to the receiver.
 *
 * @autor Tarelko Vitali
 */
public class UserReceiver {
    /**
     * The logger object, used to write logs
     *
     * @see org.apache.logging.log4j.Logger
     */
    private static final Logger LOGGER = LogManager.getLogger();
    /**
     * Object for work with user table in the database
     *
     * @see by.tareltos.fcqdelivery.repository.impl.UserRepository
     */
    private UserRepository repository = UserRepository.getInstance();
    /**
     * Parameter used to identify current user status
     */
    private static final String ACTIVE_USER_STATUS = "active";

    private static UserReceiver instance = new UserReceiver();

    public static UserReceiver getInstance() {
        return instance;
    }

    private UserReceiver() {
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
        try {
            List<User> listUser = repository.query(new UserByEmailSpecification(email));
            if (listUser.isEmpty()) {
                throw new ReceiverException("User not found");
            }
            User u = listUser.get(0);
            if (ACTIVE_USER_STATUS.equals(u.getStatus().getStatus())) {
                return true;
            }
            return false;
        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in checkUserStatus", e);
        }
    }

    public boolean checkUser(String email, String password) throws ReceiverException {
        try {
            List<User> listUser = repository.query(new UserByEmailAndPassowordSpecification(email, password));
            if (listUser.isEmpty()) {
                throw new ReceiverException("User not found");
            }
            LOGGER.log(Level.DEBUG, "Found : " + listUser.size() + " users, must be 1");
            User user = listUser.get(0);
            LOGGER.log(Level.WARN, password);
            PasswordUtil.clearString(password);
            LOGGER.log(Level.WARN, password);
            return user != null ? checkUserStatus(email) : false;
        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in checkUser", e);
        }
    }

    public boolean checkEmail(String email) throws ReceiverException {
        try {
            List<User> listUser = repository.query(new UserByEmailSpecification(email));
            LOGGER.log(Level.DEBUG, "Found : " + listUser.size() + " users, must be 0");
            if (0 == listUser.size()) {
                return true;
            }
            return false;
        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in checkEmail", e);
        }
    }

    public boolean resetUserPassword(String email, Properties props) throws ReceiverException {
        boolean result = false;
        try {
            String password = PasswordUtil.generatePassword();
            List<User> listUser = repository.query(new UserByEmailSpecification(email));
            LOGGER.log(Level.DEBUG, "Found : " + listUser.size() + " users, must be 1");
            if (listUser.size() == 1) {
                User user = listUser.get(0);
                user.setPassword(password);
                result = repository.update(user);
                EmailSender.sendMail(email, "FCQ-Delivery-Info", "New Password:" + password, props);
                LOGGER.log(Level.DEBUG, password);
                PasswordUtil.clearString(password);
                LOGGER.log(Level.DEBUG, password);
            }
        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in resetPassword method", e);
        }
        return result;
    }

    public boolean createUser(String email, String fName, String lName, String phone, String role, Properties props, String locale) throws ReceiverException {
        boolean result;
        try {
            String pass = PasswordUtil.generatePassword();
            UserRole userRole = null;
            switch (role.toUpperCase()) {
                case "CUSTOMER":
                    userRole = UserRole.CUSTOMER;
                    break;
                case "MANAGER":
                    userRole = UserRole.MANAGER;
                    break;
                case "ADMIN":
                    userRole = UserRole.ADMIN;
                    break;
                default:
                    userRole = UserRole.CUSTOMER;
            }
            User newUser = new User(email, pass, fName, lName, phone, userRole, UserStatus.ACTIVE);
            LOGGER.log(Level.DEBUG, "Created new user: ", newUser.toString());
            result = repository.add(newUser);
            String mailText = MessageManager.getProperty("registrationMessage", locale) + newUser.getPassword();
            EmailSender.sendMail(newUser.getEmail(), "FCQ-Delivery-Registration", mailText, props);
            LOGGER.log(Level.DEBUG, pass);
            PasswordUtil.clearString(pass);
            LOGGER.log(Level.DEBUG, pass);
        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in createUser method", e);
        }
        return result;
    }

    public User getUserForSession(String email) throws ReceiverException {
        try {
            List<User> listUser = repository.query(new UserByEmailSpecification(email));
            LOGGER.log(Level.DEBUG, "Found : " + listUser.size() + " users, must be 1");
            if (1 == listUser.size()) {
                return listUser.get(0);
            }
            throw new ReceiverException("Found : " + listUser.size() + " users, must be 1");
        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in getUserForSession method", e);
        }
    }

    public boolean updateUser(User loginedUser) throws ReceiverException {
        try {
            return repository.update(loginedUser);
        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in updateUser method", e);
        }
    }

    public boolean changeUserStatus(String email) throws ReceiverException {
        try {
            List<User> userList = repository.query(new UserByEmailSpecification(email));
            User user = userList.get(0);
            UserStatus currentStatus = user.getStatus();
            switch (currentStatus) {
                case ACTIVE:
                    user.setStatus(UserStatus.BLOCKED);
                    break;
                case BLOCKED:
                    user.setStatus(UserStatus.ACTIVE);
                    break;
            }
            return repository.update(user);
        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in changeUserStatus method", e);
        }

    }

    public List<User> getAllUsers() throws ReceiverException {
        try {
            return repository.query(new AllUserSpecification());
        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in getAllUser method", e);
        }
    }


}
