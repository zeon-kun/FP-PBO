package controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;


public class FrameController implements Initializable {
    @FXML
    private Button send_button;

    @FXML
    private VBox vbox_msg;

    @FXML
    private TextField tf_msg;

    @Override
    public void initialize(URL locUrl, ResourceBundle resourceBundle) {
    }

    @FXML
    public void test() {
        System.out.println("Sending Message :" + tf_msg.getText());
        tf_msg.setText("");
    }

}
