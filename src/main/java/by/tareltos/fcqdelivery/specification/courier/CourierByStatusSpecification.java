package by.tareltos.fcqdelivery.specification.courier;

import by.tareltos.fcqdelivery.specification.SqlSpecification;
/**
 * The class is used to create and return sql query
 *
 * @autor Tarelko Vitali
 * @see by.tareltos.fcqdelivery.specification.SqlSpecification
 */
public class CourierByStatusSpecification implements SqlSpecification {
    /**Parameter stores an query to the database */
    private String query = "SELECT * FROM courier WHERE status = \"%s\" ";
    /**Parameter that will be added in query like status*/
    private String status;
    /**
     * Constructor for creating a new object with certain parameters
     * @param status - current status of courier
     */
    public CourierByStatusSpecification(String status) {
        this.status = status;
    }
    /**
     * @see by.tareltos.fcqdelivery.specification.SqlSpecification
     */
    @Override
    public String toSqlClauses() {
        return String.format(query, status);
    }
}
