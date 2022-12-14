package fr.projava.triangle.Controllers;
import fr.projava.triangle.Models.User;
import java.io.*;
import java.net.*;

public class NetworkController {
    private String BroadcastAddress="255.255.255.255";
    public void setBroadcastAdress(String BroadcastAddress) {
        this.BroadcastAddress=BroadcastAddress;
    }
    public void BroadcastUDP(User u, boolean connection) throws IOException {
            int cnx=0;
            if (connection) {cnx=1;}
            String bcMsg = u.getPseudo()+"/"+u.getIPAddress()+"/"+u.getPort()+"/"+cnx;
            byte[] buffer = bcMsg.getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(this.BroadcastAddress), 1108);
            DatagramSocket socket = new DatagramSocket();
            socket.setBroadcast(true);
            socket.send(packet);
            socket.close();
    }
    public void SendUDP(User sender,User receiver, boolean connection) throws IOException {
            int cnx=0;
            if (connection) {cnx=1;}
            String bcMsg = sender.getPseudo()+"/"+sender.getIPAddress()+"/"+sender.getPort()+"/"+cnx;
            byte[] buffer = bcMsg.getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, receiver.getIPAddress(), 1108);
            DatagramSocket socket = new DatagramSocket();
            socket.setBroadcast(true);
            socket.send(packet);
            socket.close();
    }
    public String ListenUDP() throws IOException {

            byte[] buffer = new byte[512];
            DatagramPacket response = new DatagramPacket(buffer, buffer.length);
            DatagramSocket socket = new DatagramSocket(1108);
            socket.receive(response);
            String reception = new String(response.getData());
            return reception;

    }

    public void SendTCP(User u, String msg) throws IOException {
        Socket link=new Socket("10.32.43.235",1234);
        ObjectOutputStream out= new ObjectOutputStream(link.getOutputStream());
        PrintWriter writer = new PrintWriter(out, true);
        writer.println(msg);


    }
    public void ListenTCP() throws IOException {
        ServerSocket serverSocket = new ServerSocket(1234);
        Socket clientSocket = serverSocket.accept();
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        InputStreamReader isr =new InputStreamReader(clientSocket.getInputStream());
        BufferedReader in = new BufferedReader(isr);
        String greeting = in.readLine();
        System.out.println(greeting);
        InetSocketAddress socketAddress = (InetSocketAddress) clientSocket.getRemoteSocketAddress();
        String clientIpAddress = socketAddress.getAddress().getHostAddress();

    }

}
