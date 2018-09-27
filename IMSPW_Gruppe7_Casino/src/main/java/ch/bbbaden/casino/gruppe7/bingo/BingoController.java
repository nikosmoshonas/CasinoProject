/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.casino.gruppe7.bingo;

import ch.bbbaden.casino.gruppe7.MainApp;
import ch.bbbaden.casino.gruppe7.Stats;
import ch.bbbaden.casino.gruppe7.UserLoggedIn;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;


/**
 *
 * @author Pavicic
 */
public class BingoController implements Initializable {

    Stage thisStage;
    @FXML
    private Label ball;
    @FXML
    private Label potLabel;
    @FXML
    private Label balance;
    @FXML
    private Label timer;
    @FXML
    private Label players;
    @FXML
    private Label round;
    @FXML
    private GridPane bingoCard;
    @FXML
    private ToggleButton cardNumbers;
    @FXML
    private ImageView bingoBall;
    @FXML
    private Slider amountBots;
    @FXML
    private GridPane ballHolder;
    @FXML
    private Button bingoButton;
    @FXML
    private JFXButton buyCardButton;

    Random rand = new Random();
    ArrayList<Integer> KartenFeld = new ArrayList<>();
    ArrayList<Integer> Kugel = new ArrayList<>();
    ToggleButton[][] numberFields = new ToggleButton[5][5];
    ImageView[][] crossArray = new ImageView[5][5];
    ArrayList<Bot> botArray = new ArrayList<>();
    BingoController bc;

    private UserLoggedIn uli = UserLoggedIn.getInstance();
    private Stats stats = new Stats("bingo");
    

    int anzahlCards = 0;
    //int potNumber = 0;
    ArrayList<Integer> drawnNumberArray = new ArrayList<>();

    ArrayList<Image> imageArrayList = new ArrayList<>();
    private Image redBall = new Image("bingo_img/RedBallEmpty.png");
    private Image blueBall = new Image("bingo_img/BlueBallEmpty.png");
    private Image yellowBall = new Image("bingo_img/YellowBallEmpty.png");
    private Image greenBall = new Image("bingo_img/GreenBallEmpty.png");
    private Image purpleBall = new Image("bingo_img/PurpleBallEmpty.png");

    //private Image cross = new Image("srcmainresourcesbingo_img/Crossperf.png");
    boolean callBingo = false;
    Integer starttimeDuration = 5;
    Integer secondsDuration = starttimeDuration;
    final Timeline timeForNextNumber = new Timeline();
    boolean activated = false;

    private int timerSecondsDuration = 30;
    String playerName = "Jälä";
    public int konto = 0;

