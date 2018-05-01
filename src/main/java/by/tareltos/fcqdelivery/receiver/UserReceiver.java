package by.tareltos.fcqdelivery.receiver;

import by.tareltos.fcqdelivery.entity.user.User;
import by.tareltos.fcqdelivery.entity.user.UserRole;
import by.tareltos.fcqdelivery.entity.user.UserStatus;
import by.tareltos.fcqdelivery.repository.RepositoryException;
import by.tareltos.fcqdelivery.repository.impl.UserRepository;
import by.tareltos.fcqdelivery.specification.user.AllUserSpecification;
import by.tareltos.fcqdelivery.specification.user.UserByEmailSpecification;
import by.tareltos.fcqdelivery.util.EmailSender;
import by.tareltos.fcqdelivery.util.PasswordGenerator;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.util.List;
import java.util.Properties;

public class UserReceiver {

    final static Logger LOGGER = LogManager.getLogger();
    final private String ACTIVE_USER_STATUS = "active";
    private UserRepository repository = new UserRepository();

    public boolean checkUserStatus(String email) throws ReceiverException {
        try {
            List<User> listUser = repository.query(new UserByEmailSpecification(email));
            if (listUser.isEmpty()) {
                throw new ReceiverException("User not found");
            }
            if (listUser.size() > 1) {
                throw new ReceiverException("Count of users " + listUser.size() + " must be 1");
            }
            LOGGER.log(Level.DEBUG, "Found : " + listUser.size() + " users, must be 1");
            User u = listUser.get(0);
            if (ACTIVE_USER_STATUS.equals(u.getStatus().getStatus())) {
                LOGGER.log(Level.DEBUG, "User status:" + u.getEmail() + " is active");
                return true;
            }
            return false;
        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in checkUserStatus", e);
        }
    }

    public boolean checkUser(String email, String password) throws ReceiverException {
        try {
            List<User> listUser = repository.query(new UserByEmailSpecification(email));
            if (listUser.isEmpty()) {
                throw new ReceiverException("User not found");
            }
            if (listUser.size() > 1) {
                throw new ReceiverException("Count of users " + listUser.size() + " must be 1");
            }
            LOGGER.log(Level.DEBUG, "Found : " + listUser.size() + " users, must be 1");
            User user = listUser.get(0);
            if (email.equals(user.getEmail()) & password.equals(user.getPassword()) & ACTIVE_USER_STATUS.equals(user.getStatus().getStatus())) {
                LOGGER.log(Level.DEBUG, "Result:" + true);
                return true;
            }
            return false;
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
            String password = PasswordGenerator.generatePassword();
            List<User> listUser = repository.query(new UserByEmailSpecification(email));
            LOGGER.log(Level.DEBUG, "Found : " + listUser.size() + " users, must be 1");
            if (listUser.size() == 1) {
                User user = listUser.get(0);
                user.setPassword(password);
                result = repository.update(user);
                EmailSender.sendMail(email, "FCQ-Delivery-Info", "New Password:" + password, props);
            }
        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in resetPassword method", e);
        }
        return result;
    }

    public boolean createUser(String email, String fName, String lName, String phone, String role, Properties props) throws ReceiverException {
        boolean result;
        try {
            String pass = PasswordGenerator.generatePassword();
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
            }
            User newUser = new User(email, pass, fName, lName, phone, userRole, UserStatus.ACTIVE);
            LOGGER.log(Level.DEBUG, "Created new user: ", newUser.toString());
            result = repository.add(newUser);
            EmailSender.sendMail(newUser.getEmail(), "FCQ-Delivery-Registration", "Password:" + newUser.getPassword(), props);
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
