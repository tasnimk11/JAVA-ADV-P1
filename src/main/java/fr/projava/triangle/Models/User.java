package fr.projava.triangle.Models;

import java.net.InetAddress;

public class User {
    private InetAddress IPAddress;
    private int port;
    private String pseudo;
    private boolean connected= false;

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

}
