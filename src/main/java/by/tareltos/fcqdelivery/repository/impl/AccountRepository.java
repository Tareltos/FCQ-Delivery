package by.tareltos.fcqdelivery.repository.impl;

import by.tareltos.fcqdelivery.connection.ConnectionPool;
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

public class AccountRepository implements Repository<Account> {

    final static Logger LOGGER = LogManager.getLogger();
    final String ADD_ACCOUNT_QUERY = "INSERT INTO account(card_number, expiration_month, expiration_year, csv, balance, first_name, last_name) VALUES (?,?,?,?,?,?,?) ";
    final String REMOVE_ACCOUNT_QUERY = "DELETE FROM account WHERE card_number=? ";
    final String UPDATE_ACCOUNT_QUERY = "UPDATE account SET expiration_month=?, expiration_year=?, csv=?,  balance=?, first_name=?, last_name=? where card_number=? ";

    @Override
    public boolean add(Account account) throws RepositoryException {
        int executeResult;
        Connection connection = ConnectionPool.getInstance().getConnection();
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
            LOGGER.log(Level.DEBUG, "Execute result in add method: " + executeResult);
            return executeResult == 1 ? true : false;
        } catch (SQLException e) {
            LOGGER.log(Level.WARN, e);
            throw new RepositoryException("SQLException in add method in" + getClass() + "\n" + e, e);
        } finally {
            ConnectionPool.getInstance().freeConnection(connection);
        }
    }

    @Override
    public boolean remove(Account account) throws RepositoryException {
        int executeResult;
        Connection connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement pstm;
        try {
            pstm = connection.prepareStatement(String.format(REMOVE_ACCOUNT_QUERY, account.getCardNumber()));
            executeResult = pstm.executeUpdate();
            LOGGER.log(Level.DEBUG, "Execute result in remove: " + executeResult);
            return executeResult == 1 ? true : false;
        } catch (SQLException e) {
            LOGGER.log(Level.WARN, e);
            throw new RepositoryException("SQLException in remove method \n" + e, e);
        } finally {
            ConnectionPool.getInstance().freeConnection(connection);
        }
    }

    @Override
    public boolean update(Account account) throws RepositoryException {
        int executeResult;
        Connection connection = ConnectionPool.getInstance().getConnection();
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
            LOGGER.log(Level.DEBUG, "Execute result in update method: " + executeResult);
            return executeResult == 1 ? true : false;
        } catch (SQLException e) {
            LOGGER.log(Level.WARN, e);
            throw new RepositoryException("SQLException in update method \n" + e, e);
        } finally {
            ConnectionPool.getInstance().freeConnection(connection);
        }
    }

    @Override
    public List<Account> query(SqlSpecification specification) throws RepositoryException {
        List<Account> accountList = new ArrayList<>();
        Connection connection = ConnectionPool.getInstance().getConnection();
        try {
            PreparedStatement pstm = connection.prepareStatement(specification.toSqlClauses());
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
                LOGGER.log(Level.DEBUG, account.toString());
                accountList.add(account);
            }
            if (accountList.isEmpty()) {
                LOGGER.log(Level.WARN, "Result list is empty!");
            }
            return accountList;
        } catch (SQLException e) {
            LOGGER.log(Level.WARN, e);
            throw new RepositoryException("Exception in query method \n" + e, e);
        } finally {
            ConnectionPool.getInstance().freeConnection(connection);
        }
    }

}
