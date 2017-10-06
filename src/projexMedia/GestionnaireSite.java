/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projexMedia;

import java.awt.Insets;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author houdeto
 */
public class GestionnaireSite {
    
    private Tab _siteTab;
    private Pane _sitePane;
    
    public ObservableList<Site> data =
	        FXCollections.observableArrayList(
	            new Site(1, "Smith"),
	            new Site(2, "allo")
	);

    public GestionnaireSite() {
        _siteTab = new Tab("Site");
        _sitePane = new Pane();
    }
    
    @SuppressWarnings("unchecked")
	public void createPane(Stage primaryStage){
    	
    	TableView<Site> _table = new TableView<Site>();
    	TableColumn<Site,Integer> Col = new TableColumn<Site,Integer>("Nom du site");
    	TableColumn<Site,String> Col2 = new TableColumn<Site,String>("Compagnie");
        Button btnAjouter = new Button();
        Button btnModifier = new Button();
        Button btnArchiver = new Button();
        Button btnConsulter = new Button();
        
        btnAjouter.setText("Ajouter");
        btnAjouter.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
            	AjouterSite(primaryStage);
            }
        });
        btnModifier.setText("Modifier");
        btnModifier.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
            }
        });
        btnArchiver.setText("Archiver");
        btnArchiver.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
            }
        });
        
        btnConsulter.setText("Consulter archives");
        btnConsulter.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
            }
        });
        
        _table.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override 
            public void handle(MouseEvent event) {
                if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
                    Node node = ((Node) event.getTarget()).getParent();
                    TableRow row;
                    if (node instanceof TableRow) {
                        row = (TableRow) node;
                    } else {
                        // clicking on text part
                        row = (TableRow) node.getParent();
                    }
                    //System.out.println(row.getItem());
                    //AjouterSite(primaryStage);
                }
            }
        });
        
        _table.setEditable(false);
        _table.setPrefSize(525, 525);
        
        Col.setCellValueFactory(new PropertyValueFactory<Site, Integer>("idSite"));
        Col2.setCellValueFactory(new PropertyValueFactory<Site, String>("url"));
        _table.getColumns().addAll(Col,Col2);
        _table.setItems(data);
        Col.prefWidthProperty().bind(_table.widthProperty().divide(2));
        Col2.prefWidthProperty().bind(_table.widthProperty().divide(2));
        _table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
       
        btnAjouter.setLayoutX(30);
        btnAjouter.setLayoutY(40);
        btnModifier.setLayoutX(30);
        btnModifier.setLayoutY(120);
        btnArchiver.setLayoutX(30);
        btnArchiver.setLayoutY(200);
        btnConsulter.setLayoutX(30);
        btnConsulter.setLayoutY(480);
        _table.setLayoutX(200);
        _table.setLayoutY(20);
        
        btnAjouter.setMinHeight(50);
        btnAjouter.setMinWidth(150);
        btnModifier.setMinHeight(50);
        btnModifier.setMinWidth(150);
        btnArchiver.setMinHeight(50);
        btnArchiver.setMinWidth(150);
        btnConsulter.setMinHeight(50);
        btnConsulter.setMinWidth(150);
        
        _sitePane.getChildren().add(btnAjouter);
        _sitePane.getChildren().add(btnModifier);
        _sitePane.getChildren().add(btnArchiver);
        _sitePane.getChildren().add(btnConsulter);
        _sitePane.getChildren().add(_table);
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
    
    public void AjouterSite(Stage primaryStage) {
        
        //bouton
         Button btn = new Button("Ajouter");
         Button btn1 = new Button("Cancel");
         
         //textField
         TextField tf = new TextField();
         TextField tf1 = new TextField();
         TextField tf2 =new TextField();
         
         //label
         Label lbl = new Label("Serveur :");
         Label lbl1 = new Label("Client :");
         Label lbl2 = new Label("Url :");
         
         //panel
         Pane root = new Pane();
        
         //premier champ
         lbl.setLayoutX(50);
         lbl.setLayoutY(70);
         tf.setLayoutX(200);
         tf.setLayoutY(70);
         
         //deuxieme champ
         lbl1.setLayoutX(50);
         lbl1.setLayoutY(120);
         tf1.setLayoutX(200);
         tf1.setLayoutY(120);
         
         //troisieme champ
         lbl2.setLayoutX(50);
         lbl2.setLayoutY(170);
         tf2.setLayoutX(200);
         tf2.setLayoutY(170);
         
         //Bouton
         btn.setLayoutX(50);
         btn.setLayoutY(235);
         btn1.setLayoutX(250);
         btn1.setLayoutY(235);
         
         btn.setMinHeight(30);
         btn.setMinWidth(130);
         btn1.setMinHeight(30);
         btn1.setMinWidth(130);
         
         //add to panel
         root.getChildren().add(lbl);
         root.getChildren().add(lbl1);
         root.getChildren().add(lbl2);
         root.getChildren().add(tf);
         root.getChildren().add(tf1);
         root.getChildren().add(tf2);
         root.getChildren().add(btn);
         root.getChildren().add(btn1);
         
         //create window
         Scene scene = new Scene(root, 450, 300); 
         primaryStage.setTitle("Ajouter Site");
         primaryStage.setScene(scene);
         primaryStage.show();
     }
}
