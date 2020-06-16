
package Model.Report;

import java.util.ArrayList;

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

    public ArrayList setContent() {
        return null;
    }
        
}
