/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projexMedia;

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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author bruneaje
 */
public class GestionnaireClient {
	Client _test;
	Tab _clientTab;
	Pane _pane;

	public ObservableList<Client> list = FXCollections.observableArrayList();

	public GestionnaireClient() {
		_test = new Client();
		_clientTab = new Tab("Client", new Label("Salut"));
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

	@SuppressWarnings("unchecked")
	public void createPane(Stage primaryStage) throws Exception {

		affichageClient();

		TableView<Client> tableClient = new TableView<Client>();
		TableColumn<Client, Integer> id = new TableColumn<Client, Integer>("id Client");
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

		id.setCellValueFactory(new PropertyValueFactory<Client, Integer>("idClient"));
		nom.setCellValueFactory(new PropertyValueFactory<Client, String>("nomCompagnie"));
		tel.setCellValueFactory(new PropertyValueFactory<Client, String>("telephone"));
		nomR.setCellValueFactory(new PropertyValueFactory<Client, String>("nomResponsable"));
		adresse.setCellValueFactory(new PropertyValueFactory<Client, String>("adresse"));
		courriel.setCellValueFactory(new PropertyValueFactory<Client, String>("courriel"));
		tableClient.getColumns().addAll(id, nom, tel, nomR, adresse, courriel);

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
					Consulter test = new Consulter();
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

		btnConsArch.setText("Consulter Archive");
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

		btnAjouter.setLayoutX(30);
		btnAjouter.setLayoutY(60);
		btnModifier.setLayoutX(30);
		btnModifier.setLayoutY(100);
		btnArchiver.setLayoutX(30);
		btnArchiver.setLayoutY(140);
		btnConsArch.setLayoutX(30);
		btnConsArch.setLayoutY(340);
		tfRecherche.setLayoutX(500);
		tfRecherche.setLayoutY(10);
		btnRecherche.setLayoutX(690);
		btnRecherche.setLayoutY(10);

		btnAjouter.setMinHeight(35);
		btnAjouter.setMinWidth(100);
		btnModifier.setMinHeight(35);
		btnModifier.setMinWidth(100);
		btnArchiver.setMinHeight(35);
		btnArchiver.setMinWidth(100);
		btnConsArch.setMinHeight(35);
		btnConsArch.setMinWidth(100);
		btnRecherche.setPadding(Insets.EMPTY);
		//TextFields.bindAutoCompletion(tfRecherche, possibleClient);

		// layout list
		tableClient.setLayoutX(150);
		tableClient.setLayoutY(60);

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
		Label lbl = new Label("Nom de la compagnie");
		Label lbl1 = new Label("T�l�phone");
		Label lbl2 = new Label("Adresse");
		Label lbl3 = new Label("Personne Responsable");
		Label lbl4 = new Label("Courriel");

		// panel
		Pane root = new Pane();
		// BorderPane root1 = new BorderPane();
		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Client lul = new Client(tf.getText(), tf1.getText(), tf3.getText(), tf4.getText(), tf2.getText());
				try {
					lul.ajouterClient();
					Consulter test = new Consulter();
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
				Consulter test = new Consulter();
				try {
					test.start(primaryStage);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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

		// Bouton
		btn.setLayoutX(50);
		btn.setLayoutY(350);
		btn1.setLayoutX(275);
		btn1.setLayoutY(350);

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

		// create window
		Scene scene = new Scene(root, 450, 400);
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
		Label lbl = new Label("Nom de la compagnie");
		Label lbl1 = new Label("T�l�phone");
		Label lbl2 = new Label("Adresse");
		Label lbl3 = new Label("Personne Responsable");
		Label lbl4 = new Label("Courriel");

		// panel
		Pane root = new Pane();
		// BorderPane root1 = new BorderPane();

		btn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Client lul = new Client(client.getIdClient(), tf.getText(), tf1.getText(), tf3.getText(), tf2.getText(),
						tf4.getText());
				try {
					lul.modifierNomClient();
					lul.modifierAdresseClient();
					lul.modifierCourrielClient();
					lul.modifierNomResponsableClient();
					lul.modifierTelephoneClient();

					Consulter test = new Consulter();
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
				Consulter test = new Consulter();
				try {
					test.start(primaryStage);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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

		// Bouton
		btn.setLayoutX(50);
		btn.setLayoutY(350);
		btn1.setLayoutX(275);
		btn1.setLayoutY(350);

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

		// create window
		Scene scene = new Scene(root, 450, 400);
		primaryStage.setTitle("Modifier Client");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public ObservableList<Client> getList() {
		return list;
	}

	public void setList(ObservableList<Client> list) {
		this.list = list;
	}

	public void affichageClient() throws Exception {
		list.clear();
		Connection conn = SimpleDataSource.getConnection();

		try {
			Statement stat = conn.createStatement();

			ResultSet result = stat.executeQuery("SELECT * From client where actif=1");

			while (result.next()) {

				System.out.println(result.getString("id_client") + "\t" + result.getString("nom_compagnie") + "\t"
						+ result.getString("telephone"));
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

				System.out.println(result.getString("id_client") + "\t" + result.getString("nom_compagnie") + "\t"
						+ result.getString("telephone"));
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
		TableColumn<Client, Integer> id = new TableColumn<Client, Integer>("id Client");
		TableColumn<Client, String> nom = new TableColumn<Client, String>("nom Compagnie");
		TableColumn<Client, String> tel = new TableColumn<Client, String>("Telephone");
		TableColumn<Client, String> nomR = new TableColumn<Client, String>("nom Responsable");
		TableColumn<Client, String> adresse = new TableColumn<Client, String>("adresse");
		TableColumn<Client, String> courriel = new TableColumn<Client, String>("courriel");

		Button btnActiver = new Button();
		Button btnSupprimer = new Button();
		Button btnRetour = new Button();
		
		Pane root = new Pane();
		

		// construction de la table
		tableClient.setEditable(false);
		tableClient.setPrefSize(525, 525);

		id.setCellValueFactory(new PropertyValueFactory<Client, Integer>("idClient"));
		nom.setCellValueFactory(new PropertyValueFactory<Client, String>("nomCompagnie"));
		tel.setCellValueFactory(new PropertyValueFactory<Client, String>("telephone"));
		nomR.setCellValueFactory(new PropertyValueFactory<Client, String>("nomResponsable"));
		adresse.setCellValueFactory(new PropertyValueFactory<Client, String>("adresse"));
		courriel.setCellValueFactory(new PropertyValueFactory<Client, String>("courriel"));
		tableClient.getColumns().addAll(id, nom, tel, nomR, adresse, courriel);

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
				Consulter test = new Consulter();
				try {
					test.start(primaryStage);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		btnActiver.setLayoutX(30);
		btnActiver.setLayoutY(60);
		btnSupprimer.setLayoutX(30);
		btnSupprimer.setLayoutY(140);
		
		btnRetour.setLayoutX(30);
		btnRetour.setLayoutY(340);

		btnActiver.setMinHeight(35);
		btnActiver.setMinWidth(100);
		btnSupprimer.setMinHeight(35);
		btnSupprimer.setMinWidth(100);
		btnRetour.setMinHeight(35);
		btnRetour.setMinWidth(100);

		// layout list
		tableClient.setLayoutX(150);
		tableClient.setLayoutY(60);

		root.getChildren().add(tableClient);
		root.getChildren().add(btnActiver);
		root.getChildren().add(btnSupprimer);
		root.getChildren().add(btnRetour);
		// create window
		Scene scene = new Scene(root, 700, 700);
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

}
