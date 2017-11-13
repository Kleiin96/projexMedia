package projexMedia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class moteurSQL {
	
	public void ajouterClient(Client client) throws SQLException{
    	Connection conn = SimpleDataSource.getConnection();
    	try
    	{
    		PreparedStatement stat = conn.prepareStatement("INSERT INTO `client`(`nom_compagnie`, `telephone`, `adresse`, `personne_responsable`, `courriel`, `actif`) "
    				+ "VALUES (?,?,?,?,?,1)");
            stat.setString(1, client.getNomCompagnie());
            stat.setString(2, client.getTelephone());
            stat.setString(3, client.getAdresse());
            stat.setString(4, client.getNomResponsable());
            stat.setString(5, client.getCourriel());
            
            stat.executeUpdate();
    	}
    	finally {
    		conn.close();
    	}
    	
    	
    }
	
}
