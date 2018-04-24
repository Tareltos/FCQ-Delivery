package by.tareltos.fcqdelivery.specification.user;

import by.tareltos.fcqdelivery.specification.SqlSpecification;


public class AllUserSpecification implements SqlSpecification {

    private final String SELECT_ALL_QUERY= "SELECT email, password, role, firstName, lastName, phone, status FROM user ";

    public AllUserSpecification() {
    }

    @Override
    public String toSqlClauses() {
        return SELECT_ALL_QUERY;
    }
}
