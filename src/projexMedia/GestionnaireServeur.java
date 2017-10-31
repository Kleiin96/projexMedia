package projexMedia;

import java.io.IOException;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
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

	public ObservableList<Serveur> list = FXCollections.observableArrayList(new Serveur(1,"serveur1","lul",true));
	
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
	
	@SuppressWarnings({ "unchecked"})
	public void createPane(Stage primaryStage) throws Exception{
		

		TableView<Serveur> tableServeur = new TableView<Serveur>();
		TableColumn<Serveur, Integer> id = new TableColumn<Serveur, Integer>("id Serveur");
		TableColumn<Serveur, String> nom = new TableColumn<Serveur, String>("nom Serveur");
		TableColumn<Serveur, String> tel = new TableColumn<Serveur, String>("Telephone");
		TableColumn<Serveur, String> nomR = new TableColumn<Serveur, String>("nom Responsable");
		TableColumn<Serveur, String> adresse = new TableColumn<Serveur, String>("adresse");
		TableColumn<Serveur, String> courriel = new TableColumn<Serveur, String>("courriel");
		

		Button btnAjouter = new Button();
		Button btnModifier = new Button();
		Button btnArchiver = new Button();
		Button btnConsArch = new Button();
		Image imageRecherche = new Image(getClass().getResourceAsStream("loupe.png"));
		Button btnRecherche = new Button();
		ImageView image = new ImageView(imageRecherche);

		btnRecherche.setGraphic(image);
		
		TextField tfRecherche = new TextField();

		// construction de la table
		tableServeur.setEditable(false);
		tableServeur.setPrefSize(525, 525);

		id.setCellValueFactory(new PropertyValueFactory<Serveur, Integer>("idServeur"));
		nom.setCellValueFactory(new PropertyValueFactory<Serveur, String>("nomServeur"));
		//tel.setCellValueFactory(new PropertyValueFactory<Client, String>("telephone"));
		//nomR.setCellValueFactory(new PropertyValueFactory<Client, String>("nomResponsable"));
		//adresse.setCellValueFactory(new PropertyValueFactory<Client, String>("adresse"));
		//courriel.setCellValueFactory(new PropertyValueFactory<Client, String>("courriel"));
		tableServeur.getColumns().addAll(id, nom/*, tel, nomR, adresse, courriel*/);

		id.prefWidthProperty().bind(tableServeur.widthProperty().divide(2));
		nom.prefWidthProperty().bind(tableServeur.widthProperty().divide(2));
		tableServeur.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tableServeur.setItems(list);
		
		
		btnRecherche.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
			}
		});
		
		tfRecherche.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
			}
		});

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

				//if (tableServeur.getSelectionModel().getSelectedItem() != null) {
					

				//}
				
			}

		});
		btnArchiver.setText("Archiver");
		btnArchiver.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
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
					MainMenu menu = new MainMenu();
					menu.set_activeTab(1);
					try {
						menu.start(primaryStage);
					} catch (ClassNotFoundException | IOException | SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
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
		tfRecherche.setLayoutX(500);
		tfRecherche.setLayoutY(10);
		btnRecherche.setLayoutX(690);
		btnRecherche.setLayoutY(10);

		btnAjouter.setMinHeight(50);
		btnAjouter.setMinWidth(150);
		btnModifier.setMinHeight(50);
		btnModifier.setMinWidth(150);
		btnArchiver.setMinHeight(50);
		btnArchiver.setMinWidth(150);
		btnConsArch.setMinHeight(50);
		btnConsArch.setMinWidth(150);
		btnRecherche.setPadding(Insets.EMPTY);
		//TextFields.bindAutoCompletion(tfRecherche, possibleClient);

		// layout list
		tableServeur.setLayoutX(200);
		tableServeur.setLayoutY(60);

		_pane.getChildren().add(tableServeur);
		_pane.getChildren().add(btnAjouter);
		_pane.getChildren().add(btnModifier);
		_pane.getChildren().add(btnArchiver);
		_pane.getChildren().add(btnConsArch);
		_pane.getChildren().add(btnRecherche);
		_pane.getChildren().add(tfRecherche);
	}
	
	//ajouter un serveur dans la base de donn�e
	
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
						Client lul = new Client(tf.getText(), tf1.getText(), tf3.getText(), tf4.getText(), tf2.getText());
						try {
							lul.ajouterClient();
							MainMenu test = new MainMenu();
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
	
	public void ModifierServeur(Stage primaryStage) {
		// bouton
				Button btn = new Button("Modifier");
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
						Client lul = new Client(tf.getText(), tf1.getText(), tf3.getText(), tf4.getText(), tf2.getText());
						try {
							lul.ajouterClient();
							MainMenu test = new MainMenu();
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

	public void afficherArchive(Stage primaryStage) throws Exception {
		//affichageClientArchive();

		TableView<Serveur> tableServeur = new TableView<Serveur>();
		TableColumn<Serveur, Integer> id = new TableColumn<Serveur, Integer>("id Client");
		TableColumn<Serveur, String> nom = new TableColumn<Serveur, String>("nom Compagnie");
		TableColumn<Serveur, String> tel = new TableColumn<Serveur, String>("Telephone");
		TableColumn<Serveur, String> nomR = new TableColumn<Serveur, String>("nom Responsable");
		TableColumn<Serveur, String> adresse = new TableColumn<Serveur, String>("adresse");
		TableColumn<Serveur, String> courriel = new TableColumn<Serveur, String>("courriel");

		Button btnActiver = new Button();
		Button btnSupprimer = new Button();
		Button btnRetour = new Button();
		
		Pane root = new Pane();
		

		// construction de la table
		tableServeur.setEditable(false);
		tableServeur.setPrefSize(525, 525);

		id.setCellValueFactory(new PropertyValueFactory<Serveur, Integer>("idClient"));
		nom.setCellValueFactory(new PropertyValueFactory<Serveur, String>("nomCompagnie"));
		tel.setCellValueFactory(new PropertyValueFactory<Serveur, String>("telephone"));
		nomR.setCellValueFactory(new PropertyValueFactory<Serveur, String>("nomResponsable"));
		adresse.setCellValueFactory(new PropertyValueFactory<Serveur, String>("adresse"));
		courriel.setCellValueFactory(new PropertyValueFactory<Serveur, String>("courriel"));
		tableServeur.getColumns().addAll(id, nom, tel, nomR, adresse, courriel);

		// id.prefWidthProperty().bind(tableClient.widthProperty().divide(2));
		// nom.prefWidthProperty().bind(tableClient.widthProperty().divide(2));
		// tableClient.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tableServeur.setItems(list);

		btnActiver.setText("Activer");
		btnActiver.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					//tableServeur.getSelectionModel().getSelectedItem().activerClient();
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
						//tableServeur.getSelectionModel().getSelectedItem().supprimerClient();
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

}