package by.tareltos.fcqdelivery.specification.user;

import by.tareltos.fcqdelivery.specification.SqlSpecification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * The class is used to create and return sql query
 *
 * @autor Tarelko Vitali
 * @see by.tareltos.fcqdelivery.specification.SqlSpecification
 */
public class UserByRoleSpecification implements SqlSpecification {
    /**
     * Parameter stores an query to the database
     */
    private String query = "SELECT email, password, role, firstName, lastName, phone, status FROM user WHERE role = ? ";
    /**Parameter that will be added in query like role*/
    private String role;
    /**
     * Constructor for creating a new object with certain parameters
     * @param role - user role
     *      *
     */
    public UserByRoleSpecification(String role) {
        this.role = role;
    }
    /**
     * @see by.tareltos.fcqdelivery.specification.SqlSpecification
     */
    @Override
    public PreparedStatement preparedStatement(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1,role);
        return preparedStatement;
    }
}
