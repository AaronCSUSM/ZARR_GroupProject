package zarr;

import java.sql.SQLException;

public class Runner {

	public static void main(String[] args) {
		GUI guiclient = new GUI();
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
			coordinates = db.getCoordinates("Paris", "France", null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Database coordinates retrieval.");
		System.out.println(coordinates[0]);
		System.out.println(coordinates[1]);
		
		APIClient ac = new APIClient();
		try {
			coordinates = ac.geocodingRequest("San Marcos", "USA", "CA");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Geocoding API coordinates retrieval.");
		System.out.println(coordinates[0]);
		System.out.println(coordinates[1]);
		
		LocDetails[] locArr = new LocDetails[20];
		String category = "museums";
		
		try {
			locArr = ac.categoryRequest(51.51, -0.13, category);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		LocDetails.printLocDetails(locArr);
		
		
	}

}
