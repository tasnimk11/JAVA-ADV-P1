import fr.projava.triangle.Controllers.ThreadController;
import fr.projava.triangle.Models.User;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class TestConnection2 {


        public String adresse="192.168.1.40";
        ThreadController TC=new ThreadController();
        static User u;

        public TestConnection2() throws UnknownHostException {
            u=new User(InetAddress.getByName(adresse), 1104, "Dorra");
        }

        public static void main(String[] args) throws InterruptedException {
            ThreadController.LaunchListeningThreadUDP(u);

    }

}
