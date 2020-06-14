
package Controller;

import DAO.AppointmentDAO;
import DAO.CustomerDAO;
import DAO.UserDAO;
import Model.Appointment;
import Model.AppointmentList;
import Model.Customer;
import Model.CustomerList;
import Model.UserList;
import Utilities.DateTimeFormat;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.TimeZone;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
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
    
    private final ZoneId localTZ = ZoneId.of(TimeZone.getDefault().getID());
    private LocalDate currentDateMonthly;
    private LocalDate currentDateWeekly;
    
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
        
        Appointment appointmentMonth = calenderMonthlyView.getSelectionModel().getSelectedItem();
        Appointment appointmentWeek = calenderWeeklyView.getSelectionModel().getSelectedItem();
        
        Appointment appointment = validateAppointmentSelection(appointmentWeek, appointmentMonth);
        
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/View/ModifyAppointmentScreen.fxml"));
        loader.load();
        
        ModifyAppointmentScreenController controller = loader.getController();
        controller.transferAppointment(appointment);

        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();                
        Parent newScene = loader.getRoot();
        stage.setScene(new Scene(newScene));
        stage.show();
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
    
    public Appointment validateAppointmentSelection(Appointment weeklyAppointment, Appointment monthlyAppointment) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if (monthlyAppointment != weeklyAppointment && monthlyAppointment != null && weeklyAppointment != null) {
            alert.setTitle("Warning Dialog");
            alert.setContentText("Please select one appointment before modifying.");
            alert.showAndWait();
            return null;
        } else if (monthlyAppointment == null && weeklyAppointment == null) {
            alert.setTitle("Warning Dialog");
            alert.setContentText("Please select one appointment before modifying.");
            alert.showAndWait();
            return null;
        }
        
        return weeklyAppointment;
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


    
    // D.   Provide the ability to view the calendar by month and by week.
    @FXML
    void onActionNextMonth(ActionEvent event) {
        currentDateMonthly = currentDateMonthly.plusMonths(1);
        currentMonth.setText(DateTimeFormat.dateToMonth(currentDateMonthly));
        populateMonthlyTable();
    }
    
    @FXML
    void onActionPreviousMonth(ActionEvent event) {
        currentDateMonthly = currentDateMonthly.plusMonths(-1);
        currentMonth.setText(DateTimeFormat.dateToMonth(currentDateMonthly));
        populateMonthlyTable();
    }

    @FXML
    void onActionNextWeek(ActionEvent event) {
        currentDateWeekly = currentDateWeekly.plusWeeks(1);
        currentWeek.setText(DateTimeFormat.dateToWeek(currentDateWeekly));
        populateWeeklyTable();
    }

    @FXML
    void onActionPreviousWeek(ActionEvent event) {
        currentDateWeekly = currentDateWeekly.plusWeeks(-1);
        currentWeek.setText(DateTimeFormat.dateToWeek(currentDateWeekly));
        populateWeeklyTable();
    }

    // Check is any same day appointment is within 15 minutes of starting
    private void alertAppointment() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        LocalDateTime currentTime = LocalDateTime.now();
        long interval;
        for (Appointment appointment : AppointmentList.weeklyAppointments) {
            LocalDateTime startTime = appointment.getStart().toLocalDateTime();
            interval = ChronoUnit.MINUTES.between(currentTime, startTime);
            if (interval > 0 && interval <= 15) {
                alert.setTitle("Upcoming appointment(s)");
                alert.setContentText("There is an appointment starting in " + interval + " minutes");
                alert.showAndWait();
                System.out.println("You have an event in " + interval + " approx minute(s)");
                appointmentAlertTxt.setText("Appointment with " +  appointment.getCustomer().getCustomerName() + " in " + interval + " minutes.");
            } 
        }


    }
    
    // Create listener so only one appointment can be selected at a time
    private void appointmentListener() {
        calenderWeeklyView.getSelectionModel().selectedItemProperty().addListener((obs, oldAppointment, newAppointment) -> {
            if (newAppointment != null) {
                calenderMonthlyView.getSelectionModel().clearSelection();
                descriptionTxtAreaWeekly.setText(newAppointment.getDescription());
            }
        });

        calenderMonthlyView.getSelectionModel().selectedItemProperty().addListener((obs, oldAppointment, newAppointment) -> {
            if (newAppointment != null) {
                calenderWeeklyView.getSelectionModel().clearSelection();
                descriptionTxtAreaMonthly.setText(newAppointment.getDescription());
            }
        });
    }
    
    private void populateMonthlyTable() {
        AppointmentList.sortByCurrentMonth(currentDateMonthly);
        calenderMonthlyView.setItems(AppointmentList.monthlyAppointments);
    }
    
    private void populateWeeklyTable() {
        AppointmentList.sortByCurrentWeek(currentDateWeekly);
        calenderWeeklyView.setItems(AppointmentList.weeklyAppointments);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        currentDateMonthly = LocalDate.now(localTZ);
        currentMonth.setText(DateTimeFormat.dateToMonth(currentDateMonthly));
        currentDateWeekly = LocalDate.now(localTZ);
        currentWeek.setText(DateTimeFormat.dateToWeek(currentDateWeekly));
        descriptionTxtAreaWeekly.setEditable(false);
        descriptionTxtAreaMonthly.setEditable(false);       
        appointmentAlertTxt.setEditable(false);
        
        try {
            UserList.userList = UserDAO.getAllDBUsers();
        } catch (SQLException ex) {
            Logger.getLogger(MainScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }

        // TableViews for customer, weekly appointments and monthly appointments populated
        try {
            AppointmentList.allAppointments = AppointmentDAO.getAllAppointmentsDB();
            AppointmentList.weeklyAppointments.addAll(AppointmentList.allAppointments);
            CustomerList.customerList = CustomerDAO.getAllDBCustomers();
        } catch (SQLException ex) {
            Logger.getLogger(MainScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        populateWeeklyTable();
        calenderWeeklyView.setItems(AppointmentList.weeklyAppointments);

        weeklyIdCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getAppointmentId()).asObject());
        weeklyCustomerCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomer().getCustomerName()));
        weeklyTitleCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitle()));
        weeklyTypeCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getType()));
        weeklyLocationCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLocation()));
        weeklyContactCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getContact()));
        weeklyStartTimeCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDateTimeStartString()));
        weeklyEndTimeCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDateTimeEndString()));

        populateMonthlyTable();
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
            
        appointmentListener();
        alertAppointment(); 
        
    }    
    
}
