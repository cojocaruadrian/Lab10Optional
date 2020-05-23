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
public class Player {

    public String symbol;
    public boolean turn;
    public int position;

    public Player(String symbol) {
        this.turn = false;
        this.position = 1;
        this.symbol = symbol;
    }

    public boolean getTurn() {
        return turn;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

}
