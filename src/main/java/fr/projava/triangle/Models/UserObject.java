package fr.projava.triangle.Models;

import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class UserObject extends Button {
    private final User user;

    public UserObject(User user){
        this.user=user;
        this.setText(user.getPseudo());
    }

    public String getIP(){
        return user.getIPAddress().getHostAddress();
    }

    public User getUser() {
        return user;
    }
}
