/*
 Methode für de User wo a gmelded isch
 */
package ch.bbbaden.casino.gruppe7;

import java.sql.SQLException;
import java.util.ArrayList;

public final class UserLoggedIn {

    private static UserLoggedIn userloggedin = new UserLoggedIn();

    private Connect_to_Database con = new Connect_to_Database();
    private ArrayList<String> arr;

    private String username;

    public int getUserBalance() throws SQLException {
        arr = new ArrayList<>();
        con.con();
        con.prepared("select balance from Balance where username = '" + username + "'");
        con.result();
        while (con.rs.next()) {
            arr.add(con.rs.getString("balance"));
        }

        con.closeprepared();
        con.closeconn();
        return Integer.parseInt(arr.get(0));
    }

    public void setUserBalance(int balance) throws SQLException {
        con.con();
        con.prepared("UPDATE Balance SET balance = '" + balance + "' WHERE username = '" + username + "'");
        con.execute();
        con.closeconn();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public static UserLoggedIn getInstance() {
        if (userloggedin == null) {
            userloggedin = new UserLoggedIn();
        }
        return userloggedin;
    }
}
