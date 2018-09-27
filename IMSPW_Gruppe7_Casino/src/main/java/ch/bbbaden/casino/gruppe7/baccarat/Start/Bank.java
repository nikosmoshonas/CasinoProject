/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.casino.gruppe7.baccarat.Start;

/**
 *
 * @author lbaum
 */
public class Bank {

    private int bet;
    private int WinLose;
    private String betOn = "";
    private int balance = 2000;
    private int totalEarnings, totalNaturalPlayer, totalWinsPlayer, totalNaturalBanker, totalWinsBanker, totalTies;

    public void payPlayer(Game game) {

        int payoutTie = bet * 8;
        int payoutWin = bet * 3 / 2;

        if (betOn.equals("Tie") && game.isPlayerWin() == false && game.isBankerWin() == false) { // Tie Win
            WinLose = payoutTie;
            balance = balance + payoutTie;
        } else if (!betOn.equals("Player") && !game.isPlayerWin() && !game.isBankerWin()) { // <-- Tie
            WinLose = 0;
            balance = balance + bet;
        } else if (!betOn.equals("Banker") && !game.isPlayerWin() && !game.isBankerWin()) { // <-- Tie
            WinLose = 0;
            balance = balance + bet;
        } else if (betOn.equals("Player") && game.isPlayerWin()) { // <-- Player Win
            WinLose = payoutWin;
            balance = balance + payoutWin;
        } else if (betOn.equals("Banker") && game.isBankerWin()) { // <-- Banker Win
            balance = balance + payoutWin;
        } else { // <-- Lose
            WinLose = bet;
            bet = 0;
        }

    }

    public int getWinLose() {
        return WinLose;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    public int getBet() {
        return bet;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public String getBetOn() {
        return betOn;
    }

    public void setBetOn(String betOn) {
        this.betOn = betOn;
    }

    public int getTotalEarnings() {
        return totalEarnings;
    }

    public void setTotalEarnings(int totalEarnings) {
        this.totalEarnings = totalEarnings;
    }

    public int getTotalNaturalPlayer() {
        return totalNaturalPlayer;
    }

    public void setTotalNaturalPlayer(int totalNaturalPlayer) {
        this.totalNaturalPlayer = totalNaturalPlayer;
    }

    public int getTotalWinsPlayer() {
        return totalWinsPlayer;
    }

    public void setTotalWinsPlayer(int totalWinsPlayer) {
        this.totalWinsPlayer = totalWinsPlayer;
    }

    public int getTotalNaturalBanker() {
        return totalNaturalBanker;
    }

    public void setTotalNaturalBanker(int totalNaturalBanker) {
        this.totalNaturalBanker = totalNaturalBanker;
    }

    public int getTotalWinsBanker() {
        return totalWinsBanker;
    }

    public void setTotalWinsBanker(int totalWinsBanker) {
        this.totalWinsBanker = totalWinsBanker;
    }

    public int getTotalTies() {
        return totalTies;
    }

    public void setTotalTies(int totalTies) {
        this.totalTies = totalTies;
    }

}
