
package Controller;

import DAO.CustomerDAO;
import Model.Customer;
import Model.CustomerList;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
    void OnActionSaveCustomer(ActionEvent event) {

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

    }

    @FXML
    void onActionInactive(ActionEvent event) {

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        

            CustomerList.customerList = CustomerList.getCustomerList();
            
            customerTableView.setItems(CustomerList.customerList);
            
            customerIdCol.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCustomerId()).asObject());
            customerNameCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCustomerName()));
            customerAddressCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getAddress()));
            customerAddress2Col.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getAddress2()));
            customerCityCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getCity()));
            customerPostalCodeCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getPostalCode()));
            customerCountryCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getCountry()));
            customerPhoneCol.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAddress().getPhone()));

    }    
    
}
