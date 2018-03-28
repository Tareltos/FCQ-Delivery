package by.tareltos.fcqdelivery.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Random;

public class PasswordGenerator {

    final static Logger LOGGER = LogManager.getLogger();

    public static String generatePassword(String mail) {
        String password = String.valueOf(mail.hashCode() + mail.toCharArray()[0]) + "psw";
        LOGGER.log(Level.DEBUG, "Generated Password: " + password);
        return password;

    }
}
