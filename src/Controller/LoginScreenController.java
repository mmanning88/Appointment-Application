
package Controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;


public class LoginScreenController implements Initializable {
    
    @FXML
    private Label loginInstructionLbl;
    
    @FXML
    private TextField usernameTxt;

    @FXML
    private TextField passwordTxt;

    @FXML
    void onActionExit(ActionEvent event) {   
        System.exit(0);
    }

    @FXML
    void onActionLogin(ActionEvent event) {

    }
    
    private void setLocale() {
        
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }    
    
}
