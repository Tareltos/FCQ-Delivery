package by.tareltos.fcqdelivery.repository.impl;

import by.tareltos.fcqdelivery.entity.User;
import by.tareltos.fcqdelivery.entity.UserRole;
import by.tareltos.fcqdelivery.repository.Repository;
import by.tareltos.fcqdelivery.specification.SqlSpecification;
import by.tareltos.fcqdelivery.util.ConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository implements Repository<User> {
    final static Logger LOGGER = LogManager.getLogger();
    final String ADD_USER_QUERY = "INSERT INTO user(email, password, role, firstName, lastName, phone) VALUES (?,?,?,?,?,?) ";
    final String REMOVE_USER_QUERY = "DELETE FROM user WHERE email=%s ";
    final String UPDATE_USER_QUERY = "UPDATE user SET password =?, firstName=?, lastName=?, role=?, phone=? where email=? ";

    @Override
    public boolean add(User u) throws SQLException, ClassNotFoundException {
        boolean result;
        Connection connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(ADD_USER_QUERY);
        pstm.setString(1, u.getEmail());
        pstm.setString(2, u.getPassword());
        pstm.setString(3, u.getRole().getRole());
        pstm.setString(4, u.getFirstName());
        pstm.setString(5, u.getLastName());
        pstm.setString(6, u.getPhone());
        result = Boolean.valueOf(String.valueOf(pstm.executeUpdate()));
        ConnectionPool.getInstance().freeConnection(connection);
        return result;
    }

    @Override
    public boolean remove(User user) throws SQLException, ClassNotFoundException {
        boolean result = false;
        Connection connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(String.format(REMOVE_USER_QUERY, user.getEmail()));
        result = Boolean.valueOf(String.valueOf(pstm.executeUpdate()));
        ConnectionPool.getInstance().freeConnection(connection);
        return result;
    }

    @Override
    public boolean update(User user) throws SQLException, ClassNotFoundException {
        boolean result;
        Connection connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(UPDATE_USER_QUERY);
        pstm.setString(1, user.getPassword());
        pstm.setString(2, user.getRole().getRole());
        pstm.setString(3, user.getFirstName());
        pstm.setString(4, user.getLastName());
        pstm.setString(5, user.getPhone());
        pstm.setString(6, user.getEmail());
        result = Boolean.valueOf(String.valueOf(pstm.executeUpdate()));
        ConnectionPool.getInstance().freeConnection(connection);
        return result;
    }

    @Override
    public List query(SqlSpecification specification) throws SQLException, ClassNotFoundException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(specification.toSqlClauses());
        ResultSet rs = pstm.executeQuery();
        List<User> userList = new ArrayList<User>();
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
            userList.add(user);
        }
        if (userList.isEmpty()) {
            LOGGER.log(Level.WARN, "Result list is empty!");
        }
        ConnectionPool.getInstance().freeConnection(connection);
        return userList;
    }
}
