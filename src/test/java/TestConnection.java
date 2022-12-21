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
        System.out.println(u.getIPAddress().toString());
        if(ThreadController.validPseudo(u)) {ThreadController.BroadcastConnection(u,true);}
        else{System.out.println("Choose another pseudo");}
        for(int i = 0; i < u.getContactBook().size(); i++) {
            User u1= (User) u.getContactBook().get(i);
            System.out.print(u1.getPseudo() + " " + u1.getIPAddress());
        }
        System.out.println("\n Initiatiating disconnection");
        Thread aux=new Thread();
        aux.sleep(10000);
        ThreadController.BroadcastDisconnection(u);
    }
}

