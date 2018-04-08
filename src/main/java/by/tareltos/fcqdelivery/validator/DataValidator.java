package by.tareltos.fcqdelivery.validator;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DataValidator {

    final static Logger LOGGER = LogManager.getLogger(DataValidator.class);


    public static boolean validateEmail(String email) {
        return (email == null | email.isEmpty()) ? false : true;
    }

    public static boolean validatePassword(String pass) {
        return (pass == null | pass.length() < 5) ? false : true;
    }

    public static boolean validateName(String name) {
        return (name == null | name.isEmpty()) ? false : true;
    }

    public static boolean validatePhone(String phone) {
        return (null == phone | phone.isEmpty() | phone.length() != 13) ? false : true;
    }

    public static boolean validateCarNumber(String carNumber) {
        return (null == carNumber | carNumber.length() != 8) ? false : true;
    }

    public static boolean validateCarProducer(String carProducer) {
        return (null == carProducer) ? false : true;
    }

    public static boolean validateCarModel(String carModel) {
        return (null == carModel | carModel.isEmpty()) ? false : true;
    }

    public static boolean validateTax(double tax) {
        return tax >= 0;
    }

    public static boolean validateCargo(int maxCargo) {
        return maxCargo >= 0;
    }

    public static boolean validateStatus(String status) {
        if ("active".equals(status)) {
            return true;
        }
        if ("blocked".equals(status)) {
            return true;
        }
        return false;

    }
}
