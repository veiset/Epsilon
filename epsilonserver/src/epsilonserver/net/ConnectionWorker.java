package epsilonserver.net;

import epsilonserver.entity.EntityHandler;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 *
 * @author Magnus Mikalsen
 */
public class ConnectionWorker implements Runnable {

    private Socket clientSocket = null;
    private EntityHandler eHandler;
    private BufferedReader in;
    private PrintWriter out;

    public ConnectionWorker(Socket clientSocket, EntityHandler eHandler) {
        this.clientSocket = clientSocket;
        this.eHandler = eHandler;
    }

    public void run() {

        try {
            InetAddress ip = clientSocket.getInetAddress();

            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);

            String input = in.readLine();

            if (input != null && !input.equals("")) {
                boolean playerAdded = eHandler.createIfAbsent(input, ip);

               if (playerAdded) {
                    out.println("OK");
                    System.out.println("Player added");
               }
               else {
                    out.println("ERROR");
                    System.out.println("Player already exists");
               }
            }
            else {
                System.out.println("Problem with input from client in connectionworker");
            }
            in.close();
            out.close();
            clientSocket.close();
            
        }
        catch (IOException e) {
            System.out.println("Could not read from connection socket in connectionworker");
        }
    }

}
