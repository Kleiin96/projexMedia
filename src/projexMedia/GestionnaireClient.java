/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projexMedia;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

import org.controlsfx.control.textfield.TextFields;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


/**
 *
 * @author bruneaje allo
 */
public class GestionnaireClient {
	Client _test;
	Tab _clientTab;
	Pane _pane;
	
	public static boolean doubleClick;
	public static String id_Client;

	public ObservableList<Client> list = FXCollections.observableArrayList();

	public GestionnaireClient() {
		_test = new Client();
		_clientTab = new Tab("Client");
		_pane = new Pane();
	}

	public GestionnaireClient(Client _test, Tab _clientTab, Pane _pane) {
		this._test = _test;
		this._clientTab = _clientTab;
		this._pane = _pane;
	}

	public Client getTest() {
		return _test;
	}

	public Tab getClientTab() {
		return _clientTab;
	}

	public Pane getClientPane() {
		return _pane;
	}

	public void setTest(Client _test) {
		this._test = _test;
	}

	public void setClientTab(Tab _clientTab) {
		this._clientTab = _clientTab;
	}

	@SuppressWarnings({ "unchecked"})
	public void createPane(Stage primaryStage) throws Exception {

		affichageClient();

		TableView<Client> tableClient = new TableView<Client>();
		//TableColumn<Client, Integer> id = new TableColumn<Client, Integer>("id Client");
		TableColumn<Client, String> nom = new TableColumn<Client, String>("nom Compagnie");
		TableColumn<Client, String> tel = new TableColumn<Client, String>("Telephone");
		TableColumn<Client, String> nomR = new TableColumn<Client, String>("nom Responsable");
		TableColumn<Client, String> adresse = new TableColumn<Client, String>("adresse");
		TableColumn<Client, String> courriel = new TableColumn<Client, String>("courriel");
		

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
		tableClient.setEditable(false);
		tableClient.setPrefSize(525, 525);

		//id.setCellValueFactory(new PropertyValueFactory<Client, Integer>("idClient"));
		nom.setCellValueFactory(new PropertyValueFactory<Client, String>("nomCompagnie"));
		tel.setCellValueFactory(new PropertyValueFactory<Client, String>("telephone"));
		nomR.setCellValueFactory(new PropertyValueFactory<Client, String>("nomResponsable"));
		adresse.setCellValueFactory(new PropertyValueFactory<Client, String>("adresse"));
		courriel.setCellValueFactory(new PropertyValueFactory<Client, String>("courriel"));
		tableClient.getColumns().addAll(nom, tel, nomR, adresse, courriel);

		// id.prefWidthProperty().bind(tableClient.widthProperty().divide(2));
		// nom.prefWidthProperty().bind(tableClient.widthProperty().divide(2));
		// tableClient.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tableClient.setItems(list);
		
		
		btnRecherche.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					RechercherClient(tfRecherche);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		tfRecherche.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					RechercherClient(tfRecherche);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		btnAjouter.setText("Ajouter");
		btnAjouter.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				ajouterClient(primaryStage);
			}
		});
		btnModifier.setText("Modifier");
		btnModifier.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				if (tableClient.getSelectionModel().getSelectedItem() != null) {
					try {
						modifierClient(primaryStage, tableClient.getSelectionModel().getSelectedItem());
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
				// modifierClient(primaryStage);
			}

		});
		btnArchiver.setText("Archiver");
		btnArchiver.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					tableClient.getSelectionModel().getSelectedItem().archiverClient();
					MainMenu test = new MainMenu();
					test.start(primaryStage);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				;
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
		
		tableClient.setOnMousePressed(new EventHandler<MouseEvent>() {
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
						doubleClick = true;
						id_Client = tableClient.getSelectionModel().getSelectedItem().getNomCompagnie();
						menu.start(primaryStage);
						doubleClick = false;
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
		tfRecherche.setLayoutX(MainLogin.bounds.getWidth()-400);
		tfRecherche.setLayoutY(10);
		btnRecherche.setLayoutX(MainLogin.bounds.getWidth()-125);
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
		tfRecherche.setMinWidth(250);
		
		if(MainMenu._role.get_ajouter().equals("")) {
			btnAjouter.setDisable(true);
		}
		
		if(MainMenu._role.get_modifier().equals("")) {
			btnModifier.setDisable(true);
		}
		
		if(MainMenu._role.get_archiver().equals("")) {
			btnArchiver.setDisable(true);
		}

		// layout list
		tableClient.setLayoutX(200);
		tableClient.setLayoutY(60);
		tableClient.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tableClient.setPrefWidth(MainLogin.bounds.getWidth() - 300);
		tableClient.setPrefHeight(MainLogin.bounds.getHeight() -200);
		
		_pane.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		tableClient.getStyleClass().add("font");

		_pane.getChildren().add(tableClient);
		_pane.getChildren().add(btnAjouter);
		_pane.getChildren().add(btnModifier);
		_pane.getChildren().add(btnArchiver);
		_pane.getChildren().add(btnConsArch);
		_pane.getChildren().add(btnRecherche);
		_pane.getChildren().add(tfRecherche);
	}

	// Formulaire ajouter client

	public void ajouterClient(Stage primaryStage) {
		// bouton
		Button btn = new Button("Ajouter");
		Button btn1 = new Button("Cancel");

		// textField
		TextField tf = new TextField();
		TextField tf1 = new TextField();
		TextField tf2 = new TextField();
		TextField tf3 = new TextField();
		TextField tf4 = new TextField();

		// label
		Label lbl = new Label("Nom de la compagnie :");
		Label lbl1 = new Label("Téléphone :");
		Label lbl2 = new Label("Adresse :");
		Label lbl3 = new Label("Personne Responsable :");
		Label lbl4 = new Label("Courriel :");

		// panel
		Pane root = new Pane();
		// BorderPane root1 = new BorderPane();
		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Client lul = new Client(tf.getText(), tf1.getText(), tf3.getText(), tf4.getText(), tf2.getText());
				if (tf.getText().matches(".{1,128}") && tf1.getText().matches(".{0,32}") && tf2.getText().matches(".{0,128}") && tf3.getText().matches(".{0,128}") && tf4.getText().matches(".{0,128}")) {
					try {
						lul.ajouterClient();
						MainMenu test = new MainMenu();
						test.start(primaryStage);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Message d'erreur");
					alert.setHeaderText("Le champs nom de la compagnie n'est pas valide. ( Ex: Google ) et/ou \nLa limite de charactère de 128 est dépassée.");
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
				
				try {
					menu.start(primaryStage);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		
		Label lbltitle = new Label("Ajouter un client");
		lbltitle.setLayoutX((MainLogin.bounds.getWidth()/2)-240);
		lbltitle.setLayoutY((MainLogin.bounds.getHeight()/2)-225);
		lbltitle.setScaleX(2);
		lbltitle.setScaleY(2);
		
		setUpValidation(tf);

		// premier champ
		lbl.setLayoutX((MainLogin.bounds.getWidth()/2)-300);
		lbl.setLayoutY((MainLogin.bounds.getHeight()/2)-150);
		tf.setLayoutX((MainLogin.bounds.getWidth()/2)-50);
		tf.setLayoutY((MainLogin.bounds.getHeight()/2)-150);
		tf.setMinWidth(300);

		// deuxieme champ
		lbl1.setLayoutX((MainLogin.bounds.getWidth()/2)-300);
		lbl1.setLayoutY((MainLogin.bounds.getHeight()/2)-100);
		tf1.setLayoutX((MainLogin.bounds.getWidth()/2)-50);
		tf1.setLayoutY((MainLogin.bounds.getHeight()/2)-100);
		tf1.setMinWidth(300);

		// troisieme champ
		lbl2.setLayoutX((MainLogin.bounds.getWidth()/2)-300);
		lbl2.setLayoutY((MainLogin.bounds.getHeight()/2)-50);
		tf2.setLayoutX((MainLogin.bounds.getWidth()/2)-50);
		tf2.setLayoutY((MainLogin.bounds.getHeight()/2)-50);
		tf2.setMinWidth(300);

		// quatrieme champ
		lbl3.setLayoutX((MainLogin.bounds.getWidth()/2)-300);
		lbl3.setLayoutY((MainLogin.bounds.getHeight()/2));
		tf3.setLayoutX((MainLogin.bounds.getWidth()/2)-50);
		tf3.setLayoutY((MainLogin.bounds.getHeight()/2));
		tf3.setMinWidth(300);

		// cinquieme champ
		lbl4.setLayoutX((MainLogin.bounds.getWidth()/2)-300);
		lbl4.setLayoutY((MainLogin.bounds.getHeight()/2)+50);
		tf4.setLayoutX((MainLogin.bounds.getWidth()/2)-50);
		tf4.setLayoutY((MainLogin.bounds.getHeight()/2)+50);
		tf4.setMinWidth(300);

		// Bouton
		btn.setLayoutX((MainLogin.bounds.getWidth()/2)-300);
		btn.setLayoutY((MainLogin.bounds.getHeight()/2)+150);
		btn1.setLayoutX((MainLogin.bounds.getWidth()/2)+100);
		btn1.setLayoutY((MainLogin.bounds.getHeight()/2)+150);
		btn.setMinHeight(50);
		btn.setMinWidth(150);
		btn1.setMinHeight(50);
		btn1.setMinWidth(150);
		

		// add to panel
		root.getChildren().add(lbl);
		root.getChildren().add(lbl1);
		root.getChildren().add(lbl2);
		root.getChildren().add(lbl3);
		root.getChildren().add(lbl4);
		root.getChildren().add(tf);
		root.getChildren().add(tf1);
		root.getChildren().add(tf2);
		root.getChildren().add(tf3);
		root.getChildren().add(tf4);
		root.getChildren().add(btn);
		root.getChildren().add(btn1);
		root.getChildren().add(lbltitle);

		// create window
		Scene scene = new Scene(root, 450, 400);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		root.getStyleClass().add("fontFormulaire");
		primaryStage.setTitle("Ajouter Client");
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	// formulaire modifier client

	public void modifierClient(Stage primaryStage, Client client) throws SQLException {
		// bouton
		Button btn = new Button("Modifier");
		Button btn1 = new Button("Cancel");

		// textField
		TextField tf = new TextField(client.getNomCompagnie());
		TextField tf1 = new TextField(client.getTelephone());
		TextField tf2 = new TextField(client.getAdresse());
		TextField tf3 = new TextField(client.getNomResponsable());
		TextField tf4 = new TextField(client.getCourriel());

		// label
		Label lbl = new Label("Nom de la compagnie :");
		Label lbl1 = new Label("Téléphone :");
		Label lbl2 = new Label("Adresse :");
		Label lbl3 = new Label("Personne Responsable :");
		Label lbl4 = new Label("Courriel :");

		// panel
		Pane root = new Pane();
		// BorderPane root1 = new BorderPane();

		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Client lul = new Client(client.getIdClient(), tf.getText(), tf1.getText(), tf3.getText(), tf2.getText(),
						tf4.getText());
				if(tf1.getText().equals(null) || tf2.getText().equals(null) || tf3.getText().equals(null) || tf4.getText().equals(null)) {
					if(tf.getText().matches(".{1,128}")) {
						try {
							lul.modifierNomClient();
							lul.modifierAdresseClient();
							lul.modifierCourrielClient();
							lul.modifierNomResponsableClient();
							lul.modifierTelephoneClient();
		
							
							MainMenu test = new MainMenu();
							test.start(primaryStage);
		
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
				else if (tf.getText().matches(".{1,128}") && tf1.getText().matches(".{0,32}") && tf2.getText().matches(".{0,128}") && tf3.getText().matches(".{0,128}") && tf4.getText().matches(".{0,128}")) {
					try {
						lul.modifierNomClient();
						lul.modifierAdresseClient();
						lul.modifierCourrielClient();
						lul.modifierNomResponsableClient();
						lul.modifierTelephoneClient();
	
						
						MainMenu test = new MainMenu();
						test.start(primaryStage);
	
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Message d'erreur");
					alert.setHeaderText("Le champs nom de la compagnie n'est pas valide. ( Ex: Google ) et/ou \nLa limite de charactère de 128 est dépassée.");
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
				
				try {
					menu.start(primaryStage);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		Label lbltitle = new Label("Modifier un client");
		lbltitle.setLayoutX((MainLogin.bounds.getWidth()/2)-240);
		lbltitle.setLayoutY((MainLogin.bounds.getHeight()/2)-225);
		lbltitle.setScaleX(2);
		lbltitle.setScaleY(2);
		
		setUpValidation(tf);

		// premier champ
		lbl.setLayoutX((MainLogin.bounds.getWidth()/2)-300);
		lbl.setLayoutY((MainLogin.bounds.getHeight()/2)-150);
		tf.setLayoutX((MainLogin.bounds.getWidth()/2)-50);
		tf.setLayoutY((MainLogin.bounds.getHeight()/2)-150);
		tf.setMinWidth(300);

		// deuxieme champ
		lbl1.setLayoutX((MainLogin.bounds.getWidth()/2)-300);
		lbl1.setLayoutY((MainLogin.bounds.getHeight()/2)-100);
		tf1.setLayoutX((MainLogin.bounds.getWidth()/2)-50);
		tf1.setLayoutY((MainLogin.bounds.getHeight()/2)-100);
		tf1.setMinWidth(300);

		// troisieme champ
		lbl2.setLayoutX((MainLogin.bounds.getWidth()/2)-300);
		lbl2.setLayoutY((MainLogin.bounds.getHeight()/2)-50);
		tf2.setLayoutX((MainLogin.bounds.getWidth()/2)-50);
		tf2.setLayoutY((MainLogin.bounds.getHeight()/2)-50);
		tf2.setMinWidth(300);

		// quatrieme champ
		lbl3.setLayoutX((MainLogin.bounds.getWidth()/2)-300);
		lbl3.setLayoutY((MainLogin.bounds.getHeight()/2));
		tf3.setLayoutX((MainLogin.bounds.getWidth()/2)-50);
		tf3.setLayoutY((MainLogin.bounds.getHeight()/2));
		tf3.setMinWidth(300);

		// cinquieme champ
		lbl4.setLayoutX((MainLogin.bounds.getWidth()/2)-300);
		lbl4.setLayoutY((MainLogin.bounds.getHeight()/2)+50);
		tf4.setLayoutX((MainLogin.bounds.getWidth()/2)-50);
		tf4.setLayoutY((MainLogin.bounds.getHeight()/2)+50);
		tf4.setMinWidth(300);

		// Bouton
		btn.setLayoutX((MainLogin.bounds.getWidth()/2)-300);
		btn.setLayoutY((MainLogin.bounds.getHeight()/2)+150);
		btn1.setLayoutX((MainLogin.bounds.getWidth()/2)+100);
		btn1.setLayoutY((MainLogin.bounds.getHeight()/2)+150);
		btn.setMinHeight(50);
		btn.setMinWidth(150);
		btn1.setMinHeight(50);
		btn1.setMinWidth(150);

		// add to panel
		root.getChildren().add(lbl);
		root.getChildren().add(lbl1);
		root.getChildren().add(lbl2);
		root.getChildren().add(lbl3);
		root.getChildren().add(lbl4);
		root.getChildren().add(tf);
		root.getChildren().add(tf1);
		root.getChildren().add(tf2);
		root.getChildren().add(tf3);
		root.getChildren().add(tf4);
		root.getChildren().add(btn);
		root.getChildren().add(btn1);
		root.getChildren().add(lbltitle);

		// create window
		Scene scene = new Scene(root, 450, 400);
		primaryStage.setTitle("Modifier Client");
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		root.getStyleClass().add("fontFormulaire");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public ObservableList<Client> getList() {
		return list;
	}

	public void setList(ObservableList<Client> list) {
		this.list = list;
	}
	
	public void setDoubleClick() {
		doubleClick = false;
	}

	public void affichageClient() throws Exception {
		list.clear();
		Connection conn = SimpleDataSource.getConnection();

		try {
			Statement stat = conn.createStatement();

			ResultSet result = stat.executeQuery("SELECT * From client where actif=1");

			while (result.next()) {

				list.add(new Client(Integer.parseInt(result.getString("id_client")), result.getString("nom_compagnie"),
						result.getString("telephone"), result.getString("personne_responsable"),
						result.getString("adresse"), result.getString("courriel")));

			}
		}

		finally {
			conn.close();
		}

	}

	public void affichageClientArchive() throws Exception {
		list.clear();
		Connection conn = SimpleDataSource.getConnection();

		try {
			Statement stat = conn.createStatement();

			ResultSet result = stat.executeQuery("SELECT * From client where actif=0");

			while (result.next()) {

				list.add(new Client(Integer.parseInt(result.getString("id_client")), result.getString("nom_compagnie"),
						result.getString("telephone"), result.getString("personne_responsable"),
						result.getString("adresse"), result.getString("courriel")));

			}
		}

		finally {
			conn.close();
		}

	}

	public void afficherArchive(Stage primaryStage) throws Exception {
		affichageClientArchive();

		TableView<Client> tableClient = new TableView<Client>();
		//TableColumn<Client, Integer> id = new TableColumn<Client, Integer>("id Client");
		TableColumn<Client, String> nom = new TableColumn<Client, String>("nom Compagnie");
		TableColumn<Client, String> tel = new TableColumn<Client, String>("Telephone");
		TableColumn<Client, String> nomR = new TableColumn<Client, String>("nom Responsable");
		TableColumn<Client, String> adresse = new TableColumn<Client, String>("adresse");
		TableColumn<Client, String> courriel = new TableColumn<Client, String>("courriel");

		Button btnActiver = new Button();
		Button btnSupprimer = new Button();
		Button btnRetour = new Button();
		Label lbltitle = new Label("Archives des clients");
		
		Pane root = new Pane();
		

		// construction de la table
		tableClient.setEditable(false);
		tableClient.setPrefSize(525, 525);

		//id.setCellValueFactory(new PropertyValueFactory<Client, Integer>("idClient"));
		nom.setCellValueFactory(new PropertyValueFactory<Client, String>("nomCompagnie"));
		tel.setCellValueFactory(new PropertyValueFactory<Client, String>("telephone"));
		nomR.setCellValueFactory(new PropertyValueFactory<Client, String>("nomResponsable"));
		adresse.setCellValueFactory(new PropertyValueFactory<Client, String>("adresse"));
		courriel.setCellValueFactory(new PropertyValueFactory<Client, String>("courriel"));
		tableClient.getColumns().addAll(nom, tel, nomR, adresse, courriel);

		// id.prefWidthProperty().bind(tableClient.widthProperty().divide(2));
		// nom.prefWidthProperty().bind(tableClient.widthProperty().divide(2));
		// tableClient.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tableClient.setItems(list);

		btnActiver.setText("Activer");
		btnActiver.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					tableClient.getSelectionModel().getSelectedItem().activerClient();
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

				if (tableClient.getSelectionModel().getSelectedItem() != null) {
					try {
						tableClient.getSelectionModel().getSelectedItem().supprimerClient();
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

		lbltitle.setLayoutX(90);
		lbltitle.setLayoutY(50);
		lbltitle.setScaleX(2);
		lbltitle.setScaleY(2);
		btnActiver.setLayoutX(30);
		btnActiver.setLayoutY(105);
		btnSupprimer.setLayoutX(30);
		btnSupprimer.setLayoutY(185);
		
		btnRetour.setLayoutX(30);
		btnRetour.setLayoutY(575);

		btnActiver.setMinHeight(50);
		btnActiver.setMinWidth(150);
		btnSupprimer.setMinHeight(50);
		btnSupprimer.setMinWidth(150);
		btnRetour.setMinHeight(50);
		btnRetour.setMinWidth(150);
		
		if(MainMenu._role.get_activer().equals("")) {
			btnActiver.setDisable(true);
		}
		
		if(MainMenu._role.get_supprimer().equals("")) {
			btnSupprimer.setDisable(true);
		}

		// layout list
		tableClient.setLayoutX(200);
		tableClient.setLayoutY(105);
		tableClient.setPrefWidth(MainLogin.bounds.getWidth() - 300);
		tableClient.setPrefHeight(MainLogin.bounds.getHeight() -200);
		tableClient.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		root.getChildren().add(tableClient);
		root.getChildren().add(btnActiver);
		root.getChildren().add(btnSupprimer);
		root.getChildren().add(btnRetour);
		root.getChildren().add(lbltitle);
		// create window
		Scene scene = new Scene(root, 750, 650);
		primaryStage.setTitle("Archive Client");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public void RechercherClient(TextField tfRecherche) throws SQLException {

		Connection conn = SimpleDataSource.getConnection();
		try {

			Statement stat = conn.createStatement();

			ResultSet result = stat.executeQuery(
					"Select * from client Where nom_compagnie LIKE '%" + tfRecherche.getText() +"%'");
			list.clear();
			while (result.next()) {
				list.add(new Client(Integer.parseInt(result.getString("id_client")), result.getString("nom_compagnie"),
						result.getString("telephone"), result.getString("personne_responsable"),
						result.getString("adresse"), result.getString("courriel")));
			}
		} finally {
			conn.close();
		}
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
        if (!tf.getText().matches(".{1,128}")) {
            if (! styleClass.contains("error")) {
                styleClass.add("error");
            }
        } else {
            // remove all occurrences:
            styleClass.removeAll(Collections.singleton("error"));                    
        }
    }

}
