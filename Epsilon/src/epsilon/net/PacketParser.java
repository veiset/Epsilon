package epsilon.net;

import epsilon.game.Game;
import java.net.DatagramPacket;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.concurrent.BlockingQueue;

/**
 * PacketParser class creates a parsing thread that takes
 * incoming packets from the packet queue and parses its data.
 * @author mm
 */
public class PacketParser implements Runnable {

    private BlockingQueue<DatagramPacket> packetQueue;
    private HashMap<String, double[]> playerPosList;
    private NetworkHandler netHandler;

    private boolean isRunning = true;

    private String name;

    /**
     * Constructor
     * @param packetQueue
     */
    public PacketParser(BlockingQueue<DatagramPacket> packetQueue,
            HashMap<String, double[]> playerPosList, NetworkHandler netHandler, String name) {
        this.packetQueue = packetQueue;
        this.playerPosList = playerPosList;
        this.netHandler = netHandler;
        this.name = name;
    }

    /**
     * Parse incoming packets from the server
     */
    public void run() {
        while (isRunning) {
            try {
                DatagramPacket packet = packetQueue.take();
                String packetString = new String(packet.getData(), 0, packet.getLength());

                System.out.println(packetString);

                StringTokenizer part = new StringTokenizer(packetString);

                while (part.hasMoreTokens() && part.countTokens( )% 3 == 0) {
                    String name = part.nextToken();
                    String posX = part.nextToken();
                    String posY = part.nextToken();

                    //if (!name.equals(this.name)) {

                        double[] posArray = new double[2];

                        try {
                            posArray[0] = Double.valueOf(posX);
                            posArray[1] = Double.valueOf(posY);
                        }
                        catch (NumberFormatException e) {
                            System.out.println("Cant convert x or y coordinates to double");
                        }

                        if (!playerPosList.containsKey(name)) {
                            netHandler.addNewPlayer(name);
                        }

                        System.out.println(name);

                        playerPosList.put(name, posArray);

                    //}
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
