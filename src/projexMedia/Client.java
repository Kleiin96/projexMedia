/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projexMedia;

/**
 *
 * @author bruneaje
 */
public class Client {
    String _nomCompagnie;
    String _telephone;
    String _nomResponsable;
    
    public Client() {
        _nomCompagnie = "";
        _telephone = "";
        _nomResponsable = "";
    }

    public Client(String _nomCompagnie, String _telephone, String _nomResponsable) {
        this._nomCompagnie = _nomCompagnie;
        this._telephone = _telephone;
        this._nomResponsable = _nomResponsable;
    }

    public String getNomCompagnie() {
        return _nomCompagnie;
    }

    public String getTelephone() {
        return _telephone;
    }

    public String getNomResponsable() {
        return _nomResponsable;
    }

    public void setNomCompagnie(String nomCompagnie) {
        this._nomCompagnie = nomCompagnie;
    }

    public void setTelephone(String telephone) {
        this._telephone = telephone;
    }

    public void setNomResponsable(String nomResponsable) {
        this._nomResponsable = nomResponsable;
    }
    
}
