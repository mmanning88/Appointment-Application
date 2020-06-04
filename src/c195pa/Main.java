
package c195pa;

import Utilities.DBConnection;
import Utilities.DBQuery;
import com.mysql.jdbc.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        
        Connection conn = DBConnection.startConnection();
        String selectStatement = "SELECT * FROM appointment";

        DBQuery.setPreparedStatement(conn, selectStatement);

        
        PreparedStatement ps = DBQuery.getPreparedStatement();
        ps.execute();
        
        ResultSet rs = ps.getResultSet();
        System.out.println(rs.next());
        
        while (rs.next()) {
            int appointmentId = rs.getInt("appointmentId");
            int customerId = rs.getInt("customerId");
            int userId = rs.getInt("userId");
        }
//        while (rs.next()) {
//            int countryId = rs.getInt("countryId");
//            String country = rs.getString("country");
//            LocalDate date = rs.getDate("createDate").toLocalDate();
//            LocalTime time = rs.getTime("createDate").toLocalTime();
//            String createdBy = rs.getString("createdBy");
//            LocalDateTime lastUpdate = rs.getTimestamp("lastUpdate").toLocalDateTime();
//            String lastUpdateBy = rs.getString("lastUpdateBy");
//            
//            // Display record
//            System.out.println(countryId + " | " + country + " | " + date 
//            + " | " + time + " | " + createdBy + " | " + lastUpdate
//            + " | " + lastUpdateBy);
//        }
        
        launch(args);
        DBConnection.closeConnection();

    }
    
    public void initializeData() {
        
    }
    
}
