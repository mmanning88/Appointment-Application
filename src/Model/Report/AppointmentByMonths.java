
package Model.Report;

import Model.Appointment;
import Model.AppointmentList;
import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import javafx.collections.ObservableList;

/**
 * @author Matthew Manning
 */

public class AppointmentByMonths extends Report implements Reportable{
    
    public AppointmentByMonths() {
        this.reportTitle = "Number of Appointments by Month, report generated " + LocalDate.now();
    }
    
    // Inner class keeps track of type, month, and count of the same objects
    private class AppointmentMonth {

        private final String appointmentMonth;
        private final int appointmentMonthInt;
        private final String type; 
        private int count;

        public AppointmentMonth(Appointment appointment) {
            this.appointmentMonth = appointment.getStart().toLocalDateTime().getMonth().toString();
            this.appointmentMonthInt = appointment.getStart().getMonthValue();
            this.type = appointment.getType();
            this.count = 1;
        }

        public String getAppointmentMonth() {
            return appointmentMonth;
        }

        public String getType() {
            return type;
        }

        public int getAppointmentMonthInt() {
            return appointmentMonthInt;
        }

        public int getCount() {
            return count;
        }
        
        public void incrementCount() {
            this.count++;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append(this.count).append(" ").append(this.type).append(" for the month of ").append(this.appointmentMonth);
            return builder.toString();
        }
        
        // AppointmentMonth objects equal if type and month int is equal
        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            } 
            else if (obj == null) {
                return false;
            }
            else if (obj instanceof AppointmentMonth) {
                AppointmentMonth am = (AppointmentMonth) obj;
                if (am.getType().equals(this.type) && am.getAppointmentMonthInt() == this.appointmentMonthInt) {
                    return true;
                }
            }
            return false;
        }
    }

    
    private ArrayList setContent() {
        ArrayList<AppointmentMonth> appointmentMonthList = new ArrayList<>();
        // if AppointmentMonth with same type and month exists, inrement by one, else create new AppointmentMonth
        for (Appointment appointment : AppointmentList.allAppointments) {
            AppointmentMonth appointmentMonth = new AppointmentMonth(appointment);
            if (!checkAppointmentMonth(appointmentMonthList, appointmentMonth)) {
                appointmentMonthList.add(appointmentMonth);
            } 
            
        }
        return appointmentMonthList;
    }

    private boolean checkAppointmentMonth(ArrayList<AppointmentMonth> appointmentMonthList, AppointmentMonth appointmentMonth) {
        for (AppointmentMonth am : appointmentMonthList) {
            if (am.equals(appointmentMonth)) {
                am.incrementCount();
                return true;
            }       
        }
        return false;
    }
    
    public String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month-1];
    }

    // Creates formatted string of report data
    @Override
    public String collectAndDisplay() {
        ArrayList<AppointmentMonth> data = setContent();
        StringBuilder builder = new StringBuilder();
        builder.append(this.reportTitle).append("\n\n");
        
        for (int i = 1; i <= 12; i++) {
            builder.append(getMonth(i)).append("\n");
            for (AppointmentMonth appointmentMonth : data) {
                if (appointmentMonth.getAppointmentMonthInt() == i) {
                    builder.append(appointmentMonth.toString()).append("\n");
                }
            }
            builder.append("\n");
        }
        this.content = builder.toString();
        return this.content;
    }




    
    
}
