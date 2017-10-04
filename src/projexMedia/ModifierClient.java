/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projexMedia;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author bruneaje
 */
public class ModifierClient extends Application{
    @Override
    public void start(Stage primaryStage) {
    //bouton
        Button btn = new Button("Modifier");
        Button btn1 = new Button("Cancel");
        
        //textField
        TextField tf = new TextField();
        TextField tf1 = new TextField();
        TextField tf2 =new TextField();
        TextField tf3 =new TextField();
        TextField tf4 = new TextField();
        
        //label
        Label lbl = new Label("Nom de la compagnie");
        Label lbl1 = new Label("Téléphone");
        Label lbl2 = new Label("Adresse");
        Label lbl3 = new Label("Personne Responsable");
        Label lbl4 = new Label("Courriel");
        
        //panel
        Pane root = new Pane();
        //BorderPane root1 = new BorderPane();
       
        //premier champ
        lbl.setLayoutX(50);
        lbl.setLayoutY(100);
        tf.setLayoutX(200);
        tf.setLayoutY(100);
        
        //deuxieme champ
        lbl1.setLayoutX(50);
        lbl1.setLayoutY(150);
        tf1.setLayoutX(200);
        tf1.setLayoutY(150);
        
        //troisieme champ
        lbl2.setLayoutX(50);
        lbl2.setLayoutY(200);
        tf2.setLayoutX(200);
        tf2.setLayoutY(200);
        
        //quatrieme champ
        lbl3.setLayoutX(50);
        lbl3.setLayoutY(250);
        tf3.setLayoutX(200);
        tf3.setLayoutY(250);
        
        //cinquieme champ
        lbl4.setLayoutX(50);
        lbl4.setLayoutY(300);
        tf4.setLayoutX(200);
        tf4.setLayoutY(300);
        
        //Bouton
        btn.setLayoutX(50);
        btn.setLayoutY(350);
        btn1.setLayoutX(275);
        btn1.setLayoutY(350);
        
        //add to panel
        root.getChildren().add(lbl);
        root.getChildren().add(lbl1);
        root.getChildren().add(lbl2);
        root.getChildren().add(lbl3);
        root.getChildren().add(lbl4);
        root.getChildren().add(tf);
        root.getChildren().add(tf1);
        root.getChildren().add(tf2);
        root.getChildren().add(tf3);
        root.getChildren().add(tf4);
        root.getChildren().add(btn);
        root.getChildren().add(btn1);
        
        //create window
        Scene scene = new Scene(root, 450, 400); 
        primaryStage.setTitle("Modifier Client");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
