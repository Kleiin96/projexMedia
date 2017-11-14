package projexMedia;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GestionnaireRole {
	private Tab _RoleTab;
	private Pane _RolePane;
	public String _username;
	

	private ObservableList<Role> _data;

	public GestionnaireRole(String username) {
		_RoleTab = new Tab("Role");
		_RolePane = new Pane();
		_username = username;
	}

	@SuppressWarnings({ "unchecked"})
	public void createPane(Stage primaryStage) throws Exception {

		TableView<Role> _table = new TableView<Role>();
		TableColumn<Role, String> Col = new TableColumn<Role, String>("Nom du r�le");
		TableColumn<Role, String> Col2 = new TableColumn<Role, String>("Ajouter");
		TableColumn<Role, String> Col3 = new TableColumn<Role, String>("Modifier");
		TableColumn<Role, String> Col4 = new TableColumn<Role, String>("Archiver");
		TableColumn<Role, String> Col5 = new TableColumn<Role, String>("Activer");
		TableColumn<Role, String> Col6 = new TableColumn<Role, String>("Supprimer");

		SimpleDataSource.init("src/projexMedia/database.properties");

		Connection conn = SimpleDataSource.getConnection();

		_data = FXCollections.observableArrayList();

		try {
			Statement stat = conn.createStatement();

			ResultSet result = stat.executeQuery(
					"SELECT * FROM role ");

			while (result.next()) {
				_data.add(new Role(result.getInt("id_role"), result.getString("nom_role"), result.getBoolean("ajouter"),result.getBoolean("modifier"), result.getBoolean("archiver"), result.getBoolean("activer"), result.getBoolean("supprimer")));
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
		_table.getColumns().addAll(Col, Col2, Col3, Col4, Col5, Col6);
		_table.setItems(_data);
		Col.prefWidthProperty().bind(_table.widthProperty().divide(6));
		Col2.prefWidthProperty().bind(_table.widthProperty().divide(6));
		Col3.prefWidthProperty().bind(_table.widthProperty().divide(6));
		Col4.prefWidthProperty().bind(_table.widthProperty().divide(6));
		Col5.prefWidthProperty().bind(_table.widthProperty().divide(6));
		Col6.prefWidthProperty().bind(_table.widthProperty().divide(6));
		_table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		Col.getStyleClass().add("custom-align");
		Col2.getStyleClass().add("custom-align");
		Col3.getStyleClass().add("custom-align");
		Col4.getStyleClass().add("custom-align");
		Col5.getStyleClass().add("custom-align");
		Col6.getStyleClass().add("custom-align");

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
							_data.add(new Role(result.getInt("id_role"), result.getString("nom_role"), result.getBoolean("ajouter"),result.getBoolean("modifier"), result.getBoolean("archiver"), result.getBoolean("activer"), result.getBoolean("supprimer")));
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
		

		// label
		Label lbl = new Label("Nom du r�le :");
		Label lbl1 = new Label("Ajouter :");
		Label lbl2 = new Label("Modifier :");
		Label lbl3 = new Label("Archiver :");
		Label lbl4 = new Label("Activer :");
		Label lbl5 = new Label("Supprimer :");

		btnAjouter.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				Connection conn = null;
				try {
					conn = SimpleDataSource.getConnection();
					Statement stat = conn.createStatement();
	
					int ajouter = (cb1.isSelected()) ? 1 : 0;
					int modifier = (cb2.isSelected()) ? 1 : 0;
					int archiver = (cb3.isSelected()) ? 1 : 0;
					int activer = (cb4.isSelected()) ? 1 : 0;
					int supprimer = (cb5.isSelected()) ? 1 : 0;
	
					stat.execute("INSERT INTO Role (nom_role, ajouter, modifier, archiver, activer, supprimer) VALUES(\""
							+ nomRole.getText() + "\"" + "," + ajouter + "," + modifier + "," + archiver + "," + activer + "," + supprimer + ")");
	
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
		
		// Bouton
		btnAjouter.setLayoutX(50);
		btnAjouter.setLayoutY(340);
		btnCancel.setLayoutX(250);
		btnCancel.setLayoutY(340);

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
		root.getChildren().add(btnAjouter);
		root.getChildren().add(btnCancel);

		// create window
		Scene scene = new Scene(root, 450, 400);
		primaryStage.setTitle("Ajouter Role");
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
				

				// label
				Label lbl = new Label("Nom du r�le :");
				Label lbl1 = new Label("Ajouter :");
				Label lbl2 = new Label("Modifier :");
				Label lbl3 = new Label("Archiver :");
				Label lbl4 = new Label("Activer :");
				Label lbl5 = new Label("Supprimer :");
				
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
					
					
				} finally {
					conn.close();
				}

				btnModifier.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {

						Connection conn = null;
						try {
							conn = SimpleDataSource.getConnection();
							Statement stat = conn.createStatement();
			
							int ajouter = (cb1.isSelected()) ? 1 : 0;
							int modifier = (cb2.isSelected()) ? 1 : 0;
							int archiver = (cb3.isSelected()) ? 1 : 0;
							int activer = (cb4.isSelected()) ? 1 : 0;
							int supprimer = (cb5.isSelected()) ? 1 : 0;
			
							stat.execute("UPDATE role SET nom_role='" + nomRole.getText() + "', ajouter=" + ajouter
							+ ", modifier=" + modifier + ", archiver=" + archiver + ", activer=" + activer + ", supprimer=" + supprimer +
							" WHERE id_role=" + role.getId_role());
							
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
				root.getChildren().add(btnModifier);
				root.getChildren().add(btnCancel);

				// create window
				Scene scene = new Scene(root, 450, 400);
				primaryStage.setTitle("Ajouter Role");
				primaryStage.setScene(scene);
				primaryStage.show();			

	}

	public void SupprimerRole(Role role) throws SQLException {
		Connection conn = SimpleDataSource.getConnection();
		try {
			Statement stat = conn.createStatement();

			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Message de confirmation");
			alert.setHeaderText("�tes-vous s�r de vouloir supprimer ce r�le?");
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
}
