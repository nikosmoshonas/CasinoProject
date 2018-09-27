/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.casino.gruppe7.baccarat.Start;


import java.util.ArrayList;

/**
 *
 * @author lbaum
 */
public class Hand {

    //Objects
    private ArrayList<Card> hand = new ArrayList<>();

    //If the sum is more than ten, the first digit gets removed
    public int getSum(int sum) {

        for (Card c : hand) {
            sum = sum + c.getRank().getValue();
            if (sum != 10) {
                sum = sum % 10;
            }
        }
        return sum;
    }

    //Adds a card from the stockpile to the hand
    public void addCard(Card c) {
        if (hand.size() < 3) {
            hand.add(c);
        } else {
            System.out.println("Bereits 3 Karten vorhanden");
        }
    }

    //Prints all cards within a hand
    public void printHand() {
        for (int i = 0; i < hand.size(); i++) {
            System.out.println(hand.get(i));
        }
    }
}
