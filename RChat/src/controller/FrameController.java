package controller;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;


public class FrameController implements Initializable {
    @FXML
    private Button send_button;

    @FXML
    private VBox vbox_msg;

    @FXML
    private TextField tf_msg;

    @FXML
    private Label dateTime;

    @Override
    public void initialize(URL locUrl, ResourceBundle resourceBundle) {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                dateTime.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm")));
            }
        };
        timer.start();
    }

    @FXML
    public void test() {
        System.out.println("Sending Message :" + tf_msg.getText());
        // client send msg
        tf_msg.setText("");
    }
}
