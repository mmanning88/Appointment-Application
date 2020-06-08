
package DAO;



import Model.Address;
import Model.Appointment;
import Model.Customer;
import Model.User;
import Utilities.DBConnection;
import Utilities.DBQuery;
import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/*
@author Matthew Manning
*/
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
        Appointment appointment = new Appointment.AppointmentBuilder(appointmentId, user.getUserId(), customer.getCustomerId(), 
                                                                    customer, user, title, type, user.getCreateDate(), 
                                                                    user.getCreatedBy(), user.getLastUpdate(), user.getLastUpdateBy())
                                .setContact(contact)
                                .setDescription(location)
                                .setLocation(description).build();
        DBConnection.closeConnection();
        return appointment;
    }
    
    public static ObservableList getAllAppointments() throws SQLException {
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
            Appointment appointment = new Appointment.AppointmentBuilder(appointmentId, user.getUserId(), customer.getCustomerId(), 
                                                                    customer, user, title, type, user.getCreateDate(), 
                                                                    user.getCreatedBy(), user.getLastUpdate(), user.getLastUpdateBy())
                                .setContact(contact)
                                .setDescription(location)
                                .setLocation(description).build();
            
            allAppointments.add(appointment);
        }
        DBConnection.closeConnection();
        return allAppointments;
        
    }
    

    

    
}
