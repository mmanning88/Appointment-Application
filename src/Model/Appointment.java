
package Model;


public class Appointment {
    
    private int appointmentId, customerId, userId;
    private Customer customer;
    private String title, description, location, contact, type;
    
// Partial constructor sets non-required fields to empty string
    public Appointment(int appointmentId, int customerId, int userId, Customer customer, String title, String type) {
        this.appointmentId = appointmentId;
        this.customerId = customerId;
        this.userId = userId;
        this.customer = customer;
        this.title = title;
        this.type = type;
        this.description = "";
        this.location = "";
        this.contact = "";
    }   
// Full constructor
    public Appointment(int appointmentId, int customerId, int userId, Customer customer, String title, String description, String location, String contact, String type) {
        this.appointmentId = appointmentId;
        this.customerId = customerId;
        this.userId = userId;
        this.customer = customer;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.type = type;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    
    
    
}
