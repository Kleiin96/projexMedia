package projexMedia;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GestionnaireService {
	
	public ArrayList<Service> _serviceActif = new ArrayList<>();
	public ArrayList<Service> _serviceArchive = new ArrayList<>();
	public int id_Site;
	
	public ScrollPane scroll1 = new ScrollPane();
	public ScrollPane scroll2 = new ScrollPane();
	public Pane _paneActif = new Pane();
	public Pane _paneArchive = new Pane();
	public Site _site = new Site();
	public Tab actif = new Tab("Actif");
    public Tab archive = new Tab("Archive");
    public int _activeTab = 0;
    
    public void GestionnaireServiceActif(int id_Site) throws SQLException, ClassNotFoundException, IOException{
		
		SimpleDataSource.init("src/projexMedia/database.properties");
		Connection conn = SimpleDataSource.getConnection();
		
		_site.setIdSite(id_Site);
		
		
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
	
	public void afficherService(Stage primaryStage, int id_site) throws Exception {
		afficherServiceActif(primaryStage,id_site);
		afficherServiceArchive(primaryStage,id_site);
       
		actif.setClosable(false);
        actif.setContent(scroll1);
        archive.setContent(scroll2);
        archive.setClosable(false);
                   
        TabPane tabPane = new TabPane();
        tabPane.setPrefSize(750, 550);
        
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
          
        scroll1.setContent(_paneActif);
		
		Button btnAjout = new Button("Ajouter");
		Button btnRetour = new Button("Retour");
		
		btnAjout.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					interfaceAjouter(primaryStage);
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
			_group.setLayoutY(60);
			_group.setLayoutX(20);
			_paneActif.getChildren().add(_group);
		}
		else {
			for(int i = 0 ; i < _serviceActif.size() ;i++) {
				
				Pane _group = new Pane();
				
				Label lblNom = new Label(_serviceActif.get(i).get_nom());
				Label lblUrl = new Label("URL : ");
				Label lbl_url = new Label(_serviceActif.get(i).get_url());
				Label lblUsername = new Label("Username : ");
				Label lbl_username = new Label(_serviceActif.get(i).get_username());
				Label lblPassword = new Label("Password : ");
				Label lbl_password = new Label(_serviceActif.get(i).get_password());
				Label lblAutre = new Label("Autre : ");
				Label lbl_autre = new Label(_serviceActif.get(i).get_autre());
				Image imageModif = new Image(getClass().getResourceAsStream("modif.png"));
				Button btnModif = new Button();
				ImageView imageM = new ImageView(imageModif);
				Image imageArchive = new Image(getClass().getResourceAsStream("archive.png"));
				Button btnArchive = new Button();
				ImageView imageA = new ImageView(imageArchive);
				btnModif.setGraphic(imageM);
				btnArchive.setGraphic(imageA);
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
				
				//position Nom
				lblNom.setLayoutX(30);
				lblNom.setLayoutY(20);
				
				//position Username
				lblUsername.setLayoutX(30);
				lblUsername.setLayoutY(50);
				lbl_username.setLayoutX(110);
				lbl_username.setLayoutY(50);
				
				//position Password
				lblPassword.setLayoutX(350);
				lblPassword.setLayoutY(50);
				lbl_password.setLayoutX(430);
				lbl_password.setLayoutY(50);
				
				//position url
				lblUrl.setLayoutX(30);
				lblUrl.setLayoutY(80);
				lbl_url.setLayoutX(80);
				lbl_url.setLayoutY(80);
				
				//position Autre
				lblAutre.setLayoutX(30);
				lblAutre.setLayoutY(110);
				lbl_autre.setLayoutX(110);
				lbl_autre.setLayoutY(110);
				
				btnModif.setLayoutX(650);
				btnModif.setLayoutY(20);
				btnModif.setPadding(Insets.EMPTY);
				
				btnArchive.setLayoutX(650);
				btnArchive.setLayoutY(70);
				btnArchive.setPadding(Insets.EMPTY);
				
				_group.getChildren().add(btnModif);
				
				_group.getChildren().add(btnArchive);
				
				_group.getChildren().addAll(lblNom, lblUrl, lblUsername, lblPassword, lbl_url, lbl_username, lbl_password,lblAutre,lbl_autre);
				_group.getStyleClass().add("bordered-titled-border");
				_group.setLayoutY(i * 150 + 60);
				_group.setLayoutX(20);
				_paneActif.getChildren().add(_group);
			}
		}
		
		btnAjout.setLayoutX(645);
		btnAjout.setLayoutY(20);
		_paneActif.getChildren().add(btnAjout);
		btnRetour.setLayoutX(20);
		btnRetour.setLayoutY(20);
		_paneActif.getChildren().add(btnRetour);
	}

	public void afficherServiceArchive(Stage primaryStage, int id_site) throws Exception {

		GestionnaireServiceArchive(id_site);
        
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
			_group.setLayoutY(60);
			_group.setLayoutX(20);
			_paneArchive.getChildren().add(_group);
		}
		else {
			for(int i = 0 ; i < _serviceArchive.size() ;i++) {
				
				Pane _group = new Pane();
				
				Label lblNom = new Label(_serviceArchive.get(i).get_nom());
				Label lblUrl = new Label("URL : ");
				Label lbl_url = new Label(_serviceArchive.get(i).get_url());
				Label lblUsername = new Label("Username : ");
				Label lbl_username = new Label(_serviceArchive.get(i).get_username());
				Label lblPassword = new Label("Password : ");
				Label lbl_password = new Label(_serviceArchive.get(i).get_password());
				Label lblAutre = new Label("Autre : ");
				Label lbl_autre = new Label(_serviceArchive.get(i).get_autre());
				Image imageActiver = new Image(getClass().getResourceAsStream("active.png"));
				Button btnActive = new Button();
				ImageView imageM = new ImageView(imageActiver);
				Image imageSupprimer = new Image(getClass().getResourceAsStream("delete.png"));
				Button btnSupprimer = new Button();
				ImageView imageA = new ImageView(imageSupprimer);
				btnActive.setGraphic(imageM);
				btnSupprimer.setGraphic(imageA);
				btnActive.setId("" + i);
				
				btnSupprimer.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						Alert alert = new Alert(AlertType.CONFIRMATION);
						alert.setTitle("Message de confirmation");
						alert.setHeaderText("Êtes-vous sûr de vouloir supprimer ce service?");
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
				lblNom.setLayoutY(20);
				
				//position Username
				lblUsername.setLayoutX(30);
				lblUsername.setLayoutY(50);
				lbl_username.setLayoutX(110);
				lbl_username.setLayoutY(50);
				
				//position Password
				lblPassword.setLayoutX(350);
				lblPassword.setLayoutY(50);
				lbl_password.setLayoutX(430);
				lbl_password.setLayoutY(50);
				
				//position url
				lblUrl.setLayoutX(30);
				lblUrl.setLayoutY(80);
				lbl_url.setLayoutX(80);
				lbl_url.setLayoutY(80);
				
				//position Autre
				lblAutre.setLayoutX(30);
				lblAutre.setLayoutY(110);
				lbl_autre.setLayoutX(110);
				lbl_autre.setLayoutY(110);
				
				btnActive.setLayoutX(650);
				btnActive.setLayoutY(20);
				btnActive.setPadding(Insets.EMPTY);
				
				btnSupprimer.setLayoutX(650);
				btnSupprimer.setLayoutY(70);
				btnSupprimer.setPadding(Insets.EMPTY);
				
				_group.getChildren().add(btnActive);
				
				_group.getChildren().add(btnSupprimer);
				
				_group.getChildren().addAll(lblNom, lblUrl, lblUsername, lblPassword, lbl_url, lbl_username, lbl_password,lblAutre,lbl_autre);
				_group.getStyleClass().add("bordered-titled-border");
				_group.setLayoutY(i * 150 + 60);
				_group.setLayoutX(20);
				_paneArchive.getChildren().add(_group);
			}
		}
		
		btnRetour.setLayoutX(20);
		btnRetour.setLayoutY(20);
		_paneArchive.getChildren().add(btnRetour);
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
	
	public void modifierService(int id_service, Service NewE) throws SQLException {
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
            stat.setInt(7, id_service);     
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
	
	public void interfaceAjouter(Stage primaryStage) throws SQLException {

		// bouton
		Button btnAjouter = new Button("Ajouter");
		Button btnCancel = new Button("Cancel");

		// textField
		TextField tfNom = new TextField();
		TextField tfUsername = new TextField();
		TextField tfPassword = new TextField();
		TextField tfAutre = new TextField();
		TextField tfUrl = new TextField();

		// label
		Label lbl = new Label("Nom :");
		Label lbl1 = new Label("Username :");
		Label lbl2 = new Label("Password :");
		Label lbl3 = new Label("Url :");
		Label lbl4 = new Label("Autre :");

		btnAjouter.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

					try {
						ajouterService(new Service(0, tfNom.getText(), tfUrl.getText(), tfUsername.getText(), tfPassword.getText(), tfAutre.getText(), true, _site.getIdSite()));
					} catch (SQLException e1) {
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

		// panel
		Pane root = new Pane();

		// premier champ
		lbl.setLayoutX(50);
		lbl.setLayoutY(70);
		tfNom.setLayoutX(200);
		tfNom.setLayoutY(70);

		// deuxieme champ
		lbl1.setLayoutX(50);
		lbl1.setLayoutY(120);
		tfUsername.setLayoutX(200);
		tfUsername.setLayoutY(120);

		// troisieme champ
		lbl2.setLayoutX(50);
		lbl2.setLayoutY(170);
		tfPassword.setLayoutX(200);
		tfPassword.setLayoutY(170);

		lbl3.setLayoutX(50);
		lbl3.setLayoutY(220);
		tfUrl.setLayoutX(200);
		tfUrl.setLayoutY(220);
		
		lbl4.setLayoutX(50);
		lbl4.setLayoutY(270);
		tfAutre.setLayoutX(200);
		tfAutre.setLayoutY(270);

		// Bouton
		btnAjouter.setLayoutX(50);
		btnAjouter.setLayoutY(320);
		btnCancel.setLayoutX(250);
		btnCancel.setLayoutY(320);

		btnAjouter.setMinHeight(30);
		btnAjouter.setMinWidth(130);
		btnCancel.setMinHeight(30);
		btnCancel.setMinWidth(130);

		// add to panel
		root.getChildren().add(lbl);
		root.getChildren().add(lbl1);
		root.getChildren().add(lbl2);
		root.getChildren().add(tfNom);
		root.getChildren().add(tfUsername);
		root.getChildren().add(tfPassword);
		root.getChildren().add(tfAutre);
		root.getChildren().add(tfUrl);
		root.getChildren().add(lbl3);
		root.getChildren().add(lbl4);
		root.getChildren().add(btnAjouter);
		root.getChildren().add(btnCancel);

		// create window
		Scene scene = new Scene(root, 450, 370);
		primaryStage.setTitle("Ajouter Service");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public void interfaceModifier(Stage primaryStage, String id) {
		// bouton
				Button btnModifier = new Button("Modifier");
				Button btnCancel = new Button("Cancel");

				// textField
				TextField tfNom = new TextField(_serviceActif.get(Integer.parseInt(id)).get_nom());
				TextField tfUsername = new TextField(_serviceActif.get(Integer.parseInt(id)).get_username());
				TextField tfPassword = new TextField(_serviceActif.get(Integer.parseInt(id)).get_password());
				TextArea tfAutre = new TextArea(_serviceActif.get(Integer.parseInt(id)).get_autre());
				TextField tfUrl = new TextField(_serviceActif.get(Integer.parseInt(id)).get_url());

				// label
				Label lbl = new Label("Nom :");
				Label lbl1 = new Label("Username :");
				Label lbl2 = new Label("Password :");
				Label lbl3 = new Label("Url :");
				Label lbl4 = new Label("Autre :");

				btnModifier.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {

							try {
								modifierService(_serviceActif.get(Integer.parseInt(id)).get_id(),new Service(0, tfNom.getText(), tfUrl.getText(), tfUsername.getText(), tfPassword.getText(), tfAutre.getText(), true, _site.getIdSite()));
							} catch (SQLException e1) {
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

				// panel
				Pane root = new Pane();

				// premier champ
				lbl.setLayoutX(50);
				lbl.setLayoutY(70);
				tfNom.setLayoutX(200);
				tfNom.setLayoutY(70);

				// deuxieme champ
				lbl1.setLayoutX(50);
				lbl1.setLayoutY(120);
				tfUsername.setLayoutX(200);
				tfUsername.setLayoutY(120);

				// troisieme champ
				lbl2.setLayoutX(50);
				lbl2.setLayoutY(170);
				tfPassword.setLayoutX(200);
				tfPassword.setLayoutY(170);

				lbl3.setLayoutX(50);
				lbl3.setLayoutY(220);
				tfUrl.setLayoutX(200);
				tfUrl.setLayoutY(220);
				
				lbl4.setLayoutX(50);
				lbl4.setLayoutY(270);
				tfAutre.setLayoutX(200);
				tfAutre.setLayoutY(270);

				// Bouton
				btnModifier.setLayoutX(50);
				btnModifier.setLayoutY(350);
				btnCancel.setLayoutX(250);
				btnCancel.setLayoutY(350);

				tfAutre.setMaxHeight(70);
				tfAutre.setMaxWidth(230);
				btnModifier.setMinHeight(30);
				btnModifier.setMinWidth(130);
				btnCancel.setMinHeight(30);
				btnCancel.setMinWidth(130);

				// add to panel
				root.getChildren().add(lbl);
				root.getChildren().add(lbl1);
				root.getChildren().add(lbl2);
				root.getChildren().add(tfNom);
				root.getChildren().add(tfUsername);
				root.getChildren().add(tfPassword);
				root.getChildren().add(tfAutre);
				root.getChildren().add(tfUrl);
				root.getChildren().add(lbl3);
				root.getChildren().add(lbl4);
				root.getChildren().add(btnModifier);
				root.getChildren().add(btnCancel);

				// create window
				Scene scene = new Scene(root, 450, 400);
				primaryStage.setTitle("Modifier Service");
				primaryStage.setScene(scene);
				primaryStage.show();
	}
}
