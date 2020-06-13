
package DAO;

import Model.Address;
import Model.Appointment;
import Model.Customer;
import Model.User;
import Model.UserList;
import static Model.UserList.getUserList;
import Utilities.DBConnection;
import Utilities.DBQuery;
import Utilities.DateTimeFormat;
import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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
        Instant endUTC = startDT.toInstant();
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
                                                                    customer, user, title, type, startLocalZDT, endLocalZDT)
                                .setContact(contact)
                                .setDescription(location)
                                .setLocation(description).build();
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

            //ZonedDateTime dt = ZonedDateTime.ofInstant(rs.getTimestamp("datetime").toInstant(), UTCZONEID)
            
            
            ZoneId localTZ = ZoneId.of(TimeZone.getDefault().getID());
            ZonedDateTime startDT = ZonedDateTime.of(rs.getTimestamp("start").toLocalDateTime(), ZoneOffset.UTC);
            Instant startUTC = startDT.toInstant();
            ZonedDateTime localStartDT = startUTC.atZone(localTZ);
            
            ZonedDateTime endDT = ZonedDateTime.of(rs.getTimestamp("end").toLocalDateTime(), ZoneOffset.UTC);
            Instant endUTC = startDT.toInstant();
            ZonedDateTime localEndDT = endUTC.atZone(localTZ);
            
//        LocalDate parisDate = LocalDate.of(2019, 5, 28);
//        LocalTime parisTime = LocalTime.of(02, 00);
//        ZoneId parisZoneId = ZoneId.of("Europe/Paris");
//        ZonedDateTime parisZDT = ZonedDateTime.of(parisDate, parisTime, parisZoneId);
//        ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());
//        
//        Instant parisToGMTInstant = parisZDT.toInstant();
//        ZonedDateTime parisToLocalZDT = parisZDT.withZoneSameInstant(localZoneId);
//        ZonedDateTime gmtToLocalZDT = parisToGMTInstant.atZone(localZoneId);
//        
//        System.out.println("Local: " + ZonedDateTime.now());
//        System.out.println("Paris: " + parisZDT);
//        System.out.println("Paris->GMT: " + parisToGMTInstant);
//        System.out.println("GMT->Local: " + gmtToLocalZDT);
//        System.out.println("GMT->LocaleDate: " + gmtToLocalZDT.toLocalDate());
//        System.out.println("GMT->LocaleTime: " + gmtToLocalZDT.toLocalTime());
//        
//        String date = String.valueOf(gmtToLocalZDT.toLocalDate());
//        String time = String.valueOf(gmtToLocalZDT.toLocalTime());
//        String dateTime = date + " " + time;
//        System.out.println(dateTime); //MySql insertion format

            
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
                                                                    customer, targetUser, title, type, localStartDT, endDT)
                                .setContact(contact)
                                .setDescription(location)
                                .setLocation(description).build();
            
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
