package by.tareltos.fcqdelivery.specification.impl;

import by.tareltos.fcqdelivery.specification.SqlSpecification;

public class ApplicationByIdSpecification implements SqlSpecification {

    private String query = "SELECT * FROM application WHERE id= \"%s\" ";
    private String id;

    public ApplicationByIdSpecification(String id) {
        this.id = id;
    }

    @Override
    public String toSqlClauses() {
        return String.format(query, id);
    }
}
