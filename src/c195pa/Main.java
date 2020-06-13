
package c195pa;

import DAO.CustomerDAO;
import DAO.UserDAO;
import Model.Address;
import Model.Customer;
import Model.CustomerList;
import Model.UserList;
import Utilities.DateTimeFormat;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
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
//        
//        ZonedDateTime now = ZonedDateTime.now();
//        Timestamp timestamp = Timestamp.valueOf(now.toLocalDateTime());
//        String s = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(timestamp);
//        System.out.println(s);
        System.exit(0);
    }
    
    
}
