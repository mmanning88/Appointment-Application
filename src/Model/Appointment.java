
package Model;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;


/*
@author Matthew Manning
*/
public class Appointment implements Comparable<Appointment>{

    private final int appointmentId;
    private final int customerId;
    private final Customer customer;
    private final User user;
    private final String title;
    private final String type;
    private final ZonedDateTime start;
    private final ZonedDateTime end;
    private final String description; //Optional
    private final String location; //Optional
    private final String contact; //Optional

    // Builder pattern handles optional parameters
    public static class AppointmentBuilder {
        
        private final int appointmentId, customerId;
        private final Customer customer;
        private final User user;
        private final String title;
        private final String type;
        private final ZonedDateTime start;
        private final ZonedDateTime end;

        private String description, location, contact; //Optional

        
        public AppointmentBuilder(int appointmentId, int customerId, Customer customer, User user, String title, 
                                    String type, ZonedDateTime start, ZonedDateTime end) {
            
        this.appointmentId = appointmentId;
        this.customerId = customerId;
        this.customer = customer;
        this.user = user;
        this.title = title;
        this.type = type;
        this.start = start;
        this.end = end;

        
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
        this.customer = builder.customer;
        this.user = builder.user;
        this.title = builder.title;
        this.type = builder.type;
        this.start = builder.start;
        this.end = builder.end;
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

    public User getUser() {
        return user;
    }

    public ZonedDateTime getStart() {
        return start;
    }

    public ZonedDateTime getEnd() {
        return end;
    }

    public String getDateTimeStartString() {
        String date = String.valueOf(this.start.toLocalDate());
        String time = String.valueOf(this.start.toLocalTime());
        String dateTime = date + " " + time;
        return dateTime;
    }
    
        public String getDateTimeEndString() {
        String date = String.valueOf(this.end.toLocalDate());
        String time = String.valueOf(this.end.toLocalTime());
        String dateTime = date + " " + time;
        return dateTime;
    }
    // Appointments compared by start time for sorting by appointment time    
    @Override
    public int compareTo(Appointment appointment) {
        return getStart().toLocalDateTime().compareTo(appointment.getStart().toLocalDateTime());
    }
        
}
