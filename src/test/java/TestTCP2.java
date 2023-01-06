import fr.projava.triangle.Controllers.NetworkController;
import fr.projava.triangle.Models.User;

import java.io.IOException;
import java.net.InetAddress;

public class TestTCP2 {
    public static void main(String[] args) throws IOException {
        User u=new User(InetAddress.getByName("192.168.1.63"), 1234, "Dorra");
        NetworkController.ListenTCP(u.getPort());
    }
}
