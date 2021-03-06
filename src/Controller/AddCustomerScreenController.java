
package Controller;

import DAO.CityDAO;
import DAO.CountryDAO;
import DAO.CustomerDAO;
import Model.Address;
import Model.Customer;
import Model.CustomerList;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
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
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
/*
@author Matthew Manning
*/
public class AddCustomerScreenController implements Initializable {
    
    Stage stage;
    Parent scene;
    
    @FXML
    private TextField customerNameTxt;

    @FXML
    private TextField customerIdTxt;

    @FXML
    private TextField customerAddressTxt;

    @FXML
    private TextField customerAddress2Txt;

    @FXML
    private TextField customerCityTxt;

    @FXML
    private TextField customerPostalCodeTxt;

    @FXML
    private TextField customerPhoneTxt;
    
    @FXML
    private ComboBox<String> countryCombo;
    
    @FXML
    private RadioButton activeRB, inactiveRB;

    private Integer CustIdStatic;

    private int isActive;
    

    @FXML
    void onActionActive(ActionEvent event) {
        isActive = 1;
    }

    @FXML
    void onActionInactive(ActionEvent event) {
        isActive = 0;
    }

    @FXML
    void onActionSaveCustomer(ActionEvent event) throws SQLException {
        
        boolean needCity, needCountry;
        int customerId = Integer.parseInt(customerIdTxt.getText());
        String customerName = customerNameTxt.getText();
        String address1 = customerAddressTxt.getText();
        String address2 = customerAddress2Txt.getText();
        String city = customerCityTxt.getText();
        
        String country = countryCombo.getValue();
        String postalCode = customerPostalCodeTxt.getText();
        String phone = customerPhoneTxt.getText();
        
        // Check to see if required fields have been filled out
        Alert alert = new Alert(Alert.AlertType.ERROR);
        if (customerName.isEmpty()) {

            alert.setTitle("Customer Name Error");
            alert.setContentText("Customer must have a name");
            alert.showAndWait();
            return;
        } else if (address1.isEmpty()) {
            alert.setTitle("Customer Address Error");
            alert.setContentText("Address field must be filled out");
            alert.showAndWait();
            return;
        } else if (city.isEmpty()) {
            alert.setTitle("Customer City Error");
            alert.setContentText("City field must be filled out");
            alert.showAndWait();
            return;
        } else if (postalCode.isEmpty()) {
            alert.setTitle("Customer Postal Code Error");
            alert.setContentText("Postal Code field must be filled out");
            alert.showAndWait();
            return;
        } else if (phone.isEmpty()) {
            alert.setTitle("Customer Phone Error");
            alert.setContentText("Phone field must be filled out");
            alert.showAndWait();
            return;
        }  else if (country.isEmpty()) {
            alert.setTitle("Customer Country Error");
            alert.setContentText("Country must be selected");
            alert.showAndWait();
            return;
        }

        // Check if new city or country is needed
        int countryId = CountryDAO.checkCountry(country);
        int cityId = CityDAO.checkCity(city);
        
        if (countryId < 0) {
            countryId = CountryDAO.getNextCityId();
            needCountry = true;
        } else {
            needCountry = false;
        }
        
        if (cityId < 0) {
            cityId = CityDAO.getNextCityId();
            needCity = true;
        } else {
            needCity = false;
        }
        
        
        Address address = new Address(CustomerDAO.getNextAddressId(), cityId, countryId, address1, address2, postalCode, phone, city, country);
        Customer customer = new Customer(customerId, isActive, customerName, address);

        CustomerDAO.addDBCustomer(customer, needCity, needCountry);
        
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
    
    private void fillDataCB() {
        String[] locales = Locale.getISOCountries();
        ObservableList<String> countries = FXCollections.observableArrayList();
        
        for (String countryCode : locales) {
            Locale obj = new Locale("", countryCode);
            String country = obj.getDisplayCountry();
            countries.add(country);
        }
        countryCombo.setItems(countries);
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        fillDataCB();
 
        CustIdStatic = CustomerList.getCustomerNextId();

        customerIdTxt.setEditable(false);

        customerIdTxt.setText(CustIdStatic.toString());
    }    
    
}
