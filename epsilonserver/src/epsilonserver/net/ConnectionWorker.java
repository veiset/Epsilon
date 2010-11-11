package epsilonserver.net;

import epsilonserver.entity.EntityHandler;
import epsilonserver.game.ServerGUI;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * ConnectionWorker gets player name from client and creates a new network entity
 * If the name is already taken a error message is sent back to the client.
 * This thread will terminate after creating the new player.
 *
 * @author Magnus Mikalsen
 */
public class ConnectionWorker implements Runnable {

    private Socket clientSocket = null;
    private EntityHandler eHandler;
    private BufferedReader in;
    private PrintWriter out;

    /**
     * Constructor
     *
     * @param clientSocket Socket connected to client
     * @param eHandler Reference to EntityHandler
     */
    public ConnectionWorker(Socket clientSocket, EntityHandler eHandler) {
        this.clientSocket = clientSocket;
        this.eHandler = eHandler;
    }

    /**
     * Thread for creating a new player. If a player by the given name
     * does not exist we try to create one and send OK message back to client.
     * If the name is taken a ERROR message is sent.
     */
    public void run() {

        try {
            InetAddress ip = clientSocket.getInetAddress();

            // input and output streams
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            // Input from client, blocks untill we get a message
            String input = in.readLine();

            // Check if message is empty
            if (input != null && !input.equals("")) {

                // If there is a message we try to make a new network entity
                boolean playerAdded = eHandler.createIfAbsent(input, ip);

               if (playerAdded) {
                   //String colorID = eHandler.getColorByName(input);
                   // If player is added then we send a Ok message back to the client
                    out.println("OK");
                    //out.println(colorID);
               }
               else {
                    // If name is already taken we send back a ERROR message
                    out.println("ERROR");
               }
            }
            // Close streams and socket
            in.close();
            out.close();
            clientSocket.close();
            
        }
        catch (IOException e) {
            ServerGUI.getInstance().setErrorMessage("Could not get connection information from player");
        }
    }

}
