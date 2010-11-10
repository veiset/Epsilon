package epsilon.net;

import epsilon.game.Game;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.BlockingQueue;

/**
 * SenderThread creates a thread for sending packets to the server.
 * Also includes functionality for creating datagram packets.
 *
 * @author Magnus Mikalsen
 */
public class SenderThread implements Runnable {

    private DatagramSocket socket;
    private InetAddress serverAddress;
    private Game game;
    private boolean isRunning = true;

    // Local player name
    private String clientName;

    // Outgoing packet queue
    private BlockingQueue<DatagramPacket> outgoingPacketQueue;

    /**
     * Constructor
     *
     * @param socket Datagram socket
     * @param outgoingPacketQueue Outgoing packet queue
     * @param serverAddress IP address of the server
     * @param name Local player name
     */
    public SenderThread(DatagramSocket socket, InetAddress serverAddress, 
            String name, BlockingQueue<DatagramPacket> outgoingPacketQueue) {
        this.socket = socket;
        this.serverAddress = serverAddress;
        this.clientName = name;
        game = Game.get();
        this.outgoingPacketQueue = outgoingPacketQueue;
    }

    /**
     * Thread that gets a packet from the outgoing packet queue and sends it.
     */
    public void run() {
        while (isRunning) {

            try {
                // Get packet from outgoing packet queue
                DatagramPacket packet = outgoingPacketQueue.take();

                // Send packet
                socket.send(packet);
            }
            catch (IOException ioe) {
                System.out.println("Problem accessing socket in sender thread");
            }
            catch (InterruptedException ie) {
                System.out.println("Could not get packet from outgoing packet queue");
            }

        }

    }

    /**
     * Add packets to ougoing packet queue
     */
    public void addToSendQueue() {
        byte[] buf = new byte[NetworkHandler.BUFFER_SIZE];

        // Get local players last actions
        double[] posArray = game.getPlayerState();

        // Create state message
        String playerStateString = clientName + " " + posArray[0] + " " + posArray[1];

        String sendString = "";

        try {
            // Create a hash of the game state message using SHA algorithm
            MessageDigest hash = MessageDigest.getInstance("SHA");
            byte[] hashSum = hash.digest(playerStateString.getBytes());

            // Create a hexadecimal representation of the hash
            StringBuilder hexString = new StringBuilder();
            for (int i = 0; i < hashSum.length; i++) {
                hexString.append(Integer.toHexString(0xFF & hashSum[i]));
            }

            // Add hash to end of game state String
            String hashString = hexString.toString();
            sendString = playerStateString + " " + hashString;

        }
        catch (NoSuchAlgorithmException e) {
            System.out.println("Could not find hashing algorithm in sender thread");
        }

        // Convert final game state message to bytes
        buf = sendString.getBytes();

        // Create datagram packet
        DatagramPacket outgoingPacket =
                new DatagramPacket(buf, buf.length, serverAddress, NetworkHandler.SERVER_PORT);

        try {
            // Add packet to outgoing packet queue
            outgoingPacketQueue.put(outgoingPacket);
        }
        catch (InterruptedException e) {
            System.out.println("Could not add packet to outgoing packet queue");
        }
        
    }

    /**
     * Stop sender thread
     */
    public void stopSender() {
       isRunning = false;
    }

}
