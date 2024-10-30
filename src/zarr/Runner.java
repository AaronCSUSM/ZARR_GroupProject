package zarr;

import java.sql.SQLException;

public class Runner {

	public static void main(String[] args) {
		DatabaseClient db = new DatabaseClient();
		//README: run this the first time to initialize database after adding your cities to DatabaseClient class
		try {
			db.initialize();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
//		boolean CityExists = false;
//		try {
//			CityExists = db.findCity("New York City", "USA", null);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		System.out.println(CityExists);
		
		double[] coordinates = new double[2];
		try {
			coordinates = db.getCoordinates("Tokyo", "Japan", null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println(coordinates[0]);
		System.out.println(coordinates[1]);
		
	}

}
