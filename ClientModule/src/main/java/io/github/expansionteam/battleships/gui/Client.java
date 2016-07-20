package io.github.expansionteam.battleships.gui;

import org.json.JSONObject;

import java.net.Socket;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;

// TO TEST MESSAGES FROM INPUT (ECHO ACTION)
import java.util.Scanner;



class Client {
    private void connect() {
        try (
                Socket socket = new Socket("127.0.0.1", 1234);
                PrintWriter output = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                Scanner sc = new Scanner(System.in)
        )
        {
            String str = "";

            ///////////////////////////
            // CLIENT HAS TO SEND A STRING TO INITIALIZE CONNECTION WITH SERVER !!!
            // SERVER HAS TO BE STARTED FIRST (BEFORE CLIENTS) !!!
            ///////////////////////////

            //// dżejson test
            String jsonString = new JSONObject()
                    .put("JSON1", "hello world1")
                    .put("JSON2", "hello world2")
                    .put("JSON3", new JSONObject()
                            .put("key1", "val")).toString();

            output.println( jsonString );
            /////////////////////////////


            while ((str = in.readLine()) != null) {
                System.out.println( str );
                String tmpStr = sc.nextLine();
                output.println(tmpStr);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Client client = new Client();
        client.connect();
    }
}