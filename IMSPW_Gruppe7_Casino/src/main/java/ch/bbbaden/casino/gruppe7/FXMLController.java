/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.casino.gruppe7;

import com.jfoenix.controls.JFXTextField;
import ch.bbbaden.casino.gruppe7.Connect_to_Database;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javax.swing.JOptionPane;

/**
 *
 * @author Nikos
 */
public class FXMLController implements Initializable {
    
    UserLoggedIn userloggedin = UserLoggedIn.getInstance();

    @FXML
    private Label label;

 
    Connect_to_Database con = new Connect_to_Database();
    @FXML
    private AnchorPane pani;

    Alert alert = new Alert(Alert.AlertType.WARNING);
    @FXML
    private JFXTextField usernamen;
    @FXML
    private JFXTextField password;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    public void buttonclicked(ActionEvent event) throws SQLException {

        ArrayList<String> arr = new ArrayList<>();
        Connection myConn1;

        myConn1 = DriverManager.getConnection("jdbc:mysql://famschindelholz.internet-box.ch:27022/Casino", "Casino", "casino7");
        boolean bool = true;
        Statement statement = myConn1.createStatement();
        ResultSet result = statement.executeQuery("select username, password from Accounts");

        ResultSetMetaData metadata = result.getMetaData();
        int columnCount = metadata.getColumnCount();
        for (int i = 1; i <= columnCount; i++) {
        }
        while (result.next()) {
            String row = "";
            for (int i = 1; i <= columnCount; i++) {
                row += result.getString(i);
                arr.add(row);
                arr = arr;
            }
        }
        System.out.println(arr);

        if (arr.contains(usernamen.getText()) && arr.contains(usernamen.getText() + password.getText())) {
            userloggedin.setUsername(usernamen.getText());
            alert.setAlertType(Alert.AlertType.CONFIRMATION);
            alert.setTitle("LOGIN");
            alert.setContentText("You logged in successfully");
            alert.showAndWait();
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/fxml/SelectGame.fxml"));
            } catch (IOException ex) {
                Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
            //SCENE EDITED
            Scene scene = new Scene(root,1280,720);
            MainApp.getStage().setScene(scene);
            MainApp.getStage().show();
            
            // NEW EDIT 15:57 FLORIAN SCHÄFER
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            MainApp.getStage().setX((screenBounds.getWidth() - 1280) / 2);
            MainApp.getStage().setY((screenBounds.getHeight() - 720) / 2);
            MainApp.getStage().centerOnScreen();

        } else {
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.setTitle("LOGIN");
            alert.setContentText("Faild!" + "\n" + "If you don't have a Login click " + "Sign up");
            alert.showAndWait();
        }
//
//        try {
//
//            do {
//
//                con.con();
//                con.prepared("Select * from login where username = ?, password = ?");
//                con.setString(1, usernamen.getText());
//                con.setString(3, password.getText());
//
//                con.result();
//
//                if (con.rs.next() || !usernamen.getText().matches("^[a-zA-Z]{3,15}+$") || !password.getText().matches("[a-zA-Z ]*\\d+.*")) {
//
//                    bool = false;
//
//                    JOptionPane.showMessageDialog(null, "exists");
//
//                    System.out.println("existiert schon");
//
//                } else {
//                   
//
//                }
//
//            } while (bool = false);
//
//        } catch (SQLException e) {
//
//            System.out.println(e);
//        }

    }

    @FXML
    private void signupClicked(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Register.fxml"));
        Scene scene = new Scene(root);
        MainApp.getStage().setScene(scene);

    }

}
