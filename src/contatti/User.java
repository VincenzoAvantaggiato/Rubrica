package contatti;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import db.Database;
import swing.Main;

public class User {
	private String username;
	private String password;

	public User(String username, String password)  {
		
	    
	    
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}
	
	public static boolean authenticate(String username, String password) throws URISyntaxException, FileNotFoundException, IOException, SQLException{
		File jarDir = new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI()).getParentFile();
	    File file = new File(jarDir, "credenziali_database.properties");
	    Database database = new Database(file.getAbsolutePath(), "rubrica");
	    Connection dbConnection = database.connect();
	    
	    PreparedStatement stmt = dbConnection.prepareStatement("SELECT * FROM Utenti WHERE username = ? AND password = ?");
	    stmt.setString(1, username);
	    stmt.setString(2, password);
	    var result = stmt.executeQuery();
	    boolean isAuthenticated = result.next();
	    database.disconnect();
	   
	    return isAuthenticated;
	}
	
}
