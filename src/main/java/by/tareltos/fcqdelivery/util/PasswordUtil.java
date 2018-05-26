package by.tareltos.fcqdelivery.util;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Random;

public class PasswordUtil {
    /**
     * The logger object, used to write logs
     *
     * @see org.apache.logging.log4j.Logger
     */
    private static final Logger LOGGER = LogManager.getLogger();

    public static String generatePassword() {

        String password ="pass"+ new Random().nextInt(200)+"n";
        LOGGER.log(Level.DEBUG, "Generated Password: " + password);
        return password;
    }

    public static void clearString(String password) {
        try {
            Field value = String.class.getDeclaredField("value");
            value.setAccessible(true);
            char[] chars = (char[]) value.get(password);
            LOGGER.log(Level.DEBUG, "Password before: " + chars[0]);
            Arrays.fill(chars, '*');
            LOGGER.log(Level.DEBUG, "Password after: " + chars[0]);

        } catch (Exception e) {
            throw new AssertionError(e);
        }
    }
}
