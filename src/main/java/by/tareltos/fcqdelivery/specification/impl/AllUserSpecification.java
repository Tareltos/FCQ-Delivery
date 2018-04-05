package by.tareltos.fcqdelivery.specification.impl;

import by.tareltos.fcqdelivery.specification.SqlSpecification;

/**
 * Task 1 Chapter A
 * Created by Vitali Tarelko on 26.03.2018.
 * tareltos@gmail.com; skype: tareltos
 */
public class AllUserSpecification implements SqlSpecification {

    private final String SELECT_ALL_QUERY= "SELECT email, password, role, firstName, lastName, phone, status FROM user ";

    public AllUserSpecification() {
    }

    @Override
    public String toSqlClauses() {
        return SELECT_ALL_QUERY;
    }
}
