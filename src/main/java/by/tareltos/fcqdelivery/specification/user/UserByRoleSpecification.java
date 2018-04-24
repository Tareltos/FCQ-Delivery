package by.tareltos.fcqdelivery.specification.user;

import by.tareltos.fcqdelivery.specification.SqlSpecification;


public class UserByRoleSpecification implements SqlSpecification {

    private String query = "SELECT email, password, role, firstName, lastName, phone, status FROM user WHERE role = %s ";
    private String role;

    public UserByRoleSpecification(String role) {
        this.role = role;
    }

    @Override
    public String toSqlClauses() {
        return String.format(query, role);
    }
}
