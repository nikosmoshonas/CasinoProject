package ch.bbbaden.casino.gruppe7.yatzy_game;

import ch.bbbaden.casino.gruppe7.Stats;
import ch.bbbaden.casino.gruppe7.UserLoggedIn;
import com.jfoenix.controls.JFXButton;
import java.awt.Component;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Set;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Screen;
import javax.swing.JOptionPane;

public class FXMLController implements Initializable {

    TableViewloader myclass = new TableViewloader("", 0, 0);

    @FXML
    private Label label;
    @FXML
    private ImageView cube1;
    @FXML
    private ImageView cube2;
    @FXML
    private ImageView cube3;
    @FXML
    private ImageView cube4;
    @FXML
    private ImageView cube5;
    //Alert alert = new Alert(Alert.AlertType.WARNING);

    UserLoggedIn logIN = UserLoggedIn.getInstance();

    Stats st = new Stats("Yatzy");
    boolean playerTurn = true;

    AlertClass alert = new AlertClass();
    boolean checkifselectedcorrect = true;
    boolean throwcheck = true;
    boolean checkOut = false;
    boolean arrayisfull = false;
    boolean selectWrong = true;

    int player1Tries = 0;
    int player2Tries = 0;

    int sumSpieler1 = 0;
    int sumSpieler2 = 0;

    boolean alreadyexecuted = false;
    int counter = 0;

    ArrayList<Image> imagearr = new ArrayList<>();

    private final Image dice1 = new Image("/styles/assets/dice1.png");
    private final Image dice2 = new Image("/styles/assets/dice2.png");
    private final Image dice3 = new Image("/styles/assets/dice3.png");
    private final Image dice4 = new Image("/styles/assets/dice4.png");
    private final Image dice5 = new Image("/styles/assets/dice5.png");
    private final Image dice6 = new Image("/styles/assets/dice6.png");

    @FXML
    private TableColumn<TableViewloader, String> tableSpieler;
    @FXML
    private TableColumn<TableViewloader, Integer> spieler1;
    @FXML
    private TableColumn<TableViewloader, Integer> spieler2;
    @FXML
    public TableView<TableViewloader> tableview;

    String[] filltable = new String[]{"1", "2", "3", "4", "5", "6", "1 Paar", "2 Paar", "Drei Gleiche", "Vier Gleiche", "Kleine Strasse", "Grosse Strasse", "Full House", "YATZY!!!!", "Chance"};
    ObservableList<String> row = FXCollections.observableArrayList(filltable);
    ArrayList<TableViewloader> arr = new ArrayList<>();

    ArrayList<Integer> checkforNumbers = new ArrayList<>();
    ArrayList<Integer> selectedNumbers = new ArrayList<>(5);

    @FXML
    private ImageView select1;
    @FXML
    private ImageView select2;
    @FXML
    private ImageView select3;
    @FXML
    private ImageView select4;
    @FXML
    private ImageView select5;
    @FXML
    private Label labelPlayer1;
    @FXML
    private Label labelPlayer2;
    @FXML
    public Label labelAmount;
    @FXML
    private Label labelToRoll;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            logIN.getUserBalance();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

        alert.inputAlert();
        labelAmount.setText(alert.getResult());
        try {
            logIN.setUserBalance(logIN.getUserBalance() - Integer.parseInt(alert.getResult()));
        } catch (SQLException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

        tableSpieler.setCellValueFactory(new PropertyValueFactory<TableViewloader, String>("spieler"));
        spieler1.setCellValueFactory(new PropertyValueFactory<TableViewloader, Integer>("spieler1"));
        spieler2.setCellValueFactory(new PropertyValueFactory<TableViewloader, Integer>("spieler2"));

        for (String i : filltable) {

            arr.add(new TableViewloader(i, 0, 0));
        }

        ObservableList<TableViewloader> tvl = FXCollections.observableArrayList(arr);

        imagearr.add(dice1);
        imagearr.add(dice2);
        imagearr.add(dice3);
        imagearr.add(dice4);
        imagearr.add(dice5);
        imagearr.add(dice6);

        tableview.setItems(tvl);

    }

