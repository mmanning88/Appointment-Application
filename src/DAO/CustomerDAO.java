
package DAO;

import Model.Address;
import Model.Customer;
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
            String selectCustomer = "DELETE FROM customer WHERE customerId = ?";
            DBQuery.setPreparedStatement(conn, selectCustomer);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            ps.setInt(1, id);
            ps.execute();
            DBConnection.closeConnection();
        }
        
        public static void updateDBCustomer(Customer customer, int id) throws SQLException {
            Connection conn = DBConnection.startConnection();
            String updateCustomer = "UPDATE customer SET customerName = ?, active = ? WHERE customerID = ?";
            DBQuery.setPreparedStatement(conn, updateCustomer);
            PreparedStatement ps = DBQuery.getPreparedStatement();
            
            // 1 = active/true 0 = inactive/false
            int active = customer.getActive() ? 1 : 0;
            ps.setString(1, customer.getCustomerName());
            ps.setInt(2, active);
            ps.setInt(3, id);
            ps.execute();
            DBConnection.closeConnection();
        }
        
        public static void updateDBCustomerAddress(Customer customer) {

            Connection conn = DBConnection.startConnection();
            Address address = customer.getAddress();
            String updateAddress = "UPDATE address INNER JOIN city USING (cityId) INNER JOIN country USING (countryID) "
                    + "SET address.address = 'address', address.address2 = 'adress2' ";
            
            
            DBConnection.closeConnection();
        }
        
        public static void addDBCustomer(Customer customer) {
            
        }
        
        public static ObservableList getAllCustomers() throws SQLException {
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
    
}
