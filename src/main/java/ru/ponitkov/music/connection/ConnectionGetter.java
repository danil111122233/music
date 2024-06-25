package ru.ponitkov.music.connection;

import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectionGetter {
    private static final String URL = "jdbc:postgresql://localhost:5432/java2";
    private static final String USERNAME = "postgres";
    private static final String PASSWORD = "1234";
    private static final String DRIVER_NAME = "org.postgresql.Driver";
    private static final int POOL_SIZE = 10;
    private static final Logger LOGGER = Logger.getLogger(ConnectionGetter.class.getName());
    private static BlockingQueue<Connection> pool;

    static {
        LOGGER.log(Level.INFO, "Initializing the database driver.");
        loadDriver();

        LOGGER.log(Level.INFO, "Initializing the connection pool.");
        initPool();
    }

    private static void initPool() {
        pool = new ArrayBlockingQueue<>(POOL_SIZE);

        for (int i = 0; i < POOL_SIZE; i++) {
            Connection connection = open();
            Connection proxyConnection = (Connection) Proxy.newProxyInstance(
                    ConnectionGetter.class.getClassLoader(),
                    new Class[]{Connection.class},
                    (proxy, method, args) -> "close".equals(method.getName()) ? pool.add((Connection) proxy) : method.invoke(connection, args)
            );
            pool.add(proxyConnection);
        }
    }

    private static Connection open() {
        try {
            return DriverManager.getConnection(
                    URL,
                    USERNAME,
                    PASSWORD
            );
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Failed to open connection: ", e);
            throw new RuntimeException(e);
        }
    }

    private static void loadDriver() {
        try {
            Class.forName(DRIVER_NAME);
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Failed to load driver: ", e);
            throw new RuntimeException(e);
        }
    }

    public Connection get() {
        try {
            return pool.take();
        } catch (InterruptedException e) {
            LOGGER.log(Level.SEVERE, "Failed to take a connection from the pool: ", e);
            throw new RuntimeException(e);
        }
    }
}
