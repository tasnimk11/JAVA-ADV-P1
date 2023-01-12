package fr.projava.triangle.Controllers;
import fr.projava.triangle.Models.User;
import java.io.*;
import java.net.*;

public class NetworkController {
    private static DatagramSocket socket;
    private static ServerSocket serverSocket;

    /*static {
        try {
            socket = new DatagramSocket(1108);
        } catch (SocketException e) {
            throw new RuntimeException(e);
        }
    }

    ;*/

    private int i=1108;
    private static String BroadcastAddress="10.1.255.255";
    public NetworkController() throws SocketException {

    }

    public static void BroadcastUDP(User u, boolean connection) throws IOException {
        int cnx;
        if (connection)
        {cnx=1;} else{cnx=0;}
        String bcMsg;
        try {
            bcMsg = u.getPseudo() + "_" + u.getIPAddress() + "_" + u.getPort() + "_" + cnx;
            byte[] buffer = bcMsg.getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(BroadcastAddress), 1108);
            DatagramSocket socket = new DatagramSocket();
            socket.setBroadcast(true);
            socket.send(packet);
            socket.close();
        }
        catch(NullPointerException e) {
            System.out.println("NullPointerException thrown!");
        }
    }
    public static void SendUDP(User sender, User receiver, boolean connection) throws IOException {
            int cnx=0;
            if (connection) {cnx=1;}
            String bcMsg = sender.getPseudo()+"_"+sender.getIPAddress()+"_"+sender.getPort()+"_"+cnx;
            byte[] buffer = bcMsg.getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, receiver.getIPAddress(), 1108);
            DatagramSocket socket = new DatagramSocket();
            socket.setBroadcast(true);
            socket.send(packet);
            socket.close();
    }
    public static void openSocketUDP() throws SocketException {socket = new DatagramSocket(1108);}
    public static String ListenUDP() throws IOException {
        byte[] buffer = new byte[512];
        DatagramPacket response = new DatagramPacket(buffer, buffer.length);
        socket.receive(response);
        InetAddress address=response.getAddress();
        String AdrIP2=address.toString();
        String reception = new String(response.getData());
        String [] analyseMsg=reception.split("_");
        String UserName=analyseMsg[0];
        String adr=analyseMsg[1];
        String Port=analyseMsg[2];
        String cnx=analyseMsg[3];
        reception=UserName+"_"+AdrIP2+"_"+Port+"_"+adr+"_"+cnx;
        System.out.println("this is the reception   "+reception);
        return reception;
    }
    public static void CloseListenUDP() {
        socket.close();
    }
    public static void CloseListenTCP() throws IOException {
        serverSocket.close();
    }

    public static void SendTCP(User u, String msg) throws IOException {
        Socket link=new Socket(u.getIPAddress(),u.getPort());
        ObjectOutputStream out= new ObjectOutputStream(link.getOutputStream());
        PrintWriter writer = new PrintWriter(out, true);
        writer.println(msg);
    }
    public static String ListenTCP(int port) throws IOException {
        serverSocket = new ServerSocket(port);
        Socket clientSocket = serverSocket.accept();
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        InputStreamReader isr =new InputStreamReader(clientSocket.getInputStream());
        BufferedReader in = new BufferedReader(isr);
        String msg = in.readLine().substring(6);
        System.out.println(msg);
        InetSocketAddress socketAddress = (InetSocketAddress) clientSocket.getRemoteSocketAddress();
        String clientIpAddress = socketAddress.getAddress().getHostAddress();
        System.out.println("Sent by "+clientIpAddress);
        msg=msg+"-"+clientIpAddress;
        clientSocket.close();
        return msg;
    }

}
