package io.github.expansionteam.battleships;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

import static io.github.expansionteam.battleships.ConnectionThread.GameState.*;
import static io.github.expansionteam.battleships.ConnectionThread.Player.*;
import static java.lang.Thread.sleep;
import static org.apache.log4j.Logger.*;
import org.apache.log4j.Logger;

class ConnectionThread implements Runnable {
    private final SocketChannel sc1, sc2;
    private final int gameIndex;
    private final Game game = new Game();
    private GameState gameState = INITIAL;
    private final CyclicBarrier cyclicBarrierForStatesSynchronization = new CyclicBarrier(3);

    private final static Logger log = getLogger(ConnectionThread.class);

    ConnectionThread(SocketChannel sc1, SocketChannel sc2, int gameIndex) {
        this.sc1 = sc1;
        this.sc2 = sc2;
        this.gameIndex = gameIndex;
    }

    @Override
    public void run() {
        PlayerThread p1 = new PlayerThread(sc1, this, PLAYER1);
        PlayerThread p2 = new PlayerThread(sc2, this, PLAYER2);

        p1.setName("Game_" + gameIndex + "_Player_1");
        p2.setName("Game_" + gameIndex + "_Player_2");

        p1.setBarrierForStateRendezvous(cyclicBarrierForStatesSynchronization);
        p2.setBarrierForStateRendezvous(cyclicBarrierForStatesSynchronization);

        p1.start();
        p2.start();

        p1.setThreadToInform(p2);
        p2.setThreadToInform(p1);

        synchronized (this) {
            gameState = GENERATING_SHIPS;
        }

        while (!game.generatingShipsFinished()) {
            try {
                sleep(250);
            }
            catch (InterruptedException e) {
                log.trace(e);
            }
        }

        gameState = TURN_GAME;

        try {
            cyclicBarrierForStatesSynchronization.await();
        }
        catch (InterruptedException | BrokenBarrierException e) {
            log.trace(e);
        }

        try {
            p1.join();
            p2.join();
        } catch (InterruptedException e) {
            log.error("FAILED", e);
        }

        try {
            sc1.close();
            sc2.close();
        } catch (IOException e) {
            log.error("FAILED", e);
        }

        log.info("AFTER CLOSE - socket closed... Terminating thread...");
    }

    Game getGameObject() {
        return this.game;
    }

    synchronized GameState getGameState() {
        return this.gameState;
    }

    enum GameState {
        INITIAL {
            @Override
            public Player getPlayer() {
                return BOTH;
            }
        },
        GENERATING_SHIPS {
            @Override
            public Player getPlayer() {
                return BOTH;
            }
        },
        TURN_GAME {
            private Player current = PLAYER1;

            @Override
            public Player getPlayer() {
                return current;
            }

            @Override
            public void switchPlayer() {
                current = (current == PLAYER1) ? PLAYER2 : PLAYER1;
            }
        };

        public abstract Player getPlayer();
        public void switchPlayer() {}
    }

    enum Player {
        BOTH, PLAYER1, PLAYER2
    }
}