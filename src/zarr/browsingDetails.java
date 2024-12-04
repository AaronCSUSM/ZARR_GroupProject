package zarr;

import java.net.MalformedURLException;
import java.net.URL;
import java.awt.Image;
import javax.swing.*;

/**
*
* @author Aaron Cambridge, Rebecca Hall, River Hallie, Zach Miller
* @version 12/4/24
*
*/
public class browsingDetails {
	private String name;
	private String city;
	private String country;
	private String state;
	private String pictureURL;
	private String description;
	private String photoSource;
	
	/**
	 * Constructor method for setting browsing details
	 * @param n name
	 * @param city name of city
	 * @param country name of country
	 * @param st name of state
	 * @param url picture url
	 * @param desc description
	 * @param ps photo source
	 */
	public browsingDetails(String n, String city, String country, String st, String url, String desc, String ps) {
		this.name = n;
		this.city = city;
		this.country = country;
		if(country == "USA") {
			this.state = st;
		}else {
			this.state = null;
		}
		this.pictureURL = url;
		this.description = desc;
		this.photoSource = ps;
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getCity() {
		return this.city;
	}
	
	public String getCountry() {
		return this.country;
	}
	
	public String getState() {
		return this.state;
	}
	
	public String getURL() {
		return this.pictureURL;
	}
	
	public String getDescription() {
		return this.description;
	}
	
	public String getPhotoSource() {
		return this.photoSource;
	}
	
	public JLabel displayImage() {
		
		//attempt to URL object from url address
		URL imageURL = null;
		try {
			imageURL = new URL(this.pictureURL);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//create the imageicon and scale it
		ImageIcon II1 = new ImageIcon(imageURL);
		Image scaledImage1 = II1.getImage().getScaledInstance(275, 150, Image.SCALE_SMOOTH);
		II1 = new ImageIcon(scaledImage1);
		
		//create the JLabel and return it
		JLabel picture = new JLabel(II1);
		
		return picture;
	}//end display image
	
	/**
	 * Overrides toString to print an object neatly.
	 * @return String representation of an object.
	 */
	public String toString() {
		String locStr = this.getCity();
		if(this.getState() != null) {
			locStr = locStr + ", " + this.getState() + ", " + "USA";
		}else {
			locStr = locStr + ", " + this.getCountry();
		}
		return locStr;
	}//end toString
	
}
