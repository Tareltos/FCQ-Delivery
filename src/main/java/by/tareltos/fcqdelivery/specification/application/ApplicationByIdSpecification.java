package by.tareltos.fcqdelivery.specification.application;

import by.tareltos.fcqdelivery.specification.SqlSpecification;

public class ApplicationByIdSpecification implements SqlSpecification {

    private String query = "SELECT * FROM application a LEFT JOIN user u ON a.user_email = u.email LEFT JOIN courier c ON a.car_number  = c.car_number WHERE id= \"%s\" ";
    private String id;

    public ApplicationByIdSpecification(String id) {
        this.id = id;
    }

    @Override
    public String toSqlClauses() {
        return String.format(query, id);
    }
}
