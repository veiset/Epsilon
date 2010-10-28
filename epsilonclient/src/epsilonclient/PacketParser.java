

package epsilonclient;

import java.net.DatagramPacket;
import java.util.StringTokenizer;
import java.util.concurrent.BlockingQueue;

/**
 *
 * @author mm
 */
public class PacketParser implements Runnable {

    private boolean isRunning = true;
    private Map map;
    private BlockingQueue<DatagramPacket> packetQueue;
    private TestPanel tp;
    

    public PacketParser(Map map, BlockingQueue<DatagramPacket> packetQueue, TestPanel tp) {
        this.map = map;
        this.packetQueue = packetQueue;
        this.tp = tp;
    }


    /**
     * TODO: better parsing of input and call to paint
     */
    public void run() {
        while (isRunning) {
            try {
                DatagramPacket packet = packetQueue.take();
                String packetString = new String(packet.getData(), 0, packet.getLength());

                System.out.println(packetString);

                PlayerEntity p = null;
                StringTokenizer part = new StringTokenizer(packetString);

                while (part.hasMoreTokens()) {
                    String nextString = part.nextToken();

                    if (nextString.equals("x")) {
                        p.setPosX(Integer.parseInt(part.nextToken()));
                    }
                    else if (nextString.equals("y")) {
                        p.setPosY(Integer.parseInt(part.nextToken()));
                    }
                    else {
                        p = new PlayerEntity(nextString);
                        map.addPlayer(nextString, p);
                    }

                }
                tp.repaint();
            }
            catch (InterruptedException ie) {
                System.out.println("Interrupt from queue");
            }
        }
    }


    public void stopParsing() {
        isRunning = false;
    }
    

}
