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

    public int getPort() {
        return port;
    }

    public String getPseudo() {
        return pseudo;
    }

    public boolean isConnected() {
        return connected;
    }
}
