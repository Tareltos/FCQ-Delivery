package by.tareltos.fcqdelivery.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantLock;
import com.mysql.jdbc.Driver;

public class ConnectionPool {
	private String user;
	private String password;
	private String url;
	private LinkedBlockingQueue<Connection> connectionPool;
	private static int poolSize;
	private static ConnectionPool instance;
	private static AtomicBoolean isNull = new AtomicBoolean(true);
	private static ReentrantLock lock = new ReentrantLock();

	private ConnectionPool() {
		try {
			DriverManager.registerDriver(new Driver());
			poolSize = 10;
			user = "root";
			password = "Privet";
			url = "jdbc:mysql://localhost:3306/delivery";
			connectionPool = new LinkedBlockingQueue<Connection>(poolSize);
			for (int i = 0; i < poolSize; i++) {
				connectionPool.put(DriverManager.getConnection(url, user, password));
			}
		} catch (SQLException | InterruptedException e) {		
			throw new ExceptionInInitializerError(e);
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
		Iterator<Connection> iterator = connectionPool.iterator();
		while (iterator.hasNext()) {
			try {
				iterator.next().close();
			} catch (SQLException e) {
			    e.printStackTrace();
			}
		}
	}
}
