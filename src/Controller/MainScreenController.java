
package Controller;

import DAO.AppointmentDAO;
import DAO.CustomerDAO;
import DAO.UserDAO;
import Model.Appointment;
import Model.AppointmentList;
import Model.Customer;
import Model.CustomerList;
import Model.UserList;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
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
    private TableColumn<Appointment, String> monthlyLocationCol;

    @FXML
    private TableColumn<Appointment, String> monthlyContactCol;

    @FXML
    private TableColumn<Appointment, LocalDateTime> monthlyStartTimeCol;

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
    private TableColumn<Customer, String> customerPostalCodeCol;

    @FXML
    private TableColumn<Customer, String> customerCountryCol;

    @FXML
    private TableColumn<Customer, String> customerPhoneCol;
    
    @FXML
    private TextArea appointmentAlertTxt;

    @FXML
    void onActionDeleteAppointment(ActionEvent event) {

    }

    @FXML
    void onActionToModifyAppointment(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();                
        scene = FXMLLoader.load(getClass().getResource("/View/ModifyAppointmentScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionToReports(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();                
        scene = FXMLLoader.load(getClass().getResource("/View/ReportScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionToAddAppointment(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();                
        scene = FXMLLoader.load(getClass().getResource("/View/AddAppointmentScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionToAddCustomer(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();                
        scene = FXMLLoader.load(getClass().getResource("/View/AddCustomerScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    @FXML
    void onActionDeleteCustomer(ActionEvent event) throws SQLException {
        
        Customer customer = customerTableView.getSelectionModel().getSelectedItem();
        CustomerList.deleteFromCustomerList(customer);
        CustomerDAO.deleteDBCustomer(customer.getCustomerId());
    }

    @FXML
    void onActionExit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void onActionToModifyCustomer(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();                
        scene = FXMLLoader.load(getClass().getResource("/View/ModifyCustomerScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    
    // D.   Provide the ability to view the calendar by month and by week.
    @FXML
    void onActionNextMonth(ActionEvent event) {

    }

    @FXML
    void onActionNextWeek(ActionEvent event) {

    }

    @FXML
    void onActionPreviousMonth(ActionEvent event) {

    }

    @FXML
    void onActionPreviousWeek(ActionEvent event) {

    }
    
    // H.   Write code to provide an alert if there is an appointment within 15 minutes of the user’s log-in.
    
    private int alertAppointment() {
        
        for (Appointment appointment : AppointmentList.weeklyAppointments) {
            
        }
        
        return -1;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        // Alert reminder set to non-editable to user can not edit in GUI
        appointmentAlertTxt.setEditable(false);
        try {
            UserList.userList = UserDAO.getAllDBUsers();
        } catch (SQLException ex) {
            Logger.getLogger(MainScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }

        // TableViews for customer, weekly appointments and monthly appointments populated
        try {
            AppointmentList.weeklyAppointments = AppointmentDAO.getAllAppointments();
            AppointmentList.monthlyAppointments = AppointmentList.weeklyAppointments;
            calenderWeeklyView.setItems(AppointmentList.weeklyAppointments);
            
            CustomerList.customerList = CustomerDAO.getAllDBCustomers();
            
            
            weeklyIdCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAppointmentId()).asObject());
            weeklyCustomerCol.setCellValueFactory(new PropertyValueFactory<>("customer"));
            weeklyTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
            weeklyTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            weeklyLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
            weeklyContactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
            weeklyStartTimeCol.setCellValueFactory(new PropertyValueFactory<>("start"));
            weeklyEndTimeCol.setCellValueFactory(new PropertyValueFactory<>("end"));
            
            calenderMonthlyView.setItems(AppointmentList.monthlyAppointments);
            
            monthlyIdCol.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
            monthlyCustomerCol.setCellValueFactory(new PropertyValueFactory<>("customer"));
            monthlyTitleCol.setCellValueFactory(new PropertyValueFactory<>("title"));
            monthlyTypeCol.setCellValueFactory(new PropertyValueFactory<>("type"));
            monthlyLocationCol.setCellValueFactory(new PropertyValueFactory<>("location"));
            monthlyContactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
            monthlyStartTimeCol.setCellValueFactory(new PropertyValueFactory<>("start"));
            monthlyEndTimeCol.setCellValueFactory(new PropertyValueFactory<>("end"));
            
            customerTableView.setItems(CustomerList.customerList);
            
            customerIdCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCustomerId()).asObject());;
            customerNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomerName()));
            customerAddressCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getAddress()));
            customerAddress2Col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getAddress2()));
            customerCityCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getCity()));
            customerPostalCodeCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getPostalCode()));
            customerCountryCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getCountry()));
            customerPhoneCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getPhone()));
         
        } catch (SQLException ex) {
            Logger.getLogger(MainScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
}
