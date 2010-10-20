
package epsilonserver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

/**
 * Server handler that handles received object state data from clients
 * 
 * Right now it will only echo back data received from a client
 *
 * @author mm
 */
public class ServerHandler implements Runnable {

    private static int BUFFER_SIZE = 256;

    private DatagramSocket socket = null;
    private boolean isRunning = true;
    private GameState gameState;


    /**
     * Constructor
     * Open a UDP socket with a specified port 
     *
     * @param port
     * @throws SocketException
     */
    public ServerHandler(int port, GameState gameState) {
        this.gameState = gameState;
        try {
            socket = new DatagramSocket(port, InetAddress.getLocalHost());
        }
        catch (SocketException se) {
            System.out.println("Could not create socket");
        }
        catch (UnknownHostException ue) {
            System.out.println("Host address not found");
        }
    }


    /**
     * Start a thread that listens for incoming UDP traffic. When data is
     * received then start a thread to parse and handle the data.
     *
     */
    public void run() {
        
        // buffers
        byte[] buf = new byte[BUFFER_SIZE];
        DatagramPacket incomingPacket;

        while (isRunning) {

            try {
                incomingPacket = new DatagramPacket(buf, buf.length);
                socket.receive(incomingPacket);

                // do stuff with the packet
                ByteBuffer bb = ByteBuffer.wrap(buf);
                PacketParser pp = new PacketParser(gameState, bb);
                new Thread(pp).start();
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
 
        }
        socket.close();
    }


    /**
     * Send gamestate data to all registered players
     *
     * @param playerList
     */
    public void sendGameState() {
        byte[] buf = new byte[BUFFER_SIZE];
        DatagramPacket outgoingPacket;

        // insert data into packet
        buf = gameState.toString().getBytes();

        ArrayList<Player> pl = gameState.getPlayerList();
        for (Player p : pl) {
            outgoingPacket = new DatagramPacket(buf, buf.length, p.getAddress(), p.getPort());

            try {
                socket.send(outgoingPacket);
            }
            catch (IOException e) {
                System.out.println("Could not send packet to client: " + p.getAddress()
                        + " at port: "+ p.getPort());
            }
        }
    }


    /**
     * Stop listening for incoming UDP traffic
     * 
     */
    public void stopServer() {
        isRunning = false;
    }


}
