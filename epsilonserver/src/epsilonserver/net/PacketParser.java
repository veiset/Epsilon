package epsilonserver.net;

import epsilonserver.entity.EntityHandler;
import java.net.DatagramPacket;
import java.net.InetAddress;
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
                String packetString = new String(packet.getData(), 0, packet.getLength());

                InetAddress ip = packet.getAddress();

                String[] s = packetString.split(" ");

                String[] posArray = new String[2];
                posArray[0] = s[1];
                posArray[1] = s[2];

                eHandler.createIfAbsent(ip, s[0], posArray);

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
