package fr.projava.triangle.Controllers;


import fr.projava.triangle.Models.User;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;


public class AccountController {

    private static User user;
    /*
    * Gets Pseudo from authentication window
    * if pseudo new AND ip none existing then
    *   add user to DB
    * if such entry exists OR IP already used
    *   don't add, and account exists
    * */
    public static String newAccount(String pseudo) throws UnknownHostException, SQLException {
        String message;
        String ip = InetAddress.getLocalHost().getHostAddress();
        if(DatabaseController.existingAccount(ip, pseudo).equals("pseudo_new") && DatabaseController.existingIP(ip).equals("ip_new")) {
            user = new User(ip,pseudo);
            DatabaseController.addUser(user.getId(),ip, pseudo);
            message = "Pseudo created successfully !";
        } else {
            message = "Existing account. Try using another one!";
        }
        return message;
    }

    /*
    * if account exists
    *   create User
    *   switch interface with the right User_ID
    * if account doesn't exist
    *   return error message
    * */
    public static String connectToAccount(String pseudo) throws IOException, SQLException, InterruptedException {
        String message;
        String ip = InetAddress.getLocalHost().getHostAddress();
        if(DatabaseController.existingAccount(ip, pseudo).equals("pseudo_exists")) {
            String id = DatabaseController.getUserID(ip);
            System.out.println("[ACCOUNT CONTROLLER] : "+ "user id =" + id);
            if (user == null) {
                user = new User(DatabaseController.getUserID(ip), InetAddress.getByName(ip), 1108, pseudo);
            } else {
                user.setPort(1108);
            }
            /*bc connection + fill contact book*/
            if (ThreadController.validPseudo(user)){
                ThreadController.broadcastConnection(user,true);
                message = "Successful Connection";
                ThreadController.launchListeningThreadTCP(user);
            } else {
                message  = "Unable to connect";
            }

        } else
            message = "Account not found.";
        return message;
    }

    public static User getUser() {
        System.out.println("[ACCOUNT CONTROLLER] : USER " + user.getPseudo() );
        user.showConnectedUsers();
        return user;
    }

    public static String changePseudo(String pseudo) throws SQLException {
        String msg;
        if(user.checkChangedPseudo(pseudo)) { //BC : if pseudo is already in use
            user.setPseudo(pseudo);
            //BC : new pseudo
            ThreadController.broadcastConnection(user,true);
            //Update user in DB
            DatabaseController.updateAccount(user.getIpInetAddress().getHostAddress(),pseudo);
            msg = "pseudo_ok" ;
        }
        else {
            msg = "pseudo_taken";
        }
        return msg;
    }
    
    public static void closeConnection() throws IOException {
        ThreadController.broadcastDisconnection(user);
    }
}
