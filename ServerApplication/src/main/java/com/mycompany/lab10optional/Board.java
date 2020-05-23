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

import java.util.Random;

public class Board {

    public Square[][] board = new Square[6][6];
    public int dice;

    public Board() {
        int c = 1;
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                board[i][j] = new Square();
                board[i][j].setNumber(c);
                board[i][j].setAction("  ");
                c++;
            }
        }
        for (int i = 0; i < 9; i++) {
            Random rand = new Random();
            int x = rand.nextInt(4) + 1;
            int y = rand.nextInt(4) + 1;
            int pas = rand.nextInt(9) + 1;
            int direction = rand.nextInt(3);
            if (direction == 1) {
                if (board[x][y].getNumber() + pas > 36) {
                    pas = 36 - board[x][y].getNumber();
                }
                board[x][y].setAction("+" + pas);
            } else if (direction != 1) {
                if (board[x][y].getNumber() - pas < 1) {
                    pas = pas - board[x][y].getNumber();
                }
                board[x][y].setAction("-" + pas);
            }
        }
    }

    public int getDice() {
        return dice;
    }

    public void setDice(int dice) {
        this.dice = dice;
    }

    public String display() {
        String tabla = "";
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                if (board[i][j].getNumber() < 10) {
                    tabla = tabla + "|" + board[i][j].toString() + "  ";
                } else {
                    tabla = tabla + "|" + board[i][j].toString() + " ";
                }
            }
            tabla = tabla + "\n";
        }
        return tabla;
    }
    
    
}
