package projexMedia;

import javafx.scene.control.Label;

import javafx.scene.control.TextField;

import javafx.scene.Group;
import javafx.scene.layout.Pane;

public class Service {
	
	private int _id;
	private String _nom;
	private String _url;
	private String _username;
	private String _password;
	private String _autre;
	private Boolean _actif;
	private int _id_Site;
	private static int _compteurActif = 0;
	private static int _compteurArchive = 0;
	
	public Service() {
		_id = 0;
		_nom="";
		_url="";
		_username="";
		_password="";
		_autre="";
		_actif=true;
		_id_Site = 0;
	}
	
	public Service(int id, String nom, String url, String username, String password, String autre, Boolean actif, int id_Site) {
		if (actif == false)
		{
			_compteurArchive++;
		}
		else
		{
			_compteurActif++;
		}
		this._id = id;
		this._nom=nom;
		this._url=url;
		this._username=username;
		this._password=password;
		this._autre=autre;
		this._actif=actif;
		this._id_Site = id_Site;
	}
	
	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}
	
	public String get_nom() {
		return _nom;
	}
	
	public void set_nom(String _nom) {
		this._nom = _url;
	}
	
	public String get_url() {
		return _url;
	}
	
	public void nom(String _url) {
		this._url = _url;
	}
	
	public String get_username() {
		return _username;
	}
	
	public void set_username(String _username) {
		this._username = _username;
	}
	
	public String get_password() {
		return _password;
	}
	
	public void set_password(String _Password) {
		this._password = _Password;
	}
	
	public String get_autre() {
		return _autre;
	}
	
	public void set_autre(String _Autre) {
		this._autre = _Autre;
	}
	
	public Boolean get_actif() {
		return _actif;
	}

	public void set_actif(Boolean _actif) {
		this._actif = _actif;
	}
	
	public int get_id_Site() {
		return _id_Site;
	}

	public void set_id_Site(int _id_Site) {
		this._id_Site = _id_Site;
	}

	public int getCompteurActif() {
        return _compteurActif;
    }
	
	public void setCompteurActif(int compteurActif) {
        _compteurActif = compteurActif;
    }
	
	public int getCompteurArchive() {
        return _compteurArchive;
    }
	
	public void setCompteurArchive(int compteurArchive) {
        _compteurArchive = compteurArchive;
    }
	
//Double clic
	//Créer Gestionnaire
	//Appeller GestionnaireServiceActif
	//Appeller FonctionAfficherActif

}
