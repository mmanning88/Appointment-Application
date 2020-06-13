
package Model;

// Example constructor

import Utilities.DateTimeFormat;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.TimeZone;

//        Appointment testAppointment = new Appointment.AppointmentBuilder(99, 5, 2, testCustomer, "Test Title", "Test Type")
//                .setContact("Test Contact")
//                .setDescription("Test Description")
//                .setLocation("Test Location").build();

/*
@author Matthew Manning
*/
public class Appointment {

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
    
    
//            LocalDate parisDate = LocalDate.of(2019, 5, 28);
//        LocalTime parisTime = LocalTime.of(02, 00);
//        ZoneId parisZoneId = ZoneId.of("Europe/Paris");
//        ZonedDateTime parisZDT = ZonedDateTime.of(parisDate, parisTime, parisZoneId);
//        ZoneId localZoneId = ZoneId.of(TimeZone.getDefault().getID());
//        
//        Instant parisToGMTInstant = parisZDT.toInstant();
//        ZonedDateTime parisToLocalZDT = parisZDT.withZoneSameInstant(localZoneId);
//        ZonedDateTime gmtToLocalZDT = parisToGMTInstant.atZone(localZoneId);
//        
//        System.out.println("Local: " + ZonedDateTime.now());
//        System.out.println("Paris: " + parisZDT);
//        System.out.println("Paris->GMT: " + parisToGMTInstant);
//        System.out.println("GMT->Local: " + gmtToLocalZDT);
//        System.out.println("GMT->LocaleDate: " + gmtToLocalZDT.toLocalDate());
//        System.out.println("GMT->LocaleTime: " + gmtToLocalZDT.toLocalTime());
//        
//        String date = String.valueOf(gmtToLocalZDT.toLocalDate());
//        String time = String.valueOf(gmtToLocalZDT.toLocalTime());
//        String dateTime = date + " " + time;
//        System.out.println(dateTime); //MySql insertion format


    
    
    
}
