
package Model.Report;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.collections.ObservableList;

/*
@author Matthew Manning
*/
public abstract class Report {
    
    protected String reportTitle;
    protected String content;

    public Report(String reportTitle, String content) {
        this.reportTitle = reportTitle;
        this.content = content;
    }
    
    

    public Report() {

    }

    public String getReportTitle() {
        return reportTitle;
    }

    public void setReportTitle(String reportTitle) {
        this.reportTitle = reportTitle;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
    

    
}
