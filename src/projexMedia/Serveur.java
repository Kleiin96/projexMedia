package projexMedia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.scene.control.Alert.AlertType;

public class Serveur {
	int idServeur;
	
	String nomServeur;
	
	String fournisseur;
	
	String gestionCompteUrl;
	String gestionCompteUsername;
	String gestionComptePassword;
	
	String NameServer1;
	String NameServer2;
	String NameServer3;
	String NameServer4;
	
	String cPANELUrl;
	String cPANELUsername;
	String cPANELPassword;
	
	String WHMUrl;
	String WHMUsername;
	String WHMPassword;
	
	String PasswordRoot;
	
	Boolean actif;
	
	
	
	
	
	public Serveur() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Serveur(int idServeur, String nomServeur, String fournisseur, String gestionCompteUrl,
			String gestionCompteUsername, String gestionComptePassword, String nameServer1, String nameServer2,
			String nameServer3, String nameServer4, String cPANELUrl, String cPANELUsername, String cPANELPassword,
			String wHMUrl, String wHMUsername, String wHMPassword, String passwordRoot, Boolean actif) {
		
		this.idServeur = idServeur;
		this.nomServeur = nomServeur;
		
		this.fournisseur = fournisseur;
		this.gestionCompteUrl = gestionCompteUrl;
		this.gestionCompteUsername = gestionCompteUsername;
		this.gestionComptePassword = gestionComptePassword;
		NameServer1 = nameServer1;
		NameServer2 = nameServer2;
		NameServer3 = nameServer3;
		NameServer4 = nameServer4;
		this.cPANELUrl = cPANELUrl;
		this.cPANELUsername = cPANELUsername;
		this.cPANELPassword = cPANELPassword;
		WHMUrl = wHMUrl;
		WHMUsername = wHMUsername;
		WHMPassword = wHMPassword;
		PasswordRoot = passwordRoot;
		this.actif = actif;
	}


	public int getIdServeur() {
		return idServeur;
	}


	public void setIdServeur(int idServeur) {
		this.idServeur = idServeur;
	}


	public String getNomServeur() {
		return nomServeur;
	}


	public void setNomServeur(String nomServeur) {
		this.nomServeur = nomServeur;
	}


	public String getFournisseur() {
		return fournisseur;
	}


	public void setFournisseur(String fournisseur) {
		this.fournisseur = fournisseur;
	}


	public String getGestionCompteUrl() {
		return gestionCompteUrl;
	}


	public void setGestionCompteUrl(String gestionCompteUrl) {
		this.gestionCompteUrl = gestionCompteUrl;
	}


	public String getGestionCompteUsername() {
		return gestionCompteUsername;
	}


	public void setGestionCompteUsername(String gestionCompteUsername) {
		this.gestionCompteUsername = gestionCompteUsername;
	}


	public String getGestionComptePassword() {
		return gestionComptePassword;
	}


	public void setGestionComptePassword(String gestionComptePassword) {
		this.gestionComptePassword = gestionComptePassword;
	}


	public String getNameServer1() {
		return NameServer1;
	}


	public void setNameServer1(String nameServer1) {
		NameServer1 = nameServer1;
	}


	public String getNameServer2() {
		return NameServer2;
	}


	public void setNameServer2(String nameServer2) {
		NameServer2 = nameServer2;
	}


	public String getNameServer3() {
		return NameServer3;
	}


	public void setNameServer3(String nameServer3) {
		NameServer3 = nameServer3;
	}


	public String getNameServer4() {
		return NameServer4;
	}


	public void setNameServer4(String nameServer4) {
		NameServer4 = nameServer4;
	}


	public String getcPANELUrl() {
		return cPANELUrl;
	}


	public void setcPANELUrl(String cPANELUrl) {
		this.cPANELUrl = cPANELUrl;
	}


	public String getcPANELUsername() {
		return cPANELUsername;
	}


	public void setcPANELUsername(String cPANELUsername) {
		this.cPANELUsername = cPANELUsername;
	}


	public String getcPANELPassword() {
		return cPANELPassword;
	}


	public void setcPANELPassword(String cPANELPassword) {
		this.cPANELPassword = cPANELPassword;
	}


	public String getWHMUrl() {
		return WHMUrl;
	}


	public void setWHMUrl(String wHMUrl) {
		WHMUrl = wHMUrl;
	}


	public String getWHMUsername() {
		return WHMUsername;
	}


	public void setWHMUsername(String wHMUsername) {
		WHMUsername = wHMUsername;
	}


	public String getWHMPassword() {
		return WHMPassword;
	}


	public void setWHMPassword(String wHMPassword) {
		WHMPassword = wHMPassword;
	}


	public String getPasswordRoot() {
		return PasswordRoot;
	}


	public void setPasswordRoot(String passwordRoot) {
		PasswordRoot = passwordRoot;
	}


	public Boolean getActif() {
		return actif;
	}


