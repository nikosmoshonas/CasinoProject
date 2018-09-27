/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.casino.gruppe7.blackjack;

import ch.bbbaden.casino.gruppe7.Stats;
import ch.bbbaden.casino.gruppe7.UserLoggedIn;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.Stage;

/**
 *
 * @author flori
 */
public class BlackJack {

    private UserLoggedIn user = UserLoggedIn.getInstance();
    Stats stats = new Stats("Blackjack");
    public int versicherung = 100;
    private int einsatz = 0;
    private boolean spiellaeuft = true;
    private FXMLController controller;
    public static Stage stage;
    int position = 2;
    public boolean gesetzt = false;
    public Karte verdeckt;

    //Spieler Variablen
    public String name = "Florian";
    public int SpieleCounter;
    public int SpieleGewonnen;
    public int SpieleVerloren;
    public int GewinnCounter;
    public int VerlustCounter;
    public int balance;

    KartenSpiel kartenSpiel = new KartenSpiel();
    Spieler spieler = new Spieler(name, 500);
    Spieler dealer = new Spieler("Dealer", 10000);

    public BlackJack(FXMLController controller) throws SQLException {
        this.balance = user.getUserBalance();
        this.controller = controller;
    }

    public void neuesSpiel(Boolean b) {
        userBalance();
        spieler.clear();
        dealer.clear();
        gesetzt = b;
        position = 2;
        controller.setWertungDealer("--");
        if (gesetzt == true) {
            for (int i = 0; i < 2; i++) {
                Karte k = kartenSpiel.gibEineKarte();
                controller.addCard("/BlackJack_Karten/" + k.Karte2String() + ".png", i);
                neueKarte(k);
                ausgabeKartenSpieler(getSpieler(), getDealer());
            }
            for (int i = 5; i < 7; i++) {

                if (i == 5) {
                    Karte k = kartenSpiel.gibEineKarte();
                    controller.addCard("/BlackJack_Karten/deck.png", i);
                    neueKarteDealer(k);
                    setVerdeckt(k);
                    i++;

                }
                Karte k = kartenSpiel.gibEineKarte();
                controller.addCard("/BlackJack_Karten/" + k.Karte2String() + ".png", i);
                neueKarteDealer(k);
                ausgabeKartenSpieler(getSpieler(), getDealer());
            }

            spiellaeuft = true;
            Karte karte = getDealer().getKarte(1);
            System.out.println(karte.getWert());
            if (karte.getWert() == 1) {
                controller.setAusgabe("Wollen Sie Versichern?");
                controller.setInsuranceVisible();
                getDealer().setGeld(getDealer().getGeld() + einsatz);
                ausgabeKartenSpieler(getSpieler(), getDealer());
                
            }
        }

    }

    public void naechsteKarte() {

        if (getSpielStatus()) {

            //neueKarte();
            if (getSpieler().aktuelleWertung() > 21) {

                ausgabeKartenSpieler(getSpieler(), getDealer());
                controller.setAusgabe("Du hast verloren! Deine Wertung liegt mit "
                        + getSpieler().aktuelleWertung() + " über 21.");
                getDealer().setGeld(getDealer().getGeld() + einsatz);

                setKonto(2);
                gewonnen(2);
                einsatz = 0;
                spiellaeuft = true;
                kontoDaten(getSpieler(), getDealer());
            } else if (spieler.isIstAss() == true && getSpieler().aktuelleWertung() == 21) {
                controller.setAusgabe("Es ist ein Blackjack. ");
                getDealer().setGeld(getDealer().getGeld() + einsatz);
                ausgabeKartenSpieler(getSpieler(), getDealer());
                setKonto(3);
                gewonnen(2);
            } else {

                Karte k = kartenSpiel.gibEineKarte();
                controller.addCard("/BlackJack_Karten/" + k.Karte2String() + ".png", position);
                neueKarte(k);
                ausgabeKartenSpieler(getSpieler(), getDealer());
                position++;

                if (getSpieler().aktuelleWertung() > 21) {

                    ausgabeKartenSpieler(getSpieler(), getDealer());
                    controller.setAusgabe("Du hast verloren! Deine Wertung liegt mit "
                            + getSpieler().aktuelleWertung() + " ueber 21.");
                    getDealer().setGeld(getDealer().getGeld() + einsatz);
                    gewonnen(2);
                    setKonto(2);
                    einsatz = 0;
                    spiellaeuft = true;
                    kontoDaten(getSpieler(), getDealer());
                }
            }

        }

    }

