
package epsilonclient;

import java.io.IOException;

/**
 *
 * @author mm
 */
public class Network {

    private int port = 6001;
    private String host;

    public Network() {

    }

    public void connect(final String host) throws IOException {
        this.host = host;

        System.out.println("connecting to... " + host);
    }


}
