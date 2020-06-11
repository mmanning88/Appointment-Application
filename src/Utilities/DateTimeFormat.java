
package Utilities;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

//    LocalDateTime now = LocalDateTime.now();
//    System.out.println("Before : " + now);
//    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//    String formatDateTime = now.format(formatter);
//    System.out.println("After : " + formatDateTime);

public class DateTimeFormat {
    
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    
    public static String getNow() {
        LocalDateTime now = LocalDateTime.now();
        String nowFormat = now.format(formatter);
        return nowFormat;
    }
    
    public static String getCurrentUTC() {
        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
        String nowFormat = now.format(formatter);
        return nowFormat;
    }


    
}
