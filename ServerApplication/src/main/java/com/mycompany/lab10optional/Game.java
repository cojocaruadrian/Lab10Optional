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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class Game {

    public List<Player> players;
    public Board tabla;
    public String id;

    public Game(String id) {
        this.id=id;
        this.players = new ArrayList<>();
        this.tabla = new Board();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void addPlayer(Player p) {
        this.players.add(p);
    }

    public boolean isTurn(Player p) {
        return p.getTurn();
    }

    public void move(Player p) {
        Random rand = new Random();
        tabla.setDice(rand.nextInt(6) + 1);
        int bonus = 0;
        int position = p.getPosition() + tabla.getDice();
        activity(p, position);
        while (bonus == 0) {
            for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 6; j++) {
                    if (p.getPosition() == tabla.board[i][j].getNumber()) {
                        if (!tabla.board[i][j].getAction().equals("  ")) {
                            activity(p, p.getPosition());
                        } else {
                            bonus++;
                            break;
                        }
                    }
                }
            }
        }
    }

    public void activity(Player p, int position) {
        //removing the position from before
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (p.getPosition() == tabla.board[i][j].getNumber()) {
                    tabla.board[i][j].updateOcupied(p.getSymbol());
                }
            }
        }
        if (position >= 36) {
            p.setPosition(36);
        }
        int bonus = 0;
        //finding the position and doing the action if there's any
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (position == tabla.board[i][j].getNumber()) {
                    if (!(tabla.board[i][j].getAction().equals("  "))) {
                        bonus = Integer.parseInt(tabla.board[i][j].getAction());
                    }
                    p.setPosition(position + bonus);
                    break;

                }
            }
        }
        //updating the matrix
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (p.getPosition() == tabla.board[i][j].getNumber()) {
                    tabla.board[i][j].setOcupied(p.getSymbol());
                }
            }
        }
    }

    public boolean gameOver() {
        for (Player p : players) {
            if (p.getPosition() >= tabla.board[5][5].getNumber()) {
                return true;
            }
        }
        return false;
    }
}