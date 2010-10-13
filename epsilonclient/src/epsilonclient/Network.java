
package epsilonclient;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

/**
 *
 * Network class for sending and receiving object state over the network
 * State information is sendt and received using UDP transport protocol
 *
 * @author mm
 */
public class Network implements Runnable {

    private boolean running = true;
    private DatagramSocket socket = null;
    private InetAddress serverAddress;
    private TestWindow tw = null;
    private int clientPort = 6002;
    private int serverPort = 6001;


    /**
     *
     * Constructor
     *
     */
    public Network(TestWindow tw, String host) throws SocketException, UnknownHostException {
        socket = new DatagramSocket(clientPort);
        this.serverAddress = InetAddress.getByName(host);
        this.tw = tw;
    }


    /**
     *
     * Start the listening socket in a new thread to make it non-blocking
     *
     */
    public void run() {
        byte[] buf;
        DatagramPacket incoming;

        while (running) {

            buf = new byte[256];

            try {
                incoming = new DatagramPacket(buf, buf.length);
                socket.receive(incoming);
                System.out.println("Received packet");

                // get the x and y coordinates from the bytes array
                ByteBuffer bb = ByteBuffer.wrap(buf);
                int xpos = bb.getInt(0);
                int ypos = bb.getInt(4);

                // send the received coordinates to the panel
                tw.coordinatesToPaint(xpos, ypos);
            }
            catch (IOException ioe) {
                System.out.println("packet could not be received");
            }
        }
        socket.close();

    }


    /**
     *
     * Send coordinates to the server
     *
     * @param xpos
     * @param ypos
     * @throws IOException
     */
    public void sendCoordinates(int xpos, int ypos) throws IOException{
        byte[] buf = new byte[256];

        // converting x and y coordinates to bytes
        ByteBuffer bb = ByteBuffer.allocate(256);
        bb.putInt(0, xpos);
        bb.putInt(4, ypos);
        buf = bb.array();

        DatagramPacket outgoing = new DatagramPacket(buf, buf.length, serverAddress, serverPort);
        socket.send(outgoing);
        System.out.println("Packet sendt");

    }


    /**
     * Stop the listening thread
     */
    public void stopClient() {
        running = false;
    }

}
