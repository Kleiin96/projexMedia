/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projexMedia;

import java.io.IOException;
import java.sql.SQLException;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
    
    GestionnaireSite _TabSite;
    GestionnaireClient _TabClient;
    GestionnaireServeur _TabServeur;
    GestionnaireUtilisateur _TabUtilisateur;
    GestionnaireParametreService _TabParServ;
    GestionnaireRole _TabRole;
    Scene _scene;
    int _activeTab;
    public static String _username;
    public static Role _role;
    
    public MainMenu() {
    	_TabSite = new GestionnaireSite();
        _TabClient = new GestionnaireClient();
        _TabServeur = new GestionnaireServeur();
        _TabUtilisateur = new GestionnaireUtilisateur();
        _TabParServ = new GestionnaireParametreService();
        _TabRole = new GestionnaireRole();
    }
    
    public MainMenu(String username, int id_role) throws SQLException {
    	_username = username;
    	_role = new Role(id_role);
    	_TabSite = new GestionnaireSite();
        _TabClient = new GestionnaireClient();
        _TabServeur = new GestionnaireServeur();
        _TabUtilisateur = new GestionnaireUtilisateur();
        _TabParServ = new GestionnaireParametreService();
        _TabRole = new GestionnaireRole();
    }
   
    @Override
    public void start(Stage primaryStage) throws Exception {
    	primaryStage.setTitle("Gestionnaire ProjexMedia");
        Tab client = _TabClient.getClientTab();
        Tab site = _TabSite.getSiteTab();
        Tab serveur = _TabServeur.getServeurTab();
        Tab servPar = _TabParServ.get_parservTab();
        Tab user = _TabUtilisateur.getUtilisateurTab();
        Tab role = _TabRole.getRoleTab();
               
        _TabServeur.createPane(primaryStage);
        _TabSite.createPane(primaryStage);
        _TabClient.createPane(primaryStage);
        _TabParServ.createPane(primaryStage);
        
        client.setClosable(false);
        client.setContent(_TabClient.getClientPane());
        site.setContent(_TabSite.getSitePane());
        site.setClosable(false);
        serveur.setClosable(false);
        serveur.setContent(_TabServeur.getServeurPane());
        servPar.setClosable(false);
        servPar.setContent(_TabParServ.get_pane());
                   
        TabPane tabPane = new TabPane();
        tabPane.setPrefSize(750, 650);
        
        tabPane.getTabs().add(0, client);
        tabPane.getTabs().add(site);
        tabPane.getTabs().add(serveur);
        tabPane.getTabs().add(servPar);
        
        if(_role.get_utilisateur().equals("X")) {
        	
            _TabUtilisateur.createPane(primaryStage);
            user.setClosable(false);
            user.setContent(_TabUtilisateur.getUtilisateurPane());
            tabPane.getTabs().add(user);
        }

        if(_role.get_droitRole().equals("X")) {
        	
            _TabRole.createPane(primaryStage);
            role.setClosable(false);
            role.setContent(_TabRole.getRolePane());
            tabPane.getTabs().add(role);
        }
        
        if(_activeTab == 1) {
        	tabPane.getSelectionModel().select(site);
        }
        
        if(_activeTab == 2) {
        	tabPane.getSelectionModel().select(serveur);
        }
        
        if(_activeTab == 3) {
        	tabPane.getSelectionModel().select(user);
        }
        if(_activeTab == 4 ) {
        	tabPane.getSelectionModel().select(servPar);
        }
        if(_activeTab == 5 ) {
        	tabPane.getSelectionModel().select(role);
        }
        
        tabPane.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
                _TabClient.setDoubleClick();
                try {
					_TabSite.createPane(primaryStage);
				} catch (ClassNotFoundException | IOException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        });
        
        _scene = new Scene(new VBox( tabPane));
        _scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        site.getStyleClass().add("Tab");
        client.getStyleClass().add("Tab");      
        serveur.getStyleClass().add("Tab");
        user.getStyleClass().add("Tab");
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
