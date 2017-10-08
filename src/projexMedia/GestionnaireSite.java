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

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;

/**
 *
 * @author houdeto
 */
public class GestionnaireSite {

	private Tab _siteTab;
	private Pane _sitePane;
	private Site _site;

	public ObservableList<Site> data;

	public GestionnaireSite() {
		_siteTab = new Tab("Site");
		_sitePane = new Pane();
	}

	@SuppressWarnings("unchecked")
	public void createPane(Stage primaryStage) throws ClassNotFoundException, IOException, SQLException {

		TableView<Site> _table = new TableView<Site>();
		TableColumn<Site, String> Col = new TableColumn<Site, String>("Url du site");
		TableColumn<Site, String> Col2 = new TableColumn<Site, String>("Compagnie");

		SimpleDataSource.init("src/projexMedia/database.properties");

		Connection conn = SimpleDataSource.getConnection();

		ObservableList<Site> data = FXCollections.observableArrayList();

		try {
			Statement stat = conn.createStatement();

			ResultSet result = stat
					.executeQuery("SELECT * FROM site JOIN client ON site.fk_id_client = client.id_client");

			while (result.next()) {
				data.add(
						new Site(result.getInt("id_site"), result.getString("url"), result.getString("nom_compagnie")));
			}
		} finally {
			conn.close();
		}

		Button btnAjouter = new Button();
		Button btnModifier = new Button();
		Button btnArchiver = new Button();
		Button btnConsulter = new Button();

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
			}
		});

		btnConsulter.setText("Consulter archives");
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

		btnAjouter.setLayoutX(30);
		btnAjouter.setLayoutY(40);
		btnModifier.setLayoutX(30);
		btnModifier.setLayoutY(120);
		btnArchiver.setLayoutX(30);
		btnArchiver.setLayoutY(200);
		btnConsulter.setLayoutX(30);
		btnConsulter.setLayoutY(480);
		_table.setLayoutX(200);
		_table.setLayoutY(20);

		btnAjouter.setMinHeight(50);
		btnAjouter.setMinWidth(150);
		btnModifier.setMinHeight(50);
		btnModifier.setMinWidth(150);
		btnArchiver.setMinHeight(50);
		btnArchiver.setMinWidth(150);
		btnConsulter.setMinHeight(50);
		btnConsulter.setMinWidth(150);

		_sitePane.getChildren().add(btnAjouter);
		_sitePane.getChildren().add(btnModifier);
		_sitePane.getChildren().add(btnArchiver);
		_sitePane.getChildren().add(btnConsulter);
		_sitePane.getChildren().add(_table);
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

		ArrayList<String> possibleClient = new ArrayList<String>();
		ObservableList<String> options = FXCollections.observableArrayList();
		ObservableList<String> optionsForfait = FXCollections.observableArrayList();

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

			result = stat.executeQuery("SELECT nom FROM forfait");

			while (result.next()) {
				optionsForfait.add(result.getString("nom"));
			}

		} finally {
			conn.close();
		}

		// bouton
		Button btnAjouter = new Button("Ajouter");
		Button btnCancel = new Button("Cancel");

		// textField
		ComboBox<String> cmbServeur = new ComboBox<String>(options);
		ComboBox<String> cmbForfait = new ComboBox<String>(optionsForfait);
		cmbServeur.setTooltip(new Tooltip());
		TextField tfClient = new TextField();
		TextFields.bindAutoCompletion(tfClient, possibleClient);
		TextField tfUrl = new TextField();

		// label
		Label lbl = new Label("Serveur :");
		Label lbl1 = new Label("Client :");
		Label lbl2 = new Label("Url :");
		Label lbl3 = new Label("Forfait :");

		btnAjouter.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				try {
					Connection conn = SimpleDataSource.getConnection();
					Statement stat = conn.createStatement();

					ResultSet result = stat
							.executeQuery("SELECT id_forfait FROM forfait WHERE nom = '" + cmbForfait.getValue() + "'");
					result.next();
					int forfait = result.getInt("id_forfait");

					result = stat.executeQuery(
							"SELECT id_client FROM client WHERE nom_compagnie = '" + tfClient.getText() + "'");
					result.next();
					int client = result.getInt("id_client");

					result = stat.executeQuery(
							"SELECT id_serveur FROM serveur WHERE nom_serveur = '" + cmbServeur.getValue() + "'");
					result.next();
					int serveur = result.getInt("id_serveur");

					stat.execute("INSERT INTO site (url, actif, fk_id_forfait, fk_id_serveur, fk_id_client) VALUES(\""
							+ tfUrl.getText() + "\"" + ", 1 , " + forfait + ", " + serveur + ", " + client + ")");

					MainMenu menu = new MainMenu();
					menu.set_activeTab(1);
					menu.start(primaryStage);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					System.out.println(cmbForfait.getValue());
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					System.out.println("error 2");
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("error 3");
					e.printStackTrace();
				} finally {
					try {
						conn.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						System.out.println("error 4");
						e.printStackTrace();
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
		tfClient.setLayoutX(200);
		tfClient.setLayoutY(120);

		// troisieme champ
		lbl2.setLayoutX(50);
		lbl2.setLayoutY(170);
		tfUrl.setLayoutX(200);
		tfUrl.setLayoutY(170);

		lbl3.setLayoutX(50);
		lbl3.setLayoutY(220);
		cmbForfait.setLayoutX(200);
		cmbForfait.setLayoutY(220);

		// Bouton
		btnAjouter.setLayoutX(50);
		btnAjouter.setLayoutY(285);
		btnCancel.setLayoutX(250);
		btnCancel.setLayoutY(285);

		cmbServeur.setMinWidth(185);
		cmbForfait.setMinWidth(185);
		btnAjouter.setMinHeight(30);
		btnAjouter.setMinWidth(130);
		btnCancel.setMinHeight(30);
		btnCancel.setMinWidth(130);

		// add to panel
		root.getChildren().add(lbl);
		root.getChildren().add(lbl1);
		root.getChildren().add(lbl2);
		root.getChildren().add(cmbServeur);
		root.getChildren().add(tfClient);
		root.getChildren().add(tfUrl);
		root.getChildren().add(lbl3);
		root.getChildren().add(cmbForfait);
		root.getChildren().add(btnAjouter);
		root.getChildren().add(btnCancel);

		// create window
		Scene scene = new Scene(root, 450, 350);
		primaryStage.setTitle("Ajouter Site");
		primaryStage.setScene(scene);
		primaryStage.show();
		new ComboBoxAutoComplete<String>(cmbServeur);
		new ComboBoxAutoComplete<String>(cmbForfait);
	}

	public void ModifierSite(Stage primaryStage, Site site) throws SQLException {

		ArrayList<String> possibleClient = new ArrayList<String>();
		ObservableList<String> options = FXCollections.observableArrayList();
		ObservableList<String> optionsForfait = FXCollections.observableArrayList();

		// bouton
		Button btnModifier = new Button("Modifier");
		Button btnCancel = new Button("Cancel");

		// textField
		ComboBox<String> cmbServeur = new ComboBox<String>(options);
		ComboBox<String> cmbForfait = new ComboBox<String>(optionsForfait);
		cmbServeur.setTooltip(new Tooltip());
		TextField tfClient = new TextField();
		TextFields.bindAutoCompletion(tfClient, possibleClient);
		TextField tfUrl = new TextField();

		// label
		Label lbl = new Label("Serveur :");
		Label lbl1 = new Label("Client :");
		Label lbl2 = new Label("Url :");
		Label lbl3 = new Label("Forfait :");

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

			result = stat.executeQuery("SELECT nom FROM forfait");

			while (result.next()) {
				optionsForfait.add(result.getString("nom"));
			}

			result = stat.executeQuery(
					"SELECT site.id_site, site.url, forfait.nom, serveur.nom_serveur, client.nom_compagnie FROM site JOIN client ON site.fk_id_client = client.id_client JOIN forfait ON site.fk_id_forfait = id_forfait JOIN serveur ON site.fk_id_serveur = id_serveur WHERE id_site = '"
							+ site.getIdSite() + "'");
			result.next();
			cmbServeur.setValue(result.getString("nom_serveur"));
			cmbForfait.setValue(result.getString("nom"));
			tfClient.setText(result.getString("nom_compagnie"));
			tfUrl.setText(result.getString("url"));

		} finally {
			conn.close();
		}

		btnModifier.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				try {
					Connection conn = SimpleDataSource.getConnection();
					Statement stat = conn.createStatement();

					ResultSet result = stat
							.executeQuery("SELECT id_forfait FROM forfait WHERE nom = '" + cmbForfait.getValue() + "'");
					result.next();
					int forfait = result.getInt("id_forfait");

					result = stat.executeQuery(
							"SELECT id_client FROM client WHERE nom_compagnie = '" + tfClient.getText() + "'");
					result.next();
					int client = result.getInt("id_client");

					result = stat.executeQuery(
							"SELECT id_serveur FROM serveur WHERE nom_serveur = '" + cmbServeur.getValue() + "'");
					result.next();
					int serveur = result.getInt("id_serveur");

					stat.execute("UPDATE site SET url='" + tfUrl.getText() + "', fk_id_forfait=" + forfait
							+ ", fk_id_serveur=" + serveur + ", fk_id_client=" + client + " WHERE id_site="
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
		tfClient.setLayoutX(200);
		tfClient.setLayoutY(120);

		// troisieme champ
		lbl2.setLayoutX(50);
		lbl2.setLayoutY(170);
		tfUrl.setLayoutX(200);
		tfUrl.setLayoutY(170);

		lbl3.setLayoutX(50);
		lbl3.setLayoutY(220);
		cmbForfait.setLayoutX(200);
		cmbForfait.setLayoutY(220);

		// Bouton
		btnModifier.setLayoutX(50);
		btnModifier.setLayoutY(285);
		btnCancel.setLayoutX(250);
		btnCancel.setLayoutY(285);

		cmbServeur.setMinWidth(185);
		cmbForfait.setMinWidth(185);
		btnModifier.setMinHeight(30);
		btnModifier.setMinWidth(130);
		btnCancel.setMinHeight(30);
		btnCancel.setMinWidth(130);

		// add to panel
		root.getChildren().add(lbl);
		root.getChildren().add(lbl1);
		root.getChildren().add(lbl2);
		root.getChildren().add(cmbServeur);
		root.getChildren().add(tfClient);
		root.getChildren().add(tfUrl);
		root.getChildren().add(lbl3);
		root.getChildren().add(cmbForfait);
		root.getChildren().add(btnModifier);
		root.getChildren().add(btnCancel);

		// create window
		Scene scene = new Scene(root, 450, 350);
		primaryStage.setTitle("Modifier Site");
		primaryStage.setScene(scene);
		primaryStage.show();
		new ComboBoxAutoComplete<String>(cmbServeur);
		new ComboBoxAutoComplete<String>(cmbForfait);
	}
	
	public void consulterArchive(Stage primaryStage) throws SQLException {
		TableView<Site> _table = new TableView<Site>();
		TableColumn<Site, String> Col = new TableColumn<Site, String>("Url du site");
		TableColumn<Site, String> Col2 = new TableColumn<Site, String>("Compagnie");

		Connection conn = SimpleDataSource.getConnection();

		ObservableList<Site> data = FXCollections.observableArrayList();

		try {
			Statement stat = conn.createStatement();

			ResultSet result = stat
					.executeQuery("SELECT * FROM site JOIN client ON site.fk_id_client = client.id_client");

			while (result.next()) {
				data.add(
						new Site(result.getInt("id_site"), result.getString("url"), result.getString("nom_compagnie")));
			}
		} finally {
			conn.close();
		}

		Button btnAjouter = new Button();
		Button btnModifier = new Button();
		Button btnArchiver = new Button();
		Button btnConsulter = new Button();
		Pane root = new Pane();

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
			}
		});

		btnConsulter.setText("Consulter archives");
		btnConsulter.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
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

		btnAjouter.setLayoutX(30);
		btnAjouter.setLayoutY(40);
		btnModifier.setLayoutX(30);
		btnModifier.setLayoutY(120);
		btnArchiver.setLayoutX(30);
		btnArchiver.setLayoutY(200);
		btnConsulter.setLayoutX(30);
		btnConsulter.setLayoutY(480);
		_table.setLayoutX(200);
		_table.setLayoutY(20);

		btnAjouter.setMinHeight(50);
		btnAjouter.setMinWidth(150);
		btnModifier.setMinHeight(50);
		btnModifier.setMinWidth(150);
		btnArchiver.setMinHeight(50);
		btnArchiver.setMinWidth(150);
		btnConsulter.setMinHeight(50);
		btnConsulter.setMinWidth(150);

		root.getChildren().add(btnAjouter);
		root.getChildren().add(btnModifier);
		root.getChildren().add(btnArchiver);
		root.getChildren().add(btnConsulter);
		root.getChildren().add(_table);
		
		Scene scene = new Scene(root, 750, 600);
		primaryStage.setTitle("Archives");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}

