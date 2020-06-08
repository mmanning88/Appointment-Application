
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
