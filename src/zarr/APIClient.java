package zarr;
import com.mashape.unirest.http.HttpResponse;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.json.JSONArray;
import org.json.JSONObject;

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
	
	
	public LocDetails[] categoryRequest(double latitude, double longitude, String category) throws Exception{
		LocDetails[] ldArr = new LocDetails[20];
		
		//replace all spaces in category, e.g., "amusement park"
		String category1 = category.replaceAll(" ", "%20");
		
		//customize url based on parameters
		String APIurl = "https://google-map-places.p.rapidapi.com/maps/api/place/nearbysearch/json?location="
				+ latitude
				+ "%2C%20" // ", "
				+ longitude
				//radius set to 100,000 meters (about 62 miles)
				+ "&radius=100000&language=en&rankby=prominence&keyword="
				+ category1;
		
		HttpResponse<JsonNode> response = Unirest.get(APIurl)
				.header("x-rapidapi-key", "bb61dd9d71mshc40729a63f47406p12ed14jsn424d221a1e17")
				.header("x-rapidapi-host", "google-map-places.p.rapidapi.com")
				//.asString();
				.asJson();
		
		
		//convert API respons to one JSON object
		JSONObject jsonResponse = response.getBody().getObject();
		//results array will hold all locations found by api request
		JSONArray results = jsonResponse.getJSONArray("results");
		
		
		//check if anything was found
		if(results.length() == 0) {
			LocDetails invalidLoc = new LocDetails();
			//after calling function check if arr[0].name == NoDataAvailable
			invalidLoc.setName("NoDataAvailable");
			ldArr[0] = invalidLoc;
			System.out.println("No results found by API.");
			return ldArr;
		}
		
		//default number of keys from API is 20, but could be less depending on location
		for(int i = 0; i < results.length(); i++) {
			//new JSONobject to hold each location during each loop
			JSONObject oneLoc = results.getJSONObject(i);
			//new LocDetails object that will be updateed and added to array
			LocDetails ld = new LocDetails();
			
			//everything should have name
			ld.setName(oneLoc.optString("name", "N/A"));
			//so far everything has had business_status
			ld.setStatus(oneLoc.optString("business_status", "N/A"));
			//might need place_id later
			ld.setPlaceID(oneLoc.optString("place_id", "N/A"));
			//check if the location has a rating or not. 
			if(oneLoc.has("rating")) {
				ld.setRating(oneLoc.getDouble("rating"));
			}else {
				//default rating should already be -999, but set again just in case
				ld.setRating(-999);
			}
			
			//so far only restaurants have had a price_level field
			if(oneLoc.has("price_level")) {
				ld.setPriceLevel(oneLoc.getInt("price_level"));
			}else {
				ld.setPriceLevel(-999);
			}
			
			//photo_reference needed for images, but endpoint for photos not working atm
			if (oneLoc.has("photos")){
				JSONArray photoArr = oneLoc.getJSONArray("photos");
				if(photoArr.length() > 0) {
					JSONObject photo = photoArr.getJSONObject(0);
					ld.setPhotoReference(photo.getString("photo_reference"));
				}else {
					ld.setPhotoReference("N/A");
				}
				
			}
			
			//add the LocDetails object to the array
			ldArr[i] = ld;
			
		}//end for loop
	  
		return ldArr;
	}
}
