package by.tareltos.fcqdelivery.specification.application;

import by.tareltos.fcqdelivery.specification.SqlSpecification;
/**
 * The class is used to create and return sql query
 *
 * @autor Tarelko Vitali
 * @see by.tareltos.fcqdelivery.specification.SqlSpecification
 */
public class AllApplicationSpecification implements SqlSpecification {
    /**
     * Parameter stores an query to the database
     */
    private String selectAllQuery = "SELECT * FROM application a LEFT JOIN user u ON a.user_email = u.email LEFT JOIN courier c ON a.car_number  = c.car_number";

    /**
     * @see by.tareltos.fcqdelivery.specification.SqlSpecification
     */
    @Override
    public String toSqlClauses() {
        return selectAllQuery;
    }
}
