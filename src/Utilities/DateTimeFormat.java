
package Utilities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

//    LocalDateTime now = LocalDateTime.now();
//    System.out.println("Before : " + now);
//    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//    String formatDateTime = now.format(formatter);
//    System.out.println("After : " + formatDateTime);

public class DateTimeFormat {
    
    public static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static DateTimeFormatter formatterTime = DateTimeFormatter.ofPattern("HH:mm:ss");
    public static DateTimeFormatter formatterUTC = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static LocalDateTime getCurrentLDT() {
        LocalDateTime ldt = LocalDateTime.parse(getNow(), formatter);
        return ldt;
    }
    
    public static String getDate() {
        LocalDate date = LocalDate.now();
        String dateFormat = date.format(formatterDate);
        return dateFormat;
    }
    
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
