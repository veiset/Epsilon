package epsilonserver.net;

import epsilonserver.entity.EntityHandler;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * The ServerHandler class manages the network subsystem.
 * this includes starting a serverthread and a packetparsing thread
 *
 * @author mm
 */
public class NetworkHandler {

    public static final int SERVER_PORT = 6001;
    public static final int CLIENT_PORT = 6002;
    public static final int BUFFER_SIZE = 256;

    private PacketParser parser;
    private ListenerThread listener;
    private SenderThread sender;

    private BlockingQueue<DatagramPacket> incomingPacketQueue;
    private BlockingQueue<DatagramPacket> outgoingPacketQueue;
    private HashMap<String, double[]> playerPosList;
    private ArrayList<String> newPlayers;
    
    private DatagramSocket socket;

    /**
     * Private constructor
     */
    private NetworkHandler() {
        incomingPacketQueue = new LinkedBlockingQueue<DatagramPacket>();
        outgoingPacketQueue = new LinkedBlockingQueue<DatagramPacket>();
        playerPosList = new HashMap<String, double[]>();
        newPlayers = new ArrayList<String>();
    }

    /**
     * inner class to create a instance of NetworkHandler. this is
     * loaded when NetworkHandlre.getInstance() method is called or
     * when INSTANCE is accessed.
     */
    private static class NetworkHandlerHolder {
        public static final NetworkHandler INSTANCE = new NetworkHandler();
    }

    /**
     * Get instance of NetworkHandler
     * @return
     */
    public static NetworkHandler getInstance() {
        return NetworkHandlerHolder.INSTANCE;
    }

    /**
     * Start a server thread and a packetparser thread
     */
    public void startServer() {

        try {
            socket = new DatagramSocket(SERVER_PORT, InetAddress.getLocalHost());
            System.out.println("Socket created on interface " + InetAddress.getLocalHost());
        }
        catch (SocketException se) {
            System.out.println("Could not create socket");
        }
        catch (UnknownHostException ue) {
            System.out.println("Could not resolve address");
        }

        listener = new ListenerThread(socket, incomingPacketQueue);
        parser = new PacketParser(incomingPacketQueue);
        sender = new SenderThread(socket, outgoingPacketQueue);

        new Thread(listener).start();
        new Thread(parser).start();
        new Thread(sender).start();
    }

    /**
     * Stop the server
     */
    public void stopServer() {
        listener.stopListener();
        parser.stopParser();
        sender.stopSender();
        listener = null;
        parser = null;
        sender = null;
    }

    /**
     * Send gamestate to players
     */
    public void updateClients() {
        sender.addToSendQueue();
    }



}
