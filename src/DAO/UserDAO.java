
package DAO;

import Model.User;
import Utilities.DBConnection;
import Utilities.DBQuery;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/*
@author Matthew Manning
*/
public class UserDAO {
    
        public static User getDBUser(int id) throws SQLException {
            Connection conn = DBConnection.startConnection();
            try {
                String selectUser = "SELECT * FROM user where userId = ?";
                DBQuery.setPreparedStatement(conn, selectUser);
                PreparedStatement ps = DBQuery.getPreparedStatement();
                ps.setInt(1, id); // Set SQL statement parameter to method parameter
                ps.execute(); // Execute PreparedStatement
                ResultSet rs = ps.getResultSet();
                if (rs.next()) {
                    LocalDate createDate = rs.getDate("createDate").toLocalDate();
                    LocalTime createTime = rs.getTime("createDate").toLocalTime();
                    LocalDateTime createldt = LocalDateTime.of(createDate, createTime);
                    LocalDate updateDate = rs.getDate("lastUpdate").toLocalDate();
                    LocalTime updateTime = rs.getTime("lastUpdate").toLocalTime();
                    LocalDateTime updateldt = LocalDateTime.of(updateDate, updateTime);
                    User user = new User(rs.getInt("userId"), rs.getString("userName"), rs.getString("password"), rs.getString("createdBy"), rs.getString("lastUpdateBy"), createldt, updateldt);
                    DBConnection.closeConnection();
                    return user;
                } 

            } catch (SQLException e){
                System.out.println("User not found in DB");
            }
            DBConnection.closeConnection();
            return null;    
    }
        
        public static User getDBUser(String userName) throws SQLException {
            Connection conn = DBConnection.startConnection();
            try {
                String selectUser = "SELECT * FROM user where userName = ?";
                DBQuery.setPreparedStatement(conn, selectUser);
                PreparedStatement ps = DBQuery.getPreparedStatement();
                ps.setString(1, userName); // Set SQL statement parameter to method parameter
                ps.execute(); // Execute PreparedStatement
                ResultSet rs = ps.getResultSet();
                if (rs.next()) {
                    LocalDate createDate = rs.getDate("createDate").toLocalDate();
                    LocalTime createTime = rs.getTime("createDate").toLocalTime();
                    LocalDateTime createldt = LocalDateTime.of(createDate, createTime);
                    LocalDate updateDate = rs.getDate("lastUpdate").toLocalDate();
                    LocalTime updateTime = rs.getTime("lastUpdate").toLocalTime();
                    LocalDateTime updateldt = LocalDateTime.of(updateDate, updateTime);
                    User user = new User(rs.getInt("userId"), rs.getString("userName"), rs.getString("password"), rs.getString("createdBy"), rs.getString("lastUpdateBy"), createldt, updateldt);
                    DBConnection.closeConnection();
                    return user;
                } 
                
            } catch (SQLException e){
                System.out.println("User not found in DB");
            }
            DBConnection.closeConnection();
            return null;    
    }
        public static ObservableList getAllDBUsers() throws SQLException {
            ObservableList allUsers = FXCollections.observableArrayList();
            Connection conn = DBConnection.startConnection();
            String selectAllUsers = "SELECT * FROM user";
            DBQuery.setPreparedStatement(conn, selectAllUsers);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                LocalDate createDate = rs.getDate("createDate").toLocalDate();
                    LocalTime createTime = rs.getTime("createDate").toLocalTime();
                    LocalDateTime createldt = LocalDateTime.of(createDate, createTime);
                    LocalDate updateDate = rs.getDate("lastUpdate").toLocalDate();
                    LocalTime updateTime = rs.getTime("lastUpdate").toLocalTime();
                    LocalDateTime updateldt = LocalDateTime.of(updateDate, updateTime);
                    User user = new User(rs.getInt("userId"), rs.getString("userName"), rs.getString("password"), rs.getString("createdBy"), rs.getString("lastUpdateBy"), createldt, updateldt);
                    allUsers.add(user);
            }
            DBConnection.closeConnection();
            return allUsers;
        }
        
    
}
