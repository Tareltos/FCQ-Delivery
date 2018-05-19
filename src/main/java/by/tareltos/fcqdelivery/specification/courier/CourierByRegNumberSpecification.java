package by.tareltos.fcqdelivery.specification.courier;

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
public class CourierByRegNumberSpecification implements SqlSpecification {
    /**
     * Parameter stores an query to the database
     */
    private String query = "SELECT * FROM courier WHERE car_number = ? ";
    /**
     * Parameter that will be added in query like car_number
     */
    private String carNumber;

    /**
     * Constructor for creating a new object with certain parameters
     *
     * @param carNumber - primary key of courier in database
     */
    public CourierByRegNumberSpecification(String carNumber) {
        this.carNumber = carNumber;
    }

    /**
     * @see by.tareltos.fcqdelivery.specification.SqlSpecification
     */
    public PreparedStatement preparedStatement(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, carNumber);
        return preparedStatement;
    }
}
