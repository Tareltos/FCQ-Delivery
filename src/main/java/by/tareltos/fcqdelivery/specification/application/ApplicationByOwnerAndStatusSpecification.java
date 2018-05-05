package by.tareltos.fcqdelivery.specification.application;

import by.tareltos.fcqdelivery.specification.SqlSpecification;

public class ApplicationByOwnerAndStatusSpecification implements SqlSpecification {


    private String query = "SELECT * FROM application a LEFT JOIN user u ON a.user_email = u.email LEFT JOIN courier c ON a.car_number  = c.car_number WHERE user_email = \"%s\" and app_status = \"%s\" ";
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
