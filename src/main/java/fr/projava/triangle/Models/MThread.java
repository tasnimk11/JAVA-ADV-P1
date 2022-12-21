package fr.projava.triangle.Models;

//A FAIRE CONTINUER LA PHASE DE CONNEXION
import fr.projava.triangle.Controllers.NetworkController;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class MThread extends Thread {
    String type; //type peut etre ListeningUDP/BroadcastUDP/SendTCP/ListeningTCP
    User receiver;
    User sender;
    boolean connection;
    String msg;

    //CONSTRUCTEUR POUR THREAD LISTENING UDP OU TCP
    public MThread(String typeThread,User receiver){
        this.receiver=receiver;
        this.type=typeThread;
        start();
    }
    //CONSTRUCTEUR POUR THREAD ENVOI TCP
    public MThread(String typeThread, User receiver , String msg) {
        this.type=typeThread;
        this.receiver=receiver;
        this.msg=msg;
        start();
    }
    //CONTRUCTEUR POUR THREAD BROADCAST UDP
    public MThread(String typeThread,User sender , boolean connection) {
        this.type=typeThread;
        this.sender=sender;
        this.connection=connection;
        start();
    }
    //CONSTRUCTEUR POUR THREAD SEND UDP
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
        String [] analyseMsg=recpt.split("-");
        String UserName=analyseMsg[0];
        String AdrIP=analyseMsg[1];
        String Port=analyseMsg[2];
        return new User(InetAddress.getByName(AdrIP),Integer.parseInt(Port),UserName);
    }
    public void run() {
        NetworkController nc;



        if(this.type.equals("ListeningUDP")) {
            /***LISTENING THREAD UDP***/
            try {
                //while(true) {
                    String recpt=NetworkController.ListenUDP();
                    String state=recpt.substring(recpt.lastIndexOf("-")+1,recpt.lastIndexOf("-")+2);
                    User u = createOnlineUser(recpt);
                    System.out.println(recpt);
                    if(state.equals("1")) {
                        System.out.println(u.getPseudo());
                        this.receiver.addUserToContactBook(u);
                    }
                    else{
                        //LAUNCH A THREAD SEND UDP
                        new MThread("SendUDP",this.receiver,u,true);
                    }
                //}
            } catch (IOException e)
            {throw new RuntimeException(e);}
        }
        else if(this.type.equals("ListeningPermanentThread")) {

        }
        else if(this.type.equals("ListeningTCP")) {
            try {NetworkController.ListenTCP();} catch (IOException e) {throw new RuntimeException(e);}
        }
        else if(this.type.equals("SendTCP")) {
            try {NetworkController.SendTCP(receiver,msg);} catch (IOException e) {throw new RuntimeException(e);}
        }
        else if(this.type.equals("BroadcastUDP")){
            try {NetworkController.BroadcastUDP(sender,connection);} catch (IOException e) {throw new RuntimeException(e);}
        }
        else if(this.type.equals("SendUDP")) {
            try {NetworkController.SendUDP(sender,receiver,connection);} catch (IOException e) {throw new RuntimeException(e);}
        }
        else { System.out.println("Type de thread inconnu");}

}}
