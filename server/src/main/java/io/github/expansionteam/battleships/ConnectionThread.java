package io.github.expansionteam.battleships;

import java.io.IOException;
import java.nio.channels.SocketChannel;

import static org.apache.log4j.Logger.*;
import org.apache.log4j.Logger;

class ConnectionThread implements Runnable {
    private final SocketChannel sc1, sc2;
    private final int gameIndex;
    private final Game game = new Game();

    private final static Logger log = getLogger(Server.class);

    ConnectionThread(SocketChannel sc1, SocketChannel sc2, int gameIndex) {
        this.sc1 = sc1;
        this.sc2 = sc2;
        this.gameIndex = gameIndex;
    }

    @Override
    public void run() {
        PlayerThread p1 = new PlayerThread(sc1, game);
        PlayerThread p2 = new PlayerThread(sc2, game);

        p1.setName("Game_" + gameIndex + "_Player_1");
        p2.setName("Game_" + gameIndex + "_Player_2");

        p1.start();
        p2.start();

        p1.setThreadToInform(p2);
        p2.setThreadToInform(p1);

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
}