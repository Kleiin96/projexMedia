package projexMedia;

import javafx.scene.control.Label;

import javafx.scene.control.TextField;

import javafx.scene.Group;
import javafx.scene.layout.Pane;

public class Service {
	
	String _url;
	String _login;
	String _password;
	String _type;
	Group _actif;
	
	public Service() {
		_url="";
		_login="";
		_password="";
		_type="";
		_actif=new Group();
	}
	
	public void CreateGroupActif() {
		TextField recherche = new TextField();
		
		//position Recherche
		recherche.setLayoutX(200);
		recherche.setLayoutY(0);
		
		
		for(int i =0 ; i <2 ;i++) {
			Label lblType = new Label("WordPress");
			Label lblUrl = new Label("URL : ");
			Label lbl_url = new Label("www.wow.ca");
			Label lblLogin = new Label("Login : ");
			Label lbl_login =  new Label("TomyPhillip");
			Label lblPwd = new Label("Password : ");
			Label lbl_pwd = new Label("ImPear");
			
			//position Type
			lblType.setLayoutX(30);
			lblType.setLayoutY(i *100 + 20);
			
			//position url
			lblUrl.setLayoutX(30);
			lblUrl.setLayoutY(i * 100 + 50);
			lbl_url.setLayoutX(100);
			lbl_url.setLayoutY(i * 100 + 50);
			
			//position Login
			lblLogin.setLayoutX(30);
			lblLogin.setLayoutY(i * 100 +80);
			lbl_login.setLayoutX(100);
			lbl_login.setLayoutY(i * 100 +80);
			
			//position pwd
			lblPwd.setLayoutX(200);
			lblPwd.setLayoutY(i * 100 +80);
			lbl_pwd.setLayoutX(270);
			lbl_pwd.setLayoutY(i * 100 +80);
			
			
			
			
			_actif.getChildren().addAll(lblType,lblUrl,lblLogin, lblPwd, lbl_url, lbl_login, lbl_pwd);
		}
		
		_actif.getChildren().add(recherche);
	}
	
	
	public Group get_actif() {
		return _actif;
	}

	public void set_actif(Group _actif) {
		this._actif = _actif;
	}

	public String get_url() {
		return _url;
	}
	
	public void set_url(String _url) {
		this._url = _url;
	}
	
	public String get_login() {
		return _login;
	}
	
	public void set_login(String _login) {
		this._login = _login;
	}
	
	public String get_Password() {
		return _password;
	}
	
	public void set_Password(String _Password) {
		this._password = _Password;
	}
	
	public String get_type() {
		return _type;
	}
	
	public void set_type(String _type) {
		this._type = _type;
	}

}
