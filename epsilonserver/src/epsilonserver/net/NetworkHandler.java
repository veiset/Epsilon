package epsilonserver.net;

import epsilonserver.game.ServerGUI;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
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

    private Map<InetAddress, String> entityList;

    
    private DatagramSocket socket;

    /**
     * Private constructor
     */
    private NetworkHandler() {
        incomingPacketQueue = new LinkedBlockingQueue<DatagramPacket>();
        outgoingPacketQueue = new LinkedBlockingQueue<DatagramPacket>();

        entityList = Collections.synchronizedMap(new HashMap<InetAddress, String>());
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
            InetAddress bindIP = getFirstNonLoopbackAddress(true, false);
            socket = new DatagramSocket(SERVER_PORT, bindIP);
            ServerGUI.getInstance().setLogMessage("Socket created on interface " + InetAddress.getLocalHost());
        }
        catch (SocketException se) {
            System.out.println("Could not create socket");
        }
        catch (UnknownHostException ue) {
            System.out.println("Could not resolve address");
        }

        listener = new ListenerThread(socket, incomingPacketQueue);
        parser = new PacketParser(incomingPacketQueue, entityList);
        sender = new SenderThread(socket, outgoingPacketQueue, entityList);

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
        socket.close();
        ServerGUI.getInstance().setLogMessage("Server stopped");
    }

    /**
     * Send gamestate to players
     */
    public void updateClients() {
        sender.addToSendQueue();
    }

    /**
     * iterate through all addresses on host and return first non-loopback address
     *
     * @param preferIpv4
     * @param preferIPv6
     * @return addr
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
