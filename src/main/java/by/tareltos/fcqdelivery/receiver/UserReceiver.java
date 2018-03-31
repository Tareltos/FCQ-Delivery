package by.tareltos.fcqdelivery.receiver;

import by.tareltos.fcqdelivery.entity.User;
import by.tareltos.fcqdelivery.repository.impl.UserRepository;
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
        } catch (ClassNotFoundException e) {
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
        } catch (ClassNotFoundException e) {
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
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean createUser(User newUser, Properties props) {
        boolean result = false;
        try {
            result = REPOSITORY.add(newUser);
            EmailSender.sendMail(newUser.getEmail(), "FCQ-Delivery-Registration", "Password:" + newUser.getPassword(), props);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    public User getUserForSession(String email) throws SQLException, ClassNotFoundException {
        User u = null;
        UserRepository rep = new UserRepository();
        List<User> listUser = rep.query(new UserByEmailSpecification(email));
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
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }
}
