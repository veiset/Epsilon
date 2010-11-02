package epsilonserver.net;

import epsilonserver.entity.EntityHandler;
import epsilonserver.entity.NetworkEntity;
import epsilonserver.game.ServerGUI;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;

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

    private Map<InetAddress, String> entityList;

    /**
     * Constructor
     * @param socket
     */
    public SenderThread(DatagramSocket socket, BlockingQueue<DatagramPacket> outgoingPacketQueue, Map<InetAddress, String> entityList) {
        this.socket = socket;
        this.outgoingPacketQueue = outgoingPacketQueue;
        eHandler = EntityHandler.getInstance();
        this.entityList = entityList;
    }

    /**
     * Thread for creating a packet with player positions and
     * sending the information to all registered clients.
     */
    public void run() {

        ServerGUI.getInstance().setLogMessage("Sender thread started");

        while (isRunning) {

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

    /**
     * Add packets to outgoing packet queue
     */
    public synchronized void addToSendQueue() {
        byte[] buf = new byte[NetworkHandler.BUFFER_SIZE];

        //HashMap<InetAddress, String> pl = eHandler.getGameStateMap();

        InetAddress[] adrArray = new InetAddress[entityList.size()];
        adrArray = (InetAddress[]) entityList.keySet().toArray(adrArray);

        for (int i = 0; i < adrArray.length; i++) {
            String gameStateString = "";

            Iterator itr = entityList.keySet().iterator();
            while (itr.hasNext()) {
                InetAddress a = (InetAddress) itr.next();

                if (!a.equals(adrArray[i])) {
                    gameStateString += entityList.get(a);
                }

            }

            if(!gameStateString.equals("")) {
                System.out.println(gameStateString);
            }

            buf = gameStateString.getBytes();

            InetAddress ip = (InetAddress) adrArray[i];


            DatagramPacket outgoingPacket =
                    new DatagramPacket(buf, buf.length, ip, NetworkHandler.CLIENT_PORT);

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
