package projexMedia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class ParametreService {
	
	int idParametre;
	String nomParametre;
	
	public ParametreService(int idParametre, String nomParametre) {
		this.idParametre = idParametre;
		this.nomParametre = nomParametre;
	}
	
	public ParametreService() {
		idParametre= 0;
		nomParametre = "";
	}
	
	public int getIdParametre() {
		return idParametre;
	}
	
	public void setIdParametre(int idParametre) {
		this.idParametre = idParametre;
	}
	
	public String getNomParametre() {
		return nomParametre;
	}
	
	public void setNomParametre(String nomParametre) {
		this.nomParametre = nomParametre;
	}
	
	
	//ajouter un parametre de service
	public void ajouterParametre() throws SQLException {
		Connection conn = SimpleDataSource.getConnection();
    	try
    	{
    		PreparedStatement stat = conn.prepareStatement("INSERT INTO `parametreservice`(`nom_parametre`) "
    				+ "VALUES (?)");
            stat.setString(1, getNomParametre());
            
            
            stat.executeUpdate();
    	}
    	finally {
    		conn.close();
    	}
	}
	
	//changer le nom du parametre
	public void modifierParametre() throws SQLException {
		Connection conn = SimpleDataSource.getConnection();
    	try
    	{
    		PreparedStatement stat = conn.prepareStatement("UPDATE `parametreservice`SET `nom_parametre` = ? WHERE id_parametreService = ?");
            stat.setString(1, getNomParametre());
            stat.setInt(2, getIdParametre());
            
            
            stat.executeUpdate();
    	}
    	finally {
    		conn.close();
    	}
	}
	
	//supprimer supprimer un parametre
	public void supprimerParametre() throws SQLException {
		Connection conn = SimpleDataSource.getConnection();
    	try
    	{
    		PreparedStatement stat = conn.prepareStatement("Delete from parametreService where id_parametreService = ?");
    		stat.setInt(1, getIdParametre());
    		Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Message de confirmation");
			alert.setHeaderText("Êtes-vous sûr de vouloir supprimer ce parametre dans ce type de service?");
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
	
	public ObservableList<ParametreService> listParametre() throws SQLException{
		
		ObservableList<ParametreService> list = FXCollections.observableArrayList();;
		
		Connection conn = SimpleDataSource.getConnection();
		

		try {
			Statement stat = conn.createStatement();
			//Statement stat1 = conn.createStatement();

			ResultSet result = stat.executeQuery("SELECT * From parametreservice");
			//ResultSet result1 = stat.executeQuery("SELECT * From typeservice");

			while (result.next()) {
				list.add(new ParametreService(Integer.parseInt(result.getString("id_parametreService")),result.getString("nom_parametre")));
				//System.out.println(result.getString("id_parametreService")+result.getString("nom_parametre"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {
			conn.close();
		}
		
		
		return list;
	}
	
	
	public ObservableList<String> listNom() throws SQLException{
		
		ObservableList<String> list = FXCollections.observableArrayList();
		
		Connection conn = SimpleDataSource.getConnection();
		

		try {
			Statement stat = conn.createStatement();
			//Statement stat1 = conn.createStatement();

			ResultSet result = stat.executeQuery("SELECT * From parametreservice");
			//ResultSet result1 = stat.executeQuery("SELECT * From typeservice");

			while (result.next()) {
				list.add(result.getString("nom_parametre"));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {
			conn.close();
		}
		
		
		return list;
	}
	
}
