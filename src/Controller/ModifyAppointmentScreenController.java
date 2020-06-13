/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
    private TextArea appointmentDescTxt;

    @FXML
    private ComboBox<String> typeCombo;

    @FXML
    private TextField appointmentContactTxt;
    
    @FXML
    private TextField appointmentLocationTxt;

    @FXML
    private DatePicker startDatePicker;

    @FXML
    private TextField startTimeTxt;

    @FXML
    private TextField endTimeTxt;
    
    private int apptId;

    @FXML
    void onActionSaveModifiedAppointment(ActionEvent event) throws SQLException {
        int appointmentId = apptId;
        int customerId = 0;
        try {
            customerId = Integer.parseInt(customerIdTxt.getText());
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }
        Customer customer = CustomerList.searchCustomerList(customerId);
        User user = User.currentUser;
        String title = appointmentTitleTxt.getText();
        String type = typeCombo.getValue();
        LocalDate ld = null;
        LocalTime startTime = null;
        LocalTime endTime = null;
        try {
            ld = startDatePicker.getValue();
            startTime = LocalTime.parse(startTimeTxt.getText(), DateTimeFormat.formatterTime);
            endTime = LocalTime.parse(endTimeTxt.getText(), DateTimeFormat.formatterTime);
        } catch (NullPointerException e) {
            System.out.println(e.getMessage());
            // Exception control for improper minute and hour entry
        } catch (DateTimeException eDate) {
            Alert alertTime = new Alert(Alert.AlertType.ERROR);
            alertTime.setTitle("Customer Error");
            alertTime.setContentText(eDate.getMessage());
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
        
        String description = appointmentDescTxt.getText();
        String location = appointmentLocationTxt.getText();
        String contact = appointmentContactTxt.getText();
        
        // Check to see if required fields have been filled out
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if (customer == null) {
            alert.setTitle("Customer Error");
            alert.setContentText("Customer must be selected");
            alert.showAndWait();
            return;
        } else if (ld == null || startTime == null || endTime == null) {
            alert.setTitle("Time Error");
            alert.setContentText("Appointment must have a start and end time");
            alert.showAndWait();
            return;
        } else if (title.isEmpty()) {
            alert.setTitle("Title Error");
            alert.setContentText("Appointment must have a title");
            alert.showAndWait();
            return;
        } else if (type.isEmpty()) {
            alert.setTitle("Type Error");
            alert.setContentText("Appointment must have a type");
            alert.showAndWait();
            return;
        }
        
        // Creates local date time with user timezone              
        ZoneId localTZ = ZoneId.of(TimeZone.getDefault().getID());
        ZonedDateTime startLocalTime = ZonedDateTime.of(start, localTZ);
        ZonedDateTime endLocalTime = ZonedDateTime.of(end, localTZ);
        
        // Check for overlapping appointments
        //(StartA < EndB) and (EndA > StartB) = overlap)
        for (Appointment appointment : AppointmentList.getMonthlyAppointments()) {
            if (appointment.getAppointmentId() != appointmentId ) {
                if (startLocalTime.isBefore(appointment.getEnd()) && endLocalTime.isAfter(appointment.getStart())) {
                    alert.setTitle("Time Error");
                    alert.setContentText("Appointment " + appointmentId + ": " + startLocalTime + " to " + endLocalTime + " overlaps with appointment " 
                            + appointment.getAppointmentId() + " : " + appointment.getStart() + " to " + appointment.getEnd());
                    alert.showAndWait();
                    return;
                }
            }
        }

        Appointment newAppointment = new Appointment.AppointmentBuilder(appointmentId, customerId, customer, user, title, type, startLocalTime, endLocalTime)
                    .setContact(contact)
                    .setDescription(description)
                    .setLocation(location).build();   
        
        AppointmentDAO.modifyAppointmentDB(newAppointment);
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
    
    public void transferAppointment(Appointment appointment) {
        
        apptId = appointment.getAppointmentId();
        customerIdTxt.setText(Integer.toString(appointment.getCustomerId()));
        appointmentIdTxt.setText(Integer.toString(appointment.getAppointmentId()));
        customerNameTxt.setText(appointment.getCustomer().getCustomerName());
        appointmentTitleTxt.setText(appointment.getTitle());
        appointmentDescTxt.setText(appointment.getDescription());
        typeCombo.setValue(appointment.getType());
        appointmentContactTxt.setText(appointment.getContact());
        appointmentLocationTxt.setText(appointment.getLocation());
        startDatePicker.setValue(appointment.getStart().toLocalDate());
        startTimeTxt.setText(appointment.getStart().format(DateTimeFormat.formatterTime));
        endTimeTxt.setText(appointment.getEnd().format(DateTimeFormat.formatterTime));
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        customerTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> 
        {
            if (newVal != null) {
                customerIdTxt.setText(Integer.toString(newVal.getCustomerId()));
                customerNameTxt.setText(newVal.getCustomerName());

            }
        });
        
        customerIdTxt.setEditable(false);
        appointmentIdTxt.setEditable(false);
        customerNameTxt.setEditable(false);
        
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
