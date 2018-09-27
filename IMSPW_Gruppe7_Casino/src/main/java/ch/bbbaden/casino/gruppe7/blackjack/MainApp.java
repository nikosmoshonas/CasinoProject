/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.bbbaden.casino.gruppe7.blackjack;

/**
 *
 * @author flori
 */
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;

public class MainApp extends Application {

  
    private FXMLController controller;
    public static Stage stage;
    
  //  
    @Override
    public void start(Stage stage) throws Exception {

        this.stage = stage;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/Blackjack_Scene.fxml"));

        Parent root;
        root = fxmlLoader.load();

        controller = fxmlLoader.getController();
       
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/styles/Blackjack_Style.css");

        stage.setTitle("BlackJack");
        stage.setScene(scene);

        stage.show();
        
        
      

    }

    public static void main(String[] args) {
        launch(args);
        
    }


    
}
