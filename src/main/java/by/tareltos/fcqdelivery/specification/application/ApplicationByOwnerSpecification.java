package by.tareltos.fcqdelivery.specification.application;

import by.tareltos.fcqdelivery.specification.SqlSpecification;

public class ApplicationByOwnerSpecification implements SqlSpecification {


    private String query = "SELECT * FROM application a LEFT JOIN user u ON a.user_email = u.email LEFT JOIN courier c ON a.car_number  = c.car_number WHERE user_email = \"%s\" ";
    private String email;

    public ApplicationByOwnerSpecification(String email) {
        this.email = email;
    }

    @Override
    public String toSqlClauses() {
        return String.format(query, email);
    }
}
