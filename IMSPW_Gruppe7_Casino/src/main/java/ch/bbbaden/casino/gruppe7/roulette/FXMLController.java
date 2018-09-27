package ch.bbbaden.casino.gruppe7.roulette;

import ch.bbbaden.casino.gruppe7.Connect_to_Database;
import ch.bbbaden.casino.gruppe7.MainApp;
import ch.bbbaden.casino.gruppe7.UserLoggedIn;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.stage.Screen;
import javafx.util.Duration;

public class FXMLController implements Initializable {

    private UserLoggedIn userloggedin = UserLoggedIn.getInstance();

    @FXML
    private Button t, to18, even, red, black, odd, to36, st12, nd12, rd12, six1, six2, six3, six4, six5, six6, three1, three2, three3, three4, three5, three6, three7, three8, three9, three10, three11, three12, st9, nd9, rd9, zero, dzero, fivenbet;
    @FXML
    private GridPane numbergrid;
    @FXML
    private Label tobet, tolabel, account, numberrolled, moneybet, setmoneyto, fromlabel, moneywon;
    @FXML
    private ImageView wheel, ball, betcoin;
    @FXML
    private AnchorPane anchpane;

    private ImageView img;
    private Button zero1;
    private Button zero2;

    Bets bets;
    Roulette r = new Roulette();
    Connect_to_Database con = new Connect_to_Database();

    private ArrayList<Button> bttnarray = new ArrayList<>();
    private List<List<String>> m;
    private Map<String, Integer> map = new HashMap<>();
    private Map colormap = new HashMap();
    private Button[] selfmadebttn = new Button[33];
    private ArrayList<ImageView> imgarray = new ArrayList<>();
    private Map<Label, Integer> spinnermap = new HashMap<>();
    private Map<Label, Integer> finalmap = new HashMap<>();
    private Map<Label, Timeline> TimeLineMap = new HashMap<>();

    private int random;
    private int uinteger = 1;
    private int moneytobet = 0;
    private int money = 10000;
    private int won = 0;
    private boolean readytoplay = true;

    private TranslateTransition transition;
    private FadeTransition ft;

    private Random randi = new Random();

