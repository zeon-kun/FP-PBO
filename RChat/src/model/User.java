package model;

import java.io.Serializable;
import java.util.Random;
import java.util.UUID;

// User class to store user data. Implements serializable because
// we send user through stream
public class User implements Serializable {
    private UUID userId;
    private String userName;
    private String imagePath;

    // Constructor for public user
    public User(String userName, String imagePath) {
        this.userId = UUID.randomUUID();
        this.userName = userName;
        this.imagePath = assignProfilePicture();
    }

    // Get user id
    public UUID getUserId() {
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
    
    // Assigns a profile picture randomly
    private String assignProfilePicture(){
        String[] imageList = {getClass().getResource("/asset/Bulbasaur.png").toString(),
                              getClass().getResource("/asset/Charmander.png").toString(),
                              getClass().getResource("/asset/Pikachu.png").toString(),
                              getClass().getResource("/asset/Squirtle.png").toString()};
        return imageList[new Random().nextInt(imageList.length)];
    }
}
