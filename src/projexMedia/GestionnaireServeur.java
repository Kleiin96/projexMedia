package projexMedia;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.controlsfx.control.textfield.TextFields;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GestionnaireServeur {
	Serveur _test;
	Tab _serveurTab;
	Pane _pane;

	public ObservableList<Serveur> list = FXCollections.observableArrayList();
	
	public Pane getServeurPane() {
		return _pane;
	}
	
	public Tab getServeurTab() {
		return _serveurTab;
	}
	
	public GestionnaireServeur() {
		_test = new Serveur();
		_serveurTab = new Tab("Serveur");
		_pane = new Pane();
	}
	
	/**
	 * creat le pane pour le tab de la fenetre principale 
	 * @param primaryStage
	 * @throws Exception
	 */
	@SuppressWarnings({ "unchecked"})
	public void createPane(Stage primaryStage) throws Exception{
		
		SimpleDataSource.init("src/projexMedia/database.properties");
		
		
		
		
		affichageServeur();
		TableView<Serveur> tableServeur = new TableView<Serveur>();
		TableColumn<Serveur, Integer> id = new TableColumn<Serveur, Integer>("id Serveur");
		TableColumn<Serveur, String> nom = new TableColumn<Serveur, String>("nom Serveur");
		//TableColumn<Serveur, String> tel = new TableColumn<Serveur, String>("Fournisseur");
		//TableColumn<Serveur, String> nomR = new TableColumn<Serveur, String>("nom Responsable");
		//TableColumn<Serveur, String> adresse = new TableColumn<Serveur, String>("adresse");
		//TableColumn<Serveur, String> courriel = new TableColumn<Serveur, String>("courriel");
		

		Button btnAjouter = new Button();
		Button btnModifier = new Button();
		Button btnArchiver = new Button();
		Button btnConsArch = new Button();
		//Image imageRecherche = new Image(getClass().getResourceAsStream("loupe.png"));
		//Button btnRecherche = new Button();
		//ImageView image = new ImageView(imageRecherche);

		//btnRecherche.setGraphic(image);
		
		//TextField tfRecherche = new TextField();

		// construction de la table
		tableServeur.setEditable(false);
		tableServeur.setPrefSize(525, 525);

		id.setCellValueFactory(new PropertyValueFactory<Serveur, Integer>("idServeur"));
		nom.setCellValueFactory(new PropertyValueFactory<Serveur, String>("nomServeur"));
		tableServeur.getColumns().addAll(id, nom/*, tel, nomR, adresse, courriel*/);

		id.prefWidthProperty().bind(tableServeur.widthProperty().divide(2));
		nom.prefWidthProperty().bind(tableServeur.widthProperty().divide(2));
		tableServeur.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tableServeur.setItems(list);
		

		btnAjouter.setText("Ajouter");
		btnAjouter.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				ajouterServeur(primaryStage);
			}
		});
		btnModifier.setText("Modifier");
		btnModifier.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				if (tableServeur.getSelectionModel().getSelectedItem() != null) {
					
					modifierServeur(primaryStage, tableServeur.getSelectionModel().getSelectedItem());
				}
				
			}

		});
		btnArchiver.setText("Archiver");
		btnArchiver.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
					
					try {
						tableServeur.getSelectionModel().getSelectedItem().archiverServeur();
						MainMenu test = new MainMenu();
						test.set_activeTab(2);
						test.start(primaryStage);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				
				
			}
		});

		btnConsArch.setText("Consulter Archives");
		btnConsArch.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					afficherArchive(primaryStage);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		tableServeur.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
					Node node = ((Node) event.getTarget()).getParent();
					TableRow<?> row;
					if (node instanceof TableRow) {
						row = (TableRow<?>) node;
					} else {
						// clicking on text part
						row = (TableRow<?>) node.getParent();
					}
					// System.out.println(row.getItem());
					
					try {
						infoServeur(primaryStage,tableServeur.getSelectionModel().getSelectedItem());
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});


		btnAjouter.setLayoutX(30);
		btnAjouter.setLayoutY(60);
		btnModifier.setLayoutX(30);
		btnModifier.setLayoutY(140);
		btnArchiver.setLayoutX(30);
		btnArchiver.setLayoutY(220);
		btnConsArch.setLayoutX(30);
		btnConsArch.setLayoutY(530);
		

		btnAjouter.setMinHeight(50);
		btnAjouter.setMinWidth(150);
		btnModifier.setMinHeight(50);
		btnModifier.setMinWidth(150);
		btnArchiver.setMinHeight(50);
		btnArchiver.setMinWidth(150);
		btnConsArch.setMinHeight(50);
		btnConsArch.setMinWidth(150);
		

		// layout list
		tableServeur.setLayoutX(200);
		tableServeur.setLayoutY(60);

		_pane.getChildren().add(tableServeur);
		_pane.getChildren().add(btnAjouter);
		_pane.getChildren().add(btnModifier);
		_pane.getChildren().add(btnArchiver);
		_pane.getChildren().add(btnConsArch);
		/*_pane.getChildren().add(btnRecherche);
		_pane.getChildren().add(tfRecherche);*/
	}
	
	
	/**
	 * interface pour ajouter un serveur
	 * @param primaryStage
	 */
	public void ajouterServeur(Stage primaryStage) {
		// bouton
				Button btn = new Button("Ajouter");
				Button btn1 = new Button("Cancel");

				// textField
				TextField tf = new TextField();
				TextField tf1 = new TextField();
				TextField tf2 = new TextField();
				TextField tf3 = new TextField();
				TextField tf4 = new TextField();
				TextField tf5 = new TextField();
				TextField tf6 = new TextField();
				TextField tf7 = new TextField();
				TextField tf8 = new TextField();
				TextField tf9 = new TextField();
				TextField tf10 = new TextField();
				TextField tf11 = new TextField();
				TextField tf12 = new TextField();
				TextField tf13 = new TextField();
				TextField tf14 = new TextField();
				TextField tf15 = new TextField();

				// label
				Label lbl = new Label("Nom du serveur");
				Label lbl1 = new Label("Fournisseur");
				Label lbl2 = new Label("Gestion compte url");
				Label lbl3 = new Label("gestion compte username");
				Label lbl4 = new Label("gestion compte password");
				
				Label lbl5 =new Label("Nameserver 1 ");
				Label lbl6 =new Label("Nameserver 2 ");
				Label lbl7 =new Label("Nameserver 3 ");
				Label lbl8 =new Label("Nameserver 4 ");
				
				Label lbl9 =new Label("CPANEL url");
				Label lbl10 =new Label("CPANEL username");
				Label lbl11 =new Label("CPANEL password");
				
				Label lbl12 =new Label("WHM url");
				Label lbl13 =new Label("WHM username");
				Label lbl14 =new Label("WHM password");
				
				Label lbl15 =new Label("Mot de passe ROOT");

				// panel
				Pane root = new Pane();
				// BorderPane root1 = new BorderPane();
				btn.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						Serveur lul = new Serveur(1,tf.getText(), tf1.getText(), tf2.getText(), tf3.getText(), tf4.getText(), tf5.getText(), tf6.getText()
								, tf7.getText(), tf8.getText(), tf9.getText(), tf10.getText(), tf11.getText(), tf12.getText(), tf13.getText(), tf14.getText()
								, tf15.getText(),true);
						try {
							lul.ajouterServeur();
							MainMenu test = new MainMenu();
							test.set_activeTab(2);
							test.start(primaryStage);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});

				btn1.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						MainMenu menu = new MainMenu();
						menu.set_activeTab(2);
						try {
							menu.start(primaryStage);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
					}
				});

				// premier champ
				lbl.setLayoutX(50);
				lbl.setLayoutY(100);
				tf.setLayoutX(200);
				tf.setLayoutY(100);

				// deuxieme champ
				lbl1.setLayoutX(50);
				lbl1.setLayoutY(150);
				tf1.setLayoutX(200);
				tf1.setLayoutY(150);

				// troisieme champ
				lbl2.setLayoutX(50);
				lbl2.setLayoutY(200);
				tf2.setLayoutX(200);
				tf2.setLayoutY(200);

				// quatrieme champ
				lbl3.setLayoutX(50);
				lbl3.setLayoutY(250);
				tf3.setLayoutX(200);
				tf3.setLayoutY(250);

				// cinquieme champ
				lbl4.setLayoutX(50);
				lbl4.setLayoutY(300);
				tf4.setLayoutX(200);
				tf4.setLayoutY(300);
				
				lbl5.setLayoutX(50);
				lbl5.setLayoutY(350);
				tf5.setLayoutX(200);
				tf5.setLayoutY(350);
				
				//sixeme champs
				lbl6.setLayoutX(50);
				lbl6.setLayoutY(400);
				tf6.setLayoutX(200);
				tf6.setLayoutY(400);
				
				//septieme champ
				lbl7.setLayoutX(50);
				lbl7.setLayoutY(450);
				tf7.setLayoutX(200);
				tf7.setLayoutY(450);
				
				//huitieme champ
				lbl8.setLayoutX(50);
				lbl8.setLayoutY(500);
				tf8.setLayoutX(200);
				tf8.setLayoutY(500);
				
				//neuviemem champ
				lbl9.setLayoutX(50);
				lbl9.setLayoutY(550);
				tf9.setLayoutX(200);
				tf9.setLayoutY(550);
				
				//dixieme champ
				lbl10.setLayoutX(50);
				lbl10.setLayoutY(600);
				tf10.setLayoutX(200);
				tf10.setLayoutY(600);
				
				//onzieme champs
				lbl11.setLayoutX(50);
				lbl11.setLayoutY(650);
				tf11.setLayoutX(200);
				tf11.setLayoutY(650);
				
				//douzieme champ
				lbl12.setLayoutX(50);
				lbl12.setLayoutY(700);
				tf12.setLayoutX(200);
				tf12.setLayoutY(700);
				
				//treizieme champ
				lbl13.setLayoutX(50);
				lbl13.setLayoutY(750);
				tf13.setLayoutX(200);
				tf13.setLayoutY(750);
				
				//quatarzieme chmap
				lbl14.setLayoutX(50);
				lbl14.setLayoutY(800);
				tf14.setLayoutX(200);
				tf14.setLayoutY(800);
				
				//quinzieme champ
				lbl15.setLayoutX(50);
				lbl15.setLayoutY(850);
				tf15.setLayoutX(200);
				tf15.setLayoutY(850);

				// Bouton
				btn.setLayoutX(50);
				btn.setLayoutY(900);
				btn1.setLayoutX(275);
				btn1.setLayoutY(900);


				
				// add to panel
				root.getChildren().add(lbl);
				root.getChildren().add(lbl1);
				root.getChildren().add(lbl2);
				root.getChildren().add(lbl3);
				root.getChildren().add(lbl4);
				root.getChildren().add(lbl5);
				root.getChildren().add(lbl6);
				root.getChildren().add(lbl7);
				root.getChildren().add(lbl8);
				root.getChildren().add(lbl9);
				root.getChildren().add(lbl10);
				root.getChildren().add(lbl11);
				root.getChildren().add(lbl12);
				root.getChildren().add(lbl13);
				root.getChildren().add(lbl14);
				root.getChildren().add(lbl15);
				root.getChildren().add(tf);
				root.getChildren().add(tf1);
				root.getChildren().add(tf2);
				root.getChildren().add(tf3);
				root.getChildren().add(tf4);
				root.getChildren().add(tf5);
				root.getChildren().add(tf6);
				root.getChildren().add(tf7);
				root.getChildren().add(tf8);
				root.getChildren().add(tf9);
				root.getChildren().add(tf10);
				root.getChildren().add(tf11);
				root.getChildren().add(tf12);
				root.getChildren().add(tf13);
				root.getChildren().add(tf14);
				root.getChildren().add(tf15);
				root.getChildren().add(btn);
				root.getChildren().add(btn1);

				// create window
				Scene scene = new Scene(root, 450, 950);
				primaryStage.setTitle("Ajouter Serveur");
				primaryStage.setScene(scene);
				primaryStage.centerOnScreen();
				primaryStage.show();
		
	}
	
	/**
	 * interface pour modifier un serveur
	 * @param primaryStage
	 * @param serveur
	 */
	public void modifierServeur(Stage primaryStage, Serveur serveur) {
		// bouton
				Button btn = new Button("Modifier");
				Button btn1 = new Button("Cancel");

				// textField
				TextField tf = new TextField(serveur.getNomServeur());
				TextField tf1 = new TextField(serveur.getFournisseur());
				TextField tf2 = new TextField(serveur.getGestionCompteUrl());
				TextField tf3 = new TextField(serveur.getGestionCompteUsername());
				TextField tf4 = new TextField(serveur.getGestionComptePassword());
				TextField tf5 = new TextField(serveur.getNameServer1());
				TextField tf6 = new TextField(serveur.getNameServer2());
				TextField tf7 = new TextField(serveur.getNameServer3());
				TextField tf8 = new TextField(serveur.getNameServer4());
				TextField tf9 = new TextField(serveur.getcPANELUrl());
				TextField tf10 = new TextField(serveur.getcPANELUsername());
				TextField tf11 = new TextField(serveur.getcPANELPassword());
				TextField tf12 = new TextField(serveur.getWHMUrl());
				TextField tf13 = new TextField(serveur.getWHMUsername());
				TextField tf14 = new TextField(serveur.getWHMPassword());
				TextField tf15 = new TextField(serveur.getPasswordRoot());

				// label
				Label lbl = new Label("Nom du serveur");
				Label lbl1 = new Label("Fournisseur");
				Label lbl2 = new Label("Gestion compte url");
				Label lbl3 = new Label("gestion compte username");
				Label lbl4 = new Label("gestion compte password");
				
				Label lbl5 =new Label("Nameserver 1 ");
				Label lbl6 =new Label("Nameserver 2 ");
				Label lbl7 =new Label("Nameserver 3 ");
				Label lbl8 =new Label("Nameserver 4 ");
				
				Label lbl9 =new Label("CPANEL url");
				Label lbl10 =new Label("CPANEL username");
				Label lbl11 =new Label("CPANEL password");
				
				Label lbl12 =new Label("WHM url");
				Label lbl13 =new Label("WHM username");
				Label lbl14 =new Label("WHM password");
				
				Label lbl15 =new Label("Mot de passe ROOT");

				// panel
				Pane root = new Pane();
				// BorderPane root1 = new BorderPane();
				btn.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						Serveur lul = new Serveur(serveur.getIdServeur(),tf.getText(), tf1.getText(), tf2.getText(), tf3.getText(), tf4.getText(), tf5.getText(), tf6.getText()
								, tf7.getText(), tf8.getText(), tf9.getText(), tf10.getText(), tf11.getText(), tf12.getText(), tf13.getText(), tf14.getText()
								, tf15.getText(),true);
						try {
							lul.modifierServeur();
							MainMenu test = new MainMenu();
							test.set_activeTab(2);
							test.start(primaryStage);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				});

				btn1.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						MainMenu menu = new MainMenu();
						menu.set_activeTab(2);
						try {
							menu.start(primaryStage);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
					}
				});

				// premier champ
				lbl.setLayoutX(50);
				lbl.setLayoutY(100);
				tf.setLayoutX(200);
				tf.setLayoutY(100);

				// deuxieme champ
				lbl1.setLayoutX(50);
				lbl1.setLayoutY(150);
				tf1.setLayoutX(200);
				tf1.setLayoutY(150);

				// troisieme champ
				lbl2.setLayoutX(50);
				lbl2.setLayoutY(200);
				tf2.setLayoutX(200);
				tf2.setLayoutY(200);

				// quatrieme champ
				lbl3.setLayoutX(50);
				lbl3.setLayoutY(250);
				tf3.setLayoutX(200);
				tf3.setLayoutY(250);

				// cinquieme champ
				lbl4.setLayoutX(50);
				lbl4.setLayoutY(300);
				tf4.setLayoutX(200);
				tf4.setLayoutY(300);
				
				lbl5.setLayoutX(50);
				lbl5.setLayoutY(350);
				tf5.setLayoutX(200);
				tf5.setLayoutY(350);
				
				//sixeme champs
				lbl6.setLayoutX(50);
				lbl6.setLayoutY(400);
				tf6.setLayoutX(200);
				tf6.setLayoutY(400);
				
				//septieme champ
				lbl7.setLayoutX(50);
				lbl7.setLayoutY(450);
				tf7.setLayoutX(200);
				tf7.setLayoutY(450);
				
				//huitieme champ
				lbl8.setLayoutX(50);
				lbl8.setLayoutY(500);
				tf8.setLayoutX(200);
				tf8.setLayoutY(500);
				
				//neuviemem champ
				lbl9.setLayoutX(50);
				lbl9.setLayoutY(550);
				tf9.setLayoutX(200);
				tf9.setLayoutY(550);
				
				//dixieme champ
				lbl10.setLayoutX(50);
				lbl10.setLayoutY(600);
				tf10.setLayoutX(200);
				tf10.setLayoutY(600);
				
				//onzieme champs
				lbl11.setLayoutX(50);
				lbl11.setLayoutY(650);
				tf11.setLayoutX(200);
				tf11.setLayoutY(650);
				
				//douzieme champ
				lbl12.setLayoutX(50);
				lbl12.setLayoutY(700);
				tf12.setLayoutX(200);
				tf12.setLayoutY(700);
				
				//treizieme champ
				lbl13.setLayoutX(50);
				lbl13.setLayoutY(750);
				tf13.setLayoutX(200);
				tf13.setLayoutY(750);
				
				//quatarzieme chmap
				lbl14.setLayoutX(50);
				lbl14.setLayoutY(800);
				tf14.setLayoutX(200);
				tf14.setLayoutY(800);
				
				//quinzieme champ
				lbl15.setLayoutX(50);
				lbl15.setLayoutY(850);
				tf15.setLayoutX(200);
				tf15.setLayoutY(850);

				// Bouton
				btn.setLayoutX(50);
				btn.setLayoutY(900);
				btn1.setLayoutX(275);
				btn1.setLayoutY(900);

				
				// add to panel
				root.getChildren().add(lbl);
				root.getChildren().add(lbl1);
				root.getChildren().add(lbl2);
				root.getChildren().add(lbl3);
				root.getChildren().add(lbl4);
				root.getChildren().add(lbl5);
				root.getChildren().add(lbl6);
				root.getChildren().add(lbl7);
				root.getChildren().add(lbl8);
				root.getChildren().add(lbl9);
				root.getChildren().add(lbl10);
				root.getChildren().add(lbl11);
				root.getChildren().add(lbl12);
				root.getChildren().add(lbl13);
				root.getChildren().add(lbl14);
				root.getChildren().add(lbl15);
				root.getChildren().add(tf);
				root.getChildren().add(tf1);
				root.getChildren().add(tf2);
				root.getChildren().add(tf3);
				root.getChildren().add(tf4);
				root.getChildren().add(tf5);
				root.getChildren().add(tf6);
				root.getChildren().add(tf7);
				root.getChildren().add(tf8);
				root.getChildren().add(tf9);
				root.getChildren().add(tf10);
				root.getChildren().add(tf11);
				root.getChildren().add(tf12);
				root.getChildren().add(tf13);
				root.getChildren().add(tf14);
				root.getChildren().add(tf15);
				root.getChildren().add(btn);
				root.getChildren().add(btn1);

				// create window
				Scene scene = new Scene(root, 450, 950);
				primaryStage.setTitle("Modifier Serveur");
				primaryStage.setScene(scene);
				primaryStage.show();
		
	}

	/**
	 * affichage des archives pour les serveurs
	 * @param primaryStage
	 * @throws Exception
	 */
	public void afficherArchive(Stage primaryStage) throws Exception {
		affichageServeurArch();

		TableView<Serveur> tableServeur = new TableView<Serveur>();
		TableColumn<Serveur, Integer> id = new TableColumn<Serveur, Integer>("id Serveur");
		TableColumn<Serveur, String> nom = new TableColumn<Serveur, String>("nom Serveur");
		
		Button btnActiver = new Button();
		Button btnSupprimer = new Button();
		Button btnRetour = new Button();
		
		Pane root = new Pane();
		

		// construction de la table
		tableServeur.setEditable(false);
		tableServeur.setPrefSize(525, 525);

		id.setCellValueFactory(new PropertyValueFactory<Serveur, Integer>("idServeur"));
		nom.setCellValueFactory(new PropertyValueFactory<Serveur, String>("nomServeur"));
		
		tableServeur.getColumns().addAll(id, nom);

		
		tableServeur.setItems(list);

		btnActiver.setText("Activer");
		btnActiver.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					tableServeur.getSelectionModel().getSelectedItem().activerServeur();
					afficherArchive(primaryStage);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		
		btnSupprimer.setText("Supprimer");
		btnSupprimer.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				if (tableServeur.getSelectionModel().getSelectedItem() != null) {
					try {
						tableServeur.getSelectionModel().getSelectedItem().supprimerServeur();
						afficherArchive(primaryStage);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				// modifierClient(primaryStage);
			}

		});

		btnRetour.setText("Retour");
		btnRetour.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				MainMenu menu = new MainMenu();
				menu.set_activeTab(2);
				try {
					menu.start(primaryStage);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		btnActiver.setLayoutX(30);
		btnActiver.setLayoutY(60);
		btnSupprimer.setLayoutX(30);
		btnSupprimer.setLayoutY(140);
		
		btnRetour.setLayoutX(30);
		btnRetour.setLayoutY(530);

		btnActiver.setMinHeight(50);
		btnActiver.setMinWidth(150);
		btnSupprimer.setMinHeight(50);
		btnSupprimer.setMinWidth(150);
		btnRetour.setMinHeight(50);
		btnRetour.setMinWidth(150);

		// layout list
		tableServeur.setLayoutX(200);
		tableServeur.setLayoutY(60);

		root.getChildren().add(tableServeur);
		root.getChildren().add(btnActiver);
		root.getChildren().add(btnSupprimer);
		root.getChildren().add(btnRetour);
		// create window
		Scene scene = new Scene(root, 750, 650);
		primaryStage.setTitle("Archive Serveur");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	/**
	 * mettre toute la list des serveurs dans l'observable list
	 * @throws SQLException
	 */
	public void affichageServeur() throws SQLException {
		list.clear();
		Connection conn = SimpleDataSource.getConnection();

		try {
			Statement stat = conn.createStatement();

			ResultSet result = stat.executeQuery("SELECT * From serveur where actif=1");

			while (result.next()) {

				list.add(new Serveur(Integer.parseInt(result.getString("id_serveur")), result.getString("nom_serveur"),
						result.getString("fournisseur"), result.getString("gestionCompteURL"),
						result.getString("gestionCompteUsername"), result.getString("gestionComptePassword"),
						result.getString("nameServer1"), result.getString("nameServer2"),
						result.getString("nameServer3"), result.getString("nameServer4"),
						result.getString("cPanelURL"), result.getString("cPanelUsername"),
						result.getString("cPanelPassword"), result.getString("whmURL"),
						result.getString("whmUsername"), result.getString("whmPassword"),result.getString("rootPassword"),true));

			}
		}

		finally {
			conn.close();
		}
	}
	
	/**
	 * remplissage de l'observableList pour l'archive
	 * @throws SQLException
	 */
	public void affichageServeurArch() throws SQLException {
		list.clear();
		Connection conn = SimpleDataSource.getConnection();

		try {
			Statement stat = conn.createStatement();

			ResultSet result = stat.executeQuery("SELECT * From serveur where actif=0");

			while (result.next()) {

				list.add(new Serveur(Integer.parseInt(result.getString("id_serveur")), result.getString("nom_serveur"),
						result.getString("fournisseur"), result.getString("gestionCompteURL"),
						result.getString("gestionCompteUsername"), result.getString("gestionComptePassword"),
						result.getString("nameServer1"), result.getString("nameServer2"),
						result.getString("nameServer3"), result.getString("nameServer4"),
						result.getString("cPanelURL"), result.getString("cPanelUsername"),
						result.getString("cPanelPassword"), result.getString("whmURL"),
						result.getString("whmUsername"), result.getString("whmPassword"),result.getString("rootPassword"),true));

			}
		}

		finally {
			conn.close();
		}
	}
	
	/**
	 * inetrface pour les info complete du serveur
	 * @param primaryStage
	 * @param serv
	 */
	public void infoServeur(Stage primaryStage, Serveur serv) {
		// bouton
				
				Button btn1 = new Button("Retour");

				

				// label
				Label lbl = new Label("Nom du serveur");
				Label lbl1 = new Label("Fournisseur");
				Label lbl2 = new Label("Gestion compte url");
				Label lbl3 = new Label("gestion compte username");
				Label lbl4 = new Label("gestion compte password");
				
				Label lbl5 =new Label("Nameserver 1 ");
				Label lbl6 =new Label("Nameserver 2 ");
				Label lbl7 =new Label("Nameserver 3 ");
				Label lbl8 =new Label("Nameserver 4 ");
				
				Label lbl9 =new Label("CPANEL url");
				Label lbl10 =new Label("CPANEL username");
				Label lbl11 =new Label("CPANEL password");
				
				Label lbl12 =new Label("WHM url");
				Label lbl13 =new Label("WHM username");
				Label lbl14 =new Label("WHM password");
				
				Label lbl15 =new Label("Mot de passe ROOT");
				
				
				
				Label lbl16 = new Label(serv.getNomServeur());
				Label lbl17 = new Label(serv.getFournisseur());
				Label lbl18 = new Label(serv.getGestionCompteUrl());
				Label lbl19 = new Label(serv.getGestionCompteUsername());
				Label lbl20 = new Label(serv.getGestionComptePassword());
				
				Label lbl21 =new Label(serv.getNameServer1());
				Label lbl22 =new Label(serv.getNameServer2());
				Label lbl23 =new Label(serv.getNameServer3());
				Label lbl24 =new Label(serv.getNameServer4());
				
				Label lbl25 =new Label(serv.getcPANELUrl());
				Label lbl26 =new Label(serv.getcPANELUsername());
				Label lbl27 =new Label(serv.getcPANELPassword());
				
				Label lbl28 =new Label(serv.getWHMUrl());
				Label lbl29 =new Label(serv.getWHMUsername());
				Label lbl30 =new Label(serv.getWHMPassword());
				
				Label lbl31 =new Label(serv.getPasswordRoot());

				// panel
				Pane root = new Pane();
				TabPane info = new TabPane();

				btn1.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						MainMenu menu = new MainMenu();
						menu.set_activeTab(2);
						try {
							menu.start(primaryStage);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
					}
				});

				// premier champ
				lbl.setLayoutX(50);
				lbl.setLayoutY(100);
				lbl16.setLayoutX(200);
				lbl16.setLayoutY(100);

				// deuxieme champ
				lbl1.setLayoutX(50);
				lbl1.setLayoutY(150);
				lbl17.setLayoutX(200);
				lbl17.setLayoutY(150);

				// troisieme champ
				lbl2.setLayoutX(50);
				lbl2.setLayoutY(200);
				lbl18.setLayoutX(200);
				lbl18.setLayoutY(200);

				// quatrieme champ
				lbl3.setLayoutX(50);
				lbl3.setLayoutY(250);
				lbl19.setLayoutX(200);
				lbl19.setLayoutY(250);

				// cinquieme champ
				lbl4.setLayoutX(50);
				lbl4.setLayoutY(300);
				lbl20.setLayoutX(200);
				lbl20.setLayoutY(300);
				
				lbl5.setLayoutX(50);
				lbl5.setLayoutY(350);
				lbl21.setLayoutX(200);
				lbl21.setLayoutY(350);
				
				//sixeme champs
				lbl6.setLayoutX(50);
				lbl6.setLayoutY(400);
				lbl22.setLayoutX(200);
				lbl22.setLayoutY(400);
				
				//septieme champ
				lbl7.setLayoutX(50);
				lbl7.setLayoutY(450);
				lbl23.setLayoutX(200);
				lbl23.setLayoutY(450);
				
				//huitieme champ
				lbl8.setLayoutX(450);
				lbl8.setLayoutY(100);
				lbl24.setLayoutX(600);
				lbl24.setLayoutY(100);
				
				//neuviemem champ
				lbl9.setLayoutX(450);
				lbl9.setLayoutY(150);
				lbl25.setLayoutX(600);
				lbl25.setLayoutY(150);
				
				//dixieme champ
				lbl10.setLayoutX(450);
				lbl10.setLayoutY(200);
				lbl26.setLayoutX(600);
				lbl26.setLayoutY(200);
				
				//onzieme champs
				lbl11.setLayoutX(450);
				lbl11.setLayoutY(250);
				lbl27.setLayoutX(600);
				lbl27.setLayoutY(250);
				
				//douzieme champ
				lbl12.setLayoutX(450);
				lbl12.setLayoutY(300);
				lbl28.setLayoutX(600);
				lbl28.setLayoutY(300);
				
				//treizieme champ
				lbl13.setLayoutX(450);
				lbl13.setLayoutY(350);
				lbl29.setLayoutX(600);
				lbl29.setLayoutY(350);
				
				//quatarzieme chmap
				lbl14.setLayoutX(450);
				lbl14.setLayoutY(400);
				lbl30.setLayoutX(600);
				lbl30.setLayoutY(400);
				
				//quinzieme champ
				lbl15.setLayoutX(450);
				lbl15.setLayoutY(450);
				lbl31.setLayoutX(600);
				lbl31.setLayoutY(450);

				// Bouton
				
				btn1.setLayoutX(300);
				btn1.setLayoutY(500);


				
				// add to panel
				root.getChildren().add(lbl);
				root.getChildren().add(lbl1);
				root.getChildren().add(lbl2);
				root.getChildren().add(lbl3);
				root.getChildren().add(lbl4);
				root.getChildren().add(lbl5);
				root.getChildren().add(lbl6);
				root.getChildren().add(lbl7);
				root.getChildren().add(lbl8);
				root.getChildren().add(lbl9);
				root.getChildren().add(lbl10);
				root.getChildren().add(lbl11);
				root.getChildren().add(lbl12);
				root.getChildren().add(lbl13);
				root.getChildren().add(lbl14);
				root.getChildren().add(lbl15);
				root.getChildren().add(lbl16);
				root.getChildren().add(lbl17);
				root.getChildren().add(lbl18);
				root.getChildren().add(lbl19);
				root.getChildren().add(lbl20);
				root.getChildren().add(lbl21);
				root.getChildren().add(lbl22);
				root.getChildren().add(lbl23);
				root.getChildren().add(lbl24);
				root.getChildren().add(lbl25);
				root.getChildren().add(lbl26);
				root.getChildren().add(lbl27);
				root.getChildren().add(lbl28);
				root.getChildren().add(lbl29);
				root.getChildren().add(lbl30);
				root.getChildren().add(lbl31);
				root.getChildren().add(btn1);

				// create window
				Scene scene = new Scene(root,750, 650);
				primaryStage.setTitle("Ajouter Serveur");
				primaryStage.setScene(scene);
				primaryStage.centerOnScreen();
				primaryStage.show();
		
	}
	

}
