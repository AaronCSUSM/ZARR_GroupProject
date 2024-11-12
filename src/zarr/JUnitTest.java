package zarr;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JUnitTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testFindCity() {
		DatabaseClient db = new DatabaseClient();
		
		boolean expected1 = true;
		boolean expected2 = true;
		boolean expected3 = false;
		boolean expected4 = false;
		
		boolean result1 = false;
		try {
			result1 = db.findCity("Tokyo", "Japan", null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		boolean result2 = false;
		try {
			result2 = db.findCity("New York City", "USA", "New York");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		boolean result3 = true;
		try {
			result3 = db.findCity("Tokyo", "USA", null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		boolean result4 = true;
		try {
			result4 = db.findCity("New York City", "USA", null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertEquals(expected1, result1, "City should be in database.");
		assertEquals(expected2, result2, "City should be in database.");
		assertEquals(expected3, result3, "City with those properties doesn't exist.");
		assertEquals(expected4, result4, "City with those properties doesn't exist.");
		
		System.out.println("Find city in database test passed.");
		
	}//end findCity test
	
	@Test
	void testGetCoordinates() {
		DatabaseClient db = new DatabaseClient();
		
		double[] expected1 = {35.68, 139.65};
		double[] expected2 = {40.71, -74.01};
		double[] expected3 = {59.93, 30.36};	
		double[] expected4 = {30.04, 31.24};	
		
		double[] result1 = new double[2];
		double[] result2 = new double[2];
		double[] result3 = new double[2];
		double[] result4 = new double[2];
		
		try {
			result1 = db.getCoordinates("Tokyo", "Japan", null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			result2 = db.getCoordinates("New York City", "USA", "New York");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			result3 = db.getCoordinates("St. Petersburg", "Russia", null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			result4 = db.getCoordinates("Cairo", "Egypt", null);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertArrayEquals(expected1, result1, "Incorrect coordinates for test1.");
		assertArrayEquals(expected2, result2, "Incorrect coordinates for test2.");
		assertArrayEquals(expected3, result3, "Incorrect coordinates for test3.");
		assertArrayEquals(expected4, result4, "Incorrect coordinates for test4.");
		
		System.out.println("Database coordinates retrieval test passed.");
		
	}//end testGetCoordinates
	
	@Test
	void testCategoryRequests() {
		//San Marcos, CA coordinates: 33.14, -117.17
		//Madrid, Spain coordinates: 40.42, -3.70
		//Miami, FL coordinates: 25.76, -80.19
		
		APIClient ac = new APIClient();
		
		String category1 = "musuem";
		String category2 = "restaurant";
		String category3 = "amusement parks";
		
		LocDetails[] result1 = new LocDetails[20];
		LocDetails[] result2 = new LocDetails[20];
		LocDetails[] result3 = new LocDetails[20];
		
		try {
			result1 = ac.categoryRequest(33.14, -117.17, category1);
			result2 = ac.categoryRequest(40.42, -3.70, category2);
			result3 = ac.categoryRequest(25.76, -80.19, category3);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		assertNotNull(result1, "Category request test 1 failed.");
		assertNotNull(result2, "Category request test 2 failed.");
		assertNotNull(result3, "Category request test 3 failed.");
		
		System.out.println("Category API test passed.");
	}
	
	@Test
	void testgeocodingRequest() {
		//San Marcos, CA coordinates: 33.14, -117.17
		//Madrid, Spain coordinates: 40.42, -3.70
		//Miami, FL coordinates: 25.76, -80.19
				
		APIClient ac = new APIClient();
		
		double[] result1 = new double[2];
		double[] result2 = new double[2];
		double[] result3 = new double[2];
		
		try {
			result1 = ac.geocodingRequest("San Marcos", "USA", "CA");
			result2 = ac.geocodingRequest("Madrid", "Spain", null);
			result3 = ac.geocodingRequest("Miami", "USA", "FL");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		double[] expected1 = {33.14, -117.17};
		double[] expected2 = {40.42, -3.70};
		double[] expected3 = {25.76, -80.19};
		
		assertArrayEquals(expected1, result1, "GeoRequest test 1 failed.");
		assertArrayEquals(expected2, result2, "GeoRequest test 2 failed.");
		assertArrayEquals(expected3, result3, "GeoRequest test 3 failed.");
		System.out.println("Geocoding test passed.");
		
	}
		
		

}
