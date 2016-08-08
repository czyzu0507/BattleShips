package io.github.expansionteam.battleships;

import org.apache.log4j.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.concurrent.*;

import static io.github.expansionteam.battleships.ConnectionThread.*;
import static io.github.expansionteam.battleships.ConnectionThread.GameState.*;

class PlayerThread extends Thread {
    private final SocketChannel socketChannel;
    private final static Logger log = Logger.getLogger(PlayerThread.class);
    private final JsonHandler jsonHandler = new JsonHandler();
    private final Player currentPlayer;

    // TODO: consider moving it to local vars (depends on handling players disconnetion)
    private PlayerThread coupledThread = null;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;

    private final ConnectionThread parentThread;

    PlayerThread(SocketChannel socketChannel, ConnectionThread parentThread, Player currentPlayer) {
        this.socketChannel = socketChannel;
        this.parentThread = parentThread;
        this.currentPlayer = currentPlayer;
    }

    void setThreadToInform(PlayerThread playerThread) {
        coupledThread = playerThread;
    }

    @Override
    public void run() {
        try {
            dataInputStream = new DataInputStream(socketChannel.socket().getInputStream());
            dataOutputStream = new DataOutputStream(socketChannel.socket().getOutputStream());

            sleep(1000);

            while (parentThread.getGameState() == GENERATING_SHIPS) {

                String jsonRequest = dataInputStream.readUTF();
                log.debug("Message Received: " + jsonRequest);
                String jsonResponse = jsonHandler.resolveAction(jsonRequest, parentThread.getGameObject(), true);
                dataOutputStream.writeUTF(jsonResponse);
                log.debug("Message Sent: " + jsonResponse);
                dataOutputStream.flush();

            }
            System.out.println("HERE");
            while (true) {
                talkWithClient();
                sleep(1);
            }
        } catch (Exception e) {
            log.trace(e);
        } finally {
            try {
                socketChannel.close();
            } catch (IOException e) {
                log.trace(e);
            }
        }
    }

    private void talkWithClient() throws IOException {
        String jsonRequest = "", jsonResponse;
        Callable<String> readClientTask = () -> dataInputStream.readUTF();
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<String> jsonRequestFuture = executor.submit(readClientTask);

        try {
            jsonRequest = jsonRequestFuture.get(50, TimeUnit.MILLISECONDS);
            executor.shutdownNow();
        } catch (TimeoutException | InterruptedException | ExecutionException e) {
            log.trace(e);
        }

        log.debug(jsonRequest);

        if (jsonRequest.length() == 0) {
            // jsonRequestFuture.cancel(false);
            return;
        }

        log.debug("Message Received: " + jsonRequest);
        jsonResponse = jsonHandler.resolveAction(jsonRequest, parentThread.getGameObject(), true);
        dataOutputStream.writeUTF(jsonResponse);
        log.debug("Message Sent: " + jsonResponse);
        dataOutputStream.flush();
    }
}