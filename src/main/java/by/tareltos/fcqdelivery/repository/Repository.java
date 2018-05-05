package by.tareltos.fcqdelivery.repository;

import by.tareltos.fcqdelivery.specification.SqlSpecification;

import java.util.List;

/**
 * The interface is used to work with the database
 *
 * @autor Tarelko Vitali
 */
public interface Repository<T> {

    /**
     * Adds a new entry to the database
     *
     * @param t is an object that will be written to the database
     * @return true if the record is successful. otherwise false
     * @throws RepositoryException if a SQLException was caught
     */
    boolean add(T t) throws RepositoryException;

    /**
     * Removes an entry from the database
     *
     * @param t is an object that will be removed from the database
     * @return true if the deletion was successful. otherwise false
     * @throws RepositoryException if a SQLException was caught
     */
    boolean remove(T t) throws RepositoryException;

    /**
     * Updates an existing entry in the database
     *
     * @param t is an object that will be updated in the database
     * @return true if the update is successful. otherwise false
     * @throws RepositoryException if a SQLException was caught
     */
    boolean update(T t) throws RepositoryException;

    /**
     * Returns a list of objects from the database
     *
     * @param specification specification object which contains a query to the database
     * @return a list of objects matching the query
     * @throws RepositoryException if a SQLException was caught
     * @see by.tareltos.fcqdelivery.specification.SqlSpecification
     */
    List<T> query(SqlSpecification specification) throws RepositoryException;


}
