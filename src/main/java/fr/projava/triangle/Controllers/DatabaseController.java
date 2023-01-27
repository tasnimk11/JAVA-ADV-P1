package fr.projava.triangle.Controllers;


import fr.projava.triangle.Models.Message;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseController {
    static Connection connection = null;
    public DatabaseController() throws SQLException {
        String url = "jdbc:sqlite:triangle.db";
        connection = DriverManager.getConnection(url);
        System.out.println("[DATABASE CONTROLLER] : Connection to database.");

        if(!connection.isClosed()) {
            System.out.println("[DATABASE CONTROLLER] : Connection to database is successful.");
        } else {
            System.out.println("[DATABASE CONTROLLER] : Connection to database failed.");
        }

        //TODO : Verify if tables exist + create them if not
        ResultSet usersTable = connection.getMetaData().getTables(null, null, "users", null);
        if(usersTable.next()) {
            System.out.println("[DATABASE CONTROLLER] : Table users exists");
        } else {
            System.out.println("[DATABASE CONTROLLER] : Table users does not exist");
            createUsersTable();
        }

        ResultSet historyTable = connection.getMetaData().getTables(null, null, "chat_history", null);
        if(historyTable.next()) {
            System.out.println("[DATABASE CONTROLLER] : Table chat_history exists");
        } else {
            System.out.println("[DATABASE CONTROLLER] : Table chat_history does not exist");
            createHistoryTable();
        }
        
    }

    private void createUsersTable() throws SQLException {
        Statement statement = connection.createStatement();
        statement.setQueryTimeout(30);  // set timeout to 30 sec.
        statement.executeUpdate("CREATE Table users( " +
                "    MAC_address VARCHAR(30) not null unique," +
                "    Pseudo       VARCHAR(20) not null," +
                "    User_ID      VARCHAR     not null);"
        );
        System.out.println("[DATABASE CONTROLLER] : Table users created");
    }

    private void createHistoryTable() throws SQLException {
        Statement statement = connection.createStatement();
        statement.setQueryTimeout(30);  // set timeout to 30 sec.
        statement.executeUpdate("CREATE TABLE chat_history(" +
                "    Remote_User VARCHAR(17)           not null," +
                "    Message      VARCHAR(100)          not null," +
                "    Sent_At      DATETIME default CURRENT_TIMESTAMP," +
                "    Sender       BOOLEAN  default TRUE not null," +
                "    User_ID      VARCHAR);"
        );
        System.out.println("[DATABASE CONTROLLER] : Table chat_history created");
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

}
