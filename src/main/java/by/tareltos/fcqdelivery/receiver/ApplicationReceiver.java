package by.tareltos.fcqdelivery.receiver;

import by.tareltos.fcqdelivery.entity.application.Application;
import by.tareltos.fcqdelivery.entity.user.UserRole;
import by.tareltos.fcqdelivery.repository.RepositoryException;
import by.tareltos.fcqdelivery.repository.impl.ApplicationRepository;
import by.tareltos.fcqdelivery.specification.impl.AllApplicationSpecification;
import by.tareltos.fcqdelivery.specification.impl.ApplicationByOwnerSpecification;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ApplicationReceiver {

    final static Logger LOGGER = LogManager.getLogger();
    private ApplicationRepository repository = new ApplicationRepository();

    public List<Application> getAllApplications(String email, UserRole role) {
        List<Application> resultList = null;
        try {
            if (role.equals(UserRole.MANAGER)) {
                LOGGER.log(Level.DEBUG, "Application HERE");
                resultList = repository.query(new AllApplicationSpecification());
                LOGGER.log(Level.DEBUG, "Application List size:" + resultList.size());
            }
            else {
                LOGGER.log(Level.DEBUG, "Application HERE111");
                resultList = repository.query(new ApplicationByOwnerSpecification(email));
                LOGGER.log(Level.DEBUG, "Application List size:" + resultList.size());
            }
            return resultList;
        } catch (RepositoryException e) {
            new ReceiverException("Exception in getAllApplication method", e);
        }
        return resultList;
    }

}
