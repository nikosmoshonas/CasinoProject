/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.casino.gruppe7.baccarat.Start;

import javafx.scene.image.Image;


/**
 *
 * @author Baeumli
 */
public class Card {

    private final Suit suit;
    private final Rank rank;
    private String imgpath;
    private Image frontimage;
    private Image backimage;
    
    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
        
        imgpath = "styles/assets/baccarat/cards/" + rank.getName().toLowerCase() + "_of_" + suit.getName().toLowerCase() + ".png";
        frontimage = new Image(imgpath);
    }

    public Suit getSuit() {
        return suit;
    }

    public Rank getRank() {
        return rank;
    }    

    public String getImgpath() {
        return imgpath;
    }

    public Image getFrontimage() {
        return frontimage;
    }

    public Image getBackimage() {
        return backimage;
    }
 
    @Override
    public String toString() {
        return "Card{" + "suit=" + suit + ", rank=" + rank + ", value=" + rank.getValue() + '}';
    }

    
    
}
