package by.tareltos.fcqdelivery.receiver;

import by.tareltos.fcqdelivery.entity.User;
import by.tareltos.fcqdelivery.repository.impl.UserRepository;
import by.tareltos.fcqdelivery.specification.impl.UserByEmailSpecification;
import by.tareltos.fcqdelivery.util.PasswordGenerator;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.sql.SQLException;
import java.util.List;

public class UserReceiver {

    final static Logger LOGGER = LogManager.getLogger(UserReceiver.class);

    public boolean checkUser(String email, String password) {
        boolean result = false;
        UserRepository rep = new UserRepository();
        try {
            List<User> listUser = rep.query(new UserByEmailSpecification(email));
            LOGGER.log(Level.DEBUG, "Found : " + listUser.size() + " users, must be 1");
            User u = listUser.get(0);
            if (email.equals(u.getEmail()) & password.equals(u.getPassword())) {
                result = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean resetUserPassword(String email) {
        boolean result = false;
        UserRepository rep = new UserRepository();
        String password = PasswordGenerator.generatePassword(email);
        try {
            List<User> listUser = rep.query(new UserByEmailSpecification(email));
            LOGGER.log(Level.DEBUG, "Found : " + listUser.size() + " users, must be 1");
            User u = listUser.get(0);
            u.setPassword(password);
            result = rep.update(u);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }
}
