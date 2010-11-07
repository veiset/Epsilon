package epsilonserver.net;

import epsilonserver.game.ServerGUI;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.concurrent.BlockingQueue;

/**
 * ListenerThread creates a thread that listens for incoming packets.
 * When a packet is received it is added to a packet queue.
 *
 * @author Magnus Mikalsen
 */
public class ListenerThread implements Runnable {

    // Queue for incoming datagram packets
    private BlockingQueue<DatagramPacket> incomingPacketQueue;

    private DatagramSocket socket;
    private boolean isRunning = true;

    /**
     * Constrctor
     *
     * @param socket Datagram socket
     * @param packetQueue Incoming packet queue
     */
    public ListenerThread(DatagramSocket socket, BlockingQueue<DatagramPacket> incomingPacketQueue) {
        this.incomingPacketQueue = incomingPacketQueue;
        this.socket = socket;
    }

    /**
     * Thread that listens after incoming packets. If a packet is
     * received it is added to the incoming packet queue.
     */
    public void run() {
        ServerGUI.getInstance().setSystemMessage("Listener thread started");

        byte[] buf = new byte[NetworkHandler.BUFFER_SIZE];
        DatagramPacket incomingPacket;

        while (isRunning) {
            try {
                incomingPacket = new DatagramPacket(buf, buf.length);
                socket.receive(incomingPacket);
                incomingPacketQueue.put(incomingPacket);
            }
            catch (IOException e) {
                ServerGUI.getInstance().setErrorMessage("Problem reading from socket");
            }
            catch (InterruptedException ie) {
                ServerGUI.getInstance().setErrorMessage("Could not add incoming packet to packet queue");
            }
        }
        
    }

    /**
     * Stop listening for incoming packets
     */
    public void stopListener() {
        isRunning = false;
    }

}
