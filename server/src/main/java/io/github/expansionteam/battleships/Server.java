package io.github.expansionteam.battleships;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Scanner;

import static org.apache.log4j.Logger.*;

import org.apache.log4j.Logger;

public class Server {
    private static final int port = 1234;
    private ServerSocketChannel serverSocket;
    private static int n = 0;   // simple pair counter

    private final static Logger log = getLogger(Server.class);

    Server() {
        try {
            serverSocket = ServerSocketChannel.open();
            serverSocket.bind(new InetSocketAddress(port));
            serverSocket.configureBlocking(true);
        } catch (IOException e) {
            log.error("FAILED", e);
        }
    }

    public void runServer() {
         while (true) {
            try {
                ConnectionThread toRun = new ConnectionThread(acceptConnections(), acceptConnections(), ++n);
                log.info("Game " + n + " started...");
                new Thread(toRun).start();
            } catch (IOException e) {
                log.error("FAILED", e);
            }
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.launchDeamonToStopServer();
        server.runServer();
    }

    // accept connections (new clients)
    private SocketChannel acceptConnections() throws IOException {
        log.info("Waiting for player...");
        return serverSocket.accept();
    }

    // write stop or STOP to terminate the server
    private void launchDeamonToStopServer() {
        new Thread(() -> {
            try (Scanner sc = new Scanner(System.in)) {
                while (sc.hasNextLine()) {
                    String input = sc.nextLine();
                    if (input.replaceAll("[\\r\\n]+", "").equalsIgnoreCase("stop")) {
                        System.exit(-1);
                    }
                }
            }
        }).start();
    }
}