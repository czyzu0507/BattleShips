package io.github.expansionteam.battleships.gui;

//import org.json.JSONObject;

import java.io.*;
import java.net.InetSocketAddress;


import java.net.SocketAddress;
import java.nio.channels.SocketChannel;

import static java.lang.Thread.sleep;

public class Client {
    private SocketChannel sc;
    private final int port = 1234;

    Client() {
        try {
            SocketAddress address = new InetSocketAddress("127.0.0.1", port);
            sc = SocketChannel.open(address);
            sc.configureBlocking(true);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void talkWithServer() {
        try {
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

            DataInputStream dis = new DataInputStream(sc.socket().getInputStream());
            DataOutputStream dos = new DataOutputStream(sc.socket().getOutputStream());

            // ini msg
            dos.writeUTF("ini");
            dos.flush();
            String msg;

            while (true) {
                msg = dis.readUTF();

                // test communication - infinite echo
                System.out.println(msg);
                String ss = "message from the client...";
                dos.writeUTF(ss);
                dos.flush();

                // TODO: remove this because of blocking event-driven communication nature :)
                sleep(1000);
            }
        } catch (EOFException e) {    // ending game
            System.out.println("Enemy has lost the connection...");
        } catch (IOException e) {
            e.printStackTrace();
        }
        // TODO: remove this, when sleep is removed
        catch (InterruptedException e) {
            /*...*/
            e.printStackTrace();
        }
    }

    // entry point
    public static void main(String[] args) {
        Client client = new Client();
        client.talkWithServer();
    }
}