package fr.projava.triangle.Controllers;

import fr.projava.triangle.Models.MThread;
import fr.projava.triangle.Models.User;

public class ThreadController {
    public ThreadController() {}
    public static void BroadcastConnection(User sender, boolean connection) {
        new MThread("BroadcastUDP",sender , connection);
    }
    public static void LaunchListeningThreadUDP(User receiver) {
        new MThread("ListeningUDP",receiver);
    }
    public void LaunchListeningThreadTCP(User receiver) {
        new MThread("ListeningTCP",receiver);
    }
    public static boolean validPseudo(User u) throws InterruptedException {
        BroadcastConnection(u,false);
        MThread MT=new MThread("ListeningUDP",u);
        Thread aux= new Thread();
        aux.sleep(5000);
        return u.checkValidPseudo();
    }

}
