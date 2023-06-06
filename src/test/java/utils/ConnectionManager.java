package utils;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@Slf4j
public class ConnectionManager {

    private ConfigFileReader configFileReader;
    private Connection connection;

    public ConnectionManager(ConfigFileReader configFileReader) {
        this.configFileReader = configFileReader;
    }


    public void createConnection() throws RuntimeException {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            this.connection = DriverManager.getConnection(configFileReader.getDatabaseUrl(), configFileReader.getDatabaseUsername(), configFileReader.getDatabasePassword());
            connection.setAutoCommit(false);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        } 

    }
    public void executeQuery(String query) {
        createConnection();
        try(Statement statement = this.connection.createStatement()) {
            statement.executeQuery(query);
            this.connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void close() throws SQLException {
        this.connection.close();
    }
}
