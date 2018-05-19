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
public class PaginationCourierByStatusSpecification implements SqlSpecification {
    /**
     * Parameter stores an query to the database
     */
    private final String query = "SELECT * FROM courier WHERE status = ? ORDER BY max_cargo LIMIT ? OFFSET ?";
    /**
     * Parameter that will be added in query like status
     */
    private String status;
    /**
     * Parameter that will be added in query like OFFSET
     */
    private int firstRow;
    /**
     * Parameter that will be added in query like LIMIT
     */
    private int rowCount;

    /**
     * Constructor for creating a new object with certain parameters
     *
     * @param status   - current status of courier
     * @param firstRow - offset in database
     * @param rowCount - limit in database
     */
    public PaginationCourierByStatusSpecification(String status, int firstRow, int rowCount) {
        this.status = status;
        this.firstRow = firstRow;
        this.rowCount = rowCount;
    }

    /**
     * @see by.tareltos.fcqdelivery.specification.SqlSpecification
     */
    public PreparedStatement preparedStatement(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(query);
        preparedStatement.setString(1, status);
        preparedStatement.setInt(2, rowCount);
        preparedStatement.setInt(3, firstRow);
        return preparedStatement;
    }
}
