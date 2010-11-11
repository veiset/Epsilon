package epsilon.net;

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
     * Constructor
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
     * received it is added to the packet queue.
     */
    public void run() {

        byte[] buf = new byte[NetworkHandler.BUFFER_SIZE];
        DatagramPacket incomingPacket;

        while (isRunning) {
            try {
                incomingPacket = new DatagramPacket(buf, buf.length);
                socket.receive(incomingPacket);
                incomingPacketQueue.put(incomingPacket);
            }
            catch (IOException e) {
                // Could not access socket
                // Do nothing
            }
            catch (InterruptedException ie) {
                // Queue interrupted
                // Do nothing
            }
        }
        socket.close();
    }

    /**
     * Stop listening for incoming packets
     */
    public void stopListener() {
        isRunning = false;
    }

}
