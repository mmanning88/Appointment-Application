/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 *
 * @author Matthew Manning
 */
public class CountryDAO {
    
    
    public static void addDBCountry(Address address, Connection conn) throws SQLException {
        String insertCountry = "INSERT INTO country VALUES(?, ?, ?, ?, ?, ?)";
        DBQuery.setPreparedStatement(conn, insertCountry);
        PreparedStatement ps = DBQuery.getPreparedStatement();
        ps.setInt(1, address.getCountryId());
        ps.setString(2, address.getCountry());
        ps.setString(3, DateTimeFormat.getCurrentUTC());
        ps.setString(4, User.currentUser.getUserName());
        ps.setString(5, DateTimeFormat.getCurrentUTC());
        ps.setString(6, User.currentUser.getUserName());
        ps.execute();
    }
    
    
    public static int checkCountry(String country) throws SQLException {
        Connection conn = DBConnection.startConnection();
        String checkCity = "SELECT * FROM country WHERE country = ?";
        DBQuery.setPreparedStatement(conn, checkCity);
        PreparedStatement ps = DBQuery.getPreparedStatement();
        ps.setString(1, country);
        ps.execute();
        ResultSet rs = ps.getResultSet();
        int result;
        
        if (rs.next()) {
            result = rs.getInt("countryId");
        } else {
            result = -1;
        }
        DBConnection.closeConnection();
        return result;
    }
    
    
    
    
    public static int getNextCityId() throws SQLException {
        Connection conn = DBConnection.startConnection();
        String getID = "SELECT MAX(countryId) from country";
        DBQuery.setPreparedStatement(conn, getID);
        PreparedStatement ps = DBQuery.getPreparedStatement();
        ps.execute();
        ResultSet rs = ps.getResultSet();
            
        rs.next();
            
        int highestID = rs.getInt("MAX(countryId)");
        DBConnection.closeConnection();
        return highestID + 1;
    }
}
