/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.lab10optional;

/**
 *
 * @author adria
 */
import java.io.IOException;
import static java.lang.Thread.sleep;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class GameServer {

    /*
     defining the used port
     */
    public static final int PORT = 2222;
    public static boolean ruleaza;
    public static List<Game> g = new ArrayList<>();
    public static int nrPlayers = 0;
    public static List<ClientThread> clients = new ArrayList<>();

    public GameServer() throws IOException {
        ServerSocket serverSocket = null;
        ruleaza = true;
        try {
            serverSocket = new ServerSocket(PORT);
            while (ruleaza == true) {
                System.out.println("Waiting for a client ...");
                Socket socket = serverSocket.accept();
                // Execute the client's request in a new thread
                ClientThread t = new ClientThread(socket);
                t.start();
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            serverSocket.close();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        GameServer server = new GameServer();
    }

}
