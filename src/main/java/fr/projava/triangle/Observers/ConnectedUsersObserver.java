package fr.projava.triangle.Observers;

import fr.projava.triangle.Views.ChatWindowController;
import javafx.application.Platform;

public class ConnectedUsersObserver implements Observer{
    private ChatWindowController cwc;
    public ConnectedUsersObserver(ChatWindowController chatWindowController) {
        this.cwc = chatWindowController;
    }

    @Override
    public void update(String message){
        if(message.startsWith("ConnectedUsers")){
            Platform.runLater(()-> cwc.refreshConnectedUsers());
        }
    }
}
