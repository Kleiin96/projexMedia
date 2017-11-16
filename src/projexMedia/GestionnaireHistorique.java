package projexMedia;

import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GestionnaireHistorique {
	Tab _historiqueTab;
	Pane _pane;
	
	
	
	
	
	
	
	public GestionnaireHistorique() {
		
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
		
		
		TableView<TypeService> logTable = new TableView<TypeService>();
		TableColumn<TypeService,Integer> id = new TableColumn<TypeService, Integer>("date");
		TableColumn<TypeService, String> nom = new TableColumn<TypeService, String>("changement");
		TableColumn<TypeService, String> tel = new TableColumn<TypeService, String>("utilisateur");
		
		
		logTable.setEditable(false);
		logTable.setPrefSize(525, 525);

		id.setCellValueFactory(new PropertyValueFactory<TypeService, Integer>("idTypeService"));
		nom.setCellValueFactory(new PropertyValueFactory<TypeService, String>("nomType"));
		tel.setCellValueFactory(new PropertyValueFactory<TypeService, String>("description"));
		
		logTable.getColumns().addAll(id, nom, tel);

		id.prefWidthProperty().bind(logTable.widthProperty().divide(2));
		nom.prefWidthProperty().bind(logTable.widthProperty().divide(2));
		logTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
		//logTable.setItems(list);
		
		
		logTable.setLayoutX(200);
		logTable.setLayoutY(60);
		

		
		
		
		_pane.getChildren().add(logTable);
		
		
	}
	
	
	
}
