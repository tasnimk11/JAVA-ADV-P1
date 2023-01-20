package fr.projava.triangle.Controllers;
import fr.projava.triangle.Models.User;
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class NetworkController {
    private static DatagramSocket socket;
    private static ServerSocket serverSocket;

    /*
    *************************
    * UDP
    *************************
    * */
    public static String findBroadcastAddress() {
        String filePath = "BroadcastAddress";
        String nextWordAfterBroadcast = "";
        try (Scanner scanner = new Scanner(new BufferedReader(new FileReader(filePath)))) {
            // Boucle à travers chaque ligne du fichier
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                // Utilisez un espace comme délimiteur
                Scanner lineScanner = new Scanner(line).useDelimiter(" ");
                // Boucle à travers chaque mot de la ligne
                while (lineScanner.hasNext()) {
                    String word = lineScanner.next();
                    if (word.equals("BroadcastAddress:")) {
                        // Si le mot actuel est "broadcast", récupérez le prochain mot
                        if (lineScanner.hasNext()) {
                            nextWordAfterBroadcast = lineScanner.next();
                            break;
                        }
                    }
                }
                lineScanner.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Le mot suivant "broadcast" est maintenant stocké dans la variable nextWordAfterBroadcast
        System.out.println(nextWordAfterBroadcast);
        return nextWordAfterBroadcast;

    }
    public static void openSocketUDP() throws SocketException {socket = new DatagramSocket(1108);}
    public static void broadcastUDP(User u, boolean connection) throws IOException {
        int cnx;
        if (connection)
        {cnx=1;} else{cnx=0;}
        String bcMsg;
        try {
            bcMsg = u.getPseudo() + "_" + u.getIpInetAddress() + "_" + u.getPort() + "_" + cnx;
            byte[] buffer = bcMsg.getBytes();
            String broadcastAddress = findBroadcastAddress(); //TODO : adapt according to network
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(broadcastAddress), 1108);
            DatagramSocket socket = new DatagramSocket();
            socket.setBroadcast(true);
            socket.send(packet);
            socket.close();
        }
        catch(NullPointerException e) {
            System.out.println("[NETWORK CONTROLLER] : "+ e.getMessage());
        }
    }
    public static void sendUDP(User sender, User receiver, boolean connection) throws IOException {
            int cnx=0;
            if (connection) {cnx=1;}
            String bcMsg = sender.getPseudo()+"_"+sender.getIpInetAddress()+"_"+sender.getPort()+"_"+cnx;
            byte[] buffer = bcMsg.getBytes();
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, receiver.getIpInetAddress(), 1108);
            DatagramSocket socket = new DatagramSocket();
            socket.setBroadcast(true);
            socket.send(packet);
            socket.close();
    }
     public static String listenUDP() throws IOException {
        byte[] buffer = new byte[512];
        DatagramPacket response = new DatagramPacket(buffer, buffer.length);
        socket.receive(response);
        InetAddress address=response.getAddress();
        String adrIP2=address.toString();
        String reception = new String(response.getData());
        String [] analyseMsg=reception.split("_");
        String UserName=analyseMsg[0];
        String adr=analyseMsg[1];
        String port=analyseMsg[2];
        String cnx=analyseMsg[3];
        reception=UserName+"_"+adrIP2+"_"+port+"_"+adr+"_"+cnx;
        System.out.println("[NETWORK CONTROLLER] : "+ "Message received ="+reception);
        return reception;
    }
    public static void closeListenUDP() {
        socket.close();
    }

    /*
     *************************
     * TCP
     *************************
     * */
    public static boolean sendTCP(User u, String msg) throws IOException {
        Socket link= null;
        try {
            link = new Socket(u.getIpInetAddress(),u.getPort());
            ObjectOutputStream out= new ObjectOutputStream(link.getOutputStream());
            PrintWriter writer = new PrintWriter(out, true);
            writer.println(msg);
            return true;
        } catch (IOException e) {
            //ThreadController.broadcastDisconnection(u);

            return false;
        }


    }
    private static String processText(String msg) {
        if (msg.length()>6){
            return msg.substring(6);
        } else {
            String aux =msg;
            for (int i=0 ; i<msg.length();i++) {
                int a=(int)msg.charAt(i);
                if(a<0 || a>127){
                    aux.substring(1);
                } else {
                    break;
                }
            }
            return aux;
        }
    }
    public static void openServerSocket(int port) throws IOException {serverSocket = new ServerSocket(port);}
    public static String listenTCP(int port) throws IOException {
        Socket clientSocket = serverSocket.accept();
        PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
        InputStreamReader isr =new InputStreamReader(clientSocket.getInputStream(), StandardCharsets.UTF_8);
        BufferedReader in = new BufferedReader(isr);
        String msg = processText(in.readLine());
        System.out.println(msg);
        InetSocketAddress socketAddress = (InetSocketAddress) clientSocket.getRemoteSocketAddress();
        String clientIpAddress = socketAddress.getAddress().getHostAddress();
        System.out.println("[NETWORK CONTROLLER] : "+ "Sent by : "+clientIpAddress);
        msg=msg+"-"+clientIpAddress;
        clientSocket.close();
        return msg;
    }
    public static void closeListenTCP() throws IOException {
        serverSocket.close();
    }

}
