package fr.projava.triangle.Controllers;

import fr.projava.triangle.Models.MThread;
import fr.projava.triangle.Models.User;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ThreadController {
    static MThread Listener;
    public ThreadController() {}
    public static void BroadcastConnection(User sender, boolean connection) {
        new MThread("BroadcastUDP",sender , connection);
    }
    public static void BroadcastDisconnection(User sender) throws UnknownHostException {
        sender.setIPAddress(InetAddress.getByName("0.0.0.0"));
        sender.setPort(0);
        new MThread("BroadcastUDP",sender , false);
        StopListeningThread();

    }

    public static void LaunchListeningThreadUDP(User receiver) {
        Listener=new MThread("ListeningUDP",receiver);
    }
    public static void StopListeningThread(){Listener.stop();}
    public void LaunchListeningThreadTCP(User receiver) {
        new MThread("ListeningTCP",receiver);
    }
    public static boolean validPseudo(User u) throws InterruptedException {
        LaunchListeningThreadUDP(u);
        BroadcastConnection(u,false);
        Thread aux= new Thread();
        aux.sleep(1000);
        return u.checkValidPseudo();
    }

}
