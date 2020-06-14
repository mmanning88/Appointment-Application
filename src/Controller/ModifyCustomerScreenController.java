
package Controller;

import DAO.CityDAO;
import DAO.CountryDAO;
import DAO.CustomerDAO;
import Model.Address;
import Model.Customer;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/*
@author Matthew Manning
*/
public class ModifyCustomerScreenController implements Initializable {
        
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
    private RadioButton activeRB;
    
    @FXML
    private RadioButton inactiveRB;

    @FXML
    private ToggleGroup activeTG;
    
    private int isActive;
    private int transferredAddressId; 


    @FXML
    void OnActionSaveCustomer(ActionEvent event) throws SQLException {
        
        boolean needCity, needCountry;
        int customerId = Integer.parseInt(customerIdTxt.getText());
        String customerName = customerNameTxt.getText();
        String address1 = customerAddressTxt.getText();
        String address2 = customerAddress2Txt.getText();
        String city = customerCityTxt.getText();
        
        String country = countryCombo.getValue();
        String postalCode = customerPostalCodeTxt.getText();
        String phone = customerPhoneTxt.getText();
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

        Address address = new Address(transferredAddressId, cityId, countryId, address1, address2, postalCode, phone, city, country);
        Customer customer = new Customer(customerId, isActive, customerName, address);
        
        System.out.println("AddressID " + customer.getAddress().getAddressId());
        CustomerDAO.updateDBCustomer(customer, needCity, needCountry);
        
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
    void OnActionToMainScreen(ActionEvent event) throws IOException {
        stage = (Stage) ((Button) event.getSource()).getScene().getWindow();                
        scene = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }


    @FXML
    void onActionActive(ActionEvent event) {
        isActive = 1;
    }

    @FXML
    void onActionInactive(ActionEvent event) {
        isActive = 0;
    }
    
    // Retrieve selected customer from MainScreenController
    public void transferCustomer (Customer customer) {     
        transferredAddressId = customer.getAddress().getAddressId();
        customerNameTxt.setText(String.valueOf(customer.getCustomerName()));
        customerIdTxt.setText(String.valueOf(customer.getCustomerId()));
        customerAddressTxt.setText(String.valueOf(customer.getAddress().getAddress()));
        customerAddress2Txt.setText(String.valueOf(customer.getAddress().getAddress2()));
        customerCityTxt.setText(String.valueOf(customer.getAddress().getCity()));
        customerPostalCodeTxt.setText(String.valueOf(customer.getAddress().getPostalCode()));
        customerPhoneTxt.setText(String.valueOf(customer.getAddress().getPhone()));
        countryCombo.setValue(customer.getAddress().getCountry());
        
        if (customer.getActive() == 1) {
            activeRB.setSelected(true);
        } else {
            inactiveRB.setSelected(true);
        }
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
        
        customerIdTxt.setEditable(false);

    }    
    
}
