package fr.projava.triangle.Controllers;


import fr.projava.triangle.Models.User;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.Enumeration;
import java.util.UUID;


public class AccountController {

    private static User user;


    private static String getMAC() throws UnknownHostException, SocketException {
        InetAddress localHost = InetAddress.getLocalHost();
        Enumeration<NetworkInterface> ni = NetworkInterface.getNetworkInterfaces();

        while(ni.hasMoreElements()){
            byte[] hardwareAddress = ni.nextElement().getHardwareAddress();
            String[] hexadecimal= new String[hardwareAddress.length];
            for (int i = 0; i < hardwareAddress.length; i++) {
                hexadecimal[i] = String.format("%02X", hardwareAddress[i]);
            }
            return String.join("-", hexadecimal);
        }
        /*byte[] hardwareAddress = ni.getHardwareAddress();
        System.out.println("[ACCOUNT CONTROLLER] : mac 1 = "+ hardwareAddress);
        */
        return "No mac";
    }
    /*
    * Gets Pseudo from authentication window
    *   if pseudo new AND MAC_address are none existing then
    *       add user to DB + give him an ID
    *   if such MAC_address already used
    *       don't add, and give back a message = account exists
    * */
    public static String newAccount(String pseudo) throws SQLException, SocketException, UnknownHostException {
        String message;
        String mac = getMAC();
        System.out.println("[ACCOUNT CONTROLLER] mac=" +mac);
        if(DatabaseController.existingMAC(mac).equals("mac_new")) {
            DatabaseController.addUser(UUID.randomUUID().toString(), mac, pseudo); // Adding User with a new pseudo
            message = "Pseudo created successfully !";
        } else {
            message = "Existing account.";
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
        String mac = getMAC();
        System.out.println("[ACCOUNT CONTROLLER] mac=" +mac);
        InetAddress ip = InetAddress.getByName(InetAddress.getLocalHost().getHostAddress());
        if(DatabaseController.existingAccount(mac, pseudo).equals("pseudo_exists")) { //Ready to connect
            String id = DatabaseController.getUserID(mac);
            System.out.println("[ACCOUNT CONTROLLER] : "+ "user id =" + id);
            user = new User(ip, id, 1108, pseudo);
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
    public static String changePseudo(String pseudo) throws SQLException, SocketException, UnknownHostException {
        String msg;
        if(user.checkChangedPseudo(pseudo)) { //BC : if pseudo is already in use
            user.setPseudo(pseudo);
            //BC : new pseudo
            ThreadController.broadcastConnection(user,true);
            //Update user in DB
            DatabaseController.updateAccount(getMAC(),pseudo);
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
