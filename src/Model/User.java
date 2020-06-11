
package Model;

import java.time.LocalDateTime;

/*
@author Matthew Manning
*/
public class User {
    
    public static User currentUser;
    private int userId;
    private String userName, password, createdBy, lastUpdateBy;
    private LocalDateTime createDate, lastUpdate;

    public User(int userId, String userName, String password, String createdBy, String lastUpdateBy, LocalDateTime createDate, LocalDateTime lastUpdate) {
        this.userId = userId;
        this.userName = userName;
        this.password = password;
        this.createdBy = createdBy;
        this.lastUpdateBy = lastUpdateBy;
        this.createDate = createDate;
        this.lastUpdate = lastUpdate;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastUpdateBy() {
        return lastUpdateBy;
    }

    public void setLastUpdateBy(String lastUpdateBy) {
        this.lastUpdateBy = lastUpdateBy;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
    
    
    
}
