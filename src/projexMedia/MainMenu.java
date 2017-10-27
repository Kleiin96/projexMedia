/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projexMedia;

import java.io.IOException;
import java.sql.SQLException;

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
    GestionnaireClient _TabClient = new GestionnaireClient();
    GestionnaireServeur _TabServeur = new GestionnaireServeur();
    Scene _scene;
    int _activeTab;
   
    @Override
    public void start(Stage primaryStage) throws Exception {
    	primaryStage.setTitle("Gestionnaire ProjexMedia");
        Tab client = _TabClient.getClientTab();
        Tab site = _TabSite.getSiteTab();
        Tab serveur = new Tab()/*_TabServeur.getServeurTab()*/;
        _TabServeur.createPane(primaryStage);
        _TabSite.createPane(primaryStage);
        _TabClient.createPane(primaryStage);
       
        client.setClosable(false);
        client.setContent(_TabClient.getClientPane());
        site.setContent(_TabSite.getSitePane());
        site.setClosable(false);
        serveur.setClosable(false);
        serveur.setContent(_TabServeur.getServeurPane());
                   
        TabPane tabPane = new TabPane();
        tabPane.setPrefSize(750, 650);
        
        tabPane.getTabs().add(0, client);
        tabPane.getTabs().add(site);
        tabPane.getTabs().add(serveur);
        
        if(_activeTab == 1) {
        	tabPane.getSelectionModel().select(site);
        }
        
        _scene = new Scene(new VBox( tabPane));
        _scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        site.getStyleClass().add("Tab");
        client.getStyleClass().add("Tab");        
        primaryStage.setScene(_scene);
        primaryStage.show();
        javafx.geometry.Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
		primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
		primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
    }

    public Scene getScene() {
        return _scene;
    }

    public GestionnaireSite get_TabSite() {
		return _TabSite;
	}

	public void set_TabSite(GestionnaireSite _TabSite) {
		this._TabSite = _TabSite;
	}

	public Scene get_scene() {
		return _scene;
	}

	public void set_scene(Scene _scene) {
		this._scene = _scene;
	}

	public int get_activeTab() {
		return _activeTab;
	}

	public void set_activeTab(int _activeTab) {
		this._activeTab = _activeTab;
	}

	/**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
