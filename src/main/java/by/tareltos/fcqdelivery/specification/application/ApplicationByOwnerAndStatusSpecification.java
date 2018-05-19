package by.tareltos.fcqdelivery.specification.application;

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
public class ApplicationByOwnerAndStatusSpecification implements SqlSpecification {
    /**
     * Parameter stores an query to the database
     */
    private String query = "SELECT * FROM application a LEFT JOIN user u ON a.user_email = u.email LEFT JOIN courier c ON a.car_number  = c.car_number WHERE user_email =? and app_status =? ";
    /**
     * Parameter that will be added in query like user_email
     */
    private String email;
    /**
     * Parameter that will be added in query like app_status
     */
    private String status;

    /**
     * Constructor for creating a new object with certain parameters
     *
     * @param email  - email of customer
     * @param status - current status of application
     */
    public ApplicationByOwnerAndStatusSpecification(String email, String status) {
        this.email = email;
        this.status = status;
    }

    /**
     * @see by.tareltos.fcqdelivery.specification.SqlSpecification
     */
    @Override
    public PreparedStatement preparedStatement(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, email);
        preparedStatement.setString(2, status);
        return preparedStatement;
    }
}
