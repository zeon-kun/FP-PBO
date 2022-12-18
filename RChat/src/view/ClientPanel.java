package view;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class ClientPanel extends Application{
    //Override Application class' start method to load our fxml file
    @Override
    public void start(Stage primaryStage){
        try {
            //Load login.fxml as root of the application
            Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));

            //Set login scene to root
            Scene loginScene = new Scene(root);

            //Set icon for the app
            Image icon = new Image(getClass().getResource("/asset/RChat-Logo.png").toString());
            primaryStage.getIcons().add(icon);

            //Set title for the app
            primaryStage.setTitle("RChat");
            primaryStage.setScene(loginScene);
            primaryStage.show();
            
            //Set condition when app is closed
            primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent t) {
                    Platform.exit();
                    System.exit(0);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        launch(args);
    }
}
