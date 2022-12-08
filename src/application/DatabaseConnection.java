package application;
import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {

	public Connection databaselink;
	
	public Connection getConnection() {
		String databasename="managedb";
		String databaseUser="root";
		String databasePassword="root@123";
		String url="jdbc:mysql://localhost/"+databasename;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
				databaselink = DriverManager.getConnection(url, databaseUser, databasePassword);
				      // display status message
				      if (databaselink == null) {
				         System.out.println("JDBC connection is not established");
				       
				      } else
				         System.out.println("Congratulations," + 
				              " JDBC connection is established successfully.\n");
			
		}catch(Exception e) {
			
		}
		
		return databaselink;
		
	}
	
}
