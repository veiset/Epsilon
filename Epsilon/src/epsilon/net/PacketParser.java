package epsilon.net;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.StringTokenizer;
import java.util.concurrent.BlockingQueue;

/**
 * PacketParser class creates a parsing thread that takes
 * incoming packets from the packet queue and parses its data.
 * 
 * @author mm
 */
public class PacketParser implements Runnable {

    private BlockingQueue<DatagramPacket> packetQueue;
    private boolean isRunning = true;    

    /**
     * Constructor
     * @param packetQueue
     */
    public PacketParser(BlockingQueue<DatagramPacket> packetQueue) {
        this.packetQueue = packetQueue;
    }

    /**
     * TODO: Parsing logic not complete yet
     * Parse incoming packets from the server
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
                //NetworkEntity p = new NetworkEntity(clientName, address, clientPort, posX, posY);
                //eHandler.addPlayer(clientName, p);
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
