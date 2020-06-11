
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
    
    public static int searchCity(String city) {
        for (Customer customer : getCustomerList()) {
            if (customer.getAddress().getCity() == city) {
                return customer.getAddress().getCityId();
            }
        }
        return -1;
    }
    
    public static int searchCountry(String country) {
        for (Customer customer : getCustomerList()) {
            if (customer.getAddress().getCountry() == country) {
                return customer.getAddress().getCountryId();
            }
        }
        return -1;
    }
    
    public static int getCustomerNextId() {
        
        int maxId = 0;
        for (Customer customer : getCustomerList()) {
            if (customer.getCustomerId() > maxId) {
                maxId = customer.getCustomerId();
            }
        }
        return maxId + 1;
    }
    
         
    
}
