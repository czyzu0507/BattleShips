package io.github.expansionteam.battleships;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.channels.SocketChannel;

class PlayerThread extends Thread {
    final SocketChannel socketChannel;
    private PlayerThread coupledThread = null;

    DataInputStream dataInputStream = null;
    DataOutputStream dataOutputStream = null;

    JsonHandler jsonHandler = new JsonHandler();

    void closeSocket() throws IOException {
        socketChannel.close();
    }

    PlayerThread(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
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
                jsonResponse = jsonHandler.resolveAction(jsonRequest);
                dataOutputStream.writeUTF(jsonResponse);
                dataOutputStream.flush();

            }
        } catch (IOException e) {
            try {
                coupledThread.closeSocket();
            } catch (IOException ioExc) {
                ioExc.printStackTrace();
            }
        } finally {
            try {
                socketChannel.close();
                dataInputStream.close();
                dataOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}