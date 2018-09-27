/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.casino.gruppe7.yatzy_game;

/**
 *
 * @author Nikos
 */
public class TableViewloader {
    
    
    private String spieler;
    int spieler1;
    int spieler2;

    public TableViewloader(String spieler, int spieler1, int spieler2) {
        this.spieler = spieler;
        this.spieler1 = spieler1;
        this.spieler2 = spieler2;
    }

    public void setSpieler(String Spieler) {
        this.spieler = Spieler;
    }

    public void setSpieler1(int spieler1) {
        this.spieler1 = spieler1;
    }

    public void setSpieler2(int spieler2) {
        this.spieler2 = spieler2;
    }

    public String getSpieler() {
        return spieler;
    }

    public int getSpieler1() {
        return spieler1;
    }

    public int getSpieler2() {
        return spieler2;
    }
    
    
    
    
    

}