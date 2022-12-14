package test;

import java.io.IOException;
import java.net.ServerSocket;

import model.Server;

public class ServerTest {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(6969);
        Server server = new Server(serverSocket);
        server.startServer();
    }
}
