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
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javax.swing.JOptionPane;

/**
 *
 * @author Nikos
 */
public class RegisterController implements Initializable {

    @FXML
    private JFXTextField usernamen;
    @FXML
    private JFXTextField email;
    @FXML
    private JFXTextField password;

    Connect_to_Database con = new Connect_to_Database();

    boolean bool = true;

    Alert alert = new Alert(Alert.AlertType.WARNING);

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void buttonclicked(ActionEvent event) throws SQLException {

    }

    @FXML
    private void click(ActionEvent event) throws SQLException, IOException {

        System.out.println("bdsjfsdhj");

        do {

            if (usernamen.getText().matches("^[a-zA-Z]{3,15}+$") && password.getText().matches("[a-zA-Z ]*\\d+.*") && email.getText().matches("^[a-zA-Z0-9_+&*-]+(?:\\."
                    + "[a-zA-Z0-9_+&*-]+)*@"
                    + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
                    + "A-Z]{2,7}$")) {

                con.con();
                con.prepared("insert into Accounts (username,email,password) values (?, ?,?)");
                con.setString(1, usernamen.getText());
                con.setString(2, email.getText());
                con.setString(3, password.getText());

                con.execute();
                con.closeconn();

                alert.setAlertType(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Registration was successful");
                alert.showAndWait();
                con.con();
                con.prepared("insert into Balance (username,balance) values (?,?)");
                con.setString(1, usernamen.getText());
                con.setString(2, String.valueOf(2000));
                con.execute();
                con.closeconn();

                usernamen.clear();
                password.clear();
                email.clear();

                Parent root = FXMLLoader.load(getClass().getResource("/fxml/Scene.fxml"));
                Scene scene = new Scene(root);
                MainApp.getStage().setScene(scene);

                bool = true;

            } else {

                alert.setAlertType(Alert.AlertType.ERROR);
                alert.setContentText("Please try again");
                alert.showAndWait();
                bool = false;
            }

        } while (bool = false);

    }

    @FXML
    private void goBackToLogin(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Scene.fxml"));
        Scene scene = new Scene(root);
        MainApp.getStage().setScene(scene);
    }

}