    public void eineraugenzahlen() {
        boolean bool = false;
        do {

            try {

                int tothrow = 3 - counter;

                if (counter == 3) {

                    TablePosition pos = tableview.getSelectionModel().getSelectedCells().get(0);
                    int row = pos.getRow();

                    int sum = 0;

                    for (int i = 1; i <= 6; i++) {

                        for (int j : selectedNumbers) {

                            if (j == i && row == i - 1 && counter == 3) {
                                bool = false;
                                sum += j;

                                if (playerTurn) {
                                    tableview.getSelectionModel().getSelectedItem().setSpieler1(sum);

                                    tableview.getItems().add(myclass);

                                } else if (!playerTurn) {
                                    tableview.getSelectionModel().getSelectedItem().setSpieler2(sum);
                                    tableview.getItems().add(myclass);
                                }
                                throwcheck = true;
                                selectWrong = true;

                            } else {

                            }

                        }

                    }

                } else {

                    Component parentComponent = null;

                    JOptionPane.showMessageDialog(parentComponent, "Du musst noch " + tothrow + "mal würfeln!");
                    throwcheck = false;

                }

            } catch (Exception e) {

            }

        } while (throwcheck = false);

    }

    public void zweiPaar() {
        boolean bool = false;
        int multiplikator = 2;
        int sum = 0;

        int occurency = 0;

        int tothrow = 3 - counter;

        try {

            if (counter < 4) {
                TablePosition pos = tableview.getSelectionModel().getSelectedCells().get(0);
                int row = pos.getRow();
                System.out.println(row);

                Set<Integer> s = new HashSet<>(selectedNumbers);

                if (row == 7) {
                    for (Integer i : selectedNumbers) {
                        if (s.add(i) == false) {
                            System.out.println(s);
                        } else {
                            s.add(i);
                            System.out.println(s);

                        }

                    }

                    for (Integer i : s) {
                        sum += i * 2;

                        if (playerTurn) {
                            tableview.getSelectionModel().getSelectedItem().setSpieler1(sum);

                            tableview.getItems().add(myclass);

                        } else if (!playerTurn) {
                            tableview.getSelectionModel().getSelectedItem().setSpieler2(sum);
                            tableview.getItems().add(myclass);
                        }

                        System.out.println(s);
                        System.out.println(sum);
                    }

                }

            }

        } catch (Exception e) {
            System.out.println(e);
        }

//        
    }

