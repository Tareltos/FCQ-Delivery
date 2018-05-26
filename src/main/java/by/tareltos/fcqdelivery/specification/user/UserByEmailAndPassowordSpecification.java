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
public class UserByEmailAndPassowordSpecification implements SqlSpecification {
    /**
     * Parameter stores an query to the database
     */
    private String query = "SELECT email, role, firstName, lastName, phone, status FROM user WHERE email =? AND password= ?";
    /**
     * Parameter that will be added in query like email
     */
    private String email;
    private String password;

    /**
     * Constructor for creating a new object with certain parameters
     *
     * @param email - user email
     */
    public UserByEmailAndPassowordSpecification(String email, String password) {
        this.email = email;
        this.password = password;
    }

    /**
     * @see by.tareltos.fcqdelivery.specification.SqlSpecification
     */
    @Override
    public PreparedStatement preparedStatement(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, email);
        preparedStatement.setString(2, password);
        return preparedStatement;
    }
}