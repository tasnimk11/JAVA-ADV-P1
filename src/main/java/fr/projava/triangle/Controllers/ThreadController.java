package fr.projava.triangle.Controllers;

import fr.projava.triangle.Models.MThread;
import fr.projava.triangle.Models.User;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;

public class ThreadController {
    static MThread listenerUDP;
    static MThread listenerTCP;
    public ThreadController() {}

    /*
     *************************
     * TCP
     *************************
     * */

    public static void broadcastConnection(User sender, boolean connection) {
        new MThread("BroadcastUDP",sender , connection);
    }

    public static void broadcastDisconnection(User sender) throws IOException {
        sender.setIpInetAddress(InetAddress.getByName("0.0.0.0"));
        sender.setPort(0);
        new MThread("BroadcastUDP",sender , false);
        stopListeningThreadUDP();
        stopListeningThreadTCP();
        NetworkController.closeListenUDP();
        NetworkController.closeListenTCP();
    }

    public static void launchListeningThreadUDP(User receiver) {
        listenerUDP =new MThread("ListeningUDP",receiver);
    }

    public static boolean validPseudo(User u) throws InterruptedException, SocketException {
        NetworkController.openSocketUDP();
        launchListeningThreadUDP(u);
        broadcastConnection(u,false);
        Thread aux= new Thread();
        Thread.sleep(1000);
        return u.checkValidPseudo();
    }

    public static void stopListeningThreadUDP(){
        listenerUDP.stop();}

    /*
     *************************
     * TCP
     *************************
     * */

    public static void launchListeningThreadTCP(User receiver) throws IOException {
        NetworkController.openServerSocket(receiver.getPort());
        listenerTCP =new MThread("ListeningTCP",receiver);}

    public static void stopListeningThreadTCP(){
        listenerTCP.stop();}
    public static boolean sendTCP(User receiver, String msg) throws InterruptedException { MThread s=new MThread("SendTCP",receiver,msg);
        Thread aux = new Thread();
        aux.sleep(200);
        return s.successSend;
    }
}
