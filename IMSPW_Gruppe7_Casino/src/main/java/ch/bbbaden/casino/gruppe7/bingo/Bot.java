/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.casino.gruppe7.bingo;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Pavicic
 */
public class Bot {

    Random rand = new Random();

    int botCardArray[][] = new int[5][5];
    int cooldown = 15;
    boolean bingo = false;

    public Bot() {

        int min = 1;
        int randomNumber;
        ArrayList<Integer> currentCol = new ArrayList<>();

        //Generate Card for bot
        for (int i = 0; i < botCardArray.length; i++) {
            for (int j = 0; j < botCardArray[i].length; j++) {
                if (i != 2 || j != 2) {
                    do {
                        randomNumber = rand.nextInt((min + 14) - min) + min;    //generate numbers in a specific range
                    } while (currentCol.contains(randomNumber));
                    currentCol.add(randomNumber);
                    botCardArray[i][j] = randomNumber;
                }

            }
            min += 15;
            currentCol.clear();
        }
    }

    public boolean botChecker(ArrayList<Integer> drawnNumberArray) {

        boolean valid = true;
        
        for (int i = 0; i < 5; i++) {
            valid = true;
            for (int j = 0; j < 5; j++) {
                if (i != 2 || j != 2) {
                    if (!(drawnNumberArray.contains(botCardArray[j][i]))) {
                        valid = false;
                        break;
                    }
                }
            }
            if (valid) {
                break;
            }
        }

        if (!valid) {
            for (int i = 0; i < 5; i++) {
                valid = true;
                for (int j = 0; j < 5; j++) {
                    if (i != 2 || j != 2) {
                        if (!(drawnNumberArray.contains(botCardArray[i][j]))) {
                            valid = false;
                            break;
                        }
                    }
                }
                if (valid) {
                    break;
                }
            }
        }

        if (!valid) {
            valid = true;
            for (int j = 0; j < 5; j++) {
                if (j != 2) {
                    if (!(drawnNumberArray.contains(botCardArray[j][j]))) {
                        valid = false;
                        break;
                    }
                }
            }
        }

        if (!valid) {
            valid = true;
            for (int j = 0; j < 5; j++) {
                if (j != 2) {
                    if (!(drawnNumberArray.contains(botCardArray[j][4 - j]))) {
                        valid = false;
                        break;
                    }
                }
            }
        }

        return valid;
    }

    public boolean checkCooldown() {
        if (cooldown > 0) {
            cooldown--;
            return false;
        } else {
            return true;
        }
    }

    public boolean isBingo() {
        return bingo;
    }

    public void setBingo(boolean bingo) {
        this.bingo = bingo;
    }

}
