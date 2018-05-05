package by.tareltos.fcqdelivery.specification.user;

import by.tareltos.fcqdelivery.specification.SqlSpecification;
/**
 * The class is used to create and return sql query
 *
 * @autor Tarelko Vitali
 * @see by.tareltos.fcqdelivery.specification.SqlSpecification
 */
public class AllUserSpecification implements SqlSpecification {
    /**
     * Parameter stores an query to the database
     */
    private final String query = "SELECT email, password, role, firstName, lastName, phone, status FROM user ";

    /**
     * @see by.tareltos.fcqdelivery.specification.SqlSpecification
     */
    @Override
    public String toSqlClauses() {
        return query;
    }
}
