package io.github.expansionteam.battleships.logic.client;

import io.github.expansionteam.battleships.MainLauncher;
import org.apache.log4j.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.nio.channels.SocketChannel;

public class SocketClient {

    private final static Logger log = Logger.getLogger(MainLauncher.class.getSimpleName());

    private final SocketChannel socketChannel;

    public SocketClient(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

    public String talkWithServer(String str) {
        String message = "";

        try {
            DataInputStream dataInputStream = new DataInputStream(socketChannel.socket().getInputStream());
            DataOutputStream dataOutputStream = new DataOutputStream(socketChannel.socket().getOutputStream());

            dataOutputStream.writeUTF(str);
            dataOutputStream.flush();

            message = dataInputStream.readUTF();
            log.debug("[SERVER] Message arrived: " + message);

            // TODO: change ending game - send a message from the server, when one disconnected
        } catch (EOFException e) {    // ending game
            System.out.println("Enemy has lost the connection...");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return message;
    }

}