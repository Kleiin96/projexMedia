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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GestionnaireUtilisateur {
	private Tab _userTab;
	private Pane _userPane;

	private ObservableList<Utilisateur> _data;

	public GestionnaireUtilisateur() {
		_userTab = new Tab("Utilisateur");
		_userPane = new Pane();
	}

	@SuppressWarnings({ "unchecked"})
	public void createPane(Stage primaryStage) throws ClassNotFoundException, IOException, SQLException {

		TableView<Utilisateur> _table = new TableView<Utilisateur>();
		TableColumn<Utilisateur, String> Col = new TableColumn<Utilisateur, String>("Nom d'utilisateur");
		TableColumn<Utilisateur, String> Col2 = new TableColumn<Utilisateur, String>("Mot de passe");
		TableColumn<Utilisateur, String> Col3 = new TableColumn<Utilisateur, String>("Prénom");
		TableColumn<Utilisateur, String> Col4 = new TableColumn<Utilisateur, String>("Nom");
		TableColumn<Utilisateur, String> Col5 = new TableColumn<Utilisateur, String>("Rôle");

		SimpleDataSource.init("src/projexMedia/database.properties");

		Connection conn = SimpleDataSource.getConnection();

		_data = FXCollections.observableArrayList();

		try {
			Statement stat = conn.createStatement();

			ResultSet result = stat.executeQuery(
					"SELECT pk_courriel, mdp, prenom, nom,nom_role FROM utilisateur JOIN role ON utilisateur.fk_id_role = role.id_role");

			while (result.next()) {
				_data.add(new Utilisateur(result.getString("pk_courriel"), result.getString("mdp"), result.getString("prenom"),result.getString("nom"), result.getString("nom_role")));
			}
			
		} finally {
			conn.close();
		}

		Button btnAjouter = new Button();
		Button btnModifier = new Button();
		Button btnSupprimer = new Button();

		_table.setEditable(false);
		_table.setPrefSize(525, 525);

		Col.setCellValueFactory(new PropertyValueFactory<Utilisateur, String>("user"));
		Col2.setCellValueFactory(new PropertyValueFactory<Utilisateur, String>("password"));
		Col3.setCellValueFactory(new PropertyValueFactory<Utilisateur, String>("prenom"));
		Col4.setCellValueFactory(new PropertyValueFactory<Utilisateur, String>("nom"));
		Col5.setCellValueFactory(new PropertyValueFactory<Utilisateur, String>("role"));
		_table.getColumns().addAll(Col, Col2, Col3, Col4, Col5);
		_table.setItems(_data);
		Col.prefWidthProperty().bind(_table.widthProperty().divide(5));
		Col2.prefWidthProperty().bind(_table.widthProperty().divide(5));
		Col3.prefWidthProperty().bind(_table.widthProperty().divide(5));
		Col4.prefWidthProperty().bind(_table.widthProperty().divide(5));
		Col5.prefWidthProperty().bind(_table.widthProperty().divide(5));
		_table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

		btnAjouter.setText("Ajouter");
		btnAjouter.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					AjouterUtilisateur(primaryStage);
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
						ModifierUtilisateur(primaryStage, _table.getSelectionModel().getSelectedItem());
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

				if (_table.getSelectionModel().getSelectedItem() != null) {
					try {
						SupprimerUtilisateur(_table.getSelectionModel().getSelectedItem());
						Connection conn = SimpleDataSource.getConnection();

						Statement stat = conn.createStatement();

						ResultSet result = stat.executeQuery(
								"SELECT pk_courriel, mdp, prenom, nom,nom_role FROM utilisateur JOIN role ON utilisateur.fk_id_role = role.id_role");
						_data.removeAll(_data);
						while (result.next()) {
							_data.add(new Utilisateur(result.getString("pk_courriel"), result.getString("mdp"), 
									result.getString("prenom"),result.getString("nom"), result.getString("nom_role")));
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
			}
		});	

		btnAjouter.setLayoutX(30);
		btnAjouter.setLayoutY(60);
		btnModifier.setLayoutX(30);
		btnModifier.setLayoutY(140);
		btnSupprimer.setLayoutX(30);
		btnSupprimer.setLayoutY(220);
		_table.setLayoutX(200);
		_table.setLayoutY(60);

		btnAjouter.setMinHeight(50);
		btnAjouter.setMinWidth(150);
		btnModifier.setMinHeight(50);
		btnModifier.setMinWidth(150);
		btnSupprimer.setMinHeight(50);
		btnSupprimer.setMinWidth(150);
		
		_userPane.getChildren().add(btnAjouter);
		_userPane.getChildren().add(btnModifier);
		_userPane.getChildren().add(_table);
		_userPane.getChildren().add(btnSupprimer);
	}

	public void setUtilisateurPane(Pane UtilisateurPane) {
		_userPane = UtilisateurPane;
	}

	public Pane getUtilisateurPane() {
		return _userPane;
	}

	public void setUtilisateurTab(Tab UtilisateurTab) {
		_userTab = UtilisateurTab;
	}

	public Tab getUtilisateurTab() {
		return _userTab;
	}

	public void AjouterUtilisateur(Stage primaryStage) throws SQLException {

		ObservableList<String> options = FXCollections.observableArrayList();

		Connection conn = SimpleDataSource.getConnection();

		try {
			Statement stat = conn.createStatement();

			ResultSet result = stat.executeQuery("SELECT nom_role FROM role");

			while (result.next()) {
				options.add(result.getString("nom_role"));
			}

		} finally {
			conn.close();
		}

		// bouton
		Button btnAjouter = new Button("Ajouter");
		Button btnCancel = new Button("Cancel");

		// textField
		ComboBox<String> cmbRole = new ComboBox<String>(options);
		cmbRole.setTooltip(new Tooltip());
		TextField tfCourriel = new TextField();
		TextField tfMdp = new TextField();
		TextField tfPrenom = new TextField();
		TextField tfNom = new TextField();
		

		// label
		Label lbl = new Label("Courriel :");
		Label lbl1 = new Label("Mot de passe :");
		Label lbl2 = new Label("Prénom :");
		Label lbl3 = new Label("Nom :");
		Label lbl4 = new Label("Rôle :");

		btnAjouter.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				if(cmbRole.getSelectionModel().isEmpty()){
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Message d'erreur");
					alert.setHeaderText("Veuillez choisir un rôle.");
					Optional<ButtonType> result = alert.showAndWait();
					if (result.get() == ButtonType.OK) {
						
					}
				}
				else {
					if (tfCourriel.getText().matches(".{1,128}") && tfMdp.getText().matches(".{1,128}")) {
						try {
							Connection conn = SimpleDataSource.getConnection();
							Statement stat = conn.createStatement();

							ResultSet result = stat.executeQuery(
									"SELECT id_role FROM role WHERE nom_role = '" + cmbRole.getValue() + "'");
							result.next();
							int role = result.getInt("id_role");

							stat.execute("INSERT INTO Utilisateur (pk_courriel, mdp, prenom, nom, fk_id_role) VALUES(\""
									+ tfCourriel.getText() + "\"" + ", \"" + tfMdp.getText() + "\"" + ", \"" + tfPrenom.getText() + "\"" + ", \"" + tfNom.getText() + "\"" + ", " + role + ")");

							MainMenu menu = new MainMenu();
							menu.set_activeTab(3);
							menu.start(primaryStage);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}finally {
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
						alert.setHeaderText("Le champs courriel ou mot de passe n'est pas valide. ( Ex: abc@live.ca ) et/ou \nLa limite de charactère de 128 est dépassée.");
						Optional<ButtonType> result = alert.showAndWait();
						if (result.get() == ButtonType.OK) {
							
						}
					}
				}
			}
		});

		btnCancel.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				MainMenu menu = new MainMenu();
				menu.set_activeTab(3);
				try {
					menu.start(primaryStage);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		// panel
		Pane root = new Pane();
		
		setUpValidation(tfCourriel);
		setUpValidation(tfMdp);

		lbl.setLayoutX(50);
		lbl.setLayoutY(70);
		tfCourriel.setLayoutX(200);
		tfCourriel.setLayoutY(70);

		lbl1.setLayoutX(50);
		lbl1.setLayoutY(120);
		tfMdp.setLayoutX(200);
		tfMdp.setLayoutY(120);
		
		lbl2.setLayoutX(50);
		lbl2.setLayoutY(170);
		tfPrenom.setLayoutX(200);
		tfPrenom.setLayoutY(170);
		
		lbl3.setLayoutX(50);
		lbl3.setLayoutY(220);
		tfNom.setLayoutX(200);
		tfNom.setLayoutY(220);
		
		lbl4.setLayoutX(50);
		lbl4.setLayoutY(270);
		cmbRole.setLayoutX(200);
		cmbRole.setLayoutY(270);

		// Bouton
		btnAjouter.setLayoutX(50);
		btnAjouter.setLayoutY(340);
		btnCancel.setLayoutX(250);
		btnCancel.setLayoutY(340);

		cmbRole.setMinWidth(185);
		btnAjouter.setMinHeight(30);
		btnAjouter.setMinWidth(130);
		btnCancel.setMinHeight(30);
		btnCancel.setMinWidth(130);

		// add to panel
		root.getChildren().add(lbl);
		root.getChildren().add(lbl1);
		root.getChildren().add(lbl2);
		root.getChildren().add(cmbRole);
		root.getChildren().add(tfCourriel);
		root.getChildren().add(tfMdp);
		root.getChildren().add(tfPrenom);
		root.getChildren().add(tfNom);
		root.getChildren().add(lbl3);
		root.getChildren().add(lbl4);
		root.getChildren().add(btnAjouter);
		root.getChildren().add(btnCancel);

		// create window
		Scene scene = new Scene(root, 450, 400);
		primaryStage.setTitle("Ajouter Utilisateur");
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
		new ComboBoxAutoComplete<String>(cmbRole);
	}

	
	public void ModifierUtilisateur(Stage primaryStage, Utilisateur utilisateur) throws SQLException {

		ObservableList<String> options = FXCollections.observableArrayList();

		// bouton
		Button btnModifier = new Button("Modifier");
		Button btnCancel = new Button("Cancel");

		// textField
		ComboBox<String> cmbRole = new ComboBox<String>(options);
		cmbRole.setTooltip(new Tooltip());
		TextField tfCourriel = new TextField();
		TextField tfMdp = new TextField();
		TextField tfPrenom = new TextField();
		TextField tfNom = new TextField();
		

		// label
		Label lbl = new Label("Courriel :");
		Label lbl1 = new Label("Mot de passe :");
		Label lbl2 = new Label("Prénom :");
		Label lbl3 = new Label("Nom :");
		Label lbl4 = new Label("Rôle :");

		Connection conn = SimpleDataSource.getConnection();

		try {
			Statement stat = conn.createStatement();

			ResultSet result = stat.executeQuery("SELECT nom_role FROM role");

			while (result.next()) {
				options.add(result.getString("nom_role"));
			}

			result = stat.executeQuery(
					"SELECT * FROM utilisateur JOIN role ON utilisateur.fk_id_role = role.id_role WHERE pk_courriel ='"+ utilisateur.getUser() + "'");
			result.next();
			cmbRole.setValue(result.getString("nom_role"));
			tfCourriel.setText(result.getString("pk_courriel"));
			tfMdp.setText(result.getString("mdp"));
			tfPrenom.setText(result.getString("prenom"));
			tfNom.setText(result.getString("nom"));

		} finally {
			conn.close();
		}

		btnModifier.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if(cmbRole.getSelectionModel().isEmpty()){
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Message d'erreur");
					alert.setHeaderText("Veuillez choisir un rôle.");
					Optional<ButtonType> result = alert.showAndWait();
					if (result.get() == ButtonType.OK) {
						
					}
				}
				else {
					if (tfCourriel.getText().matches(".{1,128}") && tfMdp.getText().matches(".{1,128}")) {
						try {
							Connection conn = SimpleDataSource.getConnection();
							Statement stat = conn.createStatement();

							ResultSet result = stat
									.executeQuery("SELECT id_role FROM role WHERE nom_role = '" + cmbRole.getValue() + "'");
							result.next();
							int role = result.getInt("id_role");

							stat.execute("UPDATE Utilisateur SET pk_courriel='" + tfCourriel.getText() + "', mdp='" + tfMdp.getText()
									+ "', prenom='" + tfPrenom.getText() + "', nom='" + tfNom.getText() + "', fk_id_role=" + role + " WHERE pk_courriel='"
									+ utilisateur.getUser() + "'");

							MainMenu menu = new MainMenu();
							menu.set_activeTab(3);
							menu.start(primaryStage);
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
						alert.setHeaderText("Le champs courriel ou mot de passe n'est pas valide. ( Ex: abc@live.ca ) et/ou \nLa limite de charactère de 128 est dépassée.");
						Optional<ButtonType> result = alert.showAndWait();
						if (result.get() == ButtonType.OK) {
							
						}
					}
				}
				
			}
		});

		btnCancel.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				MainMenu menu = new MainMenu();
				menu.set_activeTab(3);
				try {
					menu.start(primaryStage);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		// panel
		Pane root = new Pane();
		
		setUpValidation(tfCourriel);
		setUpValidation(tfMdp);

		lbl.setLayoutX(50);
		lbl.setLayoutY(70);
		tfCourriel.setLayoutX(200);
		tfCourriel.setLayoutY(70);

		lbl1.setLayoutX(50);
		lbl1.setLayoutY(120);
		tfMdp.setLayoutX(200);
		tfMdp.setLayoutY(120);
		
		lbl2.setLayoutX(50);
		lbl2.setLayoutY(170);
		tfPrenom.setLayoutX(200);
		tfPrenom.setLayoutY(170);
		
		lbl3.setLayoutX(50);
		lbl3.setLayoutY(220);
		tfNom.setLayoutX(200);
		tfNom.setLayoutY(220);
		
		lbl4.setLayoutX(50);
		lbl4.setLayoutY(270);
		cmbRole.setLayoutX(200);
		cmbRole.setLayoutY(270);

		// Bouton
		btnModifier.setLayoutX(50);
		btnModifier.setLayoutY(340);
		btnCancel.setLayoutX(250);
		btnCancel.setLayoutY(340);

		cmbRole.setMinWidth(185);
		btnModifier.setMinHeight(30);
		btnModifier.setMinWidth(130);
		btnCancel.setMinHeight(30);
		btnCancel.setMinWidth(130);

		// add to panel
		root.getChildren().add(lbl);
		root.getChildren().add(lbl1);
		root.getChildren().add(lbl2);
		root.getChildren().add(cmbRole);
		root.getChildren().add(tfCourriel);
		root.getChildren().add(tfMdp);
		root.getChildren().add(tfPrenom);
		root.getChildren().add(tfNom);
		root.getChildren().add(lbl3);
		root.getChildren().add(lbl4);
		root.getChildren().add(btnModifier);
		root.getChildren().add(btnCancel);

		// create window
		Scene scene = new Scene(root, 450, 400);
		primaryStage.setTitle("Modifier Utilisateur");
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
		new ComboBoxAutoComplete<String>(cmbRole);
	}

	public void SupprimerUtilisateur(Utilisateur utilisateur) throws SQLException {
		Connection conn = SimpleDataSource.getConnection();
		try {
			Statement stat = conn.createStatement();

			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Message de confirmation");
			alert.setHeaderText("Êtes-vous sûr de vouloir supprimer cet utilisateur?");
			// alert.setContentText("Are you ok with this?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				stat.execute("DELETE FROM Utilisateur WHERE pk_courriel='" + utilisateur.getUser() + "'");
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
