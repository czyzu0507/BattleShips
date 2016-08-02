package io.github.expansionteam.battleships.logic.client;

import com.google.inject.Provider;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.SocketChannel;

public class SocketClientProvider implements Provider<SocketClient> {

    private static final String IP = "127.0.0.1";
    private static final int PORT = 1234;

    @Override
    public SocketClient get() {
        try {
            SocketChannel socketChannel;

            SocketAddress address = new InetSocketAddress(IP, PORT);
            socketChannel = SocketChannel.open(address);
            socketChannel.configureBlocking(true);

            return new SocketClient(socketChannel);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // TODO: We need a better solution here.
        return null;
    }

}
