/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.casino.gruppe7.baccarat.Start;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author lbaum
 */
public class Dialog implements DialogInterface {

    /*----------ADD ALERTS HERE----------*/
    
    //Type
    Alert info = new Alert(Alert.AlertType.INFORMATION);
    Alert error = new Alert(Alert.AlertType.ERROR);
    Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
    Alert warning = new Alert(Alert.AlertType.WARNING);

    //Alert
    @Override
    public void showInfoPlayerWin() {
        info.setTitle("Baccara");
        info.setContentText("Player Wins!");
        info.show();
    }

    @Override
    public void showInfoBankerWin() {
        info.setTitle("Baccara");
        info.setContentText("Banker Wins!");
        info.show();
    }

    @Override
    public void showInfoTie() {
        info.setTitle("Baccara");
        info.setContentText("Tie!");
        info.show();
    }

    @Override
    public void showInfoPlayerWinNatural() {
        info.setTitle("Baccara");
        info.setContentText("Natural! Player Wins!");
        info.show();
    }

    @Override
    public void showInfoBankerWinNatural() {
        info.setTitle("Baccara");
        info.setContentText("Natural! Banker Wins!");
        info.show();
    }

    @Override
    public void showInfoInsufficentBalance() {
        info.setTitle("Baccara");
        info.setContentText("Insufficent Balance.");
        info.show();
    }

    @Override
    public void showInfoEnterBet() {
        info.setTitle("Baccara");
        info.setContentText("Please enter the amount you want to bet.");
        info.show();
    }

    @Override
    public void showErrorMismatch() {
        error.setTitle("Baccara");
        error.setContentText("Invalid Input.");
        error.show();
    }

    @Override
    public void showErrorInvalid() {
        error.setTitle("Baccara");
        error.setContentText("Invalid Input.");
        error.show();
    }

    @Override
    public void showInfoEmptyStockpile() {
        info.setTitle("Baccara");
        info.setContentText("The Stockpile is empty.");
        info.show();
    }
    
    @Override
    public void showInfoNoBetOn() {
        info.setTitle("Baccara");
        info.setContentText("Please choose the Person you want to bet on.");
        info.show();
    }
    
    @Override
    public boolean showWarningExit() {
        confirm.setTitle("Baccara");
        confirm.setContentText("If you exit now, you will lose your current bet");

        Optional<ButtonType> result = confirm.showAndWait();
        if (result.get() == ButtonType.OK) {
            return true;
        } else if (result.get() == ButtonType.CANCEL) {
            return false;
        }
        return false;
    }

}
