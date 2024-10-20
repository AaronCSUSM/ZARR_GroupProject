package zarr;

//* indicates to include all packages from java.sql library.
import java.sql.*;
//Allows use of property class for holding settings like username and password.
import java.util.Properties;

//Declares DatabaseManager class
public class ZARR_DB {
	
	//dbClassname: name of MySQL JBDC driver class. Used to establish connection to MySQL database.
	private static final String dbClassname = "com.mysql.cj.jdbc.Driver";
	
	//url of the MySQL server
	private static final String CONNECTION = "jdbc:mysql://127.0.0.1/zarr_db";
	
	public static void main(String[] args) throws ClassNotFoundException,SQLException
	{
		//properties object used to store username and password
		Properties dbCredentials = new Properties();
		dbCredentials.put("user", "root");//store database user as root
		dbCredentials.put("password", "panacea123");//store database password as panacea123
		
		//Create connector object to database using (database url, dbcredentials) as parameters
		Connection dbConnector = DriverManager.getConnection(CONNECTION,dbCredentials);
		Statement dbStatement = dbConnector.createStatement();
		System.out.println("It works. Connected");
		
		createTable(dbConnector);
		
		addCity("Paris", "France", "Ile-de-France", 48.85, 2.35);
		addCity("Washington D.C.", "USA", "Null", 38.90, -77.03);
		addCity("London", "England", "Greater London", 51.50, -0.12);
		addCity("Vactican City", "Vatican City State", "Null", 41.90, 12.45);
		addCity("New York City", "USA", "New York", 40.71, -74.00);
		addCity("St. Petersburg", "Russia", "Null", 59.93, 30.36);
		addCity("Madrid", "Spain", "Central Spain", 40.41, -3.70);
		addCity("Beijing", "China", "North China", 39.90, 116.40);
		addCity("Cairo", "Egypt", "Northweastern", 30.04, 31.23);
		addCity("Tokyo", "Japan", "Kanto", 35.67, 139.65);
		
		dbConnector.close();//close connection to database
	}
	
	//function for creating a table using a Connection object as a parameter. 
	//Will throw an error if it fails.
	//DECIMAL(total digits, digits after decimal)
	public static void createTable(Connection dbConnector) throws SQLException{
		//SQL queries stored as string variables
		//"CREATE TABLE IF NOT EXISTS": sql command to create table if doesn't exist
		//city_coordinates: name of table being created
		String createTableSQLquery = "CREATE TABLE IF NOT EXISTS city_coordinates (" +
				"id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
				"city VARCHAR(100) NOT NULL, " +
				"country VARCHAR(100) NOT NULL, " +
				"region VARCHAR(100) NULL, " +
				"latitude DECIMAL(8, 2) NOT NULL," +
				"longitude DECIMAL(9, 2) NOT NULL" +
				")";
		
		Statement createTableQuerySTMT = dbConnector.createStatement();
		createTableQuerySTMT.executeUpdate(createTableSQLquery);
		//System.out.println("It worked, probably.");
		
		createTableQuerySTMT.close();
	}
	
	public static void addCity(String newCity, String newCountry, String newRegion, double newLatitude, double newLongitude) throws SQLException
	{
		//create a Connection object to access table
		Properties dbCredentials = new Properties();
		dbCredentials.put("user", "root");//store database user as root
		dbCredentials.put("password", "panacea123");//store database password as panacea123
		Connection dbConnector = DriverManager.getConnection(CONNECTION,dbCredentials);
		
		//first check if city is already in database using city, country, region
		//The ?'s act as placeholders; first, second,
		PreparedStatement ps = dbConnector.prepareStatement("SELECT * FROM city_coordinates WHERE "
				+ "latitude = ? AND longitude = ?");
				
		ps.setDouble(1, newLatitude);//bind latitude to first placeholder
		ps.setDouble(2, newLongitude);//bind longitude to second placeholder
		
		//execute query to check if it exists or not
		ResultSet rs = ps.executeQuery();//rs holds result of query, will be empty if no match found
		
		//rs.next() return true/false. False if no match found, true if match found
		if(rs.next()) {
			System.out.println("City already in database.");
		}else {
			PreparedStatement insertSTMT = dbConnector.prepareStatement(
					"INSERT INTO city_coordinates (city, country, region, latitude, longitude)"
					+ "VALUES(?,?,?,?,?)");
		
		
			insertSTMT.setString(1, newCity);
			insertSTMT.setString(2, newCountry);
		
			if(newRegion == null || newRegion.isEmpty()) {
				insertSTMT.setNull(3, Types.VARCHAR);
			}else {
				insertSTMT.setString(3, newRegion);
			}//end region if/else
		
			insertSTMT.setDouble(4, newLatitude);
			insertSTMT.setDouble(5, newLongitude);
		
		
			insertSTMT.executeUpdate();
			System.out.println("New city added to database.");
		
			insertSTMT.close();
		}//end if/else
		ps.close();
		rs.close();
		dbConnector.close();
	}//end addCity method
	
	public static void findCoordinates(String city, String country, String region) throws SQLException {
		//create a Connection object to access table
		Properties dbCredentials = new Properties();
		dbCredentials.put("user", "root");//store database user as root
		dbCredentials.put("password", "panacea123");//store database password as panacea123
		Connection dbConnector = DriverManager.getConnection(CONNECTION,dbCredentials);
				
		//first check if city is already in database using city, country, region
		//The ?'s act as placeholders; first, second,
		PreparedStatement ps = dbConnector.prepareStatement("SELECT * FROM city_coordinates WHERE "
				+ "city = ? AND country = ? AND (region = ? OR region IS Null)");	
	}
		

}