
package Controller;

import Model.Report.CityAppointments;
import Model.Report.ConsultantSchedule;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
/*
* @author Matthew Manning
*I.  Provide the ability to generate each  of the following reports:
* - number of appointment types by month
* - the schedule for each consultant
* - one additional report of your choice
*/

public class ReportScreenController implements Initializable {
    
    Stage stage;
    Parent scene;
    
    @FXML
    private RadioButton appointmentByMonthRB;

    @FXML
    private RadioButton consultantScheduleRB;

    @FXML
    private RadioButton appointmentByCityRB;
        
    @FXML
    private TextArea reportTxtArea;

    @FXML
    void onActionAppointmentByCity(ActionEvent event) {

        CityAppointments report = new CityAppointments();
        reportTxtArea.setText(report.collectAndDisplay());
    }
    
    @FXML
    void onActionAppointmentTypesByMonth(ActionEvent event) {

    }

    @FXML
    void onActionConsultantSchedule(ActionEvent event) {
        ConsultantSchedule report = new ConsultantSchedule();
        reportTxtArea.setText(report.collectAndDisplay());
    }
    

    @FXML
    void onActionToMainScreen(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();                
        scene = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        

    }    
    
}
