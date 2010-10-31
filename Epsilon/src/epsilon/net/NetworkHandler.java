package epsilon.net;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
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

    private BlockingQueue<DatagramPacket> packetQueue;

    private DatagramSocket socket;

    /**
     * private constructor
     */
    private NetworkHandler() {
        packetQueue = new LinkedBlockingQueue<DatagramPacket>();
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
     * setup socket, networklistener and packetparser
     */
    public void connect(InetAddress serverAddress, String name) {

        try {
            socket = new DatagramSocket(SERVER_PORT, InetAddress.getLocalHost());
            System.out.println("Socket created on interface " + InetAddress.getLocalHost());
        }
        catch (SocketException se) {
            System.out.println("Could not create socket");
        }
        catch (UnknownHostException ue) {
            System.out.println("Host address not found");
        }

        listener = new ListenerThread(socket, packetQueue);
        parser = new PacketParser(packetQueue);
        sender = new SenderThread(socket, serverAddress, name);

        new Thread(listener).start();
        new Thread(parser).start();

    }

    /**
     * stop server threads and close socket
     */
    public void disconnect() {
        listener.stopListener();
        parser.stopParser();
        socket.close();
    }

    /**
     * Send player position to server
     */
    public void send() {
        new Thread(sender).start();
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


}
