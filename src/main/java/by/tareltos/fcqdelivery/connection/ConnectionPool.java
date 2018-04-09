package by.tareltos.fcqdelivery.connection;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;

import com.mysql.jdbc.Driver;

public class ConnectionPool {
    private LinkedBlockingQueue<Connection> connectionPool;
    private static ConnectionPool instance;
    private static AtomicBoolean isNull = new AtomicBoolean(true);
    private static ReentrantLock lock = new ReentrantLock();

    private ConnectionPool() {
        try {
            DriverManager.registerDriver(new Driver());
            InputStream inputStream = ConnectionPool.class.getClassLoader().getResourceAsStream("db.properties");
            Properties properties = new Properties();
            if (properties != null) {
                try {
                    properties.load(inputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            String  url = properties.getProperty("url");
            String user = properties.getProperty("userName");
            String password = properties.getProperty("password");
            int poolSize = Integer.parseInt(properties.getProperty("poolSize"));

            connectionPool = new LinkedBlockingQueue<>(poolSize);
            for (int i = 0; i < poolSize; i++) {
                connectionPool.put(DriverManager.getConnection(url, user, password));
            }
        } catch (SQLException | InterruptedException e) {
            throw new ExceptionInInitializerError(e);//not runtimeExc
        }
    }

    public static ConnectionPool getInstance() {
        if (isNull.get()) {
            lock.lock();
            try {
                if (isNull.get()) {
                    instance = new ConnectionPool();
                    isNull.set(false);
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    public Connection getConnection() {
        Connection connection = null;
        try {
            connection = connectionPool.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void freeConnection(Connection connection) {
        try {
            if (!connection.isClosed()) {
                connectionPool.put(connection);
            }
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void shutDown() {
        for (int i = 0; i < connectionPool.size(); i++) {
            try {
                connectionPool.take().close();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
