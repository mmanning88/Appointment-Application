
package c195pa;

import Model.Address;
import Model.Appointment;
import Model.AppointmentList;
import Model.Customer;
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
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class Main extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/View/LoginScreen.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws SQLException {
        //launch(args);
        DBManager.fillWeeklyAppointments();
        for (Appointment a : AppointmentList.weeklyAppointments) {
            System.out.println(a.getAppointmentId());
        }
        System.exit(0);
    }
    
    public void initializeData() {
        
    }
    
}
