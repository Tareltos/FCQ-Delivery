package by.tareltos.fcqdelivery.specification.impl;

import by.tareltos.fcqdelivery.specification.SqlSpecification;

/**
 * Task 1 Chapter A
 * Created by Vitali Tarelko on 26.03.2018.
 * tareltos@gmail.com; skype: tareltos
 */
public class UserByRoleSpecification implements SqlSpecification {

    private String query = "SELECT email, password, role, firstName, lastName, phone, status FROM user WHERE role = %s ";
    private String role;

    public UserByRoleSpecification(String role) {
        this.role = role;
    }

    @Override
    public String toSqlClauses() {
        return String.format(query, role);
    }
}
