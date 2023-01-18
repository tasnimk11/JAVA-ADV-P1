package fr.projava.triangle.Views;

import fr.projava.triangle.Controllers.AccountController;
import fr.projava.triangle.Controllers.ConversationController;
import fr.projava.triangle.Models.Message;
import fr.projava.triangle.Models.User;
import fr.projava.triangle.Objects.MessageObject;
import fr.projava.triangle.Objects.UserObject;
import fr.projava.triangle.Observers.ConnectedUsersObserver;
import fr.projava.triangle.Observers.ReceivedMessageObserver;
import fr.projava.triangle.Observers.Subject;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ChatWindowController implements Initializable {


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
    private static User user;
    private static UserObject userSelected;
    @FXML
    private ScrollPane scrollpaneHistory;
    @FXML
    private Label lblMessageNotSent ;


    private final Subject subject = new Subject();
    private final ConnectedUsersObserver connectedUsersObserver = new ConnectedUsersObserver(this);
    private final ReceivedMessageObserver receivedMessageObserver = new ReceivedMessageObserver(this);
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
        ChatWindowController.user = user;
        if(user!=null) {
            lblPseudo.setText(ChatWindowController.user.getPseudo());
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
            System.out.println("[ChatWindow CONTROLLER] : "+ "USER NULL");
        }
    }

    public static boolean isSelected(String userPseudo) {
        return userSelected.getUser().getPseudo().equals(userPseudo);
    }

    /*
    * TODO : Message format : Time + Back to line
    * */
    public void sendMessage(MouseEvent mouseEvent) throws SQLException, InterruptedException {
        if (userSelected !=null){
            if (!message.getText().isEmpty()) {
                if ( ConversationController.sendMessage(user.getId(),userSelected.getUser(),message.getText()).equals("message_sent")){
                    MessageObject msg = new MessageObject(user.getPseudo()+" >> " + message.getText(),true,boxHistory.getWidth());
                    boxHistory.getChildren().add(msg);
                    message.clear();
                } else {
                    lblMessageNotSent.setText("User disconnected");
                }

            }
        }
    }
    /*
    * If sender is selected when a new message os received,
    * The message is added to view
    * */
    public void addMessageReceived(String pseudoSender, String msg){
        MessageObject m = new MessageObject(pseudoSender+" >> " + msg,false,boxHistory.getWidth());
        boxHistory.getChildren().add(m);
    }
    /*
    * If sender is not selected when a new message os received,
    * The appearance of this use, in the contact book, is altered.
    * */
    public void notifyNewMessage(String pseudoSender) {
        vboxConnectedUsers.getChildren().clear();
        ArrayList<User> connectedUsers = user.getContactBook();
        for(User u : connectedUsers){
            UserObject o = new UserObject(u);
            userSelected = o;
            if(o.getUser().getPseudo().equals(pseudoSender)){
                o.hasNewMessage();
            } else {
                o.readMessages();
            }
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

    /*
    * Load history :
    *   when User is selected
    *   History is loaded
    *   TODO : Add Time message sent on to Screen
    */
    public void loadHistory(String ip) throws SQLException {
        boxHistory.getChildren().clear();
        ArrayList<Message> h;
        h = ConversationController.loadHistory(ip,user.getId());
        for (Message m : h) {
            MessageObject msg;
            if(m.isSender()) {//Sender is this connected user
                msg = new MessageObject(user.getPseudo() + " >> " + m.getMessage(), true,boxHistory.getWidth());
            } else {
                msg = new MessageObject(userSelected.getUser().getPseudo() + " >> " + m.getMessage(), false,boxHistory.getWidth());
            }
            boxHistory.getChildren().add(msg);
            boxHistory.setSpacing(5);
            boxHistory.heightProperty().addListener(observable -> scrollpaneHistory.setVvalue(1.0));
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
        lblMessageNotSent.setText("  ");
        vboxConnectedUsers.getChildren().clear();
        boxHistory.getChildren().clear();
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

    /*
     * TODO : take off test examples and load REALLY connected users
     *
     */
    public void refreshConnectedUsers(){
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

    public void changePseudo(MouseEvent mouseEvent) throws SQLException, SocketException, UnknownHostException {
        lblErrorPseudo.setText("  ");
        String msg = AccountController.changePseudo(txtNewPseudo.getText());
        if (msg.equals("pseudo_ok")){
            user.setPseudo(txtNewPseudo.getText());
            lblPseudo.setText(user.getPseudo());
            txtNewPseudo.clear();
        } else {
            lblErrorPseudo.setText("Pseudo in use.");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources){
            subject.addObserver(connectedUsersObserver);
            subject.addObserver(receivedMessageObserver);
    }
}

