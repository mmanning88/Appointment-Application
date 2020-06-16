
package Model;

/*
@author Matthew Manning
*/
public class Customer {
    
    // Customer property members
    private final int customerId;
    private int active;
    private String customerName;

    // Address property members
    private Address address;

    public Customer(int customerId, int active, String customerName, Address address) {
        this.customerId = customerId;
        this.active = active;
        this.customerName = customerName;
        this.address = address;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getActive() {
        return active;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
    
    @Override
    public String toString() {
        return this.customerName;
    }

    
    
}
