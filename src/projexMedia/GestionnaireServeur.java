package projexMedia;

import java.io.IOException;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
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
	
	public void createPane(Stage primaryStage) throws Exception{
		

		TableView<Serveur> tableClient = new TableView<Serveur>();
		TableColumn<Serveur, Integer> id = new TableColumn<Serveur, Integer>("id Serveur");
		TableColumn<Serveur, String> nom = new TableColumn<Serveur, String>("nom Serveur");
		//TableColumn<Serveur, String> tel = new TableColumn<Serveur, String>("Telephone");
		//TableColumn<Serveur, String> nomR = new TableColumn<Serveur, String>("nom Responsable");
		//TableColumn<Serveur, String> adresse = new TableColumn<Serveur, String>("adresse");
		//TableColumn<Serveur, String> courriel = new TableColumn<Serveur, String>("courriel");
		

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

		id.setCellValueFactory(new PropertyValueFactory<Serveur, Integer>("idServeur"));
		nom.setCellValueFactory(new PropertyValueFactory<Serveur, String>("nomServeur"));
		//tel.setCellValueFactory(new PropertyValueFactory<Client, String>("telephone"));
		//nomR.setCellValueFactory(new PropertyValueFactory<Client, String>("nomResponsable"));
		//adresse.setCellValueFactory(new PropertyValueFactory<Client, String>("adresse"));
		//courriel.setCellValueFactory(new PropertyValueFactory<Client, String>("courriel"));
		//tableClient.getColumns().addAll(id, nom/*, tel, nomR, adresse, courriel*/);

		// id.prefWidthProperty().bind(tableClient.widthProperty().divide(2));
		// nom.prefWidthProperty().bind(tableClient.widthProperty().divide(2));
		// tableClient.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tableClient.setItems(list);
		
		
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
				
			}
		});
		btnModifier.setText("Modifier");
		btnModifier.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				if (tableClient.getSelectionModel().getSelectedItem() != null) {
					

				}
				// modifierClient(primaryStage);
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
				
			}
		});
		
		tableClient.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				/*if (event.isPrimaryButtonDown() && event.getClickCount() == 2) {
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
				}*/
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
		tableClient.setLayoutX(200);
		tableClient.setLayoutY(60);

		//_pane.getChildren().add(tableClient);
		_pane.getChildren().add(btnAjouter);
		_pane.getChildren().add(btnModifier);
		_pane.getChildren().add(btnArchiver);
		_pane.getChildren().add(btnConsArch);
		_pane.getChildren().add(btnRecherche);
		_pane.getChildren().add(tfRecherche);
	}
}
