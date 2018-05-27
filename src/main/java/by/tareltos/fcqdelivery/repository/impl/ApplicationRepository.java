package by.tareltos.fcqdelivery.repository.impl;

import by.tareltos.fcqdelivery.dbconnection.ConnectionException;
import by.tareltos.fcqdelivery.dbconnection.ConnectionPool;
import by.tareltos.fcqdelivery.entity.application.Application;
import by.tareltos.fcqdelivery.entity.application.ApplicationStatus;
import by.tareltos.fcqdelivery.entity.courier.Courier;
import by.tareltos.fcqdelivery.entity.user.User;
import by.tareltos.fcqdelivery.repository.Repository;
import by.tareltos.fcqdelivery.repository.RepositoryException;
import by.tareltos.fcqdelivery.specification.SqlSpecification;
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
 * The class is used work with the database
 * contains requests to the database and the logger
 *
 * @see by.tareltos.fcqdelivery.repository.Repository
 */
public class ApplicationRepository implements Repository<Application> {
    /**
     * The logger object, used to write logs
     *
     * @see org.apache.logging.log4j.Logger
     */
    private final static Logger LOGGER = LogManager.getLogger();
    /**
     * Parameter stores an add query to the database
     */
    private static final String ADD_APPLICATION_QUERY = "INSERT INTO application( user_email, start_point, finish_point, delivery_date, cargo_kg, comment, car_number, total_value, app_status, cancelation_reason ) VALUES (?,?,?,?,?,?,?,?,?,?) ";
    /**
     * Parameter stores an remove query to the database
     */
    private static final String REMOVE_APPLICATION_QUERY = "DELETE FROM application WHERE id=? ";
    /**
     * Parameter stores an update query to the database
     */
    private static final String UPDATE_APPLICATION_QUERY = "UPDATE application SET user_email=?, start_point=?, finish_point=?, delivery_date=?, cargo_kg=?, comment=?, car_number=?, total_value=?, app_status=?, cancelation_reason=? where id=? ";

    private static ApplicationRepository instance = new ApplicationRepository();

    public static ApplicationRepository getInstance() {
        return instance;
    }

    private ApplicationRepository() {
    }

