package controller;

import java.io.File;
import java.net.Socket;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import model.Client;
import model.MessageDatabase;
import model.Message;
import model.User;
import view.MessageBox;

// Main frame controller
// Implements the Initializable interface, so that we can run programs inside the GUI
public class FrameController implements Initializable {
    @FXML
    private ScrollPane scroll_pane;

    @FXML
    private Button send_button;

    @FXML
    private VBox vbox_msg;

    @FXML
    private TextField tf_msg;

    @FXML
    private Label dateTime;

    @FXML
    private ImageView imageView;

    
    
    private final int port = 6969;
    private User user;
    private Client client;
    private MessageDatabase messageDatabase;

    //Override the initialize method in Intializable interface
    @Override
    public void initialize(URL locUrl, ResourceBundle resourceBundle) {
        //set send_button default form to true, so you can send message with 'ENTER' key at the keyboard
        send_button.setDefaultButton(true);

        //fetch current time using the LocalDateTime and DateTimeFormatter extension
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                dateTime.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
            }
        };
        timer.start();

        //create new user from console or terminal, and then creates client from that user.username
        try {
            String userName = LoginController.getUsername();
            user = new User(userName, getClass().getResource("/asset/Pikachu.png").toString());
            client = new Client(new Socket("localhost", port), user);
        } catch (Exception e) {
            System.out.println("Error creating client!");
            Platform.exit();
            System.exit(0);
        }

        // Scroll to bottom of chat when there is a new message (when vbox height changes)
        vbox_msg.heightProperty().addListener(observable -> scroll_pane.setVvalue(1.0));
        
        // Creates MessageDatabase object as a connector to the database
        messageDatabase = new MessageDatabase(new File("messagedb"));

        // Loads messages from the database
        loadMessages();

        // Client will be able to receive messages from other clients
        client.receiveMessageFromServer(vbox_msg);
    }

    //FXML method to get the text field message that the client typed
    @FXML
    public void messageFromTextField() {
        String messageText = tf_msg.getText();
        if (messageText != ""){

            //create a new message box
            Message message = new Message(user, messageText);
            MessageBox newMsgBox = new MessageBox(message);
            newMsgBox.create();
            newMsgBox.getHbox().setAlignment(Pos.CENTER_RIGHT);
            
            vbox_msg.getChildren().add(newMsgBox.getHbox());

            //send the message object to server
            client.sendMessageToServer(message);
            
            // Add message to the database
            messageDatabase.write(message);
            
            //reset text field to empty
            tf_msg.setText("");     
            
        }
    }

    // Sends message to other clients
    public static void sendMessageToOtherClients(Message message, VBox vbox) {
        //your daily anime notifications
        startNotif();

        //create a new message box
        MessageBox newMsgBox = new MessageBox(message);
        newMsgBox.create();

        //Creates a new Runnable that runs later and merge our HBox to the original vbox_msg
        //  (since the box message will be created when we send a message from the the user's perspective)
        Platform.runLater(new Runnable() {
            @Override
            public void run(){
                vbox.getChildren().add(newMsgBox.getHbox());
            }
        });
    }

    // Method to start notification sound
    public static void startNotif(){
        Media mediaForNotif = new Media(FrameController.class.getResource("/asset/RChat-notif.mp3").toString());
        MediaPlayer notifPlayer = new MediaPlayer(mediaForNotif);
        notifPlayer.play();
        notifPlayer.setStartTime(Duration.ZERO);
    }

    // Method to load messages from the database by getting the messages
    // then iterating for every message, we create a new message box
    private void loadMessages() {
        ArrayList<Message> messages = messageDatabase.read();
        for (Message message : messages) {
            MessageBox messageBox = new MessageBox(message);
            messageBox.create();
            vbox_msg.getChildren().add(messageBox.getHbox());
        }
    }
}

    