
package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Matthew Manning
 */
public class UserList {
    public static ObservableList<User> userList = FXCollections.observableArrayList();



    public static ObservableList<User> getUserList() {
        return userList;
    }
    
    public static void addToUserList(User user) {       
        userList.add(user);  
    }
    
        
    public static User searchUserList(int userId) {

        for (User user : getUserList()) {
            if (user.getUserId() == userId) {
                return user;
            }
        }
        return null;
    
    }
    

}
