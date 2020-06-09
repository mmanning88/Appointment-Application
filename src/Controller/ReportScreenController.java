
package Controller;

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
import javafx.stage.Stage;
/*
@author Matthew Manning
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
    void onActionAppointmentByCity(ActionEvent event) {
        
    }
    
    @FXML
    void onActionAppointmentTypesByMonth(ActionEvent event) {

    }

    @FXML
    void onActionConsultantSchedule(ActionEvent event) {

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
