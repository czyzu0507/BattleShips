package io.github.expansionteam.battleships;

import java.io.*;
import java.nio.channels.SocketChannel;

class PlayerThread extends Thread {
    final SocketChannel sc;
    private PlayerThread coupledThread = null;

    DataInputStream dis = null;
    DataOutputStream dos = null;

    void closeSocket() throws IOException {
        sc.close();
    }

    PlayerThread(SocketChannel sc) {
        this.sc = sc;
    }

    void setThreadToInform(PlayerThread playerThread) {
        coupledThread = playerThread;
    }

    @Override
    public void run() {
        try {
            dis = new DataInputStream(sc.socket().getInputStream());
            dos = new DataOutputStream(sc.socket().getOutputStream());

            String str;

            while (true) {

                str = dis.readUTF();
                System.out.println(str);
                dos.writeUTF("response: " + str);
                dos.flush();

            }
        } catch (IOException e) {
            try {
                coupledThread.closeSocket();
            } catch (IOException ioExc) {
                ioExc.printStackTrace();
            }
        } finally {
            try {
                sc.close();
                dis.close();
                dos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}