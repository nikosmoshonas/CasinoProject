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
public class Karte {
    // Bewertung der Karten und Definition der Farben
   public final static int KARO  = 0, HERZ  = 1, PIK   = 2, KREUZ = 3;                            
   public final static int BUBE = 11, DAME = 12, KOENIG = 13, ASS = 1;
   private int farbe, wert;  
                            
   // Konstruktor                          
   public Karte(int f, int w) {
      farbe = f;
      wert  = w;
   }


        
   // get-set-Funktionen
   public int getFarbe() {
      return farbe;
   }
    
   public int getWert() {
      return wert;
   }
    
   // Karten-Methoden
   public String Farbe2String() {
      switch (farbe) {
         case KARO:   
            return "KARO";
         case HERZ:   
            return "HERZ";
         case PIK: 
            return "PIK";
         case KREUZ:    
            return "KREUZ";            
      }
      System.out.println("Farbe falsch! : "+farbe);
      return "-1";
   }
    
   public String Wert2String() {
       String Wert;
       Wert = Integer.toString(getWert());
       return Wert;
   }
    
   public String Karte2String() {
      return Farbe2String() + "_" + Wert2String();
   }
}
