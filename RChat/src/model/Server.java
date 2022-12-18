package model;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import controller.ClientController;

// Server class
public class Server {
    
    private ServerSocket serverSocket;

    public Server(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    // Method to start server then accept any incoming connection requests from
    // clients and assigning a clientcontroller to that client
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

    // Main method
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(6969);
        Server server = new Server(serverSocket);
        server.startServer();
    }
}