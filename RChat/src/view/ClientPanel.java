package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;

public class ClientPanel extends Application{
    //override Application class' start method to load our fxml file
    @Override
    public void start(Stage primaryStage){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("mainframe.fxml"));
            Scene scene = new Scene(root);
            Image icon = new Image(getClass().getResource("/asset/RChat-Logo.png").toString());
            // Media media = new Media(getClass().getResource("/asset/RChat-menu2.mp3").toString());
            // MediaPlayer mediaPlayer = new MediaPlayer(media);
            // mediaPlayer.setAutoPlay(true);
            primaryStage.getIcons().add(icon);
            primaryStage.setTitle("RChat");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        launch(args);
    }
}
