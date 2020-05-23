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
public class Square {

    public int number;
    public String action;
    public String ocupied;

    public Square(int number, String action) {
        this.number = number;
        this.action = action;
        this.ocupied = "   ";
    }

    public Square() {
        this.ocupied = "   ";
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getOcupied() {
        return ocupied;
    }

    public void setOcupied(String ocupied) {
        if (this.ocupied.substring(0, 1).equals(" ")) {
            this.ocupied = ocupied + "  ";
        } else {
            this.ocupied = this.ocupied.substring(0, 1) + " " + ocupied;
        }

    }

    public void updateOcupied(String ocupied) {
        int poz = this.ocupied.indexOf(ocupied);
        if (poz == 2) {
            this.ocupied = this.ocupied.replace(ocupied, " ");
        } else {
            this.ocupied = this.ocupied.replace(ocupied, " ");
            if (!this.ocupied.equals("   ")) {
                String caracter = this.ocupied.substring(this.ocupied.length() - 1, this.ocupied.length());
                this.ocupied = caracter + "  ";
            }
        }
    }

    @Override
    public String toString() {
        return number + " " + action + " " + ocupied;
    }

}