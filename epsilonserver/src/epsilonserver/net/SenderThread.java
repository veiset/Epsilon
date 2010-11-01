package epsilonserver.net;

import epsilonserver.entity.EntityHandler;
import epsilonserver.entity.NetworkEntity;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;

/**
 * SenderThread creates a thread that gets the registered players position
 * and sends it to the registered clients.
 * @author mm
 */
public class SenderThread implements Runnable {

    private DatagramSocket socket;
    private EntityHandler eHandler;
    private boolean isRunning = true;

    private BlockingQueue<DatagramPacket> outgoingPacketQueue;

    /**
     * Constructor
     * @param socket
     */
    public SenderThread(DatagramSocket socket, BlockingQueue<DatagramPacket> outgoingPacketQueue) {
        this.socket = socket;
        this.outgoingPacketQueue = outgoingPacketQueue;
        eHandler = EntityHandler.getInstance();
    }

    /**
     * Thread for creating a packet with player positions and
     * sending the information to all registered clients.
     */
    public void run() {
        System.out.println("Sender thread started");
        while (isRunning) {

            HashMap<InetAddress, NetworkEntity> playerList = eHandler.getPlayerList();

            for (InetAddress ip : playerList.keySet()) {
                try {
                    DatagramPacket packet = outgoingPacketQueue.take();
                    socket.send(packet);
                }
                catch (InterruptedException e) {
                    System.out.println("Could not get packet from outgoing packet queue");
                }
                catch (IOException e) {
                    System.out.println("Problem accessing socket");
                }
            }
            
        }
    }

    /**
     * Add packets to outgoing packet queue
     */
    public void addToSendQueue() {
        byte[] buf = new byte[NetworkHandler.BUFFER_SIZE];

        HashMap<String, NetworkEntity> playerList = eHandler.getPlayerList();
        Collection c = playerList.values();
        Iterator it = c.iterator();

        while (it.hasNext()) {
            NetworkEntity p = (NetworkEntity) it.next();

            String gameStateString = eHandler.getGameStateString(p.getAddress());
            buf = gameStateString.getBytes();

            if (!gameStateString.isEmpty()) {
                System.out.println("\n" + "Packet content: " + gameStateString + "\n");
            }

            DatagramPacket outgoingPacket =
                    new DatagramPacket(buf, buf.length, p.getAddress(), NetworkHandler.CLIENT_PORT);

            try {
                outgoingPacketQueue.put(outgoingPacket);
            }
            catch (InterruptedException e) {
                System.out.println("Could not add packet to outgoing packet queue");
            }
        }
    }

    /**
     * Stop running sender thread
     */
    public void stopSender() {
        isRunning = false;
    }


}
