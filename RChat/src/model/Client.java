package model;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter; 
    private User user;

    public Client(Socket socket, User user) {
        try {
            this.socket = socket;
            this.outputStream = new ObjectOutputStream(socket.getOutputStream());
            this.inputStream = new ObjectInputStream(socket.getInputStream());
            this.user = user;
            outputStream.writeObject(this.user);
            outputStream.flush();
        } catch (IOException e) {
            System.out.println("Error creating client.");
            e.printStackTrace();
            closeAll(socket, inputStream, outputStream);
        }
    }

    public void sendMessageToServer() {
        try {
            Scanner scanner = new Scanner(System.in);
            while (socket.isConnected()) {
                String messageText = scanner.nextLine();
                outputStream.writeObject(new Message(user, messageText, new Timestamp(System.currentTimeMillis())));
                outputStream.flush();
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error sending message to server.");
            closeAll(socket, inputStream, outputStream);
        }
    }

    public void receiveMessageFromServer() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Message message;
                while (socket.isConnected()) {
                    try {
                        message = (Message) inputStream.readObject();
                        System.out.println(message.getMessageAuthor().getUserName() + ": " + message.getMessageText());
                    } catch (IOException | ClassNotFoundException e) {
                        closeAll(socket, inputStream, outputStream);
                    }
                }
                
            }
        }).start();
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
