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
    GestionnaireHistorique _TabHistorique;
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
        _TabHistorique = new GestionnaireHistorique();
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
        _TabHistorique = new GestionnaireHistorique();
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
        Tab historique =_TabHistorique.get_historiqueTab();
               
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
        tabPane.setPrefSize(MainLogin.bounds.getWidth(), MainLogin.bounds.getHeight());
        
        tabPane.getTabs().add(0, client);
        tabPane.getTabs().add(site);
        tabPane.getTabs().add(serveur);
        
        if(_role.get_gestionService().equals("X")) {
        	_TabParServ.createPane(primaryStage);
        	 servPar.setClosable(false);
             servPar.setContent(_TabParServ.get_pane());
            tabPane.getTabs().add(servPar);
        }
        
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
        
        if(_role.get_historique().equals("X")) {
        	_TabHistorique.createPane(primaryStage);
        	historique.setClosable(false);
            historique.setContent(_TabHistorique.get_pane());
            tabPane.getTabs().add(historique);
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
        if(_activeTab == 6 ) {
        	tabPane.getSelectionModel().select(historique);
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
        role.getStyleClass().add("Tab");
        servPar.getStyleClass().add("Tab");
        historique.getStyleClass().add("Tab");
        tabPane.getStyleClass().add("font");
        primaryStage.setScene(_scene);
        primaryStage.setX(MainLogin.bounds.getMinX());
        primaryStage.setY(MainLogin.bounds.getMinY());
        primaryStage.setWidth(MainLogin.bounds.getWidth());
        primaryStage.setHeight(MainLogin.bounds.getHeight());
        primaryStage.show();
        
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
    
}
