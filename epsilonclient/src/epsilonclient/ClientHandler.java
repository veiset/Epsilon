package epsilonclient;

import java.net.DatagramPacket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * The ClientHandler class manages the network subsystem.
 * this includes starting a clientthread and a packetparsing thread
 *
 * @author mm
 */
public class ClientHandler {

    private Map map;
    private PacketParser packetParser;
    private ClientThread clientThread;
    private BlockingQueue<DatagramPacket> packetQueue;

    /**
     * Constructor
     * Creates a packetqueue, clientthread and packetparser objects
     *
     * @param map
     */
    public ClientHandler(Map map, String serverAddress, String clientName, TestPanel tp) {
        this.map = map;
        packetQueue = new LinkedBlockingQueue<DatagramPacket>();
        clientThread = new ClientThread(map, packetQueue, serverAddress, clientName);
        packetParser = new PacketParser(map, packetQueue, tp);
    }

    /**
     * Start a server thread and a packetparser thread
     */
    public void startClient() {
        new Thread(clientThread).start();
        new Thread(packetParser).start();
    }

    /**
     * Send player coordinates to the server
     *
     * @param posX
     * @param posY
     */
    public void sendCoordinates(int posX, int posY) {
        clientThread.sendCoordinates(posX, posY);
    }

    /**
     * Stop the clientthread
     */
    public void stopClient() {
        clientThread.stopClientThread();
    }

}
