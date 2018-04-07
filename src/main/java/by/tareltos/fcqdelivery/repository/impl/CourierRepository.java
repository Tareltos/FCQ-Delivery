package by.tareltos.fcqdelivery.repository.impl;

import by.tareltos.fcqdelivery.entity.Courier;
import by.tareltos.fcqdelivery.entity.CourierStatus;
import by.tareltos.fcqdelivery.repository.Repository;
import by.tareltos.fcqdelivery.specification.SqlSpecification;
import by.tareltos.fcqdelivery.connection.ConnectionPool;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Task 1 Chapter A
 * Created by Vitali Tarelko on 26.03.2018.
 * tareltos@gmail.com; skype: tareltos
 */
public class CourierRepository implements Repository<Courier> {

    final static Logger LOGGER = LogManager.getLogger();
    final String ADD_COURIER_QUERY = "INSERT INTO courier(email, password, role, firstName, lastName, phone) VALUES (?,?,?,?,?,?) ";
    final String REMOVE_COURIER_QUERY = "DELETE FROM user WHERE email=\"%s\" ";
    final String UPDATE_COURIER_QUERY = "UPDATE user SET password =?, firstName=?, lastName=?, role=?, phone=? where email=? ";

    @Override
    public boolean add(Courier courier) throws SQLException {
        return false;
    }

    @Override
    public boolean remove(Courier courier) throws SQLException {
        return false;
    }

    @Override
    public boolean update(Courier courier) throws SQLException {
        return false;
    }

    @Override
    public List query(SqlSpecification specification) throws SQLException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(specification.toSqlClauses());
        ResultSet rs = pstm.executeQuery();
        List<Courier> courierList = new ArrayList<>();
        while (rs.next()) {
            String car_numder = rs.getString("car_number");
            String car_producer = rs.getString("car_producer");
            String car_model = rs.getString("car_model");
            byte[] car_photo = rs.getBytes("car_photo");
            String driver_phone = rs.getString("driver_phone");
            String driver_name = rs.getString("driver_name");
            String driver_email = rs.getString("driver_email");
            int max_cargo = rs.getInt("max_cargo");
            double km_tax = rs.getDouble("km_tax");
            String status = rs.getString("status");
            CourierStatus courierStatus = null;
            switch (status) {
                case "active":
                    courierStatus = CourierStatus.ACTIVE;
                    break;
                case "blocked":
                    courierStatus = CourierStatus.BLOCKED;
                    break;

            }
            Courier courier = new Courier(car_numder, car_producer, car_model, driver_phone, driver_name, driver_email, max_cargo, km_tax, car_photo, courierStatus);
            courierList.add(courier);
        }
        if (courierList.isEmpty()) {
            LOGGER.log(Level.WARN, "Result list is empty!");
        }
        ConnectionPool.getInstance().freeConnection(connection);
        return courierList;
    }
}
