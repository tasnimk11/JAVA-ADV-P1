import fr.projava.triangle.Controllers.AccountController;
import fr.projava.triangle.Controllers.NetworkController;
import fr.projava.triangle.Controllers.ThreadController;
import fr.projava.triangle.Models.User;
import java.io.*;
import java.net.*;

import java.net.InetAddress;
import java.sql.SQLException;

import static java.lang.Thread.sleep;

public class TestConnection  {
    public static String adresse="10.1.5.229";
    static User u;

    public TestConnection() throws UnknownHostException {
        u=new User(InetAddress.getByName(adresse), 1104, "Tasnim");
    }

    public static void main(String[] args) throws InterruptedException, IOException, SQLException {
        User u=new User(InetAddress.getByName(adresse), 1108, "Tasnim");
        //System.out.println("TEST connect to account");
        //AccountController.connectToAccount(u.getPseudo());
        //System.out.println("_______________");
        if(ThreadController.validPseudo(u)) {
            ThreadController.BroadcastConnection(u,true);
        }
        else{
            System.out.println("Choose another pseudo");
        }

        System.out.println("______________________ ");
        System.out.println("CONNECTED USERS : ");
        u.showConnectedUsers();
        System.out.println("______________________ ");

        System.out.println("***Testing Change pseudo");
        if(u.checkChangedPseudo("TasnimNewPseudo")) {
            u.setPseudo("TasnimNewPseudo");
            ThreadController.BroadcastConnection(u,true);
        }
        else {
            //A REVOIR MESSAGE
            System.out.println("Pseudo already taken");
        }
        System.out.println("\n Initiatiating disconnection");
        Thread aux=new Thread();
        aux.sleep(10000);
        ThreadController.BroadcastDisconnection(u);
    }
}

