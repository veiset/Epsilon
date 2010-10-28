

package epsilonclient;

import java.net.DatagramPacket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author mm
 */
public class ClientHandler {

    private Map map;
    private PacketParser packetParser;
    private ClientThread clientThread;
    private BlockingQueue<DatagramPacket> packetQueue;

    
    public ClientHandler(Map map, String serverAddress, String clientName, TestPanel tp) {
        this.map = map;
        packetQueue = new LinkedBlockingQueue<DatagramPacket>();
        clientThread = new ClientThread(map, packetQueue, serverAddress, clientName);
        packetParser = new PacketParser(map, packetQueue, tp);
    }

    public void startClient() {
        new Thread(clientThread).start();
        new Thread(packetParser).start();
    }


    public void sendCoordinates(int posX, int posY) {
        clientThread.sendCoordinates(posX, posY);
    }


    public void stopClient() {
        clientThread.stopClientThread();
    }

}
