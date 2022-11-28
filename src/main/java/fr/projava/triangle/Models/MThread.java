package fr.projava.triangle.Models;


import fr.projava.triangle.Controllers.NetworkController;

import java.io.IOException;

public class MThread extends Thread {
    String type; //type peut etre ListeningUDP/BroadcastUDP/SendTCP/ListeningTCP
    User receiver;
    User sender;
    boolean connection;
    String msg;

    //CONSTRUCTEUR POUR THREAD LISTENING UDP OU TCP
    public MThread(String typeThread){
        this.type=typeThread;
        start();
    }
    //CONSTRUCTEUR POUR THREAD ENVOI TCP
    public MThread(String typeThread, User receiver , String msg) {
        this.type=typeThread;
        this.receiver=receiver;
        this.msg=msg;
    }
    //CONTRUCTEUR POUR THREAD BROADCAST UDP
    public MThread(User sender , boolean connection) {
        this.sender=sender;
        this.connection=connection;
    }

    public void run() {
        NetworkController nc=new NetworkController();
        if(this.type.equals("ListeningUDP")) {
            try {nc.ListenUDP();} catch (IOException e) {throw new RuntimeException(e);}
        }
        else if(this.type.equals("ListeningTCP")) {
            try {nc.ListenTCP();} catch (IOException e) {throw new RuntimeException(e);}
        }
        else if(this.type.equals("SendTCP")) {
            try {nc.SendTCP(receiver,msg);} catch (IOException e) {throw new RuntimeException(e);}
        }
        else if(this.type.equals("BroadcastUDP")){
            try {nc.BroadcastUDP(sender,connection);} catch (IOException e) {throw new RuntimeException(e);}
        }
        else { System.out.println("Type de thread inconnu");}

}}
