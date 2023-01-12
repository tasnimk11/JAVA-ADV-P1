package fr.projava.triangle.Models;
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
        String AdrIP2=AdrIP.substring(1);
        System.out.println(AdrIP2);
        String Port=analyseMsg[2];
        return new User(InetAddress.getByName(AdrIP2),Integer.parseInt(Port),UserName);
    }
    public void run() {
        NetworkController nc;



        if(this.type.equals("ListeningUDP")) {
            /***LISTENING THREAD UDP***/
            try {
                while(true) {
                    String recpt=NetworkController.ListenUDP();
                    String state=recpt.substring(recpt.lastIndexOf("-")+1,recpt.lastIndexOf("-")+2);
                    User u = createOnlineUser(recpt);
                    if ((u.getIPAddress().toString().equals("/0.0.0.0"))&&(u.getPort()==0)) {
                        //REMOVE FROM ANNUAIRE
                        this.receiver.removeUserFromContactBook(u);
                        System.out.println("User "+u.getPseudo()+" removed from contact book succesfully!");
                    }
                    else if(this.receiver.getIPAddress().toString().equals(u.getIPAddress().toString())) {}
                    else {
                        if(state.equals("1")) {
                            //Existing adress in Contact Book => Changing Pseudo
                            if(this.receiver.existingAddress(u)) {this.receiver.changePseudoByAdress(u);
                            System.out.println("Update of pseudo "+u.getPseudo());
                            }
                            else {
                                System.out.println("Adding " + u.getPseudo() + "to contact Book");
                                this.receiver.addUserToContactBook(u);
                                System.out.println("...."+u.getPseudo()+"...."+this.receiver.getPseudo());
                                this.receiver.showConnectedUsers();
                            }
                        }
                        else{
                            //LAUNCH A THREAD SEND UDP
                            System.out.println("Sending coordinates to "+ u.getPseudo() +"from " +this.receiver.getPseudo());
                            new MThread("SendUDP",this.receiver,u,true);
                        }
                    }
                }
            } catch (IOException e)
            {throw new RuntimeException(e);}
        }
        else if(this.type.equals("ListeningPermanentThread")) {

        }
        else if(this.type.equals("ListeningTCP")) {
            try {
                while(true){
                String msg=NetworkController.ListenTCP(this.receiver.getPort());
                String [] analyseMsg=msg.split("-");
                String message=analyseMsg[0];
                String AdrIP=analyseMsg[1];
                //TO DO RECEIVE MESSAGE CONVERSATION CONTROLLER

                }} catch (IOException e) {throw new RuntimeException(e);}
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
