
package DAO;

import Model.Address;
import Model.Appointment;
import Model.Customer;
import Model.User;
import Model.UserList;
import Utilities.DBConnection;
import Utilities.DBQuery;
import Utilities.DateTimeFormat;
import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.TimeZone;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/*
@author Matthew Manning
*/

// C.   Provide the ability to add, update, and delete appointments, capturing the type of appointment and a link to the specific customer record in the database.
public class AppointmentDAO {
    
    public static Appointment getAppointmentDAO(int id) throws SQLException {
        Connection conn = DBConnection.startConnection();
        //info from appointment/customer/address/city/country tables
        String selectAppointment = "SELECT * FROM appointment INNER JOIN customer USING (customerId) "
            + "INNER JOIN address USING (addressId) "
            + "INNER JOIN city USING (cityId) "
            + "INNER JOIN country USING (countryId) WHERE appointmentId = ?;"; 
        DBQuery.setPreparedStatement(conn, selectAppointment);
        PreparedStatement ps = DBQuery.getPreparedStatement();
        ps.setInt(1, id);
        ps.execute();
        ResultSet rs = ps.getResultSet();
        rs.next();
        //variables for appointmenbt constructor
        int appointmentId = rs.getInt("appointmentId");
        String title = rs.getString("title");
        String type = rs.getString("type");
        
        
        //Instant localInstant = this.start.toInstant();
        //ZonedDateTime utcToLocalZTD =localInstant.atZone(localTZ);
        ZoneId localTZ = ZoneId.of(TimeZone.getDefault().getID());
        ZonedDateTime startDT = ZonedDateTime.of(rs.getTimestamp("start").toLocalDateTime(), ZoneOffset.UTC);
        Instant startUTC = startDT.toInstant();
        ZonedDateTime localStartDT = startUTC.atZone(localTZ);
            
        ZonedDateTime endDT = ZonedDateTime.of(rs.getTimestamp("end").toLocalDateTime(), ZoneOffset.UTC);
        Instant endUTC = endDT.toInstant();
        ZonedDateTime localEndDT = endUTC.atZone(localTZ);
        
        
        String contact = rs.getString("contact");
        String location = rs.getString("location");
        String description = rs.getString("description");
        int userId = rs.getInt("userId");
        User user = UserDAO.getDBUser(userId);
        Address address = new Address(rs.getInt("addressId"), rs.getInt("cityId"), rs.getInt("countryId"), 
                                      rs.getString("address"), rs.getString("address2"), rs.getString("postalCode"), 
                                      rs.getString("phone"), rs.getString("city"), rs.getString("country"));
        Customer customer = new Customer(rs.getInt("customerId"), rs.getInt("active"), rs.getString("customerName"), address);        
        //appointment created
        Appointment appointment = new Appointment.AppointmentBuilder(appointmentId, customer.getCustomerId(), 
                                                                    customer, user, title, type, localStartDT, localEndDT)
                                .setContact(contact)
                                .setDescription(description)
                                .setLocation(location).build();
        DBConnection.closeConnection();
        return appointment;
    }
    
    public static ObservableList getAllAppointmentsDB() throws SQLException {
        ObservableList allAppointments = FXCollections.observableArrayList();;
        Connection conn = DBConnection.startConnection();
        String selectAllAppointments = "SELECT * FROM appointment INNER JOIN customer USING (customerId) "
            + "INNER JOIN address USING (addressId) "
            + "INNER JOIN city USING (cityId) "
            + "INNER JOIN country USING (countryId)"; 
        DBQuery.setPreparedStatement(conn, selectAllAppointments);
        PreparedStatement ps = DBQuery.getPreparedStatement();
        ps.execute();
        ResultSet rs = ps.getResultSet();
        while (rs.next()) {
           int appointmentId = rs.getInt("appointmentId");
            String title = rs.getString("title");
            String type = rs.getString("type");

            ZoneId localTZ = ZoneId.of(TimeZone.getDefault().getID());
            ZonedDateTime startDT = ZonedDateTime.of(rs.getTimestamp("start").toLocalDateTime(), ZoneOffset.UTC);
            Instant startUTC = startDT.toInstant();
            ZonedDateTime localStartDT = startUTC.atZone(localTZ);
            
            ZonedDateTime endDT = ZonedDateTime.of(rs.getTimestamp("end").toLocalDateTime(), ZoneOffset.UTC);
            Instant endUTC = endDT.toInstant();
            ZonedDateTime localEndDT = endUTC.atZone(localTZ);
                    
            String contact = rs.getString("contact");
            String location = rs.getString("location");
            String description = rs.getString("description");
            //creates connection for each appointment record returned in order to get user
            User targetUser = UserList.searchUserList(rs.getInt("userId"));
            Address address = new Address(rs.getInt("addressId"), rs.getInt("cityId"), rs.getInt("countryId"), 
                                          rs.getString("address"), rs.getString("address2"), rs.getString("postalCode"), 
                                          rs.getString("phone"), rs.getString("city"), rs.getString("country"));
            Customer customer = new Customer(rs.getInt("customerId"), rs.getInt("active"), rs.getString("customerName"), address);        
            //appointment created
            Appointment appointment = new Appointment.AppointmentBuilder(appointmentId, customer.getCustomerId(), 
                                                                    customer, targetUser, title, type, localStartDT, localEndDT)
                                .setContact(contact)
                                .setDescription(description)
                                .setLocation(location).build();
            
            allAppointments.add(appointment);
        }
        DBConnection.closeConnection();
        return allAppointments;
        
    }
    
