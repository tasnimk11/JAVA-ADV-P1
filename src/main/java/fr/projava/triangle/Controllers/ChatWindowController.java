package fr.projava.triangle.Controllers;

import fr.projava.triangle.Models.User;
import fr.projava.triangle.Models.UserObject;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChatWindowController {

    @FXML
    private Button btnSend;
    @FXML
    private TextArea message;
    @FXML
    private ListView lstHistory = new ListView();
    @FXML
    private Button btnDisconnect;
    @FXML
    private VBox vboxConnectedUsers;
    private User user;
    private UserObject userSelected;

    /*
    * *************************************
    * ONLY FOR TESTS
    */
    @FXML
    private Button btnShowConnected;


    public void setUser(User user) throws UnknownHostException {
        this.user = user;
        //ONLY FOR TEST
        String ip1 = "192.17.0.4";
        String ip2 = "195.17.0.4";
        String pseudo1 = "User1";
        String pseudo2 = "User2";
        User testUser1 = new User(InetAddress.getByName(ip1),8000,pseudo1);
        User testUser2 = new User(InetAddress.getByName(ip2),8001,pseudo2);

        user.addUserToContactBook(testUser1);
        user.addUserToContactBook(testUser2);

    }

    /*
     * *************************************
     */

    /*
    * TODO : Message format : Time, get back to line
    * */
    public void sendMessage(MouseEvent mouseEvent) throws SQLException {
        if (userSelected !=null){
            if (!message.getText().isEmpty()) {
                ConversationController.sendMessage(userSelected.getUser(),message.getText());
                lstHistory.getItems().add(user.getPseudo()+" >> " + message.getText());
                message.clear();
            }
        }
    }


    /*
    * Load history :
    *   when User is selected
    *   History is loaded
    *
    * TODO : Unsync  pseudo displayed
    */
    public void loadHistory(String ip) throws SQLException {
        lstHistory.getItems().clear();
        //get all history : conversation history
        ArrayList<String> h;
        h = ConversationController.loadHistory(ip);
        //loop to add elements to list
        for (String s : h) {
            if (s.startsWith(">>")){ //Sender is this connected user
                lstHistory.getItems().add(user.getPseudo() + " " +s);
            } else if (s.startsWith("<<")) { //Sender is selected user
                lstHistory.getItems().add(userSelected.getUser().getPseudo() + " >> " + s.substring(2));
            }

        }
    }

    public void disconnect(MouseEvent mouseEvent) throws IOException {
        AccountController.closeConnection();
        goToAuth(mouseEvent);
    }

    public void goToAuth(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(ChatWindowController.class.getResource("/AuthWindow.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void showConnectedUsers(MouseEvent mouseEvent){
        List<User> connectedUsers = user.getContactBook();
        for(User u : connectedUsers){

            UserObject o = new UserObject(u);
            userSelected = o;
            vboxConnectedUsers.getChildren().add(o);
            o.setOnMouseClicked(
                event -> {
                    try {
                        loadHistory(o.getIP());
                        userSelected = o;

                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            );

        }

    }
}
