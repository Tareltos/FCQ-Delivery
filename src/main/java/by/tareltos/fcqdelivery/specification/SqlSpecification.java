package by.tareltos.fcqdelivery.specification;

import by.tareltos.fcqdelivery.repository.RepositoryException;

/**
 * The interface is used to create and return sql query
 *
 * @autor Tarelko Vitali
 */
public interface SqlSpecification {

    /**
     * Create sql query for repository
     *
     * @return query string to the database
     */
    String toSqlClauses();

}
