import fr.projava.triangle.Controllers.NetworkController;
import fr.projava.triangle.Models.User;

import java.io.IOException;
import java.net.InetAddress;

public class TestTCP2 {
    public static void main(String[] args) throws IOException {
        User u=new User(InetAddress.getByName("10.1.5.229"), 1234, "Tasnim");
        NetworkController.ListenTCP(u.getPort());
    }
}
