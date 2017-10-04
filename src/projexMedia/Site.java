/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javafxapplication1;

/**
 *
 * @author houdeto
 */
public class Site {
    
    int _idSite;
    String _url;

    public Site(int idSite, String url) {
        _idSite = idSite;
        _url = url;
    }

    public int getIdSite() {
        return _idSite;
    }

    public String getUrl() {
        return _url;
    }

    public void setIdSite(int idSite) {
        _idSite = idSite;
    }

    public void setUrl(String url) {
        _url = url;
    }
    
    
}
