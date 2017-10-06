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

    public Site(Integer _idSite, String _url) {
        idSite = new SimpleIntegerProperty(_idSite);
        url = new SimpleStringProperty(_url);
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
    
}
