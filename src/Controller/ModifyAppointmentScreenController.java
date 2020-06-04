
package Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;


public class ModifyAppointmentScreenController implements Initializable {
    
    @FXML
    private TableView<?> customerTableView;

    @FXML
    private TextField customerIdTxt;

    @FXML
    private TextField appointmentIdTxt;

    @FXML
    private TextField customerNameTxt;

    @FXML
    private TextField appointmentTitleTxt;

    @FXML
    private TextArea appointmentDescTxt;

    @FXML
    private TextField appointmentTypeTxt;

    @FXML
    private TextField appointmentContactTxt;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    void onActionSaveAppointment(ActionEvent event) {

    }

    @FXML
    void onActionToMainScreen(ActionEvent event) {

    }
    
    


    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }    
    
}
