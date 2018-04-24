package by.tareltos.fcqdelivery.repository.impl;

import by.tareltos.fcqdelivery.entity.courier.Courier;
import by.tareltos.fcqdelivery.entity.courier.CourierStatus;
import by.tareltos.fcqdelivery.repository.Repository;
import by.tareltos.fcqdelivery.repository.RepositoryException;
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

public class CourierRepository implements Repository<Courier> {

    final static Logger LOGGER = LogManager.getLogger();
    final String ADD_COURIER_QUERY = "INSERT INTO courier(car_number, car_producer, car_model, car_photo, driver_phone, driver_name, driver_email, max_cargo, km_tax, status  ) VALUES (?,?,?,?,?,?,?,?,?,?) ";
    final String REMOVE_COURIER_QUERY = "DELETE FROM courier WHERE car_number=? ";
    final String UPDATE_COURIER_QUERY = "UPDATE courier SET car_producer=?, car_model=?, car_photo=?, driver_phone=?, driver_name=?, driver_email=?, max_cargo=?, km_tax=?, status=? where car_number=? ";

    @Override
    public boolean add(Courier courier) throws RepositoryException {
        int executeResult;
        Connection connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement pstm;
        try {
            pstm = connection.prepareStatement(ADD_COURIER_QUERY);
            pstm.setString(1, courier.getCarNumber());
            pstm.setString(2, courier.getCarProducer());
            pstm.setString(3, courier.getCarModel());
            pstm.setString(4, courier.getImageFileName());
            pstm.setString(5, courier.getDriverPhone());
            pstm.setString(6, courier.getDriverName());
            pstm.setString(7, courier.getDriverEmail());
            pstm.setInt(8, courier.getMaxCargo());
            pstm.setDouble(9, courier.getKmTax());
            pstm.setString(10, courier.getStatus().getStatus());
            executeResult = pstm.executeUpdate();
            LOGGER.log(Level.DEBUG, "Execute result in add method: " + executeResult);
            return executeResult == 1 ? true : false;
        } catch (SQLException e) {
            LOGGER.log(Level.WARN, e);
            throw new RepositoryException("SQLException in add method \n" + e, e);
        } finally {
            ConnectionPool.getInstance().freeConnection(connection);
        }
    }

    @Override
    public boolean remove(Courier courier) throws RepositoryException {
        int executeResult;
        Connection connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement pstm;
        try {
            pstm = connection.prepareStatement(REMOVE_COURIER_QUERY);
            pstm.setString(1, courier.getCarNumber());
            executeResult = pstm.executeUpdate();
            return executeResult == 1 ? true : false;
        } catch (SQLException e) {
            LOGGER.log(Level.WARN, e);
            throw new RepositoryException("SQLException in remove method \n" + e, e);
        } finally {
            ConnectionPool.getInstance().freeConnection(connection);
        }
    }

    @Override
    public boolean update(Courier courier) throws RepositoryException {
        int executeResult;
        Connection connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement pstm;
        try {
            pstm = connection.prepareStatement(UPDATE_COURIER_QUERY);
            pstm.setString(1, courier.getCarProducer());
            pstm.setString(2, courier.getCarModel());
            pstm.setString(3, courier.getImageFileName());
            pstm.setString(4, courier.getDriverPhone());
            pstm.setString(5, courier.getDriverName());
            pstm.setString(6, courier.getDriverEmail());
            pstm.setInt(7, courier.getMaxCargo());
            pstm.setDouble(8, courier.getKmTax());
            pstm.setString(9, courier.getStatus().getStatus());
            pstm.setString(10, courier.getCarNumber());
            executeResult = pstm.executeUpdate();
            LOGGER.log(Level.DEBUG, "Execute result in update method: " + executeResult);
            return executeResult == 1 ? true : false;
        } catch (SQLException e) {
            LOGGER.log(Level.WARN, e);
            throw new RepositoryException("SQLException in update method \n" + e, e);
        } finally {
            ConnectionPool.getInstance().freeConnection(connection);
        }
    }

    @Override
    public List query(SqlSpecification specification) throws RepositoryException {
        Connection connection = ConnectionPool.getInstance().getConnection();
        try {
            PreparedStatement pstm = connection.prepareStatement(specification.toSqlClauses());
            ResultSet rs = pstm.executeQuery();
            List<Courier> courierList = new ArrayList<>();
            while (rs.next()) {
                String car_number = rs.getString("car_number");
                String car_producer = rs.getString("car_producer");
                String car_model = rs.getString("car_model");
                String car_photo = rs.getString("car_photo");
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
                Courier courier = new Courier(car_number, car_producer, car_model, driver_phone, driver_name, driver_email, max_cargo, km_tax, car_photo, courierStatus);
                courierList.add(courier);

            }
            if (courierList.isEmpty()) {
                LOGGER.log(Level.WARN, "Result list is empty!");
            }
            return courierList;
        } catch (SQLException e) {
            LOGGER.log(Level.WARN, e);
            throw new RepositoryException("Exception in query method \n" + e, e);
        } finally {
            ConnectionPool.getInstance().freeConnection(connection);
        }

    }

}
