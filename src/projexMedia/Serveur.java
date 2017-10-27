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
import javafx.scene.layout.Pane;
import javafx.scene.control.Alert.AlertType;

public class Serveur {
	int idServeur;
	String nomServeur;
	String osServeur;
	Boolean actif;
	
	/**
	 * ajouter un serveur à la base de donnée
	 * @throws SQLException
	 */
	public void ajouterServeur() throws SQLException {
		Connection conn = SimpleDataSource.getConnection();
    	try
    	{
    		PreparedStatement stat = conn.prepareStatement("INSERT INTO `serveur`(`nom_serveur`, `os`, `actif`) "
    				+ "VALUES (?,?,1)");
            stat.setString(1, getNomServeur());
            stat.setString(2, getOsServeur());
            
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
	public void modifierNomServeur()throws SQLException {
		Connection conn = SimpleDataSource.getConnection();
    	try
    	{
    		PreparedStatement stat = conn.prepareStatement("Update serveur set nom_serveur=? where id_serveur = ?");
    		stat.setString(1, getNomServeur());
            stat.setInt(2, getIdServeur());
            
            stat.executeUpdate();
    	}
    	finally {
    		conn.close();
    	}
	}
	
	/**
	 * modifier le os d'un serveur dans la base de donnée
	 * @throws SQLException
	 */
	public void modifierOsServeur() throws SQLException{
		Connection conn = SimpleDataSource.getConnection();
    	try
    	{
    		PreparedStatement stat = conn.prepareStatement("Update serveur set os=? where id_serveur = ?");
    		stat.setString(1, getOsServeur());
            stat.setInt(2, getIdServeur());
            
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
    		stat.setBoolean(1, getActif());
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
    		PreparedStatement stat = conn.prepareStatement("Delete from client where id_client = ?");
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
	
	public Serveur(int idServeur, String nomServeur, String osServeur, Boolean actif) {
		super();
		this.idServeur = idServeur;
		this.nomServeur = nomServeur;
		this.osServeur = osServeur;
		this.actif = actif;
	}
	
	public Serveur() {
		idServeur = 0;
		nomServeur = "";
		osServeur = "";
		actif = true;
	}

	//gettreur et setteur
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
	
	public String getOsServeur() {
		return osServeur;
	}
	
	public void setOsServeur(String osServeur) {
		this.osServeur = osServeur;
	}
	
	public Boolean getActif() {
		return actif;
	}
	
	public void setActif(Boolean actif) {
		this.actif = actif;
	}
	
}
