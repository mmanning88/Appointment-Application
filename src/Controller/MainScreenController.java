
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
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
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
    private TableColumn<Appointment, String> weeklyCustomerCol;

    @FXML
    private TableColumn<Appointment, String> weeklyTitleCol;

    @FXML
    private TableColumn<Appointment, String> weeklyTypeCol;

    @FXML
    private TableColumn<Appointment, String> weeklyLocationCol;

    @FXML
    private TableColumn<Appointment, String> weeklyContactCol;

    @FXML
    private TableColumn<Appointment, String> weeklyStartTimeCol;

    @FXML
    private TableColumn<Appointment, String> weeklyEndTimeCol;

    @FXML
    private TextArea descriptionTxtAreaWeekly;

    @FXML
    private TableView<Appointment> calenderMonthlyView;

    @FXML
    private TableColumn<Appointment, Integer> monthlyIdCol;

    @FXML
    private TableColumn<Appointment, String> monthlyCustomerCol;

    @FXML
    private TableColumn<Appointment, String> monthlyTitleCol;

    @FXML
    private TableColumn<Appointment, String> monthlyTypeCol;

    @FXML
    private TableColumn<Appointment, String> monthlyLocationCol;

    @FXML
    private TableColumn<Appointment, String> monthlyContactCol;

    @FXML
    private TableColumn<Appointment, String> monthlyStartTimeCol;

    @FXML
    private TableColumn<Appointment, String> monthlyEndTimeCol;

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
    private TableColumn<Customer, Integer> customerActiveCol;
    
    @FXML
    private TextArea appointmentAlertTxt;
    
    @FXML
    private Label currentWeek, currentMonth;

    @FXML
    void onActionDeleteAppointment(ActionEvent event) throws SQLException {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Delete Appointment Confirmation");
        alert.setContentText("Are you sure?");
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            Appointment appointment = calenderWeeklyView.getSelectionModel().getSelectedItem();
            AppointmentDAO.deleteAppointmentDB(appointment.getAppointmentId());
            AppointmentList.deleteFromAppointmentList(appointment);
        }
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

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Delete Customer Confirmation");
        alert.setContentText("Are you sure?");
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK) {
            // DAO action must be performed first, uses CustomerList to find address
            Customer customer = customerTableView.getSelectionModel().getSelectedItem();
            CustomerDAO.deleteDBCustomer(customer.getCustomerId());
            CustomerList.deleteFromCustomerList(customer);
        }

        

    }

    @FXML
    void onActionExit(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void onActionToModifyCustomer(ActionEvent event) throws IOException {
        
        Customer customer = customerTableView.getSelectionModel().getSelectedItem();
        
        if (customer == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Warning Dialog");
            alert.setContentText("Please select customer before modifying.");
            alert.showAndWait();
            return;
        }
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/ModifyCustomerScreen.fxml"));
        loader.load();
        
        ModifyCustomerScreenController controller = loader.getController();
        controller.transferCustomer(customer);

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();                
        Parent newScene = loader.getRoot();
        stage.setScene(new Scene(newScene));
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
            AppointmentList.weeklyAppointments = AppointmentDAO.getAllAppointmentsDB();
            AppointmentList.monthlyAppointments = AppointmentList.weeklyAppointments;
            calenderWeeklyView.setItems(AppointmentList.weeklyAppointments);
            
            CustomerList.customerList = CustomerDAO.getAllDBCustomers();
            
            
            weeklyIdCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAppointmentId()).asObject());
            weeklyCustomerCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomer().getCustomerName()));
            weeklyTitleCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
            weeklyTypeCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));
            weeklyLocationCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLocation()));
            weeklyContactCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getContact()));
            weeklyStartTimeCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDateTimeStartString()));
            weeklyEndTimeCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDateTimeEndString()));
            
            calenderMonthlyView.setItems(AppointmentList.monthlyAppointments);
            
            monthlyIdCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAppointmentId()).asObject());
            monthlyCustomerCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomer().getCustomerName()));
            monthlyTitleCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
            monthlyTypeCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));
            monthlyLocationCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLocation()));
            monthlyContactCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getContact()));
            monthlyStartTimeCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDateTimeStartString()));
            monthlyEndTimeCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDateTimeEndString()));
            
            customerTableView.setItems(CustomerList.customerList);
            
            customerIdCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCustomerId()).asObject());
            customerNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomerName()));
            customerAddressCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getAddress()));
            customerAddress2Col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getAddress2()));
            customerCityCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getCity()));
            customerPostalCodeCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getPostalCode()));
            customerCountryCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getCountry()));
            customerPhoneCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getPhone()));
            customerActiveCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getActive()).asObject());
            
        } catch (SQLException ex) {
            Logger.getLogger(MainScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
}
