
package Controller;

import Model.Appointment;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
/*
@author Matthew Manning
*/
public class MainScreenController implements Initializable {
    


    @FXML
    private TableView<Appointment> calenderWeeklyView;

    @FXML
    private TextArea descriptionTxtAreaWeekly;

    @FXML
    private TableView<Appointment> calenderMonthlyView;

    @FXML
    private TextArea descriptionTxtAreaMonthly;

    private Appointment selectedAppointment;

    @FXML
    void OnActionDeleteAppointment(ActionEvent event) {

    }

    @FXML
    void OnActionModifyAppointment(ActionEvent event) {

    }

    @FXML
    void OnActionToReports(ActionEvent event) {

    }

    @FXML
    void onActionAddAppointment(ActionEvent event) {

    }

    @FXML
    void onActionAddCustomer(ActionEvent event) {

    }

    @FXML
    void onActionDeleteCustomer(ActionEvent event) {

    }

    @FXML
    void onActionExit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void onActionModifyCustomer(ActionEvent event) {

    }

    @FXML
    void onActionMonthView(ActionEvent event) {

    }

    @FXML
    void onActionWeekView(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }    
    
}
