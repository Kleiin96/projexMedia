package projexMedia;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Utilisateur {
	private SimpleStringProperty user;
    private SimpleStringProperty password;
    private SimpleStringProperty prenom;
    private SimpleStringProperty nom;
    private SimpleStringProperty role;

    public Utilisateur() {
    	role = new SimpleStringProperty(null);
    	user = new SimpleStringProperty(null);
    	password = new SimpleStringProperty(null);
    	prenom = new SimpleStringProperty(null);
    	nom = new SimpleStringProperty(null);
    }
    
    public Utilisateur(String _user, String _password, String _prenom,String _nom, String _role) {
    	role = new SimpleStringProperty(_role);
    	user = new SimpleStringProperty(_user);
    	password = new SimpleStringProperty(_password);
    	prenom = new SimpleStringProperty(_prenom);
    	nom = new SimpleStringProperty(_nom);
    }

	public String getUser() {
		return user.get();
	}

	public void setUser(String _user) {
		user = new SimpleStringProperty(_user);
	}

	public String getPassword() {
		return password.get();
	}

	public void setPassword(String _password) {
		password = new SimpleStringProperty(_password);
	}

	public String getPrenom() {
		return prenom.get();
	}

	public void setPrenom(String _prenom) {
		prenom = new SimpleStringProperty(_prenom);
	}

	public String getNom() {
		return nom.get();
	}

	public void setNom(String _nom) {
		nom = new SimpleStringProperty(_nom);
	}

	public String getRole() {
		return role.get();
	}

	public void setRole(String _role) {
		role = new SimpleStringProperty(_role);
	}
    
    
    
}
