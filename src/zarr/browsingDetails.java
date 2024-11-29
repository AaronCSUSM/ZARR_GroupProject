package zarr;

import java.net.MalformedURLException;
import java.net.URL;
import java.awt.Image;
import javax.swing.*;

public class browsingDetails {
	private String name;
	private String city;
	private String country;
	private String state;
	private String pictureURL;
	private String description;
	
	public browsingDetails(String n, String city, String country, String st, String url, String desc) {
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
