package io.github.expansionteam.battleships;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;


// I want to replace some code with SELECTORS, but I made stupid mistake yesterday
// so I will have to refactor code later, now let's use sockets
public class Server {
    private final int port = 1234;
    private ServerSocketChannel channel;
    private ConnectionThread toRun = null;

    private static int n = 0;

    // one thing TODO: examine if server is running....
    public static void main(String[] args) {
        Server server = new Server();

        try {
            server.prepareServer();
        }
        catch (IOException e) {
            System.out.println("IOException ...");
            e.printStackTrace();
        }

        while (true) {
            try {
                server.toRun = new ConnectionThread(server.acceptConnections(), server.acceptConnections(), ++n);
                new Thread(server.toRun).start();
            }
            catch (IOException e) {
                System.out.println("IOException...");
                e.printStackTrace();
            }
        }
    }

    // prepare channels
    private void prepareServer() throws IOException {
        channel = ServerSocketChannel.open();
        channel.bind(new InetSocketAddress(port));
        channel.configureBlocking(true);
    }

    // accept connections (new clients)
    private SocketChannel acceptConnections() throws IOException {
        System.out.println("Waiting for player ");
        return channel.accept();
    }
}
