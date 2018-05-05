package by.tareltos.fcqdelivery.specification.application;

import by.tareltos.fcqdelivery.specification.SqlSpecification;
/**
 * The class is used to create and return sql query
 *
 * @autor Tarelko Vitali
 * @see by.tareltos.fcqdelivery.specification.SqlSpecification
 */
public class ApplicationByOwnerSpecification implements SqlSpecification {
    /**Parameter stores an query to the database */
    private String query = "SELECT * FROM application a LEFT JOIN user u ON a.user_email = u.email LEFT JOIN courier c ON a.car_number  = c.car_number WHERE user_email = \"%s\" ";
    /**Parameter that will be added in query like user_email*/
    private String email;
    /**
     * Constructor for creating a new object with certain parameters
     * @param email - email of customer
     */
    public ApplicationByOwnerSpecification(String email) {
        this.email = email;
    }
    /**
     * @see by.tareltos.fcqdelivery.specification.SqlSpecification
     */
    @Override
    public String toSqlClauses() {
        return String.format(query, email);
    }
}
