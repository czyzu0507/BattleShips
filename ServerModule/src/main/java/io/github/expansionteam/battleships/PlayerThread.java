package io.github.expansionteam.battleships;


import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.io.BufferedReader;
import java.io.PrintWriter;

class PlayerThread implements Runnable {
    private BufferedReader br;
    private PrintWriter pw;
    private String str;

    private final SocketChannel sc;

    PlayerThread(SocketChannel sc, String str) {
        this.sc = sc;
        this.str = str;
    }

    @Override
    public void run() {
        Socket socket = sc.socket();

        try {
            InputStreamReader ir1 = new InputStreamReader(socket.getInputStream());
            br = new BufferedReader(ir1);
            pw = new PrintWriter(socket.getOutputStream(), true);
        }
        catch (IOException e) {}

        try {
            String res = "";
            while ((res = br.readLine()) != null) {
                System.out.println( Thread.currentThread().getName() + " received: " + res );
                pw.println("ECHO REPLY: " + res);
            }
        }
        catch (IOException e) {}
    }
}