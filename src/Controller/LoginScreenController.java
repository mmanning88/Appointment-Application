
package Controller;

import DAO.UserDAO;
import Model.User;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/*
@author Matthew Manning
*/
public class LoginScreenController implements Initializable {
    
    Stage stage;
    Parent scene;
    
    @FXML
    private Label loginInstructionLbl, titleLbl;
    
    @FXML
    private TextField usernameTxt;

    @FXML
    private TextField passwordTxt;
    
    @FXML
    private Button loginBtn, exitBtn;

    @FXML
    void onActionExit(ActionEvent event) {   
        System.exit(0);
    }

    @FXML
    void onActionLogin(ActionEvent event) throws IOException, SQLException {
        
        String loginUserName = getUsername();
        String loginPassword = getPassword();
        User targetUser = getUser();
        
        try {
            if (loginUserName.equals(targetUser.getUserName()) && loginPassword.equals(targetUser.getPassword())) {
                stage = (Stage) ((Button) event.getSource()).getScene().getWindow();                
                scene = FXMLLoader.load(getClass().getResource("/View/MainScreen.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            } 
        } catch (NullPointerException nullPointerException) {
            ResourceBundle rb = ResourceBundle.getBundle("Localization/Nat", Locale.getDefault());
            loginInstructionLbl.setText(rb.getString("loginError"));
        }
        

    }
    
    private void setLanguage() {
        ResourceBundle rb = ResourceBundle.getBundle("Localization/Nat", Locale.getDefault());
        titleLbl.setText(rb.getString("titleLbl"));
        loginInstructionLbl.setText(rb.getString("loginInstructionLbl"));
        usernameTxt.setPromptText(rb.getString("usernameTxt"));
        passwordTxt.setPromptText(rb.getString("passwordTxt"));
    }
    
    private String getUsername() {
        String username = usernameTxt.getText();
        return username;
    }
    
    private String getPassword() {
        String password = passwordTxt.getText();
        return password;
    }
    
    private User getUser() throws SQLException {

        User user = UserDAO.getDBUser(getUsername());
        return user;

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setLanguage();
    }    
    
}
