package by.tareltos.fcqdelivery.receiver;

import by.tareltos.fcqdelivery.dbconnection.ConnectionException;
import by.tareltos.fcqdelivery.dbconnection.ConnectionPool;
import by.tareltos.fcqdelivery.entity.user.User;
import by.tareltos.fcqdelivery.entity.user.UserRole;
import by.tareltos.fcqdelivery.entity.user.UserStatus;
import by.tareltos.fcqdelivery.repository.RepositoryException;
import by.tareltos.fcqdelivery.repository.impl.UserRepository;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.*;
import java.util.Properties;

public class UserReceiverTest {
    final static Logger LOGGER = LogManager.getLogger();
    UserRepository userRepository = UserRepository.getInstance();
    UserReceiver userReceiver = UserReceiver.getInstance();
    private User testUser = new User("test@mail.com", "12345", "Vitali", "Tarelko", "+375297340877", UserRole.MANAGER, UserStatus.ACTIVE);

    @BeforeMethod
    public void setUp() throws RepositoryException {
        userRepository.add(testUser);
    }

    @AfterMethod
    public void destroy() throws RepositoryException{
        userRepository.remove(testUser);
    }
    @AfterClass
    public void closeConnection() throws ConnectionException {
        ConnectionPool.getInstance().closeAllConnections();
    }

    @Test(groups = "UserReceiverTest", priority = 2)
    public void checkUserStatusTest() throws ReceiverException {
        boolean expected = true;
        boolean result = userReceiver.checkUserStatus(testUser.getEmail());
        LOGGER.log(Level.INFO, "CheckUserStatusTest: Expected: " + expected + ", Result: " + result);
        Assert.assertEquals(result, expected);
    }

    @Test(groups = "UserReceiverTest", priority = 2)
    public void checkUserTest() throws ReceiverException {
        boolean expected = true;
        boolean result = userReceiver.checkUser(testUser.getEmail(), testUser.getPassword());
        LOGGER.log(Level.INFO, "Check user for session: Expected: " + expected + ", Result: " + result);
        Assert.assertEquals(result, expected);
    }

    @Test(groups = "UserReceiverTest", priority = 2)
    public void checkFreeEmailTest() throws ReceiverException {
        boolean expected = false;
        boolean result = userReceiver.checkEmail(testUser.getEmail());
        LOGGER.log(Level.INFO, "Check free email: Expected: " + expected + ", Result: " + result);
        Assert.assertEquals(result, expected);
    }

    @Test(groups = "UserReceiverTest", priority = 2)
    public void getUserForSessionTest() throws ReceiverException {
        String resultUserEmail = "test@mail.com";
        User userForSession = userReceiver.getUserForSession(testUser.getEmail());
        LOGGER.log(Level.INFO, "Get user: Expected: " + testUser.toString() + ", Result: " + userForSession.toString());
        Assert.assertEquals(userForSession.getEmail(), resultUserEmail);

    }

}
