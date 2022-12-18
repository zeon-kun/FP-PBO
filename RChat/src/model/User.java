package model;

import java.io.Serializable;

// User class to store user data. Implements serializable because
// we send user through stream
public class User implements Serializable {
    private static int userCount = 0;
    private int userId;
    private String userName;
    private String imagePath;

    // Constructor for public user
    public User(int userId, String userName, String imagePath) {
        this.userId = generateId();   
        this.userName = userName;
        this.imagePath = imagePath;
    }

    // Get user id
    public int getUserId() {
        return userId;
    }

    // Get user name
    public String getUserName() {
        return userName;
    }

    // Get image path
    public String getImagePath() {
        return imagePath;
    }
    
    private int generateId() {
        return userCount + 1;
    }
}
