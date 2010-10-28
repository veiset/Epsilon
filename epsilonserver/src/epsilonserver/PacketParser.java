package epsilonserver;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.StringTokenizer;
import java.util.concurrent.BlockingQueue;

/**
 * The parsing class starts a thread that takes datagrampackets from
 * a queue and parses its data content
 *
 * @author mm
 */
public class PacketParser implements Runnable {

    private boolean isRunning = true;
    private Map map;
    private BlockingQueue<DatagramPacket> packetQueue;

    /**
     * Constructor
     *
     * @param map
     * @param packetQueue
     */
    public PacketParser(Map map, BlockingQueue<DatagramPacket> packetQueue) {
        this.map = map;
        this.packetQueue = packetQueue;
    }

    /**
     * Parsing thread.
     * Parse datagrampacket data content by converting it to a string.
     */
    public void run() {
        while (isRunning) {
            try {
                DatagramPacket packet = packetQueue.take();
                String packetString = new String(packet.getData(), 0, packet.getLength());

                InetAddress address = packet.getAddress();
                int clientPort = packet.getPort();

                String clientName = "";
                int posX = 0;
                int posY = 0;

                StringTokenizer part = new StringTokenizer(packetString);

                while (part.hasMoreTokens()) {
                    String nextString = part.nextToken();

                    if (nextString.equals("x")) {
                        posX = Integer.parseInt(part.nextToken());
                    }
                    else if (nextString.equals("y")) {
                        posY = Integer.parseInt(part.nextToken());
                    }
                    else {
                        clientName = nextString;
                    }
                }
                PlayerEntity p = new PlayerEntity(clientName, address, clientPort, posX, posY);
                map.addPlayer(clientName, p);
            }
            catch (InterruptedException ie) {
                System.out.println("Interrupt from queue");
            }
        }
    }
      
}
