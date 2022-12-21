package fr.projava.triangle.Controllers;


import fr.projava.triangle.Models.User;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;


public class AccountController {
    private static User u;
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
    public static String connectToAccount(String pseudo) throws UnknownHostException, SQLException, InterruptedException {
        String message ="";
        String ip = InetAddress.getLocalHost().getHostAddress();
        if(DatabaseController.existingAccount(ip,pseudo) == "pseudo_exists") {
            u = new User   (InetAddress.getLocalHost(),1000,pseudo);
            /*bc connection + fill contact book*/
            if (ThreadController.validPseudo(u)){
                ThreadController.BroadcastConnection(u,true);
                message = "Successful Connection";
            } else {
                message = "Unable to connect";
            };

        } else
            message = "Account not found.";
        return message;
    }
    /*public static void disconnect() throws UnknownHostException {
        ThreadController.BroadcastDisconnection(u);
        NetworkController.CloseListenUDP();
    }*/
}
