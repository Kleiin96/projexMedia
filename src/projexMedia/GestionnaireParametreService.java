package projexMedia;

import java.io.IOException;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GestionnaireParametreService {
	ParametreService _test;
	Tab _parservTab;
	Pane _pane;

	public ObservableList<String> list = FXCollections.observableArrayList();
	
	
	public GestionnaireParametreService() {
		_test = new ParametreService();
		_parservTab = new Tab("Serveur");
		_pane = new Pane();
	}


	public ParametreService get_test() {
		return _test;
	}


	public void set_test(ParametreService _test) {
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


	public ObservableList<String> getList() {
		return list;
	}


	public void setList(ObservableList<String> list) {
		this.list = list;
	}
	
	//Création du pane pour cette interface
	public void createPane(Stage primaryStage) {
		TableView<String> tableParametre = new TableView<String>();
		TableColumn<String,Integer> id = new TableColumn<String, Integer>("id type de service");
		TableColumn<String, String> nom = new TableColumn<String, String>("nom type de service");
		//TableColumn<Serveur, String> tel = new TableColumn<Serveur, String>("Telephone");
		//TableColumn<Serveur, String> nomR = new TableColumn<Serveur, String>("nom Responsable");
		//TableColumn<Serveur, String> adresse = new TableColumn<Serveur, String>("adresse");
		//TableColumn<Serveur, String> courriel = new TableColumn<Serveur, String>("courriel");
		

		Button btnAjouter = new Button();
		Button btnModifier = new Button();
		Button btnArchiver = new Button();
		Button btnConsArch = new Button();
		//Image imageRecherche = new Image(getClass().getResourceAsStream("loupe.png"));
		//Button btnRecherche = new Button();
		//ImageView image = new ImageView(imageRecherche);

		//btnRecherche.setGraphic(image);
		
		//TextField tfRecherche = new TextField();

		// construction de la table
		tableParametre.setEditable(false);
		tableParametre.setPrefSize(525, 525);

		id.setCellValueFactory(new PropertyValueFactory<String, Integer>("idServeur"));
		nom.setCellValueFactory(new PropertyValueFactory<String, String>("nomServeur"));
		//tel.setCellValueFactory(new PropertyValueFactory<Client, String>("telephone"));
		//nomR.setCellValueFactory(new PropertyValueFactory<Client, String>("nomResponsable"));
		//adresse.setCellValueFactory(new PropertyValueFactory<Client, String>("adresse"));
		//courriel.setCellValueFactory(new PropertyValueFactory<Client, String>("courriel"));
		tableParametre.getColumns().addAll(id, nom/*, tel, nomR, adresse, courriel*/);

		id.prefWidthProperty().bind(tableParametre.widthProperty().divide(2));
		nom.prefWidthProperty().bind(tableParametre.widthProperty().divide(2));
		tableParametre.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		tableParametre.setItems(list);
		
		
		/*btnRecherche.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
			}
		});
		
		tfRecherche.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
			}
		});*/

		btnAjouter.setText("Ajouter");
		btnAjouter.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				//ajouterServeur(primaryStage);
			}
		});
		btnModifier.setText("Modifier");
		btnModifier.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {

				if (tableParametre.getSelectionModel().getSelectedItem() != null) {
					
					//modifierServeur(primaryStage, tableParametre.getSelectionModel().getSelectedItem());
				}
				
			}

		});
		btnArchiver.setText("Archiver");
		btnArchiver.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
					
					try {
						//tableParametre.getSelectionModel().getSelectedItem().archiverServeur();
						MainMenu test = new MainMenu();
						test.start(primaryStage);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				
				
			}
		});

		btnConsArch.setText("Consulter Archives");
		btnConsArch.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					//afficherArchive(primaryStage);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		tableParametre.setOnMousePressed(new EventHandler<MouseEvent>() {
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
		/*tfRecherche.setLayoutX(500);
		tfRecherche.setLayoutY(10);
		btnRecherche.setLayoutX(690);
		btnRecherche.setLayoutY(10);*/

		btnAjouter.setMinHeight(50);
		btnAjouter.setMinWidth(150);
		btnModifier.setMinHeight(50);
		btnModifier.setMinWidth(150);
		btnArchiver.setMinHeight(50);
		btnArchiver.setMinWidth(150);
		btnConsArch.setMinHeight(50);
		btnConsArch.setMinWidth(150);
		//btnRecherche.setPadding(Insets.EMPTY);
		//TextFields.bindAutoCompletion(tfRecherche, possibleClient);

		// layout list
		tableParametre.setLayoutX(200);
		tableParametre.setLayoutY(60);

		_pane.getChildren().add(tableParametre);
		_pane.getChildren().add(btnAjouter);
		_pane.getChildren().add(btnModifier);
		_pane.getChildren().add(btnArchiver);
		_pane.getChildren().add(btnConsArch);
		/*_pane.getChildren().add(btnRecherche);
		_pane.getChildren().add(tfRecherche);*/
	}
	
	public void ajouterParametre(Stage primaryStage) {
		
	}
}
