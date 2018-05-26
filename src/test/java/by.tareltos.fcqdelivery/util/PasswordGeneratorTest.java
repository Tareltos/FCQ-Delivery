package by.tareltos.fcqdelivery.util;

import by.tareltos.fcqdelivery.entity.user.User;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.*;

public class PasswordGeneratorTest {

    final static Logger LOGGER = LogManager.getLogger();

    @Test
    public void generateTest() {

        User user = new User();
        user.setEmail("tareltos");
        User to = new User();
  //      to = user;
        LOGGER.log(Level.INFO, "User: " + user.getEmail() + " : " + " to " + to.getEmail());
        to.setEmail("newTareltos");
        LOGGER.log(Level.INFO, "User: " + user.getEmail() + " : " + " to " + to.getEmail());
        user.setEmail("oldTareltos");
        LOGGER.log(Level.INFO, "User: " + user.getEmail() + " : " + " to " + to.getEmail());
        userCheanges(user);
        LOGGER.log(Level.INFO, "After : User: " + user.getEmail() + " : " + " to " + to.getEmail());

    }

    private void userCheanges(User user) {
        user.setEmail(user.getEmail() + ": after method");
    }

}
