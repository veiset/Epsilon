package epsilonserver.net;

import epsilonserver.entity.EntityHandler;
import epsilonserver.entity.NetworkEntity;
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

                StringTokenizer part = new StringTokenizer(packetString);

                String name = part.nextToken();
                String posX = part.nextToken();
                String posY = part.nextToken();

                double[] posArray = new double[2];

                try {
                    posArray[0] = Double.parseDouble(posX);
                    posArray[1] = Double.parseDouble(posY);
                }
                catch (NumberFormatException e) {
                    System.out.println("Cant convert x or y coordinates to double");
                }

                NetworkEntity entity = eHandler.getPlayerByIP(ip);

                if (entity == null) {
                    entity = new NetworkEntity(name, ip, posArray);
                    eHandler.addPlayer(ip, entity);
                    System.out.println("Created new network entity");
                }
                else {
                    entity.setCoordinates(posArray);
                    System.out.println("Set new coordinates on a player");
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
