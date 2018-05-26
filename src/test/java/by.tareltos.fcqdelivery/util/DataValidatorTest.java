package by.tareltos.fcqdelivery.util;

import by.tareltos.fcqdelivery.util.DataValidator;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.*;


public class DataValidatorTest {
    final static Logger LOGGER = LogManager.getLogger();
    private String validEmail;
    private String emptyEmail;
    private String validPassword;
    private String shortPassword;
    private String validCSV;
    private String shortCSV;

    @BeforeMethod
    public void init() {
        validEmail = "tareltos@gmail.com";
        emptyEmail = "";
        validPassword = "privet";
        shortPassword = "134";
        validCSV = "651";
        shortCSV = "34";
    }

    @AfterMethod
    public void destroy() {
        validEmail = null;
        emptyEmail = null;
        validPassword = null;
        shortPassword = null;
    }

    @Test(groups = "DataValidatorTest", priority = 1)
    public void validateEmailTrueTest() {
        boolean expected = true;
        boolean result = DataValidator.validateEmail(validEmail);
        LOGGER.log(Level.INFO, "EmailValidatorTest: Expected: " + expected + ", Result: " + result);
        Assert.assertEquals(result, expected);
    }

    @Test(groups = "DataValidatorTest", priority = 1)
    public void validateEmailFalseTest() {
        boolean expected = false;
        boolean result = DataValidator.validateEmail(emptyEmail);
        LOGGER.log(Level.INFO, "EmailValidatorTest: Expected: " + expected + ", Result: " + result);
        Assert.assertEquals(result, expected);
    }

    @Test(groups = "DataValidatorTest", priority = 1)
    public void validatePasswordTrueTest() {
        boolean expected = true;
        boolean result = DataValidator.validatePassword(validPassword);
        LOGGER.log(Level.INFO, "PasswordValidatorTest: Expected: " + expected + ", Result: " + result);
        Assert.assertEquals(result, expected);
    }

    @Test(groups = "DataValidatorTest", priority = 1)
    public void validatePasswordFalseTest() {
        boolean expected = false;
        boolean result = DataValidator.validatePassword(shortPassword);
        LOGGER.log(Level.INFO, "PasswordValidatorTest: Expected: " + expected + ", Result: " + result);
        Assert.assertEquals(result, expected);
    }

    @Test(groups = "DataValidatorTest", priority = 1)
    public void validateCSVTrueTest() {
        boolean expected = true;
        boolean result = DataValidator.validateCsv(validCSV);
        LOGGER.log(Level.INFO, "CSV CodeValidatorTest: Expected: " + expected + ", Result: " + result);
        Assert.assertEquals(result, expected);
    }

    @Test(groups = "DataValidatorTest", priority = 1)
    public void validateCSVFalseTest() {
        boolean expected = false;
        boolean result = DataValidator.validateCsv(shortCSV);
        LOGGER.log(Level.INFO, "CSV CodeValidatorTest: Expected: " + expected + ", Result: " + result);
        Assert.assertEquals(result, expected);
    }


}
