package fr.projava.triangle.Controllers;


import java.sql.*;
import java.util.ArrayList;

public class DatabaseController {
    static Connection connection = null;
    public DatabaseController() throws SQLException {
        try {
            String url = "jdbc:sqlite:src/main/java/fr/projava/triangle/db/triangle.db";
            connection = DriverManager.getConnection(url);
            System.out.println("Connection to DB established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static  String existingIP(String ip) throws SQLException {
        String reqSQL= "SELECT * " +
                "FROM users " +
                "WHERE IP_address='" + ip +"'";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(reqSQL);
        if (rs.next()){
            return "ip_exists";
        } else {
            return "ip_new";
        }

    }

    public static String existingAccount(String ip, String pseudo) throws SQLException {
        String reqSQL="SELECT * " +
                "FROM users " +
                "WHERE IP_address='" + ip + "'AND Pseudo='" + pseudo +"'";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(reqSQL);
        if (!rs.next()){
             return "pseudo_new";
        } else {
            return "pseudo_exists";
        }
    }


    public static void addUser(String ip, String pseudo) throws SQLException {
        String reqSQL= "INSERT INTO users (IP_address,Pseudo) VALUES ('"+ ip+"','" + pseudo + "')";
        Statement statement = connection.createStatement();
        statement.executeUpdate(reqSQL);
    }


    public static void addMessage(String remoteUser, String message,Boolean sender) throws SQLException {
        String reqSQL= "INSERT INTO chat_history (Remote_User,Message,Sender) VALUES ('"+ remoteUser + "','" + message + "','" + sender + "')";
        Statement statement = connection.createStatement();
        statement.executeUpdate(reqSQL);
    }


    public static ArrayList<String> loadHistory(String remoteUser) throws SQLException {
        ArrayList<String> h = new ArrayList<>();
        String reqSQL="SELECT * " +
                "FROM chat_history " +
                "WHERE Remote_User='" + remoteUser + "'";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(reqSQL);
        while(rs.next()){
            if (rs.getBoolean("Sender")){
                h.add(">> " + rs.getString("Message"));
            } else {
                h.add("<< " + rs.getString("Message"));
            }

        }
        return h;
    }


}
