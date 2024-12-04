package zarr;

/**
 * 
 * @author Aaron Cambridge, Rebecca Hall, River Hallie, Zach Miller
 * @version 12/4/24
 *
 */
public class LocDetails {

	private String name;//name of location
	private String status;//operation status
	private String photo_reference;//originally intended for retrieving photos
	private String place_id;//used for other endpoints, collected just in case
	private double rating;//so far all places have had a rating
	private int priceLevel;//not all places had a pricelevel
	
	public LocDetails() {
		//String values intially set to "N/A" in case they didn't have a value
		this.name = "N/A";
		this.status = "N/A";
		this.photo_reference = "N/A";
		this.place_id = "N/A";
		//other methods will check if rating or priceLevel is -999 to determine if it should display or not
		this.rating = -999;
		this.priceLevel = -999;
	}
	
	/**
	 * 
	 * @param name name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * 
	 * @return name name to get
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * 
	 * @param status status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	/**
	 * 
	 * @return status status to get
	 */
	public String getStatus() {
		return this.status;
	}
	
	/**
	 * 
	 * @param photo_reference photo reference to set
	 */
	public void setPhotoReference(String photo_reference) {
		this.photo_reference = photo_reference;
	}
	
	/**
	 * 
	 * @return photo reference to get
	 */
	public String getPhotoReference() {
		return this.photo_reference;
	}
	
	/**
	 * 
	 * @param place_id place id to set
	 */
	public void setPlaceID(String place_id) {
		this.place_id = place_id;
	}
	
	/**
	 * 
	 * @return place_id place id to get.
	 */
	public String getPlaceID() {
		return this.place_id;
	}
	
	/**
	 * 
	 * @param rating rating to set
	 */
	public void setRating(double rating) {
		this.rating = rating;
	}
	
	/**
	 * 
	 * @return rating to get
	 */
	public double getRating() {
		return this.rating;
	}
	
	/**
	 * 
	 * @param priceLevel price level to set
	 */
	public void setPriceLevel(int priceLevel) {
		this.priceLevel = priceLevel;
	}
	
	/**
	 * 
	 * @return price level to get
	 */
	public int getPriceLevel() {
		return this.priceLevel;
	}
	
	/**
	 * Method to print local details from passed array object.
	 * @param loc local details array object
	 */
	//used to display the details of each location
	public static void printLocDetails(LocDetails[] loc) {
		for(int i = 0; i < loc.length; i++) {
			if(loc[i] != null) {
				System.out.println("LOCATION " + (i+1));
				System.out.println("NAME: " + loc[i].getName());
				System.out.println("STATUS: " + loc[i].getStatus());
				//System.out.println("PHOTO REFERENCE: " + loc[i].getPhotoReference());
				//System.out.println("PLACE ID: " + loc[i].getPlaceID());
				if(loc[i].getRating() != -999) {
					System.out.println("RATING: " + loc[i].getRating());
				}
				if(loc[i].getPriceLevel() != -999) {
					System.out.println("PRICE LEVEL: " + loc[i].getPriceLevel());
				}
				System.out.println("");
			}
			
		}
	}//end printLocDetails
	
	
	/**
	 * Method to append information from API into a string for output. 
	 * @param ld local details object
	 * @return
	 */
	//another function for displaying the location details that returns a string instead of just printing with void
	//was originally used when the results were just display a large textbox as one continuous string
	//not used in final product
	public static String LocDetailsToString(LocDetails[] ld) {
		//uses stringBuilder for format the string
		StringBuilder sb = new StringBuilder();
		//loops through the location details array
		for(int i = 0; i < ld.length; i++) {
			if(ld[i] != null) {
				sb.append(String.format("LOCATION: %d\n", i+1));
				//gives each label a space of 15, left aligned, so that they line up neatly
				sb.append(String.format("%-15s %s\n", "NAME:", ld[i].getName()));
				sb.append(String.format("%-15s %s\n", "STATUS:", ld[i].getStatus()));
				
				if(ld[i].getRating() != -999) {
					sb.append(String.format("%-15s %.1f\n", "RATING:", ld[i].getRating()));
				}
				if(ld[i].getPriceLevel() != -999) {
					//maybe append picture of dollar sign
					sb.append(String.format("%-15s %d\n", "PRICE LEVEL:", ld[i].getPriceLevel()));
				}
				sb.append("\n");
			}
			
		}//end for loop
		String str = sb.toString();
		return str;
	}//end LocDetailsToString
	
}
