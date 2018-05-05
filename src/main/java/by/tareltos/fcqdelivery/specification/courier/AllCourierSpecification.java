package by.tareltos.fcqdelivery.specification.courier;

import by.tareltos.fcqdelivery.specification.SqlSpecification;
/**
 * The class is used to create and return sql query
 *
 * @autor Tarelko Vitali
 * @see by.tareltos.fcqdelivery.specification.SqlSpecification
 */
public class AllCourierSpecification implements SqlSpecification {

    /**
     * Parameter stores an query to the database
     */
    private String allUser = "SELECT * FROM courier ";
    /**
     * @see by.tareltos.fcqdelivery.specification.SqlSpecification
     */
    @Override
    public String toSqlClauses() {
        return allUser;
    }
}
