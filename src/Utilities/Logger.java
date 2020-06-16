
package Utilities;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;


public class Logger {
    
    public static void logUserLogin(String username, boolean success) throws IOException {
        String attempt;
        if (success) {
            attempt = "SUCCESS";
        } else {
            attempt = "FAILED";
        }
        try {
            try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("userlog.txt", true)))) {
                out.println("Login attempt at " + LocalDateTime.now().format(DateTimeFormat.formatter) + " with username " + username + " : " + attempt);
            }
        } catch (IOException e) {
            
        }


    }
    
    

}
