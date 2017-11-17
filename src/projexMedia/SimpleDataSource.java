package projexMedia;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class SimpleDataSource {
	private static String url = "jdbc:mysql://127.0.0.1/projexmediabd";
	private static String username= "root";
	private static String password = "";
	
	public static void init(String fileName)
	throws IOException, ClassNotFoundException
	 {
		/*Properties props = new Properties();
		FileInputStream in = new FileInputStream(fileName);
		props.load(in);
		
		String driver = props.getProperty("jdbc.driver");
		url = props.getProperty("jdbc.url");
		username = props.getProperty("jdbc.username");
		if (username == null) username = "";
		password = props.getProperty("jdbc.password");
		if (password == null) password = "";
		if (driver != null)
		Class.forName(driver);*/
	 }
	
	public static Connection getConnection() throws SQLException
	{
		return DriverManager.getConnection(url, username, password);
	}


}
