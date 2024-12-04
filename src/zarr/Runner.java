package zarr;

import java.sql.SQLException;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * 
 * @author Aaron Cambridge, Rebecca Hall, River Hallie, Zach Miller
 * @version 12/2/24
 *
 */
//runner class is entry point of the program, gets everything running
public class Runner {
	
	/**
	 * Main Method to run the program.
	 * @param args The command line arguments
	 */
	public static void main(String[] args) {
		 APIClient ac = new APIClient();//object for interacting with API
	     DatabaseClient dc = new DatabaseClient();//object for interacting with database

	     // Creating GUI with required clients
	     GUI guiclient = new GUI(ac, dc);
	}//end main
}//end runner

//
//try {
//    dc.deleteCity("San Mcos");
//} catch (SQLException e) {
//    e.printStackTrace();
//}
//		DatabaseClient db = new DatabaseClient();
//		//README: run this the first time to initialize database after adding your cities to DatabaseClient class
//		try {
//			db.initialize();
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		
//		boolean CityExists = false;
//		try {
//			CityExists = db.findCity("New York City", "USA", null);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		System.out.println(CityExists);
//		
//		double[] coordinates = new double[2];
//		try {
//			coordinates = db.getCoordinates("Mexico City", "Mexico", null);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		System.out.println("Database coordinates retrieval.");
//		System.out.println(coordinates[0]);
//		System.out.println(coordinates[1]);
		
		
		
		
//		try {
//			coordinates = ac.geocodingRequest("San Marcos", "USA", "CA");
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		try {
//			coordinates = ac.geocodingRequest("Tokyo", "Japan", null);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		System.out.println("Geocoding API coordinates retrieval.");
//		System.out.println(coordinates[0]);
//		System.out.println(coordinates[1]);
//		
//		LocDetails[] locArr = new LocDetails[20];
//		String category = "museums";
//		
//		try {
//			locArr = ac.categoryRequest(51.51, -0.13, category);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		LocDetails.printLocDetails(locArr);
		
		


