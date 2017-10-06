/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projexMedia;

import java.util.ArrayList;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author bruneaje
 */
public class GestionnaireClient {
    Client _test;
    Tab _clientTab;
    Pane _pane;
    
    public GestionnaireClient() {
        _test = new Client();
        _clientTab = new Tab("Client", new Label("Salut"));
        _pane = new Pane();
    }
    
    public GestionnaireClient(Client _test, Tab _clientTab, Pane _pane) {
        this._test = _test;
        this._clientTab = _clientTab;
        this._pane =_pane;
    }

    public Client getTest() {
        return _test;
    }

    public Tab getClientTab() {
        return _clientTab;
    }
    
    public Pane getClientPane() {
    	return _pane;
    }

    public void setTest(Client _test) {
        this._test = _test;
    }

    public void setClientTab(Tab _clientTab) {
        this._clientTab = _clientTab;
    }
    
    public void createPane(Stage primaryStage){
    	
    	ObservableList<String> data =
    	        FXCollections.observableArrayList(
    	            new String("Tomy-phillip") 
    	        ); 
    	
    	 Button btnAjouter = new Button();
         Button btnModifier = new Button();
         Button btnArchiver = new Button();
         Button btnConsArch = new Button();
         TableView<String> tableClient = new TableView<>(/*data*/);
         
         //List<String> list = new ArrayList<String>();
         /*ObservableList<String> data =
        	        FXCollections.observableArrayList(
        	            new String("Tomy-phillip") 
        	        ); */
        tableClient.setItems(data);
         
         
         btnAjouter.setText("Ajouter");
         btnAjouter.setOnAction(new EventHandler<ActionEvent>() {
             
             @Override
             public void handle(ActionEvent event) {
                 ajouterClient(primaryStage);
             }
         });
         btnModifier.setText("Modifier");
         btnModifier.setOnAction(new EventHandler<ActionEvent>() {
             
             @Override
             public void handle(ActionEvent event) {
                 modifierClient(primaryStage);
             }
         });
         btnArchiver.setText("Archiver");
         btnArchiver.setOnAction(new EventHandler<ActionEvent>() {
             
             @Override
             public void handle(ActionEvent event) {
                 System.exit(0);
             }
         });
         
         btnConsArch.setText("Consulter Archive");
         btnConsArch.setOnAction(new EventHandler<ActionEvent>() {
             
             @Override
             public void handle(ActionEvent event) {
                 System.exit(0);
             }
         });
         
         //table column
         
         TableColumn  client = new TableColumn("Client");
         
         tableClient.getColumns().addAll(client);
         tableClient.getItems().add("cojt");
         
         
         
         btnAjouter.setLayoutX(30);
         btnAjouter.setLayoutY(60);
         btnModifier.setLayoutX(30);
         btnModifier.setLayoutY(100);
         btnArchiver.setLayoutX(30);
         btnArchiver.setLayoutY(140);
         btnConsArch.setLayoutX(30);
         btnConsArch.setLayoutY(340);
         
         btnAjouter.setMinHeight(35);
         btnAjouter.setMinWidth(100);
         btnModifier.setMinHeight(35);
         btnModifier.setMinWidth(100);
         btnArchiver.setMinHeight(35);
         btnArchiver.setMinWidth(100);
         btnConsArch.setMinHeight(35);
         btnConsArch.setMinWidth(100);
         
         //layout list
         tableClient.setLayoutX(150);
         tableClient.setLayoutY(60);
         
         _pane.getChildren().add(tableClient);
         _pane.getChildren().add(btnAjouter);
         _pane.getChildren().add(btnModifier);
         _pane.getChildren().add(btnArchiver);
         _pane.getChildren().add(btnConsArch);
    }
    
    
    public void ajouterClient(Stage primaryStage){
    	 //bouton
        Button btn = new Button("Ajouter");
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
        primaryStage.setTitle("Ajouter Client");
        primaryStage.setScene(scene);
        primaryStage.show();
        
    }
    
    public void modifierClient(Stage primaryStage){
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
        Label lbl1 = new Label("TÃ©lÃ©phone");
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
    
    public void modifierNomClient(){
        
    }
    
    public void modifierTelephoneClient(){
        
    }
    
    public void modifierNomResponsableClient(){
        
    }
    
    public void archiverClient(){
        
    }
    
    public void activerClient(){
        
    }
    
    public void rechercherClient(){
        
    }
    
}
