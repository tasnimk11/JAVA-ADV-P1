package fr.projava.triangle.Models;
import fr.projava.triangle.Controllers.NetworkController;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class MThread extends Thread {
    String type; //type peut-etre ListeningUDP/BroadcastUDP/SendTCP/ListeningTCP
    User receiver;
    User sender;
    boolean connection;
    String msg;

    //CONSTRUCTOR POUR THREAD LISTENING UDP OU TCP
    public MThread(String typeThread,User receiver){
        this.receiver=receiver;
        this.type=typeThread;
        start();
    }
    //CONSTRUCTOR POUR THREAD ENVOI TCP
    public MThread(String typeThread, User receiver , String msg) {
        this.type=typeThread;
        this.receiver=receiver;
        this.msg=msg;
        start();
    }
    //CONSTRUCTOR POUR THREAD BROADCAST UDP
    public MThread(String typeThread,User sender , boolean connection) {
        this.type=typeThread;
        this.sender=sender;
        this.connection=connection;
        start();
    }
    //CONSTRUCTOR POUR THREAD SEND UDP
    public MThread (String typeThread,User sender , User receiver , boolean connection) {
        this.type=typeThread;
        this.sender=sender;
        this.receiver=receiver;
        this.connection=connection;
        start();
    }

    /* CREER UNE INSTANCE USER A AJOUTER A NOTE CONTACT BOOK A PARTIR DU MSG DE CONFIRMATION RECU SUITE A NOTE BROADCAST
     * LA CONFIRMATION EST DE LA FORME USERNAME/PORT/ADRESSE IP*/

    private User createOnlineUser(String recpt) throws UnknownHostException {
        String [] analyseMsg=recpt.split("_");
        String UserName=analyseMsg[0];
        String AdrIP=analyseMsg[1];
        String AdrIP2=AdrIP.substring(1);
        System.out.println(AdrIP2);
        String Port=analyseMsg[2];
        return new User(InetAddress.getByName(AdrIP2),Integer.parseInt(Port),UserName);
    }
    public void run() {

        switch (this.type) {
            case "ListeningUDP":
                //LISTENING THREAD UDP
                try {
                    while (true) {
                        String recpt = NetworkController.ListenUDP();
                        String[] analyseMsg = recpt.split("_");
                        String adr = analyseMsg[3];
                        String state = recpt.substring(recpt.lastIndexOf("_") + 1, recpt.lastIndexOf("_") + 2);
                        User u = createOnlineUser(recpt);
                        if ((adr.equals("/0.0.0.0")) && (u.getPort() == 0)) {
                            //REMOVE FROM ANNUAIRE
                            this.receiver.removeUserFromContactBook(u);
                            System.out.println("User " + u.getPseudo() + " removed from contact book succesfully!");
                        } else if (this.receiver.getIPAddress().toString().equals(adr)) {
                            System.out.println("I am here");
                        } else {
                            if (state.equals("1")) {
                                //Existing address in Contact Book => Changing Pseudo
                                if (this.receiver.existingAddress(u)) {
                                    this.receiver.changePseudoByAddress(u);
                                    System.out.println("Update of pseudo " + u.getPseudo());
                                } else {
                                    System.out.println("Adding " + u.getPseudo() + "to contact Book");
                                    this.receiver.addUserToContactBook(u);
                                }
                            } else {
                                //LAUNCH A THREAD SEND UDP
                                System.out.println("Sending coordinates to " + u.getPseudo() + "from " + this.receiver.getPseudo());
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
                        String msg = NetworkController.ListenTCP(this.receiver.getPort());
                        String[] analyseMsg = msg.split("-");
                        String message = analyseMsg[0];
                        String AdrIP = analyseMsg[1];
                        //TODO RECEIVE MESSAGE CONVERSATION CONTROLLER

                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            case "SendTCP":
                try {
                    NetworkController.SendTCP(receiver, msg);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "BroadcastUDP":
                try {
                    NetworkController.BroadcastUDP(sender, connection);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            case "SendUDP":
                try {
                    NetworkController.SendUDP(sender, receiver, connection);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                break;
            default:
                System.out.println("Type de thread inconnu");
                break;
        }
}}
