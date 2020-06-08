
package Utilities;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DBConnection {
    

    // JDBC URL
    private static final String jdbcURL = "jdbc:mysql://3.227.166.251/U0617s";
    
    // Driver and Connection interface reference
    private static final String MYSQLJDBCDriver = "com.mysql.jdbc.Driver";
    private static Connection conn;
    
    private static final String username = "U0617s"; // Username
    private static final String password = "53688668655"; // Password
    
    public static Connection startConnection() {
        try {
            Class.forName(MYSQLJDBCDriver);
            conn = (Connection) DriverManager.getConnection(jdbcURL, username, password);
            System.out.println("Connection successful");
            
        } catch (ClassNotFoundException | SQLException ex) {
            //Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println(ex.getMessage());
        }
        //Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        
        return conn;
    }
    
    public static void closeConnection() {
        try {
            conn.close();
            System.out.println("Connection closed");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }
}
