import fr.projava.triangle.Controllers.NetworkController;
import fr.projava.triangle.Controllers.ThreadController;
import fr.projava.triangle.Models.User;
import java.io.*;
import java.net.*;

import java.net.InetAddress;

public class TestConnection  {
    public static String adresse="192.168.1.40";
    static User u;

    public TestConnection() throws UnknownHostException {
        u=new User(InetAddress.getByName(adresse), 1104, "Sofiene");
    }

    public static void main(String[] args) throws InterruptedException, IOException {

        User u=new User(InetAddress.getByName(adresse), 1108, "Sofiene");
        if(ThreadController.validPseudo(u)) {ThreadController.BroadcastConnection(u,true);}
        for(int i = 0; i < u.getContactBook().size(); i++) {
            System.out.print(u.getContactBook().get(i));
        }
    }}

