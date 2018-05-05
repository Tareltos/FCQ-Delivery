package by.tareltos.fcqdelivery.specification.application;

import by.tareltos.fcqdelivery.specification.SqlSpecification;
/**
 * The class is used to create and return sql query
 *
 * @autor Tarelko Vitali
 * @see by.tareltos.fcqdelivery.specification.SqlSpecification
 */
public class ApplicationByIdSpecification implements SqlSpecification {
    /**Parameter stores an query to the database */
    private String query = "SELECT * FROM application a LEFT JOIN user u ON a.user_email = u.email LEFT JOIN courier c ON a.car_number  = c.car_number WHERE id= \"%s\" ";
    /**Parameter that will be added in query like id*/
    private String id;
    /**
     * Constructor for creating a new object with certain parameters
     * @param id - primary key of application in database
     *
     */
    public ApplicationByIdSpecification(String id) {
        this.id = id;
    }
    /**
     * @see by.tareltos.fcqdelivery.specification.SqlSpecification
     */
    @Override
    public String toSqlClauses() {
        return String.format(query, id);
    }
}
