package epsilonserver.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 *
 * @author Magnus Mikalsen
 */
public class ConnectionInitializer implements Runnable {

    private Socket tcpSocket = null;


    public ConnectionInitializer(Socket tcpSocket) {
        this.tcpSocket = tcpSocket;
    }

    public void run() {

        try {
            PrintWriter output = new PrintWriter(tcpSocket.getOutputStream(), true);
            BufferedReader input = new BufferedReader(new InputStreamReader(tcpSocket.getInputStream()));

            String clientInput = input.readLine();
        }
        catch (IOException e) {

        }
    }

}
