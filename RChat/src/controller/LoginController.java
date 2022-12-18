package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;

public class LoginController implements Initializable{
    @FXML
    private Button login_button;
    @FXML
    private TextField tf_username;

    private static String username;
    private Stage stage;
    private Scene scene;
    private Parent root;

    //Override initialize method for the login scene
    @Override
    public void initialize(URL location, ResourceBundle source){
        //Set default button condition to true (So we can click enter whenever we want)
        login_button.setDefaultButton(true);
    }

    public void loginToChat(ActionEvent event) throws IOException{
        //Set username for static call purpose
        username = tf_username.getText(); 
        
        //Load the mainframe.fxml (a.k.a the chat frame)
        root = FXMLLoader.load(getClass().getResource("../view/mainframe.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    //Static method to call the username string
    public static String getUsername(){
        return username;
    }
}
