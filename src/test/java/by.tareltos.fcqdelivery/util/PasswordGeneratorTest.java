package by.tareltos.fcqdelivery.util;

import by.tareltos.fcqdelivery.entity.user.User;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;

public class PasswordGeneratorTest {

    final static Logger LOGGER = LogManager.getLogger();

    @Test(groups = "PasswordUtilTest", priority = 0)
    public void generatePasswordTest() {
        int minLenght = 6;
        int maxLenght = 8;
        String password = PasswordUtil.generatePassword();
        boolean result = (password.length() >= minLenght & password.length() <= maxLenght);
        LOGGER.log(Level.INFO, "PasswordGeneratorTest: Expected: " + true + ", Result: " + result);
        Assert.assertEquals(result, true);
    }

    @Test(groups = "PasswordUtilTest", priority = 0)
    public void clearStringTest() {
        String password = PasswordUtil.generatePassword();
        LOGGER.log(Level.INFO, "Password before clear: " + password);
        PasswordUtil.clearString(password);
        LOGGER.log(Level.INFO, "Password after clear: " + password);
        String expected = "";
        for(int i=0; i < password.length(); i++){
            expected += "*";
        }
        Assert.assertEquals(password, expected);



    }


}
