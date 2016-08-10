package io.github.expansionteam.battleships;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import static org.apache.log4j.Logger.*;
import org.apache.log4j.Logger;

public class Server {
    private static final int port = 1234;
    private ServerSocketChannel serverSocket;
    private static int n = 0;   // simple pair counter

    private final static Logger log = getLogger(Server.class);

    private Server() {
        try {
            serverSocket = ServerSocketChannel.open();
            serverSocket.bind(new InetSocketAddress(port));
            serverSocket.configureBlocking(true);
        }
        catch (IOException e) {
            log.error("FAILED", e);
        }
    }

    public static void main(String[] args) {
        Server server = new Server();

        while (true) {
            try {
                ConnectionThread toRun = new ConnectionThread(server.acceptConnections(), server.acceptConnections(), ++n);
                log.info("Game " + n + " started...");
                new Thread(toRun).start();
            }
            catch (IOException e) {
                log.error("FAILED", e);
            }
        }
    }

    // accept connections (new clients)
    private SocketChannel acceptConnections() throws IOException {
        log.info("Waiting for player...");
        return serverSocket.accept();
    }
}
