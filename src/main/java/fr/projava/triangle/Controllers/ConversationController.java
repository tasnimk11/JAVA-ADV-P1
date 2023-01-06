package fr.projava.triangle.Controllers;

import java.sql.SQLException;
import java.util.ArrayList;

public class ConversationController {
    public static void sendMessage(String remoteIP, String message) throws SQLException {
        //Network controller : send TCP
        //To-Do

        //DB controller : add to DB
        DatabaseController.addMessage(remoteIP,message, true);
    }

    public static void receiveMessage(String remoteIP, String message) throws SQLException {
        //Supposedly, message was received by TCP listener => called once everything is okay
        DatabaseController.addMessage(remoteIP,message, false);

    }
    public static ArrayList<String> loadHistory(String remoteIP) throws SQLException {
        return DatabaseController.loadHistory(remoteIP);
    }
}
