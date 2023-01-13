package fr.projava.triangle.Models;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Iterator;

public class User {
    private InetAddress IPAddress;
    private int port;
    private String pseudo;
    private final boolean connected= false;
    private final ArrayList<User> ContactBook=new ArrayList<>();

    public User(InetAddress ipAddress, int port, String pseudo) {
        IPAddress = ipAddress;
        this.port = port;
        this.pseudo = pseudo;
    }

    /*
    * GETTERS
    */

    public InetAddress getIPAddress() {
        return IPAddress;
    }
    public int getPort() {
        return port;
    }
    public String getPseudo() {return pseudo;}
    public void setPseudo(String pseudo ) {
        this.pseudo=pseudo;
    }
    public boolean isConnected() {
        return connected;
    }
    public ArrayList<User> getContactBook() {
        return this.ContactBook;
    }

    /*
     * SETTERS
     */
    public void setIPAddress(InetAddress ipAddress) {
        this.IPAddress=ipAddress;
    }
    public void setPort(int port) {
        this.port=port;
    }
    public void addUserToContactBook(User toAdd){
        this.ContactBook.add(toAdd);
    }

    /*
    * Print Connected Users
    */
    public void showConnectedUsers() {
        for(int i = 0; i < this.getContactBook().size(); i++) {
            User u1= this.getContactBook().get(i);
            System.out.println(u1.getPseudo() + " ");
        }
    }

    public void removeUserFromContactBook (User toRmv) {
        int i=0;
        boolean found=false;
        while((i < this.getContactBook().size())&&(!found)) {
            User u1= this.getContactBook().get(i);
            if (toRmv.getPseudo().equals((u1.getPseudo()))) {
                this.ContactBook.remove(i);
                found=true;
            }
            i++;
        }
    }
    public boolean checkValidPseudo() {
        boolean valid=true;
        Iterator<User> iter = this.ContactBook.iterator();
        while ((iter.hasNext())&&(valid)) {
            if (this.pseudo.equals(iter.next().getPseudo())) {valid=false;}

        }
        return valid;
    }
    public boolean checkChangedPseudo(String newPseudo) {
        boolean valid=true;
        for(int i = 0; i < this.getContactBook().size(); i++) {
            User u1= this.getContactBook().get(i);
            if (newPseudo.equals(u1.getPseudo())) {
                valid = false;
                break;
            }
        }
        return valid;
    }

    //Change pseudo with the address received
    public void changePseudoByAddress(User u) {
        int i=0;
        boolean found=false;
        while((i < this.getContactBook().size())&&(!found)) {
            User u1= this.getContactBook().get(i);
            if (u1.getIPAddress().toString().equals(u.getIPAddress().toString())) {
                u1.setPseudo(u.getPseudo());
                found=true;
            }
            i++;
        }
    }
    public boolean existingAddress(User u) {
        int i=0;
        boolean found=false;
        while((i < this.getContactBook().size())&&(!found)) {
            User u1= this.getContactBook().get(i);
            if (u1.getIPAddress().toString().equals(u.getIPAddress().toString())) {
                found=true;
            }
            i++;
        }
        return found;

    }

}
