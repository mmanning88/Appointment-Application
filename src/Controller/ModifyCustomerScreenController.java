
package Controller;

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
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
    private TableView<?> customerTableView;

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

    }    
    
}
