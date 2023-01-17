package fr.projava.triangle.Controllers;


import fr.projava.triangle.Models.Message;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseController {
    static Connection connection = null;
    public DatabaseController() {
        try {
            String url = "jdbc:sqlite:src/main/java/fr/projava/triangle/db/triangle.db";
            connection = DriverManager.getConnection(url);
            System.out.println("[DATABASE CONTROLLER] : "+"Connection to DB established.");

        } catch (SQLException e) {
            System.out.println("[DATABASE CONTROLLER] : "+ e.getMessage());
        }
    }

    /*
    * Verifies if MAC isn't already used
    * which means that : if any account is already defined for this computer
    * */
    public static  String existingMAC(String mac) throws SQLException {
        String reqSQL= "SELECT * " +
                "FROM users " +
                "WHERE MAC_address='" + mac +"'";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(reqSQL);
        if (rs.next()){
            return "mac_exists";
        } else {
            return "mac_new";
        }

    }

    /*
    * Verifies if an account was created using this MAC address and with this pseudo
    * */
    public static String existingAccount(String mac, String pseudo) throws SQLException {
        String reqSQL="SELECT * " +
                "FROM users " +
                "WHERE MAC_address='" + mac + "'AND Pseudo='" + pseudo +"'";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(reqSQL);
        if (!rs.next()){
             return "pseudo_new";
        } else {
            return "pseudo_exists";
        }
    }


    /*
    * Add a new entry to DB : id, pseudo and mac
    * */
    public static void addUser(String uid, String mac, String pseudo) throws SQLException {
        String reqSQL= "INSERT INTO users (User_ID,MAC_address,Pseudo) VALUES ('"+ uid+"',+ '"+ mac+"','" + pseudo + "')";
        Statement statement = connection.createStatement();
        statement.executeUpdate(reqSQL);
    }


    public static void addMessage(String id, String remoteUser, String message,Boolean sender) throws SQLException {
        String reqSQL;
        if(sender){
            reqSQL= "INSERT INTO chat_history (User_ID, Remote_User,Message,Sender) VALUES ('"+ id + "','"+ remoteUser + "','" + message + "','" + 1 + "')";
        } else {
            reqSQL= "INSERT INTO chat_history (User_ID, Remote_User,Message,Sender) VALUES ('"+ id + "',+'"+ remoteUser + "','" + message + "','" + 0 + "')";
        }
        Statement statement = connection.createStatement();
        statement.executeUpdate(reqSQL);
    }


    public static ArrayList<Message> loadHistory(String remoteUser,String id) throws SQLException {
        ArrayList<Message> h = new ArrayList<>();
        String reqSQL="SELECT * " +
                "FROM chat_history " +
                "WHERE Remote_User='" + remoteUser + "'AND User_ID='" + id +"'";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(reqSQL);
        while(rs.next()){
                //h.add(new Message(rs.getBoolean("Sender"), rs.getString("Message"), rs.getDate("Sent_At")));
                h.add(new Message(rs.getBoolean("Sender"), rs.getString("Message")));
        }
        return h;
    }

    public static void updateAccount(String mac, String newPseudo) throws SQLException {
        String reqSQL= "UPDATE users " +
                "SET  Pseudo='"+ newPseudo + "'" +
                "WHERE MAC_address='" + mac + "'";
        Statement statement = connection.createStatement();
        statement.executeUpdate(reqSQL);

    }


    public static String getUserID(String mac) throws SQLException {
        String reqSQL="SELECT * " +
                "FROM users " +
                "WHERE MAC_address='" + mac + "'";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(reqSQL);
        if (rs.next()){
            return rs.getString("User_ID");
        } else {
            return "NO_ID";
        }
    }
}
