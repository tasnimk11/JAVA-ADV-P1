package fr.projava.triangle.Controllers;


import fr.projava.triangle.Models.User;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.Enumeration;


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
        String message ="";
        String ip = InetAddress.getLocalHost().getHostAddress();
        if(DatabaseController.existingAccount(ip,pseudo) == "pseudo_new" && DatabaseController.existingIP(ip)=="ip_new") {
            DatabaseController.addUser(ip, pseudo);
            message = "Pseudo created successfully !";
        } else
            message = "Existing account. Try using another one!";
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
        String message ="";
        if(DatabaseController.existingAccount("10.1.5.229",pseudo) == "pseudo_exists") {
            user = new User(InetAddress.getByName("10.1.5.229"),1108,pseudo);
            /*bc connection + fill contact book*/
            if (ThreadController.validPseudo(user)){

                System.out.println("______________________ ");
                System.out.println("CONNECTED USERS : "+ user.getPseudo());
                user.showConnectedUsers();
                System.out.println("______________________ ");

                ThreadController.BroadcastConnection(user,true);

                message = "Successful Connection";
                ThreadController.LaunchListeningThreadTCP(user);
            } else {
                message = "Unable to connect";
            };

        } else
            message = "Account not found.";
        return message;
    }

    public static User getUser() {
        return user;
    }

    public static void changePseudo(String pseudo){
        if(user.checkChangedPseudo("SofieneNewPseudo")) {
            user.setPseudo("SofieneNewPseudo");
            ThreadController.BroadcastConnection(user,true);
        }
        else {
            //A REVOIR MESSAGE
            System.out.println("Pseudo already taken");
        }
    }
    
    public static void closeConnection() throws UnknownHostException {
        ThreadController.BroadcastDisconnection(user);
    }
}
