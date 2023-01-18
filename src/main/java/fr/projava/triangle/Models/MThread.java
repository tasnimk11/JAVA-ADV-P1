package fr.projava.triangle.Models;
import fr.projava.triangle.Controllers.ConversationController;
import fr.projava.triangle.Controllers.NetworkController;
import fr.projava.triangle.Observers.Subject;
import fr.projava.triangle.Views.ChatWindowController;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.SQLException;

public class MThread extends Thread {
    private final String type; //Possible Types : ListeningUDP - BroadcastUDP - SendTCP - ListeningTCP
    private User receiver;
    private User sender;
    private boolean connection;
    private String message;
    public boolean successSend=true;

    /*
     * CONSTRUCTOR : LISTENING UDP OR LISTENING TCP
     * */
    public MThread(String typeThread,User receiver){
        this.receiver=receiver;
        this.type=typeThread;
        start();
    }
    /*
     * CONSTRUCTOR : SEND TCP
     * */
    public MThread(String typeThread, User receiver , String message) {
        this.type=typeThread;
        this.receiver=receiver;
        this.message = message;
        start();
    }
    /*
    * CONSTRUCTOR : BROADCAST UDP
    * */
    public MThread(String typeThread,User sender , boolean connection) {
        this.type=typeThread;
        this.sender=sender;
        this.connection=connection;
        start();
    }
    /*
    *CONSTRUCTOR : THREAD SEND UDP
    */
    public MThread (String typeThread,User sender , User receiver , boolean connection) {
        this.type=typeThread;
        this.sender=sender;
        this.receiver=receiver;
        this.connection=connection;
        start();
    }

    /*
     * Creates an instance of the remote User to be added to contactBook of our current user,
     * giving the confirmation message received after Broadcast UDP
     *
     * CONFIRMATION has this form : "pseudo/port/ipAddress"
     */

    private User createOnlineUser(String reception) throws UnknownHostException {
        String [] analyseMsg=reception.split("_");
        String UserName=analyseMsg[0];
        String AdrIP=analyseMsg[1];
        String AdrIP2=AdrIP.substring(1);
        System.out.println("[MThread] : "+"IP 2 = "+AdrIP2);
        String Port=analyseMsg[2];
        return new User(InetAddress.getByName(AdrIP2),Integer.parseInt(Port),UserName);
    }
    public void run() {

        switch (this.type) {
            case "ListeningUDP":
                //LISTENING THREAD UDP
                try {
                    while (true) {
                        String reception = NetworkController.listenUDP();
                        String[] analyseMsg = reception.split("_");
                        String adr = analyseMsg[3];
                        String state = reception.substring(reception.lastIndexOf("_") + 1, reception.lastIndexOf("_") + 2);
                        User u = createOnlineUser(reception);
                        if ((adr.equals("/0.0.0.0")) && (u.getPort() == 0)) {
                            //REMOVE from contactBook
                            this.receiver.removeUserFromContactBook(u);
                            //notify observer
                            Subject.notifyObservers("ConnectedUsers");
                            System.out.println("[MThread] : "+"User " + u.getPseudo() + " removed from contact book successfully!");
                        } else if (this.receiver.getIpInetAddress().toString().equals(adr)) {
                            System.out.println("[MThread] : "+"I am here");
                        } else {
                            if (state.equals("1")) {
                                //Existing address in Contact Book => Changing Pseudo
                                if (this.receiver.existingAddress(u)) {
                                    this.receiver.changePseudoByAddress(u);
                                    //notify observer
                                    Subject.notifyObservers("ConnectedUsers");
                                    System.out.println("[MThread] : "+"Update of pseudo " + u.getPseudo());
                                } else {
                                    System.out.println("[MThread] : "+"Adding " + u.getPseudo() + "to contact Book");
                                    this.receiver.addUserToContactBook(u);
                                    //notify observer
                                    Subject.notifyObservers("ConnectedUsers");
                                    Subject.notifyObservers("refresh_users");
                                }
                            } else {
                                //LAUNCH A THREAD SEND UDP
                                System.out.println("[MThread] : "+"Sending coordinates to " + u.getPseudo() + "from " + this.receiver.getPseudo());
                                new MThread("SendUDP", this.receiver, u, true);
                            }
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            case "ListeningPermanentThread":
                break;
            case "ListeningTCP":
                try {
                    while (true) {
                        String msg = NetworkController.listenTCP(this.receiver.getPort());
                        String[] analyseMsg = msg.split("-");
                        String message = analyseMsg[0];
                        String adrIP = analyseMsg[1];
                        ConversationController.receiveMessage(this.receiver,adrIP,message);
                        //Notify Observer
                        User aux=this.receiver.findByAddress(adrIP);
                        Subject.notifyObservers("NewMessage_"+aux.getPseudo()+"_"+message);
                    }
                } catch (IOException | SQLException e) {
                    throw new RuntimeException(e);
                }
            case "SendTCP":
                try {
                    successSend=NetworkController.sendTCP(receiver, message);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "BroadcastUDP":
                try {
                    NetworkController.broadcastUDP(sender, connection);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "SendUDP":
                try {
                    NetworkController.sendUDP(sender, receiver, connection);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            default:
                System.out.println("[MThread] : "+"Unknown Thread Type");
                break;
        }
}}
