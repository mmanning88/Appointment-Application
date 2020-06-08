
package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
/*
@author Matthew Manning
*/
public class CustomerList {
    
    public static ObservableList<Customer> customerList = FXCollections.observableArrayList();


    public static ObservableList<Customer> getCustomerList() {
        return customerList;
    }

    public static void addToCustomerList(Customer customer) {       
        customerList.add(customer);  
    }
    
    public static void deleteFromCustomerList(Customer customer) {       
        customerList.remove(customer);  
    }
    
    public static void updateCustomerList(int index, Customer customer) {
        customerList.set(index, customer);
    }
    
    public static Customer searchCustomerList(int customerId) {

        for (Customer customer : getCustomerList()) {
            if (customer.getCustomerId() == customerId) {
                return customer;
            }
        }
        return null;
    
    }
    
    
    
    
}
