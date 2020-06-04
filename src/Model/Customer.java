
package Model;


public class Customer {
    
    // Customer property members
    private int customerId, active;
    private String customerName;
    
    // Address property members
    private int addressId;
    private String postalCode, phone, address2, address;

    // City property members
    private int cityId;
    private String city;
    
    // Country property members
    private int countryId;
    private String country;

    public Customer(int customerId, int active, String customerName, int addressId, String postalCode, String phone, String address2, String address, int cityId, String city, int countryId, String country) {
        this.customerId = customerId;
        this.active = active;
        this.customerName = customerName;
        this.addressId = addressId;
        this.postalCode = postalCode;
        this.phone = phone;
        this.address2 = address2;
        this.address = address;
        this.cityId = cityId;
        this.city = city;
        this.countryId = countryId;
        this.country = country;
    }
    
    
}
