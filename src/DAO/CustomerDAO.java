
package DAO;

import Controller.LoginScreenController;
import Model.Address;
import Model.Customer;
import Model.User;
import Utilities.DBConnection;
import Utilities.DBQuery;
import Utilities.DateTimeFormat;
import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/*
@author Matthew Manning
*/

// B.   Provide the ability to add, update, and delete customer records in the database, including name, address, and phone number.
public class CustomerDAO {
    
        public static Customer getDBCustomer(int id) throws SQLException {
            Connection conn = DBConnection.startConnection();
            String selectCustomer = "SELECT * from customer "
                    + "INNER JOIN address USING (addressId) "
                    + "INNER JOIN city USING (cityId) "
                    + "INNER JOIN country USING (countryId) WHERE customerId = ?";
            DBQuery.setPreparedStatement(conn, selectCustomer);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.setInt(1, id);
            ps.execute();
            ResultSet rs = ps.getResultSet();
            rs.next();

            Address address = new Address(rs.getInt("addressId"), rs.getInt("cityId"), rs.getInt("countryId"), 
                                      rs.getString("address"), rs.getString("address2"), rs.getString("postalCode"), 
                                      rs.getString("phone"), rs.getString("city"), rs.getString("country"));
            //customer created
            Customer customer = new Customer(rs.getInt("customerId"), rs.getInt("active"), rs.getString("customerName"), address);
            DBConnection.closeConnection();
            return customer;
        }
        
        public static void deleteDBCustomer(int id) throws SQLException {
            Connection conn = DBConnection.startConnection();
            String deleteCustomer = "DELETE FROM customer WHERE customerId = ?";
            DBQuery.setPreparedStatement(conn, deleteCustomer);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.setInt(1, id);
            ps.execute();
            DBConnection.closeConnection();
        }
        
        public static void updateDBCustomer(Customer customer, boolean needNewCity, boolean needNewCountry) throws SQLException {
            Connection conn = DBConnection.startConnection();
            
            // Check if new country is needed
            if (needNewCountry) {
                CountryDAO.addDBCountry(customer.getAddress(), conn);
            }
            
            // Check if new city is needed
            if (needNewCity) {
                CityDAO.addDBCity(customer.getAddress(), conn);
            }
            updateDBCustomerAddress(customer, conn);
            String updateCustomer = "UPDATE customer SET customerName = ?, active = ? WHERE customerID = ?";
            DBQuery.setPreparedStatement(conn, updateCustomer);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            
            // 1 = active/true 0 = inactive/false
            int active = customer.getActive() ? 1 : 0;
            ps.setString(1, customer.getCustomerName());
            ps.setInt(2, active);
            ps.setInt(3, customer.getCustomerId());
            ps.execute();
            

             
            
            DBConnection.closeConnection();
        }
        
        public static void updateDBCustomerAddress(Customer customer, Connection conn) throws SQLException {
            
            Address address = customer.getAddress();
            String updateAddress = "UPDATE address SET address = ?, address2 = ?, cityId = ?, postalCode = ?, "
                    + "phone = ?, lastUpdate = ?, lastUpdateBy = ? WHERE addressId = ?";
            DBQuery.setPreparedStatement(conn, updateAddress);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.setString(1, address.getAddress());
            ps.setString(2, address.getAddress2());
            ps.setInt(3, address.getCityId());
            ps.setString(4, address.getPostalCode());
            ps.setString(5, address.getPhone());
            ps.setString(6, DateTimeFormat.getCurrentUTC());
            ps.setString(7, User.currentUser.getUserName());
            ps.setInt(8, address.getAddressId());
            ps.execute();

        }
        
        public static void addDBCustomer(Customer customer, boolean needNewCity, boolean needNewCountry) throws SQLException {

            Connection conn = DBConnection.startConnection();
            
            // Check if new country is needed
            if (needNewCountry) {
                CountryDAO.addDBCountry(customer.getAddress(), conn);
            }
            
            // Check if new city is needed
            if (needNewCity) {
                CityDAO.addDBCity(customer.getAddress(), conn);
            }
            addDBAddress(customer.getAddress(), conn);
            String addCustomer = "INSERT INTO customer VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
                    
            // Execute insert with customer object
            int active;
            if (customer.getActive()) {
                active = 1;
            } else {
                active = 0;
            }

            DBQuery.setPreparedStatement(conn, addCustomer);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.setInt(1, customer.getCustomerId());
            ps.setString(2, customer.getCustomerName());
            ps.setInt(3, customer.getAddress().getAddressId());
            ps.setInt(4, active);
            ps.setString(5, DateTimeFormat.getCurrentUTC());
            ps.setString(6, "admin");
            ps.setString(7, DateTimeFormat.getCurrentUTC());
            ps.setString(8, "admin");
            ps.execute();
            
            if (ps.getUpdateCount() > 0) {
                System.out.println("Number of rows affects: " + ps.getUpdateCount());
            } else {
                System.out.println("No change");
            }
            
            DBConnection.closeConnection();
        }
        
        public static ObservableList getAllDBCustomers() throws SQLException {
            ObservableList allCustomers = FXCollections.observableArrayList();
            Connection conn = DBConnection.startConnection();
            String selectAllCustomers = "SELECT * from customer "
                    + "INNER JOIN address USING (addressId) "
                    + "INNER JOIN city USING (cityId) "
                    + "INNER JOIN country USING (countryId)";
            DBQuery.setPreparedStatement(conn, selectAllCustomers);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.execute();
            ResultSet rs = ps.getResultSet();
            while (rs.next()) {
                Address address = new Address(rs.getInt("addressId"), rs.getInt("cityId"), rs.getInt("countryId"), 
                                            rs.getString("address"), rs.getString("address2"), rs.getString("postalCode"), 
                                            rs.getString("phone"), rs.getString("city"), rs.getString("country"));
                Customer customer = new Customer(rs.getInt("customerId"), rs.getInt("active"), rs.getString("customerName"), address);
                
                allCustomers.add(customer);
            }
            DBConnection.closeConnection();
            return allCustomers;
        }
        
    public static void addDBAddress(Address address, Connection conn) throws SQLException {
        String insertAddress = "INSERT INTO address VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
 
        DBQuery.setPreparedStatement(conn, insertAddress);
        PreparedStatement ps2 = DBQuery.getPreparedStatement();
        ps2.setInt(1, address.getAddressId());
        ps2.setString(2, address.getAddress());
        ps2.setString(3, address.getAddress2());
        ps2.setInt(4, address.getCityId());
        ps2.setString(5, address.getPostalCode());
        ps2.setString(6, address.getPhone());
        ps2.setString(7, DateTimeFormat.getCurrentUTC());
        ps2.setString(8, User.currentUser.getUserName());
        ps2.setString(9, DateTimeFormat.getCurrentUTC());
        ps2.setString(10, User.currentUser.getUserName());
        ps2.execute();
    }
        

        public static int getNextAddressId() throws SQLException {
            Connection conn = DBConnection.startConnection();
            String getID = "SELECT MAX(addressId) from address";
            DBQuery.setPreparedStatement(conn, getID);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.execute();
            ResultSet rs = ps.getResultSet();
            
            rs.next();
            
            int highestID = rs.getInt("MAX(addressId)");
            return highestID + 1;

        }
        
        
    
}