    public void InitializeBoardGUI() {
        r.normalboard();
        r.mapboard();
        m = r.getM();
        bets = new Bets(r.getM());

        for (int i = 0, u = uinteger; i < 23; i += 2) {
            for (int j = 0; j < 5; j += 2) {
                if (j == 0 || j == 2 || j == 4) {
                    genBttn(j, i, Double.MAX_VALUE, Double.MAX_VALUE, numbergrid);
                    t.setId(m.get(u).get(0));
                    t.setText(m.get(u).get(0).substring(1));
                    t.setStyle("-fx-base: " + m.get(u).get(1) + ";");
                    bttnarray.add(t);
                    colormap.put(t.getId(), m.get(u).get(1));
                    u++;
                }

            }

        }

        for (int i = 0, u = uinteger; i < 23; i += 2) {
            for (int j = 1; j < 5; j += 2) {
                if (j == 1 || j == 3) {
                    genBttn(j, i, 10, 80, numbergrid);
                    t.setId(m.get(u).get(8));
                    t.setStyle("-fx-base: white;");
                    bttnarray.add(t);
                    colormap.put(t.getId(), "white");
                    u++;
                }

            }
            u++;
        }

        for (int i = 1, u = uinteger; i < 23; i += 2) {
            for (int j = 0; j < 5; j += 2) {
                if (j == 0 || j == 2 || j == 4) {
                    genBttn(j, i, 75, 10, numbergrid);
                    t.setId(m.get(u).get(10));
                    t.setStyle("-fx-base: white;");
                    bttnarray.add(t);
                    colormap.put(t.getId(), "white");
                    u++;
                }
            }
        }

        for (int i = 1, u = uinteger; i < 23; i += 2) {
            for (int j = 1; j < 5; j += 2) {
                if (j == 1 || j == 3) {
                    genBttn(j, i, 10, 10, numbergrid);
                    t.setId(m.get(u).get(14));
                    t.setStyle("-fx-base: white;");
                    bttnarray.add(t);
                    colormap.put(t.getId(), "white");
                    u++;
                }
            }
            u++;
        }
        for (int i = 0; i < selfmadebttn.length; i++) {
            selfmadebttn[i].setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        handlebuttons(event);
                    } catch (FileNotFoundException ex) {
                        Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });
            colormap.put(selfmadebttn[i].getId(), "white");
        }
    }

    public void genBttn(int x, int y, double width, double height, GridPane h) {
        t = new Button();
        t.getStyleClass().add("field");
        t.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    handlebuttons(event);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        numbergrid.setConstraints(t, x, y);
        numbergrid.getChildren().addAll(t);

        if (width == 1.7976931348623157E308) {
            t.setMaxWidth(Double.MAX_VALUE);
            t.setMaxHeight(Double.MAX_VALUE);
        } else {
            t.setMinWidth(width);
            t.setMinHeight(height);
        }
    }

    @FXML
    private void rollEx(MouseEvent event) {
        ((ImageView) event.getSource()).setImage(new Image("/styles/assets/roll.png"));
    }

    @FXML
    private void rollEn(MouseEvent event) {
        ((ImageView) event.getSource()).setImage(new Image("/styles/assets/roll2.png"));
    }

    @FXML
    private void rollIcn(MouseEvent event) throws IOException {
        if (readytoplay == true) {
            readytoplay = false;

            ft = fadeAnimation(2, numberrolled, 1.0, 0.0);
            ft.play();

            random = r.RandomNumber();

            boolean cont = true;
            try {
                won = bets.checkBets(map, random);

                map.clear();

                money = money - finalmap.get(moneybet) + won;

                userloggedin.setUserBalance(money);
            } catch (Exception e) {
                cont = false;
                System.out.println("DB ERROR");
                readytoplay = true;
            }

            if (cont == true) {
                for (Button b : bttnarray) {
                    b.setStyle("-fx-base: " + colormap.get(b.getId()).toString() + ";");
                }
                for (int i = 0; i < selfmadebttn.length; i++) {
                    selfmadebttn[i].setStyle("-fx-base: " + colormap.get(selfmadebttn[i].getId()).toString() + ";");
                }

                try {
                    setMoney();
                } catch (InterruptedException ex) {
                    Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    @FXML
    private void coinbutton(ActionEvent event) throws InterruptedException, FileNotFoundException {
        if (readytoplay == true) {
            Button button = (Button) event.getSource();
            int moneytobetalt = moneytobet;
            switch (button.getId()) {
                case "bttn1":
                    moneytobet += 10;
                    break;
                case "bttn2":
                    moneytobet += 50;
                    break;
                case "bttn3":
                    moneytobet += 100;
                    break;
                case "bttn4":
                    moneytobet += 500;
                    break;
                case "bttn5":
                    moneytobet += 1000;
                    break;
                case "bttn6":
                    moneytobet += 5000;
                    break;
            }
            if (money - moneytobet - finalmap.get(moneybet) >= 0) {
                newBet();
                int accountalt = finalmap.get(account);
                finalmap.put(account, money - moneytobet - finalmap.get(moneybet));
                finalmap.put(tobet, moneytobet);

                if (tobet.getOpacity() == 0.0) {
                    ft = fadeAnimation(2, tobet, 0.0, 1.0);
                    ft.play();
                }
                spinner(tobet, moneytobetalt);
                spinner(account, accountalt);
            } else {
                moneytobet = moneytobetalt;
            }
        }
    }

    public void handlebuttons(ActionEvent event) throws FileNotFoundException, InterruptedException {
        if (readytoplay == true) {
            Button button = (Button) event.getSource();
            if (button.getStyle() != "-fx-base: fbb03b;") {
                if (moneytobet != 0) {
                    button.setStyle("-fx-base: fbb03b;");
                    imgarray.add(img);
                    map.put(button.getId(), moneytobet);
                    finalmap.put(moneybet, (finalmap.get(moneybet) + finalmap.get(tobet)));
                    if (moneybet.getOpacity() == 0.0) {
                        ft = fadeAnimation(2, moneybet, 0.0, 1.0);
                        ft.play();
                    }
                    ft = fadeAnimation(2, tobet, 1.0, 0.0);
                    ft.play();
                    spinner(moneybet, spinnermap.get(moneybet));
                    spinner(tobet, finalmap.get(tobet));
                    moneytobet = 0;
                    finalmap.put(tobet, moneytobet);
                    setBet();
                }
            } else if (button.getStyle() == "-fx-base: fbb03b;") {
                button.setStyle("-fx-base: " + colormap.get(button.getId()).toString() + ";");
                finalmap.put(moneybet, (finalmap.get(moneybet) - map.get(button.getId())));
                finalmap.put(account, (finalmap.get(account) + map.get(button.getId())));
                map.remove(button.getId());
                spinner(moneybet, spinnermap.get(moneybet));
                spinner(account, spinnermap.get(account));
                if (imgarray.size() == 1) {
                    ft = fadeAnimation(2, moneybet, 1.0, 0.0);
                    ft.play();
                }
                removeBet(imgarray.get(0));
            }
        }
    }

    public void array() {
        Button[] wastearray = {to18, even, red, black, odd, to36, st12, nd12, rd12, six1, six2, six3, six4, six5, six6, three1, three2, three3, three4, three5, three6, three7, three8, three9, three10, three11, three12, st9, nd9, rd9, zero1, zero2, fivenbet};
        for (int i = 0; i < selfmadebttn.length; i++) {
            selfmadebttn[i] = wastearray[i];
            selfmadebttn[i].getStyleClass().add("field");
        }
    }

    public void wheel(double angle) throws InterruptedException {
        RotateTransition rt = new RotateTransition(javafx.util.Duration.seconds(4), wheel);
        RotateTransition bt = new RotateTransition(javafx.util.Duration.seconds(4), ball);

        rt.setToAngle(1440 + (angle / 2));
        bt.setToAngle(-1080 - (angle / 2));
        rt.setInterpolator(Interpolator.EASE_BOTH);
        bt.setInterpolator(Interpolator.EASE_OUT);
        bt.setCycleCount(1);
        rt.setCycleCount(1);

        bt.setOnFinished(e -> {
            ft = fadeAnimation(2, numberrolled, 0.0, 1.0);
            ft.play();
            if (random == 0) {
                numberrolled.setText("00");
            } else if (random == 37) {
                numberrolled.setText("0");
            } else {
                numberrolled.setText(random + "");
            }
            numberrolled.setTextFill(Paint.valueOf(m.get(random).get(1)));
            try {
                getMoney();
            } catch (InterruptedException ex) {
                Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        rt.play();
        bt.play();
    }

    public double switchWheel(int i) {
        double angle = 0.0;
        switch (i) {
            case 0:
                angle = -5;
                break;
            case 1:
                angle = -358;
                break;
            case 2:
                angle = -175;
                break;
            case 3:
                angle = -320;
                break;
            case 4:
                angle = -140;
                break;
            case 5:
                angle = -282;
                break;
            case 6:
                angle = -102;
                break;
            case 7:
                angle = -242;
                break;
            case 8:
                angle = -65;
                break;
            case 9:
                angle = -205;
                break;
            case 10:
                angle = -25;
                break;
            case 11:
                angle = -235;
                break;
            case 12:
                angle = -55;
                break;
            case 13:
                angle = -348;
                break;
            case 14:
                angle = -165;
                break;
            case 15:
                angle = -310;
                break;
            case 16:
                angle = -130;
                break;
            case 17:
                angle = -272;
                break;
            case 18:
                angle = -92;
                break;
            case 19:
                angle = -75;
                break;
            case 20:
                angle = -252;
                break;
            case 21:
                angle = -112;
                break;
            case 22:
                angle = -292;
                break;
            case 23:
                angle = -145;
                break;
            case 24:
                angle = -330;
                break;
            case 25:
                angle = -35;
                break;
            case 26:
                angle = -215;
                break;
            case 27:
                angle = -15;
                break;
            case 28:
                angle = -195;
                break;
            case 29:
                angle = -45;
                break;
            case 30:
                angle = -225;
                break;
            case 31:
                angle = -82;
                break;
            case 32:
                angle = -262;
                break;
            case 33:
                angle = -120;
                break;
            case 34:
                angle = -300;
                break;
            case 35:
                angle = -155;
                break;
            case 36:
                angle = -338;
                break;
            case 37:
                angle = -185;
                break;
        }
        return angle;
    }

    public TranslateTransition moveAnimation(double timeInSec, ImageView imgview, double FromX, double FromY, double ToX, double ToY) {
        transition = new TranslateTransition(javafx.util.Duration.seconds(timeInSec), imgview);
        if (FromX != -999) {
            transition.setFromX(FromX);
        }
        if (FromY != -999) {
            transition.setFromY(FromY);
        }
        if (ToX != -999) {
            transition.setToX(ToX);
        }
        if (ToY != -999) {
            transition.setToY(ToY);
        }
        return transition;
    }

    public FadeTransition fadeAnimation(double timeInSec, Node imgview, double from, double to) {
        ft = new FadeTransition(Duration.seconds(2), imgview);
        ft.setFromValue(from);
        ft.setToValue(to);
        return ft;
    }

    public void newBet() throws InterruptedException, FileNotFoundException {
        transition = moveAnimation(2.5, genImageView(), betcoin.getLayoutX(), fromlabel.getLayoutY(), betcoin.getLayoutX(), betcoin.getLayoutY());
        ft = fadeAnimation(2, img, 0.0, 1.0);

        transition.play();
        ft.play();
    }

    public void setBet() throws InterruptedException {
        transition = moveAnimation(4, imgarray.get(imgarray.size() - 1), -999, -999, tolabel.getLayoutX() - genLayout(130, 205), genLayout((int) tolabel.getLayoutY() - 230, (int) (tolabel.getLayoutY() - 170)));
        transition.play();
    }

    public void removeBet(ImageView img) {
        imgarray.remove(0);

        transition = moveAnimation(4, img, -999, -999, betcoin.getLayoutX(), betcoin.getLayoutY());
        transition.setOnFinished(e -> {
            anchpane.getChildren().remove(img);
        });
        transition.play();
    }

    public void setMoney() throws InterruptedException {
        double animationTime = 2.0;
        finalmap.put(moneybet, 0);

        ParallelTransition p = new ParallelTransition();
        FadeTransition ft = fadeAnimation(animationTime, moneybet, 1.0, 0.0);
        Timeline tl = spin(moneybet, finalmap.get(moneybet));

        if (finalmap.get(tobet) > 0) {
            finalmap.put(tobet, 0);
            finalmap.put(account, finalmap.get(account) + Integer.parseInt(tobet.getText()));
            spinner(tobet, Integer.parseInt(tobet.getText()));
            spinner(account, Integer.parseInt(account.getText()));
        }

        for (ImageView i : imgarray) {
            transition = moveAnimation(animationTime, i, -999, -999, setmoneyto.getLayoutX(), setmoneyto.getLayoutY());
            p.getChildren().add(transition);
        }

        p.setOnFinished(e -> {
            anchpane.getChildren().removeAll(imgarray);
            imgarray.removeAll(imgarray);

            ball.setRotate(0);
            wheel.setRotate(0);
            try {
                wheel(switchWheel(random));
            } catch (InterruptedException ex) {
                Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        p.play();
        if (won != 0) {
            ft.play();
        }
        tl.playFromStart();
    }

    public void getMoney() throws InterruptedException {
        if (won != 0) {
            ft = fadeAnimation(4, moneywon, 0.0, 1.0);

            moneywon.setText("" + won);

            finalmap.put(account, finalmap.get(account) + won);
            finalmap.put(moneywon, 0);

            ft.setOnFinished(e -> {
                ft = fadeAnimation(8, moneywon, 1.0, 0.0);

                ft.setOnFinished(b -> {
                    cleanUp();
                });
                try {
                    spinner(moneywon, won);
                    spinner(account, spinnermap.get(account));
                } catch (InterruptedException ex) {
                    Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
                }
                ft.play();
            });
            ft.play();
        } else {
            cleanUp();
        }
    }

    public ImageView genImageView() throws FileNotFoundException {
        FileInputStream input = new FileInputStream("src\\main\\resources\\Roulette_img\\coins.png");
        Image image = new Image(input);

        img = new ImageView(image);
        img.setFitHeight(betcoin.getFitHeight());
        img.setFitWidth(betcoin.getFitWidth());
        anchpane.getChildren().addAll(img);
        anchpane.getChildren().get(anchpane.getChildren().indexOf(img)).toBack();
        return img;
    }

    public int genLayout(int lower, int upper) {
        int randomNum = randi.nextInt(upper - lower + 1) + lower;
        return randomNum;
    }

    public void spinner(Label label, int from) throws InterruptedException {
        Timeline tl = spin(label, from);
        tl.playFromStart();
    }

    public Timeline spin(Label label, int from) {
        try {
            TimeLineMap.get(label).stop();
        } catch (Exception e) {
        }
        Timeline tl = new Timeline();
        TimeLineMap.put(label, tl);
        tl.setCycleCount(Timeline.INDEFINITE);
        tl.getKeyFrames().add(
                new KeyFrame(Duration.millis(7),
                        new EventHandler() {
                    public void handle(Event event) {
                        spinnerhandle(tl, label);
                    }
                }));
        return tl;
    }

    public void spinnerhandle(Timeline tl, Label l) {
        int from = spinnermap.get(l);
        int to = finalmap.get(l);
        int pullout = 4;
        int step;
        int diff = (int) Math.sqrt(Math.pow((to - from), 2));
        if (diff < 101) {
            step = 1;
        } else if (diff < 500) {
            step = 2;
        } else if (diff < 5001) {
            step = 5;
        } else if (diff < 15001) {
            step = 10;
        } else if (diff < 50001) {
            step = 15;
        } else if (diff < 100001) {
            step = 25;
        } else if (diff < 500000) {
            step = 40;
        } else {
            step = 50;
        }

        if (from < to) {
            pullout = 0;
            from += step;
            l.setTextFill(Paint.valueOf("green"));
        } else if (from > to) {
            pullout = 1;
            from -= step;
            l.setTextFill(Paint.valueOf("red"));
        } else if (to == from) {
            pullout = 2;
        }
        spinnermap.put(l, from);
        l.setText("" + spinnermap.get(l));
        if (from == to && pullout == 0 || from == to && pullout == 1 || pullout == 2) {
            spinnermap.put(l, to);
            l.setText("" + spinnermap.get(l));
            l.setTextFill(Paint.valueOf("fbb03b"));
            tl.stop();
        }
    }

    public void cleanUp() {
        spinnermap.put(moneywon, 0);
        spinnermap.put(account, money);
        spinnermap.put(moneybet, 0);
        spinnermap.put(tobet, 0);
        spinnermap.put(moneywon, 0);

        finalmap.put(tobet, 0);
        finalmap.put(account, money);
        finalmap.put(moneybet, 0);
        finalmap.put(moneywon, 0);

        moneytobet = 0;
        readytoplay = true;
    }

    @FXML
    private void back(ActionEvent event) throws IOException {
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
        MainApp.getStage().setX((screenBounds.getWidth() - 1280) / 2);
        MainApp.getStage().setY((screenBounds.getHeight() - 720) / 2);
        MainApp.getStage().centerOnScreen();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        zero1 = zero;
        zero2 = dzero;
        zero1.setId("00");
        zero2.setId("0");

        try {
            money = userloggedin.getUserBalance();
        } catch (SQLException ex) {
            Logger.getLogger(FXMLController.class.getName()).log(Level.SEVERE, null, ex);
        }

        finalmap.put(account, money);

        finalmap.put(moneybet,
                0);
        finalmap.put(tobet,
                0);
        finalmap.put(moneywon,
                0);
        spinnermap.put(account, money);

        spinnermap.put(moneybet,
                0);
        spinnermap.put(tobet,
                0);
        spinnermap.put(moneywon,
                0);
        account.setText(
                "" + money);
        array();

        InitializeBoardGUI();
    }
}
