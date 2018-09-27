/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.casino.gruppe7.baccarat.Start;

import java.util.ArrayList;
import java.util.EnumSet;

/**
 *
 * @author Baeumli
 */
public class Deck {
    
    public ArrayList<Card> createDeck() {

        ArrayList<Card> cards = new ArrayList<>();

        for (Suit suit : EnumSet.allOf(Suit.class)) {

            for (Rank rank : EnumSet.allOf(Rank.class)) {
                
                Card card = new Card(suit, rank);
                cards.add(card);
            }

        }
        return cards;
    }
}
