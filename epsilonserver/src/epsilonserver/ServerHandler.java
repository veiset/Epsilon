
package epsilonserver;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 *
 * Server handler that handles received object state data from clients
 * 
 * Right now it will only echo back data received from a client
 *
 * @author mm
 */
public class ServerHandler implements Runnable {


    private DatagramSocket socket = null;
    private boolean running = true;
//    private ArrayList<InetAddress> clientList = new ArrayList<InetAddress>();


    /**
     *
     * Constructor
     * Open a UDP socket with a specified port 
     *
     * @param port
     * @throws SocketException
     */
    public ServerHandler(int port) throws SocketException {
        socket = new DatagramSocket(port);

    }


    /**
     *
     * Start a thread that listens for incoming UDP traffic. When data is
     * received it is sendt back to the sender.
     *
     */
    public void run() {

        while (running) {

            byte[] buf = new byte[256];

            try {
                DatagramPacket incoming = new DatagramPacket(buf, buf.length);
                socket.receive(incoming);
                System.out.println("reveived packet");

                InetAddress clientaddress = incoming.getAddress();
                int clientport = incoming.getPort();

//              if (!clientList.contains(address)) {
//                  clientList.add(address);
//              }
//
//              for (int i=0; i<clientList.size(); i++) {
//                    InetAddress a = clientList.get(i);
//                    DatagramPacket outgoing = new DatagramPacket(buf, buf.length, a, 6001);
//                    socket.send(outgoing);
//                    System.out.println("packet sendt");
//              }

                DatagramPacket outgoing = new DatagramPacket(buf, buf.length, clientaddress, clientport);
                socket.send(outgoing);
                System.out.println("packet sendt");
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
 
        }
        socket.close();
    }


    /**
     *
     * Stop listening for incoming UDP traffic
     * 
     */
    public void stopServer() {
        running = false;
    }


}
