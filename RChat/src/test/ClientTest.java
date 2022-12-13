package test;

import java.io.IOException;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

import model.*;

public class ClientTest {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter your name: ");
        String name = scanner.nextLine();
        Socket socket = new Socket("localhost", 6969);
        Client client = new Client(socket, new User(new Random().nextInt(10), name));
        client.receiveMessageFromServer();
        client.sendMessageToServer();
        scanner.close();
    }
}
