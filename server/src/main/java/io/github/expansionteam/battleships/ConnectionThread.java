package io.github.expansionteam.battleships;

import java.io.IOException;
import java.nio.channels.SocketChannel;

// TODO: change ending game - send a message from the server, when one disconnected
// thread to manage the game of two players
class ConnectionThread implements Runnable {

    private final SocketChannel sc1;
    private final SocketChannel sc2;
    private final int n;
    private Game game = new Game();

    ConnectionThread(SocketChannel sc1, SocketChannel sc2, int n) {
        this.sc1 = sc1;
        this.sc2 = sc2;
        this.n = n;
    }

    @Override
    public void run() {

        PlayerThread p1 = new PlayerThread(sc1, game);
        PlayerThread p2 = new PlayerThread(sc2, game);

        p1.setName("Thread_" + n + "_Player_1");
        p2.setName("Thread_" + n + "_Player_2");

        p1.start();
        p2.start();

        p1.setThreadToInform(p2);
        p2.setThreadToInform(p1);

        try {
            p1.join();
            p2.join();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("AFTER JOIN - game " + n + " ended...");

        try {
            sc1.close();
            sc2.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("AFTER CLOSE - socket closed... Terminating thread...");
    }
}