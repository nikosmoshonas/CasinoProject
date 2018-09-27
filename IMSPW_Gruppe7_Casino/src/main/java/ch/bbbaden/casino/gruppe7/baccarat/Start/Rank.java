/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.casino.gruppe7.baccarat.Start;

/**
 *
 * @author Baeumli
 */
public enum Rank {

    //Defining the Ranks of the Cards
    
    TWO("2", 2),
    THREE("3", 3),
    FOUR("4", 4),
    FIVE("5", 5),
    SIX("6", 6),
    SEVEN("7", 7),
    EIGHT("8", 8),
    NINE("9", 9),
    TEN("10", 0),
    JACK("Jack", 0),
    QUEEN("Queen", 0),
    KING("King", 0),
    ACE("Ace", 1);
    
    //Variables
    private final String name;
    private final int value;

    //Constructor
    private Rank(String name, int value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }
    
}
