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
import java.util.Vector;

public class Spieler {
   private String spielerName;
   private int geld;
   private Vector karten; 
   public boolean istAss;
   
   // Konstruktor
   public Spieler(String n, int g) {
      spielerName = n;
      geld        = g;
      karten      = new Vector();
   }
   
   // get-set-Funktionen
   public void setName(String n){
      spielerName = n;
   }
   
   public String getName(){
      return spielerName;
   }

   public void setGeld(int g){
      geld  = g;
   }
   
   public int getGeld(){
      return geld;
   }

   public void clear() {
      karten.removeAllElements();
   }
   
   public void addKarte(Karte k) {
      karten.addElement(k);
   }
   
   public void remKarte(Karte k) {
      karten.removeElement(k);
   }

   public int getAnzahlKarten() {
      return karten.size();
   }
   
   // Spieler-Methoden   
   public Karte getKarte(int p) {
      if ((p >= 0) && (p < karten.size()))
         return (Karte)karten.elementAt(p);
      else
         return null;
   }   
   
   public int aktuelleWertung() {
      int wert, anzahl;      
     ;  

      wert     = 0;
      istAss   = false;
      anzahl   = getAnzahlKarten();
      Karte karte;    
      int kartenWert;
      
      // wir durchlaufen unseren aktuellen Kartenstapel
      for (int i=0; i < anzahl; i++) {
         karte       = getKarte(i);
         kartenWert  = karte.getWert();
         
         // Bewertung der Bilder
         if (kartenWert > 10) kartenWert = 10;          
         if (kartenWert == 1) istAss = true;   
            
         wert += kartenWert;
      }

      // Ass-Wert selber bestimmen
      if (istAss && (wert + 10 <= 21))
         wert = wert + 10;

      return wert;
   }

    public boolean isIstAss() {
        return istAss;
    }
   
   
   
}
