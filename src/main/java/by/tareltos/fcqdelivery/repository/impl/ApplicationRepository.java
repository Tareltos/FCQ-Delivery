package by.tareltos.fcqdelivery.repository.impl;

import by.tareltos.fcqdelivery.connection.ConnectionPool;
import by.tareltos.fcqdelivery.entity.application.Application;
import by.tareltos.fcqdelivery.entity.application.ApplicationStatus;
import by.tareltos.fcqdelivery.entity.courier.Courier;
import by.tareltos.fcqdelivery.entity.user.User;
import by.tareltos.fcqdelivery.repository.Repository;
import by.tareltos.fcqdelivery.repository.RepositoryException;
import by.tareltos.fcqdelivery.specification.SqlSpecification;
import by.tareltos.fcqdelivery.specification.impl.CourierByRegNumberSpecification;
import by.tareltos.fcqdelivery.specification.impl.UserByEmailSpecification;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ApplicationRepository implements Repository<Application> {

    final static Logger LOGGER = LogManager.getLogger();
    final String ADD_APPLICATION_QUERY = "INSERT INTO application( user_email, start_point, finish_point, delivery_date, cargo_kg, comment, car_number, total_value, app_status  ) VALUES (?,?,?,?,?,?,?,?,?) ";
    final String REMOVE_COURIER_QUERY = "DELETE FROM courier WHERE car_number=? ";
    final String UPDATE_COURIER_QUERY = "UPDATE courier SET car_producer=?, car_model=?, car_photo=?, driver_phone=?, driver_name=?, driver_email=?, max_cargo=?, km_tax=?, status=? where car_number=? ";
    private UserRepository userRepository = new UserRepository();
    private CourierRepository courierRepository = new CourierRepository();

    @Override
    public boolean add(Application application) throws RepositoryException {
        int executeResult;
        Connection connection = ConnectionPool.getInstance().getConnection();
        PreparedStatement pstm;
        try {
            pstm = connection.prepareStatement(ADD_APPLICATION_QUERY);
            pstm.setString(1, application.getOwner().getEmail());
            pstm.setString(2, application.getStartPoint());
            pstm.setString(3, application.getFinishPoint());
            pstm.setString(4, application.getDeliveryDate());
            pstm.setInt(5, application.getCargo());
            pstm.setString(6, application.getComment());
            pstm.setString(7, null);
            pstm.setDouble(8, application.getPrice());
            pstm.setString(9, application.getStatus().getStatus());
            executeResult = pstm.executeUpdate();
            return executeResult == 1 ? true : false;
        } catch (SQLException e) {
            throw new RepositoryException("Exception in add method", e);
        } finally {
            ConnectionPool.getInstance().freeConnection(connection);
        }
    }

    @Override
    public boolean remove(Application application) throws RepositoryException {
        return false;
    }

    @Override
    public boolean update(Application application) throws RepositoryException {
        return false;
    }

    @Override
    public List<Application> query(SqlSpecification specification) throws RepositoryException {
        List<Application> appList = new ArrayList<>();
        Connection connection = ConnectionPool.getInstance().getConnection();
        try {
            PreparedStatement pstm = connection.prepareStatement(specification.toSqlClauses());
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Application application = new Application();
                application.setId(rs.getInt("id"));
                List<User> list = userRepository.query(new UserByEmailSpecification(rs.getString("user_email")));
                User owner = list.get(0);
                LOGGER.log(Level.DEBUG, owner.toString());
                application.setOwner(owner);
                application.setStartPoint(rs.getString("start_point"));
                application.setFinishPoint(rs.getString("finish_point"));
                application.setDeliveryDate(rs.getString("delivery_date"));
                application.setCargo(rs.getInt("cargo_kg"));
                LOGGER.log(Level.DEBUG, application.getCargo());
                application.setComment(rs.getString("comment"));
                String carNumber = rs.getString("car_number");
                LOGGER.log(Level.DEBUG, carNumber);
                if (carNumber != null) {
                    Courier courier = (Courier) courierRepository.query(new CourierByRegNumberSpecification(carNumber)).get(0);
                    LOGGER.log(Level.DEBUG, courier.toString());
                    application.setCourier(courier);
                }
                application.setPrice(rs.getDouble("total_value"));
                String status = rs.getString("app_status");
                switch (status) {
                    case "new":
                        application.setStatus(ApplicationStatus.NEW);
                        break;
                    case "waiting":
                        application.setStatus(ApplicationStatus.WAITING);
                        break;
                    case "confirmed":
                        application.setStatus(ApplicationStatus.CONFIRMED);
                        break;
                    case "delivered":
                        application.setStatus(ApplicationStatus.DELIVERED);
                        break;
                    case "canceled":
                        application.setStatus(ApplicationStatus.CANCELED);
                        break;
                }
                appList.add(application);
            }
            if (appList.isEmpty()) {
                LOGGER.log(Level.WARN, "Result list is empty!");
            }
            return appList;
        } catch (SQLException e) {
            throw new RepositoryException("Exception in query method", e);
        } finally {
            ConnectionPool.getInstance().freeConnection(connection);
        }

    }
}
