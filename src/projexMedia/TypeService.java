package projexMedia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class TypeService {
	int idTypeService;
	String nomType;
	String description;
	ArrayList<ParametreService> listParametre;
	
	
	
	public TypeService(int idTypeService, String nomType, String description,
			ArrayList<ParametreService> listParametre) {
		super();
		this.idTypeService = idTypeService;
		this.nomType = nomType;
		this.description = description;
		this.listParametre = listParametre;
	}
	
	public TypeService(int idTypeService, ArrayList<ParametreService> listParametre) {
		this.idTypeService = idTypeService;
		this.listParametre = listParametre;
	}
	public TypeService() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	
	
	
	public int getIdTypeService() {
		return idTypeService;
	}

	public void setIdTypeService(int idTypeService) {
		this.idTypeService = idTypeService;
	}

	public String getNomType() {
		return nomType;
	}

	public void setNomType(String nomType) {
		this.nomType = nomType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ArrayList<ParametreService> getListParametre() {
		return listParametre;
	}

	public void setListParametre(ArrayList<ParametreService> listParametre) {
		this.listParametre = listParametre;
	}
	
	
	//***********Ajouter***************//

	//Ajouter un parametre a un type de service 
	public void ajouterparametreService( int idParametre) throws SQLException {
		Connection conn = SimpleDataSource.getConnection();
    	try
    	{
    		PreparedStatement stat = conn.prepareStatement("INSERT INTO `ta_service`(`fk_id_typeService`,`fk_id_parametreService`) "
    				+ "VALUES (?,?)");
            stat.setInt(1, getIdTypeService());
            stat.setInt(2, idParametre);
            
            
            stat.executeUpdate();
    	}
    	finally {
    		conn.close();
    	}
	}
	
	//ajouter un type de service 
	public void ajouterTypeService(String type, String description) throws SQLException {
		Connection conn = SimpleDataSource.getConnection();
    	try
    	{
    		PreparedStatement stat = conn.prepareStatement("INSERT INTO `typeservice`(`nom_type`,`description`) "
    				+ "VALUES (?,?)");
            stat.setString(1, type);
            stat.setString(2, description);
            
            
            stat.executeUpdate();
    	}
    	finally {
    		conn.close();
    	}
	}
	
	
	
	
	//***********modifier*************//
	
	
	//changer le nom du type de service
	public void modifierTypeService() throws SQLException {
		Connection conn = SimpleDataSource.getConnection();
    	try
    	{
    		PreparedStatement stat = conn.prepareStatement("UPDATE `typeservice`SET `nom_type` = ?, `description` = ?  WHERE id_typeService = ?");
            stat.setString(1, getNomType());
            stat.setString(2, getDescription());
            stat.setInt(3, getIdTypeService());
            
            
            stat.executeUpdate();
    	}
    	finally {
    		conn.close();
    	}
	}
	
	
	
	
	//*********Supprimer***********//
	
	
	//supprimer un type de service
	public void supprimerTypeService() throws SQLException {
		Connection conn = SimpleDataSource.getConnection();
    	try
    	{
    		PreparedStatement stat = conn.prepareStatement("Delete from typeservice where id_typeService = ?");
    		stat.setInt(1, getIdTypeService());
    		Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Message de confirmation");
			alert.setHeaderText("Êtes-vous sûr de vouloir supprimer ce site?");
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
	
	//supprimer un parametre à un type de service
	public void supprimerparametreService(int id) throws SQLException {
		Connection conn = SimpleDataSource.getConnection();
    	try
    	{
    		PreparedStatement stat = conn.prepareStatement("Delete from ta_service where id_service = ?");
    		stat.setInt(1, id);
    		
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
	
	
	
	

}
