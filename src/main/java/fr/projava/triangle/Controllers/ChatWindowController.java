package fr.projava.triangle.Controllers;

import fr.projava.triangle.Models.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

public class ChatWindowController {

    @FXML
    private Button btnSend;
    @FXML
    private TextArea message;
    @FXML
    private ListView lstHistory = new ListView();
    @FXML
    private Button btnUser;
    @FXML
    private Button btnDisconnect;
    private User user;

    /*
    * *************************************
    * ONLY FOR TESTS
    */
    private final String ip1 = "192.17.0.4";
    private final String ip2 = "195.17.0.4";
    private final String pseudo1 = "Mohsen";
    private final String pseudo2 = "Saleh";
    private Scene scene;


    public void setUser(User user) {
        this.user = user;
    }

    /*
     * *************************************
     */

    public void sendMessage(MouseEvent mouseEvent) throws SQLException {
        if (!message.getText().isEmpty()) {
            ConversationController.sendMessage(ip1,message.getText());
            //TODO : Message format : Time, Pseudo, Text, Side to right
            lstHistory.getItems().add(">> " + message.getText());
            message.clear();
        }
    }


    /*
    * Load history : TODO
    *   when User is selected
    *   History is loaded
    */
    public void loadHistory(MouseEvent mouseEvent) throws SQLException {
        lstHistory.getItems().clear();
        //get all history : conversation history
        ArrayList<String> h;
        h = ConversationController.loadHistory(ip1);
        //loop to add elements to list
        for (String s : h) {
            lstHistory.getItems().add(s);
        }
        if (user != null)
             System.out.println("User = "+ user.getPseudo());
        else
            System.out.println("null");
    }

    public void disconnect(MouseEvent mouseEvent) throws IOException {
        AccountController.closeConnection();
        goToAuth(mouseEvent);
    }

    public void goToAuth(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(ChatWindowController.class.getResource("/AuthWindow.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
