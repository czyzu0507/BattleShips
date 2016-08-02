package io.github.expansionteam.battleships.gui;

//import org.json.JSONObject;

import java.io.*;
import java.net.InetSocketAddress;


import java.net.SocketAddress;
import java.nio.channels.SocketChannel;

public class Client {
    private SocketChannel sc;
    private final int port = 1234;

    public Client() {
        try {
            SocketAddress address = new InetSocketAddress("127.0.0.1", port);
            sc = SocketChannel.open(address);
            sc.configureBlocking(true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String talkWithServer(String str) {

        ///////////////////////////
        // CLIENT HAS TO SEND A STRING TO INITIALIZE CONNECTION WITH SERVER !!!
        // SERVER HAS TO BE STARTED FIRST (BEFORE CLIENTS) !!!
        ///////////////////////////

        //// dżejson test
            /*String jsonString = new JSONObject()
                    .put("JSON1", "hello world1")
                    .put("JSON2", "hello world2")
                    .put("JSON3", new JSONObject()
                            .put("key1", "val")).toString();

            output.println( jsonString );*/
        /////////////////////////////
        String msg = "";

        try {
            DataInputStream dis = new DataInputStream(sc.socket().getInputStream());
            DataOutputStream dos = new DataOutputStream(sc.socket().getOutputStream());

            dos.writeUTF(str);
            dos.flush();

            msg = dis.readUTF();

            System.out.println(msg);

            // TODO: change ending game - send a message from the server, when one disconnected
        } catch (EOFException e) {    // ending game
            System.out.println("Enemy has lost the connection...");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return msg;
    }
}