    /**
     * @see by.tareltos.fcqdelivery.repository.Repository
     * @see by.tareltos.fcqdelivery.entity.application.Application
     */
    @Override
    public boolean add(Application application) throws RepositoryException {
        int executeResult;
        Connection connection;
        try {
            connection = ConnectionPool.getInstance().getConnection();
        } catch (ConnectionException e) {
            throw new RepositoryException("ConnectionException in add method" + e.getMessage(), e);
        }
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
            pstm.setString(10, null);
            executeResult = pstm.executeUpdate();
            LOGGER.log(Level.INFO, "Execute result in add method: " + executeResult);
            return executeResult == 1 ? true : false;
        } catch (SQLException e) {
            throw new RepositoryException("SQLException in add method", e);
        } finally {
            try {
                ConnectionPool.getInstance().returnConnection(connection);
            } catch (ConnectionException e) {
                throw new RepositoryException("ConnectionException in add method" + e.getMessage(), e);
            }
        }
    }

    /**
     * @see by.tareltos.fcqdelivery.repository.Repository
     * @see by.tareltos.fcqdelivery.entity.application.Application
     */
    @Override
    public boolean remove(Application application) throws RepositoryException {
        int executeResult;
        Connection connection;
        try {
            connection = ConnectionPool.getInstance().getConnection();
        } catch (ConnectionException e) {
            throw new RepositoryException("ConnectionException in remove method" + e.getMessage(), e);
        }
        PreparedStatement pstm;
        try {
            pstm = connection.prepareStatement(REMOVE_APPLICATION_QUERY);
            pstm.setInt(1, application.getId());
            executeResult = pstm.executeUpdate();
            LOGGER.log(Level.INFO, "Execute result in remove: " + executeResult);
            return executeResult == 1 ? true : false;
        } catch (SQLException e) {
            throw new RepositoryException("SQLException in remove method", e);
        } finally {
            try {
                ConnectionPool.getInstance().returnConnection(connection);
            } catch (ConnectionException e) {
                throw new RepositoryException("ConnectionException in remove method" + e.getMessage(), e);
            }
        }
    }

    /**
     * @see by.tareltos.fcqdelivery.repository.Repository
     * @see by.tareltos.fcqdelivery.entity.application.Application
     */
    @Override
    public boolean update(Application application) throws RepositoryException {
        int executeResult;
        Connection connection;
        try {
            connection = ConnectionPool.getInstance().getConnection();
        } catch (ConnectionException e) {
            throw new RepositoryException("ConnectionException in update method" + e.getMessage(), e);
        }
        PreparedStatement pstm;
        try {
            pstm = connection.prepareStatement(UPDATE_APPLICATION_QUERY);
            pstm.setString(1, application.getOwner().getEmail());
            pstm.setString(2, application.getStartPoint());
            pstm.setString(3, application.getFinishPoint());
            pstm.setString(4, application.getDeliveryDate());
            pstm.setInt(5, application.getCargo());
            pstm.setString(6, application.getComment());
            pstm.setString(7, application.getCourier().getCarNumber());
            pstm.setDouble(8, application.getPrice());
            pstm.setString(9, application.getStatus().getStatus());
            pstm.setString(10, application.getCancelationReason());
            pstm.setInt(11, application.getId());
            executeResult = pstm.executeUpdate();
            LOGGER.log(Level.DEBUG, "Execute result in update method: " + executeResult);
            return executeResult == 1 ? true : false;
        } catch (SQLException e) {
            throw new RepositoryException("SQLException in update method", e);
        } finally {
            try {
                ConnectionPool.getInstance().returnConnection(connection);
            } catch (ConnectionException e) {
                throw new RepositoryException("ConnectionException in update method" + e.getMessage(), e);
            }
        }
    }

    /**
     * @see by.tareltos.fcqdelivery.repository.Repository
     * @see by.tareltos.fcqdelivery.entity.application.Application
     */
    @Override
    public List<Application> query(SqlSpecification specification) throws RepositoryException {
        List<Application> appList = new ArrayList<>();
        Connection connection;
        try {
            connection = ConnectionPool.getInstance().getConnection();
        } catch (ConnectionException e) {
            throw new RepositoryException("ConnectionException in query method" + e.getMessage(), e);
        }
        try {
            PreparedStatement pstm = specification.preparedStatement(connection);
            ResultSet rs = pstm.executeQuery();
            while (rs.next()) {
                Application application = new Application();
                application.setId(rs.getInt("id"));
                User owner = new User();
                owner.setEmail(rs.getString("user_email"));
                owner.setFirstName(rs.getString("firstName"));
                owner.setLastName(rs.getString("lastName"));
                owner.setPhone(rs.getString("phone"));
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
                    Courier courier = new Courier();
                    courier.setCarNumber(carNumber);
                    courier.setCarProducer(rs.getString("car_producer"));
                    courier.setCarModel(rs.getNString("car_model"));
                    courier.setDriverEmail(rs.getString("driver_email"));
                    courier.setDriverPhone(rs.getString("driver_phone"));
                    courier.setDriverName(rs.getString("driver_name"));
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
                        application.setCancelationReason(rs.getString("cancelation_reason"));
                        break;
                    default:
                        application.setStatus(ApplicationStatus.NEW);
                }
                LOGGER.log(Level.INFO, application.toString());
                appList.add(application);
            }
            if (appList.isEmpty()) {
                LOGGER.log(Level.INFO, "Result list is empty!");
            }
            return appList;
        } catch (SQLException e) {
            throw new RepositoryException("Exception in query method", e);
        } finally {
            try {
                ConnectionPool.getInstance().returnConnection(connection);
            } catch (ConnectionException e) {
                throw new RepositoryException("ConnectionException in query method" + e.getMessage(), e);
            }
        }
    }
}
