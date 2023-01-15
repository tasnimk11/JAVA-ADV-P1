package fr.projava.triangle.Objects;

import fr.projava.triangle.Models.User;
import javafx.scene.control.Button;

public class UserObject extends Button {
    private final User user;

    public UserObject(User user){
        this.user=user;
        this.setText(user.getPseudo());
        this.readMessages();
    }

    public String getIP(){
        return user.getIpInetAddress().getHostAddress();
    }

    public User getUser() {
        return user;
    }

    public void hasNewMessage(){
        this.setStyle("-fx-background-color: #ea51f3 ; -fx-text-fill: #FFFFFF");
    }

    public void readMessages() {
        this.setStyle("-fx-background-color: #FFFFFF ; -fx-text-fill: #000000");
    }
}