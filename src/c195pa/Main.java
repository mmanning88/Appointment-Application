
package c195pa;

import DAO.AppointmentDAO;
import DAO.CustomerDAO;
import DAO.UserDAO;
import Model.Address;
import Model.Appointment;
import Model.AppointmentList;
import Model.Customer;
import Model.CustomerList;
import Model.User;
import Utilities.DBConnection;
import Utilities.DBManager;
import Utilities.DBQuery;
import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/*
@author Matthew Manning
*/
public class Main extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/View/LoginScreen.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws SQLException {
        launch(args);
        System.exit(0);
    }
    
    
}
