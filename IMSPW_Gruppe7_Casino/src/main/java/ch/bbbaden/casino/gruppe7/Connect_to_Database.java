/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.casino.gruppe7;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Nikos
 */
public class Connect_to_Database {

    Connection myConn1;
    PreparedStatement preparedStmt;
    public ResultSet rs;

    public void con() throws SQLException {
        myConn1 = DriverManager.getConnection("jdbc:mysql://famschindelholz.internet-box.ch:27022/Casino", "Casino", "casino7");
    }

    public void prepared(String query) throws SQLException {
        preparedStmt = myConn1.prepareStatement(query);
    }

    public void setString(int i, String x) throws SQLException {
        preparedStmt.setString(i, x);
    }

    public void executeQuerry() throws SQLException {

        preparedStmt.executeQuery();
    }

    public void execute() throws SQLException {
        preparedStmt.execute();
    }

    public void executeupdate() throws SQLException {
        preparedStmt.executeUpdate();
    }

    public void closeprepared() throws SQLException {
        preparedStmt.close();
    }

    public void closeconn() throws SQLException {
        myConn1.close();
    }

    public void result() throws SQLException {
        rs = preparedStmt.executeQuery();
    }

    public String getResult() throws SQLException {
//        ResultSetMetaData md = rs.getMetaData();
//        int columns = md.getColumnCount();
//        ArrayList list = new ArrayList(50);
//        while (rs.next()) {
//            HashMap row = new HashMap(columns);
//            for (int i = 1; i <= columns; ++i) {
//                row.put(md.getColumnName(i), rs.getObject(i));
//            }
//            list.add(row);
//        }
//        return list;

        rs.next();
        String sum = rs.getString(1);
        
        return sum;
    }

}
