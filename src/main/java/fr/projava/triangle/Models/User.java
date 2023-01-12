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
    public User(int port, String pseudo) {
        this.port=port;
        this.pseudo=pseudo;
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
        this.port=Port;
    }

    public String getPseudo() {return pseudo;}
    public void setPseudo(String pseudo ) {
        this.pseudo=pseudo;
    }

    public boolean isConnected() {
        return connected;
    }
    public void addUserToContactBook(User toAdd){
        this.ContactBook.add(toAdd);
    }
    public void removeUserFromContactBook (User toRmv) {
        int i=0;
        boolean found=false;
        while((i < this.getContactBook().size())&&(!found)) {
            User u1= (User) this.getContactBook().get(i);
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
            User u1= (User) this.getContactBook().get(i);
            if(newPseudo.equals(u1.getPseudo())){
                valid=false;
            }
        }
        return valid;
    }
    public ArrayList getContactBook() {
        return this.ContactBook;
    }

    //Change pseudo with the adress received
    public void changePseudoByAdress(User u) {
        int i=0;
        boolean found=false;
        while((i < this.getContactBook().size())&&(!found)) {
            User u1= (User) this.getContactBook().get(i);
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
            User u1= (User) this.getContactBook().get(i);
            if (u1.getIPAddress().toString().equals(u.getIPAddress().toString())) {
                found=true;
            }
            i++;
        }
        return found;

    }
    public void showConnectedUsers() {
        for(int i = 0; i < this.getContactBook().size(); i++) {
            User u1= (User) this.getContactBook().get(i);
            System.out.print(u1.getPseudo() + " ");
        }
    }
}
