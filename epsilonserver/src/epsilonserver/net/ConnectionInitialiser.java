package epsilonserver.net;

import epsilonserver.entity.EntityHandler;
import epsilonserver.game.ServerGUI;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Magnus Mikalsen
 */
public class ConnectionInitialiser implements Runnable {

    private boolean listening = true;
    private ServerSocket connectionSocket = null;
    private EntityHandler eHandler;

    public ConnectionInitialiser(ServerSocket connectionSocket) {
        this.connectionSocket = connectionSocket;
        eHandler = EntityHandler.getInstance();
    }

    public void run() {

        ServerGUI.getInstance().setSystemMessage("Connection initialiser started");

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
