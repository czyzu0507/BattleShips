package io.github.expansionteam.battleships;

import org.apache.log4j.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.channels.SocketChannel;

class PlayerThread extends Thread {
    private final SocketChannel socketChannel;
    private final static Logger log = Logger.getLogger(PlayerThread.class);
    private final JsonHandler jsonHandler = new JsonHandler();

    // TODO: consider moving it to local vars (depends on handling players disconnetion)
    private PlayerThread coupledThread = null;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    private final Game game;

    PlayerThread(SocketChannel socketChannel, Game game) {
        this.socketChannel = socketChannel;
        this.game = game;
    }

    void setThreadToInform(PlayerThread playerThread) {
        coupledThread = playerThread;
    }

    @Override
    public void run() {
        try {
            dataInputStream = new DataInputStream(socketChannel.socket().getInputStream());
            dataOutputStream = new DataOutputStream(socketChannel.socket().getOutputStream());

            String jsonRequest;
            String jsonResponse;

            while (true) {

                jsonRequest = dataInputStream.readUTF();
                log.debug("Message Received: " + jsonRequest);
                jsonResponse = jsonHandler.resolveAction(jsonRequest, game, true);
                dataOutputStream.writeUTF(jsonResponse);
                log.debug("Message Sent: " + jsonResponse);
                dataOutputStream.flush();

            }
        } catch (IOException e) {
            log.error("FAILED", e);
        } finally {
            try {
                socketChannel.close();
            } catch (IOException e) {
                log.error("FAILED", e);
            }
        }
    }
}