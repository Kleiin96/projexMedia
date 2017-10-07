/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projexMedia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 *
 * @author bruneaje
 */
public class Client {
	int _idClient;
    String _nomCompagnie;
    String _telephone;
    String _nomResponsable;
    String _adresse;
    String _courriel;
    Boolean _actif;
    
    public Client() {
    	_idClient = 0;
        _nomCompagnie = "";
        _telephone = "";
        _nomResponsable = "";
        _adresse = "";
        _courriel = "";
    }

    public Client(String _nomCompagnie, String _telephone, String _nomResponsable, String _courriel, String _adresse) {
        this._nomCompagnie = _nomCompagnie;
        this._telephone = _telephone;
        this._nomResponsable = _nomResponsable;
        this._courriel= _courriel;
        this._adresse = _adresse;
    }
    
    //Setter and getter

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
    
    public int get_idClient() {
		return _idClient;
	}

	public void set_idClient(int _idClient) {
		this._idClient = _idClient;
	}

	public String getAdresse() {
		return _adresse;
	}

	public void setAdresse(String _adresse) {
		this._adresse = _adresse;
	}

	public String get_courriel() {
		return _courriel;
	}

	public void set_courriel(String _courriel) {
		this._courriel = _courriel;
	}

	public Boolean get_actif() {
		return _actif;
	}

	public void set_actif(Boolean _actif) {
		this._actif = _actif;
	}
    
    //sql action
    
   

	public void ajouterClient() throws SQLException{
    	Connection conn = SimpleDataSource.getConnection();
    	try
    	{
    		PreparedStatement stat = conn.prepareStatement("INSERT INTO `client`(`nom_compagnie`, `telephone`, `adresse`, `personne_responsable`, `courriel`, `actif`) "
    				+ "VALUES (?,?,?,?,?,1)");
            stat.setString(1, getNomCompagnie());
            stat.setString(2, getTelephone());
            stat.setString(3, getAdresse());
            
            stat.executeUpdate();
    	}
    	finally {
    		conn.close();
    	}
    	
    	
    }
    
    public void modifierNomClient() throws SQLException{
    	Connection conn = SimpleDataSource.getConnection();
    	try
    	{
    		PreparedStatement stat = conn.prepareStatement("Update client set nom_compagnie='Georgetown' where id_client = 2");
            
            
            stat.executeUpdate();
    	}
    	finally {
    		conn.close();
    	}
    }
    
    public void modifierTelephoneClient() throws SQLException{
    	
    	Connection conn = SimpleDataSource.getConnection();
    	try
    	{
    		PreparedStatement stat = conn.prepareStatement("Update client set telephone='818-987-1234' where id_client = 2");
            
            
            stat.executeUpdate();
    	}
    	finally {
    		conn.close();
    	}
    	
    	
    }
    
    public void modifierNomResponsableClient() throws SQLException{
        
    	Connection conn = SimpleDataSource.getConnection();
    	try
    	{
    		PreparedStatement stat = conn.prepareStatement("Update client set personne_responsable='Pierre Laroche' where id_client = 2");
            
            
            stat.executeUpdate();
    	}
    	finally {
    		conn.close();
    	}
    }
    
    public void modifierAdresseClient() throws SQLException {
    	
    	Connection conn = SimpleDataSource.getConnection();
    	try
    	{
    		PreparedStatement stat = conn.prepareStatement("Update client set adresse='567 rue Kenndy Sherbrooke' where id_client = 2");
            
            
            stat.executeUpdate();
    	}
    	finally {
    		conn.close();
    	}
    }
    
    public void modifierCourrielClient() throws SQLException{
    	Connection conn = SimpleDataSource.getConnection();
    	try
    	{
    		PreparedStatement stat = conn.prepareStatement("Update client set courriel='pierre@ggt.ca' where id_client = 2");
            
            
            stat.executeUpdate();
    	}
    	finally {
    		conn.close();
    	}
    }
    
    public void archiverClient(){
        
    }
    
    public void activerClient(){
        
    }
    
    public void rechercherClient(){
        
    }
    
    
}
