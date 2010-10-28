package epsilonclient;

import java.net.DatagramPacket;
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
    private TestPanel tp;
    
    /**
     * Constructor
     *
     * @param map
     * @param packetQueue
     * @param tp
     */
    public PacketParser(Map map, BlockingQueue<DatagramPacket> packetQueue, TestPanel tp) {
        this.map = map;
        this.packetQueue = packetQueue;
        this.tp = tp;
    }

    /**
     * Parsing thread.
     * Parse datagrampacket data content by converting it to a string
     */
    public void run() {
        while (isRunning) {
            try {
                DatagramPacket packet = packetQueue.take();
                String packetString = new String(packet.getData(), 0, packet.getLength());

                System.out.println(packetString);

                PlayerEntity p = null;
                StringTokenizer part = new StringTokenizer(packetString);

                while (part.hasMoreTokens()) {
                    String nextString = part.nextToken();

                    if (nextString.equals("x")) {
                        p.setPosX(Integer.parseInt(part.nextToken()));
                    }
                    else if (nextString.equals("y")) {
                        p.setPosY(Integer.parseInt(part.nextToken()));
                    }
                    else {
                        p = new PlayerEntity(nextString);
                        map.addPlayer(nextString, p);
                    }
                }

                tp.repaint();
            }
            catch (InterruptedException ie) {
                System.out.println("Interrupt from queue");
            }
        }
    }

    /**
     * Stop packetparsing thread
     */
    public void stopParsing() {
        isRunning = false;
    }
    
}
