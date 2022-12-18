package model;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import controller.FrameController;
import javafx.scene.layout.VBox;

// Client that stores client data and functions
public class Client {
    private Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private User user;

    public Client(Socket socket, User user) {
        // Connect client with server socket so it can send and receive messages
        try {
            this.socket = socket;
            this.outputStream = new ObjectOutputStream(socket.getOutputStream());
            this.inputStream = new ObjectInputStream(socket.getInputStream());
            this.user = user;

            // Send user to client controller
            outputStream.writeObject(this.user);
            outputStream.flush();
        } catch (IOException e) {
            System.out.println("Error creating client.");
            closeAll(socket, inputStream, outputStream);
        }
    }
 
    // Method to send message to server by writing message to stream
    public void sendMessageToServer(Message message) {
        try {
            outputStream.writeObject(message);
            outputStream.flush();
        } catch (IOException e) {
            System.out.println("Error sending message to server.");
            closeAll(socket, inputStream, outputStream);
        }
    }

    // Method to receive message from server by reading continuously if there
    // is a message available then forward that message to other clients
    public void receiveMessageFromServer(VBox vbox) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message;
                while (socket.isConnected()) {
                    try {
                        message = (Message) inputStream.readObject();
                        FrameController.sendMessageToOtherClients(message, vbox);
                    } catch (IOException | ClassNotFoundException e) {
                        closeAll(socket, inputStream, outputStream);
                        System.out.println("Error retrieving message from client.");
                        break;
                    }
                }
                
            }
        }).start();
    }
    
    // Method to close everything if we have caught an error
    public void closeAll(Socket socket, ObjectInputStream inputStream, ObjectOutputStream outputStream) {
        try {
            if (inputStream != null) {
                inputStream.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
            if (socket != null) {
                socket.close();
                // Notify client if server is down
                System.out.println("The server is down.");
            }
        } catch (IOException e) {
        }
    }

}