	public void setActif(Boolean actif) {
		this.actif = actif;
	}


	/**
	 * ajouter un serveur à la base de donnée
	 * @throws SQLException
	 */
	public void ajouterServeur() throws SQLException {
		Connection conn = SimpleDataSource.getConnection();
    	try
    	{
    		PreparedStatement stat = conn.prepareStatement("INSERT INTO `serveur`(`nom_serveur`, `fournisseur`, `gestionCompteURL`,"
    				+ " `gestionCompteUsername`, `gestionComptePassword`, `nameServer1`, `nameServer2`, `nameServer3`, `nameServer4`,"
    				+ " `cPanelURL`, `cPanelUsername`, `cPanelPassword`, `whmURL`, `whmUsername`, `whmPassword`, `rootPassword`, `actif`) "
    				+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,1)");
    		
            stat.setString(1, getNomServeur());
            stat.setString(2, getFournisseur());
            stat.setString(3, getGestionCompteUrl());
            stat.setString(4, getGestionCompteUsername());
            stat.setString(5, getGestionComptePassword());
            stat.setString(6, getNameServer1());
            stat.setString(7, getNameServer2());
            stat.setString(8, getNameServer3());
            stat.setString(9, getNameServer4());
            stat.setString(10, getcPANELUrl());
            stat.setString(11, getcPANELUsername());
            stat.setString(12, getcPANELPassword());
            stat.setString(13, getWHMUrl());
            stat.setString(14, getWHMUsername());
            stat.setString(15, getWHMPassword());
            stat.setString(16, getPasswordRoot());
            
            stat.executeUpdate();
    	}
    	finally {
    		conn.close();
    	}
	}
	
	
	/**
	 * modifier le nom d'un serveur
	 * @throws SQLException
	 */
	public void modifierServeur()throws SQLException {
		Connection conn = SimpleDataSource.getConnection();
    	try
    	{
    		PreparedStatement stat = conn.prepareStatement("UPDATE `serveur` SET"
    				+ "`nom_serveur`=?,`fournisseur`=?,`gestionCompteURL`=?,"
    				+ "`gestionCompteUsername`=?,`gestionComptePassword`=?,"
    				+ "`nameServer1`=?,`nameServer2`=?,`nameServer3`=?,"
    				+ "`nameServer4`=?,`cPanelURL`=?,`cPanelUsername`=?,"
    				+ "`cPanelPassword`=?,`whmURL`=?,`whmUsername`=?,"
    				+ "`whmPassword`=?,`rootPassword`=? WHERE id_serveur=?");
    		stat.setString(1, getNomServeur());
            stat.setString(2, getFournisseur());
            stat.setString(3, getGestionCompteUrl());
            stat.setString(4, getGestionCompteUsername());
            stat.setString(5, getGestionComptePassword());
            stat.setString(6, getNameServer1());
            stat.setString(7, getNameServer2());
            stat.setString(8, getNameServer3());
            stat.setString(9, getNameServer4());
            stat.setString(10, getcPANELUrl());
            stat.setString(11, getcPANELUsername());
            stat.setString(12, getcPANELPassword());
            stat.setString(13, getWHMUrl());
            stat.setString(14, getWHMUsername());
            stat.setString(15, getWHMPassword());
            stat.setString(16, getPasswordRoot());
            stat.setInt(17, getIdServeur());
            
           
            
            stat.executeUpdate();
    	}
    	finally {
    		conn.close();
    	}
	}
	
	
	/**
	 * archiver un servuer dans la base de donnée
	 * @throws SQLException
	 */
	public void archiverServeur() throws SQLException{
		Connection conn = SimpleDataSource.getConnection();
    	try
    	{
    		PreparedStatement stat = conn.prepareStatement("Update serveur set actif=? where id_serveur = ?");
    		stat.setBoolean(1, false);
            stat.setInt(2, getIdServeur());
            
            stat.executeUpdate();
    	}
    	finally {
    		conn.close();
    	}
	}
	
	/**
	 * activer un serveur dans la base de donnée
	 * @throws SQLException
	 */
	public void activerServeur() throws SQLException{
		Connection conn = SimpleDataSource.getConnection();
    	try
    	{
    		PreparedStatement stat = conn.prepareStatement("Update serveur set actif=? where id_serveur = ?");
    		stat.setBoolean(1, getActif());
            stat.setInt(2, getIdServeur());
            
            stat.executeUpdate();
    	}
    	finally {
    		conn.close();
    	}
	}
	
	public void supprimerServeur() throws SQLException{
		Connection conn = SimpleDataSource.getConnection();
    	try
    	{
    		PreparedStatement stat = conn.prepareStatement("Delete from serveur where id_serveur = ?");
    		stat.setInt(1, getIdServeur());
    		Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Message de confirmation");
			alert.setHeaderText("Êtes-vous sûr de vouloir supprimer ce serveur?");
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