    public void gleiche() {
        int tothrow = 3 - counter;
        int i;
        int sum = 0;
        boolean bool = true;
        do {

            try {

                if (counter < 4) {

                    TablePosition pos = tableview.getSelectionModel().getSelectedCells().get(0);
                    int row = pos.getRow();

                    for (int augenzahl = 1; augenzahl <= 6; augenzahl++) {

                        int occurency = Collections.frequency(selectedNumbers, augenzahl);

                    }

                    for (i = 2; i <= 5; i++) {

                        switch (i) {

                            case 2:

                                for (int augenzahl = 1; augenzahl <= 6; augenzahl++) {

                                    int occurency = Collections.frequency(selectedNumbers, augenzahl);

                                    if (occurency == i && row == 6) {
                                        sum += augenzahl * i;

                                        if (playerTurn) {
                                            tableview.getSelectionModel().getSelectedItem().setSpieler1(sum);

                                            tableview.getItems().add(myclass);

                                        } else if (!playerTurn) {
                                            tableview.getSelectionModel().getSelectedItem().setSpieler2(sum);
                                            tableview.getItems().add(myclass);
                                        }
                                        throwcheck = true;
                                        break;
                                    } else {
//
                                        if (!bool) {
                                            if (occurency == i) {
                                                System.out.println("du hättest eine grössere auswahl treffen können");
                                            } else if (row != 6) {

                                            }

                                        }

                                    }

                                }
                                break;

                            case 3:
                                for (int augenzahl = 1; augenzahl <= 6; augenzahl++) {

                                    int occurency = Collections.frequency(selectedNumbers, augenzahl);

                                    if (occurency == i && row == 8) {

                                        sum += augenzahl * i;

                                        if (playerTurn) {
                                            tableview.getSelectionModel().getSelectedItem().setSpieler1(sum);

                                            tableview.getItems().add(myclass);

                                        } else if (!playerTurn) {
                                            tableview.getSelectionModel().getSelectedItem().setSpieler2(sum);
                                            tableview.getItems().add(myclass);
                                        }

                                        throwcheck = false;
                                        break;
                                    } else {

                                    }

                                }
                                break;

                            case 4:

                                for (int augenzahl = 1; augenzahl <= 6; augenzahl++) {

                                    int occurency = Collections.frequency(selectedNumbers, augenzahl);

                                    if (occurency == i && row == 9) {

                                        sum += augenzahl * i;

                                        if (playerTurn) {
                                            tableview.getSelectionModel().getSelectedItem().setSpieler1(sum);

                                            tableview.getItems().add(myclass);

                                        } else if (!playerTurn) {
                                            tableview.getSelectionModel().getSelectedItem().setSpieler2(sum);
                                            tableview.getItems().add(myclass);
                                        }
                                        throwcheck = true;
                                        break;
                                    } else {
//

                                    }

                                }

                                break;

                            case 5:

                                for (int augenzahl = 1; augenzahl <= 6; augenzahl++) {

                                    int occurency = Collections.frequency(selectedNumbers, augenzahl);

                                    if (occurency == i && row == 13) {

                                        if (playerTurn) {
                                            tableview.getSelectionModel().getSelectedItem().setSpieler1(50);

                                            tableview.getItems().add(myclass);

                                        } else if (!playerTurn) {
                                            tableview.getSelectionModel().getSelectedItem().setSpieler2(50);
                                            tableview.getItems().add(myclass);
                                        }

                                        throwcheck = true;
                                        break;
                                    } else {

                                    }

                                }

                        }
                    }

                } else {
                    Component parentComponent = null;

                    JOptionPane.showMessageDialog(parentComponent, "Du musst noch " + tothrow + "mal würfeln!");
                }

            } catch (Exception e) {

            }

        } while (throwcheck = false);

    }

