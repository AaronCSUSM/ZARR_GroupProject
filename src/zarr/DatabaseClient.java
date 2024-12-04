package zarr;

//* indicates to include all packages from java.sql library.
import java.sql.*;
//Allows use of property class for holding settings like username and password.
import java.util.Properties;

/**
 * 
 * @author Aaron Cambridge, Rebecca Hall, River Hallie, Zach Miller
 * @version 12/4/24
 *
 */
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
		
		//creates the table when run first time
		createTable(dbConnector);
		
		
		
		// (name, country, region, lat, long)
		//starting data for testing and display purposes
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
		addCity("San Diego", "USA", "California", 32.72, -117.16);
		addCity("Amsterdam", "Netherlands", null, 52.37, 4.90);
		addCity("Athens", "Greece", null, 37.98, 23.73);
		addCity("Bern", "Switzerland", null, 46.95, 7.45);
		addCity("Manila", "Phillippines", null, 46.95, 7.45);
		addCity("Seoul", "South Korea", null, 37.56, 126.98);
		addCity("Houston", "USA", "Texas", 29.76, -95.37);
		addCity("Vancouver", "Canada", "British Columbia", 49.28, -123.12);
		addCity("Copenhagen", "Denmark", null, 55.67, 12.57);
		addCity("Rome", "Italy", null, 41.90, 12.48);
		addCity("Mexico City", "Mexico", null, 19.43, -99.13);
		addCity("Ottawa", "Canada", "Ontario", 45.42, -75.70);
		addCity("Sao Paulo", "Brazil", null, -23.56, -46.64);
		addCity("Johannesburg", "South Africa", null, -26.20, 28.03);
		addCity("Delhi", "India", null, 28.70, 77.10);
		
		dbConnector.close();//close connection to database
	}
	

	/**
	 * Method for creating a table using a Connection object as a parameter.
	 * @param dbConnector
	 * @throws SQLException
	 */
	// DECIMAL(total digits, digits after decimal)
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
		
		//create the statement and execute it
		Statement createTableQuerySTMT = dbConnector.createStatement();
		createTableQuerySTMT.executeUpdate(createTableSQLquery);
		
		createTableQuerySTMT.close();//close connection
	}
	
	/**
	 * Method adds cities to the database.
	 * @param newCity
	 * @param newCountry
	 * @param newState
	 * @param newLatitude
	 * @param newLongitude
	 * @throws SQLException
	 */
	
	//adds a new city, in hindsight should have used class object as a parameter for cleaner code
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
		
			//every input should have at least a city, country, and coordinates
			insertSTMT.setString(1, newCity);
			insertSTMT.setString(2, newCountry);
		
			//state is optional so could be null
			if(newState == null || newState.isEmpty()) {
				insertSTMT.setNull(3, Types.VARCHAR);
			}else {
				insertSTMT.setString(3, newState);
			}//end state if/else
		
			
			insertSTMT.setDouble(4, newLatitude);
			insertSTMT.setDouble(5, newLongitude);
		
			
			//execute the update query, adding a new city to the database
			insertSTMT.executeUpdate();
			System.out.println("New city added to database.");
		
			insertSTMT.close();//close the PreparedStatement object
		}//end if/else
		ps.close();
		rs.close();
		dbConnector.close();
	}//end addCity method
	
	/**
	 * Method fetches coordinates from cities within the database to be used for a future API call.
	 * @param city
	 * @param country
	 * @param state
	 * @return coordinates
	 * @throws SQLException
	 */
	//retrives coordinates for cities already in database
	public double[] getCoordinates(String city, String country, String state) throws SQLException {
		//create array to return coordinates
		double[] coordinates = new double[2];
		
		//set up the properties for the database connection
		Properties dbCredentials = new Properties();
		dbCredentials.put("user", "root");//store database user as root
		dbCredentials.put("password", "panacea123");//store database password as panacea123
		Connection dbConnector = DriverManager.getConnection(CONNECTION,dbCredentials);
		
		//attempts to retrieve the coordinates associates with the city, country, and state
		try {
			String query = "SELECT latitude, longitude FROM city_coordinates WHERE city = ? AND country = ? AND (state = ? OR state IS Null)";
			
			//create a preparedstatement to execute the query
			PreparedStatement ps = dbConnector.prepareStatement(query);
			
			//fill in query placeholders, avoids SQL injection attacks
			ps.setString(1, city);
			ps.setString(2,  country);
			ps.setString(3,  state);
			
			//execute query using prepared statement and assign the return to a resultset object
			ResultSet rs = ps.executeQuery();
			
			//should always have a result as database will be search before retrieving coordinates
			rs.next();
			//store double from latitude column
			coordinates[0] = rs.getDouble("latitude");
			//store double from longitude column into coordinates array
			coordinates[1] = rs.getDouble("longitude");
			
			//close connections
			rs.close();
			ps.close();
		}finally {
			dbConnector.close();
		}
		//return the array with the coordinates
		return coordinates;
	}
	
	/**
	 * Method searches through the database to see if the provided city is already present.<br> 
	 * This prevents unnecessary API calls if the data is already stored in database.
	 * 
	 * @param city name of city to be searched
	 * @param country name of country to be searched
	 * @param state name of state to be searched
	 * @return cityFound to be searched
	 * @throws SQLException
	 */
	//this method is called first when performing a search, 
	public boolean findCity(String city, String country, String state) throws SQLException{
		boolean cityFound = false;
		
		//set up the credentials for connection
		Properties dbCredentials = new Properties();
		dbCredentials.put("user",  "root");//store database user as root
		dbCredentials.put("password",  "panacea123"); //store database password as panacea123
		Connection dbConnector = DriverManager.getConnection(CONNECTION, dbCredentials);
		
		//put the query together
		try {
			String query = "SELECT * FROM city_coordinates WHERE "
					+ "city = ? AND country = ? AND (state = ? OR state IS Null)";
			
			//creates a prepared statement
			PreparedStatement ps = dbConnector.prepareStatement(query);
			
			//fill in the placeholders with the arguments
			ps.setString(1, city);
			ps.setString(2,  country);
			ps.setString(3,  state);
			
			//store query results in rs
			ResultSet rs = ps.executeQuery();
			
			//if there are results and city was in database, return true
			if(rs.next()) {
				cityFound = true;
			}
			
			//close connection
			rs.close();
			ps.close();
		}finally {
			if(dbConnector != null) {
				dbConnector.close();
			}
		}// end of try/finally
		//if city was not found, this will still be false
		return cityFound;
	}//end of search function
		
	
	/**
	 * @param target, city to be deleted
	 * @return boolean, whether city was deleted or not
	 * @throws SQLException
	 */
	public boolean deleteCity(String target) throws SQLException {
		//included for testing purposes, so don't have to check database
		boolean removed = false;
	
		//set up credentials for connection
		Properties dbCredentials = new Properties();
		dbCredentials.put("user",  "root");//store database user as root
		dbCredentials.put("password",  "panacea123"); //store database password as panacea123
		Connection dbConnector = DriverManager.getConnection(CONNECTION, dbCredentials);
		
		//store query in a string
		String query = "DELETE FROM city_coordinates WHERE city = ?";
			
		PreparedStatement ps = dbConnector.prepareStatement(query);
			
		//fill in placeholder with city being deleted
		ps.setString(1, target);
			
		//check how many rows got deleted, may delete more than one
		int deletedRows = ps.executeUpdate();
		
		//if there was at least one row deleted, return true
		if(deletedRows > 0) {
			removed = true;
		}
		
		//close connections
		ps.close();
		dbConnector.close();
		
		return removed;
	}
}