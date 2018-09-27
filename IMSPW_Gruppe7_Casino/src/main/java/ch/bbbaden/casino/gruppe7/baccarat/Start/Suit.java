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
public enum Suit {
    
    //Defining the Suits of the Cards
    
    SPADES("Spades"),
    DIAMONDS("Diamonds"),
    CLUBS("Clubs"),
    HEARTS("Hearts");
    
    //Variables
    private final String name;

    //Constructor
    private Suit(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }  
}
    