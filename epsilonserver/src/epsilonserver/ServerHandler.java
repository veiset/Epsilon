
package epsilonserver;

import java.net.DatagramPacket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author mm
 */
public class ServerHandler {

    private Map map;
    private PacketParser packetParser;
    private ServerThread serverThread;
    private BlockingQueue<DatagramPacket> packetQueue;


    public ServerHandler(Map map) {
        this.map = map;
        packetQueue = new LinkedBlockingQueue<DatagramPacket>();
        serverThread = new ServerThread(map, packetQueue);
        packetParser = new PacketParser(map, packetQueue);
    }


    public void startServer() {
        new Thread(serverThread).start();
        new Thread(packetParser).start();
    }


    public void sendGameState() {
        serverThread.sendGameState();
    }


    public void stopServer() {
        serverThread.stopServerThread();   
    }

}
