package fr.projava.triangle.Controllers;

import fr.projava.triangle.Models.Message;
import fr.projava.triangle.Models.User;
import fr.projava.triangle.Views.ChatWindowController;

import java.sql.SQLException;
import java.util.ArrayList;

public class ConversationController {
    public static String sendMessage(String id, User user, String message) throws SQLException, InterruptedException {
        boolean success=ThreadController.sendTCP(user,message); //TODO : should be a blocking call !
        //DB controller : add to DB
        System.out.println("Le success est: "+ success);
        if (success)  {
            DatabaseController.addMessage(id,user.getIpInetAddress().getHostAddress(),message, true);
            return "message_sent";
        }
        return "message_not_sent";
    }

    public static void receiveMessage(User user,String remoteIP, String message) throws SQLException {
        //Supposedly, message was received by TCP listener => called once everything is okay
        DatabaseController.addMessage(user.getId(),remoteIP,message, false);

    }
    public static ArrayList<Message> loadHistory(String remoteIP,String id) throws SQLException {
        return DatabaseController.loadHistory(remoteIP,id);
    }
}
