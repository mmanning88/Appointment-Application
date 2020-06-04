
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

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
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

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    
    
    
    
}
