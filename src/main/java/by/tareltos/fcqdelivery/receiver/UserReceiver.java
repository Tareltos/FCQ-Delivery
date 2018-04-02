package by.tareltos.fcqdelivery.receiver;

import by.tareltos.fcqdelivery.entity.User;
import by.tareltos.fcqdelivery.entity.UserRole;
import by.tareltos.fcqdelivery.repository.impl.UserRepository;
import by.tareltos.fcqdelivery.specification.impl.AllUserSpecification;
import by.tareltos.fcqdelivery.specification.impl.UserByEmailSpecification;
import by.tareltos.fcqdelivery.util.EmailSender;
import by.tareltos.fcqdelivery.util.PasswordGenerator;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class UserReceiver {

    final static Logger LOGGER = LogManager.getLogger(UserReceiver.class);
    final static UserRepository REPOSITORY = new UserRepository();

    public boolean checkUser(String email, String password) {
        boolean result = false;
        try {
            List<User> listUser = REPOSITORY.query(new UserByEmailSpecification(email));
            if (listUser.size() != 0) {
                LOGGER.log(Level.DEBUG, "Found : " + listUser.size() + " users, must be 1");
                User u = listUser.get(0);
                LOGGER.log(Level.DEBUG, u.toString());
                if (email.equals(u.getEmail()) & password.equals(u.getPassword())) {
                    LOGGER.log(Level.DEBUG, "Result:" + true);
                    result = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean checkEmail(String email) {
        boolean result = false;
        try {
            List<User> listUser = REPOSITORY.query(new UserByEmailSpecification(email));
            LOGGER.log(Level.DEBUG, "Found : " + listUser.size() + " users, must be 0");
            if (listUser.size() == 0) {
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean resetUserPassword(String email, Properties props) {
        boolean result = false;
        String password = PasswordGenerator.generatePassword(email);
        try {
            List<User> listUser = REPOSITORY.query(new UserByEmailSpecification(email));
            LOGGER.log(Level.DEBUG, "Found : " + listUser.size() + " users, must be 1");
            if (listUser.size() > 0) {
                User u = listUser.get(0);
                u.setPassword(password);
                result = REPOSITORY.update(u);
                EmailSender.sendMail(email, "FCQ-Delivery-Info", "New Password:" + password, props);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean createUser(String email, String fName, String lName, String phone, String role, Properties props) {
        boolean result = false;
        try {
            String pass = PasswordGenerator.generatePassword(email);
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
            User newUser = new User(email, pass, fName, lName, phone, userRole);
            result = REPOSITORY.add(newUser);
            EmailSender.sendMail(newUser.getEmail(), "FCQ-Delivery-Registration", "Password:" + newUser.getPassword(), props);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public User getUserForSession(String email) throws SQLException {
        User u = null;
        List<User> listUser = REPOSITORY.query(new UserByEmailSpecification(email));
        LOGGER.log(Level.DEBUG, "Found : " + listUser.size() + " users, must be 1");
        if (listUser.size() > 0) {
            u = listUser.get(0);
        }
        return u;
    }

    public boolean updateUser(User loginedUser) {
        boolean result = false;
        try {
            result = REPOSITORY.update(loginedUser);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    public List<User> getAllUsers() {
        List<User> users = null;
        try {
            users = REPOSITORY.query(new AllUserSpecification());
            LOGGER.log(Level.DEBUG, "Found : " + users.size());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public boolean deleteUser(String email) {
        boolean result = false;
        User u;
        try {
            u = (User) REPOSITORY.query(new UserByEmailSpecification(email)).get(0);
            result = REPOSITORY.remove(u);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}
