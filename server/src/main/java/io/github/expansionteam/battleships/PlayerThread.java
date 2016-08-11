package io.github.expansionteam.battleships;

import org.apache.log4j.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import static io.github.expansionteam.battleships.ConnectionThread.*;
import static io.github.expansionteam.battleships.ConnectionThread.GameState.GENERATING_SHIPS;
import static io.github.expansionteam.battleships.ConnectionThread.GameState.TURN_GAME;
import static io.github.expansionteam.battleships.ConnectionThread.Player;
import static io.github.expansionteam.battleships.ConnectionThread.Player.*;
import static io.github.expansionteam.battleships.JsonHandler.*;

class PlayerThread extends Thread {
    private final SocketChannel socketChannel;
    private final static Logger log = Logger.getLogger(PlayerThread.class);
    private final JsonHandler jsonHandler = new JsonHandler();
    private final Player currentPlayer;

    private PlayerThread coupledThread = null;
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private final ConnectionThread parentThread;

    private CyclicBarrier cyclicBarrierForStateSynchronization;

    PlayerThread(SocketChannel socketChannel, ConnectionThread parentThread, Player currentPlayer) {
        this.socketChannel = socketChannel;
        this.parentThread = parentThread;
        this.currentPlayer = currentPlayer;
        try {
            dataInputStream = new DataInputStream(socketChannel.socket().getInputStream());
            dataOutputStream = new DataOutputStream(socketChannel.socket().getOutputStream());
        } catch (IOException e) {
            log.trace(e);
        }
    }

    void setBarrierForStateRendezvous(CyclicBarrier cyclicBarrier) {
        this.cyclicBarrierForStateSynchronization = cyclicBarrier;
    }

    void setThreadToInform(PlayerThread playerThread) {
        coupledThread = playerThread;
    }

    private String readFromClient() throws IOException {
        String jsonRequest = dataInputStream.readUTF();
        log.info("Message Received: " + jsonRequest);
        return jsonRequest;
    }

    private String generateJSONResponse(String jsonRequest, boolean player, RequestState requestState) {
        return jsonHandler.createMessage(jsonRequest, parentThread.getGameObject(), player, requestState);
    }

    private void writeToClient(String answer) throws IOException {
        dataOutputStream.writeUTF(answer);
        log.info("Message Sent: " + answer);
        dataOutputStream.flush();
    }

    @Override
    public void run() {
        try {
            sleep(50);
            RequestState requestState;
            String request, answer;

            while (parentThread.getGameState() == GENERATING_SHIPS) {
                request = readFromClient();
                requestState = jsonHandler.apply(request, parentThread.getGameObject());
                answer = generateJSONResponse(request, currentPlayer == PLAYER1, requestState);
                writeToClient(answer);

                if (getJSONType(answer).equals("ShipsGeneratedEvent")) {
                    break;
                }

                sleep(100);
            }

            log.info("BEFORE BARRIER 1");
            cyclicBarrierForStateSynchronization.await();
            log.info("AFTER BARRIER 2");

            while (parentThread.getGameState() == TURN_GAME) {
                Player player = parentThread.getCurrentPlayer();

                if (player == currentPlayer) {
                    sleep(50);

                    request = readFromClient();
                    requestState = jsonHandler.apply(request, parentThread.getGameObject());
                    String playerResponse = generateJSONResponse(request, true, requestState);
                    String opponentResponse = generateJSONResponse(request, false, requestState);
                    writeToClient(playerResponse);

                    if (requestState != RequestState.FIELD_ALREADY_HIT) {
                        coupledThread.dataOutputStream.writeUTF(opponentResponse);
                        coupledThread.interrupt();
                        log.info("P1:  AFTER SENDING MESSAGES TO BOTH PLAYERS");
                    }

                    if (requestState == RequestState.EMPTY_FIELD_HIT) {
                        parentThread.switchPlayer();
                        log.info("P1:  PLAYER SWITCHED");
                    }
                } else {
                    readFromClient();
                    try {
                        synchronized (this) {
                            wait();
                        }

                    } catch (InterruptedException e) {
                        log.trace(e);
                    }
                }

                sleep(50);
            }

        } catch (IOException | InterruptedException | BrokenBarrierException e) {
            log.trace(e);
        } finally {
            try {
                socketChannel.close();
            } catch (IOException e) {
                log.trace(e);
            }
        }
    }
}