package epsilonserver.net;

import epsilonserver.entity.EntityHandler;
import epsilonserver.game.ServerGUI;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Thread for listning after new TCP connection. If a new connection is established
 * then a new ConnectionWorker thread is started.
 *
 * @author Magnus Mikalsen
 */
public class ConnectionInitialiser implements Runnable {

    private boolean isRunning;
    private ServerSocket connectionSocket = null;
    private EntityHandler eHandler;

    /**
     * Constructor
     *
     * @param connectionSocket Server socket for listning after incomming connections
     * @param eHandler Reference to EntityHandler
     */
    public ConnectionInitialiser(ServerSocket connectionSocket, EntityHandler eHandler) {
        this.connectionSocket = connectionSocket;
        this.eHandler = eHandler;
    }

    /**
     * Incoming connection listening thread
     */
    public void run() {

        isRunning = true;

        ServerGUI.getInstance().setSystemMessage("Connection initialiser started");

        while (isRunning) {

            Socket clientSocket = null;

            try {
                clientSocket = connectionSocket.accept();
            }
            catch (IOException ex) {
                // This exception is thrown when the socket is closed
                //ServerGUI.getInstance().setErrorMessage("Problem receiving incoming connections");
            }

            // Create a new worker thread to communicate with client
            if (clientSocket != null) {
                new Thread(new ConnectionWorker(clientSocket, eHandler)).start();
            }

        }

        try {
            connectionSocket.close();
        }
        catch (IOException ex) {
             ServerGUI.getInstance().setErrorMessage("Problem closing connection listener");
        }

    }

    /**
     * Stop listning after new connections
     */
    public void stopConnection() {
        isRunning = false;
    }
    
}
