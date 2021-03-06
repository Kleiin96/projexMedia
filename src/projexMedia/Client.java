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
import java.util.Optional;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;


/**
 *
 * @author bruneaje
 */
public class Client {
	int idClient;
    String nomCompagnie;
    String telephone;
    String nomResponsable;
    String adresse;
    String courriel;
    Boolean actif;
    
    public Client() {
    	idClient = 0;
        nomCompagnie ="";
        telephone = "";
        nomResponsable = "";
        adresse = "";
        courriel = "";
    }
    
    public Client(int _idClient, String _nomCompagnie, String _telephone, String _nomResponsable, String _adresse, String _courriel) {
    	this.idClient = _idClient;
        this.nomCompagnie = _nomCompagnie;
        this.telephone = _telephone;
        this.nomResponsable = _nomResponsable;
        this.courriel= _courriel;
        this.adresse = _adresse;
    }

    public Client(String _nomCompagnie, String _telephone, String _nomResponsable, String _courriel, String _adresse) {
        this.nomCompagnie = _nomCompagnie;
        this.telephone = _telephone;
        this.nomResponsable = _nomResponsable;
        this.courriel= _courriel;
        this.adresse = _adresse;
    }
    
    //Setter and getter

    public String getNomCompagnie() {
        return nomCompagnie;
    }

    public String getTelephone() {
        return telephone;
    }

    public String getNomResponsable() {
        return nomResponsable;
    }

    public void setNomCompagnie(String nomCompagnie) {
        this.nomCompagnie =nomCompagnie;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public void setNomResponsable(String nomResponsable) {
        this.nomResponsable = nomResponsable;
    }
    
    public int getIdClient() {
		return idClient;
	}

	public void setIdClient(int _idClient) {
		this.idClient = _idClient;
	}

	public String getAdresse() {
		return adresse;
	}

	public void setAdresse(String _adresse) {
		this.adresse = _adresse;
	}

	public String getCourriel() {
		return courriel;
	}

	public void setCourriel(String _courriel) {
		this.courriel = _courriel;
	}

	public Boolean getActif() {
		return actif;
	}

	public void setActif(Boolean _actif) {
		this.actif = _actif;
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
            stat.setString(4, getNomResponsable());
            stat.setString(5, getCourriel());
            
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
    		PreparedStatement stat = conn.prepareStatement("Update client set nom_compagnie=? where id_client = ?");
    		stat.setString(1, getNomCompagnie());
            stat.setInt(2, getIdClient());
            
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
    		PreparedStatement stat = conn.prepareStatement("Update client set telephone=? where id_client = ?");
    		stat.setString(1, getTelephone());
            stat.setInt(2, getIdClient());
            
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
    		PreparedStatement stat = conn.prepareStatement("Update client set personne_responsable=? where id_client = ?");
    		stat.setString(1, getNomResponsable());
            stat.setInt(2, getIdClient());
            
            
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
    		PreparedStatement stat = conn.prepareStatement("Update client set adresse=? where id_client = ?");
    		stat.setString(1, getAdresse());
            stat.setInt(2, getIdClient());
            
            
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
    		PreparedStatement stat = conn.prepareStatement("Update client set courriel=? where id_client = ?");
    		stat.setString(1, getCourriel());
            stat.setInt(2, getIdClient());
            
            
            stat.executeUpdate();
    	}
    	finally {
    		conn.close();
    	}
    }
    
    public void archiverClient() throws SQLException{
    	Connection conn = SimpleDataSource.getConnection();
    	try
    	{
    		setActif(false);
    		PreparedStatement stat = conn.prepareStatement("Update client set actif=? where id_client = ?");
    		stat.setBoolean(1, getActif());
            stat.setInt(2, getIdClient());
            
            
            stat.executeUpdate();
    	}
    	finally {
    		conn.close();
    	}
    }
    
    public void activerClient() throws SQLException{
    	Connection conn = SimpleDataSource.getConnection();
    	try
    	{
    		setActif(true);
    		PreparedStatement stat = conn.prepareStatement("Update client set actif=? where id_client = ?");
    		stat.setBoolean(1, getActif());
            stat.setInt(2, getIdClient());
            
            
            stat.executeUpdate();
    	}
    	finally {
    		conn.close();
    	}
    }
    
    public void supprimerClient() throws SQLException{
    	Connection conn = SimpleDataSource.getConnection();
    	try
    	{
    		PreparedStatement stat = conn.prepareStatement("Delete from serveur where id_serveur = ?");
    		stat.setInt(1, getIdClient());
    		Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Message de confirmation");
			alert.setHeaderText("�tes-vous s�r de vouloir supprimer ce client?\nCar ceci va supprimer tout les �l�ments reli� � ce client dont tout les sites de ce client.");
			// alert.setContentText("Are you ok with this?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				 stat.executeUpdate();
			}         
    	}
    	finally {
    		conn.close();
    	}
    }
    
    
}
