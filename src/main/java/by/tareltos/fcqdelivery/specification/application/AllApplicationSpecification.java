package by.tareltos.fcqdelivery.specification.application;

import by.tareltos.fcqdelivery.specification.SqlSpecification;

public class AllApplicationSpecification implements SqlSpecification {

    private final String SELECT_ALL_QUERY = "SELECT * FROM application a LEFT JOIN user u ON a.user_email = u.email LEFT JOIN courier c ON a.car_number  = c.car_number";

    public AllApplicationSpecification() {
    }

    @Override
    public String toSqlClauses() {
        return SELECT_ALL_QUERY;
    }
}
