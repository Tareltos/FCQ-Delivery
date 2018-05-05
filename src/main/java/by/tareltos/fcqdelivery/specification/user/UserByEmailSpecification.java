package by.tareltos.fcqdelivery.specification.user;

import by.tareltos.fcqdelivery.specification.SqlSpecification;
/**
 * The class is used to create and return sql query
 *
 * @autor Tarelko Vitali
 * @see by.tareltos.fcqdelivery.specification.SqlSpecification
 */
public class UserByEmailSpecification implements SqlSpecification {
    /**
     * Parameter stores an query to the database
     */
    private String query = "SELECT email, password, role, firstName, lastName, phone, status FROM user WHERE email = \"%s\" ";
    /**Parameter that will be added in query like email*/
    private String email;
    /**
     * Constructor for creating a new object with certain parameters
     * @param email - user email
     *
     */
    public UserByEmailSpecification(String email) {
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
