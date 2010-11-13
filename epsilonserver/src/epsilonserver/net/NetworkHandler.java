package epsilonserver.net;

import epsilonserver.entity.EntityHandler;
import epsilonserver.game.ServerGUI;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Singleton for managing and interfacing with the network subsystem.
 *
 * @author Magnus Mikalsen
 */
public class NetworkHandler {

    /**
     * Server port
     */
    public static final int SERVER_PORT = 6001;

    /**
     * Client port
     */
    public static final int CLIENT_PORT = 6002;

    /**
     * Size of packet buffer
     */
    public static final int BUFFER_SIZE = 1000;

    private PacketParser parser = null;
    private ListenerThread listener = null;
    private SenderThread sender = null;
    private ConnectionInitialiser connectionInit = null;

    // Incoming packet queue
    private BlockingQueue<DatagramPacket> incomingPacketQueue;

    // Outgoing packet queue
    private BlockingQueue<DatagramPacket> outgoingPacketQueue;

    // UDP socket
    private DatagramSocket socket;

    // TCP socket
    private ServerSocket connectionSocket;

    private EntityHandler eHandler;

    /**
     * Private constructor.
     * Initializes incoming and outgoing pakcet queues.
     */
    private NetworkHandler() {
        incomingPacketQueue = new LinkedBlockingQueue<DatagramPacket>();
        outgoingPacketQueue = new LinkedBlockingQueue<DatagramPacket>();
        eHandler = EntityHandler.getInstance();
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
     * Get instance of NetworkHandler.
     *
     * @return Instance of NetworkHandler
     */
    public static NetworkHandler getInstance() {
        return NetworkHandlerHolder.INSTANCE;
    }

    /**
     * Start servers network subsystem. this includes creating a socket,
     * and starting network threads.
     */
    public void startServer() {

        try {
            // get local ip to bind socket to
            InetAddress bindIP = getFirstNonLoopbackAddress(true, false);

            // Create a new UDP socket
            socket = new DatagramSocket(null);
            socket.setReuseAddress(true);
            socket.bind(new InetSocketAddress(bindIP, SERVER_PORT));

            // Create a new TCP socket
            connectionSocket = new ServerSocket(SERVER_PORT);
            
            ServerGUI.getInstance().setSystemMessage("Socket created on interface " + bindIP);
        }
        catch (SocketException se) {
            ServerGUI.getInstance().setErrorMessage("Could not create UDP socket");
        }
        catch (IOException e) {
            ServerGUI.getInstance().setErrorMessage("Could not create TCP socket");
        }

        connectionInit = new ConnectionInitialiser(connectionSocket, eHandler);
        listener = new ListenerThread(socket, incomingPacketQueue);
        parser = new PacketParser(incomingPacketQueue, eHandler);
        sender = new SenderThread(socket, outgoingPacketQueue, eHandler);

        // start network threads
        new Thread(connectionInit, "ConnectionThread").start();
        new Thread(listener, "ListenerThread").start();
        new Thread(parser, "ParserThread").start();
        new Thread(sender, "SenderThread").start();
    }

    /**
     * Stop the server by stopping all network threads and closing socket.
     */
    public void stopServer() {

        // Stop threads
        connectionInit.stopConnection();
        listener.stopListener();
        parser.stopParser();
        sender.stopSender();

        incomingPacketQueue.clear();
        outgoingPacketQueue.clear();

        // Close sockets
        try {
            connectionSocket.close();
            socket.close();
        }
        catch (IOException e) {
            ServerGUI.getInstance().setErrorMessage("Could not close sockets");
        }

        // Remove all references
        socket = null;
        connectionSocket = null;
        connectionInit = null;
        listener = null;
        parser = null;
        sender = null;

        ServerGUI.getInstance().setSystemMessage("Server stopped");
    }

    /**
     * Send game data to all registered players.
     */
    public void updateClients() {
        sender.addToSendQueue();
    }

    /**
     * Iterate through all addresses on host and return first non-loopback address.
     * This is mainly for linux compatibility
     *
     * @param preferIpv4 Search for IPv4 address
     * @param preferIPv6 Search for IPv6 address
     * @return First non-loopback IP address found
     * @throws SocketException No IP address found
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
