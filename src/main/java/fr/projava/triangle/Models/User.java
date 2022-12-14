package fr.projava.triangle.Models;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Iterator;

public class User {
    private InetAddress IPAddress;
    private int port;
    private String pseudo;
    private boolean connected= false;
    private ArrayList ContactBook=new ArrayList<>();

    public User(InetAddress ipAddress, int port, String pseudo) {
        IPAddress = ipAddress;
        this.port = port;
        this.pseudo = pseudo;
    }

    public InetAddress getIPAddress() {
        return IPAddress;
    }
    public void setIPAddress(InetAddress ipAdress) {
        this.IPAddress=ipAdress;
    }

    public int getPort() {
        return port;
    }
    public void setPort(int Port) {
        this.port=port;
    }

    public String getPseudo() {
        return pseudo;
    }
    public void setPseudo(String pseudo ) {
        this.pseudo=pseudo;
    }

    public boolean isConnected() {
        return connected;
    }
    public void addUserToContactBook(User toAdd){
        this.ContactBook.add(toAdd);
    }
    public boolean checkValidPseudo() {
        boolean valid=true;
        Iterator<User> iter = this.ContactBook.iterator();
        while ((iter.hasNext())&&(valid)) {
            if (this.pseudo.equals(iter.next())) {valid=false;}

        }
        return valid;
    }
}
