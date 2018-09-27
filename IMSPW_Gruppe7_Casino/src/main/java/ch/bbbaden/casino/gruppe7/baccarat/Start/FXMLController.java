package ch.bbbaden.casino.gruppe7.baccarat.Start;

import ch.bbbaden.casino.gruppe7.Stats;
import ch.bbbaden.casino.gruppe7.UserLoggedIn;
import ch.bbbaden.casino.gruppe7.baccarat.Start.Dialog;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javafx.util.Duration;

public class FXMLController implements Initializable {

    //FXML ImageViews
    @FXML
    private ImageView imgPlayerCard1 = new ImageView();
    @FXML
    private ImageView imgPlayerCard2 = new ImageView();
    @FXML
    private ImageView imgPlayerCard3 = new ImageView();
    @FXML
    private ImageView imgBankerCard1 = new ImageView();
    @FXML
    private ImageView imgBankerCard2 = new ImageView();
    @FXML
    private ImageView imgBankerCard3 = new ImageView();
    //FXML Labels
    @FXML
    private Label lblBet;
    @FXML
    private Label lblBalance;
    @FXML
    private Label lblSumPlayer;
    @FXML
    private Label lblSumBanker;
    //FXML Buttons
    @FXML
    private Button btnBetPlayer;
    @FXML
    private Button btnBetBanker;
    @FXML
    private Button btnBetTie;

    //Variables
    private int bet, balance;

    //Objects
    Dialog dialog = new Dialog();
    Bank bank = new Bank();
    UserLoggedIn lg = UserLoggedIn.getInstance();
    Stats st = new Stats("baccarat");

    @FXML
    void btnReturn(ActionEvent event) throws IOException {
        if (dialog.showWarningExit()) {
            balance = balance - bet;
            lblBet.setText("0");
            System.out.println("Returning...");
        }
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/SelectGame.fxml"));
        Scene scene = new Scene(root, 1280, 720);
        ch.bbbaden.casino.gruppe7.MainApp.getStage().setScene(scene);
        // NEW EDIT 15:57 FLORIAN SCHÄFER
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        ch.bbbaden.casino.gruppe7.MainApp.getStage().setX((screenBounds.getWidth() - 1280) / 2);
        ch.bbbaden.casino.gruppe7.MainApp.getStage().setY((screenBounds.getHeight() - 720) / 2);
        ch.bbbaden.casino.gruppe7.MainApp.getStage().centerOnScreen();
    }

    @FXML
    void btnReset(ActionEvent event) {
        bank.setBetOn("");
        bet = 0;
        lblBet.setText("0");
    }

    @FXML
    void btnStart(ActionEvent event) {
        if (!bank.getBetOn().equals("")) {
            makeBet();
        } else {
            dialog.showInfoNoBetOn();
        }
    }

    @FXML
    void btnBetPlayerClick(ActionEvent event) {
        bank.setBetOn("Player");
    }

    @FXML
    void btnBetBankerClick(ActionEvent event) {
        bank.setBetOn("Banker");
    }

    @FXML
    void btnBetTieClick(ActionEvent event) {
        bank.setBetOn("Tie");
    }

    @FXML
    void btnChip2Click(MouseEvent event) {
        if (balance >= 2) {
            bet = bet + 2;
            lblBet.setText(Integer.toString(bet));

        } else {
            dialog.showInfoInsufficentBalance();
        }
    }

    @FXML
    void btnChip10Click(MouseEvent event) {
        if (balance >= 10) {
            bet = bet + 10;
            lblBet.setText(Integer.toString(bet));

        } else {
            dialog.showInfoInsufficentBalance();
        }
    }

    @FXML
    void btnChip50Click(MouseEvent event) {
        if (balance >= 50) {
            bet = bet + 50;
            lblBet.setText(Integer.toString(bet));

        } else {
            dialog.showInfoInsufficentBalance();
        }
    }

    @FXML
    void btnChip100Click(MouseEvent event) {
        if (balance >= 100) {
            bet = bet + 100;
            lblBet.setText(Integer.toString(bet));

        } else {
            dialog.showInfoInsufficentBalance();
        }
    }

    @FXML
    void btnChip500Click(MouseEvent event) {
        if (balance >= 500) {
            bet = bet + 500;
            lblBet.setText(Integer.toString(bet));

        } else {
            dialog.showInfoInsufficentBalance();
        }
    }

    @FXML
    void btnChip1000Click(MouseEvent event) {
        if (balance >= 1000) {
            bet = bet + 1000;
            lblBet.setText(Integer.toString(bet));
        } else {
            dialog.showInfoInsufficentBalance();
        }
    }

    @FXML
    void btnChip5000Click(MouseEvent event) {
        if (balance >= 5000) {
            bet = bet + 5000;
            lblBet.setText(Integer.toString(bet));

        } else {
            dialog.showInfoInsufficentBalance();
        }
    }

    public void makeBet() {

        balance = bank.getBalance();

        try {

            if (balance < bet) {
                dialog.showInfoInsufficentBalance();
            } else if (bet <= 0) {
                dialog.showErrorInvalid();
            } else {

                //Disables all the bet buttons
                btnBetPlayer.setDisable(true);
                btnBetBanker.setDisable(true);
                btnBetTie.setDisable(true);

                lblBet.setText(Integer.toString(bet));

                bank.setBalance(balance -= bet); // <-- Sets the new Balance
                bank.setBet(bet);
                lblBalance.setText(Integer.toString(balance));

                startGame();
            }
        } catch (Exception e) {
            dialog.showErrorInvalid();
        }
    }

