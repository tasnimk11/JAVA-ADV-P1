package fr.projava.triangle.Views;

import fr.projava.triangle.Controllers.AccountController;
import fr.projava.triangle.Controllers.ConversationController;
import fr.projava.triangle.Models.Message;
import fr.projava.triangle.Objects.MessageObject;
import fr.projava.triangle.Models.User;
import fr.projava.triangle.Objects.UserObject;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;

public class ChatWindowController {


    @FXML
    private Label lblPseudo;
    @FXML
    private Button btnSend;
    @FXML
    private TextArea message;
    @FXML
    private VBox boxHistory;
    @FXML
    private Button btnDisconnect;
    @FXML
    private VBox vboxConnectedUsers;
    @FXML
    private Button btnChangePseudo;
    @FXML
    private TextField txtNewPseudo;
    @FXML
    private Label lblErrorPseudo;
    private User user;
    private UserObject userSelected;

    /*
    * *************************************
    * ONLY FOR TESTS
    */
    @FXML
    private Button btnShowConnected;


    /*
    TODO : take off testing options + test for real connected users
     */
    public void setUser(User user) throws UnknownHostException {
        this.user = user;
        if(user!=null) {
            lblPseudo.setText(this.user.getPseudo());
            //ONLY FOR TEST
            String ip1 = "192.17.0.4";
            String ip2 = "195.17.0.4";
            String pseudo1 = "User1";
            String pseudo2 = "User2";
            User testUser1 = new User(InetAddress.getByName(ip1), 8000, pseudo1);
            User testUser2 = new User(InetAddress.getByName(ip2), 8001, pseudo2);

            user.addUserToContactBook(testUser1);
            user.addUserToContactBook(testUser2);
        } else {
            System.out.println("[CHAT WINDOW] : USER NULL");
        }

    }

    /*
    * TODO : Message format : Time
    * */
    public void sendMessage(MouseEvent mouseEvent) throws SQLException {
        if (userSelected !=null){
            if (!message.getText().isEmpty()) {
                ConversationController.sendMessage(userSelected.getUser(),message.getText());
                MessageObject msg = new MessageObject(user.getPseudo()+" >> " + message.getText(),true);
                boxHistory.getChildren().add(msg);
                message.clear();
            }
        }
    }


    /*
    * Load history :
    *   when User is selected
    *   History is loaded
    *   TODO : Add Time message sent on to Screen
    */
    private void loadHistory(String ip) throws SQLException {
        boxHistory.getChildren().clear();
        ArrayList<Message> h;
        h = ConversationController.loadHistory(ip);
        for (Message m : h) {
            MessageObject msg;
            if(m.isSender()) {//Sender is this connected user
                msg = new MessageObject(user.getPseudo() + " >> " + m.getMessage(), true);
            } else {
                msg = new MessageObject(userSelected.getUser().getPseudo() + " >> " + m.getMessage(), false);
            }
            boxHistory.getChildren().add(msg);
            boxHistory.setSpacing(5);
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

    /*
    * TODO : take off test examples and load REALLY connected users
    *
     */
    public void showConnectedUsers(MouseEvent mouseEvent){
        vboxConnectedUsers.getChildren().clear();
        ArrayList<User> connectedUsers = user.getContactBook();
        for(User u : connectedUsers){
            UserObject o = new UserObject(u);
            userSelected = o;
            vboxConnectedUsers.getChildren().add(o);
            vboxConnectedUsers.setSpacing(10);
            o.setOnMouseClicked(
                event -> {
                    try {
                        userSelected = o;
                        o.readMessages();
                        loadHistory(o.getIP());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            );

        }

    }

    public void changePseudo(MouseEvent mouseEvent) throws SQLException {
        lblErrorPseudo.setText("  ");
        String msg = AccountController.changePseudo(txtNewPseudo.getText());
        if (msg.equals("pseudo_ok")){
            user.setPseudo(txtNewPseudo.getText());
            lblPseudo.setText(this.user.getPseudo());
            txtNewPseudo.clear();
        } else {
            lblErrorPseudo.setText("Pseudo in use.");
        }


    }
}

