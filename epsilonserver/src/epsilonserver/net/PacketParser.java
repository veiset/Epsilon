package epsilonserver.net;

import epsilonserver.entity.EntityHandler;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.StringTokenizer;
import java.util.concurrent.BlockingQueue;

/**
 * PacketParser class creates a parsing thread that takes
 * incoming packets from the packet queue and parses its data.
 * @author mm
 */
public class PacketParser implements Runnable {

    private EntityHandler eHandler;
    private BlockingQueue<DatagramPacket> packetQueue;

    private boolean isRunning = true;

    /**
     * Constructor
     * @param packetQueue
     */
    public PacketParser(BlockingQueue<DatagramPacket> packetQueue) {
        this.packetQueue = packetQueue;
        eHandler = EntityHandler.getInstance();
    }

    /**
     * Parse incoming packets from clients
     */
    public void run() {
        while (isRunning) {
            try {
                DatagramPacket packet = packetQueue.take();

                System.out.println("Packet received");

                String packetString = new String(packet.getData(), 0, packet.getLength());

                InetAddress ip = packet.getAddress();

                String[] strArray = packetString.split(" ");

                if (strArray.length == 4) {

                    String[] posArray = new String[2];
                    posArray[0] = strArray[1];
                    posArray[1] = strArray[2];

                    String incomningHash = strArray[3];
                    System.out.println("Incoming hash: " + incomningHash);
                    String calculatedHash = "";

                    try {
                        String temp = strArray[0] + " " + strArray[1] + " " + strArray[2];
                        MessageDigest hash = MessageDigest.getInstance("SHA");
                        byte[] hashSum = hash.digest(temp.getBytes());

                        System.out.println(hashSum.length);

                        StringBuilder hexString = new StringBuilder();

                        for (int i = 0; i < hashSum.length; i++) {
                            hexString.append(Integer.toHexString(0xFF & hashSum[i]));
                        }
         
                        calculatedHash = hexString.toString();
                        System.out.println("Calculated hash: " + calculatedHash);

                    }
                    catch (NoSuchAlgorithmException e) {
                        System.out.println("Could not hash incoming message");
                    }


                    if (calculatedHash.equals(incomningHash)) {
                        eHandler.createIfAbsent(ip, strArray[0], posArray);
                        System.out.println("Incoming hashing complete");
                    }
                }
            }
            catch (InterruptedException ie) {
                System.out.println("Interrupt from queue");
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
