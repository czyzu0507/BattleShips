package io.github.expansionteam.battleships;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

// I want to replace some code with SELECTORS, but I made stupid mistake yesterday
// so I will have to refactor code later, now let's use sockets
public class Server {

    private final int port = 1234;
    private ServerSocketChannel serverSocket;
    private static int n = 0;   // simple pair counter

    Server() {
        try {
            serverSocket = ServerSocketChannel.open();
            serverSocket.bind(new InetSocketAddress(port));
            serverSocket.configureBlocking(true);
        }
        catch (IOException e) {
            /*...*/
            e.printStackTrace();
        }
    }

    // entry point
    public static void main(String[] args) {
        Server server = new Server();
        ConnectionThread toRun;

        while (true) {
            try {
                toRun = new ConnectionThread(server.acceptConnections(), server.acceptConnections(), ++n);
                System.out.println("Game " + n + " started...");
                new Thread(toRun).start();
            }
            catch (IOException e) {
                System.out.println("IOException...");
                e.printStackTrace();
            }
        }
    }

    // accept connections (new clients)
    private SocketChannel acceptConnections() throws IOException {
        System.out.println("Waiting for player ");
        return serverSocket.accept();
    }
}
