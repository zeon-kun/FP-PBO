package controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import model.*;

// client controller to receive and send messages from clients
public class ClientController implements Runnable {
    public static ArrayList<ClientController> clientControllers = new ArrayList<>();
    private Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private User user;

    public ClientController(Socket socket) {
        try {
            this.socket = socket;
            this.outputStream = new ObjectOutputStream(socket.getOutputStream());
            this.inputStream = new ObjectInputStream(socket.getInputStream());
            this.user = (User) inputStream.readObject();
            clientControllers.add(this);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (socket.isConnected()) {
            try {
                Message message = (Message) inputStream.readObject();
                
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
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
