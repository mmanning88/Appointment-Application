
package DAO;

import Model.Address;
import Model.User;
import Utilities.DBConnection;
import Utilities.DBQuery;
import Utilities.DateTimeFormat;
import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class CityDAO {
    
    
    public static void addDBCity(Address address, Connection conn) throws SQLException {
        String insertCity = "INSERT INTO city VALUES(?, ?, ?, ?, ?, ?, ?)";

            
        DBQuery.setPreparedStatement(conn, insertCity);
        PreparedStatement ps2 = DBQuery.getPreparedStatement();
        ps2.setInt(1, address.getCityId());
        ps2.setString(2, address.getCity());
        ps2.setInt(3, address.getCountryId());
        ps2.setString(4, DateTimeFormat.getCurrentUTC());
        ps2.setString(5, User.currentUser.getUserName());
        ps2.setString(6, DateTimeFormat.getCurrentUTC());
        ps2.setString(7, User.currentUser.getUserName());
        ps2.execute();
    }

    public static int checkCity(String city) throws SQLException {
        Connection conn = DBConnection.startConnection();
        String checkCity = "SELECT * FROM city WHERE city = ?";
        DBQuery.setPreparedStatement(conn, checkCity);
        PreparedStatement ps = DBQuery.getPreparedStatement();
        ps.setString(1, city);
        ps.execute();
        ResultSet rs = ps.getResultSet();
        int result;
        
        if (rs.next()) {
            result = rs.getInt("cityId");
        } else {
            result = -1;
        }
        DBConnection.closeConnection();
        return result;
    }
    
    public static int getNextCityId() throws SQLException {
        Connection conn = DBConnection.startConnection();
        String getID = "SELECT MAX(cityId) from city";
        DBQuery.setPreparedStatement(conn, getID);
        PreparedStatement ps = DBQuery.getPreparedStatement();
        ps.execute();
        ResultSet rs = ps.getResultSet();
            
        rs.next();
            
        int highestID = rs.getInt("MAX(cityId)");
        DBConnection.closeConnection();
        return highestID + 1;
        
    }
}
