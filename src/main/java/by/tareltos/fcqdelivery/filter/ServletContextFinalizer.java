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
        abandonedThreadDestroyed();
    }

    private void abandonedThreadDestroyed() {
        //26-May-2018 12:34:31.622 WARNING [localhost-startStop-2] org.apache.catalina.loader.WebappClassLoaderBase.clearReferencesThreads The web application [ROOT] appears to have started a thread named [Abandoned connection cleanup thread] but has failed to stop it. This is very likely to create a memory leak. Stack trace of thread:
        // java.lang.Object.wait(Native Method)
        // java.lang.ref.ReferenceQueue.remove(ReferenceQueue.java:143)
        // com.mysql.jdbc.AbandonedConnectionCleanupThread.run(AbandonedConnectionCleanupThread.java:43)
        try {
            Class<?> cls=Class.forName("com.mysql.jdbc.AbandonedConnectionCleanupThread");
            Method mth=(cls==null ? null : cls.getMethod("shutdown"));
            if(mth!=null) { mth.invoke(null); }
        }
        catch (Throwable thr) {
            thr.printStackTrace();
        }
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
    }
}