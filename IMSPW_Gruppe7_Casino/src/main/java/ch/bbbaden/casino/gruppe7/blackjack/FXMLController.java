package ch.bbbaden.casino.gruppe7.blackjack;

import ch.bbbaden.casino.gruppe7.Stats;
import ch.bbbaden.casino.gruppe7.UserLoggedIn;
import static ch.bbbaden.casino.gruppe7.blackjack.MainApp.stage;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.util.Duration;

public class FXMLController implements Initializable {

    Boolean einsBelegt;
    Boolean zweiBelegt;
    KartenSpiel kartenSpiel;
    BlackJack spiel;
    public boolean gesetzt;

    @FXML
    private ImageView hit;
    @FXML
    private ImageView stand;
    @FXML
    public AnchorPane anchorPane;
    @FXML
    private ImageView spielen;
    @FXML
    private ImageView positionS1;
    @FXML
    private ImageView positionS2;
    @FXML
    private ImageView positionS3;
    @FXML
    private ImageView positionS4;
    @FXML
    private ImageView positionS5;
    @FXML
    private ImageView positionD1;
    @FXML
    private ImageView positionD2;
    @FXML
    private ImageView positionD4;
    @FXML
    private ImageView positionD3;
    @FXML
    private ImageView positionD5;
    @FXML
    public Label wertDealer;
    @FXML
    public Label wertSpieler;
    @FXML
    private Text ausgabe;
    @FXML
    private Label einsatz;
    @FXML
    private ImageView chips;
    @FXML
    private ImageView white;
    @FXML
    private ImageView red;
    @FXML
    private ImageView green;
    @FXML
    private ImageView blue;
    @FXML
    private ImageView black;
    @FXML
    private Label balance;
    @FXML
    private ImageView neuesSpiel;
    @FXML
    private ImageView insurance;
    @FXML
    private ImageView doubledown;
    private UserLoggedIn user = UserLoggedIn.getInstance();
    Stats stats = new Stats("Blackjack");
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            spiel = new BlackJack(this);
        } catch (SQLException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
//        stage.setResizable(false);
        neuesSpiel.setVisible(false);
        hit.setVisible(false);
        stand.setVisible(false);
        insurance.setVisible(false);
        doubledown.setVisible(false);
        System.out.println(spiel.getBalance());
//
        cardSlots[0] = positionS1;
        cardSlots[1] = positionS2;
        cardSlots[2] = positionS3;
        cardSlots[3] = positionS4;
        cardSlots[4] = positionS5;
        cardSlots[5] = positionD1;
        cardSlots[6] = positionD2;
        cardSlots[7] = positionD3;
        cardSlots[8] = positionD4;
        cardSlots[9] = positionD5;

        setAusgabe("Hallo, Dir stehen " + spiel.getBalance() + "$ als Kapital zur Verfuegung. Mach Deinen Einsatz und beginne das Spiel.");
        try {
            setBalance(String.valueOf(user.getUserBalance()));
            
            
        } catch (SQLException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

   

    private final ImageView[] cardSlots = new ImageView[10];
    public ImageView[] cards = new ImageView[10];
    public ImageView[] chip = new ImageView[100];

    public void addCard(String pfad, int position) {

        final ImageView c = new ImageView("/BlackJack_Karten/deck.png");
        anchorPane.getChildren().add(c);
        ImageView cs = cardSlots[position];
        c.setFitWidth(130);
        c.setFitHeight(202);

        c.setLayoutX(1600);

        c.setLayoutY(cs.getLayoutY());
        moveNodeAnim(c, cs.getLayoutX(), cs.getLayoutY(), 300);
        c.setImage(new Image(pfad));
        cards[position] = c;
    }
    
     public void dropCards() {
        einsatz.setText("00");
        for (ImageView card : cards) {
            if (card != null) {
                double x1 = card.getLayoutX();
                double y1 = card.getFitHeight() + 900;

                anchorPane.getChildren().remove(card);

                moveNodeAnim(card, x1, y1, 500);
            }
        }
        for (ImageView chips : chip) {
            if (chips != null) {
                double x1 = chips.getLayoutX();
                double y1 = chips.getFitHeight() + 900;

                anchorPane.getChildren().remove(chip);

                moveNodeAnim(chips, x1, y1, 500);
            }
        }
    }
    public void addBet(String n, Node node) {

        Node newNode = node;
        final ImageView c = new ImageView("/BlackJack_Assets/" + n + ".png");
        anchorPane.getChildren().add(c);
        ImageView cs = chips;
        c.setFitWidth(80);
        c.setFitHeight(80);
        c.setLayoutX(newNode.getLayoutX());
        c.setLayoutY(newNode.getLayoutY());
        moveNodeAnim(c, cs.getLayoutX(), cs.getLayoutY(), 300);
        int anzahl = 0;
        chip[anzahl] = c;
        anzahl++;
        switch (n) {
            case "white":
                spiel.erhoeheEinsatz(5);
                break;
            case "red":
                spiel.erhoeheEinsatz(10);
                break;
            case "green":
                spiel.erhoeheEinsatz(20);
                break;
            case "blue":
                spiel.erhoeheEinsatz(50);
                break;
            case "black":
                spiel.erhoeheEinsatz(100);
                break;
        }

    }
    
     public void moveNodeAnim(Node n, double x, double y, int ms) {
        Timeline timeline = new Timeline();
        timeline.setCycleCount(1);
        timeline.setAutoReverse(false);
        final KeyValue kv = new KeyValue(n.layoutXProperty(), x, Interpolator.EASE_OUT);
        final KeyFrame kf = new KeyFrame(Duration.millis(ms), kv);

        final KeyValue kv2 = new KeyValue(n.layoutYProperty(), y, Interpolator.EASE_OUT);
        final KeyFrame kf2 = new KeyFrame(Duration.millis(ms), kv2);
        timeline.getKeyFrames().addAll(kf, kf2);
        timeline.play();
    }
     
    @FXML
    private void handleSpielen(MouseEvent event) {
        btnEnable();
        spiel.setSpieleCounter(1);
        if (einsatz.getText().equals("00")) {
            setAusgabe("Bitte setzen Sie zuerst.");
        } else {
            hit.setVisible(true);
            stand.setVisible(true);
            spielen.setVisible(false);
            spiel.neuesSpiel(gesetzt);
            white.setVisible(true);
            doubledown.setVisible(true);
            
        }
    }

    @FXML
    private void handleNeuesSpiel(MouseEvent event) {
        dropCards();
        spielen.setVisible(true);
        neuesSpiel.setVisible(false);
        wertSpieler.setText("00");
        wertDealer.setText("00");
        spiel.setEinsatz(0);
    }

    @FXML
    private void handleDoubledown(MouseEvent event) {

        spiel.verdoppeln();
        insurance.setVisible(false);

    }

    @FXML
    private void handleInsureance(MouseEvent event) {
        insurance.setVisible(false);
        spiel.versichern();
        setAusgabe("Sie haben sich versichert.");

    }

    @FXML
    private void doubledownEX(MouseEvent event) {
        ((ImageView) event.getSource()).setImage(new Image("/BlackJack_Assets/doubledown.png"));
    }

    @FXML
    private void doubledownEN(MouseEvent event) {
        ((ImageView) event.getSource()).setImage(new Image("/BlackJack_Assets/doubledown2.png"));
    }

    @FXML
    private void insuranceEX(MouseEvent event) {
        ((ImageView) event.getSource()).setImage(new Image("/BlackJack_Assets/Insurance.png"));
    }

    @FXML
    private void insuranceEN(MouseEvent event) {
        ((ImageView) event.getSource()).setImage(new Image("/BlackJack_Assets/Insurance2.png"));
    }

    @FXML
    private void btnexited(MouseEvent event) {
        ((ImageView) event.getSource()).setImage(new Image("/BlackJack_Assets/Hit.png"));
    }

    @FXML
    private void btnentered(MouseEvent event) {
        ((ImageView) event.getSource()).setImage(new Image("/BlackJack_Assets/Hit2.png"));
    }

    @FXML
    private void btnexited1(MouseEvent event) {
        ((ImageView) event.getSource()).setImage(new Image("/BlackJack_Assets/Stand.png"));
    }

    @FXML
    private void btnentered1(MouseEvent event) {
        ((ImageView) event.getSource()).setImage(new Image("/BlackJack_Assets/Stand2.png"));
    }

    @FXML
    private void handleHit(MouseEvent event) {
        spiel.naechsteKarte();
        insurance.setVisible(false);
    }

    @FXML
    private void handleStand(MouseEvent event) {
        spiel.naechsterSpieler();
        spiel.dealerIstDran();
        insurance.setVisible(false);
    }

    @FXML
    private void handleBet(MouseEvent event) {
        gesetzt = true;
        Node node = (Node) event.getSource();
        addBet(node.getId(), node);
        System.out.println(node.getId());

    }

   

    public void btnDisable() {
        stand.setDisable(true);
        hit.setDisable(true);
        insurance.setDisable(true);

        doubledown.setDisable(true);
    }

    public void btnEnable() {
        stand.setDisable(false);
        hit.setDisable(false);
        insurance.setDisable(false);

        doubledown.setDisable(false);
    }

    void setAusgabe(String s) {
        ausgabe.setText(s);
    }

    void setzeImageView(String karte) {
        cards[5].setImage(new Image("/BlackJack_Karten/" + karte + ".png"));
    }

    public void setNeuesSpielVisible() {
        neuesSpiel.setVisible(true);
    }

    public void setInsuranceVisible() {
        insurance.setVisible(true);
    }

    public void setSpiel(BlackJack spiel) {
        this.spiel = spiel;
    }

    public void setEinsatz(String zahl) {
        einsatz.setText(zahl);
    }

    public void setBalance(String zahl) {
        balance.setText(zahl);
    }

    public void setWertungSpieler(String zahl) {

        wertSpieler.setText(zahl);

    }

    public void setWertungDealer(String zahl) {
        wertDealer.setText(zahl);
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
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/SelectGame.fxml"));
        Scene scene = new Scene(root,1280,720);
        ch.bbbaden.casino.gruppe7.MainApp.getStage().setScene(scene);
         // NEW EDIT 15:57 FLORIAN SCHÄFER
            Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
            ch.bbbaden.casino.gruppe7.MainApp.getStage().setX((screenBounds.getWidth() - 1280) / 2);
            ch.bbbaden.casino.gruppe7.MainApp.getStage().setY((screenBounds.getHeight() - 720) / 2);
            ch.bbbaden.casino.gruppe7.MainApp.getStage().centerOnScreen();
        
    }

}
