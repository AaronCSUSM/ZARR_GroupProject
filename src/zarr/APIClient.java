package zarr;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class APIClient {
	public double[] geocodingRequest(String city, String country, String state) throws Exception{
		double[] coordinates = new double[2];
		
		String city1 = city.replaceAll(" ", "%20");
		String country1 = country.replaceAll(" ", "%20");
		String state1 = state.replaceAll(" ", "%20");
		
		String location;
		if("USA".equals(country)) {
			location = city1 + "%2c%20" + state1 + "%2c%20" + country1;
		}else {
			location = city1 + "%2c%20" + country1;
		}
		
		String APIurl = "https://google-map-places.p.rapidapi.com/maps/api/geocode/json?address="
				+ location
				+ "&language=en&region=en&result_type=administrative_area_level_1&location_type=APPROXIMATE";
		
		HttpResponse<JsonNode> response = Unirest.get(APIurl)
				.header("x-rapidapi-key", "bb61dd9d71mshc40729a63f47406p12ed14jsn424d221a1e17")
				.header("x-rapidapi-host", "google-map-places.p.rapidapi.com")
				//.asString();
				.asJson();
//		String resp = response.getBody();
//		System.out.println(resp);
//		
//		resp = resp.substring(resp.indexOf("\"location\" :")+22);
//		System.out.println(resp);
//		
//		String longitude, latitude;
//		
//		if(resp.indexOf(".") == 1) {
//			latitude = resp.substring(0, 4);
//		}else if(resp.indexOf(".") == 2) {
//			latitude = resp.substring(0, 5);
//		}else if(resp.indexOf(".") == 3) {
//			latitude = resp.substring(0, 6);
//		}else {
//			latitude = resp.substring(0, 7);
//		}
		
		//System.out.println("Latitude: " + latitude);
		
		//resp = resp.substring(resp.indexOf("\"location\" :")+22);
		
		
		
		
		
//		Gson gson = new GsonBuilder().setPrettyPrinting().create();
//	    JsonParser jp = new JsonParser();
//	    JsonElement je = jp.parse(response.getBody().toString());
//	    String prettyJsonString = gson.toJson(je);
//	    System.out.println(prettyJsonString);
		
	    JSONObject jsonResponse = new JSONObject(response.getBody().toString());
	      
	    if(jsonResponse.getJSONArray("results").length() > 0) {
	    	JSONObject locationData = jsonResponse.getJSONArray("results")
	    			.getJSONObject(0)
	    			.getJSONObject("geometry")
	    			.getJSONObject("location");
	    		  
	    	coordinates[0] = locationData.getDouble("lat");
	    	coordinates[1] = locationData.getDouble("lng");
	    	
	    	coordinates[0] = roundCoordinates(coordinates[0]);
	    	coordinates[1] = roundCoordinates(coordinates[1]);
	    	
	    }else {
	    	System.out.println("Location not found.");  
	    }
	    
	    System.out.println("Latitude: " + coordinates[0]);
	    System.out.println("Longitude: " + coordinates[1]);
	    
		//System.out.println("Geocoding Response finished.");
		
		return coordinates;
	}
	
	public double roundCoordinates(double x) {
		x = Math.round(x * 100.0)/100.0;
		return x;
	}
}
