package by.tareltos.fcqdelivery.receiver;

import by.tareltos.fcqdelivery.entity.user.User;
import by.tareltos.fcqdelivery.repository.RepositoryException;
import by.tareltos.fcqdelivery.repository.impl.UserRepository;
import by.tareltos.fcqdelivery.specification.user.UserByEmailSpecification;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class CheckUserStatusUtil {

    /**
     * Method is used to check user status
     *
     * @param email -primary key of user in database
     * @return true if user status is active. Otherwise false
     * @throws ReceiverException if a RepositoryException was caught and if user not found
     * @see by.tareltos.fcqdelivery.entity.user.User
     */
    public static boolean checkUserStatus(String email, UserRepository userRepository, Logger logger, String activeUserStatus) throws ReceiverException {
        try {
            List<User> listUser = userRepository.query(new UserByEmailSpecification(email));
            if (listUser.isEmpty()) {
                throw new ReceiverException("User not found");
            }
            if (listUser.size() > 1) {
                throw new ReceiverException("Count of users " + listUser.size() + " must be 1");
            }
            logger.log(Level.DEBUG, "Found : " + listUser.size() + " users, must be 1");
            User u = listUser.get(0);
            if (activeUserStatus.equals(u.getStatus().getStatus())) {
                logger.log(Level.DEBUG, "User status:" + u.getEmail() + " is active");
                return true;
            }
            return false;
        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in checkUserStatus", e);
        }
    }
}
