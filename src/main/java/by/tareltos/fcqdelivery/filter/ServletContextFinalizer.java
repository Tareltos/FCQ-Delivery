package by.tareltos.fcqdelivery.filter;


import java.lang.reflect.Method;
import java.sql.Driver;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.Set;

public class ServletContextFinalizer implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        deregisterDrivers(getDrivers());
    }

    private Enumeration<Driver> getDrivers() {
        return DriverManager.getDrivers();
    }

    private void deregisterDrivers(Enumeration<Driver> drivers) {
        while (drivers.hasMoreElements()) {
            deregisterDriver(drivers.nextElement());
        }
    }

    private void deregisterDriver(Driver driver) {
        try {
            DriverManager.deregisterDriver(driver);
        }  catch (SQLException e) {
            e.printStackTrace();
        }
        //
        try {
            Class<?> cls=Class.forName("com.mysql.jdbc.AbandonedConnectionCleanupThread");
            Method mth=(cls==null ? null : cls.getMethod("shutdown"));
            if(mth!=null) { mth.invoke(null); }
        }
        catch (Throwable thr) {
            thr.printStackTrace();
        }
    }
}