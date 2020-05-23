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

import com.github.javafaker.Faker;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import static java.lang.System.exit;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientThread extends Thread {

    private Socket socket = null;
    private Game joc;
    private Player player;

    public ClientThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        int ok = 0;
        int inGame = 0;
        try {
            while (ok == 0) {
                Faker f = new Faker();
                String tabla = "";
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream());
                String[] simbol = {"A", "B"};
                String raspuns;
                String request = in.readLine();
                if (request.equals("stop")) {
                    GameServer.ruleaza = false;
                    out.println("Server stopped");
                    out.flush();
                    System.out.println("Stopping server...");
                    exit(1);
                } else if (request.equals("exit")) {
                    out.println("Closing connection with the server... you lost");
                    System.out.println("Client exited");
                    out.flush();
                    ok++;
                    for (Game g : GameServer.g) {
                        if (g.getId().equals(joc.getId())) {
                            GameServer.g.remove(g);
                        }
                    }
                    socket.close();
                    break;
                } else if (request.equals("Create game") && inGame == 0) {
                    out.println("Creating game");
                    System.out.println("Creating game");
                    out.flush();
                    joc = new Game(f.idNumber().toString());
                    player = new Player(simbol[joc.players.size()]);
                    player.setTurn(true);
                    joc.addPlayer(player);
                    GameServer.g.add(joc);
                    out.println("Game created: wait for another player");
                    out.flush();
                    out.println("You are player: " + player.getSymbol());
                    out.flush();
                    raspuns = joc.tabla.display();
                    tabla = raspuns;
                    out.print(raspuns);
                    out.flush();
                    inGame++;
                } else if (request.equals("Join game") && inGame == 0) {
                    if (GameServer.g.size() != 0) {
                        for (Game g : GameServer.g) {
                            if (g.players.size() < 2) {
                                joc = g;
                                player = new Player(simbol[joc.players.size()]);
                                joc.addPlayer(player);
                                g = joc;
                                out.println("You are player: " + player.getSymbol());
                                out.flush();
                                raspuns = joc.tabla.display();
                                tabla = raspuns;
                                out.print(raspuns);
                                out.flush();
                                inGame++;
                                break;
                            }
                        }
                    } else {
                        out.println("No game available, try creating one");
                        out.flush();
                    }
                } else if (request.equals("Roll dice")) {
                    int found = 0;
                    for (Game g : GameServer.g) {
                        if (joc.getId().equals(g.getId())) {
                            found++;
                            break;
                        }
                    }
                    if (found != 0) {
                        String id = "null";
                        int poz = -1;
                        for (Game g : GameServer.g) {
                            poz++;
                            if (joc.getId().equals(g.getId())) {
                                id = joc.getId();
                                break;
                            }
                        }
                        String k;
                        if (id.equals("null")) {
                            out.println("0");
                            out.flush();
                            k = "0";
                        } else {
                            out.println("1");
                            out.flush();
                            k = "1";
                        }
                        if (k.equals("1") && GameServer.g.get(poz).players.size() == 2) {
                            String c = in.readLine();
                            for (Player p : GameServer.g.get(poz).players) {
                                if (p.getSymbol().equals(c)) {
                                    if (p.getTurn() == true) {
                                        GameServer.g.get(poz).move(p);
                                        out.println("Dice: " + GameServer.g.get(poz).tabla.getDice());
                                        out.flush();
                                        raspuns = GameServer.g.get(poz).tabla.display();
                                        out.print(raspuns);
                                        out.flush();
                                        for (Player p1 : GameServer.g.get(poz).players) {
                                            p1.setTurn(!p1.getTurn());
                                        }
                                    } else {
                                        out.println("Wait for your turn");
                                        out.flush();
                                    }
                                }
                            }
                            if (GameServer.g.get(poz).gameOver() != true) {
                                out.println("Continue");
                                out.flush();
                            } else {
                                out.println("Game over");
                                out.flush();
                                socket.close();
                            }
                        } else {
                            if (!GameServer.g.get(poz).getId().equals(null)) {
                                out.println("Not enough players... please wait");
                                out.flush();
                            } else {
                                out.println("No game available... try creating one");
                                out.flush();
                            }
                        }
                    } else {
                        if (inGame==0) {
                            out.println("No game available... try creating one");
                            out.flush();
                        } else {
                            out.println("The opponent exited... you won");
                            out.flush();
                            socket.close();
                            break;
                        }
                    }
                } else {
                    out.println("Invalid command");
                    out.flush();
                }
            }
        } catch (Exception e) {
            System.out.println(e + " " + e.getStackTrace()[0].getLineNumber());
            for (Game g : GameServer.g) {
                if (g.getId().equals(joc.getId())) {
                    GameServer.g.remove(g);
                }
            }
        } finally {
            try {
                socket.close();
            } catch (IOException ex) {
                System.out.println(ex.getStackTrace()[0].getLineNumber());
            }
        }

    }
}