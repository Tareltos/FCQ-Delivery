package by.tareltos.fcqdelivery.specification.impl;

import by.tareltos.fcqdelivery.specification.SqlSpecification;

public class ApplicationByOwnerSpecification implements SqlSpecification {


    private String query = "SELECT * FROM application WHERE user_email = \"%s\" ";
    private String email;

    public ApplicationByOwnerSpecification(String email) {
        this.email = email;
    }

    @Override
    public String toSqlClauses() {
        return String.format(query, email);
    }
}
