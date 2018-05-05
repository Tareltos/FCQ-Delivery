package by.tareltos.fcqdelivery.specification.courier;

import by.tareltos.fcqdelivery.specification.SqlSpecification;
/**
 * The class is used to create and return sql query
 *
 * @autor Tarelko Vitali
 * @see by.tareltos.fcqdelivery.specification.SqlSpecification
 */
public class PaginationCourierSpecification implements SqlSpecification {
    /**Parameter stores an query to the database */
    private String query = "SELECT * FROM courier contact ORDER BY max_cargo LIMIT %d OFFSET %d";
    /**Parameter that will be added in query like OFFSET*/
    private int firstRow;
    /**Parameter that will be added in query like LIMIT*/
    private int rowCount;
    /**
     * Constructor for creating a new object with certain parameters
     * @param firstRow - offset in database
     * @param rowCount - limit in database
     *
     */
    public PaginationCourierSpecification(int firstRow, int rowCount) {
        this.firstRow = firstRow;
        this.rowCount = rowCount;
    }
    /**
     * @see by.tareltos.fcqdelivery.specification.SqlSpecification
     */
    @Override
    public String toSqlClauses() {
        return String.format(query, rowCount,  firstRow);
    }
}