    public static void addAppointmentDB(Appointment appointment) throws SQLException {
        Connection conn = DBConnection.startConnection();
        String addAppointment = "INSERT INTO appointment VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        DBQuery.setPreparedStatement(conn, addAppointment);
        PreparedStatement ps = DBQuery.getPreparedStatement();
  
        ZonedDateTime startUTC = appointment.getStart().withZoneSameInstant(ZoneId.of("UTC"));
        ZonedDateTime endUTC = appointment.getEnd().withZoneSameInstant(ZoneId.of("UTC"));
        Timestamp startTS = Timestamp.valueOf(startUTC.toLocalDateTime());
        Timestamp endTS = Timestamp.valueOf(endUTC.toLocalDateTime());
  
        ps.setInt(1, appointment.getAppointmentId());
        ps.setInt(2, appointment.getCustomerId());
        ps.setInt(3, User.currentUser.getUserId());
        ps.setString(4, appointment.getTitle());
        ps.setString(5, appointment.getDescription());
        ps.setString(6, appointment.getLocation());
        ps.setString(7, appointment.getContact());
        ps.setString(8, appointment.getType());
        ps.setString(9, "");
        ps.setTimestamp(10, startTS);
        ps.setTimestamp(11, endTS);
        ps.setString(12, DateTimeFormat.getCurrentUTC());
        ps.setString(13, User.currentUser.getUserName());
        ps.setString(14, DateTimeFormat.getCurrentUTC());
        ps.setString(15, User.currentUser.getUserName());
        ps.execute();
        
        DBConnection.closeConnection();
    }
    
    public static void modifyAppointmentDB(Appointment appointment) throws SQLException {
        Connection conn = DBConnection.startConnection();
        String updateAppointment = "UPDATE appointment SET customerId = ?, title = ?, description = ?, location = ?, "
                    + "contact = ?, type = ?, start = ?, end = ?, lastUpdate = ?, lastUpdateBy = ? WHERE appointmentId = ?";
        DBQuery.setPreparedStatement(conn, updateAppointment);
        PreparedStatement ps = DBQuery.getPreparedStatement();
        
        ZonedDateTime startUTC = appointment.getStart().withZoneSameInstant(ZoneId.of("UTC"));
        ZonedDateTime endUTC = appointment.getEnd().withZoneSameInstant(ZoneId.of("UTC"));
        Timestamp startTS = Timestamp.valueOf(startUTC.toLocalDateTime());
        Timestamp endTS = Timestamp.valueOf(endUTC.toLocalDateTime());
        
        ps.setInt(1, appointment.getCustomerId());
        ps.setString(2, appointment.getTitle());
        ps.setString(3, appointment.getDescription());
        ps.setString(4, appointment.getLocation());
        ps.setString(5, appointment.getContact());
        ps.setString(6, appointment.getType());
        ps.setTimestamp(7, startTS);
        ps.setTimestamp(8, endTS);
        ps.setString(9, DateTimeFormat.getCurrentUTC());
        ps.setString(10, appointment.getUser().getUserName());
        ps.setInt(11, appointment.getAppointmentId());
        ps.execute();
        
        DBConnection.closeConnection();
    }
    
    public static void deleteAppointmentDB(int id) throws SQLException { 
        Connection conn = DBConnection.startConnection();
        String deleteAppointment = "DELETE FROM appointment WHERE appointmentId = ?";
        DBQuery.setPreparedStatement(conn, deleteAppointment);
        PreparedStatement ps = DBQuery.getPreparedStatement();
        ps.setInt(1, id);
        ps.execute();
        DBConnection.closeConnection();
    }
    
    public static int getNextAppointmentId() throws SQLException {
        Connection conn = DBConnection.startConnection();
        String getID = "SELECT MAX(appointmentId) from appointment";
        DBQuery.setPreparedStatement(conn, getID);
        PreparedStatement ps = DBQuery.getPreparedStatement();
        ps.execute();
        ResultSet rs = ps.getResultSet();
            
        rs.next();
            
        int highestID = rs.getInt("MAX(appointmentId)");
        DBConnection.closeConnection();
        return highestID + 1;

    }
    

    

    
}
