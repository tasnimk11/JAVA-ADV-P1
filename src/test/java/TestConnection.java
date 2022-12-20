import fr.projava.triangle.Controllers.ThreadController;
import fr.projava.triangle.Models.User;
import java.io.*;
import java.net.*;

import java.net.InetAddress;

public class TestConnection  {
    public String adresse="192.168.1.40";
    ThreadController TC=new ThreadController();
    static User u;

    public TestConnection() throws UnknownHostException {
        u=new User(InetAddress.getByName(adresse), 1104, "Sofiene");
    }

    public static void main(String[] args) throws InterruptedException {
        if(ThreadController.validPseudo(u)) {ThreadController.BroadcastConnection(u,true);}
    }
}
