/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projexMedia;

import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.Pane;

/**
 *
 * @author bruneaje
 */
public class GestionnaireClient {
    Client _test;
    Tab _clientTab;
    
    public GestionnaireClient() {
        _test = new Client();
        _clientTab = new Tab("Client", new Label("Salut"));
    }
    
    public GestionnaireClient(Client _test, Tab _clientTab) {
        this._test = _test;
        this._clientTab = _clientTab;
    }

    public Client getTest() {
        return _test;
    }

    public Tab getClientTab() {
        return _clientTab;
    }

    public void setTest(Client _test) {
        this._test = _test;
    }

    public void setClientTab(Tab _clientTab) {
        this._clientTab = _clientTab;
    }
    
    public void createTab(){
        Pane lul = new Pane();
    }
    
    public void ajouterClient(){
        
    }
    
    public void modifierClient(){
        
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
