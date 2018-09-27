/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.casino.gruppe7.bingo;

import ch.bbbaden.casino.gruppe7.MainApp;
import ch.bbbaden.casino.gruppe7.UserLoggedIn;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javax.swing.text.View;

/**
 * FXML Controller class
 *
 * @author Pavicic
 */
public class YouLoseWinController implements Initializable {

    @FXML
    private ImageView quit;
    @FXML
    private ImageView playAgainButton;
    @FXML
    private Label winLabel;
    private UserLoggedIn uli = UserLoggedIn.getInstance();
    private BingoController bc = new BingoController();
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
           // winLabel.setText("You won: " + (bc.getBots().size() * 100));
            
//        try {
//            uli.setUserBalance(uli.getUserBalance() + (bc.getBots().size() * 100));
//        } catch (SQLException ex) {
//            Logger.getLogger(YouLoseWinController.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }

    @FXML
    private void playAgain(MouseEvent event) throws IOException, SQLException {
        // Setzt neue View
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Bingo.fxml"));
        //Erstellen einer Scene mittels der neuen View
        Scene scene = new Scene(root);
        // Holt das Stage-Objekt von der Start-Methode
        Stage stage = MainApp.getStage();
        // Setzt die Scene
        stage.setScene(scene);
        // Öffnet das neue Bild
        stage.show();
        ImageView img = (ImageView) event.getSource();
        img.getScene().getWindow().hide();
        bc.konto = uli.getUserBalance();
        
    }

    @FXML
    private void onExitedQuit(MouseEvent event) {
        ((ImageView) event.getSource()).setImage(new Image("/bingo_img/YouWinScreenQuitButton.png"));

    }

    @FXML
    private void onEnteredQuit(MouseEvent event) {
        ((ImageView) event.getSource()).setImage(new Image("/bingo_img/YouWinScreenQuitButtonPressed.png"));

    }

    @FXML
    private void quit(MouseEvent event) throws IOException, SQLException {
         // Setzt neue View
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/SelectGame.fxml"));
        //Erstellen einer Scene mittels der neuen View
        Scene scene = new Scene(root);
        // Holt das Stage-Objekt von der Start-Methode
        Stage stage = MainApp.getStage();
        // Setzt die Scene
        stage.setScene(scene);
        // Öffnet das neue Bild
        stage.show();
        ImageView img = (ImageView) event.getSource();
        img.getScene().getWindow().hide();
        bc.konto = uli.getUserBalance();
        

    }

    @FXML
    private void onExited(MouseEvent event) {
        ((ImageView) event.getSource()).setImage(new Image("/bingo_img/PlayAgainButton.png"));

    }

    @FXML
    private void onEntered(MouseEvent event) {
        ((ImageView) event.getSource()).setImage(new Image("/bingo_img/PlayAgainButtonPressed.png"));

    }

}
