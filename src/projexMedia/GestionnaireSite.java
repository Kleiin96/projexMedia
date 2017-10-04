/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projexMedia;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;

/**
 *
 * @author houdeto
 */
public class GestionnaireSite {
    
    private Tab _siteTab;
    private Pane _sitePane;

    public GestionnaireSite() {
        _siteTab = new Tab("Site");
        _sitePane = new Pane();
    }
    
    public void createPane(){
        Button btnAjouter = new Button();
        Button btnModifier = new Button();
        Button btnArchiver = new Button();
        
        btnAjouter.setText("Ajouter");
        btnAjouter.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                
            }
        });
        btnModifier.setText("Modifier");
        btnModifier.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });
        btnArchiver.setText("Archiver");
        btnArchiver.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });
        
        btnAjouter.setLayoutX(30);
        btnAjouter.setLayoutY(60);
        btnModifier.setLayoutX(30);
        btnModifier.setLayoutY(100);
        btnArchiver.setLayoutX(30);
        btnArchiver.setLayoutY(140);
        
        btnAjouter.setMinHeight(35);
        btnAjouter.setMinWidth(100);
        btnModifier.setMinHeight(35);
        btnModifier.setMinWidth(100);
        btnArchiver.setMinHeight(35);
        btnArchiver.setMinWidth(100);
        
        _sitePane.getChildren().add(btnAjouter);
        _sitePane.getChildren().add(btnModifier);
        _sitePane.getChildren().add(btnArchiver);
    }

    public void setSitePane(Pane sitePane) {
        _sitePane = sitePane;
    }

    public Pane getSitePane() {
        return _sitePane;
    }

    public void setSiteTab(Tab siteTab) {
        _siteTab = siteTab;
    }

    public Tab getSiteTab() {
        return _siteTab;
    }
}
