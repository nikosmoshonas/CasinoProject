/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.casino.gruppe7.baccarat.Start;


import java.util.ArrayList;
import java.util.Collections;


/**
 *
 * @author Baeumli
 */
public class Game {

    //Variables
    private int playerSum, bankerSum;
    boolean playerWin, bankerWin;
    private Card playerCard1, playerCard2, playerCard3;
    private Card bankerCard1, bankerCard2, bankerCard3;
    private ArrayList<Card> stock, deck;
    private int totalEarnings, totalNaturalPlayer, totalWinsPlayer, totalNaturalBanker, totalWinsBanker, totalTies;

    Hand playerHand = new Hand();
    Hand bankerHand = new Hand();
    Bank stats = new Bank();
    
    public void createStock() {
        Deck deckObj = new Deck();
        deck = deckObj.createDeck();
        stock = new ArrayList<>(deck);
    }
   
    public void dealTwoCards() {

        playerWin = false;
        bankerWin = false;

        if (!stock.isEmpty()) {

            Collections.shuffle(stock);

            playerCard1 = stock.remove(0);
            playerCard2 = stock.remove(0);
            bankerCard1 = stock.remove(0);
            bankerCard2 = stock.remove(0);
            playerCard3 = stock.remove(0);
            bankerCard3 = stock.remove(0);

            playerHand.addCard(playerCard1);
            playerHand.addCard(playerCard2);
            bankerHand.addCard(bankerCard1);
            bankerHand.addCard(bankerCard2);

        } else {
            Dialog dialog = new Dialog();
            dialog.showInfoEmptyStockpile();
        }
    }

    public void compareTwoCards() {
        
        int playerTotal = playerHand.getSum(playerSum);
        int bankerTotal = bankerHand.getSum(bankerSum);
        
        if (playerTotal == 9 && bankerTotal == 9 || playerTotal == 8 && bankerTotal == 8) {
            stats.setTotalTies(totalTies++);
            Dialog dialog = new Dialog();
            dialog.showInfoTie();
        } else if (playerTotal == 8 || playerTotal == 9) {
            playerWin = true;
            stats.setTotalWinsPlayer(totalWinsPlayer++);
            stats.setTotalNaturalPlayer(totalNaturalPlayer++);
            Dialog dialog = new Dialog();
            dialog.showInfoPlayerWinNatural();
        } else if (bankerTotal == 8 || bankerTotal == 9) {
            bankerWin = true;
            stats.setTotalWinsBanker(totalWinsBanker++);
            stats.setTotalNaturalBanker(totalNaturalBanker++);
            Dialog dialog = new Dialog();
            dialog.showInfoBankerWinNatural();
        } else {
            dealThirdCardPlayer();
        }
    }

    public boolean playerGetsThirdCard() {

        int playerTotal = playerHand.getSum(playerSum);

        if (!playerWin && !bankerWin) {
            if (playerTotal != 6 || playerTotal != 7) {
                return true;
            }
        }
        return false;
    }

    //Dealing the third card for the player
    public void dealThirdCardPlayer() {
        if (playerGetsThirdCard()) {
                playerHand.addCard(playerCard3);
            }
        }

    public boolean bankerGetsThirdCard() {
        
        int bankerTotal = bankerHand.getSum(bankerSum);
        int playerCard3Value = playerCard3.getRank().getValue();
        
        if (!playerWin && !bankerWin) {

            if (bankerTotal <= 2) {
                return true;
            } else if (bankerTotal == 3 && playerCard3Value != 8) {
                return true;
            } else if (bankerTotal == 4 && playerCard3Value >= 2 && playerCard3Value <= 7) {
                return true;
            } else if (bankerTotal == 5 && playerCard3Value >= 4 && playerCard3Value <= 7) {
                return true;
            } else if (bankerTotal == 6 && playerCard3Value >= 6 && playerCard3Value <= 7) {
                return true;
            }
        }
        return false;
    }

    public void dealThirdCardBanker() {
        if (bankerGetsThirdCard()) {
            bankerHand.addCard(bankerCard3);
        }
        if (!playerWin && !bankerWin) {
            compareAllCards();
        }

    }

    //Final comparison of all the cards
    public void compareAllCards() {
        
        int playerTotal = playerHand.getSum(playerSum);
        int bankerTotal = bankerHand.getSum(bankerSum);
        
        int closer = Math.abs(9 - playerTotal) < Math.abs(9 - bankerTotal) ? playerTotal : bankerTotal;
        if (playerTotal == bankerTotal) {
            stats.setTotalTies(totalTies++);
            Dialog dialog = new Dialog();
            dialog.showInfoTie();
        } else if (closer == playerTotal) {
            stats.setTotalWinsPlayer(totalWinsPlayer++);
            playerWin = true;
            Dialog dialog = new Dialog();
            dialog.showInfoPlayerWin();
        } else {
            stats.setTotalWinsBanker(totalWinsBanker++);
            bankerWin = true;
            Dialog dialog = new Dialog();
            dialog.showInfoBankerWin();
        }
    }

    public Card getPlayerCard1() {
        return playerCard1;
    }

    public Card getPlayerCard2() {
        return playerCard2;
    }

    public Card getPlayerCard3() {
        return playerCard3;
    }

    public Card getBankerCard1() {
        return bankerCard1;
    }

    public Card getBankerCard2() {
        return bankerCard2;
    }

    public Card getBankerCard3() {
        return bankerCard3;
    }

    public boolean isPlayerWin() {
        return playerWin;
    }

    public boolean isBankerWin() {
        return bankerWin;
    }

    public int getPlayerSum() {
        return playerSum;
    }

    public int getBankerSum() {
        return bankerSum;
    }

}
