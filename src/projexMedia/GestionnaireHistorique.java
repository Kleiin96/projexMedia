package projexMedia;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.sql.Statement;

public class GestionnaireHistorique {
	Tab _historiqueTab;
	Pane _pane;
	
	public ObservableList<Historiques> list = FXCollections.observableArrayList();
	
	
	
	
	
	public GestionnaireHistorique() {
		_historiqueTab = new Tab("Historique");
		_pane = new Pane();
	}
	public GestionnaireHistorique(Tab _historiqueTab, Pane _pane) {
		
		this._historiqueTab = _historiqueTab;
		this._pane = _pane;
	}
	public Tab get_historiqueTab() {
		return _historiqueTab;
	}
	public void set_historiqueTab(Tab _historiqueTab) {
		this._historiqueTab = _historiqueTab;
	}
	public Pane get_pane() {
		return _pane;
	}
	public void set_pane(Pane _pane) {
		this._pane = _pane;
	}
	
	
	public void createPane(Stage primaryStage) throws Exception {
		createListHisotrique();
		
		TextField tfRecherche = new TextField();
		TextField tfRechercheT = new TextField();
		TextField tfRechercheP = new TextField();
		Button btnR =  new Button();
		
		ObservableList<String> histo = FXCollections.observableArrayList("Aujourd'hui", "Semaine", "Mois", "Année", "Toutes");
		ComboBox<String> cbDate = new ComboBox<String>(histo);
		cbDate.setValue("Semaine");
		
		
		
		TableView<Historiques> logTable = new TableView<Historiques>();
		TableColumn<Historiques,String> url = new TableColumn<Historiques, String>("Url");
		TableColumn<Historiques, String> type = new TableColumn<Historiques, String>("Type de service");
		TableColumn<Historiques, String> par = new TableColumn<Historiques, String>("parametre");
		TableColumn<Historiques, String> valeur = new TableColumn<Historiques, String>("valeur");
		TableColumn<Historiques, String> date = new TableColumn<Historiques, String>("date");
		TableColumn<Historiques, String> action = new TableColumn<Historiques, String>("action");
		TableColumn<Historiques, String> utilisateur = new TableColumn<Historiques, String>("utilisateur");
		
		
		logTable.setEditable(false);
		logTable.setPrefSize(725, 525);

		url.setCellValueFactory(new PropertyValueFactory<Historiques, String>("url"));
		type.setCellValueFactory(new PropertyValueFactory<Historiques, String>("type"));
		par.setCellValueFactory(new PropertyValueFactory<Historiques, String>("parametre"));
		valeur.setCellValueFactory(new PropertyValueFactory<Historiques, String>("valeur"));
		date.setCellValueFactory(new PropertyValueFactory<Historiques, String>("date"));
		action.setCellValueFactory(new PropertyValueFactory<Historiques, String>("action"));
		utilisateur.setCellValueFactory(new PropertyValueFactory<Historiques, String>("utilisateur"));
		
		logTable.getColumns().addAll(url, type, par,valeur, date,action, utilisateur);

		
		url.prefWidthProperty().bind(logTable.widthProperty().divide(2));
		type.prefWidthProperty().bind(logTable.widthProperty().divide(2));
		logTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		logTable.setItems(list);
		
		
		cbDate.valueProperty().addListener(new ChangeListener<String>() {
	        @Override public void changed(ObservableValue ov, String t, String t1) {
	            try {
					rechercheDateHistoriques(t1);
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
					rechercheHistorique(tfRecherche.getText(), cbDate.getValue(),tfRechercheT.getText(), tfRechercheP.getText());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		
		tfRechercheT.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				try {
					rechercheHistorique(tfRecherche.getText(), cbDate.getValue(),tfRechercheT.getText(), tfRechercheP.getText());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		
		tfRechercheP.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				
				try {
					rechercheHistorique(tfRecherche.getText(), cbDate.getValue(),tfRechercheT.getText(), tfRechercheP.getText());
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		
		btnR.setText("Rechercher");
		btnR.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				try {
					rechercheHistorique(tfRecherche.getText(), cbDate.getValue(),tfRechercheT.getText(), tfRechercheP.getText() );
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		logTable.setOnMousePressed(new EventHandler<MouseEvent>() {
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
						service.afficherService(primaryStage, logTable.getSelectionModel().getSelectedItem().getIdSite());
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
		
		Label lblU = new Label("URL :");
		Label lblT = new Label("Type Service :");
		Label lblP = new Label("Parametre Service :");
		
		cbDate.setLayoutX(10);
		cbDate.setLayoutY(30);
		lblU.setLayoutX(150);
		lblU.setLayoutY(10);
		tfRecherche.setLayoutX(150);
		tfRecherche.setLayoutY(30);
		lblT.setLayoutX(330);
		lblT.setLayoutY(10);
		tfRechercheT.setLayoutX(330);
		tfRechercheT.setLayoutY(30);
		lblP.setLayoutX(510);
		lblP.setLayoutY(10);
		tfRechercheP.setLayoutX(510);
		tfRechercheP.setLayoutY(30);
		btnR.setLayoutX(680);
		btnR.setLayoutY(30);
		logTable.setLayoutX(10);
		logTable.setLayoutY(60);
		
		

		
		
		_pane.getChildren().add(cbDate);
		_pane.getChildren().add(lblU);
		_pane.getChildren().add(lblT);
		_pane.getChildren().add(lblP);
		_pane.getChildren().add(tfRecherche);
		_pane.getChildren().add(tfRechercheT);
		_pane.getChildren().add(tfRechercheP);
		_pane.getChildren().add(btnR);
		_pane.getChildren().add(logTable);
		
		
	}
	
	public void createListHisotrique() throws SQLException {
		list.clear();
		Connection conn = SimpleDataSource.getConnection();

		try {
			Statement stat = conn.createStatement();

			ResultSet result = stat.executeQuery("SELECT s.url, e.nom_type,p.nom_parametre,v.valeur, v.date, v.action,v.fk_courriel, v.fk_id_site\r\n" + 
					"FROM `valeurparametre` v join site s ON v.fk_id_site = s.id_site \r\n" + 
					"JOIN ta_service t ON v.fk_id_service = t.id_service\r\n" + 
					"JOIN typeservice e ON t.fk_id_typeService = e.id_typeService\r\n" + 
					"JOIN parametreservice p ON t.fk_id_parametreService = p.id_parametreService\r\n" + 
					"WHERE date > CURRENT_DATE - INTERVAL '1' WEEK" + " ORDER BY date DESC, action DESC");

			while (result.next()) {

				
				list.add(new Historiques(result.getString("url"), result.getString("nom_type"),
						result.getString("nom_parametre"), result.getString("valeur"),
						result.getString("date"), result.getString("action"),result.getString("fk_courriel") , result.getInt("fk_id_site")));

			}
		}

		finally {
			conn.close();
		}
	}
	
	
	public void rechercheDateHistoriques(String date) throws SQLException{
		list.clear();
		
		String inter;
		
		if(date == "Aujourd'hui") {
			
			inter = "WHERE date = CURRENT_DATE";
			
		}else if(date == "Semaine"){
			
			inter = "WHERE date > CURRENT_DATE - INTERVAL '1' WEEK";
			
		}else if(date == "Mois") {
			
			inter = "WHERE date > CURRENT_DATE - INTERVAL '1' MONTH";
			
		}else if(date == "Année") {
			
			inter = "WHERE date > CURRENT_DATE - INTERVAL '1' YEAR";
			
		}else {
			
			inter = "";
			
		}
		
		
		
		Connection conn = SimpleDataSource.getConnection();

		try {
			Statement stat = conn.createStatement();

			ResultSet result = stat.executeQuery("SELECT s.url, e.nom_type,p.nom_parametre,v.valeur, v.date, v.action,v.fk_courriel, v.fk_id_site\r\n" + 
					"FROM `valeurparametre` v join site s ON v.fk_id_site = s.id_site \r\n" + 
					"JOIN ta_service t ON v.fk_id_service = t.id_service\r\n" + 
					"JOIN typeservice e ON t.fk_id_typeService = e.id_typeService\r\n" + 
					"JOIN parametreservice p ON t.fk_id_parametreService = p.id_parametreService\r\n" + 
					inter+ " ORDER BY date DESC, action DESC");

			while (result.next()) {

				
				list.add(new Historiques(result.getString("url"), result.getString("nom_type"),
						result.getString("nom_parametre"), result.getString("valeur"),
						result.getString("date"), result.getString("action"), result.getString("fk_courriel"),result.getInt("fk_id_site")));

			}
		}

		finally {
			conn.close();
		}
	}
	
	public void rechercheHistorique(String reche, String date, String type, String par )throws SQLException{
		list.clear();
		
		String inter;
		
		if(date == "Aujourd'hui") {
			
			inter = "WHERE date = CURRENT_DATE";
			
		}else if(date == "Semaine"){
			
			inter = "WHERE date > CURRENT_DATE - INTERVAL '1' WEEK";
			
		}else if(date == "Mois") {
			
			inter = "WHERE date > CURRENT_DATE - INTERVAL '1' MONTH";
			
		}else if(date == "Année") {
			
			inter = "WHERE date > CURRENT_DATE - INTERVAL '1' YEAR";
			
		}else {
			
			inter = "";
			
		}
		
		String rech = "";
		
		if(type.equals("") && par.equals("")) {
			
			rech =" and s.url LIKE '%" + reche  +"%' ";
		}else if(type.equals("")) {
			
			rech =" and s.url LIKE '%" + reche  +"%'  and p.nom_parametre LIKE '%" + par  +"%'  ";
		}else if(par.equals("")) {
			
			rech =" and s.url LIKE '%" + reche  +"%'  and e.nom_type LIKE '%" + type  +"%'  ";
			
		}else if(reche.equals("")){
			
			rech =" and e.nom_type LIKE '%" + type  +"%'  and p.nom_parametre LIKE '%" + par  +"%'  ";
		}else {
		
			
			rech =" and s.url LIKE '%" + reche  +"%'  and e.nom_type LIKE '%" + type  +"%' and p.nom_parametre LIKE '%" + par  +"%'  ";
		}
		
		Connection conn = SimpleDataSource.getConnection();

		try {
			Statement stat = conn.createStatement();

			ResultSet result = stat.executeQuery("SELECT s.url, e.nom_type,p.nom_parametre,v.valeur, v.date, v.action,  fk_courriel, v.fk_id_site\r\n" + 
					"FROM `valeurparametre` v join site s ON v.fk_id_site = s.id_site \r\n" + 
					"JOIN ta_service t ON v.fk_id_service = t.id_service\r\n" + 
					"JOIN typeservice e ON t.fk_id_typeService = e.id_typeService\r\n" + 
					"JOIN parametreservice p ON t.fk_id_parametreService = p.id_parametreService\r\n" + 
					inter + rech + " ORDER BY date DESC, action DESC" );

			while (result.next()) {

				
				list.add(new Historiques(result.getString("url"), result.getString("nom_type"),
						result.getString("nom_parametre"), result.getString("valeur"),
						result.getString("date"), result.getString("action"), result.getString("fk_courriel"), result.getInt("fk_id_site")));

			}
		}

		finally {
			conn.close();
		}
	}
	
	
	
}
