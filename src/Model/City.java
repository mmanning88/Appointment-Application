
package Model;


public class City {
    
    private int cityId, countryId;
    private String city;

    public City(int cityId, int countryId, String city) {
        this.cityId = cityId;
        this.countryId = countryId;
        this.city = city;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }


    
    
}
