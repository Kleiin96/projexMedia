package projexMedia;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GestionnaireService {
	
	public ArrayList<Service> _serviceActif;
	public ArrayList<Service> _serviceArchive;
	public int id_Site;
	
	public ScrollPane scroll1;
	public ScrollPane scroll2;
	public Pane _paneActif;
	public Pane _paneArchive;
	public Site _site;
	public Tab actif;
    public Tab archive;
    public int _activeTab;
    public ArrayList<ArrayList<String>> _tableChamps;
    public ArrayList<String> _valeurChamps;
    
    public int typeService;
    public String nom_type;
    Date date;
    DateFormat dateFormat;
    
    public GestionnaireService() {
    	_serviceActif = new ArrayList<>();
    	_serviceArchive = new ArrayList<>();
    	scroll1 = new ScrollPane();
    	scroll2 = new ScrollPane();
    	_paneActif = new Pane();
    	_paneArchive = new Pane();
    	_site = new Site();
    	actif = new Tab("Actif");
    	archive = new Tab("Archive");
    	_activeTab = 0;
    	_tableChamps = new ArrayList<ArrayList<String>>();
    	_valeurChamps = new ArrayList<>();
    	typeService = 0;
    	nom_type = "";
    	date = new Date();
    	dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }
    
    public void GestionnaireServiceActif(int id_Site) throws SQLException, ClassNotFoundException, IOException{
		
		SimpleDataSource.init("src/projexMedia/database.properties");
		Connection conn = SimpleDataSource.getConnection();
		_serviceActif = new ArrayList<>();
		
		_site.setIdSite(id_Site);
		
		String sql = "SELECT valeurparametre.fk_id_site, ta_service.fk_id_typeService, valeurparametre.actif, typeservice.nom_type FROM valeurparametre,ta_service,typeservice,parametreservice "
				+ "WHERE ta_service.id_service = valeurparametre.fk_id_service AND ta_service.fk_id_typeService = typeservice.id_typeService AND "
				+ "ta_service.fk_id_parametreService = parametreservice.id_parametreService AND valeurparametre.actif=1 AND valeurparametre.historique=1 AND valeurparametre.fk_id_site =" + id_Site + " GROUP BY ta_service.fk_id_typeService";
		
		try {
			PreparedStatement stat = conn.prepareStatement(sql);
			
	        ResultSet rs; 
	        rs = stat.executeQuery(sql);
	        
	        while(rs.next()) {
	        	
	        	String sql2 = "SELECT valeurparametre.valeur, valeurparametre.actif, valeurparametre.fk_id_site, "
	    				+ "ta_service.fk_id_typeService, ta_service.id_service,typeservice.nom_type, parametreservice.nom_parametre, valeurparametre.date, valeurparametre.fk_courriel "
	    				+ "FROM valeurparametre,ta_service,typeservice,parametreservice "
	    				+ "WHERE ta_service.id_service = valeurparametre.fk_id_service AND "
	    				+ "ta_service.fk_id_typeService = typeservice.id_typeService AND "
	    				+ "ta_service.fk_id_parametreService = parametreservice.id_parametreService AND valeurparametre.actif=1 AND valeurparametre.historique=1 AND "
	    				+ "ta_service.fk_id_typeService =" + rs.getInt("fk_id_typeService") + " AND valeurparametre.fk_id_site =" + id_Site + " ORDER BY ta_service.id_service";
	        	
	        	PreparedStatement stat2 = conn.prepareStatement(sql2);
	        	ResultSet rs2;
	        	rs2 = stat2.executeQuery(sql2);
	             while(rs2.next()) {
	            	 _valeurChamps.add(rs2.getString("nom_parametre"));
	            	 _valeurChamps.add(rs2.getString("valeur"));
	            	 _valeurChamps.add(String.valueOf(rs2.getInt("id_service")));
	            	 _valeurChamps.add(String.valueOf(rs2.getDate("date")));
	            	 _valeurChamps.add(rs2.getString("fk_courriel"));
	            	 _tableChamps.add(_valeurChamps);
	            	 _valeurChamps = new ArrayList<>();
	             }
	            
	 	        Service service1 = new Service();
	 	        service1.setCompteurActif(0);
	 	        //_serviceActif.clear();
 	            Service service = new Service(rs.getInt("fk_id_typeService"),rs.getString("nom_type"),_tableChamps,rs.getBoolean("actif"),rs.getInt("fk_id_Site"));
 	            _serviceActif.add(service);
 	            _tableChamps = new ArrayList<ArrayList<String>>();
 	            
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
		_serviceArchive = new ArrayList<>();
		
		String sql = "SELECT valeurparametre.fk_id_site, ta_service.fk_id_typeService, valeurparametre.actif, typeservice.nom_type FROM valeurparametre,ta_service,typeservice,parametreservice "
				+ "WHERE ta_service.id_service = valeurparametre.fk_id_service AND ta_service.fk_id_typeService = typeservice.id_typeService AND "
				+ "ta_service.fk_id_parametreService = parametreservice.id_parametreService AND valeurparametre.actif=0 AND valeurparametre.historique=1 AND valeurparametre.fk_id_site =" + id_Site + " GROUP BY ta_service.fk_id_typeService";
		
		try {
			PreparedStatement stat = conn.prepareStatement(sql);
			
	        ResultSet rs; 
	        rs = stat.executeQuery(sql);
	        
	        while(rs.next()) {
	        	
	        	String sql2 = "SELECT valeurparametre.valeur, valeurparametre.actif, valeurparametre.fk_id_site, "
	    				+ "ta_service.fk_id_typeService, ta_service.id_service,typeservice.nom_type, parametreservice.nom_parametre , valeurparametre.date, valeurparametre.fk_courriel "
	    				+ "FROM valeurparametre,ta_service,typeservice,parametreservice "
	    				+ "WHERE ta_service.id_service = valeurparametre.fk_id_service AND "
	    				+ "ta_service.fk_id_typeService = typeservice.id_typeService AND "
	    				+ "ta_service.fk_id_parametreService = parametreservice.id_parametreService AND valeurparametre.actif=0 AND valeurparametre.historique=1 AND "
	    				+ "ta_service.fk_id_typeService =" + rs.getInt("fk_id_typeService") + " AND valeurparametre.fk_id_site =" + id_Site;
	        	
	        	PreparedStatement stat2 = conn.prepareStatement(sql2);
	        	ResultSet rs2;
	        	rs2 = stat2.executeQuery(sql2);
	             while(rs2.next()) {
	            	 _valeurChamps.add(rs2.getString("nom_parametre"));
	            	 _valeurChamps.add(rs2.getString("valeur"));
	            	 _valeurChamps.add(String.valueOf(rs2.getInt("id_service")));
	            	 _valeurChamps.add(String.valueOf(rs2.getDate("date")));
	            	 _valeurChamps.add(rs2.getString("fk_courriel"));
	            	 _tableChamps.add(_valeurChamps);
	            	 _valeurChamps = new ArrayList<>();
	             }
	            
	 	        Service service1 = new Service();
	 	        service1.setCompteurActif(0);
	 	        //_serviceActif.clear();
 	            Service service = new Service(rs.getInt("fk_id_typeService"),rs.getString("nom_type"),_tableChamps,rs.getBoolean("actif"),rs.getInt("fk_id_Site"));
 	            _serviceArchive.add(service);
 	            _tableChamps = new ArrayList<ArrayList<String>>();
 	            
	        }
		} catch(SQLException ex){
            ex.printStackTrace();
            
        } finally {
            conn.close();
        }
    }
	
	public void afficherService(Stage primaryStage, int id_site) throws Exception {
		afficherServiceActif(primaryStage,id_site);
		afficherServiceArchive(primaryStage,id_site);
       
		actif.setClosable(false);
        actif.setContent(scroll1);
        archive.setContent(scroll2);
        archive.setClosable(false);
                   
        TabPane tabPane = new TabPane();
        tabPane.setPrefSize(MainLogin.bounds.getWidth(), MainLogin.bounds.getHeight());
        
        tabPane.getTabs().add(0, actif);
        tabPane.getTabs().add(archive);
        
        if(_activeTab == 1) {
        	tabPane.getSelectionModel().select(archive);
        }
        
        Scene scene = new Scene(new VBox( tabPane));
        scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
        actif.getStyleClass().add("Tab");
        archive.getStyleClass().add("Tab");    
        primaryStage.setTitle("Service du Site");
        primaryStage.setScene(scene);
        primaryStage.show();
	}
	
	public void afficherServiceActif(Stage primaryStage, int id_site) throws Exception {
		GestionnaireServiceActif(id_site);
          
		double yservice = 0;
		double totalY = 0;
        scroll1.setContent(_paneActif);
		
		Button btnAjout = new Button("Ajouter");
		Button btnRetour = new Button("Retour");
		
		btnAjout.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					interfaceAjouter(primaryStage,id_site);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		btnRetour.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					MainMenu menu = new MainMenu();
					menu.set_activeTab(1);
					menu.start(primaryStage);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		if (_serviceActif.isEmpty()) {
			Pane _group = new Pane();
			Label lblType = new Label("Aucun service");
			lblType.setLayoutX(30);
			lblType.setLayoutY(20);
			_group.getChildren().addAll(lblType);
			_group.getStyleClass().add("bordered-titled-border");
			_group.setLayoutY(80);
			_group.setLayoutX(20);
			_paneActif.getChildren().add(_group);
		}
		else {
			for(int i = 0 ; i < _serviceActif.size() ;i++) {
				
				Pane _group = new Pane();
				_tableChamps = _serviceActif.get(i).get_Champs();
				
				Label lblNom = new Label(_serviceActif.get(i).get_nom());
				double x = 30;
				double y = 0;
				for(int j = 0; j < _tableChamps.size();j++) {
					Label lblChamps = new Label(_tableChamps.get(j).get(0) + " :");
					Label lblValeur = new Label(_tableChamps.get(j).get(1));
					lblChamps.setLayoutX(x);
					lblChamps.setLayoutY(y * 25 + 40);
					lblValeur.setLayoutX(x+170);
					lblValeur.setLayoutY(y * 25 + 40);
					_group.getChildren().addAll(lblChamps,lblValeur);
					if(j <= 7){
						yservice = j * 25 + 60;
					}
					y++;
					if((j % 7)==0 && j != 0) {
						x=MainLogin.bounds.getWidth()/2;
						y=0;
					}
				}
				
				Image imageModif = new Image(getClass().getResourceAsStream("edit.png"));
				Button btnModif = new Button();
				Button btnCopy = new Button();
				ImageView imageM = new ImageView(imageModif);
				Image imageArchive = new Image(getClass().getResourceAsStream("archive1.png"));
				Button btnArchive = new Button();
				ImageView imageA = new ImageView(imageArchive);
				Image imageCopy = new Image(getClass().getResourceAsStream("copy.png"));
				ImageView imageC = new ImageView(imageCopy);
				btnModif.setGraphic(imageM);
				btnArchive.setGraphic(imageA);
				btnCopy.setGraphic(imageC);
				btnModif.setId("" + _serviceActif.get(i).get_id());
				btnModif.setTooltip(new Tooltip("Modifier"));
				btnArchive.setTooltip(new Tooltip("Archiver"));
				btnCopy.setTooltip(new Tooltip("Copier"));
				
				btnModif.setId(String.valueOf(i));
				
				btnModif.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						try {
							interfaceModifier(primaryStage, btnModif.getId());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
				
				btnArchive.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						try {
							archiverService(_serviceActif.get(Integer.parseInt(btnModif.getId())));
							_paneActif.getChildren().clear();
							afficherServiceActif(primaryStage, id_site);
							_paneArchive.getChildren().clear();
							afficherServiceArchive(primaryStage, id_site);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
				
				btnCopy.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						String myString = "";
						
						
						for(int j = 0; j < _serviceActif.get(Integer.parseInt(btnModif.getId())).get_Champs().size();j++) {
								myString += _serviceActif.get(Integer.parseInt(btnModif.getId())).get_Champs().get(j).get(0) + " :" +  _serviceActif.get(Integer.parseInt(btnModif.getId())).get_Champs().get(j).get(1) + "\n";
						}
						StringSelection stringSelection = new StringSelection(myString);
						Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
						clpbrd.setContents(stringSelection, null);
					}
				});
				
				//position Nom
				lblNom.setLayoutX(30);
				lblNom.setLayoutY(10);
				
				btnModif.setLayoutX(MainLogin.bounds.getWidth()-265);
				btnModif.setLayoutY(20);
				btnModif.setPadding(Insets.EMPTY);
				
				btnArchive.setLayoutX(MainLogin.bounds.getWidth()-215);
				btnArchive.setLayoutY(20);
				btnArchive.setPadding(Insets.EMPTY);
				
				btnCopy.setLayoutX(MainLogin.bounds.getWidth()-165);
				btnCopy.setLayoutY(20);
				btnCopy.setPadding(Insets.EMPTY);
				
				_group.getChildren().add(btnModif);
				
				_group.getChildren().add(btnArchive);
				_group.getChildren().add(btnCopy);
				
		        lblNom.getStyleClass().add("typeNom");
		      
				
				if(MainMenu._role.get_modifier().equals("")) {
					btnModif.setDisable(true);
				}
				
				if(MainMenu._role.get_archiver().equals("")) {
					btnArchive.setDisable(true);
				}
				
		        
				_group.getChildren().add(lblNom);
				_group.getStyleClass().add("bordered-titled-border");
				_group.setLayoutY(totalY + 80);
				_group.setLayoutX(20);
				_group.setMinWidth(MainLogin.bounds.getWidth()-100);
				_paneActif.getChildren().add(_group);
				totalY += yservice + 40;
				
				
			}
		}
		
		btnAjout.setLayoutX(MainLogin.bounds.getWidth()-250);
		btnAjout.setLayoutY(20);
		btnAjout.setMinHeight(50);
		btnAjout.setMinWidth(150);
		
		
		if(MainMenu._role.get_ajouter().equals("")) {
			btnAjout.setDisable(true);
		}
		
		_paneActif.getChildren().add(btnAjout);
		btnRetour.setLayoutX(20);
		btnRetour.setLayoutY(20);
		btnRetour.setMinHeight(50);
		btnRetour.setMinWidth(150);
		_paneActif.getChildren().add(btnRetour);
		
		_paneActif.getStyleClass().add("fontFormulaire");
		_tableChamps = new ArrayList<ArrayList<String>>();
	}

	public void afficherServiceArchive(Stage primaryStage, int id_site) throws Exception {

		GestionnaireServiceArchive(id_site);
		
		double yservice = 0;
		double totalY = 0;
        
        scroll2.setContent(_paneArchive);
		
		Button btnRetour = new Button("Retour");
		
		btnRetour.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					MainMenu menu = new MainMenu();
					menu.set_activeTab(1);
					menu.start(primaryStage);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		if (_serviceArchive.isEmpty()) {
			Pane _group = new Pane();
			Label lblType = new Label("Aucun service");
			lblType.setLayoutX(30);
			lblType.setLayoutY(20);
			_group.getChildren().addAll(lblType);
			_group.getStyleClass().add("bordered-titled-border");
			_group.setLayoutY(80);
			_group.setLayoutX(20);
			_paneArchive.getChildren().add(_group);
		}
		else {
			for(int i = 0 ; i < _serviceArchive.size() ;i++) {
				
				Pane _group = new Pane();
				_tableChamps = _serviceArchive.get(i).get_Champs();
				
				Label lblNom = new Label(_serviceArchive.get(i).get_nom());
				double x = 30;
				double y = 0;
				for(int j = 0; j < _tableChamps.size();j++) {
					Label lblChamps = new Label(_tableChamps.get(j).get(0) + " :");
					Label lblValeur = new Label(_tableChamps.get(j).get(1));
					lblChamps.setLayoutX(x);
					lblChamps.setLayoutY(y * 25 + 40);
					lblValeur.setLayoutX(x+170);
					lblValeur.setLayoutY(y * 25 + 40);
					_group.getChildren().addAll(lblChamps,lblValeur);
					if(j <= 7){
						yservice = j * 25 + 60;
					}
					y++;
					if((j % 7)==0 && j != 0) {
						x=MainLogin.bounds.getWidth()/2;
						y=0;
					}
				}
				
				
				Image imageActiver = new Image(getClass().getResourceAsStream("active1.jpg"));
				Button btnActive = new Button();
				ImageView imageM = new ImageView(imageActiver);
				Image imageSupprimer = new Image(getClass().getResourceAsStream("delete1.jpg"));
				Button btnSupprimer = new Button();
				ImageView imageA = new ImageView(imageSupprimer);
				btnActive.setGraphic(imageM);
				btnSupprimer.setGraphic(imageA);
				btnActive.setId("" + i);
				btnActive.setTooltip(new Tooltip("Activer"));
				btnSupprimer.setTooltip(new Tooltip("Supprimer"));
				
				btnSupprimer.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						Alert alert = new Alert(AlertType.CONFIRMATION);
						alert.setTitle("Message de confirmation");
						alert.setHeaderText("�tes-vous s�r de vouloir supprimer ce service? \n Car cela suuprimeras ces donn�es dans l'archive d�finitivement");
						// alert.setContentText("Are you ok with this?");

						Optional<ButtonType> result = alert.showAndWait();
						if (result.get() == ButtonType.OK) {
							try {
								supprimerService(_serviceArchive.get(Integer.parseInt(btnActive.getId())));
								_paneArchive.getChildren().clear();
								afficherServiceArchive(primaryStage, id_site);
							}catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						} 
					}
				});
				
				btnActive.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						try {
							activerService(_serviceArchive.get(Integer.parseInt(btnActive.getId())));
							_paneArchive.getChildren().clear();
							afficherServiceArchive(primaryStage, id_site);
							_paneActif.getChildren().clear();
							afficherServiceActif(primaryStage, id_site);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});
				
				//position Nom
				lblNom.setLayoutX(30);
				lblNom.setLayoutY(10);
				
				btnActive.setLayoutX(MainLogin.bounds.getWidth()-215);
				btnActive.setLayoutY(20);
				btnActive.setPadding(Insets.EMPTY);
				
				btnSupprimer.setLayoutX(MainLogin.bounds.getWidth()-165);
				btnSupprimer.setLayoutY(20);
				btnSupprimer.setPadding(Insets.EMPTY);
				
				if(MainMenu._role.get_activer().equals("")) {
					btnActive.setDisable(true);
				}
				
				if(MainMenu._role.get_supprimer().equals("")) {
					btnSupprimer.setDisable(true);
				}
				
				_group.getChildren().add(btnActive);
				
				_group.getChildren().add(btnSupprimer);
				
				lblNom.getStyleClass().add("typeNom");
				
				_group.getChildren().add(lblNom);
				_group.getStyleClass().add("bordered-titled-border");
				_group.setLayoutY(totalY + 80);
				_group.setLayoutX(20);
				_group.setMinWidth(MainLogin.bounds.getWidth()-100);
				_paneArchive.getChildren().add(_group);
				totalY += yservice + 40;
			}
		}
		
		btnRetour.setLayoutX(20);
		btnRetour.setLayoutY(20);
		btnRetour.setMinHeight(50);
		btnRetour.setMinWidth(150);		
		_paneArchive.getStyleClass().add("fontFormulaire");
		_paneArchive.getChildren().add(btnRetour);
		_tableChamps = new ArrayList<ArrayList<String>>();
	}
	
	public void ajouterService(Service e) throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        Connection conn = SimpleDataSource.getConnection();
        try {
            PreparedStatement stat = conn.prepareStatement(
                    "INSERT INTO valeurparametre (valeur, date, action, actif, fk_id_service,fk_id_site,fk_courriel)"
                    + " VALUES (?, ?, 'Ajouter', 1, ?, ?, ?)");
            for(int j = 0; j < e.get_Champs().size();j++) {
            	/*java.sql.Date sql = new java.sql.Date(formatter.parse(e.get_Champs().get(j).get(3)).getTime());*/
            	java.sql.Date sql= new java.sql.Date(date.getTime());
	            stat.setString(1, e.get_Champs().get(j).get(1));
	            stat.setDate(2, sql );
	            stat.setInt(3, Integer.parseInt(e.get_Champs().get(j).get(2)));
	            stat.setInt(4, e.get_id_Site());
	            stat.setString(5, e.get_Champs().get(j).get(4));     
	            stat.executeUpdate();  
            }
        } catch(SQLException ex){
            ex.printStackTrace();
            
        } finally {
            conn.close();
        }
    }
	
	public void modifierService(int id_service, Service NewE) throws Exception {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        Connection conn = SimpleDataSource.getConnection();
        try {
        	
            PreparedStatement stat = conn.prepareStatement(
                    "UPDATE valeurparametre SET historique = 0"
                    + " WHERE id_valeurParametre = ?");
            for(int j = 0; j < NewE.get_Champs().size();j++) {
            	
            	if(Integer.parseInt(NewE.get_Champs().get(j).get(5)) == 0) {
            		PreparedStatement stat2 = conn.prepareStatement(
                            "INSERT INTO valeurparametre (valeur, date, action, actif, fk_id_service,fk_id_site,fk_courriel)"
                            + " VALUES (?, ?, 'Ajouter', 1, ?, ?, ?)");
            		/*java.sql.Date sql = new java.sql.Date(formatter.parse(NewE.get_Champs().get(j).get(3)).getTime());*/
            		java.sql.Date sql= new java.sql.Date(date.getTime());
    	            stat2.setString(1, NewE.get_Champs().get(j).get(1));
    	            stat2.setDate(2, sql);
    	            stat2.setInt(3, Integer.parseInt(NewE.get_Champs().get(j).get(2)));
    	            stat2.setInt(4, NewE.get_id_Site());
    	            stat2.setString(5, NewE.get_Champs().get(j).get(4));     
    	            stat2.executeUpdate();  
    	            
            	}else {
	            	/*java.sql.Date sql = new java.sql.Date(formatter.parse(NewE.get_Champs().get(j).get(3)).getTime());*/
	            	java.sql.Date sql= new java.sql.Date(date.getTime());
		           /* stat.setString(1, NewE.get_Champs().get(j).get(1));
		            stat.setDate(2, sql);
		            stat.setString(3, "Modifier");
		            stat.setString(4, NewE.get_Champs().get(j).get(4));*/
		            stat.setInt(1, Integer.parseInt(NewE.get_Champs().get(j).get(5)));
		            
		            
		            
		            
		             PreparedStatement stat3 = conn.prepareStatement(
	                            "INSERT INTO valeurparametre (valeur, date, action, actif, fk_id_service,fk_id_site,fk_courriel)"
	                            + " VALUES (?, ?, 'modifier', 1, ?, ?, ?)");
	            		/*java.sql.Date sql = new java.sql.Date(formatter.parse(NewE.get_Champs().get(j).get(3)).getTime());*/
	            		//java.sql.Date sql= new java.sql.Date(date.getTime());
	    	            stat3.setString(1, NewE.get_Champs().get(j).get(1));
	    	            stat3.setDate(2, sql);
	    	            stat3.setInt(3, Integer.parseInt(NewE.get_Champs().get(j).get(2)));
	    	            stat3.setInt(4, NewE.get_id_Site());
	    	            stat3.setString(5, NewE.get_Champs().get(j).get(4));     
		            String verf = new String();
	            	//v�rification de la modification
	            	Statement statV = conn.createStatement();
	
	    			ResultSet result = statV.executeQuery("SELECT valeur From valeurparametre where id_valeurParametre=" +  Integer.parseInt(NewE.get_Champs().get(j).get(5)));
	
	    			while (result.next()) {
	
	    				verf = result.getString("valeur");
	
	    			}
		            
		            
	    			//System.out.print(j + verf + NewE.get_Champs().get(j).get(1) );
		            
		            
		            //if modifier les autres parametre du servcie
		            if (NewE.get_Champs().get(j).get(1).equals(verf) == false) {
		            	stat.executeUpdate();
		            	stat3.executeUpdate();  
		            }
		            
	            }
            }
            
            
            
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
                    "UPDATE valeurparametre JOIN ta_service ON valeurparametre.fk_id_service = ta_service.id_service SET actif = 1"
                    + " WHERE valeurparametre.fk_id_site = ? AND ta_service.fk_id_typeService = ?");
            stat.setInt(1, e.get_id_Site());  
            stat.setInt(2, e.get_id());     
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
                    "UPDATE valeurparametre JOIN ta_service ON valeurparametre.fk_id_service = ta_service.id_service SET actif = 0"
                    + " WHERE valeurparametre.fk_id_site = ? AND ta_service.fk_id_typeService = ?");
            stat.setInt(1, e.get_id_Site());  
            stat.setInt(2, e.get_id());
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
                    "DELETE FROM valeurparametre USING valeurparametre,ta_service WHERE valeurparametre.fk_id_service = ta_service.id_service AND valeurparametre.fk_id_site = ? AND ta_service.fk_id_typeService = ?");
            stat.setInt(1, e.get_id_Site());  
            stat.setInt(2, e.get_id());     
            stat.executeUpdate();                
        } catch(SQLException ex){
            ex.printStackTrace();
            
        } finally {
            conn.close();
        }
    }
	
	public void interfaceAjouter(Stage primaryStage, int idSite) throws SQLException {

		ArrayList<TextField> textfields = new ArrayList<TextField>();
		ObservableList<String> options = FXCollections.observableArrayList();
		ObservableList<String> listType = FXCollections.observableArrayList();
		ObservableList<String> listTypeSite = FXCollections.observableArrayList();
		ObservableList<String> listTypeSiteNoDup = FXCollections.observableArrayList();
		
		Connection conn = SimpleDataSource.getConnection();

		try {
			Statement stat = conn.createStatement();
			Statement stat2 = conn.createStatement();
			
			ResultSet result2 = stat2.executeQuery("SELECT t.fk_id_typeService \r\n" + 
					"FROM `valeurparametre` v join ta_service t on v.fk_id_service = t.id_service \r\n" + 
					"WHERE v.fk_id_site =" + idSite + " ORDER BY t.fk_id_typeService ASC");
			
			while (result2.next()) {
				listTypeSite.add(result2.getString("fk_id_typeService"));
			}
			
			String dup = "";
			for(int i= 0 ;i < listTypeSite.size();i++) {
				if(listTypeSite.get(i).equals(dup)!=true) {
					dup = listTypeSite.get(i);
					listTypeSiteNoDup.add(dup);
					//System.out.println(listTypeSite.get(i));
				}
			}
			
			String list ="";
			for(int i= 0 ;i < listTypeSiteNoDup.size();i++) {
				if(i == 0) {
					list += " Where id_typeService <> " + listTypeSiteNoDup.get(i);
				}else if(i != (listTypeSiteNoDup.size()-1)){
					list += " and id_typeService <> " + listTypeSiteNoDup.get(i);
				}else {
					list +=  " and id_typeService <>" +listTypeSiteNoDup.get(i);
				}
			}
			//System.out.println(list);

			ResultSet result = stat.executeQuery("SELECT nom_type FROM typeservice" + list);
			

			while (result.next()) {
				options.add(result.getString("nom_type"));
			}
			
			

		} finally {
			conn.close();
		}
		
		
		
		Pane root = new Pane();
		Pane _group = new Pane();
		
		// bouton
		Button btnAjouter = new Button("Ajouter");
		Button btnCancel = new Button("Cancel");

		ComboBox<String> cmbType = new ComboBox<String>(options);

		Label lbl = new Label("Type de service :");

		btnAjouter.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if(!cmbType.getSelectionModel().isEmpty()){
					boolean empty = true;
					
					for(int j = 0; j < textfields.size();j++) {
						_tableChamps.get(j).add(1,textfields.get(j).getText());
						if(textfields.get(j).getText().matches(".{1,512}") && empty == true) {
							empty = false;
						}
					}
					
					if(empty == false) {
						try {
							ajouterService(new Service(typeService,nom_type,_tableChamps,true,_site.getIdSite()));
							_tableChamps = new ArrayList<ArrayList<String>>();
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						GestionnaireService service = new GestionnaireService();
						try {
							service.afficherService(primaryStage, _site.getIdSite());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					else {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Message d'erreur");
						alert.setHeaderText("Veuillez remplir au moins un champ et respecter \nla limite de charact�re de 512.");
						Optional<ButtonType> result = alert.showAndWait();
						if (result.get() == ButtonType.OK) {
							
						}
					}
				}
				else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Message d'erreur");
					alert.setHeaderText("Veuillez choisir un type.");
					Optional<ButtonType> result = alert.showAndWait();
					if (result.get() == ButtonType.OK) {
						
					}
				}
			}
		});

		btnCancel.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				GestionnaireService service = new GestionnaireService();
				try {
					service.afficherService(primaryStage, _site.getIdSite());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		
		cmbType.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
            @Override
            public void changed(ObservableValue<?> ov, Object t, Object t1) {

            	_group.getChildren().clear();
            	
        		try {
        			Connection conn = SimpleDataSource.getConnection();
        			Statement stat = conn.createStatement();

        			ResultSet result = stat.executeQuery("SELECT parametreservice.nom_parametre, typeservice.id_typeService, ta_service.id_service, typeservice.nom_type FROM parametreservice "
        					+ "JOIN ta_service ON parametreservice.id_parametreService = ta_service.fk_id_parametreService "
        					+ "JOIN typeservice ON ta_service.fk_id_typeService = typeservice.id_typeService WHERE typeservice.nom_type ='"+ t1.toString() + "'");

        			int nbChamps = 0;
        			int y = 0;
        			while (result.next()) {
        				
        				Label lbl1 = new Label(result.getString("nom_parametre"));
        				TextField tf = new TextField();
        				setUpValidation(tf);
        				if(nbChamps <=7) {
        					lbl1.setLayoutX((MainLogin.bounds.getWidth()/2)-500);
            				lbl1.setLayoutY(y * 50 + ((MainLogin.bounds.getHeight()/2)-125));
            				tf.setLayoutX((MainLogin.bounds.getWidth()/2)-360);
            				tf.setLayoutY(y * 50 + ((MainLogin.bounds.getHeight()/2)-125));
            				tf.setPrefWidth(300);
        				}
        				else {
        					if(y == 8) {
        						y=0;
        					}
        					lbl1.setLayoutX((MainLogin.bounds.getWidth()/2)-20);
            				lbl1.setLayoutY(y * 50 + ((MainLogin.bounds.getHeight()/2)-125));
            				tf.setLayoutX((MainLogin.bounds.getWidth()/2)+120);
            				tf.setLayoutY(y * 50 + ((MainLogin.bounds.getHeight()/2)-125));
            				tf.setPrefWidth(300);
        				}
        				_group.getChildren().add(lbl1);
        				_group.getChildren().add(tf);
        				textfields.add(tf);
        				typeService = result.getInt("id_typeService");
        				nom_type = result.getString("nom_type");
        				_valeurChamps.add(result.getString("nom_parametre"));
    	            	 _valeurChamps.add(String.valueOf(result.getInt("id_service")));
    	            	 _valeurChamps.add(String.valueOf(dateFormat.format(date)));
    	            	 _valeurChamps.add(MainMenu._username);
    	            	 _tableChamps.add(_valeurChamps);
    	            	 _valeurChamps = new ArrayList<>();
        				nbChamps++;
        				y++;
        			}

        		} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
        			try {
						conn.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
        		}
            	
            	
            }
        });
		
		Label lbltitle = new Label("Ajouter un service");
		lbltitle.setLayoutX((MainLogin.bounds.getWidth()/2)-425);
		lbltitle.setLayoutY((MainLogin.bounds.getHeight()/2)-275);
		lbltitle.setScaleX(2);
		lbltitle.setScaleY(2);
		
		cmbType.setLayoutX((MainLogin.bounds.getWidth()/2)-325);
		cmbType.setLayoutY((MainLogin.bounds.getHeight()/2)-200);
		lbl.setLayoutX((MainLogin.bounds.getWidth()/2)-500);
		lbl.setLayoutY((MainLogin.bounds.getHeight()/2)-200);
		

		// Bouton
		btnAjouter.setLayoutX((MainLogin.bounds.getWidth()/2)-300);
		btnAjouter.setLayoutY((MainLogin.bounds.getHeight()/2)+300);
		btnCancel.setLayoutX((MainLogin.bounds.getWidth()/2)+100);
		btnCancel.setLayoutY((MainLogin.bounds.getHeight()/2)+300);

		btnAjouter.setMinHeight(50);
		btnAjouter.setMinWidth(150);
		btnCancel.setMinHeight(50);
		btnCancel.setMinWidth(150);

		root.getChildren().add(_group);
		
		// add to panel
		root.getChildren().add(lbl);
		root.getChildren().add(cmbType);
		root.getChildren().add(btnAjouter);
		root.getChildren().add(btnCancel);
		root.getChildren().add(lbltitle);

		// create window
		Scene scene = new Scene(root, 450, 370);
		primaryStage.setTitle("Ajouter Service");
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		root.getStyleClass().add("fontFormulaire");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public void interfaceModifier(Stage primaryStage, String id) throws Exception {
		// bouton
		ArrayList<TextField> textfields = new ArrayList<TextField>();
		ObservableList<String> options = FXCollections.observableArrayList();
		
		Connection conn = SimpleDataSource.getConnection();

		try {
			Statement stat = conn.createStatement();

			ResultSet result = stat.executeQuery("SELECT nom_type FROM typeservice");

			while (result.next()) {
				options.add(result.getString("nom_type"));
			}

		} finally {
			conn.close();
		}
		
		Pane root = new Pane();
		Pane _group = new Pane();
		
		// bouton
		Button btnModifier = new Button("Modifier");
		Button btnCancel = new Button("Cancel");

		Label lbl = new Label("Type de service :");
		Label lbl2 = new Label();

		btnModifier.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
					boolean empty = true;
					
					for(int j = 0; j < textfields.size();j++) {
						_tableChamps.get(j).add(1,textfields.get(j).getText());
						if(textfields.get(j).getText().matches(".{1,512}") && empty == true) {
							empty = false;
						}
					}
					
					if(empty == false) {
						try {
							modifierService(Integer.parseInt(id),new Service(typeService,nom_type,_tableChamps,true,_site.getIdSite()));
							_tableChamps = new ArrayList<ArrayList<String>>();
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						GestionnaireService service = new GestionnaireService();
						try {
							service.afficherService(primaryStage, _site.getIdSite());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					else {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Message d'erreur");
						alert.setHeaderText("Veuillez remplir au moins un champ et respecter \nla limite de charact�re de 512.");
						Optional<ButtonType> result = alert.showAndWait();
						if (result.get() == ButtonType.OK) {
							
						}
					}
			}
		});

		btnCancel.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				GestionnaireService service = new GestionnaireService();
				try {
					service.afficherService(primaryStage, _site.getIdSite());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		
	
		try {
			conn = SimpleDataSource.getConnection();
			Statement stat = conn.createStatement();
			Statement stat2 = conn.createStatement();

			ResultSet result = stat.executeQuery("SELECT valeurparametre.id_valeurParametre,valeurparametre.valeur, valeurparametre.actif, valeurparametre.fk_id_site," + 
											"ta_service.fk_id_typeService, ta_service.id_service,typeservice.nom_type, parametreservice.nom_parametre, valeurparametre.date, valeurparametre.fk_courriel " + 
											"FROM valeurparametre,ta_service,typeservice,parametreservice " + 
											"WHERE ta_service.id_service = valeurparametre.fk_id_service AND " + 
											"ta_service.fk_id_typeService = typeservice.id_typeService AND " + 
											"ta_service.fk_id_parametreService = parametreservice.id_parametreService AND valeurparametre.actif=1 AND valeurparametre.historique=1 AND " + 
											"ta_service.fk_id_typeService =" + _serviceActif.get(Integer.parseInt(id)).get_id() + " AND valeurparametre.fk_id_site =" + _serviceActif.get(Integer.parseInt(id)).get_id_Site() + " ORDER BY ta_service.id_service");
			ResultSet result2 = stat2.executeQuery("SELECT parametreservice.nom_parametre, typeservice.id_typeService, ta_service.id_service, typeservice.nom_type FROM parametreservice"
											+ " JOIN ta_service ON parametreservice.id_parametreService = ta_service.fk_id_parametreService "
											+" JOIN typeservice ON ta_service.fk_id_typeService = typeservice.id_typeService WHERE typeservice.nom_type ='" + _serviceActif.get(Integer.parseInt(id)).get_nom() + "'");
			
			int nbChamps = 0;
			int y = 0;
			while (result2.next()) {
				lbl2 = new Label(result2.getString("nom_type"));
				Label lbl1 = new Label(result2.getString("nom_parametre"));
				TextField tf = new TextField();
				setUpValidation(tf);
				if(nbChamps <=7) {
					lbl1.setLayoutX((MainLogin.bounds.getWidth()/2)-500);
    				lbl1.setLayoutY(y * 50 + ((MainLogin.bounds.getHeight()/2)-125));
    				tf.setLayoutX((MainLogin.bounds.getWidth()/2)-360);
    				tf.setLayoutY(y * 50 + ((MainLogin.bounds.getHeight()/2)-125));
    				tf.setPrefWidth(300);
				}
				else {
					if(y == 8) {
						y=0;
					}
					lbl1.setLayoutX((MainLogin.bounds.getWidth()/2)-20);
    				lbl1.setLayoutY(y * 50 + ((MainLogin.bounds.getHeight()/2)-125));
    				tf.setLayoutX((MainLogin.bounds.getWidth()/2)+120);
    				tf.setLayoutY(y * 50 + ((MainLogin.bounds.getHeight()/2)-125));
    				tf.setPrefWidth(300);
				}
				root.getChildren().add(lbl1);
				root.getChildren().add(tf);
				textfields.add(tf);
				typeService = result2.getInt("id_typeService");
				nom_type = result2.getString("nom_type");
				_valeurChamps.add(result2.getString("nom_parametre"));
            	 _valeurChamps.add(String.valueOf(result2.getInt("id_service")));
            	 _valeurChamps.add(String.valueOf(dateFormat.format(date)));
            	 _valeurChamps.add(MainMenu._username);
            	 
            	 if(result.next()){
            		tf.setText(result.getString("valeur"));
            		_valeurChamps.add(String.valueOf(result.getInt("id_valeurParametre")));
            	 }
            	 else {
            		 _valeurChamps.add("0");
            	 }
            	 
            	 _tableChamps.add(_valeurChamps);
            	 _valeurChamps = new ArrayList<>();
				nbChamps++;
				y++;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// panel
		
		
		Label lbltitle = new Label("Modifier un service");
		lbltitle.setLayoutX((MainLogin.bounds.getWidth()/2)-425);
		lbltitle.setLayoutY((MainLogin.bounds.getHeight()/2)-275);
		lbltitle.setScaleX(2);
		lbltitle.setScaleY(2);
		
		lbl.setLayoutX((MainLogin.bounds.getWidth()/2)-500);
		lbl.setLayoutY((MainLogin.bounds.getHeight()/2)-200);
		lbl2.setLayoutX((MainLogin.bounds.getWidth()/2)-325);
		lbl2.setLayoutY((MainLogin.bounds.getHeight()/2)-200);
		
		

		// Bouton
		btnModifier.setLayoutX((MainLogin.bounds.getWidth()/2)-300);
		btnModifier.setLayoutY((MainLogin.bounds.getHeight()/2)+300);
		btnCancel.setLayoutX((MainLogin.bounds.getWidth()/2)+100);
		btnCancel.setLayoutY((MainLogin.bounds.getHeight()/2)+300);

		btnModifier.setMinHeight(50);
		btnModifier.setMinWidth(150);
		btnCancel.setMinHeight(50);
		btnCancel.setMinWidth(150);

		root.getChildren().add(_group);
		
		// add to panel
		root.getChildren().add(lbl);
		root.getChildren().add(lbl2);
		root.getChildren().add(btnModifier);
		root.getChildren().add(btnCancel);
		root.getChildren().add(lbltitle);

		// create window
		Scene scene = new Scene(root, 450, 370);
		primaryStage.setTitle("Ajouter Service");
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		root.getStyleClass().add("fontFormulaire");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	private void setUpValidation(final TextField tf) { 
        tf.textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                validate(tf);
            }

        });

        validate(tf);
    }

    private void validate(TextField tf) {
        ObservableList<String> styleClass = tf.getStyleClass();
        if (!tf.getText().matches(".{0,512}")) {
            if (! styleClass.contains("error")) {
                styleClass.add("error");
            }
        } else {
            // remove all occurrences:
            styleClass.removeAll(Collections.singleton("error"));                    
        }
    }
}

