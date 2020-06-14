
package Model.Report;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javafx.collections.ObservableList;

/**
 * @author Matthew Manning
 */
public class AppointmentByMonths extends Report implements Reportable{
    
    public AppointmentByMonths() {
        this.reportTitle = "Number of Appointments by Month, report generated " + LocalDate.now();
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
