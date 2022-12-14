package model;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import controller.ClientController;

public class Server {
    
    private ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    public void startServer() {
        try {
            System.out.println("Server started!");
            while (!serverSocket.isClosed()) {
                Socket socket = serverSocket.accept();
                System.out.println("A new client has connected!");
                ClientController clientController = new ClientController(socket);

                Thread thread = new Thread(clientController);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeServerSocket() {
        try {
            if (serverSocket != null) {
                serverSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}