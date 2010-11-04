package epsilon.net;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * NetworkHandler starts the network subsystem
 * This class uses Bill Pugh's singleton pattern
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
     * Inner class to create a instance of NetworkHandler which is
     * loaded when NetworkHandler.getInstance() method is called or
     * when INSTANCE is accessed.
     */
    private static class NetworkHandlerHolder {
        public static final NetworkHandler INSTANCE = new NetworkHandler();
    }

    /**
     * Get instance of NetworkHandler
     * @return INSTANCE
     */
    public static NetworkHandler getInstance() {
        return NetworkHandlerHolder.INSTANCE;
    }

    /**
     * setup socket, networklistener and packetparser
     */
    public void connect(InetAddress serverAddress, String name) {

        try {

            InetAddress bindIP = getFirstNonLoopbackAddress(true, false);
            socket = new DatagramSocket(CLIENT_PORT, bindIP);
            System.out.println("Socket created on non-loopback interface " + bindIP);
        }
        catch (SocketException se) {
            System.out.println("Could not create socket");
        }


        listener = new ListenerThread(socket, incomingPacketQueue);
        parser = new PacketParser(incomingPacketQueue, playerPosList, this, name);
        sender = new SenderThread(socket, serverAddress, name, outgoingPacketQueue);

        new Thread(listener).start();
        new Thread(parser).start();
        new Thread(sender).start();

    }

    /**
     * stop server threads and close socket
     */
    public void disconnect() {
        listener.stopListener();
        parser.stopParser();
        sender.stopSender();
    }

    /**
     * Send player position to server
     */
    public void sendPlayerAction() {
        sender.addToSendQueue();
    }

    /**
     * TODO: handshake with server to establish a connection
     * 
     * @return handshake_result
     */
    public boolean handShake() {

        boolean handshake_result = false;

        return handshake_result;
    }

    /**
     * Get a networks players position by name
     *
     * @param playerName
     * @return playerPos
     */
    public double[] getPlayerPositionByName(String playerName) {
        double[] playerPos = playerPosList.get(playerName);
        return playerPos;
    }

    /**
     * Check if there are new players
     *
     * @return hasNewPlayers
     */
    public boolean hasNewPlayers() {
        boolean newPlayerState = false;

        if (!newPlayers.isEmpty()) {
            newPlayerState = true;
        }
        
        return newPlayerState;
    }

    /**
     * Get name of player added last to the new player list
     * 
     * @return newPlayer
     */
    public synchronized String getNewPlayer() {
        int arraySize = newPlayers.size();
        String newPlayer = newPlayers.get(arraySize-1);
        newPlayers.remove(arraySize-1);
        return newPlayer;
    }

    /**
     * Add a players name to the new players list
     *
     * @param name
     */
    public synchronized void addNewPlayer(String playerName) {
       newPlayers.add(playerName);
    }

    /**
     * 
     *
     * @param preferIpv4
     * @param preferIPv6
     * @return
     * @throws SocketException
     */
    private static InetAddress getFirstNonLoopbackAddress(boolean preferIpv4, boolean preferIPv6) throws SocketException {
        Enumeration en = NetworkInterface.getNetworkInterfaces();
        while (en.hasMoreElements()) {
            NetworkInterface i = (NetworkInterface) en.nextElement();
            for (Enumeration en2 = i.getInetAddresses(); en2.hasMoreElements();) {
                InetAddress addr = (InetAddress) en2.nextElement();
                if (!addr.isLoopbackAddress()) {
                    if (addr instanceof Inet4Address) {
                        if (preferIPv6) {
                            continue;
                        }
                        return addr;
                    }
                    if (addr instanceof Inet6Address) {
                        if (preferIpv4) {
                            continue;
                        }
                        return addr;
                    }
                }
            }
        }
        return null;
    }
    
}
