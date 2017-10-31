package projexMedia;

import java.util.ArrayList;

public class Service {
	
	private int _id;
	private String _nomType;
	private ArrayList<ArrayList<String>> _Champs;
	Boolean _actif;
	private int _id_Site;
	private static int _compteurActif = 0;
	private static int _compteurArchive = 0;
	
	public Service() {
		_id = 0;
		_nomType="";
		_Champs = new ArrayList<ArrayList<String>>();
		_actif=true;
		_id_Site = 0;
	}
	
	public Service(int id, String nom,ArrayList<ArrayList<String>> champs,Boolean actif, int id_Site) {
		if (actif == false)
		{
			_compteurArchive++;
		}
		else
		{
			_compteurActif++;
		}
		this._id = id;
		this._nomType=nom;
		_Champs = champs;
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
		return _nomType;
	}
	
	public void set_nom(String _nom) {
		this._nomType = _nom;
	}
	
	public Boolean get_actif() {
		return _actif;
	}

	public void set_actif(Boolean _actif) {
		this._actif = _actif;
	}
	
	public ArrayList<ArrayList<String>> get_Champs() {
		return _Champs;
	}

	public void set_Champs(ArrayList<ArrayList<String>> _Champs) {
		this._Champs = _Champs;
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
