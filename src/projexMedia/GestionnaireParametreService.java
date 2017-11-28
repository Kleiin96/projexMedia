package projexMedia;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GestionnaireParametreService {
	TypeService _test;
	Tab _parservTab;
	Pane _pane;

	public ObservableList<TypeService> list = FXCollections.observableArrayList();
	
	/**
	 * constructeur par défaut
	 */
	public GestionnaireParametreService() {
		_test = new TypeService();
		_parservTab = new Tab("Gestion Service");
		_pane = new Pane();
	}

	
	
	//******Getteur et setteur *******//

	public TypeService get_test() {
		return _test;
	}


	public void set_test(TypeService _test) {
		this._test = _test;
	}


	public Tab get_parservTab() {
		return _parservTab;
	}


	public void set_parservTab(Tab _parservTab) {
		this._parservTab = _parservTab;
	}


	public Pane get_pane() {
		return _pane;
	}


	public void set_pane(Pane _pane) {
		this._pane = _pane;
	}


	public ObservableList<TypeService> getList() {
		return list;
	}


	public void setList(ObservableList<TypeService> list) {
		this.list = list;
	}
	
	
	/**
	 * Le panneau principal de cette interface 
	 * @param primaryStage
	 * @throws SQLException
	 */
	public void createPane(Stage primaryStage) throws SQLException {
		afficherTypeService();
		
		TableView<TypeService> tableTypeService = new TableView<TypeService>();
		//TableColumn<TypeService,Integer> id = new TableColumn<TypeService, Integer>("id type de service");
		TableColumn<TypeService, String> nom = new TableColumn<TypeService, String>("nom type de service");
		TableColumn<TypeService, String> tel = new TableColumn<TypeService, String>("Description");
		
		

		Button btnAjouter = new Button();
		Button btnModifier = new Button();
		Button btnSupprimerT = new Button();
		Button btnAjouterParType = new Button();
		Button btnGestionPar = new Button();
		

		// construction de la table
		tableTypeService.setEditable(false);
		tableTypeService.setPrefSize(525, 525);

		//id.setCellValueFactory(new PropertyValueFactory<TypeService, Integer>("idTypeService"));
		nom.setCellValueFactory(new PropertyValueFactory<TypeService, String>("nomType"));
		tel.setCellValueFactory(new PropertyValueFactory<TypeService, String>("description"));
		
		tableTypeService.getColumns().addAll(nom, tel);

		//id.prefWidthProperty().bind(tableTypeService.widthProperty().divide(2));
		nom.prefWidthProperty().bind(tableTypeService.widthProperty().divide(2));
		tableTypeService.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tableTypeService.setItems(list);
		

		btnAjouter.setText("Ajouter un Type service");
		btnAjouter.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				ajouterType(primaryStage);
			}
		});
		btnModifier.setText("Modifier Type Service");
		btnModifier.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				if (tableTypeService.getSelectionModel().getSelectedItem() != null) {
					
					modifierType(primaryStage, tableTypeService.getSelectionModel().getSelectedItem());
				}
				//ajouterType(primaryStage);
			}

		});
		btnSupprimerT.setText("   Supprimer\ntype de service");
		btnSupprimerT.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				if (tableTypeService.getSelectionModel().getSelectedItem() != null) {
					try {
						tableTypeService.getSelectionModel().getSelectedItem().supprimerTypeService();
						MainMenu test = new MainMenu();
						test.set_activeTab(4);
						test.start(primaryStage);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				
			}
		});

		
		
		tableTypeService.setOnMousePressed(new EventHandler<MouseEvent>() {
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
						afficherParametre(primaryStage, tableTypeService.getSelectionModel().getSelectedItem());
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
						
					
				}
			}
		});
		
		btnGestionPar.setText("Gestion Parametre");
		btnGestionPar.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					gestionParametre(primaryStage);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});


		btnAjouter.setLayoutX(30);
		btnAjouter.setLayoutY(60);
		btnModifier.setLayoutX(30);
		btnModifier.setLayoutY(140);
		btnSupprimerT.setLayoutX(30);
		btnSupprimerT.setLayoutY(220);
		btnGestionPar.setLayoutX(30);
		btnGestionPar.setLayoutY(530);
		

		btnAjouter.setMinHeight(50);
		btnAjouter.setMinWidth(150);
		btnModifier.setMinHeight(50);
		btnModifier.setMinWidth(150);
		btnSupprimerT.setMinHeight(50);
		btnSupprimerT.setMinWidth(150);
		//btnAjouterParType.setMinHeight(50);
		//btnAjouterParType.setMinWidth(150);
		btnGestionPar.setMinHeight(50);
		btnGestionPar.setMinWidth(150);
		
		if(MainMenu._role.get_ajouter().equals("")) {
			btnAjouter.setDisable(true);
		}
		
		if(MainMenu._role.get_modifier().equals("")) {
			btnModifier.setDisable(true);
		}
		
		if(MainMenu._role.get_supprimer().equals("")) {
			btnSupprimerT.setDisable(true);
		}
		
		// layout list
		tableTypeService.setLayoutX(200);
		tableTypeService.setLayoutY(60);
		tableTypeService.setPrefWidth(MainLogin.bounds.getWidth() - 300);
		tableTypeService.setPrefHeight(MainLogin.bounds.getHeight() -200);

		_pane.getChildren().add(tableTypeService);
		_pane.getChildren().add(btnAjouter);
		_pane.getChildren().add(btnModifier);
		_pane.getChildren().add(btnSupprimerT);
		//_pane.getChildren().add(btnAjouterParType);
		_pane.getChildren().add(btnGestionPar);
		
		
	}
	
	/**
	 * Interface pour ajouter un parametre de service
	 * @param primaryStage
	 */
	public void ajouterParametre(Stage primaryStage) {
		// bouton
				Button btn = new Button("Ajouter");
				Button btn1 = new Button("Cancel");

				// textField
				TextField tf = new TextField();
				

				// label
				Label lbl = new Label("Nom du Parametre");
				

				// panel
				Pane root = new Pane();
				// BorderPane root1 = new BorderPane();
				btn.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						if (tf.getText().matches(".{1,256}")) {
							ParametreService lul = new ParametreService(1,tf.getText());
							
							try {
								lul.ajouterParametre();
								gestionParametre(primaryStage);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						else {
							Alert alert = new Alert(AlertType.ERROR);
							alert.setTitle("Message d'erreur");
							alert.setHeaderText("Le champs Nom du paramètre n'est pas valide. ( Ex: Url ) et/ou \nLa limite de charactère de 256 est dépassée.");
							Optional<ButtonType> result = alert.showAndWait();
							if (result.get() == ButtonType.OK) {
								
							}
						}
						
					}
				});

				btn1.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						
						try {
							gestionParametre(primaryStage);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
					}
				});
				
				setUpValidation(tf);

				// premier champ
				lbl.setLayoutX(50);
				lbl.setLayoutY(100);
				tf.setLayoutX(200);
				tf.setLayoutY(100);

				

				// Bouton
				btn.setLayoutX(50);
				btn.setLayoutY(350);
				btn1.setLayoutX(275);
				btn1.setLayoutY(350);

				// add to panel
				root.getChildren().add(lbl);
				
				root.getChildren().add(tf);
				
				root.getChildren().add(btn);
				root.getChildren().add(btn1);

				// create window
				Scene scene = new Scene(root, 450, 400);
				primaryStage.setTitle("Ajouter Paramètre de Service");
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				primaryStage.setScene(scene);
				primaryStage.show();
	}
	
	/**
	 * interface pour ajouter un type de Service
	 * @param primaryStage
	 */
	public void ajouterType(Stage primaryStage) {
		// bouton
		Button btn = new Button("Ajouter");
		Button btn1 = new Button("Cancel");

		// textField
		TextField tf = new TextField();
		TextField tf1 = new TextField();
		

		// label
		Label lbl = new Label("Nom du type de service");
		Label lbl1 = new Label("Description");
		

		// panel
		Pane root = new Pane();
		// BorderPane root1 = new BorderPane();
		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (tf.getText().matches(".{1,256}") && tf1.getText().matches(".{0,256}")) {
					TypeService lul = new TypeService();
					try {
						lul.ajouterTypeService(tf.getText(),tf1.getText());
						MainMenu test = new MainMenu();
						test.set_activeTab(4);
						test.start(primaryStage);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Message d'erreur");
					alert.setHeaderText("Le champs Nom du type de service n'est pas valide. ( Ex: DB ) et/ou \nLa limite de charactère de 256 est dépassée.");
					Optional<ButtonType> result = alert.showAndWait();
					if (result.get() == ButtonType.OK) {
						
					}
				}
				
			}
		});

		btn1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				MainMenu menu = new MainMenu();
				menu.set_activeTab(4);
				try {
					menu.start(primaryStage);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		
		setUpValidation(tf);

		// premier champ
		lbl.setLayoutX(50);
		lbl.setLayoutY(100);
		tf.setLayoutX(200);
		tf.setLayoutY(100);

		// premier champ
		lbl1.setLayoutX(50);
		lbl1.setLayoutY(150);
		tf1.setLayoutX(200);
		tf1.setLayoutY(150);
		

		// Bouton
		btn.setLayoutX(50);
		btn.setLayoutY(350);
		btn1.setLayoutX(275);
		btn1.setLayoutY(350);

		// add to panel
		root.getChildren().add(lbl);
		
		root.getChildren().add(tf);
		
		root.getChildren().add(lbl1);
		
		root.getChildren().add(tf1);
		
		root.getChildren().add(btn);
		root.getChildren().add(btn1);

		// create window
		Scene scene = new Scene(root, 450, 400);
		primaryStage.setTitle("Ajouter Paramètre de Service");
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	/**
	 * interface pour ajouter un parametre a un type de service
	 * @param primaryStage
	 * @param type
	 * @throws SQLException
	 */
	public void ajouterParametreType(Stage primaryStage, TypeService type) throws SQLException {
		
				ParametreService test = new ParametreService();
				// bouton
				Button btn = new Button("Ajouter");
				Button btn1 = new Button("Cancel");

				
				

				// label
				Label lbl = new Label("Nom du parametre du service");
				
				
				//combobox
				ComboBox<String> cbParametre = new ComboBox<String>(test.listNom());
				
				
				//System.out.println(cbParametre.getSele);

				// panel
				Pane root = new Pane();
				// BorderPane root1 = new BorderPane();
				
					System.out.println("la valeur est : "+cbParametre.getValue());
					btn.setOnAction(new EventHandler<ActionEvent>() {
	
						@Override
						public void handle(ActionEvent event) {
							if (!cbParametre.getSelectionModel().isEmpty()) {
								try {
									Connection conn = SimpleDataSource.getConnection();
									Statement stat = conn.createStatement();
									if(cbParametre.getValue() != null) {	
										ResultSet result = stat
												.executeQuery("SELECT id_parametreService FROM parametreservice WHERE nom_parametre = '" + cbParametre.getValue() + "'");
										result.next();
										int idPara = result.getInt("id_parametreService");
										
										type.ajouterparametreService(idPara);
										afficherParametre(primaryStage, type);
									}
								} catch (Exception e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							else {
								Alert alert = new Alert(AlertType.ERROR);
								alert.setTitle("Message d'erreur");
								alert.setHeaderText("Veuillez choisir un paramètre.");
								Optional<ButtonType> result = alert.showAndWait();
								if (result.get() == ButtonType.OK) {
									
								}
							}
							
						}
					});

				btn1.setOnAction(new EventHandler<ActionEvent>() {//

					@Override
					public void handle(ActionEvent event) {
						
						try {
							afficherParametre(primaryStage, type);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
					}
				});

				// premier champ
				lbl.setLayoutX(50);
				lbl.setLayoutY(100);
				cbParametre.setLayoutX(200);
				cbParametre.setLayoutY(100);
				

				// Bouton
				btn.setLayoutX(50);
				btn.setLayoutY(350);
				btn1.setLayoutX(275);
				btn1.setLayoutY(350);

				// add to panel
				root.getChildren().add(lbl);
				
				root.getChildren().add(cbParametre);
				
				root.getChildren().add(btn);
				root.getChildren().add(btn1);

				// create window
				Scene scene = new Scene(root, 450, 400);
				primaryStage.setTitle("Ajouter un parametre a un type service");
				primaryStage.setScene(scene);
				primaryStage.show();
	}
	
	
	/**
	 * Interface pour modifier un parametre de service
	 * @param primaryStage
	 */
	public void modifierParametre(Stage primaryStage, ParametreService par) {
		// bouton
				Button btn = new Button("Modifier");
				Button btn1 = new Button("Cancel");

				// textField
				TextField tf = new TextField(par.getNomParametre());
				

				// label
				Label lbl = new Label("Nom du Parametre");
				

				// panel
				Pane root = new Pane();
				// BorderPane root1 = new BorderPane();
				btn.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						if (tf.getText().matches(".{1,256}")) {
							par.setNomParametre(tf.getText());
							
							try {
								par.modifierParametre();
								gestionParametre(primaryStage);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						else {
							Alert alert = new Alert(AlertType.ERROR);
							alert.setTitle("Message d'erreur");
							alert.setHeaderText("Le champs Nom du paramètre n'est pas valide. ( Ex: Url ) et/ou \\nLa limite de charactère de 256 est dépassée.");
							Optional<ButtonType> result = alert.showAndWait();
							if (result.get() == ButtonType.OK) {
								
							}
						}
						
					}
				});

				btn1.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						
						try {
							gestionParametre(primaryStage);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
					}
				});

				setUpValidation(tf);
				
				// premier champ
				lbl.setLayoutX(50);
				lbl.setLayoutY(100);
				tf.setLayoutX(200);
				tf.setLayoutY(100);

				

				// Bouton
				btn.setLayoutX(50);
				btn.setLayoutY(350);
				btn1.setLayoutX(275);
				btn1.setLayoutY(350);

				// add to panel
				root.getChildren().add(lbl);
				
				root.getChildren().add(tf);
				
				root.getChildren().add(btn);
				root.getChildren().add(btn1);

				// create window
				Scene scene = new Scene(root, 450, 400);
				primaryStage.setTitle("Modifier Paramètre de Service");
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				primaryStage.setScene(scene);
				primaryStage.show();
	}
	
	/**
	 * interface pour modifier le type de service
	 * @param primaryStage
	 * @param type
	 */
	public void modifierType(Stage primaryStage, TypeService type) {
		// bouton
		Button btn = new Button("Modifier");
		Button btn1 = new Button("Cancel");

		// textField
		TextField tf = new TextField(type.getNomType());
		TextField tf1 = new TextField(type.getDescription());
		

		// label
		Label lbl = new Label("Nom du type de service");
		Label lbl1 = new Label("Description");
		

		// panel
		Pane root = new Pane();
		// BorderPane root1 = new BorderPane();
		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (tf.getText().matches(".{1,256}") && tf1.getText().matches(".{0,256}")) {
					try {
						type.setDescription(tf1.getText());
						type.setNomType(tf.getText());
						type.modifierTypeService();
						MainMenu test = new MainMenu();
						test.set_activeTab(4);
						test.start(primaryStage);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Message d'erreur");
					alert.setHeaderText("Le champs Nom du type de service n'est pas valide. ( Ex: DB ) et/ou \nLa limite de charactère de 256 est dépassée.");
					Optional<ButtonType> result = alert.showAndWait();
					if (result.get() == ButtonType.OK) {
						
					}
				}
			}
		});

		btn1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				MainMenu menu = new MainMenu();
				menu.set_activeTab(4);
				try {
					menu.start(primaryStage);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		
		setUpValidation(tf);

		// premier champ
		lbl.setLayoutX(50);
		lbl.setLayoutY(100);
		tf.setLayoutX(200);
		tf.setLayoutY(100);

		// premier champ
		lbl1.setLayoutX(50);
		lbl1.setLayoutY(150);
		tf1.setLayoutX(200);
		tf1.setLayoutY(150);
		

		// Bouton
		btn.setLayoutX(50);
		btn.setLayoutY(350);
		btn1.setLayoutX(275);
		btn1.setLayoutY(350);

		// add to panel
		root.getChildren().add(lbl);
		
		root.getChildren().add(tf);
		
		root.getChildren().add(lbl1);
		
		root.getChildren().add(tf1);
		
		root.getChildren().add(btn);
		root.getChildren().add(btn1);

		// create window
		Scene scene = new Scene(root, 450, 400);
		primaryStage.setTitle("Modifer le type de Service");
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	/**
	 * interface pour la gestion des parametres
	 * @param primaryStage
	 * @throws SQLException 
	 */
	public void gestionParametre(Stage primaryStage) throws SQLException {
		ParametreService list = new ParametreService();
		
		Pane root = new Pane();
		
		TableView<ParametreService> tableParametre = new TableView<ParametreService>();
		//TableColumn<ParametreService,Integer> id = new TableColumn<ParametreService, Integer>("id parametre de service");
		TableColumn<ParametreService, String> nom = new TableColumn<ParametreService, String>("nom parametre de service");
		

		Button btnAjouter = new Button();
		Button btnModifier = new Button();
		Button btnSupprimerT = new Button();
		Button btn1 = new Button();
		
		Label lbltitle = new Label("Gestion des paramètres");

		// construction de la table
		tableParametre.setEditable(false);
		tableParametre.setPrefSize(525, 525);
		

		//id.setCellValueFactory(new PropertyValueFactory<ParametreService, Integer>("idParametre"));
		nom.setCellValueFactory(new PropertyValueFactory<ParametreService, String>("nomParametre"));
		
		tableParametre.getColumns().addAll(nom);

		//id.prefWidthProperty().bind(tableParametre.widthProperty().divide(2));
		nom.prefWidthProperty().bind(tableParametre.widthProperty().divide(2));
		tableParametre.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tableParametre.setItems(list.listParametre());
		
		btnAjouter.setText("Ajouter un Parametre");
		btnAjouter.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				ajouterParametre(primaryStage);
			}
		});
		
		btnModifier.setText("Modifier Parametre");
		btnModifier.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				if (tableParametre.getSelectionModel().getSelectedItem() != null) {
					
					modifierParametre(primaryStage, tableParametre.getSelectionModel().getSelectedItem());
				}
				
			}

		});
		
		btnSupprimerT.setText("Supprimer Parametre");
		btnSupprimerT.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				if (tableParametre.getSelectionModel().getSelectedItem() != null) {
					try {
						tableParametre.getSelectionModel().getSelectedItem().supprimerParametre();
						gestionParametre(primaryStage);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				
			}
		});
		
		btn1.setText("Retour");
		btn1.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				MainMenu menu = new MainMenu();
				menu.set_activeTab(4);
				try {
					menu.start(primaryStage);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		
		lbltitle.setLayoutX(100);
		lbltitle.setLayoutY(50);
		lbltitle.setScaleX(2);
		lbltitle.setScaleY(2);
		
		btnAjouter.setLayoutX(30);
		btnAjouter.setLayoutY(105);
		btnModifier.setLayoutX(30);
		btnModifier.setLayoutY(185);
		btnSupprimerT.setLayoutX(30);
		btnSupprimerT.setLayoutY(265);
		btn1.setLayoutX(30);
		btn1.setLayoutY(575);
		
		btnAjouter.setMinHeight(50);
		btnAjouter.setMinWidth(150);
		btnModifier.setMinHeight(50);
		btnModifier.setMinWidth(150);
		btnSupprimerT.setMinHeight(50);
		btnSupprimerT.setMinWidth(150);
		btn1.setMinHeight(50);
		btn1.setMinWidth(150);
		
		if(MainMenu._role.get_ajouter().equals("")) {
			btnAjouter.setDisable(true);
		}
		
		if(MainMenu._role.get_modifier().equals("")) {
			btnModifier.setDisable(true);
		}
		
		if(MainMenu._role.get_supprimer().equals("")) {
			btnSupprimerT.setDisable(true);
		}
		
		tableParametre.setLayoutX(200);
		tableParametre.setLayoutY(105);
		tableParametre.setPrefWidth(MainLogin.bounds.getWidth() - 300);
		tableParametre.setPrefHeight(MainLogin.bounds.getHeight() -200);

		root.getChildren().add(tableParametre);
		root.getChildren().add(btn1);
		root.getChildren().add(btnAjouter);
		root.getChildren().add(btnModifier);
		root.getChildren().add(btnSupprimerT);
		root.getChildren().add(lbltitle);
		
		
		Scene scene = new Scene(root, 750, 650);
		primaryStage.setTitle("Gestionnaire des parametres");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	/**
	 * Création de l'observableList pour la tableView
	 * @throws SQLException
	 */
	public void afficherTypeService() throws SQLException {
		list.clear();
		Connection conn = SimpleDataSource.getConnection();
		ArrayList<ParametreService> list1 = new ArrayList<ParametreService>();

		try {
			Statement stat = conn.createStatement();
			//Statement stat1 = conn.createStatement();

			ResultSet result = stat.executeQuery("SELECT * From typeservice");
			//ResultSet result1 = stat.executeQuery("SELECT * From typeservice");

			while (result.next()) {
				list1.clear();
				
				
				list.add(new TypeService(Integer.parseInt(result.getString("id_typeService")),result.getString("nom_type"),
						result.getString("description"),createListParametre(Integer.parseInt(result.getString("id_typeService")))));

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {
			conn.close();
		}
	}
	
	/**
	 * Crée un ArrayList de parametre
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public  ArrayList<ParametreService> createListParametre(int id) throws SQLException {
		ArrayList<ParametreService> list1 = new ArrayList<ParametreService>();
		Connection conn = SimpleDataSource.getConnection();
		Statement stat1 = conn.createStatement();
		
		ResultSet result1 = stat1.executeQuery("SELECT p.id_parametreService, p.nom_parametre From typeservice t JOIN ta_service s ON t.id_typeService = s.fk_id_typeService "
				+ "JOIN parametreservice p ON s.fk_id_parametreService = p.id_parametreService WHERE  s.fk_id_typeService=" + id );
		while(result1.next()) {
			
			
			list1.add(new ParametreService(Integer.parseInt(result1.getString("p.id_parametreService")),result1.getString("p.nom_parametre")));
			
			
		}
		conn.close();
		return list1;
	}
	
	/**
	 * array des id de ta_service pour un type de service
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public  ArrayList<Integer> createidListParametre(int id) throws SQLException {
		ArrayList<Integer> list1 = new ArrayList<Integer>();
		Connection conn = SimpleDataSource.getConnection();
		Statement stat1 = conn.createStatement();
		
		ResultSet result1 = stat1.executeQuery("SELECT s.id_service From typeservice t JOIN ta_service s ON t.id_typeService = s.fk_id_typeService "
				+ "JOIN parametreservice p ON s.fk_id_parametreService = p.id_parametreService WHERE  s.fk_id_typeService=" + id );
		while(result1.next()) {
			
			
			list1.add(Integer.parseInt(result1.getString("s.id_service")));
			
			
		}
		conn.close();
		return list1;
	}
	
	
	/**
	 * afficher tous les parametre pour type de services
	 * @param primaryStage
	 * @param type
	 * @throws SQLException 
	 */
	public void afficherParametre(Stage primaryStage, TypeService type) throws SQLException {
				// bouton
				
				Button btn1 = new Button("Retour");
				Button btnAjouterParType = new Button();
				
				//list
				ObservableList<ParametreService> test = FXCollections.observableArrayList();
				
				Label lbltitle = new Label("Les paramètres du type");
				// panel
					
				Pane root = new Pane();
				
				//*******p-t erreur********//
				type.setListParametre(createListParametre(type.getIdTypeService()));
				
				for (int i =0; i < type.getListParametre().size();i++) {
				
					test.add(type.getListParametre().get(i));
				}
				
				TableView<ParametreService> tableParametre = new TableView<ParametreService>();
				//TableColumn<ParametreService,Integer> id = new TableColumn<ParametreService, Integer>("id parametre de service");
				TableColumn<ParametreService, String> nom = new TableColumn<ParametreService, String>("nom parametre de service");
				
				// construction de la table
				tableParametre.setEditable(false);
				tableParametre.setPrefSize(525, 525);

				//id.setCellValueFactory(new PropertyValueFactory<ParametreService, Integer>("idParametre"));
				nom.setCellValueFactory(new PropertyValueFactory<ParametreService, String>("nomParametre"));
				
				tableParametre.getColumns().addAll(nom);

				//id.prefWidthProperty().bind(tableParametre.widthProperty().divide(2));
				nom.prefWidthProperty().bind(tableParametre.widthProperty().divide(2));
				tableParametre.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
				tableParametre.setItems(test);
				
				btnAjouterParType.setText("Ajouter un parametre"+"\n"+ "a un type");
				btnAjouterParType.setContentDisplay(ContentDisplay.CENTER);;
				btnAjouterParType.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						
							try {
								ajouterParametreType(primaryStage,type);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							
						}
					}
				});
				
				Button btnSupprimer = new Button("Supprimer");
				btnSupprimer.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						if (tableParametre.getSelectionModel().getSelectedItem() != null) {
							
								
							
							try {
								ArrayList<Integer> test = createidListParametre(type.getIdTypeService());
								int id = tableParametre.getSelectionModel().getSelectedIndex();
								
								
								
								type.supprimerparametreService(test.get(id));
								afficherParametre(primaryStage, type);
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}
				});
					
				btnAjouterParType.setLayoutX(30);
				btnAjouterParType.setLayoutY(105);
				btnAjouterParType.setMinHeight(50);
				btnAjouterParType.setMinWidth(150);
				
				btnSupprimer.setLayoutX(30);
				btnSupprimer.setLayoutY(185);
				btnSupprimer.setMinHeight(50);
				btnSupprimer.setMinWidth(150);
				
				if(MainMenu._role.get_ajouter().equals("")) {
					btnAjouterParType.setDisable(true);
				}
				
				if(MainMenu._role.get_supprimer().equals("")) {
					btnSupprimer.setDisable(true);
				}
			
				// add to panel
				//root.getChildren().add(lbl);
				
				
				root.getChildren().add(btnSupprimer);
				root.getChildren().add(btnAjouterParType);
				

				// Bouton
				btn1.setLayoutX(30);
				btn1.setLayoutY(575);
				btn1.setMinHeight(50);
				btn1.setMinWidth(150);
				
				
				btn1.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						MainMenu menu = new MainMenu();
						menu.set_activeTab(4);
						try {
							menu.start(primaryStage);
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						
					}
				});
				
				lbltitle.setLayoutX(100);
				lbltitle.setLayoutY(50);
				lbltitle.setScaleX(2);
				lbltitle.setScaleY(2);
				
				tableParametre.setLayoutX(200);
				tableParametre.setLayoutY(105);
				tableParametre.setPrefWidth(MainLogin.bounds.getWidth() - 300);
				tableParametre.setPrefHeight(MainLogin.bounds.getHeight() -200);

				root.getChildren().add(tableParametre);
				
				root.getChildren().add(btn1);
				root.getChildren().add(lbltitle);

				// create window
				Scene scene = new Scene(root, 750, 650);
				primaryStage.setTitle("Paramètre d'un type Service");
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
        if (!tf.getText().matches(".{1,512}")) {
            if (! styleClass.contains("error")) {
                styleClass.add("error");
            }
        } else {
            // remove all occurrences:
            styleClass.removeAll(Collections.singleton("error"));                    
        }
    }
}
