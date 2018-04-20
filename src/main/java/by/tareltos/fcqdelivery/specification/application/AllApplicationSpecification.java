package by.tareltos.fcqdelivery.specification.application;

import by.tareltos.fcqdelivery.specification.SqlSpecification;

public class AllApplicationSpecification implements SqlSpecification {

    private final String SELECT_ALL_QUERY = "SELECT * FROM application ";

    public AllApplicationSpecification() {
    }

    @Override
    public String toSqlClauses() {
        return SELECT_ALL_QUERY;
    }
}
