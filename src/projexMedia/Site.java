/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projexMedia;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author houdeto
 */
public class Site {
    
    private SimpleIntegerProperty idSite;
    private SimpleStringProperty url;
    private SimpleStringProperty client;
    private SimpleStringProperty serveur;

    public Site() {
        idSite = new SimpleIntegerProperty(0);
        url = new SimpleStringProperty(null);
        client = new SimpleStringProperty(null);
        serveur = new SimpleStringProperty(null);
    }
    
    public Site(Integer _idSite, String _url,String _client,String _serveur) {
        idSite = new SimpleIntegerProperty(_idSite);
        url = new SimpleStringProperty(_url);
        client = new SimpleStringProperty(_client);
        serveur = new SimpleStringProperty(_serveur);
    }
    
    public Integer getIdSite() {
        return idSite.get();
    }

    public String getUrl() {
        return url.get();
    }

    public void setIdSite(Integer _idSite) {
    	idSite = new SimpleIntegerProperty(_idSite);
    }

    public void setUrl(String _url) {
    	url = new SimpleStringProperty(_url);
    }
    
    public String getClient() {
		return client.get();
	}

	public void setClient(String _client) {
		client = new SimpleStringProperty(_client);
	}

	public String getServeur() {
		return serveur.get();
	}

	public void setServeur(String _serveur) {
		serveur = new SimpleStringProperty(_serveur);
	}
	
	
}
