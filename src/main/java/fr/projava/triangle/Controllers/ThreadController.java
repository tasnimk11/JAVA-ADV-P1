package fr.projava.triangle.Controllers;
import fr.projava.triangle.Models.MThread;
import fr.projava.triangle.Models.User;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ThreadController {
    static MThread ListenerUDP;
    static MThread ListenerTCP;
    public ThreadController() {}

    public static void BroadcastConnection(User sender, boolean connection) {
        new MThread("BroadcastUDP",sender , connection);
    }

    public static void BroadcastDisconnection(User sender) throws IOException {
        sender.setIPAddress(InetAddress.getByName("0.0.0.0"));
        sender.setPort(0);
        new MThread("BroadcastUDP",sender , false);
        StopListeningThreadUDP();
        StopListeningThreadTCP();
        NetworkController.CloseListenUDP();
        NetworkController.CloseListenTCP();
    }

    public static void LaunchListeningThreadUDP(User receiver) {
        ListenerUDP=new MThread("ListeningUDP",receiver);
    }

    public static void StopListeningThreadUDP(){ListenerUDP.stop();}

    public static void LaunchListeningThreadTCP(User receiver) {ListenerTCP=new MThread("ListeningTCP",receiver);}

    public static void StopListeningThreadTCP(){ListenerTCP.stop();}

    public static boolean validPseudo(User u) throws InterruptedException, SocketException {
        NetworkController.openSocketUDP();
        LaunchListeningThreadUDP(u);
        BroadcastConnection(u,false);
        Thread aux= new Thread();
        aux.sleep(1000);
        return u.checkValidPseudo();
    }
    public static void SendTCP(User receiver, String msg) { new MThread("SendTCP",receiver,msg);}
}