    public void toggleCardImages() {

        if (imgPlayerCard1.isVisible()) {
            imgPlayerCard1.setVisible(false);
            imgBankerCard1.setVisible(false);
            imgPlayerCard2.setVisible(false);
            imgBankerCard2.setVisible(false);
            imgPlayerCard3.setVisible(false);
            imgBankerCard3.setVisible(false);
        } else {
            imgPlayerCard1.setVisible(true);
            imgBankerCard1.setVisible(true);
            imgPlayerCard2.setVisible(true);
            imgBankerCard2.setVisible(true);
            imgPlayerCard3.setVisible(true);
            imgBankerCard3.setVisible(true);
        }
    }

    public void startGame() throws SQLException {

        Game game = new Game();

        game.createStock();
        game.dealTwoCards();

        //GUI updates
        imgPlayerCard1.setVisible(false);
        imgBankerCard1.setVisible(false);
        imgPlayerCard2.setVisible(false);
        imgBankerCard2.setVisible(false);
        imgPlayerCard3.setVisible(false);
        imgBankerCard3.setVisible(false);

        imgPlayerCard1.setImage(game.getPlayerCard1().getFrontimage());
        imgPlayerCard2.setImage(game.getPlayerCard2().getFrontimage());
        imgBankerCard1.setImage(game.getBankerCard1().getFrontimage());
        imgBankerCard2.setImage(game.getBankerCard2().getFrontimage());
        imgPlayerCard3.setImage(game.getPlayerCard3().getFrontimage());
        imgBankerCard3.setImage(game.getBankerCard3().getFrontimage());

        imgPlayerCard1.setVisible(true);
        imgPlayerCard2.setVisible(true);
        imgBankerCard1.setVisible(true);
        imgBankerCard2.setVisible(true);

        game.compareTwoCards();

        if (game.playerGetsThirdCard()) {
            imgPlayerCard3.setVisible(true);
        }

        game.dealThirdCardPlayer();

        game.bankerGetsThirdCard();
        if (game.bankerGetsThirdCard()) {
            imgBankerCard3.setVisible(true);
        }

        game.dealThirdCardBanker();

        //The animation only plays at the beginning, will be fixed in the future
        animateStart();

        bank.payPlayer(game);

        if (bank.getBet() == 0) {
            st.setStats(bank.getWinLose(), 0, bank.getWinLose());
        } else if (bank.getWinLose() == 0) {
            st.setStats(bank.getBet(), 0, 0);
        } else {
            st.setStats(bank.getBet(), bank.getWinLose(), 0);
        }

        //Refreshing values
        bet = 0;
        bank.setBetOn("");
        balance = bank.getBalance();

        //GUI updates
        btnBetPlayer.setDisable(false);
        btnBetBanker.setDisable(false);
        btnBetTie.setDisable(false);
        btnBetPlayer.setDisable(false);
        btnBetBanker.setDisable(false);
        btnBetTie.setDisable(false);

        lblSumPlayer.setText(Integer.toString(game.playerHand.getSum(game.getPlayerSum())));
        lblSumBanker.setText(Integer.toString(game.bankerHand.getSum(game.getBankerSum())));
        lblBalance.setText(Integer.toString(bank.getBalance()));
        lblBet.setText("0");

    }

    public void animateStart() {

        Timeline timeline = new Timeline();
        timeline.getKeyFrames().add(new KeyFrame(Duration.millis(1000),
                new KeyValue(imgPlayerCard1.translateXProperty(), -1026),
                new KeyValue(imgPlayerCard1.translateYProperty(), 102),
                new KeyValue(imgPlayerCard1.rotateProperty(), 0),
                new KeyValue(imgPlayerCard2.translateXProperty(), -893),
                new KeyValue(imgPlayerCard2.translateYProperty(), 102),
                new KeyValue(imgPlayerCard2.rotateProperty(), 0),
                new KeyValue(imgBankerCard1.translateXProperty(), -518),
                new KeyValue(imgBankerCard1.translateYProperty(), 102),
                new KeyValue(imgBankerCard1.rotateProperty(), 0),
                new KeyValue(imgBankerCard2.translateXProperty(), -385),
                new KeyValue(imgBankerCard2.translateYProperty(), 102),
                new KeyValue(imgBankerCard2.rotateProperty(), 0),
                new KeyValue(imgPlayerCard3.translateXProperty(), -760),
                new KeyValue(imgPlayerCard3.translateYProperty(), 102),
                new KeyValue(imgPlayerCard3.rotateProperty(), 0),
                new KeyValue(imgBankerCard3.translateXProperty(), -252),
                new KeyValue(imgBankerCard3.translateYProperty(), 102),
                new KeyValue(imgBankerCard3.rotateProperty(), 0)
        ));

        timeline.play();
    }

    //Execute at runtime
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Controller always gets the values with getters, not the other way around
        balance = bank.getBalance();
    }
}
