
package Model;

// Example constructor

import java.time.LocalDateTime;

//        Appointment testAppointment = new Appointment.AppointmentBuilder(99, 5, 2, testCustomer, "Test Title", "Test Type")
//                .setContact("Test Contact")
//                .setDescription("Test Description")
//                .setLocation("Test Location").build();


public class Appointment {

    private final int appointmentId;
    private final int customerId;
    private final int userId;
    private final Customer customer;
    private final String title;
    private final String type;
    private final LocalDateTime createDate;
    private final String createdBy;
    private String lastUpdateBy;
    private LocalDateTime lastUpdate;
    private final String description; //Optional
    private final String location; //Optional
    private final String contact; //Optional
    
    // Builder pattern handles optional parameters
    public static class AppointmentBuilder {
        
        private final int appointmentId, customerId, userId;
        private final Customer customer;
        private final String title;
        private final String type;
        private final LocalDateTime createDate;
        private final String createdBy;
        private String lastUpdateBy;
        private LocalDateTime lastUpdate;
        private String description, location, contact; //Optional
        
        public AppointmentBuilder(int appointmentId, int customerId, int userId, Customer customer, String title, 
                                    String type, LocalDateTime createDate, String createdBy, LocalDateTime lastUpdate, String lastUpdateBy) {
            
        this.appointmentId = appointmentId;
        this.customerId = customerId;
        this.userId = userId;
        this.customer = customer;
        this.title = title;
        this.type = type;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
        
        }
        
        public AppointmentBuilder setDescription(String description) {
            this.description = description;
            return this;
        }
        
        public AppointmentBuilder setLocation(String location) {
            this.location = location;
            return this;
        }
        
        public AppointmentBuilder setContact(String contact) {
            this.contact = contact;
            return this;
        }
        
        public Appointment build() {
            // must be called after optional setters
            return new Appointment(this);
        }
        
    }

    private Appointment(AppointmentBuilder builder) {
        this.appointmentId = builder.appointmentId;
        this.customerId = builder.customerId;
        this.userId = builder.userId;
        this.customer = builder.customer;
        this.title = builder.title;
        this.type = builder.type;
        this.createDate = builder.createDate;
        this.createdBy = builder.createdBy;
        this.lastUpdate = builder.lastUpdate;
        this.lastUpdateBy = builder.lastUpdateBy;
        this.description = builder.description;
        this.location = builder.location;
        this.contact = builder.contact;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getUserId() {
        return userId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public String getTitle() {
        return title;
    }

    public String getType() {
        return type;
    }

    public String getDescription() {
        return description;
    }

    public String getLocation() {
        return location;
    }

    public String getContact() {
        return contact;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }


    
    
    
}
