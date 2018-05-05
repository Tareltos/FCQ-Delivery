package by.tareltos.fcqdelivery.util;

import by.tareltos.fcqdelivery.util.IdGenerator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;
import java.math.BigInteger;

public class PasswordGeneratorTest {

    final static Logger LOGGER = LogManager.getLogger();

    @Test
    public void generateTest() {
        BigInteger integer = IdGenerator.generate();
        LOGGER.log(Level.INFO, "Password is: " + integer);
    }

}