    private int ballHolderX = 0;
    private int ballHolderY = 1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            konto = uli.getUserBalance();
            //   balance.setText("balance: " + konto);
        } catch (SQLException ex) {
            Logger.getLogger(BingoController.class.getName()).log(Level.SEVERE, null, ex);
        }

        imageArrayList.add(redBall);
        imageArrayList.add(blueBall);
        imageArrayList.add(yellowBall);
        imageArrayList.add(greenBall);
        imageArrayList.add(purpleBall);
    }

    public void timer() {
        KeyFrame frameDuration = new KeyFrame(javafx.util.Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Label ballNumber;
                if (drawnNumberArray.size() < 75) {
                    secondsDuration--;
                    timer.setText("Timer: " + secondsDuration.toString());
                    if ((secondsDuration % timerSecondsDuration) == 0) {
                        int randomNum = 0;

                        do {
                            randomNum = ThreadLocalRandom.current().nextInt(1, 75 + 1);                                  //generate a random number

                        } while (drawnNumberArray.contains(randomNum));

                        drawnNumberArray.add(randomNum);
                        String parse = String.valueOf(randomNum);

                        ball.setText(parse);
                        secondsDuration = timerSecondsDuration;
                        int n = rand.nextInt(5 - 0) + 0;
                        bingoBall.setImage(imageArrayList.get(n));
                        // ball.setText("" + randomNum); //

                        ballNumber = new Label();
                        ballNumber.setAlignment(Pos.CENTER);
                        ballNumber.setTextFill(Paint.valueOf("white"));
                        ballNumber.setText(parse);
                        ballNumber.setFont(Font.font(32.0));
                        ballHolder.setConstraints(ballNumber, ballHolderX, ballHolderY);
                        ballHolder.getChildren().addAll(ballNumber);
                        ballHolderX++;

                        ballNumber.setMinHeight(30);   //Höhe der Felder
                        ballNumber.setMinWidth(100);    //Breite der Felder

                        if (ballHolderX == 5) {
                            ballHolderX = 0;
                            ballHolderY++;
                        }
                        System.out.println(ballHolderX + " | " + ballHolderY + " - " + ballNumber.getText());

                        //potNumber = randomNum;
                        for (Bot bot : botArray) {
                            bot.setBingo(bot.botChecker(drawnNumberArray));
                        }
                    }
                    for (Bot bot : botArray) {
                        if (bot.isBingo()) {
                            if (bot.checkCooldown()) {
                                if (bot.checkCooldown()) {
                                    callBingo = true;
                                    timeForNextNumber.stop();
                                    try {
                                        stats.setStats(100, 0, 100);
                                    } catch (SQLException ex) {
                                        Logger.getLogger(BingoController.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    System.out.println("Bingo!");
                                    System.out.println("The game is over. Bot " + (botArray.indexOf(bot) + 1) + " won!");
                                    bingoButton.setDisable(true);
                                    Parent root = null;
                                    try {
                                        root = FXMLLoader.load(getClass().getResource("/fxml/Bingo_YouLose.fxml"));
                                        uli.setUserBalance(konto);
                                    } catch (SQLException ex) {
                                        Logger.getLogger(BingoController.class.getName()).log(Level.SEVERE, null, ex);
                                    } catch (IOException ex) {
                                        Logger.getLogger(BingoController.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                    //Erstellen einer Scene mittels der neuen View
                                    Scene scene = new Scene(root);
                                    //Holt das Stage-Objekt von der Start-Methode
                                    Stage stage = new Stage();
                                    //Setzt die Scene
                                    stage.setScene(scene);
                                    //Öffnet das neue Bild
                                    stage.show();
                                    break;
                                }
                            }
                        }
                    }
                } else {
                    callBingo = true;
                    timeForNextNumber.stop();
                    System.out.println("Nobody won!");
                }
            }
        });
        if (!callBingo) {
            timeForNextNumber.setCycleCount(Timeline.INDEFINITE);
            timeForNextNumber.getKeyFrames().add(frameDuration);
            timeForNextNumber.play();
        }
    }

    @FXML
    private void buttonclick(ActionEvent event) throws SQLException {

        System.out.println("konto: " + konto);
        System.out.println("Userbanalce: " + uli.getUserBalance());

        if (uli.getUserBalance() < 100) {
            Alert alert = new Alert(Alert.AlertType.WARNING);

            alert.setTitle("Not enough Money to Play");
            alert.setHeaderText("You don't have enough Money to Play");
            alert.setContentText("Please add funds.");

            alert.showAndWait();
        } else {

            if (botArray.size() >= 5) {
                if (anzahlCards == 0) {

                    for (int i = 0; i < 5; i++) {
                        for (int j = 0; j < 5; j++) {
                            if (i == 2 && j == 2) {

                            } else {
                                cardNumbers = new ToggleButton();

                                cardNumbers.setOnAction((ActionEvent e) -> {
                                    ToggleButton tw = (ToggleButton) e.getSource();
                                    if (tw.isSelected()) {                           //debug  

                                        System.out.println(e.getSource().toString());
                                        tw.setStyle("-fx-graphic: url('bingo_img/RedCross1.png')");

                                    } else {
                                        tw.setStyle("");

                                    }
                                });

                                bingoCard.setConstraints(cardNumbers, i, j);
                                bingoCard.getChildren().addAll(cardNumbers);

                                cardNumbers.setMinHeight(42);   //Höhe der Felder
                                cardNumbers.setMinWidth(83);    //Breite der Felder

                                cardNumbers.setId(i + "/" + j);
                                numberFields[i][j] = cardNumbers;       //Numberfields entspricht dem Array

                            }

                        }
                    }

                    int min = 1;
                    int randomNumber;
                    ArrayList<Integer> currentCol = new ArrayList<>();

                    for (int i = 0; i < numberFields.length; i++) {
                        for (int j = 0; j < numberFields[i].length; j++) {
                            if (i != 2 || j != 2) {
                                do {
                                    randomNumber = rand.nextInt((min + 14) - min) + min;
                                } while (currentCol.contains(randomNumber));
                                currentCol.add(randomNumber);
                                numberFields[i][j].setText(Integer.toString(randomNumber));
                            }

                        }
                        min += 15;
                        currentCol.clear();
                    }

                    anzahlCards++;
                    bingoButton.setDisable(false);
                    buyCardButton.setDisable(true);                    
                    setKonto(getKonto() - 100);
                    balance.setText("Balance: " + (konto));

                    timer();
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);

                    alert.setTitle("You have reached the limit of the Cards");
                    alert.setHeaderText("You can only buy one Card!");
                    alert.setContentText("Please continue to play.");

                    alert.showAndWait();

                }

            } else {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setHeaderText("You need to set bots first!");
                alert.show();
            }
        }
    }

    public void check() {
        if (callBingo == true) {
            //   numberFields[i][j].isSelected();
        }
    }

    @FXML
    private void handleBingoAction(ActionEvent event) throws IOException, SQLException {
        boolean valid = true;

        for (int i = 0; i < 5; i++) {
            valid = true;
            for (int j = 0; j < 5; j++) {
                if (i != 2 || j != 2) {
                    if (!(drawnNumberArray.contains(Integer.parseInt(numberFields[j][i].getText()))
                            && numberFields[j][i].isSelected())) {
                        valid = false;
                        break;
                    }
                }
            }
            if (valid) {
                break;
            }
        }

        if (!valid) {
            for (int i = 0; i < 5; i++) {
                valid = true;
                for (int j = 0; j < 5; j++) {
                    if (i != 2 || j != 2) {
                        if (!(drawnNumberArray.contains(Integer.parseInt(numberFields[i][j].getText()))
                                && numberFields[i][j].isSelected())) {
                            valid = false;
                            break;
                        }
                    }
                }
                if (valid) {
                    break;
                }
            }
        }
        //Cross checker
        if (!valid) {
            valid = true;
            for (int j = 0; j < 5; j++) {
                if (j != 2) {
                    if (!(drawnNumberArray.contains(Integer.parseInt(numberFields[j][j].getText()))
                            && numberFields[j][j].isSelected())) {
                        valid = false;
                        break;
                    }
                }
            }
        }
        //Crosschecker
        if (!valid) {
            valid = true;
            for (int j = 0; j < 5; j++) {
                if (j != 2) {
                    if (!(drawnNumberArray.contains(Integer.parseInt(numberFields[j][4 - j].getText()))
                            && numberFields[j][4 - j].isSelected())) {
                        valid = false;
                        break;
                    }
                }
            }
        }

        if (valid) {
            stats.setStats(100, 100*botArray.size(), 0);
            playerName = uli.getUsername();
            callBingo = true;
            bingoButton.setDisable(false);
            timeForNextNumber.stop();
            System.out.println("Bingo!");
            System.out.println("The game is over." + playerName + " won!");

            Parent root = FXMLLoader.load(getClass().getResource("/fxml/Bingo_YouWin.fxml"));
            konto = konto + (100 * botArray.size());
            System.out.println(konto);
            uli.setUserBalance(konto);
            balance.setText("balance: " + konto);

            //Erstellen einer Scene mittels der neuen View
            Scene scene = new Scene(root);
            //Holt das Stage-Objekt von der Start-Methode
            Stage stage = new Stage();
            //Setzt die Scene
            stage.setScene(scene);
            //Öffnet das neue Bild
            stage.show();

        } else {
            System.out.println("Kein Bingo");
            timeForNextNumber.play();

        }
    }

    @FXML
    public void handleSetAction(ActionEvent e) throws SQLException {
        createBot();
        players.setText("Players: " + (botArray.size() + 1));
        potLabel.setText("Jack Pot: " +(botArray.size() * 100));
    }

    public void createBot() {
        try {
            int bots = new Double(amountBots.getValue()).intValue();
            if (bots != botArray.size()) {
                if (bots > botArray.size()) {
                    for (int i = bots - botArray.size(); i > 0; i--) {
                        botArray.add(new Bot());

                    }
                } else {
                    for (int i = botArray.size() - bots; i > 0; i--) {
                        botArray.remove(botArray.size() - 1);

                    }
                }
            }
            System.out.println(botArray.size());
        } catch (NumberFormatException e) {

        }

    }

    @FXML
    private void handleExited(MouseEvent event) {
        ((ImageView) event.getSource()).setImage(new Image("/bingo_img/GoBackButton.png"));
    }

    @FXML
    private void handleEntered(MouseEvent event) {
        ((ImageView) event.getSource()).setImage(new Image("/bingo_img/GoBackClicked.png"));
    }

    @FXML
    private void changePicture(MouseEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/SelectGame.fxml"));
        //Erstellen einer Scene mittels der neuen View
        Scene scene = new Scene(root, 1280, 720);
        //Holt das Stage-Objekt von der Start-Methode
        Stage stage = MainApp.getStage();
        //Setzt die Scene
        stage.setScene(scene);
        //Öffnet das neue Bild
        stage.show();

        ch.bbbaden.casino.gruppe7.MainApp.getStage().setScene(scene);
        // NEW EDIT 15:57 FLORIAN SCHÄFER
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        ch.bbbaden.casino.gruppe7.MainApp.getStage().setX((screenBounds.getWidth() - 1280) / 2);
        ch.bbbaden.casino.gruppe7.MainApp.getStage().setY((screenBounds.getHeight() - 720) / 2);
        ch.bbbaden.casino.gruppe7.MainApp.getStage().centerOnScreen();
    }

    @FXML
    private void handleExitedPlay(MouseEvent event) {
        ((ImageView) event.getSource()).setImage(new Image("/bingo_img/PlayButton.png"));

    }

    @FXML
    private void handleEnteredPlay(MouseEvent event) {
        ((ImageView) event.getSource()).setImage(new Image("/bingo_img/PlayClicked.png"));

    }

    @FXML
    private void onPlayAction(MouseEvent event) throws IOException, SQLException {

        //Setzt neue View
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Bingo.fxml"));
        //Erstellen einer Scene mittels der neuen View
        Scene scene = new Scene(root);
        //Holt das Stage-Objekt von der Start-Methode
        Stage stage = MainApp.getStage();
        //Setzt die Scene
        stage.setScene(scene);
        //Öffnet das neue Bild
        stage.show();
//        buyCardButton.setVisible(false);
        uli.getUserBalance();
    }

    @FXML
    private void goBackAction(MouseEvent event) throws IOException {
        //Setzt neue View
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Bingo_Welcome.fxml"));
        //Erstellen einer Scene mittels der neuen View
        Scene scene = new Scene(root, 1600, 900);
        //Holt das Stage-Objekt von der Start-Methode
        Stage stage = MainApp.getStage();
        //Setzt die Scene
        stage.setScene(scene);
        //Öffnet das neue Bild
        stage.show();

        ch.bbbaden.casino.gruppe7.MainApp.getStage().setScene(scene);
        // NEW EDIT 15:57 FLORIAN SCHÄFER
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        ch.bbbaden.casino.gruppe7.MainApp.getStage().setX((screenBounds.getWidth() - 1600) / 2);
        ch.bbbaden.casino.gruppe7.MainApp.getStage().setY((screenBounds.getHeight() - 900) / 2);
        ch.bbbaden.casino.gruppe7.MainApp.getStage().centerOnScreen();
    }

//    @FXML
//    private void playAgain(MouseEvent event) throws IOException{
//        //Setzt neue View
//        Parent root = FXMLLoader.load(getClass().getResource("BingoController.fxml"));
//        //Erstellen einer Scene mittels der neuen View
//        Scene scene = new Scene(root);
//        //Holt das Stage-Objekt von der Start-Methode
//        Stage stage = MainApp.getStage();
//        //Setzt die Scene
//        stage.setScene(scene);
//        //Öffnet das neue Bild
//        stage.show();
//        ImageView img = (ImageView) event.getSource();
//        img.getScene().getWindow().hide();
//    }
    @FXML
    public void quitButton(ActionEvent event) throws IOException, SQLException {
        timeForNextNumber.stop();
        int value = 0;
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText("Do you really want to quit? You,ll lose your bet");
        
            Optional<ButtonType> result = alert.showAndWait();

            value = 1;

            if (result.get() == ButtonType.OK) {
                Parent root = FXMLLoader.load(getClass().getResource("/fxml/Bingo_Welcome.fxml"));
                //Erstellen einer Scene mittels der neuen View
                Scene scene = new Scene(root);
                //Holt das Stage-Objekt von der Start-Methode
                Stage stage = MainApp.getStage();
                //Setzt die Scene
                stage.setScene(scene);
                //Öffnet das neue Bild
                stage.show();
                uli.setUserBalance(konto);
                reset();
            }else{
                timeForNextNumber.play();
                //stops playing
            }
            
    }

    public void setStage(Stage stage) {
        thisStage = stage;
    }

    public ArrayList<Bot> getBots() {
        return botArray;
    }

    public void setKonto(int konto) {
        this.konto = konto;
    }

    public int getKonto() {
        return konto;
    }
    
    public void reset(){
        timeForNextNumber.stop();
        drawnNumberArray.clear();
    }
    
}
