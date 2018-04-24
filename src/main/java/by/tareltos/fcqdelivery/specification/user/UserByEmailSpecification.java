package by.tareltos.fcqdelivery.specification.user;

import by.tareltos.fcqdelivery.specification.SqlSpecification;


public class UserByEmailSpecification implements SqlSpecification {

    private String query = "SELECT email, password, role, firstName, lastName, phone, status FROM user WHERE email = \"%s\" ";
    private String email;

    public UserByEmailSpecification(String email) {
        this.email = email;
    }

    @Override
    public String toSqlClauses() {
        return String.format(query, email);
    }
}
