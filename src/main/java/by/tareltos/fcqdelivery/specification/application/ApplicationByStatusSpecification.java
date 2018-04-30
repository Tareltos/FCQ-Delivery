package by.tareltos.fcqdelivery.specification.application;

import by.tareltos.fcqdelivery.specification.SqlSpecification;

public class ApplicationByStatusSpecification implements SqlSpecification {


    private String query = "SELECT * FROM application WHERE app_status = \"%s\" ";
    private String status;

    public ApplicationByStatusSpecification(String status) {

        this.status = status;
    }

    @Override
    public String toSqlClauses() {
        return String.format(query, status);
    }
}
