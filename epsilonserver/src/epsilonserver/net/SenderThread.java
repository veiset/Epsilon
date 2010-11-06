package epsilonserver.net;

import epsilonserver.entity.EntityHandler;
import epsilonserver.game.ServerGUI;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.BlockingQueue;

/**
 * SenderThread creates a thread that gets the registered players position
 * and sends it to the registered clients.
 *
 * @author mm
 */
public class SenderThread implements Runnable {

    private DatagramSocket socket;
    private EntityHandler eHandler;
    private boolean isRunning = true;

    private BlockingQueue<DatagramPacket> outgoingPacketQueue;

    /**
     * Constructor
     *
     * @param socket
     */
    public SenderThread(DatagramSocket socket, BlockingQueue<DatagramPacket> outgoingPacketQueue) {
        this.socket = socket;
        this.outgoingPacketQueue = outgoingPacketQueue;
        eHandler = EntityHandler.getInstance();
    }

    /**
     * Thread that gets a packet from the outgoing packet queue and sends it 
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

        String[] nameArray = eHandler.getNameArray();

        for (int i = 0; i < nameArray.length; i++) {

            String sendString = "";
            String gs = "";

            gs = eHandler.getGameStateString(nameArray[i]);
            buf = gs.getBytes();

            try {
                MessageDigest hash = MessageDigest.getInstance("SHA");
                byte[] hashsum = hash.digest(buf);
                String hashString = hashsum.toString();
                sendString = gs + " " + hashString;

            }
            catch (NoSuchAlgorithmException e) {
                System.out.println("Could not hash outgoing message");
            }

            buf = sendString.getBytes();

            if(!sendString.equals("")) {
                System.out.println(sendString);
            }

            InetAddress ip = eHandler.getAddressByName(nameArray[i]);

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
