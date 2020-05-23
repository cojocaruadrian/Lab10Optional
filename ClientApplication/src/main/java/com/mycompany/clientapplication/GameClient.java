/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.clientapplication;

/**
 *
 * @author adria
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class GameClient {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    
    public static void main(String[] args) throws IOException {
        String symbol = "";
        String serverAddress = "127.0.0.1"; //adresa IP a serverului
        int PORT = 2222;
        int ok = 0;
        int inGame = 0;

        try (
                Socket socket = new Socket(serverAddress, PORT);
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            while (ok == 0) {
                // Send a request to the server
                Scanner scan = new Scanner(System.in);
                System.out.println("Enter command... ");
                String request = scan.nextLine();
                out.println(request);
                if (request.equals("Create game") && inGame == 0) {
                    String response = in.readLine();
                    System.out.println(response);
                    response = in.readLine();
                    System.out.println(response);
                    response = in.readLine();
                    symbol = response.substring(response.length() - 1, response.length());
                    System.out.println(response);
                    for (int i = 0; i < 6; i++) {
                        response = in.readLine();
                        System.out.println(response);
                    }
                    inGame = 1;
                } else if (request.equals("Join game")) {
                    String response = in.readLine();
                    System.out.println(response);
                    if (!response.equals("No game available, try creating one")) {
                        symbol = response.substring(response.length() - 1, response.length());
                        for (int i = 0; i < 6; i++) {
                            response = in.readLine();
                            System.out.println(response);
                        }
                        inGame = 1;
                    }
                } else if (request.equals("Roll dice") && inGame == 1) {
                    String r = in.readLine();
                    System.out.println(r);
                    if (!r.equals("The opponent exited... you won") && !r.equals("Not enough players... please wait")) {

                        if (Integer.parseInt(r) == 1) {
                            out.println(symbol);
                        }
                        String response = in.readLine();
                        System.out.println(response);
                        if (!(response.equals("Not enough players... please wait")) && !(response.equals("No game available... try creating one"))) {
                            if (!(response.equals("Wait for your turn"))) {
                                for (int i = 0; i < 6; i++) {
                                    response = in.readLine();
                                    System.out.println(response);
                                }
                            }
                            response = in.readLine();
                            System.out.println(response);
                        } else {
                            System.out.println(response);
                        }
                    } else {
                        System.out.println(r);
                        if (r.equals("The opponent exited... you won")) {
                            ok++;
                        }
                    }
                } else if (request.equals("exit")) {
                    String response = in.readLine();
                    System.out.println(response);
                    ok++;
                } else {
                    String response = in.readLine();
                    System.out.println(response);
                }
            }
        } catch (Exception e) {
            System.err.println("No server listening... " + e + " " + e.getStackTrace()[0].getLineNumber());
        }

    }

}
