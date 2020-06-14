/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model.Report;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;
import javafx.collections.ObservableList;

/**
 *
 * @author Matthew Manning
 */
public class ReportConsultantSchedule extends Report implements Reportable{

    public ReportConsultantSchedule() {
        this.reportTitle = "Consultant Appointment Schedule, report generated " + LocalDate.now();
    }



    public void setContent() {
        
    }
    
    @Override
    public String collectAndDisplay() {
        //ArrayList data = setContent();
        StringBuilder builder = new StringBuilder();
        String titleString = this.reportTitle;
        String breakLine = "\n";
        
        builder.append(titleString).append(breakLine).append(breakLine);

        return null;
    }

    



    
}
