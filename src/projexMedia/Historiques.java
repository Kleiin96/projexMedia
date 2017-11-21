package projexMedia;

public class Historiques {
	
	String url;
	String type;
	String parametre;
	String valeur;
	String date;
	String action;
	String prenom;
	String nom;
	int idSite;
	
	
	public Historiques() {
		this.url = "";
		this.type = "";
		this.parametre = "";
		this.valeur = "";
		this.date = "";
		this.action = "";
		this.prenom = "";
		this.nom = "";
		this.idSite= 0;
	}
	public Historiques(String url, String type, String parametre, String valeur, String date, String action,
			String prenom, String nom, int id) {
		
		this.url = url;
		this.type = type;
		this.parametre = parametre;
		this.valeur = valeur;
		this.date = date;
		this.action = action;
		this.prenom = prenom;
		this.nom = nom;
		this.idSite= id;
	}
	public int getIdSite() {
		return idSite;
	}
	public void setIdSite(int idSite) {
		this.idSite = idSite;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getParametre() {
		return parametre;
	}
	public void setParametre(String parametre) {
		this.parametre = parametre;
	}
	public String getValeur() {
		return valeur;
	}
	public void setValeur(String valeur) {
		this.valeur = valeur;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	
	
	

}
