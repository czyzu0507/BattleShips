package io.github.expansionteam.battleships;

import java.nio.channels.SocketChannel;

// thread to manage the game of two players
public class ConnectionThread implements Runnable {
    private final SocketChannel sc1;
    private final SocketChannel sc2;

    //tmp
    private final int n;

    ConnectionThread( SocketChannel sc1, SocketChannel sc2, int n) {
        this.sc1 = sc1;
        this.sc2 = sc2;
        this.n = n;
    }

    @Override
    public void run() {
        Runnable r1 = new PlayerThread(sc1, "player1");
        Runnable r2 = new PlayerThread(sc2, "player2");

        Thread t1 = new Thread(r1);
        Thread t2 = new Thread(r2);

        t1.setName( "Thread_" + n + "_Player_1" );
        t2.setName( "Thread_" + n + "_Player_2" );

        //while (true) {
        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        }
        catch (Exception e) {
            /* ... */
        }
    }
}

