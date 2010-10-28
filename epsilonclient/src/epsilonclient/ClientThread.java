

package epsilonclient;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.concurrent.BlockingQueue;

/**
 *
 * @author mm
 */
public class ClientThread implements Runnable {

    private Map map;
    private InetAddress serverAddress;
    private String clientName;
    private DatagramSocket socket;
    private BlockingQueue<DatagramPacket> packetQueue;
    private int serverPort = 6001;
    private int clientPort = 6002; 
    private int BUFFER_SIZE = 256;
    private boolean isRunning = true;


    public ClientThread(Map map, BlockingQueue<DatagramPacket> packetQueue,
            String serverAddress, String clientName) {
        this.map = map;
        this.clientName = clientName;
        this.packetQueue = packetQueue;

        try {
            this.serverAddress = InetAddress.getByName(serverAddress);
            socket = new DatagramSocket(clientPort, InetAddress.getLocalHost());
            System.out.println("Socket created");
        }
        catch (SocketException se) {
            System.out.println("Could not create socket");
        }
        catch (UnknownHostException ue) {
            System.out.println("Unknown host");
        }
    }


    public void run() {
        System.out.println("Started listening for incoming packets");

        byte[] buf = new byte[BUFFER_SIZE];
        DatagramPacket incomingPacket;

        while (isRunning) {
            try {
                incomingPacket = new DatagramPacket(buf, buf.length);
                socket.receive(incomingPacket);
                System.out.println("Packet received from " + incomingPacket.getAddress());
                packetQueue.put(incomingPacket);
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
            catch (InterruptedException ie) {
                System.out.println("Could not add packet to queue");
            }
        }
        socket.close();
    }

    
    public void sendCoordinates(int posX, int posY) {
        byte[] buf = new byte[BUFFER_SIZE];
        DatagramPacket outgoingPacket;

        String playerCoordinates = "";

        playerCoordinates += clientName + " x " + posX + " y " + posY + " ";

        buf = playerCoordinates.getBytes();

        try {
            outgoingPacket = new DatagramPacket(buf, buf.length, serverAddress, serverPort);
            socket.send(outgoingPacket);
            System.out.println("Packet sendt");
        }
        catch (IOException e) {
            System.out.println("Could not send packet to server");
        }

    }

    
    public void stopClientThread() {
        this.isRunning = false;
    }

}
