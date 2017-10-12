/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projexMedia;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author houdeto
 */
public class Site {
    
    private SimpleIntegerProperty idSite;
    private SimpleStringProperty url;
    private SimpleStringProperty client;

    public Site() {
        idSite = new SimpleIntegerProperty(0);
        url = new SimpleStringProperty(null);
        client = new SimpleStringProperty(null);
    }
    
    public Site(Integer _idSite, String _url,String _client) {
        idSite = new SimpleIntegerProperty(_idSite);
        url = new SimpleStringProperty(_url);
        client = new SimpleStringProperty(_client);
    }
    
    public Integer getIdSite() {
        return idSite.get();
    }

    public String getUrl() {
        return url.get();
    }

    public void setIdSite(Integer _idSite) {
    	idSite = new SimpleIntegerProperty(_idSite);
    }

    public void setUrl(String _url) {
    	url = new SimpleStringProperty(_url);
    }
    
    public String getClient() {
		return client.get();
	}

	public void setClient(String _client) {
		client = new SimpleStringProperty(_client);
	}

//	public ObservableList<Site> consulterSite() throws SQLException, ClassNotFoundException, IOException {
//    	
//    	SimpleDataSource.init("src/projexMedia/database.properties");
//
//        Connection conn = SimpleDataSource.getConnection();
//        
//        ObservableList<Site> data = FXCollections.observableArrayList();
//
//        try {
//            Statement stat = conn.createStatement();
//
//            ResultSet result = stat.executeQuery("SELECT * FROM site");
//            
//            while(result.next()) {
//            	data.add(new Site(result.getInt("id_site"), result.getString("url")));
//            }
//        } finally {
//            conn.close();
//        }
//        return data;
//    }
}
