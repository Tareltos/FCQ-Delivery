package by.tareltos.fcqdelivery.specification.courier;

import by.tareltos.fcqdelivery.specification.SqlSpecification;

public class PaginationCourierByStatusSpecification implements SqlSpecification {

    private final String SELECT_QUERY = "SELECT * FROM courier WHERE status = \"%s\" ORDER BY max_cargo LIMIT %d OFFSET %d";
    private String status;
    private int firstRow;
    private int rowCount;


    public PaginationCourierByStatusSpecification(String status, int firstRow, int rowCount) {
        this.status = status;
        this.firstRow = firstRow;
        this.rowCount = rowCount;
    }

    @Override
    public String toSqlClauses() {
        return String.format(SELECT_QUERY, status, rowCount, firstRow);
    }
}
