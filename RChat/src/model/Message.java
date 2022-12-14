package model;

import java.io.Serializable;
import java.sql.Timestamp;

public class Message implements Serializable {
    private User messageAuthor;
    private String messageText;
    private Timestamp timestamp;

    public Message(User messageAuthor, String messageText, Timestamp timestamp) {
        this.messageAuthor = messageAuthor;
        this.messageText = messageText;
        this.timestamp = timestamp;
    }

    public User getMessageAuthor() {
        return messageAuthor;
    }

    public String getMessageText() {
        return messageText;
    }
}
