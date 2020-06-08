
package Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
/*
@author Matthew Manning
*/
public class ReportScreenController implements Initializable {
    
    
    @FXML
    private RadioButton appointmentByMonthRB;

    @FXML
    private RadioButton consultantScheduleRB;

    @FXML
    private RadioButton appointmentByCityRB;

    @FXML
    void onActionAppointmentByCity(ActionEvent event) {
        
    }
    
    @FXML
    void onActionAppointmentTypesByMonth(ActionEvent event) {

    }

    @FXML
    void onActionConsultantSchedule(ActionEvent event) {

    }

    @FXML
    void onActionToMainScreen(ActionEvent event) {

    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }    
    
}
