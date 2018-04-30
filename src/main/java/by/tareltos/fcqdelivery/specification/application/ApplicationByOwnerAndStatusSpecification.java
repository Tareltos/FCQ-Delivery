package by.tareltos.fcqdelivery.specification.application;

import by.tareltos.fcqdelivery.specification.SqlSpecification;

public class ApplicationByOwnerAndStatusSpecification implements SqlSpecification {


    private String query = "SELECT * FROM application WHERE user_email = \"%s\" and app_status = \"%s\" ";
    private String email;
    private String status;

    public ApplicationByOwnerAndStatusSpecification(String email, String status) {
        this.email = email;
        this.status = status;
    }

    @Override
    public String toSqlClauses() {
        return String.format(query, email, status);
    }
}
