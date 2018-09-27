/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.casino.gruppe7;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Tim
 */
public class Stats {

    private UserLoggedIn userloggedin = UserLoggedIn.getInstance();
    private ArrayList<String> arr = new ArrayList<>();
    Connect_to_Database con = new Connect_to_Database();
    public String game;

    public Stats(String game) {
        this.game = game;
    }

    public Stats() {

    }

    public void setStats(int set, int won, int lost) throws SQLException {
        con.con();
        con.prepared("insert into Games (username,game,bet,won,lost) values (?,?,?,?,?)");
        con.setString(1, userloggedin.getUsername());
        con.setString(2, game);
        con.setString(3, "" + set);
        con.setString(4, "" + won);
        con.setString(5, "" + lost);

        con.execute();
        con.closeconn();
    }

    public int getUserTotalFromGames(String columns, String game) throws SQLException {
        con.con();
        if (userloggedin.getUsername().equals("admin")) {
            if (columns.isEmpty() == true) {
                if (game.isEmpty() == true) {
                    con.prepared("SELECT COUNT(bet) FROM Games");
                } else {
                    con.prepared("SELECT COUNT(bet) FROM Games WHERE game = '" + game + "'");
                }
            } else {
                con.prepared("SELECT SUM(" + columns + ") FROM Games");
                if (game.isEmpty() == true) {
                    con.prepared("SELECT SUM(" + columns + ") FROM Games");
                } else {
                    con.prepared("SELECT SUM(" + columns + ") FROM Games WHERE game = '" + game + "'");
                }
            }
        } else {
            if (columns.isEmpty() == true) {
                if (game.isEmpty() == true) {
                    con.prepared("SELECT COUNT(bet) FROM Games WHERE username = '" + userloggedin.getUsername() + "'");
                } else {
                    con.prepared("SELECT COUNT(bet) FROM Games WHERE username = '" + userloggedin.getUsername() + "' AND game = '" + game + "'");
                }
            } else {
                if (game.isEmpty() == true) {
                    con.prepared("SELECT SUM(" + columns + ") FROM Games WHERE username = '" + userloggedin.getUsername() + "'");
                } else {
                    con.prepared("SELECT SUM(" + columns + ") FROM Games WHERE username = '" + userloggedin.getUsername() + "' AND game = '" + game + "'");
                }
            }
        }

        con.result();
        String back = con.getResult();

        con.closeprepared();
        con.closeconn();
        int i = 0;
        try {
            i = Integer.parseInt(back);
        } catch (Exception e) {
        }

        return i;
    }
}
