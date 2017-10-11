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
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
/**
 *
 * @author bruneaje
 */
public class Consulter extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception{
    Tab tab1 = new Tab("Client");
    Tab tab2 = new Tab("Site");
    GestionnaireClient tabClient = new GestionnaireClient();
    //Service test = new Service();
    //test.CreateGroupActif();
    
    tabClient.createPane(primaryStage);
    
    tab1.setClosable(false);
    
    tab1.setContent(tabClient.getClientPane());
    
    
    tab2.setClosable(false);
    
    

    TabPane tabPane = new TabPane();
    tabPane.setPrefSize(700, 700);
 
    tabPane.getTabs().add(0, tab1);
   
    tabPane.getTabs().add(tab2);
    
    //panel Client
    

    Scene scene = new Scene(new VBox( tabPane));

    primaryStage.setScene(scene);
    primaryStage.show();
        
    }
}
