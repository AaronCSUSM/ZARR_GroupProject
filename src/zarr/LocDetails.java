package zarr;

public class LocDetails {

	private String name;
	private String status;
	private String photo_reference;
	private String place_id;
	private double rating;
	private int priceLevel;
	
	public LocDetails() {
		this.name = "N/A";
		this.status = "N/A";
		this.photo_reference = "N/A";
		this.place_id = "N/A";
		this.rating = -999;
		this.priceLevel = -999;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getStatus() {
		return this.status;
	}
	
	public void setPhotoReference(String photo_reference) {
		this.photo_reference = photo_reference;
	}
	
	public String getPhotoReference() {
		return this.photo_reference;
	}
	
	public void setPlaceID(String place_id) {
		this.place_id = place_id;
	}
	
	public String getPlaceID() {
		return this.place_id;
	}
	
	public void setRating(double rating) {
		this.rating = rating;
	}
	
	public double getRating() {
		return this.rating;
	}
	
	public void setPriceLevel(int priceLevel) {
		this.priceLevel = priceLevel;
	}
	
	public int getPriceLevel() {
		return this.priceLevel;
	}
	
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
	
//	public static String LocDetailsToString(LocDetails[] ld) {
//		String str = "";
//		for(int i = 0; i < ld.length; i++) {
//			if(ld[i] != null) {
//				str = str + str + "LOCATION " + (i+1) + "\n" +
//						"NAME: " + ld[i].getName() + "\n" +
//						"STATUS: " + ld[i].getStatus() + "\n";
//				if(ld[i].getRating() != -999) {
//					str = str + "RATING: " + ld[i].getRating() + "\n";
//				}
//				if(ld[i].getPriceLevel() != -999) {
//					str = str + "PRICE LEVEL: " + ld[i].getPriceLevel() + "\n";
//				}
//				str = str + "\n";
//			}
//		}//end for loop
//		
//		return str;
//	}//end LocDetailsToString
	
	public static String LocDetailsToString(LocDetails[] ld) {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < ld.length; i++) {
			if(ld[i] != null) {
				sb.append(String.format("LOCATION: %d\n", i+1));
				
				sb.append(String.format("%-15s %s\n", "NAME:", ld[i].getName()));
				sb.append(String.format("%-15s %s\n", "STATUS:", ld[i].getStatus()));
				
				if(ld[i].getRating() != -999) {
					sb.append(String.format("%-15s %.1f\n", "RATING:", ld[i].getRating()));
				}
				if(ld[i].getPriceLevel() != -999) {
					sb.append(String.format("%-15s %d\n", "PRICE LEVEL:", ld[i].getPriceLevel()));
				}
				sb.append("\n");
			}
			
		}//end for loop
		String str = sb.toString();
		return str;
	}//end LocDetailsToString
	
}
