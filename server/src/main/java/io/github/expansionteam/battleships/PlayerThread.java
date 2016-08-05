package io.github.expansionteam.battleships;

import org.apache.log4j.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.channels.SocketChannel;

class PlayerThread extends Thread {
    private final SocketChannel socketChannel;
    private final static Logger log = Logger.getLogger(JsonHandler.class);
    private PlayerThread coupledThread = null;
    private JsonHandler jsonHandler = new JsonHandler();
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    private Game game;

    private void closeSocket() throws IOException {
        socketChannel.close();
    }

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
                jsonResponse = jsonHandler.resolveAction(jsonRequest, game);
                dataOutputStream.writeUTF(jsonResponse);
                log.debug("Message Sent: " + jsonResponse);
                dataOutputStream.flush();

            }
        }
//        catch (EOFException e) {
//            try {
        // TODO: closing connection when one player is disconnected
                /*
                coupledThread.socketChannel.shutdownInput();
                coupledThread.dataOutputStream.writeUTF("AAAAAAAAAAAAAAAA");
                coupledThread.closeSocket();
                */

//            } catch (IOException ioExc) {
//                ioExc.printStackTrace();
//            }
//        }
        catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socketChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}