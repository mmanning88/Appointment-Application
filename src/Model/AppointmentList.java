/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Matthew Manning
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
    
    
    
        
}
