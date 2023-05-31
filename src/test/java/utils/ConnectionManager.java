package utils;

import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@Slf4j
public class ConnectionManager {

    private ConfigFileReader configFileReader;

    public ConnectionManager(ConfigFileReader configFileReader) {
        this.configFileReader = configFileReader;
    }

    public Connection createConnection() throws RuntimeException {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            Connection connection = DriverManager.getConnection(configFileReader.getDatabaseUrl(), configFileReader.getDatabaseUsername(), configFileReader.getDatabasePassword());
            connection.setAutoCommit(false);
            return connection;
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public void executeQuery(String query) {
        try( Connection connection = createConnection(); 
            Statement statement = connection.createStatement()) {
            statement.executeQuery(query);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
