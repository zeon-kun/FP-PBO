package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import model.Message;
import model.User;

public class MessageBox {
    private Message message;
    private User messageAuthor;
    private ImageView imageView;
    private HBox hBox;

    public MessageBox(Message message){
        this.message = message;
        this.messageAuthor = message.getMessageAuthor();
        this.imageView = new ImageView(new Image(messageAuthor.getImagePath()));
        this.hBox = new HBox();
    }

    public void create(){
        //create a HBox to align the nodes horizontally
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(5, 5, 5, 10));

        //create an ImageView for user's icon
        imageView.setFitWidth(30);
        imageView.setFitHeight(30);
        hBox.getChildren().add(imageView);

        //create a VBox2 to contains our user's name and user's message
        VBox vBox2 = new VBox();
        vBox2.setPadding(new Insets(0, 0, 0, 12));
            Text userName = new Text(message.getMessageAuthor().getUserName());
            userName.setFont(Font.font("consolas"));
            TextFlow userNameContainer = new TextFlow(userName);
            userNameContainer.setPadding(new Insets(0, 0, 3, 7)); 
        vBox2.getChildren().add(userNameContainer);
            Text text = new Text(message.getMessageText());
            TextFlow textFlow = new TextFlow(text);
            textFlow.setStyle("-fx-background-color: rgb(233,233,235);" + "-fx-background-radius: 20px;");
            textFlow.setPadding(new Insets(5, 10, 5, 10));
        vBox2.getChildren().add(textFlow);

        //add the VBox2 to our HBox
        hBox.getChildren().add(vBox2);
    }
    
    public HBox getHbox(){
        return this.hBox;
    }
}
