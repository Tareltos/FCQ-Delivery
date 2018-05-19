package by.tareltos.fcqdelivery.specification;

import by.tareltos.fcqdelivery.repository.RepositoryException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * The interface is used to create and return preparedStatment for query
 *
 * @autor Tarelko Vitali
 */
public interface SqlSpecification {

    /**
     * return preparedStatment for repository
     *
     * @return preparedStatment with parameters
     */
    PreparedStatement preparedStatement(Connection connection) throws SQLException;

}
