package by.tareltos.fcqdelivery.specification.courier;

import by.tareltos.fcqdelivery.specification.SqlSpecification;

public class CourierByStatusSpecification implements SqlSpecification {

    private final String SELECT_QUERY = "SELECT * FROM courier WHERE status = \"%s\" ";
    private String status;


    public CourierByStatusSpecification(String status) {
        this.status = status;
    }

    @Override
    public String toSqlClauses() {
        return String.format(SELECT_QUERY, status);
    }
}
