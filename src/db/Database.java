package db;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.*;

public class Database {
	
	private String HOSTNAME;
	private String PORT;
	private String USERNAME;
	private String PASSWORD;
	private String URL;
	private Connection connection;
	
	public Database(String propertiesFile, String databaseName) throws FileNotFoundException, IOException {
		Properties props = new Properties();
		props.load(new FileInputStream(propertiesFile));
		HOSTNAME = props.getProperty("hostname");
		PORT = props.getProperty("port");
		USERNAME = props.getProperty("username");
		PASSWORD = props.getProperty("password");
		
		URL = "jdbc:mysql://" + HOSTNAME + ":" + PORT + "/" +databaseName + "?useSSL=false&serverTimezone=UTC";
		
	}
	
	public Connection connect() throws SQLException {
		if (connection == null || connection.isClosed()) {
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		}
		return connection;
	}
	
	public void disconnect() throws SQLException {
		if (connection != null && !connection.isClosed()) {
			connection.close();
		}
	}
}
