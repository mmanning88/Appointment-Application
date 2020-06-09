
package Controller;

import DAO.AppointmentDAO;
import Model.Appointment;
import Model.Customer;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
/*
@author Matthew Manning
*/
public class MainScreenController implements Initializable {
    
    Stage stage;
    Parent scene;

 @FXML
    private TableView<Appointment> calenderWeeklyView;

    @FXML
    private TableColumn<Appointment, Integer> weeklyIdCol;

    @FXML
    private TableColumn<Appointment, Customer> weeklyCustomerCol;

    @FXML
    private TableColumn<Appointment, String> weeklyTitleCol;

    @FXML
    private TableColumn<Appointment, String> weeklyTypeCol;

    @FXML
    private TableColumn<Appointment, String> weeklyDescCol;

    @FXML
    private TableColumn<Appointment, String> weeklyLocationCol;

    @FXML
    private TableColumn<Appointment, String> weeklyContactCol;

    @FXML
    private TableColumn<Appointment, LocalDateTime> weeklyStartTimeCol;

    @FXML
    private TableColumn<Appointment, LocalDateTime> weeklyEndTimeCol;

    @FXML
    private TextArea descriptionTxtAreaWeekly;

    @FXML
    private TableView<Appointment> calenderMonthlyView;

    @FXML
    private TableColumn<Appointment, Integer> monthlyIdCol;

    @FXML
    private TableColumn<Customer, String> monthlyCustomerCol;

    @FXML
    private TableColumn<Appointment, String> monthlyTitleCol;

    @FXML
    private TableColumn<Appointment, String> monthlyTypeCol;

    @FXML
    private TableColumn<Appointment, String> monthlyDescCol;

    @FXML
    private TableColumn<Appointment, String> monthlyLocationCol;

    @FXML
    private TableColumn<Appointment, String> monthlyContactCol;

    @FXML
    private TableColumn<Appointment, LocalDateTime> monthlyStartTimeCo;

    @FXML
    private TableColumn<Appointment, LocalDateTime> monthlyEndTimeCol;

    @FXML
    private TextArea descriptionTxtAreaMonthly;

    @FXML
    private TableView<Customer> customerTableView;

    @FXML
    private TableColumn<Customer, Integer> customerIdCol;

    @FXML
    private TableColumn<Customer, String> customerNameCol;

    @FXML
    private TableColumn<Customer, String> customerAddressCol;

    @FXML
    private TableColumn<Customer, String> customerAddress2Col;

    @FXML
    private TableColumn<Customer, String> customerCityCol;

    @FXML
    private TableColumn<Customer, String> customerCountryCol;

    @FXML
    private TableColumn<Customer, String> customerPhoneCol;

    @FXML
    void OnActionDeleteAppointment(ActionEvent event) {

    }

    @FXML
    void OnActionModifyAppointment(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();                
        scene = FXMLLoader.load(getClass().getResource("/View/ModifyAppointmentScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void OnActionToReports(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();                
        scene = FXMLLoader.load(getClass().getResource("/View/ReportScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionAddAppointment(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();                
        scene = FXMLLoader.load(getClass().getResource("/View/AddAppointmentScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionAddCustomer(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();                
        scene = FXMLLoader.load(getClass().getResource("/View/AddCustomerScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionDeleteCustomer(ActionEvent event) {

    }

    @FXML
    void onActionExit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void onActionModifyCustomer(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();                
        scene = FXMLLoader.load(getClass().getResource("/View/ModifyCustomerScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {     
            calenderWeeklyView.setItems(AppointmentDAO.getAllAppointments());
        } catch (SQLException ex) {
            Logger.getLogger(MainScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        weeklyIdCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        weeklyCustomerCol.setCellValueFactory(new PropertyValueFactory<>("Customer"));
        weeklyTitleCol.setCellValueFactory(new PropertyValueFactory<>("Title"));
        weeklyTypeCol.setCellValueFactory(new PropertyValueFactory<>("Type"));
        weeklyDescCol.setCellValueFactory(new PropertyValueFactory<>("Description"));
        weeklyLocationCol.setCellValueFactory(new PropertyValueFactory<>("Location"));
        weeklyContactCol.setCellValueFactory(new PropertyValueFactory<>("Contact"));
        weeklyStartTimeCol.setCellValueFactory(new PropertyValueFactory<>("Start Time"));
        weeklyEndTimeCol.setCellValueFactory(new PropertyValueFactory<>("End Time"));
        
    }    
    
}
