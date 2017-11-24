package projexMedia;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.Optional;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GestionnaireRole {
	private Tab _RoleTab;
	private Pane _RolePane;
	

	private ObservableList<Role> _data;

	public GestionnaireRole() {
		_RoleTab = new Tab("Role");
		_RolePane = new Pane();
	}

	@SuppressWarnings({ "unchecked"})
	public void createPane(Stage primaryStage) throws Exception {

		TableView<Role> _table = new TableView<Role>();
		TableColumn<Role, String> Col = new TableColumn<Role, String>("Nom du rôle");
		TableColumn<Role, String> Col2 = new TableColumn<Role, String>("Ajouter");
		TableColumn<Role, String> Col3 = new TableColumn<Role, String>("Modifier");
		TableColumn<Role, String> Col4 = new TableColumn<Role, String>("Archiver");
		TableColumn<Role, String> Col5 = new TableColumn<Role, String>("Activer");
		TableColumn<Role, String> Col6 = new TableColumn<Role, String>("Supprimer");
		TableColumn<Role, String> Col7 = new TableColumn<Role, String>("Historique");
		TableColumn<Role, String> Col8 = new TableColumn<Role, String>("Utilisateur");
		TableColumn<Role, String> Col9 = new TableColumn<Role, String>("Rôle");

		SimpleDataSource.init("src/projexMedia/database.properties");

		Connection conn = SimpleDataSource.getConnection();

		_data = FXCollections.observableArrayList();

		try {
			Statement stat = conn.createStatement();

			ResultSet result = stat.executeQuery(
					"SELECT * FROM role ");

			while (result.next()) {
				_data.add(new Role(result.getInt("id_role"), result.getString("nom_role"), result.getBoolean("ajouter"),result.getBoolean("modifier"), result.getBoolean("archiver"), result.getBoolean("activer"), result.getBoolean("supprimer"), result.getBoolean("historique"), result.getBoolean("utilisateur"), result.getBoolean("role")));
			}
			
		} finally {
			conn.close();
		}

		Button btnAjouter = new Button();
		Button btnModifier = new Button();
		Button btnSupprimer = new Button();

		_table.setEditable(false);
		_table.setPrefSize(525, 525);

		Col.setCellValueFactory(new PropertyValueFactory<Role, String>("_role"));
		Col2.setCellValueFactory(new PropertyValueFactory<Role, String>("_ajouter"));
		Col3.setCellValueFactory(new PropertyValueFactory<Role, String>("_modifier"));
		Col4.setCellValueFactory(new PropertyValueFactory<Role, String>("_archiver"));
		Col5.setCellValueFactory(new PropertyValueFactory<Role, String>("_activer"));
		Col6.setCellValueFactory(new PropertyValueFactory<Role, String>("_supprimer"));
		Col7.setCellValueFactory(new PropertyValueFactory<Role, String>("_historique"));
		Col8.setCellValueFactory(new PropertyValueFactory<Role, String>("_utilisateur"));
		Col9.setCellValueFactory(new PropertyValueFactory<Role, String>("_droitRole"));
		_table.getColumns().addAll(Col, Col2, Col3, Col4, Col5, Col6, Col7, Col8, Col9);
		_table.setItems(_data);
		Col.prefWidthProperty().bind(_table.widthProperty().divide(9));
		Col2.prefWidthProperty().bind(_table.widthProperty().divide(9));
		Col3.prefWidthProperty().bind(_table.widthProperty().divide(9));
		Col4.prefWidthProperty().bind(_table.widthProperty().divide(9));
		Col5.prefWidthProperty().bind(_table.widthProperty().divide(9));
		Col6.prefWidthProperty().bind(_table.widthProperty().divide(9));
		Col7.prefWidthProperty().bind(_table.widthProperty().divide(9));
		Col8.prefWidthProperty().bind(_table.widthProperty().divide(9));
		Col9.prefWidthProperty().bind(_table.widthProperty().divide(9));
		_table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		Col.getStyleClass().add("custom-align");
		Col2.getStyleClass().add("custom-align");
		Col3.getStyleClass().add("custom-align");
		Col4.getStyleClass().add("custom-align");
		Col5.getStyleClass().add("custom-align");
		Col6.getStyleClass().add("custom-align");
		Col7.getStyleClass().add("custom-align");
		Col8.getStyleClass().add("custom-align");
		Col9.getStyleClass().add("custom-align");

		btnAjouter.setText("Ajouter");
		btnAjouter.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					AjouterRole(primaryStage);
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
						ModifierRole(primaryStage, _table.getSelectionModel().getSelectedItem());
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
						SupprimerRole(_table.getSelectionModel().getSelectedItem());
						Connection conn = SimpleDataSource.getConnection();

						Statement stat = conn.createStatement();

						ResultSet result = stat.executeQuery(
								"SELECT * FROM role");
						_data.removeAll(_data);
						while (result.next()) {
							_data.add(new Role(result.getInt("id_role"), result.getString("nom_role"), result.getBoolean("ajouter"),result.getBoolean("modifier"), result.getBoolean("archiver"), result.getBoolean("activer"), result.getBoolean("supprimer"), result.getBoolean("historique"), result.getBoolean("utilisateur"), result.getBoolean("role")));
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
		
		_RolePane.getChildren().add(btnAjouter);
		_RolePane.getChildren().add(btnModifier);
		_RolePane.getChildren().add(_table);
		_RolePane.getChildren().add(btnSupprimer);
	}

	public void setRolePane(Pane RolePane) {
		_RolePane = RolePane;
	}

	public Pane getRolePane() {
		return _RolePane;
	}

	public void setRoleTab(Tab RoleTab) {
		_RoleTab = RoleTab;
	}

	public Tab getRoleTab() {
		return _RoleTab;
	}

	public void AjouterRole(Stage primaryStage) throws SQLException {

		// bouton
		Button btnAjouter = new Button("Ajouter");
		Button btnCancel = new Button("Cancel");

		TextField nomRole = new TextField();
		CheckBox cb1 = new CheckBox();
		CheckBox cb2 = new CheckBox();
		CheckBox cb3 = new CheckBox();
		CheckBox cb4 = new CheckBox();
		CheckBox cb5 = new CheckBox();
		CheckBox cb6 = new CheckBox();
		CheckBox cb7 = new CheckBox();
		CheckBox cb8 = new CheckBox();
		

		// label
		Label lbl = new Label("Nom du rôle :");
		Label lbl1 = new Label("Ajouter :");
		Label lbl2 = new Label("Modifier :");
		Label lbl3 = new Label("Archiver :");
		Label lbl4 = new Label("Activer :");
		Label lbl5 = new Label("Supprimer :");
		Label lbl6 = new Label("Historique :");
		Label lbl7 = new Label("Utilisateur :");
		Label lbl8 = new Label("Rôle :");

		btnAjouter.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				if (nomRole.getText().matches(".{1,64}")) {
					Connection conn = null;
					try {
						conn = SimpleDataSource.getConnection();
						Statement stat = conn.createStatement();
		
						int ajouter = (cb1.isSelected()) ? 1 : 0;
						int modifier = (cb2.isSelected()) ? 1 : 0;
						int archiver = (cb3.isSelected()) ? 1 : 0;
						int activer = (cb4.isSelected()) ? 1 : 0;
						int supprimer = (cb5.isSelected()) ? 1 : 0;
						int historique = (cb6.isSelected()) ? 1 : 0;
						int utilisateur = (cb7.isSelected()) ? 1 : 0;
						int role = (cb8.isSelected()) ? 1 : 0;
		
						stat.execute("INSERT INTO Role (nom_role, ajouter, modifier, archiver, activer, supprimer, historique, utilisateur, role) VALUES(\""
								+ nomRole.getText() + "\"" + "," + ajouter + "," + modifier + "," + archiver + "," + activer + "," + supprimer + "," + historique + "," + utilisateur + "," + role + ")");
		
						MainMenu menu = new MainMenu();
						menu.set_activeTab(5);
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
					alert.setHeaderText("Le champs Nom du rôle n'est pas valide. ( Ex: Admin ) et/ou \nLa limite de charactère de 64 est dépassée.");
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
				menu.set_activeTab(5);
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
		
		setUpValidation(nomRole);

		lbl.setLayoutX(50);
		lbl.setLayoutY(70);
		nomRole.setLayoutX(200);
		nomRole.setLayoutY(70);

		lbl1.setLayoutX(50);
		lbl1.setLayoutY(120);
		cb1.setLayoutX(200);
		cb1.setLayoutY(120);
		
		lbl2.setLayoutX(50);
		lbl2.setLayoutY(170);
		cb2.setLayoutX(200);
		cb2.setLayoutY(170);
		
		lbl3.setLayoutX(50);
		lbl3.setLayoutY(220);
		cb3.setLayoutX(200);
		cb3.setLayoutY(220);
		
		lbl4.setLayoutX(50);
		lbl4.setLayoutY(270);
		cb4.setLayoutX(200);
		cb4.setLayoutY(270);

		lbl5.setLayoutX(50);
		lbl5.setLayoutY(300);
		cb5.setLayoutX(200);
		cb5.setLayoutY(300);
		
		lbl6.setLayoutX(50);
		lbl6.setLayoutY(320);
		cb6.setLayoutX(200);
		cb6.setLayoutY(320);
		
		lbl7.setLayoutX(50);
		lbl7.setLayoutY(340);
		cb7.setLayoutX(200);
		cb7.setLayoutY(340);
		
		lbl8.setLayoutX(50);
		lbl8.setLayoutY(360);
		cb8.setLayoutX(200);
		cb8.setLayoutY(360);
		
		// Bouton
		btnAjouter.setLayoutX(50);
		btnAjouter.setLayoutY(380);
		btnCancel.setLayoutX(250);
		btnCancel.setLayoutY(380);

		btnAjouter.setMinHeight(30);
		btnAjouter.setMinWidth(130);
		btnCancel.setMinHeight(30);
		btnCancel.setMinWidth(130);

		// add to panel
		root.getChildren().add(lbl);
		root.getChildren().add(lbl1);
		root.getChildren().add(lbl2);
		root.getChildren().add(nomRole);
		root.getChildren().add(cb1);
		root.getChildren().add(cb2);
		root.getChildren().add(cb3);
		root.getChildren().add(cb4);
		root.getChildren().add(cb5);
		root.getChildren().add(lbl5);
		root.getChildren().add(lbl3);
		root.getChildren().add(lbl4);
		root.getChildren().add(lbl6);
		root.getChildren().add(lbl7);
		root.getChildren().add(lbl8);
		root.getChildren().add(cb6);
		root.getChildren().add(cb7);
		root.getChildren().add(cb8);
		root.getChildren().add(btnAjouter);
		root.getChildren().add(btnCancel);

		// create window
		Scene scene = new Scene(root, 450, 600);
		primaryStage.setTitle("Ajouter Role");
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	
	public void ModifierRole(Stage primaryStage, Role role) throws SQLException {

		// bouton
				Button btnModifier = new Button("Modifier");
				Button btnCancel = new Button("Cancel");

				TextField nomRole = new TextField();
				CheckBox cb1 = new CheckBox();
				CheckBox cb2 = new CheckBox();
				CheckBox cb3 = new CheckBox();
				CheckBox cb4 = new CheckBox();
				CheckBox cb5 = new CheckBox();
				CheckBox cb6 = new CheckBox();
				CheckBox cb7 = new CheckBox();
				CheckBox cb8 = new CheckBox();
				

				// label
				Label lbl = new Label("Nom du rôle :");
				Label lbl1 = new Label("Ajouter :");
				Label lbl2 = new Label("Modifier :");
				Label lbl3 = new Label("Archiver :");
				Label lbl4 = new Label("Activer :");
				Label lbl5 = new Label("Supprimer :");
				Label lbl6 = new Label("Historique :");
				Label lbl7 = new Label("Utilisateur :");
				Label lbl8 = new Label("Rôle :");

				
				Connection conn = SimpleDataSource.getConnection();
				
				try {
					
					Statement stat = conn.createStatement();

					ResultSet result = stat.executeQuery("SELECT * FROM role WHERE id_role=" + role.getId_role());

					result.next();
					nomRole.setText(result.getString("nom_role"));
					cb1.setSelected(result.getBoolean("ajouter"));
					cb2.setSelected(result.getBoolean("modifier"));
					cb3.setSelected(result.getBoolean("archiver"));
					cb4.setSelected(result.getBoolean("activer"));
					cb5.setSelected(result.getBoolean("supprimer"));
					cb6.setSelected(result.getBoolean("historique"));
					cb7.setSelected(result.getBoolean("utilisateur"));
					cb8.setSelected(result.getBoolean("role"));
					
					
				} finally {
					conn.close();
				}

				btnModifier.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {

						if (nomRole.getText().matches(".{1,64}")) {
							Connection conn = null;
							try {
								conn = SimpleDataSource.getConnection();
								Statement stat = conn.createStatement();
				
								int ajouter = (cb1.isSelected()) ? 1 : 0;
								int modifier = (cb2.isSelected()) ? 1 : 0;
								int archiver = (cb3.isSelected()) ? 1 : 0;
								int activer = (cb4.isSelected()) ? 1 : 0;
								int supprimer = (cb5.isSelected()) ? 1 : 0;
								int historique = (cb6.isSelected()) ? 1 : 0;
								int utilisateur = (cb7.isSelected()) ? 1 : 0;
								int droitRole = (cb8.isSelected()) ? 1 : 0;
				
								stat.execute("UPDATE role SET nom_role='" + nomRole.getText() + "', ajouter=" + ajouter
								+ ", modifier=" + modifier + ", archiver=" + archiver + ", activer=" + activer + ", supprimer=" + supprimer +
								",historique=" + historique + ",utilisateur=" + utilisateur + ",role=" + droitRole + " WHERE id_role=" + role.getId_role());
								
								MainMenu menu = new MainMenu();
								menu.set_activeTab(5);
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
							alert.setHeaderText("Le champs Nom du rôle n'est pas valide. ( Ex: Admin ) et/ou \nLa limite de charactère de 64 est dépassée.");
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
						menu.set_activeTab(5);
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
				
				setUpValidation(nomRole);

				lbl.setLayoutX(50);
				lbl.setLayoutY(70);
				nomRole.setLayoutX(200);
				nomRole.setLayoutY(70);

				lbl1.setLayoutX(50);
				lbl1.setLayoutY(120);
				cb1.setLayoutX(200);
				cb1.setLayoutY(120);
				
				lbl2.setLayoutX(50);
				lbl2.setLayoutY(170);
				cb2.setLayoutX(200);
				cb2.setLayoutY(170);
				
				lbl3.setLayoutX(50);
				lbl3.setLayoutY(220);
				cb3.setLayoutX(200);
				cb3.setLayoutY(220);
				
				lbl4.setLayoutX(50);
				lbl4.setLayoutY(270);
				cb4.setLayoutX(200);
				cb4.setLayoutY(270);

				lbl5.setLayoutX(50);
				lbl5.setLayoutY(300);
				cb5.setLayoutX(200);
				cb5.setLayoutY(300);
				
				lbl6.setLayoutX(50);
				lbl6.setLayoutY(320);
				cb6.setLayoutX(200);
				cb6.setLayoutY(320);
				
				lbl7.setLayoutX(50);
				lbl7.setLayoutY(340);
				cb7.setLayoutX(200);
				cb7.setLayoutY(340);
				
				lbl8.setLayoutX(50);
				lbl8.setLayoutY(360);
				cb8.setLayoutX(200);
				cb8.setLayoutY(360);
				
				// Bouton
				btnModifier.setLayoutX(50);
				btnModifier.setLayoutY(340);
				btnCancel.setLayoutX(250);
				btnCancel.setLayoutY(340);

				btnModifier.setMinHeight(30);
				btnModifier.setMinWidth(130);
				btnCancel.setMinHeight(30);
				btnCancel.setMinWidth(130);

				// add to panel
				root.getChildren().add(lbl);
				root.getChildren().add(lbl1);
				root.getChildren().add(lbl2);
				root.getChildren().add(nomRole);
				root.getChildren().add(cb1);
				root.getChildren().add(cb2);
				root.getChildren().add(cb3);
				root.getChildren().add(cb4);
				root.getChildren().add(cb5);
				root.getChildren().add(lbl5);
				root.getChildren().add(lbl3);
				root.getChildren().add(lbl4);
				root.getChildren().add(lbl6);
				root.getChildren().add(lbl7);
				root.getChildren().add(lbl8);
				root.getChildren().add(cb6);
				root.getChildren().add(cb7);
				root.getChildren().add(cb8);
				root.getChildren().add(btnModifier);
				root.getChildren().add(btnCancel);

				// create window
				Scene scene = new Scene(root, 450, 600);
				primaryStage.setTitle("Modifier Role");
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				primaryStage.setScene(scene);
				primaryStage.show();			

	}

	public void SupprimerRole(Role role) throws SQLException {
		Connection conn = SimpleDataSource.getConnection();
		try {
			Statement stat = conn.createStatement();

			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Message de confirmation");
			alert.setHeaderText("Êtes-vous sûr de vouloir supprimer ce rôle?\nCar ceci va supprimer tout les éléments relié à ce rôle dont les utilisateurs avec ce rôle.");
			// alert.setContentText("Are you ok with this?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {
				stat.execute("DELETE FROM role WHERE id_role=" + role.getId_role());
				stat.execute("DELETE FROM utilisateur WHERE fk_id_role=" + role.getId_role());
				
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
