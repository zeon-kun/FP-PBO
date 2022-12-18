package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

// Class that handles store and retrieving messages from database
public class MessageDatabase {
    private ArrayList<Message> messages;
    private File file;

    // Constructor that takes a file as parameter
    public MessageDatabase(File file) {
        this.file = file;
        messages = new ArrayList<Message>();
    }

    // Method to write a message to the database
    public void write(Message message) {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(this.file));
            messages.add(message);
            objectOutputStream.writeObject(messages);
            objectOutputStream.flush();
            objectOutputStream.close();
            System.out.println("The message " + message.getMessageText() + " has successfully been written to database");
        } catch (IOException e) {
        }
    }

    // Method to read all messages from the database
    public ArrayList<Message> read() {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(this.file));
            
            messages = (ArrayList<Message>) objectInputStream.readObject();
            objectInputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        return messages;
    }
}
