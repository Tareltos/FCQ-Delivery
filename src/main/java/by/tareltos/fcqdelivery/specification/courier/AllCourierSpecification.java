package by.tareltos.fcqdelivery.specification.courier;

import by.tareltos.fcqdelivery.specification.SqlSpecification;

public class AllCourierSpecification implements SqlSpecification {

    private final String SELECT_ALL_QUERY = "SELECT * FROM courier ";

    public AllCourierSpecification() {
    }

    @Override
    public String toSqlClauses() {
        return SELECT_ALL_QUERY;
    }
}
