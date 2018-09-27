/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.casino.gruppe7.yatzy_game;

import ch.bbbaden.casino.gruppe7.UserLoggedIn;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;
import javafx.scene.text.Text;
import javax.swing.JOptionPane;

/**
 *
 * @author Nikos
 */
public class AlertClass {

    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Scene.fxml"));

    FXMLController c = loader.getController();

    UserLoggedIn logIN = UserLoggedIn.getInstance();

    boolean checkifselectedcorrect = true;
    boolean throwcheck = true;
    Alert alert = new Alert(Alert.AlertType.WARNING);

    Optional<String> result;

    public String getResult() {
        return result.get();
    }

    public void alert() {

        if (checkifselectedcorrect) {

            alert.setTitle("Wrong selection");
            alert.setContentText("You are a Looser");
            alert.showAndWait();
            checkifselectedcorrect = false;
        }
    }

    public void setCheckifselectedcorrect(boolean b) {
        checkifselectedcorrect = b;

    }

    public void checkAlert() {

        alert.setTitle("You can't check yet");
        alert.setContentText("Please roll the Dice");
        alert.showAndWait();
        checkifselectedcorrect = false;

    }

    public void winAlert() {

        alert.setTitle("Win");
        alert.setContentText("Player 1 Wins");
        alert.showAndWait();
    }

    public void winAlert2() {
        alert.setTitle("Win");
        alert.setContentText("Player 2 Wins");
        alert.showAndWait();

    }

    public void winAlertBoth() {
        alert.setTitle("Win");
        alert.setContentText("You have the same score");
        alert.showAndWait();

    }

    public void inputAlert() {
        List<String> choices = new ArrayList<>();
        choices.add("2");
        choices.add("10");
        choices.add("50");
        choices.add("100");
        choices.add("500");
        choices.add("1000");

        ChoiceDialog<String> dialog = new ChoiceDialog<>("2", choices);
        dialog.setTitle("Choice Dialog");
        dialog.setHeaderText("Set your Amount");
        dialog.setContentText("Your amount: ");

// Traditional way to get the response value.
        result = dialog.showAndWait();
        if (result.isPresent()) {
            System.out.println("Your choice: " + result.get());

        }

    }

    public void quitorcontinueButton(Boolean bool) {
        
         alert.setTitle("Choose");
        alert.setAlertType(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Decide if you want to play or quit.");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK && bool == true) {

        } else if (result.get() == ButtonType.CANCEL) {

            if (result.get() == ButtonType.OK) {

            } else if (result.get() == ButtonType.CANCEL) {

                System.exit(0);
            }

        }
        
    }

    public void depositAlert(Text l) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Choice Dialog");
        dialog.setHeaderText("Enter your deposit");
        dialog.setContentText("Your amount: ");

        result = dialog.showAndWait();
        try {
            if (result.isPresent() && Integer.parseInt(result.get().toString()) > 0) {
                logIN.setUserBalance((logIN.getUserBalance() + Integer.parseInt(result.get().toString())));
                l.setText(String.valueOf(logIN.getUserBalance()));
            } else {
                throw new IllegalArgumentException();
            }
        } catch (Exception e) {
            depositErrorAlert(l);
        }
    }

    public void depositErrorAlert(Text l) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("DEPOSIT ERROR");
        alert.setHeaderText(null);
        alert.setContentText("Oops! Something went wrong.");
        alert.showAndWait();
        depositAlert(l);
    }

  
}
