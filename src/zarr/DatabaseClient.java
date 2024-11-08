package zarr;

//* indicates to include all packages from java.sql library.
import java.sql.*;
//Allows use of property class for holding settings like username and password.
import java.util.Properties;

//Declares DatabaseManager class
public class DatabaseClient {
	
	//dbClassname: name of MySQL JBDC driver class. Used to establish connection to MySQL database.
	private static final String dbClassname = "com.mysql.cj.jdbc.Driver";
	
	//url of the MySQL server
	private static final String CONNECTION = "jdbc:mysql://127.0.0.1/zarr_db";
	
	public void initialize() throws ClassNotFoundException,SQLException
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
		
		
		//add your cities in here the same format I have
		//don't worry about region unless it is a state, then add the state, null for other countries
		//make sure you get coordinates from the Google Maps API, so we're not getting slightly different numbers from different sources
		//round them to two decimal places
		addCity("Paris", "France", null, 48.86, 2.35);
		addCity("Washington D.C.", "USA", null, 38.91, -77.04);
		addCity("London", "England", null, 51.51, -0.13);
		addCity("Vactican City", "Vatican City State", null, 41.90, 12.45);
		addCity("New York City", "USA", "New York", 40.71, -74.01);
		addCity("St. Petersburg", "Russia", null, 59.93, 30.36);
		addCity("Madrid", "Spain", null, 40.42, -3.70);
		addCity("Beijing", "China", null, 39.90, 116.41);
		addCity("Cairo", "Egypt", null, 30.04, 31.24);
		addCity("Tokyo", "Japan", null, 35.68, 139.65);
		
		
		dbConnector.close();//close connection to database
	}
	
	//function for creating a table using a Connection object as a parameter. 
	//Will throw an error if it fails.
	//DECIMAL(total digits, digits after decimal)
	public void createTable(Connection dbConnector) throws SQLException{
		//SQL queries stored as string variables
		//"CREATE TABLE IF NOT EXISTS": sql command to create table if doesn't exist
		//city_coordinates: name of table being created
		String createTableSQLquery = "CREATE TABLE IF NOT EXISTS city_coordinates (" +
				"id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
				"city VARCHAR(100) NOT NULL, " +
				"country VARCHAR(100) NOT NULL, " +
				"state VARCHAR(100) NULL, " +
				"latitude DECIMAL(8, 2) NOT NULL," +
				"longitude DECIMAL(9, 2) NOT NULL" +
				")";
		
		Statement createTableQuerySTMT = dbConnector.createStatement();
		createTableQuerySTMT.executeUpdate(createTableSQLquery);
		//System.out.println("It worked, probably.");
		
		createTableQuerySTMT.close();
	}
	
	public void addCity(String newCity, String newCountry, String newState, double newLatitude, double newLongitude) throws SQLException
	{
		//create a Connection object to access table
		Properties dbCredentials = new Properties();
		dbCredentials.put("user", "root");//store database user as root
		dbCredentials.put("password", "panacea123");//store database password as panacea123
		Connection dbConnector = DriverManager.getConnection(CONNECTION,dbCredentials);
		
		//first check if city is already in database using city, country, state
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
					"INSERT INTO city_coordinates (city, country, state, latitude, longitude)"
					+ "VALUES(?,?,?,?,?)");
		
		
			insertSTMT.setString(1, newCity);
			insertSTMT.setString(2, newCountry);
		
			if(newState == null || newState.isEmpty()) {
				insertSTMT.setNull(3, Types.VARCHAR);
			}else {
				insertSTMT.setString(3, newState);
			}//end state if/else
		
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
	
	public double[] getCoordinates(String city, String country, String state) throws SQLException {
		//create array to return coordinates
		double[] coordinates = new double[2];
		
		Properties dbCredentials = new Properties();
		dbCredentials.put("user", "root");//store database user as root
		dbCredentials.put("password", "panacea123");//store database password as panacea123
		Connection dbConnector = DriverManager.getConnection(CONNECTION,dbCredentials);
		
		try {
			String query = "SELECT latitude, longitude FROM city_coordinates WHERE city = ? AND country = ? AND (state = ? OR state IS Null)";
			
			PreparedStatement ps = dbConnector.prepareStatement(query);
			
			ps.setString(1, city);
			ps.setString(2,  country);
			ps.setString(3,  state);
			
			ResultSet rs = ps.executeQuery();
			
			rs.next();
			coordinates[0] = rs.getDouble("latitude");
			coordinates[1] = rs.getDouble("longitude");
			
			rs.close();
			ps.close();
		}finally {
			dbConnector.close();
		}
		return coordinates;
	}
	
	
	//search through database to see if city is already present using city, country, and state
	public boolean findCity(String city, String country, String state) throws SQLException{
		boolean cityFound = false;
		
		Properties dbCredentials = new Properties();
		dbCredentials.put("user",  "root");//store database user as root
		dbCredentials.put("password",  "panacea123"); //store database password as panacea123
		Connection dbConnector = DriverManager.getConnection(CONNECTION, dbCredentials);
		
		
		try {
			String query = "SELECT * FROM city_coordinates WHERE "
					+ "city = ? AND country = ? AND (state = ? OR state IS Null)";
			
			PreparedStatement ps = dbConnector.prepareStatement(query);
			
			ps.setString(1, city);
			ps.setString(2,  country);
			ps.setString(3,  state);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				cityFound = true;
			}
			
			rs.close();
			ps.close();
		}finally {
			if(dbConnector != null) {
				dbConnector.close();
			}
		}// end of try/finally
		return cityFound;
	}//end of search function
		

}