/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Report;

import Model.Appointment;
import Model.AppointmentList;
import Model.User;
import Utilities.DateTimeFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 *
 * @author Matthew Manning
 */
public class ConsultantSchedule extends Report implements Reportable{
    
    
    public ConsultantSchedule() {
        this.reportTitle = "Consultant Appointment Schedule, report generated " + LocalDate.now();
    }

    // Inner class holds user and appointments user has made and sorts by date
    private class UserReport {
        private final User user;
        private ArrayList<Appointment> userAppointments = new ArrayList<>();

        public UserReport(User user) {
            this.user = user;
        }

        public User getUser() {
            return user;
        }

        public ArrayList<Appointment> getUserAppointments() {
            return userAppointments;
        }
        
        public void addAppointment(Appointment appointment) {
            userAppointments.add(appointment);
        }
        
        public boolean checkForAppointment(Appointment appointment) {
            if (userAppointments.contains(appointment)) {
                return true;
            }
            return false;
        }
        
        public void sortAppointments() {
            Collections.sort(userAppointments);
        }
        
        @Override
        public String toString() {
            int index = 1;
            StringBuilder builder = new StringBuilder();
            for (Appointment userAppointment : userAppointments) {
                builder.append(index).append("\t\t");
                index++;
                builder.append("From ").append(userAppointment.getStart().format(DateTimeFormat.formatter));
                builder.append(" to ").append(userAppointment.getEnd().format(DateTimeFormat.formatter)).append("\n");
            }
            
            String toString = builder.toString();
            return toString;
        }
        

        
    }

    public ArrayList setContent() {
        ArrayList<UserReport> userReportsList = new ArrayList<>();
        for (Appointment appointment : AppointmentList.allAppointments) {
            if (!checkUserReports(userReportsList, appointment.getUser())) {
                UserReport newUR = new UserReport(appointment.getUser());
                userReportsList.add(newUR);
            }
            for (UserReport userReport : userReportsList) {
                if (!userReport.checkForAppointment(appointment) && (appointment.getUser().equals(userReport.getUser()))) {
                    userReport.addAppointment(appointment);
                }
            }

        }
        for (UserReport userReport : userReportsList) {
            userReport.sortAppointments();
        }
                
        return userReportsList;
        
    }
    
    private boolean checkUserReports(ArrayList<UserReport> reportList, User user) {
        for (UserReport userReport : reportList) {
            if (userReport.getUser().equals(user)) {
                return true;
            }
        }
        return false;
    }
    

    // Creates formatted string of report data
    @Override
    public String collectAndDisplay() {
        ArrayList<UserReport> data = setContent();
        StringBuilder builder = new StringBuilder();
        String titleString = this.reportTitle;        
        builder.append(titleString).append("\n\n");
        
        for (UserReport userReport : data) {
            builder.append("User: ").append(userReport.getUser().getUserName()).append("\n");
            builder.append(userReport.toString());
        }
        this.content = builder.toString();
        return this.content;
    }

    



    
}
