package io.github.expansionteam.battleships;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.nio.channels.SocketChannel;

class PlayerThread extends Thread {
    private final SocketChannel socketChannel;
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
                jsonResponse = jsonHandler.resolveAction(jsonRequest, game);
                dataOutputStream.writeUTF(jsonResponse);
                dataOutputStream.flush();

            }
        }
        catch (EOFException e) {
            try {
                // TODO: write proper json
              //  coupledThread.dataOutputStream.writeUTF("something ");
                System.out.println("shutdown input");
                coupledThread.socketChannel.shutdownInput();
                System.out.println("send AAAA to " + Thread.currentThread().getName());
                coupledThread.dataOutputStream.writeUTF("AAAAAAAAAAAAAAAA");
                System.out.println("close socket");
                coupledThread.closeSocket();
                System.out.println("after closing socket");
            } catch (IOException ioExc) {
                ioExc.printStackTrace();
            }
        }
        catch (IOException e) {
            //
        }
        finally {
            try {
                socketChannel.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}