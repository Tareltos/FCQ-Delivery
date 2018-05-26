package by.tareltos.fcqdelivery.repository.impl;

import by.tareltos.fcqdelivery.dbconnection.ConnectionException;
import by.tareltos.fcqdelivery.dbconnection.ConnectionPool;
import by.tareltos.fcqdelivery.entity.account.Account;
import by.tareltos.fcqdelivery.repository.Repository;
import by.tareltos.fcqdelivery.repository.RepositoryException;
import by.tareltos.fcqdelivery.specification.SqlSpecification;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * The class is used work with the database
 * contains requests to the database and the logger
 *
 * @see by.tareltos.fcqdelivery.repository.Repository
 */
public class AccountRepository implements Repository<Account> {
    /**
     * The logger object, used to write logs
     *
     * @see org.apache.logging.log4j.Logger
     */
    private static final Logger LOGGER = LogManager.getLogger();
    /**
     * Parameter stores an add query to the database
     */
    private static final String ADD_ACCOUNT_QUERY = "INSERT INTO account(card_number, expiration_month, expiration_year, csv, balance, first_name, last_name) VALUES (?,?,?,?,?,?,?) ";
    /**
     * Parameter stores an remove query to the database
     */
    private static final String REMOVE_ACCOUNT_QUERY = "DELETE FROM account WHERE card_number= ? ";
    /**
     * Parameter stores an update query to the database
     */
    private static final String UPDATE_ACCOUNT_QUERY = "UPDATE account SET expiration_month=?, expiration_year=?, csv=?,  balance=?, first_name=?, last_name=? where card_number=? ";

    private static AccountRepository instance = new AccountRepository();

    public static AccountRepository getInstance() {
        return instance;
    }
    private AccountRepository(){
    }

    /**
     * @see by.tareltos.fcqdelivery.repository.Repository
     * @see by.tareltos.fcqdelivery.entity.account.Account
     */
    @Override
    public boolean add(Account account) throws RepositoryException {
        int executeResult;
        Connection connection;
        try {
            connection = ConnectionPool.getInstance().getConnection();
        } catch (ConnectionException e) {
            throw new RepositoryException("ConnectionException in add method" + e.getMessage(), e);
        }
        PreparedStatement pstm;
        try {
            pstm = connection.prepareStatement(ADD_ACCOUNT_QUERY);
            pstm.setString(1, account.getCardNumber());
            pstm.setInt(2, account.getExpirationMonth());
            pstm.setInt(3, account.getExpirationYear());
            pstm.setInt(4, account.getCsv());
            pstm.setDouble(5, account.getBalance());
            pstm.setString(6, account.getFirstName());
            pstm.setString(7, account.getLastName());
            executeResult = pstm.executeUpdate();
            LOGGER.log(Level.INFO, "Execute result in add method: " + executeResult);
            return executeResult == 1 ? true : false;
        } catch (SQLException e) {
            throw new RepositoryException("SQLException in add method in", e);
        } finally {
            try {
                ConnectionPool.getInstance().returnConnection(connection);
            } catch (ConnectionException e) {
                throw new RepositoryException("ConnectionException in add method" + e.getMessage(), e);
            }
        }
    }

    /**
     * @see by.tareltos.fcqdelivery.repository.Repository
     * @see by.tareltos.fcqdelivery.entity.account.Account
     */
    @Override
    public boolean remove(Account account) throws RepositoryException {
        int executeResult;
        Connection connection;
        try {
            connection = ConnectionPool.getInstance().getConnection();
        } catch (ConnectionException e) {
            throw new RepositoryException("ConnectionException in remove method" + e.getMessage(), e);
        }
        PreparedStatement pstm;
        try {
            pstm = connection.prepareStatement(REMOVE_ACCOUNT_QUERY);
            pstm.setString(1, account.getCardNumber());
            executeResult = pstm.executeUpdate();
            LOGGER.log(Level.INFO, "Execute result in remove: " + executeResult);
            return executeResult == 1 ? true : false;
        } catch (SQLException e) {
            throw new RepositoryException("SQLException in remove method", e);
        } finally {
            try {
                ConnectionPool.getInstance().returnConnection(connection);
            } catch (ConnectionException e) {
                throw new RepositoryException("ConnectionException in remove method" + e.getMessage(), e);
            }
        }
    }

    /**
     * @see by.tareltos.fcqdelivery.repository.Repository
     * @see by.tareltos.fcqdelivery.entity.account.Account
     */
    @Override
    public boolean update(Account account) throws RepositoryException {
        int executeResult;
        Connection connection;
        try {
            connection = ConnectionPool.getInstance().getConnection();
        } catch (ConnectionException e) {
            throw new RepositoryException("ConnectionException in update method" + e.getMessage(), e);
        }
        PreparedStatement pstm;
        try {
            pstm = connection.prepareStatement(UPDATE_ACCOUNT_QUERY);
            pstm.setInt(1, account.getExpirationMonth());
            pstm.setInt(2, account.getExpirationYear());
            pstm.setInt(3, account.getCsv());
            pstm.setDouble(4, account.getBalance());
            pstm.setString(5, account.getFirstName());
            pstm.setString(6, account.getLastName());
            pstm.setString(7, account.getCardNumber());
            executeResult = pstm.executeUpdate();
            LOGGER.log(Level.INFO, "Execute result in update method: " + executeResult);
            return executeResult == 1 ? true : false;
        } catch (SQLException e) {
            throw new RepositoryException("SQLException in update method", e);
        } finally {
            try {
                ConnectionPool.getInstance().returnConnection(connection);
            } catch (ConnectionException e) {
                throw new RepositoryException("ConnectionException in update method" + e.getMessage(), e);
            }
        }
    }

    /**
     * @see by.tareltos.fcqdelivery.repository.Repository
     * @see by.tareltos.fcqdelivery.entity.account.Account
     */
    @Override
    public List<Account> query(SqlSpecification specification) throws RepositoryException {
        List<Account> accountList = new ArrayList<>();
        Connection connection;
        try {
            connection = ConnectionPool.getInstance().getConnection();
        } catch (ConnectionException e) {
            throw new RepositoryException("ConnectionException in query method" + e.getMessage(), e);
        }
        try {
            PreparedStatement pstm = specification.preparedStatement(connection);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Account account = new Account();
                account.setCardNumber(rs.getString("card_number"));
                account.setExpirationMonth(rs.getInt("expiration_month"));
                account.setExpirationYear(rs.getInt("expiration_year"));
                account.setCsv(rs.getInt("csv"));
                account.setFirstName(rs.getString("first_name"));
                account.setBalance(rs.getDouble("balance"));
                account.setLastName(rs.getString("last_name"));
                LOGGER.log(Level.INFO, account.toString());
                accountList.add(account);
            }
            if (accountList.isEmpty()) {
                LOGGER.log(Level.INFO, "Result list is empty!");
            }
            return accountList;
        } catch (SQLException e) {
            throw new RepositoryException("Exception in query method ", e);
        } finally {
            try {
                ConnectionPool.getInstance().returnConnection(connection);
            } catch (ConnectionException e) {
                throw new RepositoryException("ConnectionException in query method" + e.getMessage(), e);
            }
        }
    }

}
