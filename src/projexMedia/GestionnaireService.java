package projexMedia;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Optional;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GestionnaireService {
	
	public ArrayList<Service> _serviceActif = new ArrayList<>();
	public ArrayList<Service> _serviceArchive = new ArrayList<>();
	public int id_Site;
	
	public Pane _pane;
	
	public void GestionnaireServiceActif(int id_Site) throws SQLException, ClassNotFoundException, IOException{
		
		SimpleDataSource.init("src/projexMedia/database.properties");
		Connection conn = SimpleDataSource.getConnection();
		
		
		
		
		String sql = "Select * From service where fk_id_site = '"+id_Site+"' AND actif = 1";
		try {
			PreparedStatement stat = conn.prepareStatement(sql);
	        ResultSet rs; 
	        rs = stat.executeQuery(sql);
	        Service service1 = new Service();
	        service1.setCompteurActif(0);
	        _serviceActif.clear();
	        while (rs.next()) {
	            Service service = new Service
	            		(rs.getInt("id_service"),
	            		rs.getString("nom"),
	            		rs.getString("url"),
	            		rs.getString("username"),
	            		rs.getString("password"),
	            		rs.getString("autre"),
	            		rs.getBoolean("actif"),
	            		rs.getInt("fk_id_Site"));
	            _serviceActif.add(service);
	        }
		} catch(SQLException ex){
            ex.printStackTrace();
            
        } finally {
            conn.close();
        }
    }
	
	
	
	public void GestionnaireServiceArchive(int id_Site) throws SQLException, ClassNotFoundException, IOException{
		
		SimpleDataSource.init("src/projexMedia/database.properties");
		Connection conn = SimpleDataSource.getConnection();
		
		
		
				
		String sql = "Select * From service where fk_id_site = '"+id_Site+"' AND actif = 0";
		try {
			PreparedStatement stat = conn.prepareStatement(sql);
	        ResultSet rs;
	        rs = stat.executeQuery(sql);
	        Service service1 = new Service();
	        service1.setCompteurArchive(0);
	        _serviceArchive.clear();
	        while (rs.next()) {
	            Service service = new Service
	            		(rs.getInt("id_service"),
	            		rs.getString("nom"),
	            		rs.getString("url"),
	            		rs.getString("username"),
	            		rs.getString("password"),
	            		rs.getString("autre"),
	            		rs.getBoolean("actif"),
	            		rs.getInt("fk_id_Site"));
	            _serviceArchive.add(service);
	        }
		} catch(SQLException ex){
            ex.printStackTrace();
            
        } finally {
            conn.close();
        }
    }
	
	public void afficherServiceActif(Stage primaryStage, int id_site) throws Exception {
		GestionnaireServiceActif(id_site);
		for(int i = 0 ; i < _serviceActif.size() ;i++) {
			Label lblNom = new Label(_serviceActif.get(i).get_nom());
			Label lblUrl = new Label("URL : ");
			Label lbl_url = new Label(_serviceActif.get(i).get_url());
			Label lblUsername = new Label("Username : ");
			Label lbl_username = new Label(_serviceActif.get(i).get_username());
			Label lblPassword = new Label("Password : ");
			Label lbl_password = new Label(_serviceActif.get(i).get_password());
			Label lblAutre = new Label("Autre : ");
			Label lbl_autre = new Label(_serviceActif.get(i).get_autre());
			
			//position Nom
			lblNom.setLayoutX(30);
			lblNom.setLayoutY(i *100 + 20);
			
			//position url
			lblUrl.setLayoutX(30);
			lblUrl.setLayoutY(i * 100 + 50);
			lbl_url.setLayoutX(100);
			lbl_url.setLayoutY(i * 100 + 50);
			
			//position Username
			lblUsername.setLayoutX(30);
			lblUsername.setLayoutY(i * 100 + 80);
			lbl_username.setLayoutX(100);
			lbl_username.setLayoutY(i * 100 + 80);
			
			//position Password
			lblPassword.setLayoutX(200);
			lblPassword.setLayoutY(i * 100 + 50);
			lbl_password.setLayoutX(270);
			lbl_password.setLayoutY(i * 100 + 50);
			
			//position Autre
			lblAutre.setLayoutX(200);
			lblAutre.setLayoutY(i * 100 + 80);
			lbl_autre.setLayoutX(270);
			lbl_autre.setLayoutY(i * 100 + 80);
			
			_pane.getChildren().add(lblNom);
			_pane.getChildren().add(lblUrl);
			_pane.getChildren().add(lbl_url);
			_pane.getChildren().add(lblUsername);
			_pane.getChildren().add(lbl_username);
			_pane.getChildren().add(lblPassword);
			_pane.getChildren().add(lbl_password);
			_pane.getChildren().add(lblAutre);
			_pane.getChildren().add(lbl_autre);
		}
		Scene scene = new Scene(_pane, 450, 400);
		primaryStage.setTitle("Ajouter Client");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public void afficherServiceArchive() {

		for(int i = 0 ; i < _serviceArchive.size() ;i++) {
			Label lblNom = new Label(_serviceArchive.get(i).get_nom());
			Label lblUrl = new Label("URL : ");
			Label lbl_url = new Label(_serviceArchive.get(i).get_url());
			Label lblUsername = new Label("Username : ");
			Label lbl_username = new Label(_serviceArchive.get(i).get_username());
			Label lblPassword = new Label("Password : ");
			Label lbl_password = new Label(_serviceArchive.get(i).get_password());
			Label lblAutre = new Label("Autre : ");
			Label lbl_autre = new Label(_serviceArchive.get(i).get_autre());
			
			//position Nom
			lblNom.setLayoutX(30);
			lblNom.setLayoutY(i *100 + 20);
			
			//position url
			lblUrl.setLayoutX(30);
			lblUrl.setLayoutY(i * 100 + 50);
			lbl_url.setLayoutX(100);
			lbl_url.setLayoutY(i * 100 + 50);
			
			//position Username
			lblUsername.setLayoutX(30);
			lblUsername.setLayoutY(i * 100 + 80);
			lbl_username.setLayoutX(100);
			lbl_username.setLayoutY(i * 100 + 80);
			
			//position Password
			lblPassword.setLayoutX(200);
			lblPassword.setLayoutY(i * 100 + 50);
			lbl_password.setLayoutX(270);
			lbl_password.setLayoutY(i * 100 + 50);
			
			//position Autre
			lblAutre.setLayoutX(200);
			lblAutre.setLayoutY(i * 100 + 80);
			lbl_autre.setLayoutX(270);
			lbl_autre.setLayoutY(i * 100 + 80);
			
			/*_pane.getChildren().add(lblNom);
			_pane.getChildren().add(lblUrl);
			_pane.getChildren().add(lbl_url);
			_pane.getChildren().add(lblUsername);
			_pane.getChildren().add(lbl_username);
			_pane.getChildren().add(lblPassword);
			_pane.getChildren().add(lbl_password);
			_pane.getChildren().add(lblAutre);
			_pane.getChildren().add(lbl_autre);*/
			
		}
	}
	
	public void ajouterService(Service e) throws SQLException {
        Connection conn = SimpleDataSource.getConnection();
        try {
            PreparedStatement stat = conn.prepareStatement(
                    "INSERT INTO service"
                    + " VALUES (NULL, ?, ?, ?, ?, ?, ?, ?)");
            stat.setString(1, e.get_nom());
            stat.setString(2, e.get_url());
            stat.setString(3, e.get_username());
            stat.setString(4, e.get_password());
            stat.setString(5, e.get_autre());  
            stat.setBoolean(6, e.get_actif());  
            stat.setInt(7, e.get_id_Site());     
            stat.executeUpdate();                
        } catch(SQLException ex){
            ex.printStackTrace();
            
        } finally {
            conn.close();
        }
    }
	
	public void modifierService(Service e, Service NewE) throws SQLException {
        Connection conn = SimpleDataSource.getConnection();
        try {
            PreparedStatement stat = conn.prepareStatement(
                    "UPDATE service SET nom = ?, url = ?, username = ?, password = ?, autre = ?, actif = ?"
                    + " WHERE id_service = ?");
            stat.setString(1, NewE.get_nom());
            stat.setString(2, NewE.get_url());
            stat.setString(3, NewE.get_username());
            stat.setString(4, NewE.get_password());
            stat.setString(5, NewE.get_autre());  
            stat.setBoolean(6, NewE.get_actif());  
            stat.setInt(7, e.get_id());     
            stat.executeUpdate();                
        } catch(SQLException ex){
            ex.printStackTrace();
            
        } finally {
            conn.close();
        }
    }
	
	public void activerService(Service e) throws SQLException {
        Connection conn = SimpleDataSource.getConnection();
        try {
            PreparedStatement stat = conn.prepareStatement(
                    "UPDATE service SET actif = 1"
                    + " WHERE id_service = ?");
            stat.setInt(1, e.get_id());     
            stat.executeUpdate();                
        } catch(SQLException ex){
            ex.printStackTrace();
            
        } finally {
            conn.close();
        }
    }
	
	public void archiverService(Service e) throws SQLException {
        Connection conn = SimpleDataSource.getConnection();
        try {
            PreparedStatement stat = conn.prepareStatement(
                    "UPDATE service SET actif = 0"
                    + " WHERE id_service = ?");
            stat.setInt(1, e.get_id());     
            stat.executeUpdate();                
        } catch(SQLException ex){
            ex.printStackTrace();
            
        } finally {
            conn.close();
        }
    }
	
	public void supprimerService(Service e) throws SQLException {
        Connection conn = SimpleDataSource.getConnection();
        try {
            PreparedStatement stat = conn.prepareStatement(
                    "DELETE FROM service"
                    + " WHERE id_service = ?");
            stat.setInt(1, e.get_id());     
            stat.executeUpdate();                
        } catch(SQLException ex){
            ex.printStackTrace();
            
        } finally {
            conn.close();
        }
    }

}
