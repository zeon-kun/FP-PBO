package model;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

import javafx.application.Application;
import view.ClientPanel;

public class Client {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;

    public Client(Socket socket, String username) {
        try {
            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.username = username;
        } catch (IOException io) {
            closeAll(socket, bufferedReader, bufferedWriter);
        }
    }

    public void sendMessage() {
        try {
            bufferedWriter.write(username);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            Scanner scanner = new Scanner(System.in);
            while (socket.isConnected()) {
                String clientMessage = scanner.nextLine();
                bufferedWriter.write(username + ": " + clientMessage);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
            scanner.close();
        } catch (IOException e) {
            closeAll(socket, bufferedReader, bufferedWriter);
        }
    }

    public void sendMessage(String clientMessage) {
        try {
            bufferedWriter.write(username);
            bufferedWriter.newLine();
            bufferedWriter.flush();
            while (socket.isConnected()) {
                // String clientMessage = scanner.nextLine();
                bufferedWriter.write(username + ": " + clientMessage);
                bufferedWriter.newLine();
                bufferedWriter.flush();
                
            }
            // scanner.close();
        } catch (IOException e) {
            closeAll(socket, bufferedReader, bufferedWriter);
        }
    }

    public void listenForMessage() {
        new Thread(new Runnable() {

            @Override
            public void run() {
                String groupChatMessage;
                
                while (socket.isConnected()) {
                    try {
                        groupChatMessage = bufferedReader.readLine();
                        System.out.println(groupChatMessage);
                    } catch (IOException e) {
                        closeAll(socket, bufferedReader, bufferedWriter);
                    }
                }
                
            }
            
        }).start();
    }

    public void closeAll(Socket socket, BufferedReader bufferedReader, BufferedWriter bufferedWriter) {
        try {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (bufferedWriter != null) {
                bufferedWriter.close();
            }
            if (socket != null) {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        // Scanner scanner = new Scanner(System.in);
        // System.out.println("Enter your username: ");
        // String username = scanner.nextLine();

        // Socket socket = new Socket("localhost", 6969);
        // Client client = new Client(socket, username);
        
        Application.launch(ClientPanel.class);

        // client.listenForMessage();
        // client.sendMessage();
        // scanner.close();
    }
}