    public void kleineStrasse() {
//        boolean bool = false;
        TablePosition pos = tableview.getSelectionModel().getSelectedCells().get(0);
        int row = pos.getRow();

        int tothrow = 3 - counter;

        int zähle = 0;

        try {

            if (counter < 4) {

                for (int i = 1; i <= 5; i++) {

                    if (selectedNumbers.contains(i) && row == 10) {
                        zähle++;

                    } else {

                        if (selectedNumbers.contains(i) && row != 10) {

                        }
                    }

                }

                if (zähle == 5) {

                    if (playerTurn) {
                        tableview.getSelectionModel().getSelectedItem().setSpieler1(15);

                        tableview.getItems().add(myclass);

                    } else if (!playerTurn) {
                        tableview.getSelectionModel().getSelectedItem().setSpieler2(15);
                        tableview.getItems().add(myclass);
                    }

                } else {

                }

            } else {
                Component parentComponent = null;

                JOptionPane.showMessageDialog(parentComponent, "Du musst noch " + tothrow + "mal würfeln!");
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void grosseStrasse() {

        TablePosition pos = tableview.getSelectionModel().getSelectedCells().get(0);
        int row = pos.getRow();

        int tothrow = 3 - counter;

        int zähle = 0;

        try {

            if (counter < 4) {

                for (int i = 2; i <= 6; i++) {

                    if (selectedNumbers.contains(i) && row == 11) {
                        zähle++;

                    } else {

                        if (selectedNumbers.contains(i) && row != 11) {

                        }
                    }

                }

                if (zähle == 5) {

                    if (playerTurn) {
                        tableview.getSelectionModel().getSelectedItem().setSpieler1(20);

                        tableview.getItems().add(myclass);

                    } else if (!playerTurn) {
                        tableview.getSelectionModel().getSelectedItem().setSpieler2(20);
                        tableview.getItems().add(myclass);
                    }

                }

            } else {
                Component parentComponent = null;

                JOptionPane.showMessageDialog(parentComponent, "Du musst noch " + tothrow + "mal würfeln!");
            }

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    public void fullHouse() {
        TablePosition pos = tableview.getSelectionModel().getSelectedCells().get(0);
        int row = pos.getRow();
        Collections.sort(selectedNumbers);

        try {

            if (counter < 4) {

                if (row == 12) {

                    if ((((row == 12 && selectedNumbers.get(0) == selectedNumbers.get(1)) && (selectedNumbers.get(1) == selectedNumbers.get(2)))
                            && (selectedNumbers.get(3) == selectedNumbers.get(4))
                            && // Two of a Kind
                            (selectedNumbers.get(2) != selectedNumbers.get(3))) // make sure they are of different values

                            // or two of a kind and a three of a kind
                            || ((selectedNumbers.get(0) == selectedNumbers.get(1))
                            && // Two of a Kind
                            // Three of a kind
                            ((selectedNumbers.get(2) == selectedNumbers.get(3)) && (selectedNumbers.get(3) == selectedNumbers.get(4)))
                            // make sure they are of different values
                            && (selectedNumbers.get(1) != selectedNumbers.get(2)))) {

                        if (playerTurn) {
                            tableview.getSelectionModel().getSelectedItem().setSpieler1(25);

                            tableview.getItems().add(myclass);

                        } else if (!playerTurn) {
                            tableview.getSelectionModel().getSelectedItem().setSpieler2(25);
                            tableview.getItems().add(myclass);
                        }

                    } else {

                    }

                } else {

                }

            }

        } catch (Exception e) {
        }

    }

    public void chance() {
        try {
            int sum = 0;

            if (counter < 4) {

                TablePosition pos = tableview.getSelectionModel().getSelectedCells().get(0);
                int row = pos.getRow();

                if (row == 14) {

                    for (Integer i : selectedNumbers) {

                        sum += i;
                        System.out.println(sum);
                        
                        if (playerTurn) {
                        tableview.getSelectionModel().getSelectedItem().setSpieler1(sum);

                        tableview.getItems().add(myclass);

                    } else if (!playerTurn) {
                        tableview.getSelectionModel().getSelectedItem().setSpieler2(sum);
                        tableview.getItems().add(myclass);
                    }

                    }
              

                }

            }

        } catch (Exception e) {

        }

    }

    @FXML
    public void clickonCube1(MouseEvent event) {

        cube1.setVisible(false);
        select1.setImage(cube1.getImage());
        select1.setVisible(true);

        int get = checkforNumbers.get(0);

        selectedNumbers.add(get);

    }

    @FXML
    public void undoCube1(MouseEvent event) {

        select1.setVisible(false);
        cube1.setImage(select1.getImage());
        cube1.setVisible(true);
        int get = selectedNumbers.get(0);
        checkforNumbers.add(0, get);
        selectedNumbers.remove(0);

    }

    @FXML
    private void clickonCube2(MouseEvent event) {

        cube2.setVisible(false);
        select2.setImage(cube2.getImage());
        select2.setVisible(true);

        int get = checkforNumbers.get(1);

        selectedNumbers.add(get);

    }

    @FXML
    private void clickonCube3(MouseEvent event) {

        cube3.setVisible(false);
        select3.setImage(cube3.getImage());
        select3.setVisible(true);

        int get = checkforNumbers.get(2);

        selectedNumbers.add(get);

    }

    @FXML
    private void clickonCube4(MouseEvent event) {

        cube4.setVisible(false);
        select4.setImage(cube4.getImage());
        select4.setVisible(true);

        int get = checkforNumbers.get(3);

        selectedNumbers.add(get);
    }

    @FXML
    private void clickonCube5(MouseEvent event) {

        cube5.setVisible(false);
        select5.setImage(cube5.getImage());
        select5.setVisible(true);

        int get = checkforNumbers.get(4);

        selectedNumbers.add(get);
    }

    @FXML
    private void undoCube2(MouseEvent event) {

        select2.setVisible(false);
        cube2.setImage(select2.getImage());
        cube2.setVisible(true);

        int get = selectedNumbers.get(0);
        checkforNumbers.add(1, get);
        selectedNumbers.remove(0);
    }

    @FXML
    private void undoCube3(MouseEvent event) {

        select3.setVisible(false);
        cube3.setImage(select3.getImage());
        cube3.setVisible(true);

        int get = selectedNumbers.get(0);
        checkforNumbers.add(2, get);
        selectedNumbers.remove(0);
    }

    @FXML
    private void undoCube4(MouseEvent event) {

        select4.setVisible(false);
        cube4.setImage(select4.getImage());
        cube4.setVisible(true);

        int get = selectedNumbers.get(0);
        checkforNumbers.add(3, get);
        selectedNumbers.remove(0);
    }

    @FXML
    public void undoCube5(MouseEvent event) {

        select5.setVisible(false);
        cube5.setImage(select5.getImage());
        cube5.setVisible(true);

        int get = selectedNumbers.get(0);
        checkforNumbers.add(4, get);
        selectedNumbers.remove(0);
    }

    public void undoCubes() {

        select1.setVisible(false);
        select2.setVisible(false);
        select3.setVisible(false);
        select4.setVisible(false);
        select5.setVisible(false);

        cube1.setVisible(false);
        cube2.setVisible(false);
        cube3.setVisible(false);
        cube4.setVisible(false);
        cube5.setVisible(false);

        selectedNumbers.removeAll(selectedNumbers);
        checkforNumbers.removeAll(checkforNumbers);
        alreadyexecuted = false;

    }

    public void setCubesVisible() {

        cube1.setVisible(true);
        cube2.setVisible(true);
        cube3.setVisible(true);
        cube4.setVisible(true);
        cube5.setVisible(true);
    }

    public void countColumn1() {

        try {

            ArrayList<Integer> columnData = new ArrayList<>();
            for (TableViewloader item : tableview.getItems()) {
                columnData.add(spieler1.getCellObservableValue(item).getValue());

            }

            for (Integer i : columnData) {

                sumSpieler1 += i;

            }

            System.out.println(sumSpieler1);

        } catch (Exception e) {

            System.out.println(e);

        }

    }

    public void countColumn2() {
        try {

            ArrayList<Integer> columnData = new ArrayList<>();
            for (TableViewloader item : tableview.getItems()) {
                columnData.add(spieler2.getCellObservableValue(item).getValue());

            }

            for (Integer i : columnData) {

                sumSpieler2 += i;

            }

            System.out.println(sumSpieler2);

        } catch (Exception e) {

            System.out.println(e);

        }
    }

    private int rollcounter = 3;

    public void newGame() {
        tableview.getItems().removeAll(tableview.getItems());
        checkforNumbers.clear();
        selectedNumbers.clear();
        for (String i : filltable) {
            arr.add(new TableViewloader(i, 0, 0));
        }
        ObservableList<TableViewloader> tvl = FXCollections.observableArrayList(arr);
        tableview.setItems(tvl);

    }

    @FXML
    private void rollDice(MouseEvent event) {

        if (!alreadyexecuted) {
            setCubesVisible();
            alreadyexecuted = true;
        }

        if (playerTurn) {
            rollcounter--;
            labelToRoll.setText(Integer.toString(rollcounter));
            labelPlayer1.setStyle("-fx-text-fill: red;");
            labelPlayer2.setStyle("-fx-text-fill: black;");
            if (rollcounter == 0) {
                rollcounter = 3;
            }

        } else if (!playerTurn) {
            rollcounter--;
            labelToRoll.setText(Integer.toString(rollcounter));
            labelPlayer2.setStyle("-fx-text-fill: red;");
            labelPlayer1.setStyle("-fx-text-fill: black;");
            if (rollcounter == 0) {
                rollcounter = 3;
            }

        }

        if (counter < 4) {

            checkforNumbers.removeAll(checkforNumbers);

            counter++;

            if (playerTurn && counter == 3) {
                player1Tries += 1;

            } else if (!playerTurn && counter == 3) {
                player2Tries += 1;

            }

            int a = (int) ((6.0 * Math.random()) + 0);
            cube1.setImage(imagearr.get(a));

            checkforNumbers.add(a + 1);
            int b = (int) ((6.0 * Math.random()) + 0);
            cube2.setImage(imagearr.get(b));
            checkforNumbers.add(b + 1);
            int c = (int) ((6.0 * Math.random()) + 0);
            cube3.setImage(imagearr.get(c));
            checkforNumbers.add(c + 1);
            int d = (int) ((6.0 * Math.random()) + 0);
            cube4.setImage(imagearr.get(d));
            checkforNumbers.add(d + 1);
            int e = (int) ((6.0 * Math.random()) + 0);
            cube5.setImage(imagearr.get(e));
            checkforNumbers.add(e + 1);

        }

        System.out.println(selectedNumbers);

        Component parentComponent = null;
        if (counter > 3) {
            JOptionPane.showMessageDialog(parentComponent, "nächster Spieler");

            if (playerTurn) {
                playerTurn = false;
                undoCubes();
                counter = 0;
                labelPlayer1.setStyle("-fx-text-fill: red;");
                labelPlayer2.setStyle("-fx-text-fill: black;");

            } else if (!playerTurn) {
                playerTurn = true;
                undoCubes();
                counter = 0;
                labelPlayer2.setStyle("-fx-text-fill: red;");
                labelPlayer1.setStyle("-fx-text-fill: black;");

            }

        }
    }

    @FXML
    private void checkDice(MouseEvent event) throws SQLException {
        int winearning = Integer.parseInt(labelAmount.getText());

        int checkCounter = 0;
        boolean checkonlyonce = false;

        if (counter == 3) {

            if (playerTurn) {

                eineraugenzahlen();
                gleiche();
                zweiPaar();
                kleineStrasse();
                grosseStrasse();
                fullHouse();
                chance();

                undoCubes();
                counter = 0;
                if (rollcounter == 0) {
                    rollcounter = 3;
                }

                playerTurn = false;

            } else if (!playerTurn) {

                eineraugenzahlen();
                gleiche();
                zweiPaar();
                kleineStrasse();
                grosseStrasse();
                fullHouse();
                chance();

                undoCubes();
                counter = 0;
                if (rollcounter == 0) {
                    rollcounter = 3;
                }

                playerTurn = true;

            }

        } else {

            alert.checkAlert();
        }

        if (player1Tries == 15 && player2Tries == 15) {

            countColumn1();
            countColumn2();

            System.out.println(sumSpieler1);
            System.out.println(sumSpieler2);

            if (sumSpieler1 > sumSpieler2) {
                alert.winAlert();
                st.setStats(winearning, winearning, 0);
                logIN.setUserBalance(logIN.getUserBalance() + winearning * 2);

            } else if (sumSpieler1 < sumSpieler2) {
                alert.winAlert2();
                st.setStats(winearning, 0, winearning);
                logIN.setUserBalance(logIN.getUserBalance() - winearning);

            } else {
                alert.winAlertBoth();
            }
//
//            if (checkonlyonce) {
//
//            }
            alert.quitorcontinueButton(true);
            undoCubes();
            newGame();
           
            counter = 0;

        }

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
        Scene scene = new Scene(root, 1280, 720);
        ch.bbbaden.casino.gruppe7.MainApp.getStage().setScene(scene);
        // NEW EDIT 15:57 FLORIAN SCHÄFER
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        ch.bbbaden.casino.gruppe7.MainApp.getStage().setX((screenBounds.getWidth() - 1280) / 2);
        ch.bbbaden.casino.gruppe7.MainApp.getStage().setY((screenBounds.getHeight() - 720) / 2);
        ch.bbbaden.casino.gruppe7.MainApp.getStage().centerOnScreen();
    }
}
