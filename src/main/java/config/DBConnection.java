package config;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.sql.DataSource;

import jakarta.servlet.ServletContext;

public class DBConnection {
	public Connection conn;
	
	public DBConnection(ServletContext context) {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			DataSource source= (DataSource)context.getAttribute("DataSource");
			conn = source.getConnection();
		}catch(Exception e) {
			
		}
	}
	
}
