package projexMedia;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.beans.property.SimpleStringProperty;

public class Role {
	int id_role;
	private SimpleStringProperty _role;
    private SimpleStringProperty _ajouter;
    private SimpleStringProperty _modifier;
    private SimpleStringProperty _archiver;
    private SimpleStringProperty _activer;
    private SimpleStringProperty _supprimer;
    private SimpleStringProperty _historique;
    private SimpleStringProperty _utilisateur;
    private SimpleStringProperty _droitRole;
    private SimpleStringProperty _gestionService;

    public Role() {
    	id_role = 0;
    	_role = new SimpleStringProperty(null);
    	_ajouter = new SimpleStringProperty(null);
    	_modifier = new SimpleStringProperty(null);
    	_archiver = new SimpleStringProperty(null);
    	_activer = new SimpleStringProperty(null);
    	_supprimer = new SimpleStringProperty(null);
    	_historique = new SimpleStringProperty(null);
    	_utilisateur = new SimpleStringProperty(null);
    	_droitRole = new SimpleStringProperty(null);
    	_gestionService = new SimpleStringProperty(null);
    }
    
    public Role(int id) throws SQLException {
		Connection conn = SimpleDataSource.getConnection();

		try {
			Statement stat = conn.createStatement();

			ResultSet result = stat.executeQuery(
					"SELECT * FROM role WHERE id_role=" + id);

			result.next();
			
			id_role = result.getInt("id_role");
	    	
	    	_role = new SimpleStringProperty(result.getString("nom_role"));
	    	
	    	if(result.getBoolean("ajouter") == true) {
	    		_ajouter = new SimpleStringProperty("X");
	    	}
	    	else {
	    		_ajouter = new SimpleStringProperty("");
	    	}
	    	
	    	if(result.getBoolean("modifier") == true) {
	    		_modifier = new SimpleStringProperty("X");
	    	}
	    	else {
	    		_modifier = new SimpleStringProperty("");
	    	}
	    	
	    	if(result.getBoolean("archiver") == true) {
	    		_archiver = new SimpleStringProperty("X");
	    	}
	    	else {
	    		_archiver = new SimpleStringProperty("");
	    	}
	    	
	    	if(result.getBoolean("activer") == true) {
	    		_activer = new SimpleStringProperty("X");
	    	}
	    	else {
	    		_activer = new SimpleStringProperty("");
	    	}
	    	
	    	if(result.getBoolean("supprimer") == true) {
	    		_supprimer = new SimpleStringProperty("X");
	    	}
	    	else {
	    		_supprimer = new SimpleStringProperty("");
	    	}
	    	
	    	if(result.getBoolean("historique") == true) {
	    		_historique = new SimpleStringProperty("X");
	    	}
	    	else {
	    		_historique = new SimpleStringProperty("");
	    	}
	    	
	    	if(result.getBoolean("utilisateur") == true) {
	    		_utilisateur = new SimpleStringProperty("X");
	    	}
	    	else {
	    		_utilisateur = new SimpleStringProperty("");
	    	}
	    	
	    	if(result.getBoolean("role") == true) {
	    		_droitRole = new SimpleStringProperty("X");
	    	}
	    	else {
	    		_droitRole = new SimpleStringProperty("");
	    	}
	    	
	    	if(result.getBoolean("gestionService") == true) {
	    		_gestionService = new SimpleStringProperty("X");
	    	}
	    	else {
	    		_gestionService = new SimpleStringProperty("");
	    	}
			
		} finally {
			conn.close();
		}
	}
    
    public Role(int id, String role, boolean ajouter,boolean modifier,boolean archiver,boolean activer,boolean supprimer, boolean historique, boolean utilisateur, boolean droitRole,boolean gestionService) {
    	id_role = id;
    	
    	_role = new SimpleStringProperty(role);
    	
    	if(ajouter == true) {
    		_ajouter = new SimpleStringProperty("X");
    	}
    	else {
    		_ajouter = new SimpleStringProperty("");
    	}
    	
    	if(modifier == true) {
    		_modifier = new SimpleStringProperty("X");
    	}
    	else {
    		_modifier = new SimpleStringProperty("");
    	}
    	
    	if(archiver == true) {
    		_archiver = new SimpleStringProperty("X");
    	}
    	else {
    		_archiver = new SimpleStringProperty("");
    	}
    	
    	if(activer == true) {
    		_activer = new SimpleStringProperty("X");
    	}
    	else {
    		_activer = new SimpleStringProperty("");
    	}
    	
    	if(supprimer == true) {
    		_supprimer = new SimpleStringProperty("X");
    	}
    	else {
    		_supprimer = new SimpleStringProperty("");
    	}
    	
    	if(historique == true) {
    		_historique = new SimpleStringProperty("X");
    	}
    	else {
    		_historique = new SimpleStringProperty("");
    	}
    	
    	if(utilisateur == true) {
    		_utilisateur = new SimpleStringProperty("X");
    	}
    	else {
    		_utilisateur = new SimpleStringProperty("");
    	}
    	
    	if(droitRole == true) {
    		_droitRole = new SimpleStringProperty("X");
    	}
    	else {
    		_droitRole = new SimpleStringProperty("");
    	}
    	
    	if(gestionService == true) {
    		_gestionService = new SimpleStringProperty("X");
    	}
    	else {
    		_gestionService = new SimpleStringProperty("");
    	}
    }

	public int getId_role() {
		return id_role;
	}

	public void setId_role(int id_role) {
		this.id_role = id_role;
	}

	public String get_role() {
		return _role.get();
	}

	public void set_role(String role) {
		this._role = new SimpleStringProperty(role);
	}

	public String get_ajouter() {
		return _ajouter.get();
	}

	public void set_ajouter(String ajouter) {
		this._ajouter = new SimpleStringProperty(ajouter);
	}

	public String get_modifier() {
		return _modifier.get();
	}

	public void set_modifier(String modifier) {
		this._modifier = new SimpleStringProperty(modifier);
	}

	public String get_archiver() {
		return _archiver.get();
	}

	public void set_archiver(String archiver) {
		this._archiver = new SimpleStringProperty(archiver);
	}

	public String get_activer() {
		return _activer.get();
	}

	public void set_activer(String activer) {
		this._activer = new SimpleStringProperty(activer);
	}

	public String get_supprimer() {
		return _supprimer.get();
	}

	public void set_supprimer(String supprimer) {
		this._supprimer = new SimpleStringProperty(supprimer);
	}

	public String get_historique() {
		return _historique.get();
	}

	public void set_historique(String historique) {
		this._historique = new SimpleStringProperty(historique);
	}

	public String get_utilisateur() {
		return _utilisateur.get();
	}

	public void set_utilisateur(String utilisateur) {
		this._utilisateur = new SimpleStringProperty(utilisateur);
	}

	public String get_droitRole() {
		return _droitRole.get();
	}

	public void set_droitRole(String droitRole) {
		this._droitRole = new SimpleStringProperty(droitRole);
	}

	public String get_gestionService() {
		return _gestionService.get();
	}

	public void set_gestionService(String gestionService) {
		this._gestionService = new SimpleStringProperty(gestionService);
	} 
	
    
}
