
package Model;


/*
@author Matthew Manning
*/
public class Address {
    
    private final int addressId, cityId, countryId;
    private final String address, address2, postalCode, phone;
    private final String city;
    private final String country;

    public Address(int addressId, int cityId, int countryId, String address, String address2, String postalCode, String phone, String city, String country) {
        this.addressId = addressId;
        this.cityId = cityId;
        this.countryId = countryId;
        this.address = address;
        this.address2 = address2;
        this.postalCode = postalCode;
        this.phone = phone;
        this.city = city;
        this.country = country;
    }

    public int getAddressId() {
        return addressId;
    }

    public int getCityId() {
        return cityId;
    }

    public int getCountryId() {
        return countryId;
    }

    public String getAddress() {
        return address;
    }

    public String getAddress2() {
        return address2;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public String getPhone() {
        return phone;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }

    
    
}
