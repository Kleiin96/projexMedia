/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projexMedia;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author houdeto
 */
public class MainLogin extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        
        Button btn = new Button();
        Button btn2 = new Button();
        Label lbl = new Label();
        Label lbl2 = new Label();
        TextField txt = new TextField();
        PasswordField pwd = new PasswordField();
        
        btn.setText("Connexion");
        btn.setOnAction(new EventHandler<ActionEvent>() {
            
        	@Override
            public void handle(ActionEvent event) {
                MainMenu menu = new MainMenu();
                menu.start(primaryStage);
            }
        });
        btn2.setText("Quitter");
        btn2.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });
        
        lbl.setText("Nom d'utilisateur :");
        lbl2.setText("Mot de passe :");
        lbl.setScaleX(1.5);
        lbl.setScaleY(1.5);
        lbl2.setScaleX(1.5);
        lbl2.setScaleY(1.5);
        
        Pane root = new Pane();
        
        btn.setLayoutX(90);
        btn.setLayoutY(220);
        btn2.setLayoutX(240);
        btn2.setLayoutY(220);
        lbl.setLayoutX(50);
        lbl.setLayoutY(60);
        lbl2.setLayoutX(45);
        lbl2.setLayoutY(135);
        txt.setLayoutX(215);
        txt.setLayoutY(58);
        pwd.setLayoutX(215);
        pwd.setLayoutY(133);
        
        btn.setMinHeight(35);
        btn.setMinWidth(100);
        btn2.setMinHeight(35);
        btn2.setMinWidth(100);
        
        root.getChildren().add(btn);
        root.getChildren().add(btn2);
        root.getChildren().add(lbl);
        root.getChildren().add(lbl2);
        root.getChildren().add(txt);
        root.getChildren().add(pwd);
        
        Scene scene = new Scene(root, 420, 300);
        
        primaryStage.setTitle("Gestionnaire ProjexMedia");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
