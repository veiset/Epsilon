package epsilonserver.net;

import epsilonserver.entity.EntityHandler;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Magnus Mikalsen
 */
public class ConnectionInitializer implements Runnable {

    private boolean listening = true;
    private ServerSocket connectionSocket = null;
    private EntityHandler eHandler;

    public ConnectionInitializer(ServerSocket connectionSocket) {
        this.connectionSocket = connectionSocket;
        eHandler = EntityHandler.getInstance();
    }

    public void run() {

        while (listening) {

            Socket clientSocket = null;

            try {
                clientSocket = connectionSocket.accept();
            }
            catch (IOException ex) {
                System.out.println("Error reading from connection socket in ConnectionInitializer");
            }

            new Thread(new ConnectionWorker(clientSocket, eHandler)).start();

        }
        
        try {
            connectionSocket.close();
        }
        catch (IOException ex) {
            System.out.println("Could not close connectionSocket in ConnectionInitializer");
        }

    }

    public void stopConnection() {
        listening = false;
    }

}
