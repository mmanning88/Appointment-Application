package Utilities;

import Model.Address;
import Model.Appointment;
import Model.AppointmentList;
import Model.City;
import Model.Country;
import Model.Customer;
import Model.User;
import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class DBManager {
    
//    LocalDateTime now = LocalDateTime.now();
//    System.out.println("Before : " + now);
//    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//    String formatDateTime = now.format(formatter);
//    System.out.println("After : " + formatDateTime);
    
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

   
    
    public static Country getDBCountry(int id) throws SQLException {
        
        Connection conn = DBConnection.startConnection(); 
        String selectCountry = "SELECT * FROM country where countryId = ?";
        DBQuery.setPreparedStatement(conn, selectCountry);
        PreparedStatement ps = DBQuery.getPreparedStatement();
        ps.setInt(1, id); // Set SQL statement parameter to method parameter
        ps.execute(); // Execute PreparedStatement
        ResultSet rs = ps.getResultSet();
        rs.next();
        Country country = new Country(rs.getInt("countryId"), rs.getString("country"));
        DBConnection.closeConnection();
        return country;

    }
    
    public static City getDBCity(int id) throws SQLException {
        
        Connection conn = DBConnection.startConnection(); 
        String selectCity = "SELECT * FROM city where cityId = ?";
        DBQuery.setPreparedStatement(conn, selectCity);
        PreparedStatement ps = DBQuery.getPreparedStatement();
        ps.setInt(1, id); // Set SQL statement parameter to method parameter
        ps.execute(); // Execute PreparedStatement
        ResultSet rs = ps.getResultSet();
        rs.next();
        City city = new City(rs.getInt("cityId"), rs.getInt("countryId"), rs.getString("city"));
        DBConnection.closeConnection();
        return city;
        
    }
    
    public static Address getDBAddress(int id) throws SQLException {
        Connection conn = DBConnection.startConnection(); 
        String selectAddress = "SELECT * FROM address where addressId = ?";
        DBQuery.setPreparedStatement(conn, selectAddress);
        PreparedStatement ps = DBQuery.getPreparedStatement();
        ps.setInt(1, id); // Set SQL statement parameter to method parameter
        ps.execute(); // Execute PreparedStatement
        ResultSet rs = ps.getResultSet();
        rs.next();
        City city = getDBCity(rs.getInt("cityId"));
        Country country = getDBCountry(city.getCountryId());
        Address address = new Address(rs.getInt("addressId"), city.getCityId(), country.getCountryId(), rs.getString("address"),
                                    rs.getString("address2"), rs.getString("postalCOde"), rs.getString("phone"), city, country);
        DBConnection.closeConnection();
        return address;
    }
    

    public static Customer getDBCustomer(int id) throws SQLException {
        Connection conn = DBConnection.startConnection();
        String selectCustomer = "SELECT * FROM customer where customerId = ?";
        DBQuery.setPreparedStatement(conn, selectCustomer);
        PreparedStatement ps = DBQuery.getPreparedStatement();
        ps.setInt(1, id); // Set SQL statement parameter to method parameter
        ps.execute(); // Execute PreparedStatement
        ResultSet rs = ps.getResultSet();
        rs.next();
        Address address = getDBAddress(rs.getInt("addressId"));
        Customer customer = new Customer(rs.getInt("customerId"), rs.getInt("active"), rs.getString("customerName"), address);
        DBConnection.closeConnection();
        return customer;
    }

    public static Appointment getDBAppointment(int id) throws SQLException {
        Connection conn = DBConnection.startConnection();
        String selectAppointment = "SELECT * FROM appointment where appointmentId = ?";
        DBQuery.setPreparedStatement(conn, selectAppointment);
        PreparedStatement ps = DBQuery.getPreparedStatement();
        ps.setInt(1, id); // Set SQL statement parameter to method parameter
        ps.execute(); // Execute PreparedStatement
        ResultSet rs = ps.getResultSet();
        rs.next();
        Customer customer = getDBCustomer(rs.getInt("customerId"));
        User user = getDBUser(rs.getInt("userId"));
        
        int appointmentId = rs.getInt("appointmentId");
        int customerId = customer.getCustomerId();
        int userId = user.getUserId();
        String title = rs.getString("title");
        String type = rs.getString("type");        
        String contact = rs.getString("contact");
        String createdBy = rs.getString("createdBy");
        String lastUpdateBy = rs.getString("lastUpdateBy");
        LocalDate createDate = rs.getDate("createDate").toLocalDate();
        LocalTime createTime = rs.getTime("createDate").toLocalTime();
        LocalDateTime createldt = LocalDateTime.of(createDate, createTime);
        LocalDate updateDate = rs.getDate("lastUpdate").toLocalDate();
        LocalTime updateTime = rs.getTime("lastUpdate").toLocalTime();
        LocalDateTime updateldt = LocalDateTime.of(updateDate, updateTime);        
                
        contact = rs.wasNull() ? null : contact;
        String description = rs.getString("description");
        description = rs.wasNull() ? null : description;
        String location = rs.getString("location");
        location = rs.wasNull() ? null : location;
            
        Appointment appointment = new Appointment.AppointmentBuilder(appointmentId, customerId, userId, customer, title, type, createldt, createdBy, updateldt, lastUpdateBy)
                .setContact(contact)
                .setDescription(description)
                .setLocation(location).build();

        DBConnection.closeConnection();
        return appointment;

    }

    public static User getDBUser(int id) throws SQLException {
        Connection conn = DBConnection.startConnection();
        String selectUser = "SELECT * FROM user where userId = ?";
        DBQuery.setPreparedStatement(conn, selectUser);
        PreparedStatement ps = DBQuery.getPreparedStatement();
        ps.setInt(1, id); // Set SQL statement parameter to method parameter
        ps.execute(); // Execute PreparedStatement
        ResultSet rs = ps.getResultSet();
        rs.next();    
        Boolean active = (rs.getInt("active") == 1);
        LocalDate createDate = rs.getDate("createDate").toLocalDate();
        LocalTime createTime = rs.getTime("createDate").toLocalTime();
        LocalDateTime createldt = LocalDateTime.of(createDate, createTime);
        LocalDate updateDate = rs.getDate("lastUpdate").toLocalDate();
        LocalTime updateTime = rs.getTime("lastUpdate").toLocalTime();
        LocalDateTime updateldt = LocalDateTime.of(updateDate, updateTime);
        
        User user = new User(rs.getInt("userId"), rs.getString("userName"), rs.getString("password"), rs.getString("createdBy"), rs.getString("lastUpdateBy"), active, createldt, updateldt);
        DBConnection.closeConnection();
        return user;
    }
    // TODO: try a join to reduce number of connections needed
    public static void fillWeeklyAppointments() throws SQLException {
        Connection conn = DBConnection.startConnection();
        String selectAppointments = "SELECT * FROM appointment";
        DBQuery.setPreparedStatement(conn, selectAppointments);
        PreparedStatement ps = DBQuery.getPreparedStatement();
        ps.execute(); // Execute PreparedStatement
        ResultSet rs = ps.getResultSet();
        while (rs.next()) {
            Customer customer = getDBCustomer(rs.getInt("customerId"));
            User user = getDBUser(rs.getInt("userId"));

            int appointmentId = rs.getInt("appointmentId");
            int customerId = customer.getCustomerId();
            int userId = user.getUserId();
            String title = rs.getString("title");
            String type = rs.getString("type");        
            String contact = rs.getString("contact");
            String createdBy = rs.getString("createdBy");
            String lastUpdateBy = rs.getString("lastUpdateBy");
            LocalDate createDate = rs.getDate("createDate").toLocalDate();
            LocalTime createTime = rs.getTime("createDate").toLocalTime();
            LocalDateTime createldt = LocalDateTime.of(createDate, createTime);
            LocalDate updateDate = rs.getDate("lastUpdate").toLocalDate();
            LocalTime updateTime = rs.getTime("lastUpdate").toLocalTime();
            LocalDateTime updateldt = LocalDateTime.of(updateDate, updateTime);        

            contact = rs.wasNull() ? null : contact;
            String description = rs.getString("description");
            description = rs.wasNull() ? null : description;
            String location = rs.getString("location");
            location = rs.wasNull() ? null : location;

            Appointment appointment = new Appointment.AppointmentBuilder(appointmentId, customerId, userId, customer, title, type, createldt, createdBy, updateldt, lastUpdateBy)
                    .setContact(contact)
                    .setDescription(description)
                    .setLocation(location).build();
            AppointmentList.weeklyAppointments.add(appointment);
        }
    }

    
}
