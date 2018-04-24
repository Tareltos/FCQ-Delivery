package by.tareltos.fcqdelivery.specification.courier;

import by.tareltos.fcqdelivery.specification.SqlSpecification;

public class PaginationCourierSpecification implements SqlSpecification {

    private final String SELECT_QUERY = "SELECT * FROM courier contact ORDER BY max_cargo LIMIT %d OFFSET %d";
    private int firstRow;
    private int rowCount;


    public PaginationCourierSpecification(int firstRow, int rowCount) {
        this.firstRow = firstRow;
        this.rowCount = rowCount;
    }

    @Override
    public String toSqlClauses() {
        return String.format(SELECT_QUERY, rowCount,  firstRow);
    }
}