    public void naechsterSpieler() {

        if ((getEinsatz() > 0) && (getSpielStatus())) {
            dealerIstDran();

            if (getDealer().aktuelleWertung() > 21) {
                controller.setAusgabe("Du hast gewonnen! Der Dealer hat mit "
                        + getDealer().aktuelleWertung() + " ueberboten.");
                getSpieler().setGeld(getSpieler().getGeld()
                        + einsatz);
                ausgabeKartenSpieler(getSpieler(), getDealer());
                setKonto(1);
                gewonnen(1);

            } else if (getDealer().getAnzahlKarten() == 5) {
                controller.setAusgabe("Du hast verloren. Der Dealer erhielt 5 Karten unter 21.");
                getDealer().setGeld(getDealer().getGeld() + einsatz);
                ausgabeKartenSpieler(getSpieler(), getDealer());
                setKonto(2);
                gewonnen(2);
            } else if (getDealer().aktuelleWertung() > getSpieler().aktuelleWertung()) {
                controller.setAusgabe("Du hast verloren. " + getDealer().aktuelleWertung() + " zu "
                        + getSpieler().aktuelleWertung() + ".");
                getDealer().setGeld(getDealer().getGeld() + einsatz);
                ausgabeKartenSpieler(getSpieler(), getDealer());
                setKonto(2);
                gewonnen(2);
            } else if (getDealer().aktuelleWertung() == getSpieler().aktuelleWertung()) {
                controller.setAusgabe("Es ist ein Unentschieden. Der Dealer zog mit " + getDealer().aktuelleWertung()
                        + " Punkten gleich.");
                getDealer().setGeld(getDealer().getGeld() + einsatz);
                ausgabeKartenSpieler(getSpieler(), getDealer());

                gewonnen(2);

            } else {
                controller.setAusgabe("Du hast gewonnen! " + getSpieler().aktuelleWertung()
                        + " zu " + getDealer().aktuelleWertung() + "!");
                getSpieler().setGeld(getSpieler().getGeld() + einsatz);
                ausgabeKartenSpieler(getSpieler(), getDealer());
                setKonto(1);
                gewonnen(1);
            }
            ausgabeKartenSpieler(getSpieler(), getDealer());
            kontoDaten(getSpieler(), getDealer());
            einsatz = 0;
            spiellaeuft = true;
        }

    }

    public void dealerIstDran() {

        int i = 7;
        while ((getDealer().aktuelleWertung() <= 16) && (dealer.getAnzahlKarten() < 5)) {
            Karte k = kartenSpiel.gibEineKarte();

            controller.addCard("/BlackJack_Karten/" + k.Karte2String() + ".png", i);
            neueKarteDealer(k);
            i++;
        }
    }

    public void erhoeheEinsatz(int wert) {
        if (0 < spieler.getGeld() - wert) {
            if (dealer.getGeld() - 50 >= 0) {
                dealer.setGeld(dealer.getGeld() - 50);
                // einsatz += 50;
            } else {
                controller.setAusgabe("WOW!!! DU HAST DIE BANK PLEITE GEMACHT! GRATULATION!!");

            }

            if (spieler.getGeld() - 50 >= 0) {
                spieler.setGeld(spieler.getGeld() - 50);
                einsatz += wert;
                controller.setEinsatz(Integer.toString(einsatz));

            }

        } else {
            controller.setAusgabe("Sie haben nicht genügend Geld auf dem Konto.");
            controller.setBalance("0");
        }

    }

    private void ausgabeKartenSpieler(Spieler s, Spieler d) {

        int wertung = s.aktuelleWertung();
        System.out.println();
        System.out.print("Du erhaelst: ");
        for (int i = 0; i < s.getAnzahlKarten(); i++) {
            Karte karte = s.getKarte(i);
            System.out.print(karte.Karte2String() + " ");
        }
        controller.setWertungSpieler(String.valueOf(s.aktuelleWertung()));
        System.out.println("(Wertung=" + wertung + ")");

        System.out.print("Der Dealer erhaelt: ");
        for (int i = 0; i < d.getAnzahlKarten(); i++) {
            Karte karte = d.getKarte(i);
            System.out.print(karte.Karte2String() + " ");
        }
        System.out.println("(Wertung=" + d.aktuelleWertung() + ")");
        //controller.setWertungDealer(String.valueOf(d.aktuelleWertung()));
        System.out.println();
    }

