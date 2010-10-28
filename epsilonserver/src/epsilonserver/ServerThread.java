

package epsilonserver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;

/**
 *
 * @author mm
 */
public class ServerThread implements Runnable {

    private Map map;
    private DatagramSocket socket;
    private BlockingQueue<DatagramPacket> packetQueue;
    private int serverPort = 6001;
    private int clientPort = 6002; // not currently in use
    private int BUFFER_SIZE = 256;
    private boolean isRunning = true;


    public ServerThread(Map map, BlockingQueue<DatagramPacket> packetQueue) {
        this.map = map;
        this.packetQueue = packetQueue;

        try {
            socket = new DatagramSocket(serverPort, InetAddress.getLocalHost());
            System.out.println("Socket created");
        }
        catch (SocketException se) {
            System.out.println("Could not create socket");
        }
        catch (UnknownHostException ue) {
            System.out.println("Host address not found");
        }

    }


    public void run() {
        System.out.println("Server is running");

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


    public void sendGameState() {
        byte[] buf = new byte[BUFFER_SIZE];
        DatagramPacket outgoingPacket;

        String gameStateString = map.toString();
        buf = gameStateString.getBytes();

        if (!gameStateString.isEmpty()) {
            System.out.println("\n" + gameStateString + "\n");
        }

        HashMap<String, PlayerEntity> playerList = map.getPlayerList();
        Collection c = playerList.values();
        Iterator it = c.iterator();

        while (it.hasNext()) {
            PlayerEntity p = (PlayerEntity) it.next();
            System.out.println("sending to " + p.getAddress() + " at port " + p.getPort());
            outgoingPacket = new DatagramPacket(buf, buf.length, p.getAddress(), p.getPort());

            try {
                socket.send(outgoingPacket);
                System.out.println("Packet sendt");
            }
            catch (IOException e) {
                System.out.println("Could not send packet to client: " + p.getAddress()
                        + " at port: "+ p.getPort());
            }
        }        
    }


    public void stopServerThread() {
        this.isRunning = false;
    }
    
}
