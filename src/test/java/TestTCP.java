import fr.projava.triangle.Controllers.NetworkController;
import fr.projava.triangle.Models.User;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class TestTCP {
    public static void main(String[] args) throws IOException {
        User u=new User(InetAddress.getByName("10.1.5.23"), 1234, "Sofiene");
        NetworkController.SendTCP(u,"Salut");
    }
}
