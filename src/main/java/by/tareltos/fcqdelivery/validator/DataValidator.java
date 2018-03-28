package by.tareltos.fcqdelivery.validator;

import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class DataValidator {

    final static Logger LOGGER = LogManager.getLogger(DataValidator.class);


    public static boolean validateEmail(String email) {
        if (email == null | email.isEmpty()) {
            LOGGER.log(Level.DEBUG, "Email is null");
            return false;
        }
        return true;
    }

    public static boolean validatePassword(String pass) {
        if (pass == null | pass.length() < 5) {
            return false;
        }
        return true;
    }
    public static boolean validateName(String name){
        if (name == null | name.isEmpty()) {
            LOGGER.log(Level.DEBUG, "Name is null");
            return false;
        }
        return true;
    }
    public static boolean validatePhone(String phone){
        if (phone == null | phone.isEmpty()| phone.length()!=13) {
            LOGGER.log(Level.DEBUG, "Phone is not valid");
            return false;
        }
        return true;
    }



}
