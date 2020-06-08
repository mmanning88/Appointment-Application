
package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/*
@author Matthew Manning
*/
public class AppointmentList {
    
    public static ObservableList<Appointment> weeklyAppointments = FXCollections.observableArrayList();
    public static ObservableList<Appointment> monthlyAppointments = FXCollections.observableArrayList();

    public static ObservableList<Appointment> getWeeklyAppointments() {
        return weeklyAppointments;
    }

    public static ObservableList<Appointment> getMonthlyAppointments() {
        return monthlyAppointments;
    }
    
    public static void addToAppointmentList(Appointment appointment) {       
        weeklyAppointments.add(appointment);
        monthlyAppointments.add(appointment);     
    }
    
    public static void deleteFromAppointmentList(Appointment appointment) {
        weeklyAppointments.remove(appointment);
        monthlyAppointments.remove(appointment);
    }
    
    public static void updateAppointmentList(int index, Appointment appointment) {
        weeklyAppointments.set(index, appointment);
        monthlyAppointments.set(index, appointment);
    }
    
    public static Appointment searchAppointmentList(int appointmentId) {

        for (Appointment appointment : getWeeklyAppointments()) {
            if (appointment.getAppointmentId() == appointmentId) {
                return appointment;
            }
        }
        return null;
    
    }
    
    
    
        
}
