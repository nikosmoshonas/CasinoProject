/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.casino.gruppe7.blackjack;

/**
 *
 * @author flori
 */
public class KartenSpiel {

    // 52 Karten (2-10,Bube,Dame,König,Ass für Karo, Herz, Pik und Kreuz)
    private Karte[] stapel;
    int Stapel = 52;
     public void setStapel(Karte[] stapel) {
        this.stapel = stapel;
    }

    // Konstruktor
    public KartenSpiel() {
        stapel = new Karte[52];
        int zaehler = 0;
        for (int f = 0; f < 4; f++) {
            for (int w = 1; w < 14; w++) {
                stapel[zaehler] = new Karte(f, w);
                zaehler++;
            }
        }
        mischen();
    }

    // KartenSpiel-Methoden
    public void mischen() {
        Karte temp;
        for (int i = 51; i > 0; i--) {
            int zuff = (int) (Math.random() * (i + 1));
            temp = stapel[i];
            stapel[i] = stapel[zuff];
            stapel[zuff] = temp;
        }
    }

    public Karte gibEineKarte() {
        
        if (Stapel < 0){
              mischen();
        
        }
        System.out.println(stapel.length -1);
        Stapel --;
        System.out.println(Stapel);
        return stapel[Stapel];
        
    }

   
}
