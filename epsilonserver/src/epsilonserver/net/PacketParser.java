package epsilonserver.net;

import epsilonserver.entity.EntityHandler;
import epsilonserver.game.ServerGUI;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.BlockingQueue;

/**
 * PacketParser class creates a parsing thread that takes
 * incoming packets from the packet queue and parses its data.
 *
 * @author Magnus Mikalsen
 */
public class PacketParser implements Runnable {

    // Incoming packet queue
    private BlockingQueue<DatagramPacket> incomingPacketQueue;

    private EntityHandler eHandler;
    private boolean isRunning = true;

    /**
     * Constructor
     *
     * @param incomingPacketQueue Queue for incoming packets
     */
    public PacketParser(BlockingQueue<DatagramPacket> incomingPacketQueue) {
        this.incomingPacketQueue = incomingPacketQueue;
        eHandler = EntityHandler.getInstance();
    }

    /**
     * Parse incoming packets from clients
     */
    public void run() {

        ServerGUI.getInstance().setSystemMessage("Parser thread started");

        while (isRunning) {
            try {
                // Get packet from incoming packet queue
                DatagramPacket packet = incomingPacketQueue.take();

                // Get message from packet
                String packetString = new String(packet.getData(), 0, packet.getLength());

                // Get clients IP address from packet
                InetAddress ip = packet.getAddress();

                // Split message into words
                String[] strArray = packetString.split(" ");

                // message should be 4 words
                if (strArray.length == 4) {

                    // Get X and Y coordinates from message
                    String[] posArray = new String[2];
                    posArray[0] = strArray[1];
                    posArray[1] = strArray[2];

                    // Get hash from incoming message
                    String incomningHash = strArray[3];
                    
                    String calculatedHash = "";

                    try {
                        // Calculate a hash of incoming message
                        String temp = strArray[0] + " " + strArray[1] + " " + strArray[2];
                        MessageDigest hash = MessageDigest.getInstance("SHA");
                        byte[] hashSum = hash.digest(temp.getBytes());

                        // Create a hexadecimal representation of the hash
                        StringBuilder hexString = new StringBuilder();
                        for (int i = 0; i < hashSum.length; i++) {
                            hexString.append(Integer.toHexString(0xFF & hashSum[i]));
                        }
         
                        calculatedHash = hexString.toString();
                    }
                    catch (NoSuchAlgorithmException e) {
                        ServerGUI.getInstance().setErrorMessage("Could not find hashing algorithm in parser");
                    }

                    // Check if hash is correct
                    if (calculatedHash.equals(incomningHash)) {
                        // Create or update player if hash is correct
                        eHandler.createIfAbsent(ip, strArray[0], posArray);
                    }
                }
            }
            catch (InterruptedException ie) {
                ServerGUI.getInstance().setErrorMessage("Could not get packet from incoming packet queue");
            }
        }
    }

    /**
     * Stop parser thread
     */
    public void stopParser() {
        isRunning = false;
    }

}
