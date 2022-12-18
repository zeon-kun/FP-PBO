package model;

import java.io.Serializable;

// Class message to store message data, implements Serializable 
// so we can send messages through stream
public class Message implements Serializable {
    private User messageAuthor;
    private String messageText;

    // Message constructor
    public Message(User messageAuthor, String messageText) {
        this.messageAuthor = messageAuthor;
        this.messageText = messageText;
    }

    // Method to get message author
    public User getMessageAuthor() {
        return messageAuthor;
    }

    // Method to get message text
    public String getMessageText() {
        return messageText;
    }
}
