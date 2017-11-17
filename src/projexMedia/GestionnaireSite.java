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
import java.util.regex.Pattern;

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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;

/**
 *
 * @author houdeto
 */
public class GestionnaireSite {

	private Tab _siteTab;
	private Pane _sitePane;

	private ObservableList<Site> _data;

	public GestionnaireSite() {
		_siteTab = new Tab("Site");
		_sitePane = new Pane();
	}

	@SuppressWarnings({ "unchecked"})
	public void createPane(Stage primaryStage) throws ClassNotFoundException, IOException, SQLException {

		TableView<Site> _table = new TableView<Site>();
		TableColumn<Site, String> Col = new TableColumn<Site, String>("Url du site");
		TableColumn<Site, String> Col2 = new TableColumn<Site, String>("Compagnie");

		SimpleDataSource.init("src/projexMedia/database.properties");

		Connection conn = SimpleDataSource.getConnection();

		_data = FXCollections.observableArrayList();

		if(GestionnaireClient.doubleClick) {
			RechercherSite(GestionnaireClient.id_Client);
		}
		else {
			try {
				Statement stat = conn.createStatement();

				ResultSet result = stat.executeQuery(
						"SELECT * FROM site JOIN client ON site.fk_id_client = client.id_client WHERE site.actif=1");

				while (result.next()) {
					_data.add(
							new Site(result.getInt("id_site"), result.getString("url"), result.getString("nom_compagnie")));
				}
				
			} finally {
				conn.close();
			}
		}
		

		Button btnAjouter = new Button();
		Button btnModifier = new Button();
		Button btnArchiver = new Button();
		Button btnConsulter = new Button();
		Image imageRecherche = new Image(getClass().getResourceAsStream("loupe.png"));
		Button btnRecherche = new Button();
		ImageView image = new ImageView(imageRecherche);

		btnRecherche.setGraphic(image);

		TextField tfRecherche = new TextField();

		_table.setEditable(false);
		_table.setPrefSize(525, 525);

		Col.setCellValueFactory(new PropertyValueFactory<Site, String>("url"));
		Col2.setCellValueFactory(new PropertyValueFactory<Site, String>("client"));
		_table.getColumns().addAll(Col, Col2);
		_table.setItems(_data);
		Col.prefWidthProperty().bind(_table.widthProperty().divide(2));
		Col2.prefWidthProperty().bind(_table.widthProperty().divide(2));
		_table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		btnAjouter.setText("Ajouter");
		btnAjouter.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					AjouterSite(primaryStage);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnModifier.setText("Modifier");
		btnModifier.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				if (_table.getSelectionModel().getSelectedItem() != null) {
					try {
						ModifierSite(primaryStage, _table.getSelectionModel().getSelectedItem());
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		btnArchiver.setText("Archiver");
		btnArchiver.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {

					ArchiverSite(primaryStage, _table.getSelectionModel().getSelectedItem());

					Connection conn = SimpleDataSource.getConnection();

					Statement stat = conn.createStatement();

					ResultSet result = stat.executeQuery(
							"SELECT * FROM site JOIN client ON site.fk_id_client = client.id_client WHERE site.actif=1");

					_data.removeAll(_data);
					while (result.next()) {
						_data.add(new Site(result.getInt("id_site"), result.getString("url"),
								result.getString("nom_compagnie")));
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

		btnConsulter.setText("Consulter Archives");
		btnConsulter.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					consulterArchive(primaryStage);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		btnRecherche.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					RechercherSite(tfRecherche.getText());
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
					RechercherSite(tfRecherche.getText());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		

		_table.setOnMousePressed(new EventHandler<MouseEvent>() {
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
						GestionnaireService service = new GestionnaireService();
						service.afficherService(primaryStage, _table.getSelectionModel().getSelectedItem().getIdSite());
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});

		DropShadow shadow = new DropShadow();
		// Adding the shadow when the mouse cursor is on
		btnRecherche.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				btnRecherche.setEffect(shadow);
			}
		});
		// Removing the shadow when the mouse cursor is off
		btnRecherche.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				btnRecherche.setEffect(null);
			}
		});

		btnAjouter.setLayoutX(30);
		btnAjouter.setLayoutY(60);
		btnModifier.setLayoutX(30);
		btnModifier.setLayoutY(140);
		btnArchiver.setLayoutX(30);
		btnArchiver.setLayoutY(220);
		btnConsulter.setLayoutX(30);
		btnConsulter.setLayoutY(530);
		tfRecherche.setLayoutX(500);
		tfRecherche.setLayoutY(10);
		btnRecherche.setLayoutX(690);
		btnRecherche.setLayoutY(10);
		_table.setLayoutX(200);
		_table.setLayoutY(60);

		btnAjouter.setMinHeight(50);
		btnAjouter.setMinWidth(150);
		btnModifier.setMinHeight(50);
		btnModifier.setMinWidth(150);
		btnArchiver.setMinHeight(50);
		btnArchiver.setMinWidth(150);
		btnConsulter.setMinHeight(50);
		btnConsulter.setMinWidth(150);
		btnRecherche.setPadding(Insets.EMPTY);
		
		if(MainMenu._role.get_ajouter().equals("")) {
			btnAjouter.setDisable(true);
		}
		
		if(MainMenu._role.get_modifier().equals("")) {
			btnModifier.setDisable(true);
		}
		
		if(MainMenu._role.get_archiver().equals("")) {
			btnArchiver.setDisable(true);
		}

		_sitePane.getChildren().add(btnAjouter);
		_sitePane.getChildren().add(btnModifier);
		_sitePane.getChildren().add(btnArchiver);
		_sitePane.getChildren().add(btnConsulter);
		_sitePane.getChildren().add(tfRecherche);
		_sitePane.getChildren().add(_table);
		_sitePane.getChildren().add(btnRecherche);
	}

	public void setSitePane(Pane sitePane) {
		_sitePane = sitePane;
	}

	public Pane getSitePane() {
		return _sitePane;
	}

	public void setSiteTab(Tab siteTab) {
		_siteTab = siteTab;
	}

	public Tab getSiteTab() {
		return _siteTab;
	}

	public void AjouterSite(Stage primaryStage) throws SQLException {

		ObservableList<String> possibleClient = FXCollections.observableArrayList();
		ObservableList<String> options = FXCollections.observableArrayList();

		Connection conn = SimpleDataSource.getConnection();

		try {
			Statement stat = conn.createStatement();

			ResultSet result = stat.executeQuery("SELECT nom_compagnie FROM client");

			while (result.next()) {
				possibleClient.add(result.getString("nom_compagnie"));
			}

			result = stat.executeQuery("SELECT nom_serveur FROM serveur");

			while (result.next()) {
				options.add(result.getString("nom_serveur"));
			}

		} finally {
			conn.close();
		}
		

		// bouton
		Button btnAjouter = new Button("Ajouter");
		Button btnCancel = new Button("Cancel");

		// textField
		ComboBox<String> cmbServeur = new ComboBox<String>(options);
		cmbServeur.setTooltip(new Tooltip());
		ComboBox<String> cmbClient = new ComboBox<String>(possibleClient);
		cmbClient.setTooltip(new Tooltip());
		TextField tfUrl = new TextField();

		// label
		Label lbl = new Label("Serveur :");
		Label lbl1 = new Label("Client :");
		Label lbl2 = new Label("Url :");

		btnAjouter.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				if (tfUrl.getText().matches(".{1,512}")) {
					try {
						Connection conn = SimpleDataSource.getConnection();
						Statement stat = conn.createStatement();

						ResultSet result = stat.executeQuery(
								"SELECT id_client FROM client WHERE nom_compagnie = '" + cmbClient.getValue() + "'");
						result.next();
						int client = result.getInt("id_client");

						result = stat.executeQuery(
								"SELECT id_serveur FROM serveur WHERE nom_serveur = '" + cmbServeur.getValue() + "'");
						result.next();
						int serveur = result.getInt("id_serveur");

						stat.execute("INSERT INTO site (url, actif, fk_id_serveur, fk_id_client) VALUES(\""
								+ tfUrl.getText() + "\"" + ", 1 , " + serveur + ", " + client + ")");

						MainMenu menu = new MainMenu();
						menu.set_activeTab(1);
						menu.start(primaryStage);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
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
				else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Message d'erreur");
					alert.setHeaderText("Le champs url n'est pas valide. ( Ex: www.google.com ) \nLa limite de charactère est de 512.");
					Optional<ButtonType> result = alert.showAndWait();
					if (result.get() == ButtonType.OK) {
						
					}
				}
					
			}
		});

		btnCancel.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
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
		});
		
		setUpValidation(tfUrl);

		// panel
		Pane root = new Pane();

		// premier champ
		lbl.setLayoutX(50);
		lbl.setLayoutY(70);
		cmbServeur.setLayoutX(200);
		cmbServeur.setLayoutY(70);

		// deuxieme champ
		lbl1.setLayoutX(50);
		lbl1.setLayoutY(120);
		cmbClient.setLayoutX(200);
		cmbClient.setLayoutY(120);

		// troisieme champ
		lbl2.setLayoutX(50);
		lbl2.setLayoutY(170);
		tfUrl.setLayoutX(200);
		tfUrl.setLayoutY(170);

		// Bouton
		btnAjouter.setLayoutX(50);
		btnAjouter.setLayoutY(285);
		btnCancel.setLayoutX(250);
		btnCancel.setLayoutY(285);

		cmbServeur.setMinWidth(185);
		btnAjouter.setMinHeight(30);
		btnAjouter.setMinWidth(130);
		btnCancel.setMinHeight(30);
		btnCancel.setMinWidth(130);

		// add to panel
		root.getChildren().add(lbl);
		root.getChildren().add(lbl1);
		root.getChildren().add(lbl2);
		root.getChildren().add(cmbServeur);
		root.getChildren().add(cmbClient);
		root.getChildren().add(tfUrl);
		root.getChildren().add(btnAjouter);
		root.getChildren().add(btnCancel);

		// create window
		Scene scene = new Scene(root, 450, 350);
		primaryStage.setTitle("Ajouter Site");
		primaryStage.setScene(scene);
		primaryStage.show();
		new ComboBoxAutoComplete<String>(cmbServeur);
		new ComboBoxAutoComplete<String>(cmbClient);
	}

	public void ModifierSite(Stage primaryStage, Site site) throws SQLException {

		ObservableList<String> possibleClient = FXCollections.observableArrayList();
		ObservableList<String> options = FXCollections.observableArrayList();

		// bouton
		Button btnModifier = new Button("Modifier");
		Button btnCancel = new Button("Cancel");

		// textField
		ComboBox<String> cmbServeur = new ComboBox<String>(options);
		cmbServeur.setTooltip(new Tooltip());
		ComboBox<String> cmbClient = new ComboBox<String>(possibleClient);
		cmbClient.setTooltip(new Tooltip());
		TextField tfUrl = new TextField();

		// label
		Label lbl = new Label("Serveur :");
		Label lbl1 = new Label("Client :");
		Label lbl2 = new Label("Url :");

		Connection conn = SimpleDataSource.getConnection();

		try {
				Statement stat = conn.createStatement();
	
				ResultSet result = stat.executeQuery("SELECT nom_compagnie FROM client");
	
				while (result.next()) {
					possibleClient.add(result.getString("nom_compagnie"));
				}
	
				result = stat.executeQuery("SELECT nom_serveur FROM serveur");
	
				while (result.next()) {
					options.add(result.getString("nom_serveur"));
				}
	
				result = stat.executeQuery(
						"SELECT site.id_site, site.url, serveur.nom_serveur, client.nom_compagnie FROM site JOIN client ON site.fk_id_client = client.id_client JOIN serveur ON site.fk_id_serveur = serveur.id_serveur WHERE id_site = '"
								+ site.getIdSite() + "'");
				result.next();
				cmbServeur.setValue(result.getString("nom_serveur"));
				cmbClient.setValue(result.getString("nom_compagnie"));
				tfUrl.setText(result.getString("url"));

		} finally {
			conn.close();
		}

		btnModifier.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (tfUrl.getText().matches(".{1,512}")) {
					try {
						Connection conn = SimpleDataSource.getConnection();
						Statement stat = conn.createStatement();
	
						ResultSet result = stat.executeQuery(
								"SELECT id_client FROM client WHERE nom_compagnie = '" + cmbClient.getValue() + "'");
						result.next();
						int client = result.getInt("id_client");
	
						result = stat.executeQuery(
								"SELECT id_serveur FROM serveur WHERE nom_serveur = '" + cmbServeur.getValue() + "'");
						result.next();
						int serveur = result.getInt("id_serveur");
	
						stat.execute("UPDATE site SET url='" + tfUrl.getText() + "', fk_id_serveur=" + serveur + ", fk_id_client=" + client + " WHERE id_site="
								+ site.getIdSite());
	
						MainMenu menu = new MainMenu();
						menu.set_activeTab(1);
						menu.start(primaryStage);
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
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
				else {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Message d'erreur");
					alert.setHeaderText("Le champs url n'est pas valide. ( Ex: www.google.com ) \nLa limite de charactère est de 512.");
					Optional<ButtonType> result = alert.showAndWait();
					if (result.get() == ButtonType.OK) {
						
					}
				}
			}
		});

		btnCancel.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
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
		});

		// panel
		Pane root = new Pane();

		// premier champ
		lbl.setLayoutX(50);
		lbl.setLayoutY(70);
		cmbServeur.setLayoutX(200);
		cmbServeur.setLayoutY(70);

		// deuxieme champ
		lbl1.setLayoutX(50);
		lbl1.setLayoutY(120);
		cmbClient.setLayoutX(200);
		cmbClient.setLayoutY(120);

		// troisieme champ
		lbl2.setLayoutX(50);
		lbl2.setLayoutY(170);
		tfUrl.setLayoutX(200);
		tfUrl.setLayoutY(170);

		// Bouton
		btnModifier.setLayoutX(50);
		btnModifier.setLayoutY(285);
		btnCancel.setLayoutX(250);
		btnCancel.setLayoutY(285);

		cmbServeur.setMinWidth(185);
		btnModifier.setMinHeight(30);
		btnModifier.setMinWidth(130);
		btnCancel.setMinHeight(30);
		btnCancel.setMinWidth(130);

		// add to panel
		root.getChildren().add(lbl);
		root.getChildren().add(lbl1);
		root.getChildren().add(lbl2);
		root.getChildren().add(cmbServeur);
		root.getChildren().add(cmbClient);
		root.getChildren().add(tfUrl);
		root.getChildren().add(btnModifier);
		root.getChildren().add(btnCancel);

		// create window
		Scene scene = new Scene(root, 450, 350);
		primaryStage.setTitle("Modifier Site");
		primaryStage.setScene(scene);
		primaryStage.show();
		new ComboBoxAutoComplete<String>(cmbServeur);
		new ComboBoxAutoComplete<String>(cmbClient);
	}

	public void ArchiverSite(Stage primaryStage, Site site) throws SQLException {

		Connection conn = SimpleDataSource.getConnection();
		try {
			Statement stat = conn.createStatement();

			stat.execute("UPDATE site SET actif=0 WHERE id_site=" + site.getIdSite());
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void consulterArchive(Stage primaryStage) throws SQLException {
		TableView<Site> _table = new TableView<Site>();
		TableColumn<Site, String> Col = new TableColumn<Site, String>("Url du site");
		TableColumn<Site, String> Col2 = new TableColumn<Site, String>("Compagnie");

		Connection conn = SimpleDataSource.getConnection();

		ObservableList<Site> data = FXCollections.observableArrayList();

		try {
			Statement stat = conn.createStatement();

			ResultSet result = stat.executeQuery(
					"SELECT * FROM site JOIN client ON site.fk_id_client = client.id_client WHERE site.actif=0");

			while (result.next()) {
				data.add(
						new Site(result.getInt("id_site"), result.getString("url"), result.getString("nom_compagnie")));
			}
		} finally {
			conn.close();
		}

		Button btnActiver = new Button();
		Button btnSupprimer = new Button();
		Button btnRetour = new Button();
		Pane root = new Pane();

		btnActiver.setText("Activer");
		btnActiver.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {

					activerSite(_table.getSelectionModel().getSelectedItem());

					Connection conn = SimpleDataSource.getConnection();

					Statement stat = conn.createStatement();

					ResultSet result = stat.executeQuery(
							"SELECT * FROM site JOIN client ON site.fk_id_client = client.id_client WHERE site.actif=0");

					data.removeAll(data);
					while (result.next()) {
						data.add(new Site(result.getInt("id_site"), result.getString("url"),
								result.getString("nom_compagnie")));
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
		btnSupprimer.setText("Supprimer");
		btnSupprimer.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {

					SupprimerSite(_table.getSelectionModel().getSelectedItem());

					Connection conn = SimpleDataSource.getConnection();

					Statement stat = conn.createStatement();

					ResultSet result = stat.executeQuery(
							"SELECT * FROM site JOIN client ON site.fk_id_client = client.id_client WHERE site.actif=0");

					data.removeAll(data);
					while (result.next()) {
						data.add(new Site(result.getInt("id_site"), result.getString("url"),
								result.getString("nom_compagnie")));
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
		btnRetour.setText("Retour");
		btnRetour.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
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
		});

		_table.setOnMousePressed(new EventHandler<MouseEvent>() {
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
						ModifierSite(primaryStage, _table.getSelectionModel().getSelectedItem());
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});

		_table.setEditable(false);
		_table.setPrefSize(525, 525);

		Col.setCellValueFactory(new PropertyValueFactory<Site, String>("url"));
		Col2.setCellValueFactory(new PropertyValueFactory<Site, String>("client"));
		_table.getColumns().addAll(Col, Col2);
		_table.setItems(data);
		Col.prefWidthProperty().bind(_table.widthProperty().divide(2));
		Col2.prefWidthProperty().bind(_table.widthProperty().divide(2));
		_table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		btnActiver.setLayoutX(30);
		btnActiver.setLayoutY(40);
		btnSupprimer.setLayoutX(30);
		btnSupprimer.setLayoutY(120);
		btnRetour.setLayoutX(30);
		btnRetour.setLayoutY(480);
		_table.setLayoutX(200);
		_table.setLayoutY(20);

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

		root.getChildren().add(btnActiver);
		root.getChildren().add(btnSupprimer);
		root.getChildren().add(btnRetour);
		root.getChildren().add(_table);

		Scene scene = new Scene(root, 750, 550);
		primaryStage.setTitle("Archives");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public void activerSite(Site site) throws SQLException {
		Connection conn = SimpleDataSource.getConnection();
		try {
			Statement stat = conn.createStatement();

			stat.execute("UPDATE site SET actif=1 WHERE id_site=" + site.getIdSite());
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void SupprimerSite(Site site) throws SQLException {
		Connection conn = SimpleDataSource.getConnection();
		try {
			Statement stat = conn.createStatement();

			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Message de confirmation");
			alert.setHeaderText("Êtes-vous sûr de vouloir supprimer ce site?");
			// alert.setContentText("Are you ok with this?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				stat.execute("DELETE FROM site WHERE id_site=" + site.getIdSite());
			}

		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void RechercherSite(String tfRecherche) throws SQLException {

		Connection conn = SimpleDataSource.getConnection();
		try {

			Statement stat = conn.createStatement();

			ResultSet result = stat.executeQuery(
					"SELECT client.nom_compagnie,site.id_site, site.url FROM client JOIN site ON client.id_client = site.fk_id_client WHERE client.nom_compagnie LIKE '%" + tfRecherche + "%' OR site.url LIKE '%" + tfRecherche + "%'");
			_data.removeAll(_data);
			while (result.next()) {
				_data.add(
						new Site(result.getInt("id_site"), result.getString("url"), result.getString("nom_compagnie")));
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
