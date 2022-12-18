package controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import model.*;

// Client controller to forward a message to other client controllers
public class ClientController implements Runnable {
    public static ArrayList<ClientController> clientControllers = new ArrayList<>();
    private Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private User user;

    // Constructor connects client controller to socket so it can send and receive
    // message objects through object stream
    public ClientController(Socket socket) {
        try {
            this.socket = socket;
            this.outputStream = new ObjectOutputStream(socket.getOutputStream());
            this.inputStream = new ObjectInputStream(socket.getInputStream());

            // Receives user from client
            this.user = (User) inputStream.readObject();
            clientControllers.add(this);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Method to create a new thread that reads message from stream and sends it
    // to other client controllers
    @Override
    public void run() {
        while (socket.isConnected()) {
            try {
                Message message = (Message) inputStream.readObject();
                
                // Sends to other client controllers by checking client controllers
                // that have different user id
                for (ClientController clientController : clientControllers) {
                    if (clientController.user.getUserId() != user.getUserId()) {                        
                        clientController.outputStream.writeObject(message);
                        clientController.outputStream.flush();
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                closeAll(socket, inputStream, outputStream);
                break;
            }
        }
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
                // Notifies server if client has disconnected
                System.out.println("A client has disconnected!");
                socket.close();
            }
        } catch (IOException e) {
        }
    }
}
