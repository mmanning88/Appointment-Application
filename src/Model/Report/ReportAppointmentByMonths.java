
package Model.Report;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javafx.collections.ObservableList;

/**
 *
 * @author Matthew Manning
 */
public class ReportAppointmentByMonths extends Report implements Reportable{
    
    public ReportAppointmentByMonths() {
        this.reportTitle = "Number of Appointments by Month, report generated " + LocalDate.now();
    }
    
    
    
//    @Test
//public void whenCollectorsJoining_thenPrintCustom() {
//    List<Integer> intList = Arrays.asList(1, 2, 3);
//    String result = intList.stream()
//      .map(n -> String.valueOf(n))
//      .collect(Collectors.joining("-", "{", "}"));
//  
//    System.out.println(result);
//}
    
    
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
