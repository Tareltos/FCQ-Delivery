package by.tareltos.fcqdelivery.repository;

import by.tareltos.fcqdelivery.specification.SqlSpecification;

import java.sql.SQLException;
import java.util.List;

public interface Repository<T> {

    boolean add(T t) throws SQLException;

    boolean remove(T t) throws SQLException;

    boolean update(T t) throws SQLException;

    List<T> query(SqlSpecification specification) throws SQLException;


}