    public void setKonto(int fall) {
        if (balance > 0) {
            int wert = getEinsatz();
            switch (fall) {
                case 1:
                    balance = balance + wert;

                    controller.setBalance(Integer.toString(balance));
                    setGewinnCounter(getEinsatz());
                    break;
                case 2:
                    balance = balance - wert;

                    controller.setBalance(Integer.toString(balance));
                    setVerlustCounter(wert);
                    break;
                case 3:
                    balance = balance + (wert * 3);

                    controller.setBalance(Integer.toString(balance));
                    setGewinnCounter(getEinsatz() * 3);
                    break;
                case 4:
                    balance = balance + (versicherung * 2);

                    controller.setBalance(Integer.toString(balance));
                    setGewinnCounter(versicherung * 2);
                    break;
                case 5:
                    balance = balance - versicherung;

                    controller.setBalance(Integer.toString(balance));
                    setVerlustCounter(versicherung);
                    break;

            }
        } else {

            controller.setAusgabe("Sie sind Pleite!");
            spiellaeuft = false;
        }
    }

    public void gewonnen(int z) {

        controller.setNeuesSpielVisible();
        //controller.dropCards();
        switch (z) {
            case 1:
                setSpieleGewonnen(1);
                controller.btnDisable();
                controller.setWertungDealer(String.valueOf(dealer.aktuelleWertung()));
                controller.setzeImageView(getVerdeckt().Karte2String());
                break;
            case 2:
                setSpieleVerloren(1);
                controller.btnDisable();
                controller.setWertungDealer(String.valueOf(dealer.aktuelleWertung()));
                controller.setzeImageView(getVerdeckt().Karte2String());
                break;
        }

    }

    public void versichern() {

        if (dealer.aktuelleWertung() == 21) {

            setKonto(4);

        } else {

            setKonto(5);

        }

    }

    public void verdoppeln() {

        erhoeheEinsatz(einsatz);
        naechsteKarte();
        naechsterSpieler();

    }

    public void neueKarte(Karte karte) {
        spieler.addKarte(karte);
    }

    public void neueKarteDealer(Karte karte) {
        dealer.addKarte(karte);
    }

    private static void kontoDaten(Spieler s, Spieler d) {
        System.out.println();
        System.out.println("$$$ " + s.getName() + ": " + s.getGeld() + ", Bank: " + d.getGeld() + " $$$");
        System.out.println();
    }

    // get-set-Funktionen
    public Spieler getSpieler() {
        return spieler;
    }

    public Spieler getDealer() {
        return dealer;
    }

    public int getEinsatz() {
        return einsatz;
    }

    public boolean getSpielStatus() {
        return spiellaeuft;
    }

    public Karte getVerdeckt() {
        return verdeckt;
    }

    public void setVerdeckt(Karte verdeckt) {
        this.verdeckt = verdeckt;
    }

    public boolean isGesetzt() {
        return gesetzt;
    }

    public void setSpieleCounter(int SpieleCounter) {
        this.SpieleCounter += SpieleCounter;
    }

    public void setSpieleGewonnen(int SpieleGewonnen) {
        this.SpieleGewonnen += SpieleGewonnen;
    }

    public void setSpieleVerloren(int SpieleVerloren) {
        this.SpieleVerloren += SpieleVerloren;
    }

    public void setGewinnCounter(int GewinnCounter) {
        this.GewinnCounter += GewinnCounter;
    }

    public void setVerlustCounter(int VerlustCounter) {
        this.VerlustCounter += VerlustCounter;
    }

    public String getName() {
        return name;
    }

    public int getGewinnCounter() {
        return GewinnCounter;
    }

    public int getVerlustCounter() {
        return VerlustCounter;
    }

    public int getBalance() {
        return balance;
    }

    public void userBalance() {
        try {
            user.setUserBalance(getBalance());
            stats.setStats(einsatz, getGewinnCounter(), getVerlustCounter());
            System.out.println(einsatz);
            System.out.println(getGewinnCounter());
            System.out.println(getVerlustCounter());

        } catch (SQLException ex) {
            Logger.getLogger(BlackJack.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setEinsatz(int einsatz) {
        this.einsatz = einsatz;
    }
    

}
