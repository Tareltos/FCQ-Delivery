package by.tareltos.fcqdelivery.specification.courier;

import by.tareltos.fcqdelivery.specification.SqlSpecification;

public class PaginationCourierSpecification implements SqlSpecification {

    private final String SELECT_QUERY = "SELECT * FROM courier contact ORDER BY max_cargo LIMIT %d OFFSET %d";
    private int firstrow;
    private int rowcount;


    public PaginationCourierSpecification(int firstrow, int rowcount) {
        this.firstrow = firstrow;
        this.rowcount = rowcount;
    }

    @Override
    public String toSqlClauses() {
        return String.format(SELECT_QUERY, rowcount,  firstrow);
    }
}
