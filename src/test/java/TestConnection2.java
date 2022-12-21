import fr.projava.triangle.Controllers.ThreadController;
import fr.projava.triangle.Models.User;

import java.net.*;
import java.net.InetAddress;
import java.net.UnknownHostException;


public class TestConnection2 {


    public static String adresse="192.168.1.63";
    ThreadController TC=new ThreadController();
    static User u;

    public TestConnection2() throws UnknownHostException {
        u=new User(InetAddress.getByName(adresse), 1104, "Sofiene");
    }

    public static void main(String[] args) throws InterruptedException, UnknownHostException {
        u=new User(InetAddress.getByName(adresse), 1104, "Sofiene");
        ThreadController.LaunchListeningThreadUDP(u);
        Thread aux=new Thread();
        aux.sleep(2000);
        for(int i = 0; i < u.getContactBook().size(); i++) {
            User u1= (User) u.getContactBook().get(i);
            System.out.print(u1.getPseudo() + " " + u1.getIPAddress());
        }

    }

}
