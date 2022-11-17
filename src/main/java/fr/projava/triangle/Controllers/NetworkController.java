package fr.projava.triangle.Controllers;

import fr.projava.triangle.Models.User;

import java.io.IOException;
import java.net.*;

public class NetworkController {
    private String BroadcastAddress="255.255.255.255";
    public void BroadcastUDP(User u, boolean connection) throws IOException {

            DatagramSocket socket = new DatagramSocket();
            socket.setBroadcast(true);
            String bcMsg = u.getPseudo()+"|"+u.getIPAddress()+"|"+u.getPort()+"|"+connection;
            byte[] buffer = bcMsg.getBytes();

            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(BroadcastAddress), 1108);
            socket.send(packet);
            socket.close();
    }
    public String ListenUDP() throws IOException {

            byte[] buffer = new byte[512];
            DatagramPacket response = new DatagramPacket(buffer, buffer.length);
            DatagramSocket socket = new DatagramSocket();
            socket.receive(response);
            String reception = new String(buffer, 0, response.getLength());
            System.out.println(reception);
            return reception;


    }
}
