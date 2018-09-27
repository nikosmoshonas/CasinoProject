/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.casino.gruppe7;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Screen;

/**
 *
 * @author TimPro
 */
public class StatsController implements Initializable {

    @FXML
    private TabPane TabPane;
    @FXML
    private Tab TabOverview, TabBaccarat, TabBingo, TabBlackJack, TabRoulette, TabYatzy, TabAdmin;
    @FXML
    private Label tbet, tbet1, tbet2, tbet3, tbet4, tbet5, trounds, trounds1, trounds2, trounds3, trounds4, trounds5;
    @FXML
    private PieChart OverviewPieChart, BaccaratPieChart, BingoPieChart, BlackJackPieChart, RoulettePieChart, YatzyPieChart;
    private Stats stats = new Stats();
    private UserLoggedIn userloggedin = UserLoggedIn.getInstance();

    @FXML
    private void closeStats(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/SelectGame.fxml"));
        Scene scene = new Scene(root, 1280, 720);
        ch.bbbaden.casino.gruppe7.MainApp.getStage().setScene(scene);
        // NEW EDIT 15:57 FLORIAN SCHÄFER
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        ch.bbbaden.casino.gruppe7.MainApp.getStage().setX((screenBounds.getWidth() - 1280) / 2);
        ch.bbbaden.casino.gruppe7.MainApp.getStage().setY((screenBounds.getHeight() - 720) / 2);
        ch.bbbaden.casino.gruppe7.MainApp.getStage().centerOnScreen();
    }

    public void PieChartWonLost(PieChart PieChart, String game) throws SQLException {
        ObservableList<PieChart.Data> pieChartData
                = FXCollections.observableArrayList(
                        new PieChart.Data("Won", stats.getUserTotalFromGames("won", game)),
                        new PieChart.Data("Lost", stats.getUserTotalFromGames("lost", game)));
        PieChart.setData(pieChartData);

    }

    public void TotalBetAndRound(Label l1, Label l2, PieChart PieChart, String game) throws SQLException {
        l1.setText(l1.getText() + " " + stats.getUserTotalFromGames("bet", game));
        l2.setText(l2.getText() + " " + stats.getUserTotalFromGames("", game));
        if (l2.getText().equals("TOTAL ROUNDS:  0") == false) {
            PieChartWonLost(PieChart, game);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            TotalBetAndRound(tbet, trounds, OverviewPieChart, "");
            TotalBetAndRound(tbet1, trounds1, BaccaratPieChart, "baccarat");
            TotalBetAndRound(tbet2, trounds2, BingoPieChart, "bingo");
            TotalBetAndRound(tbet3, trounds3, BlackJackPieChart, "blackjack");
            TotalBetAndRound(tbet4, trounds4, RoulettePieChart, "roulette");
            TotalBetAndRound(tbet5, trounds5, YatzyPieChart, "yatzy");
        } catch (SQLException ex) {
            Logger.getLogger(StatsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
