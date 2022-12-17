package model;

import java.io.Serializable;

public class User implements Serializable {
    private int userId;
    private String userName;
    private String imagePath;

    public User(int userId, String userName, String imagePath) {
        this.userId = userId;   
        this.userName = userName;
        this.imagePath = imagePath;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getImagePath() {
        return imagePath;
    }
}
