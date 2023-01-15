package fr.projava.triangle.Observers;

import fr.projava.triangle.Views.ChatWindowController;
import javafx.application.Platform;

public class ReceivedMessageObserver implements Observer {
    private ChatWindowController cwc;
    public ReceivedMessageObserver(ChatWindowController chatWindowController) {
        this.cwc = chatWindowController;
    }

    @Override
    public void update(String message) {
        if(message.startsWith("NewMessage")){
            String[] msg = message.split(":");
            if(ChatWindowController.isSelected(msg[0])) { // check if the sender is selected
                Platform.runLater(() -> cwc.addMessageReceived(msg[1],msg[2])); // add message to the history
            } else { // show notification
                Platform.runLater(() -> cwc.notifyNewMessage(msg[1]));
            }
        }
    }

}
