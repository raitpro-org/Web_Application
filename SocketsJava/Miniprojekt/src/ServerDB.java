import java.io.*;
import java.net.*;
import java.sql.*;
//import java.awt.*;
//import javax.swing.*;


public class ServerDB //implements ActionListener{

	
	String sqlSelectAllPersons = "SELECT * FROM person";
	String connectionUrl = "jdbc:mysql://localhost:3306/test?serverTimezone=UTC";

	try (Connection conn = DriverManager.getConnection(connectionUrl, "username", "password"); 
	        PreparedStatement ps = conn.prepareStatement(sqlSelectAllPersons); 
	        ResultSet rs = ps.executeQuery()) {

	        while (rs.next()) {
	            long id = rs.getLong("ID");
	            String name = rs.getString("FIRST_NAME");
	            String lastName = rs.getString("LAST_NAME");

	        }
	} catch (SQLException e) {
	}
}