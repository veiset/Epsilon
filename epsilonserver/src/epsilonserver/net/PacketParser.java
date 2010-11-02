package epsilonserver.net;

import epsilonserver.entity.EntityHandler;
import epsilonserver.entity.NetworkEntity;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.Map;
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

    private Map<InetAddress, String> entityList;

    /**
     * Constructor
     * @param packetQueue
     */
    public PacketParser(BlockingQueue<DatagramPacket> packetQueue, Map<InetAddress, String> entityList) {
        this.packetQueue = packetQueue;
        eHandler = EntityHandler.getInstance();
        this.entityList = entityList;
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

//                StringTokenizer part = new StringTokenizer(packetString);

//                String name = part.nextToken();
//                String posX = part.nextToken();
//                String posY = part.nextToken();
//
//                String[] posArray = new String[2];
//                posArray[0] = posX;
//                posArray[1] = posY;

//                if (!eHandler.hasPlayer(ip)) {
//                    eHandler.addPlayer(name, ip, posArray);
//                }
//                else {
//                    eHandler.setPlayerCoordinates(ip, posArray);
//                }

                entityList.put(ip, packetString);

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
