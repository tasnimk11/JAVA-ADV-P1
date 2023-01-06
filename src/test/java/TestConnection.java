import fr.projava.triangle.Controllers.AccountController;
import fr.projava.triangle.Controllers.NetworkController;
import fr.projava.triangle.Controllers.ThreadController;
import fr.projava.triangle.Models.User;
import java.io.*;
import java.net.*;

import java.net.InetAddress;

import static java.lang.Thread.sleep;

public class TestConnection  {
    public static String adresse="192.168.1.40";
    static User u;

    public TestConnection() throws UnknownHostException {
        u=new User(InetAddress.getByName(adresse), 1104, "Sofiene");
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        User u=new User(InetAddress.getByName(adresse), 1108, "Sofiene");
        if(ThreadController.validPseudo(u)) {ThreadController.BroadcastConnection(u,true);}
        else{System.out.println("Choose another pseudo");}
        u.showConnectedUsers();
        System.out.println("\n Initiatiating disconnection");
        Thread aux=new Thread();
        aux.sleep(10000);
        ThreadController.BroadcastDisconnection(u);
    }
}

