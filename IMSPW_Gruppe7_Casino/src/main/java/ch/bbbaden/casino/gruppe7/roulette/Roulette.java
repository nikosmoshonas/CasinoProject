package ch.bbbaden.casino.gruppe7.roulette;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Roulette {

    private List<List<String>> m = new ArrayList<List<String>>();
    private ArrayList<String> boardarray;

    private int[][] board = new int[12][3];
    private int u = 1;
    private String defnull = null;
    private double z = 0.0;
    private int integeramount = 17;

    private int number = 0, redorblack = 1, evenorodd = 2, lowerorupper = 3, twelverow = 4, sixrow = 5, ninecolumn = 6, two1 = 7, two2 = 8, two3 = 9, two4 = 10, quad1 = 11, quad2 = 12, quad3 = 13, quad4 = 14, threerow = 15, fivenbet = 16;

    private Random random = new Random();

    public List getM() {
        return m;
    }

    public int RandomNumber() {
        int randomNum = random.nextInt(37 - 0 + 1) + 0;
        return randomNum;
    }

    public void normalboard() {
        for (int i = 0; i < board.length; i++) {  // <- inizialisierung des bretts
            for (int j = 0; j < board[0].length; j++) {
                board[i][j] = u++;
            }
        }
    }

    public void mapboard() {
        for (int i = 0; i < 1; i++) {
            boardarray = new ArrayList<String>();
            for (int j = 0; j < integeramount; j++) {
                boardarray.add("null");
            }
            boardarray.set(redorblack, "green");
            boardarray.set(integeramount - 1, "fivenbet");
            if (i == 0) {
                boardarray.set(0, "0");
            }
            m.add(boardarray);
        }
        for (int i = 0; i < board.length; i++, z += 0.5) {  // <- ersttellen des roulette vergleich bretts
            for (int j = 0; j < board[0].length; j++) {
                boardarray = new ArrayList<String>();
                for (int k = 0; k < integeramount; k++) {
                    boardarray.add("");
                }
                boardarray.set(number, "-" + board[i][j]);
                if (board[i][j] % 2 == 0) {
                    boardarray.set(evenorodd, "even");
                } else {
                    boardarray.set(evenorodd, "odd");
                }

                if (board[i][j] < 19) {
                    boardarray.set(lowerorupper, "to18");
                } else {
                    boardarray.set(lowerorupper, "to36");
                }

                if (board[i][j] < 13) {
                    boardarray.set(twelverow, "st12");
                } else if (board[i][j] < 25) {
                    boardarray.set(twelverow, "nd12");
                } else {
                    boardarray.set(twelverow, "nd12");
                }

                if (j == 0) {
                    boardarray.set(ninecolumn, "st9");
                } else if (j == 1) {
                    boardarray.set(ninecolumn, "nd9");
                } else {
                    boardarray.set(ninecolumn, "rd9");
                }

                boardarray.set(threerow, "three" + (i + 1));

                boardarray.set(sixrow, "six" + ((int) z + 1));

                switch (board[i][j]) { // <- zuweisung der "speziellen" roten & schwarzen zahlen
                    case 11:
                        boardarray.set(redorblack, "black");
                        break;
                    case 13:
                        boardarray.set(redorblack, "black");
                        break;
                    case 15:
                        boardarray.set(redorblack, "black");
                        break;
                    case 17:
                        boardarray.set(redorblack, "black");
                        break;
                    case 29:
                        boardarray.set(redorblack, "black");
                        break;
                    case 31:
                        boardarray.set(redorblack, "black");
                        break;
                    case 33:
                        boardarray.set(redorblack, "black");
                        break;
                    case 35:
                        boardarray.set(redorblack, "black");
                        break;

                    case 12:
                        boardarray.set(redorblack, "red");
                        break;
                    case 14:
                        boardarray.set(redorblack, "red");
                        break;
                    case 16:
                        boardarray.set(redorblack, "red");
                        break;
                    case 18:
                        boardarray.set(redorblack, "red");
                        break;
                    case 30:
                        boardarray.set(redorblack, "red");
                        break;
                    case 32:
                        boardarray.set(redorblack, "red");
                        break;
                    case 34:
                        boardarray.set(redorblack, "red");
                        break;
                    case 36:
                        boardarray.set(redorblack, "red");
                        break;

                    default:
                        if (board[i][j] % 2 == 0) {
                            boardarray.set(redorblack, "black");
                        } else {
                            boardarray.set(redorblack, "red");
                        }
                }

                try {
                    boardarray.set(two1, "" + board[i][j] * board[i][j] * board[i][j - 1] * board[i][j - 1]); // <- 2er bet left
                } catch (Exception e) {
                    boardarray.set(two1, "" + defnull);
                }
                try {
                    boardarray.set(two2, "" + board[i][j] * board[i][j] * board[i][j + 1] * board[i][j + 1]); // <- 2er bet right
                } catch (Exception e) {
                    boardarray.set(two2, "" + defnull);
                }
                try {
                    boardarray.set(two3, "" + board[i][j] * board[i][j] * board[i - 1][j] * board[i - 1][j]); // <- 2er bet up
                } catch (Exception e) {
                    boardarray.set(two3, "" + defnull);
                }
                try {
                    boardarray.set(two4, "" + board[i][j] * board[i][j] * board[i + 1][j] * board[i + 1][j]); // <- 2er bet down
                } catch (Exception e) {
                    boardarray.set(two4, "" + defnull);
                }

                //____________________4er bets
                try {
                    boardarray.set(quad1, "" + board[i][j] * board[i - 1][j - 1] * board[i][j - 1] * board[i - 1][j]); // <- 4er bet up left
                } catch (Exception e) {
                    boardarray.set(quad1, "" + defnull);
                }
                try {
                    boardarray.set(quad2, "" + board[i][j] * board[i - 1][j] * board[i][j + 1] * board[i - 1][j + 1]); // <- 4er bet up right
                } catch (Exception e) {
                    boardarray.set(quad2, "" + defnull);
                }
                try {
                    boardarray.set(quad3, "" + board[i][j] * board[i][j - 1] * board[i + 1][j] * board[i + 1][j - 1]); // <- 4er bet down left
                } catch (Exception e) {
                    boardarray.set(quad3, "" + defnull);
                }
                try {
                    boardarray.set(quad4, "" + board[i][j] * board[i][j + 1] * board[i + 1][j] * board[i + 1][j + 1]); // <- 4er bet down right
                } catch (Exception e) {
                    boardarray.set(quad4, "" + defnull);
                }
                if (board[i][j] == 1 || board[i][j] == 2 || board[i][j] == 3) {
                    boardarray.set(integeramount - 1, "fivenbet");
                } else {
                    boardarray.set(integeramount - 1, "" + defnull);
                }
                m.add(boardarray);
            }
        }
        boardarray = new ArrayList<String>();
        for (int j = 0; j < integeramount; j++) {
            boardarray.add("null");
        }
        boardarray.set(redorblack, "green");
        boardarray.set(integeramount - 1, "fivenbet");

        boardarray.set(0, "37");

        m.add(boardarray);
    }
}
