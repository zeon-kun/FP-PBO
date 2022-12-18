package controller;

import java.net.Socket;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.sql.Timestamp;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;
import model.Client;
import model.Message;
import model.User;
import view.MessageBox;

public class FrameController implements Initializable {
    @FXML
    private ScrollPane scrollPane;

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

    private Boolean[] alreadyAssigned = {false, false, false, false};

    public String assignProfilePicture(User user){
        String imagepath = "";
        int tempId = user.getUserId()/4;
        String[] imageList = {getClass().getResource("/asset/Bulbasaur.png").toString(),
                              getClass().getResource("/asset/Charmander.png").toString(),
                              getClass().getResource("/asset/Pikachu.png").toString(),
                              getClass().getResource("/asset/Squirtle.png").toString()};

        if (alreadyAssigned[tempId] == false){
            imagepath = imageList[tempId];
            alreadyAssigned[tempId] = true;
        } else {
            while(alreadyAssigned[tempId] == true){
                tempId++;
            }
            imagepath = imageList[tempId];
            alreadyAssigned[tempId] = true;
        }
        return imagepath;
    }
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
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter your name: ");
            String name = scanner.nextLine();
            user = new User(new Random().nextInt(100), name, getClass().getResource("/asset/Pikachu.png").toString());
            client = new Client(new Socket("localhost", port), user);
            scanner.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        client.receiveMessageFromServer(vbox_msg);
    }

    //FXML method to get the text field message that the client typed
    @FXML
    public void messageFromTextField() {
        String messageText = tf_msg.getText();
        if (messageText != ""){
            //for testing purpose on console
            System.out.println("Sending Message :" + messageText);

            //create a new message box
            Message message = new Message(user, messageText, new Timestamp(System.currentTimeMillis()));
            MessageBox newMsgBox = new MessageBox(message);
            newMsgBox.create();
            newMsgBox.getHbox().setAlignment(Pos.CENTER_RIGHT);
            
            vbox_msg.getChildren().add(newMsgBox.getHbox());

            // for (Node node : scrollPane.lookupAll(".scroll-bar")) {
            //     if (node instanceof ScrollBar) {
            //         ScrollBar scrollBar = (ScrollBar) node;
            //         if (scrollBar.getOrientation() == Orientation.HORIZONTAL) {
            //             // Do something with the horizontal scroll bar
        
            //             // Example 1: Print scrollbar height
            //             // System.out.println(scrollBar.heightProperty().get());
        
            //             // Example 2: Listen to visibility changes
            //             // scrollBar.visibleProperty().addListener((observable, oldValue, newValue) -> {
            //             //     if(newValue) {
            //             //         // Do something when scrollbar gets visible
            //             //     } else {
            //             //         // Do something when scrollbar gets hidden
            //             //     }
            //             // });
            //         }
            //         if (scrollBar.getOrientation() == Orientation.VERTICAL) {
            //             // Do something with the vertical scroll bar
            //         }
        
            //     }
        

            //send the message object to server
            client.sendMessageToServer(message);
            
            //reset text field to empty
            tf_msg.setText("");     
        }
    }

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
        }
        );
        
    }

    public static void startNotif(){
        Media mediaForNotif = new Media(FrameController.class.getResource("/asset/RChat-notif.mp3").toString());
        MediaPlayer notifPlayer = new MediaPlayer(mediaForNotif);
        notifPlayer.play();
        notifPlayer.setStartTime(Duration.ZERO);
    }
    
    @FXML
    public void gotoMenu(){

    }

    @FXML
    public void gotoTypeRace(){

    }
}

    