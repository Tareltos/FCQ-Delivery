package by.tareltos.fcqdelivery.repository.impl;

import by.tareltos.fcqdelivery.entity.Courier;
import by.tareltos.fcqdelivery.repository.Repository;
import by.tareltos.fcqdelivery.specification.SqlSpecification;

import java.sql.SQLException;
import java.util.List;

/**
 * Task 1 Chapter A
 * Created by Vitali Tarelko on 26.03.2018.
 * tareltos@gmail.com; skype: tareltos
 */
public class CourierRepository implements Repository<Courier> {

    @Override
    public boolean add(Courier courier) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean remove(Courier courier) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public boolean update(Courier courier) throws SQLException, ClassNotFoundException {
        return false;
    }

    @Override
    public List query(SqlSpecification specification) throws SQLException, ClassNotFoundException {
        return null;
    }
}
