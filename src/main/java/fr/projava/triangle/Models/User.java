package fr.projava.triangle.Models;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Iterator;

public class User {
    private String id;
    private InetAddress ipInetAddress;
    private int port;
    private String pseudo;
    private final boolean connected= false;
    private final ArrayList<User> contactBook =new ArrayList<>();

    public User(InetAddress ipAddress, int port, String pseudo) {
        this.ipInetAddress = ipAddress;
        this.port = port;
        this.pseudo = pseudo;
    }

    public User(InetAddress ip, String id, int port, String pseudo) {
        this.ipInetAddress = ip;
        this.id = id;
        this.port = port;
        this.pseudo = pseudo;
    }


    /*
    * GETTERS
    */

    public String getId() {
        return id;
    }
    public InetAddress getIpInetAddress() {
        return ipInetAddress;
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
        return this.contactBook;
    }

    /*
     * SETTERS
     */

    public void setId(String id) {
        this.id = id;
    }

    public void setIpInetAddress(InetAddress ipAddress) {
        this.ipInetAddress =ipAddress;
    }
    public void setPort(int port) {
        this.port=port;
    }
    public void addUserToContactBook(User toAdd){
        this.contactBook.add(toAdd);
    }

    /*
    * Print Connected Users
    */
    public void showConnectedUsers() {
        for(int i = 0; i < this.getContactBook().size(); i++) {
            User u1= this.getContactBook().get(i);
            System.out.println("[USER] : "+ u1.getPseudo() + " ");
        }
    }
    public void removeUserFromContactBook (User toRmv) {
        int i=0;
        boolean found=false;
        while((i < this.getContactBook().size())&&(!found)) {
            User u1= this.getContactBook().get(i);
            if (toRmv.getPseudo().equals((u1.getPseudo()))) {
                this.contactBook.remove(i);
                found=true;
            }
            i++;
        }
    }
    public boolean checkValidPseudo() {
        boolean valid=true;
        Iterator<User> iter = this.contactBook.iterator();
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
            if (u1.getIpInetAddress().toString().equals(u.getIpInetAddress().toString())) {
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
            if (u1.getIpInetAddress().toString().equals(u.getIpInetAddress().toString())) {
                found=true;
            }
            i++;
        }
        return found;

    }
    public User findByAddress(String adr) { 
        String adr2="/"+adr;
        int i=0;
        User u1 = null;
        boolean found=false;
        while((i < this.getContactBook().size())&&(!found)) {
             u1=this.getContactBook().get(i);
            if (u1.getIpInetAddress().toString().equals(adr2)) {
                found=true;
            }
            i++;
        }
        return u1;
        
    }

}
