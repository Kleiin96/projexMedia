/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projexMedia;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 *
 * @author houdeto
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
                   
        TabPane tabPane = new TabPane();
        tabPane.setPrefSize(750, 600);
        
        tabPane.getTabs().add(0, client);
        tabPane.getTabs().add(site);
        
        
        _scene = new Scene(new VBox( tabPane));
        _scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        site.getStyleClass().add("Tab");
        primaryStage.setScene(_scene);
        primaryStage.show();
        javafx.geometry.Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
		primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
		primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
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
