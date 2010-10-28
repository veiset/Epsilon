package epsilonserver;

import java.net.DatagramPacket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * The ServerHandler class manages the network subsystem.
 * this includes starting a serverthread and a packetparsing thread
 *
 * @author mm
 */
public class ServerHandler {

    private Map map;
    private PacketParser packetParser;
    private ServerThread serverThread;
    private BlockingQueue<DatagramPacket> packetQueue;

    /**
     * Constructor
     * Creates a packetqueue and serverthread and packetparser objects
     *
     * @param map
     */
    public ServerHandler(Map map) {
        this.map = map;
        packetQueue = new LinkedBlockingQueue<DatagramPacket>();
        serverThread = new ServerThread(map, packetQueue);
        packetParser = new PacketParser(map, packetQueue);
    }

    /**
     * Start a server thread and a packetparser thread
     */
    public void startServer() {
        new Thread(serverThread).start();
        new Thread(packetParser).start();
    }

    /**
     * Send complete gamestate to players
     */
    public void sendGameState() {
        serverThread.sendGameState();
    }

    /**
     * Stop the server thread
     */
    public void stopServer() {
        serverThread.stopServerThread();   
    }

}
