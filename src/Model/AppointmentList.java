
package Model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalAdjusters;
import static java.time.temporal.TemporalQueries.zoneId;
import java.util.Iterator;
import java.util.TimeZone;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/*
@author Matthew Manning
*/
public class AppointmentList {
    
    public static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();
    public static ObservableList<Appointment> weeklyAppointments = FXCollections.observableArrayList();
    public static ObservableList<Appointment> monthlyAppointments = FXCollections.observableArrayList();
    

    public static ObservableList<Appointment> getAllAppointments() {
        return allAppointments;
    }
    
    public static ObservableList<Appointment> getWeeklyAppointments() {
        return weeklyAppointments;
    }

    public static ObservableList<Appointment> getMonthlyAppointments() {
        return monthlyAppointments;
    }
    
    public static void addToAppointmentList(Appointment appointment) {   
        allAppointments.add(appointment);
        weeklyAppointments.add(appointment);
        monthlyAppointments.add(appointment);     
    }
    
    public static void deleteFromAppointmentList(Appointment appointment) {
        allAppointments.remove(appointment);
        weeklyAppointments.remove(appointment);
        monthlyAppointments.remove(appointment);
    }
    
    public static void updateAppointmentList(int index, Appointment appointment) {
        weeklyAppointments.set(index, appointment);
        monthlyAppointments.set(index, appointment);
        allAppointments.set(index, appointment);
    }
    
    public static Appointment searchAppointmentList(int appointmentId) {

        for (Appointment appointment : getWeeklyAppointments()) {
            if (appointment.getAppointmentId() == appointmentId) {
                return appointment;
            }
        }
        return null;
    
    }
    
    public static int getAppointmentNextId() {
        
        int maxId = 0;
        for (Appointment appointment : getWeeklyAppointments()) {
            if (appointment.getAppointmentId() > maxId) {
                    maxId = appointment.getAppointmentId();
            }
        }
        return maxId + 1;
    }
    


    public static void sortByCurrentMonth(LocalDate date) {

        LocalDate firstOfCurrentMonth = date.with(TemporalAdjusters.firstDayOfMonth());
        LocalDate lastOfCurrentMonth = date.with(TemporalAdjusters.lastDayOfMonth());
               
        monthlyAppointments.clear();
        
        // Must use iterator when iterating on collection being modified
        for (Iterator<Appointment> iterator = allAppointments.iterator(); iterator.hasNext();) {
            Appointment appointment = iterator.next();
            if (appointment.getStart().toLocalDate().isAfter(firstOfCurrentMonth) && appointment.getStart().toLocalDate().isBefore(lastOfCurrentMonth)) {
                monthlyAppointments.add(appointment);
            }
        }
    }
    
    public static void sortByCurrentWeek(LocalDate date) {
        
        LocalDate firstWeekday = date.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY));
        LocalDate lastWeekday = date.with(TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY));
        
        weeklyAppointments.clear();
        
        // Must use iterator when iterating on collection being modified
        for (Iterator<Appointment> iterator = allAppointments.iterator(); iterator.hasNext();) {
            Appointment appointment = iterator.next();
            if ((appointment.getStart().toLocalDate().isEqual(firstWeekday) || appointment.getStart().toLocalDate().isAfter(firstWeekday)) 
                    && (appointment.getEnd().toLocalDate().isBefore(lastWeekday)) || appointment.getEnd().toLocalDate().isEqual(lastWeekday)) {
                weeklyAppointments.add(appointment);
            }
        }
        
    }
            
    
        
}
