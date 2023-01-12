package fr.projava.triangle.Controllers;

import fr.projava.triangle.Models.Message;
import fr.projava.triangle.Models.User;

import java.sql.SQLException;
import java.util.ArrayList;

public class ConversationController {
    public static void sendMessage(User user, String message) throws SQLException {
        ThreadController.SendTCP(user,message);
        //DB controller : add to DB
        DatabaseController.addMessage(user.getIPAddress().getHostAddress(),message, true);
    }

    public static void receiveMessage(String remoteIP, String message) throws SQLException {
        //Supposedly, message was received by TCP listener => called once everything is okay
        //TODO : Every Message received is added to DB (TCP)
        //TODO : connected user sending the message is highlighted in front
        DatabaseController.addMessage(remoteIP,message, false);

    }
    public static ArrayList<Message> loadHistory(String remoteIP) throws SQLException {
        return DatabaseController.loadHistory(remoteIP);
    }
}
