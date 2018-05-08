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
     * Parameter used to identify current user status
     */
    private static final String ACTIVE_USER_STATUS = "active";

    /**
     * Method is used to check user status
     *
     * @param email -primary key of user in database
     * @return true if user status is active. Otherwise false
     * @throws ReceiverException if a RepositoryException was caught and if user not found
     * @see by.tareltos.fcqdelivery.entity.user.User
     */
    public static boolean checkUserStatus(String email, UserRepository userRepository) throws ReceiverException {
        try {
            List<User> listUser = userRepository.query(new UserByEmailSpecification(email));
            if (listUser.isEmpty()) {
                throw new ReceiverException("User not found");
            }
            if (listUser.size() > 1) {
                throw new ReceiverException("Count of users " + listUser.size() + " must be 1");
            }
            User u = listUser.get(0);
            if (ACTIVE_USER_STATUS.equals(u.getStatus().getStatus())) {
                return true;
            }
            return false;
        } catch (RepositoryException e) {
            throw new ReceiverException("Exception in checkUserStatus", e);
        }
    }
}
