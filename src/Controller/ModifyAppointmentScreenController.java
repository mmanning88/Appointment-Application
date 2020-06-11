/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Customer;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Matthew Manning
 */
public class ModifyAppointmentScreenController implements Initializable {
    
    Stage stage;
    Parent scene;

    @FXML
    private TableView<Customer> customerTableView;

    @FXML
    private TableColumn<?, ?> customerIdCol;

    @FXML
    private TableColumn<?, ?> customerNameCol;

    @FXML
    private TableColumn<?, ?> customerAddressCol;

    @FXML
    private TableColumn<?, ?> customerAddress2Col;

    @FXML
    private TableColumn<?, ?> customerCityCol;

    @FXML
    private TableColumn<?, ?> customerPostalCodeCol;

    @FXML
    private TableColumn<?, ?> customerCountryCol;

    @FXML
    private TableColumn<?, ?> customerPhoneCol;
    
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
    private TextField startTimeTxt;

    @FXML
    private DatePicker endDatePicker;

    @FXML
    private TextField endTimeTxt;

    @FXML
    void onActionSaveModifiedAppointment(ActionEvent event) {

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
        // TODO
    }    
    
}
