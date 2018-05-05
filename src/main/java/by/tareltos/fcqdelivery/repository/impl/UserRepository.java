package by.tareltos.fcqdelivery.repository.impl;

import by.tareltos.fcqdelivery.entity.user.User;
import by.tareltos.fcqdelivery.entity.user.UserRole;
import by.tareltos.fcqdelivery.entity.user.UserStatus;
import by.tareltos.fcqdelivery.repository.Repository;
import by.tareltos.fcqdelivery.repository.RepositoryException;
import by.tareltos.fcqdelivery.specification.SqlSpecification;
import by.tareltos.fcqdelivery.connection.ConnectionPool;
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
public class UserRepository implements Repository<User> {
    /**
     * The logger object, used to write logs
     *
     * @see org.apache.logging.log4j.Logger
     */
    private static final Logger LOGGER = LogManager.getLogger();
    /**Parameter stores an add query to the database */
    private static final String ADD_USER_QUERY = "INSERT INTO user(email, password, role, firstName, lastName, phone, status) VALUES (?,?,?,?,?,?,?) ";
    /**Parameter stores an remove query to the database */
    private static final String REMOVE_USER_QUERY = "DELETE FROM user WHERE email=\"%s\" ";
    /**Parameter stores an update query to the database */
    private static final String UPDATE_USER_QUERY = "UPDATE user SET password =?, firstName=?, lastName=?, role=?, phone=?, status=? where email=? ";

    /**
     * @see by.tareltos.fcqdelivery.repository.Repository
     * @see by.tareltos.fcqdelivery.entity.user.User
     */
    @Override
    public boolean add(User u) throws RepositoryException {
        int executeResult;
        Connection connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement pstm;
        try {
            pstm = connection.prepareStatement(ADD_USER_QUERY);
            pstm.setString(1, u.getEmail());
            pstm.setString(2, u.getPassword());
            pstm.setString(3, u.getRole().getRole());
            pstm.setString(4, u.getFirstName());
            pstm.setString(5, u.getLastName());
            pstm.setString(6, u.getPhone());
            pstm.setString(7, u.getStatus().getStatus());
            executeResult = pstm.executeUpdate();
            LOGGER.log(Level.INFO, "Execute result in add method: " + executeResult);
            return executeResult == 1 ? true : false;
        } catch (SQLException e) {
            throw new RepositoryException("SQLException in add method ", e);
        } finally {
            ConnectionPool.getInstance().freeConnection(connection);
        }
    }

    /**
     * @see by.tareltos.fcqdelivery.repository.Repository
     * @see by.tareltos.fcqdelivery.entity.user.User
     */
    @Override
    public boolean remove(User user) throws RepositoryException {
        int executeResult;
        Connection connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement pstm;
        try {
            pstm = connection.prepareStatement(String.format(REMOVE_USER_QUERY, user.getEmail()));
            executeResult = pstm.executeUpdate();
            LOGGER.log(Level.INFO, "Execute result in remove: " + executeResult);
            return executeResult == 1 ? true : false;
        } catch (SQLException e) {
            throw new RepositoryException("SQLException in remove method", e);
        } finally {
            ConnectionPool.getInstance().freeConnection(connection);
        }
    }

    /**
     * @see by.tareltos.fcqdelivery.repository.Repository
     * @see by.tareltos.fcqdelivery.entity.user.User
     */
    @Override
    public boolean update(User user) throws RepositoryException {
        int executeResult;
        Connection connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement pstm;
        try {
            pstm = connection.prepareStatement(UPDATE_USER_QUERY);
            pstm.setString(1, user.getPassword());
            pstm.setString(2, user.getFirstName());
            pstm.setString(3, user.getLastName());
            pstm.setString(4, user.getRole().getRole());
            pstm.setString(5, user.getPhone());
            pstm.setString(6, user.getStatus().getStatus());
            pstm.setString(7, user.getEmail());
            executeResult = pstm.executeUpdate();
            LOGGER.log(Level.INFO, "Execute result in update method: " + executeResult);
            return executeResult == 1 ? true : false;
        } catch (SQLException e) {
            throw new RepositoryException("SQLException in update method", e);
        } finally {
            ConnectionPool.getInstance().freeConnection(connection);
        }
    }

    /**
     * @see by.tareltos.fcqdelivery.repository.Repository
     * @see by.tareltos.fcqdelivery.entity.user.User
     */
    @Override
    public List query(SqlSpecification specification) throws RepositoryException {
        List<User> userList = new ArrayList<>();
        Connection connection = ConnectionPool.getInstance().getConnection();
        try {
            PreparedStatement pstm = connection.prepareStatement(specification.toSqlClauses());
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setEmail(rs.getString("email"));
                user.setPassword(rs.getString("password"));
                user.setFirstName(rs.getString("firstName"));
                user.setLastName(rs.getString("lastName"));
                user.setPhone(rs.getString("phone"));
                String role = rs.getString("role");
                switch (role) {
                    case "admin":
                        user.setRole(UserRole.ADMIN);
                        break;
                    case "manager":
                        user.setRole(UserRole.MANAGER);
                        break;
                    case "customer":
                        user.setRole(UserRole.CUSTOMER);
                }
                String status = rs.getString("status");
                switch (status) {
                    case "active":
                        user.setStatus(UserStatus.ACTIVE);
                        break;
                    case "blocked":
                        user.setStatus(UserStatus.BLOCKED);
                        break;
                }
                LOGGER.log(Level.INFO, user.toString());
                userList.add(user);
            }
            if (userList.isEmpty()) {
                LOGGER.log(Level.INFO, "Result list is empty!");
            }
            return userList;
        } catch (SQLException e) {
            throw new RepositoryException("Exception in query method", e);
        } finally {
            ConnectionPool.getInstance().freeConnection(connection);
        }
    }
}
