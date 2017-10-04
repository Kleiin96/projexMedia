/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author bruneaje
 */
public class MainMenu extends Application {
    
    GestionnaireSite _TabSite = new GestionnaireSite();
    Scene _scene;
    
    @Override
    public void start(Stage primaryStage) {
        Tab client = new Tab("Client");
        Tab site = _TabSite.getSiteTab();
        _TabSite.createPane();
        
        client.setClosable(false);
        site.setContent(_TabSite.getSitePane());
        site.setClosable(false);
        
        _scene.getStylesheets().add("stylesheet.css");
        //site.getStyleClass().add("siteTab");
        
        TabPane tabPane = new TabPane();
        tabPane.setPrefSize(750, 600);
        
        tabPane.getTabs().add(0, client);
        tabPane.getTabs().add(site);
        
        
        _scene = new Scene(new VBox( tabPane));
        primaryStage.setScene(_scene);
        primaryStage.show();
    }

    public Scene getScene() {
        return _scene;
    }
    
    

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
