
package Controller;

import DAO.AppointmentDAO;
import Model.Appointment;
import Model.AppointmentList;
import Model.Customer;
import Model.CustomerList;
import Model.User;
import Utilities.DateTimeFormat;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;
import java.util.TimeZone;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * @author Matthew Manning
 */
public class AddAppointmentScreenController implements Initializable {
    
    Stage stage;
    Parent scene;

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
    private TextField customerIdTxt;

    @FXML
    private TextField appointmentIdTxt;

    @FXML
    private TextField customerNameTxt;

    @FXML
    private TextField appointmentTitleTxt;

    @FXML
    private TextArea appointmentDescTxt;// Optional

    @FXML
    private ComboBox<String> typeCombo;

    @FXML
    private TextField appointmentContactTxt, appointmentLocationTxt; // Optional

    @FXML
    private DatePicker startDatePicker;
    
    @FXML
    private TextField startTimeHourTxt, startTimeMinuteTxt, endTimeHourTxt, endTimeMinuteTxt;
    
    //Holds next appointment ID
    private int apptIdStatic;


    @FXML
    void onActionSaveAppointment(ActionEvent event) throws SQLException {
        
        int appointmentId = apptIdStatic;
        int customerId = 0;
        
        try {
            customerId = Integer.parseInt(customerIdTxt.getText());
        } catch (NumberFormatException e) {
            Alert alertTime = new Alert(Alert.AlertType.ERROR);
            alertTime.setTitle("Appointment Error");
            alertTime.setContentText("Customer rmust be selected");
            alertTime.showAndWait();
            return;
        }
        Customer customer = CustomerList.searchCustomerList(customerId);
        if (customer.getActive() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Appointment Error");
            alert.setContentText("Customer must be active");
            alert.showAndWait();
            return;
        }
        
        User user = User.currentUser;
        String title = appointmentTitleTxt.getText();
        String type = typeCombo.getValue();
        LocalDate ld = null;
        LocalTime startTime = null;
        LocalTime endTime = null;
        try {
            ld = startDatePicker.getValue();
            Alert alertTime = new Alert(Alert.AlertType.ERROR);
            if (ld == null) {
                alertTime.setTitle("Appointment Date Error");
                alertTime.setContentText("Date must be provided");
                alertTime.showAndWait();
                return;
            }
            if (startTimeHourTxt.getText().isEmpty() || startTimeMinuteTxt.getText().isEmpty() || endTimeHourTxt.getText().isEmpty() || endTimeMinuteTxt.getText().isEmpty()) {
                alertTime.setTitle("Appointment Time Error");
                alertTime.setContentText("Start and end times must not be blank");
                alertTime.showAndWait();
                return;
            }
            //String.format("%03d" , number)
            StringBuilder startBuilder = new StringBuilder();
            StringBuilder endBuilder = new StringBuilder();
            String startHour = String.format("%02d", Integer.valueOf(startTimeHourTxt.getText()));
            String endHour = String.format("%02d", Integer.valueOf(endTimeHourTxt.getText()));
            String startMinute = String.format("%02d", Integer.valueOf(startTimeMinuteTxt.getText()));
            String endMinute = String.format("%02d", Integer.valueOf(endTimeMinuteTxt.getText()));            
            startTime = LocalTime.parse(startBuilder.append(startHour).append(":").append(startMinute).toString());
            endTime = LocalTime.parse(endBuilder.append(endHour).append(":").append(endMinute).toString());
        } catch (DateTimeException eTime) {
            Alert alertTime = new Alert(Alert.AlertType.ERROR);
            alertTime.setTitle("Appointment Error");
            alertTime.setContentText(eTime.getMessage());
            alertTime.showAndWait();
            return;
        }
        LocalDateTime start = null;
        LocalDateTime end = null;
        try {
            start = LocalDateTime.parse(ld + " " + startTime + ":00", DateTimeFormat.formatter);
            end = LocalDateTime.parse(ld + " " + endTime + ":00", DateTimeFormat.formatter);
        } catch (DateTimeParseException e) {
            System.out.println(e.getMessage());
        }
        // Business hours that appointments must be in range of
        LocalTime businessStart = LocalTime.of(9, 00);
        LocalTime businessEnd = LocalTime.of(18, 00);
    
        String description = appointmentDescTxt.getText();
        String location = appointmentLocationTxt.getText();
        String contact = appointmentContactTxt.getText();
        
        
        // Check to see if required fields have been filled out
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if (startTime == null || endTime == null) {
            alert.setTitle("Time Error");
            alert.setContentText("Appointment must have a start and end time");
            alert.showAndWait();
            return;
        } else if (title == null || title.isEmpty()) {
            alert.setTitle("Title Error");
            alert.setContentText("Appointment must have a title");
            alert.showAndWait();
            return;
        } else if (type == null) {
            alert.setTitle("Type Error");
            alert.setContentText("Appointment must have a type");
            alert.showAndWait();
            return;
        } else if (start.equals(end) ) {
            alert.setTitle("Time Error");
            alert.setContentText("Time must not be the same");
            alert.showAndWait();
            return;
        } else if (start.isAfter(end)) {
            alert.setTitle("Time Error");
            alert.setContentText("Start time must be before end time");
            alert.showAndWait();
            return;
        } else if (start.toLocalTime().isBefore(businessStart) || end.toLocalTime().isAfter(businessEnd)) {
            alert.setTitle("Time Error");
            alert.setContentText("Appointment must be within business hours " + businessStart + " to " + businessEnd);
            alert.showAndWait();
            return;
        }
 
        // Creates local date time with user timezone              
        ZoneId localTZ = ZoneId.of(TimeZone.getDefault().getID());
        ZonedDateTime startLocalTime = ZonedDateTime.of(start, localTZ);
        ZonedDateTime endLocalTime = ZonedDateTime.of(end, localTZ);
        
        // Check for overlapping appointments
        //(StartA < EndB) and (EndA > StartB)
        for (Appointment appointment : AppointmentList.allAppointments) {
            if (startLocalTime.isBefore(appointment.getEnd()) && endLocalTime.isAfter(appointment.getStart())) {
                alert.setTitle("Time Error");
                alert.setContentText("Appointment " + startLocalTime + " to " + endLocalTime + " overlaps with appointment " 
                        + appointment.getAppointmentId() + " : " + appointment.getStart() + " to " + appointment.getEnd());
                alert.showAndWait();
                return;
            }
        }

        Appointment appointment = new Appointment.AppointmentBuilder(appointmentId, customerId, customer, user, title, type, startLocalTime, endLocalTime)
                    .setContact(contact)
                    .setDescription(description)
                    .setLocation(location).build();   
        
        
        AppointmentDAO.addAppointmentDB(appointment);
        try {
            stage = (Stage) ((Button) event.getSource()).getScene().getWindow();            
            scene = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
            stage.setScene(new Scene(scene));
            stage.show();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void onActionToMainScreen(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();                
        scene = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }
    
    private void fillTypeCB() {
        ObservableList<String> options = FXCollections.observableArrayList(
            "Introduction",
            "Project Planning",
            "Status Update",
            "Project Wrap-Up",
            "Consulatation"
        );
        typeCombo.setItems(options);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillTypeCB();
        
        // Listener for selecting customer
        customerTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> 
        {
            if (newVal != null) {
                customerIdTxt.setText(Integer.toString(newVal.getCustomerId()));
                customerNameTxt.setText(newVal.getCustomerName());

            }
        });
        
        try {
            apptIdStatic = AppointmentDAO.getNextAppointmentId();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        appointmentIdTxt.setText(Integer.toString(apptIdStatic));
        customerIdTxt.setEditable(false);
        appointmentIdTxt.setEditable(false);
        customerNameTxt.setEditable(false);
        
        /* All TableViews populated using lambda expressions. 
        *  This allows parallel execution of operations, tables populated with higher effieciency.
        */
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
    }    
    
}
