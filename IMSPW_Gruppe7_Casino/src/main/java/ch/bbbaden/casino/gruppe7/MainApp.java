package ch.bbbaden.casino.gruppe7;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MainApp extends Application {

    private static Stage stage;

    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/Scene.fxml"));

        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Styles.css");
        scene.getStylesheets().add("/styles/Yatzy.css");
        scene.getStylesheets().add("/styles/tableviewstyle.css");
        scene.getStylesheets().add("/styles/Baccarat_Style.css");
        scene.getStylesheets().add("/styles/BlackJack_Style.css");

        stage.setResizable(false);
        stage.setTitle("Casino");
        stage.setScene(scene);
        stage.show();
        
        
        
    }

    public static Stage getStage() {
        return stage;
    }

}
