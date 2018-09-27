package ch.bbbaden.casino.gruppe7;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import ch.bbbaden.casino.gruppe7.yatzy_game.AlertClass;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Screen;

/**
 * FXML Controller class
 *
 * @author Nikos
 */
public class SelectGameController implements Initializable {

    @FXML
    private Text userBalance;
    private UserLoggedIn userloggedin = UserLoggedIn.getInstance();
    AlertClass alert = new AlertClass();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            userBalance.setText(String.valueOf(userloggedin.getUserBalance()));
        } catch (SQLException ex) {
            Logger.getLogger(SelectGameController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void blackjackEx(MouseEvent event) {
        ((ImageView) event.getSource()).setImage(new Image("/styles/assets/blackjack.png"));
    }

    @FXML
    public void blackjackEn(MouseEvent event) {
        ((ImageView) event.getSource()).setImage(new Image("/styles/assets/blackjack2.png"));

    }

    @FXML
    private void handleblackjack(MouseEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Blackjack_Scene.fxml"));
        Scene scene = new Scene(root, 1600, 900);
        MainApp.getStage().setScene(scene);
        // NEW EDIT 15:57 FLORIAN SCHÄFER
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        MainApp.getStage().setX((screenBounds.getWidth() - 1600) / 2);
        MainApp.getStage().setY((screenBounds.getHeight() - 900) / 2);
        MainApp.getStage().centerOnScreen();

    }

    @FXML
    private void bingoEx(MouseEvent event) {
        ((ImageView) event.getSource()).setImage(new Image("/styles/assets/bingo.png"));
    }

    @FXML
    private void bingoEn(MouseEvent event) {
        ((ImageView) event.getSource()).setImage(new Image("/styles/assets/bingo2.png"));
    }

    @FXML
    private void handlebingo(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Bingo_Welcome.fxml"));
        Scene scene = new Scene(root, 1600, 900);
        MainApp.getStage().setScene(scene);
        // NEW EDIT 15:57 FLORIAN SCHÄFER
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        MainApp.getStage().setX((screenBounds.getWidth() - 1600) / 2);
        MainApp.getStage().setY((screenBounds.getHeight() - 900) / 2);
        MainApp.getStage().centerOnScreen();
    }

    @FXML
    private void yatzyEx(MouseEvent event) {
        ((ImageView) event.getSource()).setImage(new Image("/styles/assets/yatzy.png"));
    }

    @FXML
    private void yatzyEn(MouseEvent event) {
        ((ImageView) event.getSource()).setImage(new Image("/styles/assets/yatzy2.png"));
    }

    @FXML
    private void handleyatzy(MouseEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Yatzy.fxml"));
        Scene scene = new Scene(root, 1600, 900);
        MainApp.getStage().setScene(scene);
        // NEW EDIT 15:57 FLORIAN SCHÄFER
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        MainApp.getStage().setX((screenBounds.getWidth() - 1600) / 2);
        MainApp.getStage().setY((screenBounds.getHeight() - 900) / 2);
        MainApp.getStage().centerOnScreen();

    }

    @FXML
    private void rouletteEx(MouseEvent event) {
        ((ImageView) event.getSource()).setImage(new Image("/styles/assets/roulette.png"));
    }

    @FXML
    private void rouletteEn(MouseEvent event) {
        ((ImageView) event.getSource()).setImage(new Image("/styles/assets/roulette2.png"));
    }

    @FXML
    private void handleroulette(MouseEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Roulette.fxml"));
        Scene scene = new Scene(root, 1920, 900);
        MainApp.getStage().setScene(scene);
        // NEW EDIT 15:57 FLORIAN SCHÄFER
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        MainApp.getStage().setX((screenBounds.getWidth() - 1920) / 2);
        MainApp.getStage().setY((screenBounds.getHeight() - 900) / 2);
        MainApp.getStage().centerOnScreen();
    }

    @FXML
    private void baccaraEx(MouseEvent event) {
        ((ImageView) event.getSource()).setImage(new Image("/styles/assets/baccara.png"));
    }

    @FXML
    private void baccaraEn(MouseEvent event) {
        ((ImageView) event.getSource()).setImage(new Image("/styles/assets/baccara2.png"));
    }

    @FXML
    private void handlebaccara(MouseEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Baccarat_Scene.fxml"));
        Scene scene = new Scene(root, 1600, 900);
        MainApp.getStage().setScene(scene);
        // NEW EDIT 15:57 FLORIAN SCHÄFER
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        MainApp.getStage().setX((screenBounds.getWidth() - 1600) / 2);
        MainApp.getStage().setY((screenBounds.getHeight() - 900) / 2);
        MainApp.getStage().centerOnScreen();

    }

    @FXML
    private void backEx(MouseEvent event) {
        ((ImageView) event.getSource()).setImage(new Image("/styles/assets/backarrow.png"));
    }

    @FXML
    private void backEn(MouseEvent event) {
        ((ImageView) event.getSource()).setImage(new Image("/styles/assets/backarrow2.png"));
    }

    @FXML
    private void backArrow(MouseEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Scene.fxml"));
        Scene scene = new Scene(root);
        MainApp.getStage().setScene(scene);
    }

    @FXML
    private void statsEx(MouseEvent event) {
        ((ImageView) event.getSource()).setImage(new Image("/styles/assets/stats.png"));
    }

    @FXML
    private void statsEn(MouseEvent event) {
        ((ImageView) event.getSource()).setImage(new Image("/styles/assets/stats2.png"));
    }

    @FXML
    private void statsIcn(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Stats.fxml"));
        Scene scene = new Scene(root);
        MainApp.getStage().setScene(scene);
    }

    @FXML
    private void plusEx(MouseEvent event) {
        ((ImageView) event.getSource()).setImage(new Image("/styles/assets/plus.png"));
    }

    @FXML
    private void plusEn(MouseEvent event) {
        ((ImageView) event.getSource()).setImage(new Image("/styles/assets/plus2.png"));
    }

    @FXML
    private void plusIcn(MouseEvent event) throws IOException {
        alert.depositAlert(userBalance);
    }
}
