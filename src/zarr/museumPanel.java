package zarr;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 
 * @author Aaron Cambridge, Rebecca Hall, River Hallie, Zach Miller
 * @version 12/4/24
 *
 */
public class museumPanel extends JPanel{
	
	private searchPanel SearchPanel;
	private CardLayout cardLayout;
	private JPanel mainPanel;
	
	/**
	 * Constructor method for creating the museum panel
	 * @param sp search panel object
	 * @param cl card layout object
	 * @param mp JPanel object to create main panel
	 */
	public museumPanel(searchPanel sp, CardLayout cl, JPanel mp) {
		
		this.SearchPanel = sp;
		this.cardLayout = cl;
		this.mainPanel = mp;
		
		//create layout for panel
		setLayout(new BorderLayout());
		
		//header for panel
		JLabel headerLabel = new JLabel("Most Popular Museums to Visit", JLabel.CENTER); //centers this
		headerLabel.setFont(new Font("Arial", Font.BOLD, 24)); //set the font of it

		//add to panel layout (what to add, where to add)
		add(headerLabel, BorderLayout.NORTH);
		
		
		//creates a JPanel and changes its layout to BoxLayout
		JPanel detailsPanel = new JPanel();
		//BoxLayout.Y_AXIS will make it so components are organized vertically
		detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
		//give panel some padding so its not merging into window
		detailsPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));	
		
		//public browsingDetails(String n, String city, String country, String st, String url, String desc)	
		browsingDetails[] locations = new browsingDetails[5];
		locations[0] = new browsingDetails(
				"Louvre Museum",
				"Paris ", 
				"France",
				null,
				"https://upload.wikimedia.org/wikipedia/commons/thumb/6/66/Louvre_Museum_Wikimedia_Commons.jpg/1200px-Louvre_Museum_Wikimedia_Commons.jpg?20161126115921",
				"The largest museum in the world housing some of the most popular works of art such as the Mona Lisa. Contains over 500,000 objects from France and abroad.",
				"Benh LIEU SONG, Wikipedia"
				);
		
		locations[1] = new browsingDetails(
				"Vatican Museums",
				"Vatican City", 
				"Rome",
				null,
				"https://upload.wikimedia.org/wikipedia/commons/thumb/c/c9/0_Cortile_della_Pigna_-_Vatican.JPG/1280px-0_Cortile_della_Pigna_-_Vatican.JPG",
				"Contains roughly 70,000 works of art with 20,000 pieces on display across it's 54 galleries. The museum's primary themes include Roman art, Catholicism, and Renaissance paintings.",
				"Jean-Pol GRANDMONT, Wikipedia"
				);
		
		locations[2] = new browsingDetails(
				"National Museum of China",
				"Beijing", 
				"China",
				null,
				"https://upload.wikimedia.org/wikipedia/commons/thumb/0/08/62684-Beijing-Tiananmen-Square_%2828609003992%29.jpg/1920px-62684-Beijing-Tiananmen-Square_%2828609003992%29.jpg",
				"This museum houses the largest collection of Chinese cultural relics from various time periods with over 1.4 million pieces.",
				"xiquinhosilva, Wikipedia"
				
				);
		
		locations[3] = new browsingDetails(
				"British Museum",
				"London", 
				"United Kingdom",
				null,
				"https://upload.wikimedia.org/wikipedia/commons/thumb/1/1b/British_Museum_from_NE_2_%28cropped%29.JPG/1280px-British_Museum_from_NE_2_%28cropped%29.JPG",
				"This museum boasts the largest collection in the world with over 8,000,000 cultural works. The British museum was the most popular attraction in the United Kingdom in 2023. ",
				"Ham, Wikipedia"
				
				);
		
		locations[4] = new browsingDetails(
				"Metropolitan Museum of Art (Met)",
				"New York City", 
				"USA",
				"New York",
				"https://upload.wikimedia.org/wikipedia/commons/thumb/3/30/Metropolitan_Museum_of_Art_%28The_Met%29_-_Central_Park%2C_NYC.jpg/1920px-Metropolitan_Museum_of_Art_%28The_Met%29_-_Central_Park%2C_NYC.jpg",
				"The Met is the fifth most popular art museum in the world and the most popular art museum throughout the United States. Contains 1.5 million works. ",
				"Hugo Schneider, Wikipedia"
				
				);
				
		for(int i = 0; i < 5; i++) {
			detailsPanel.add(createLocationsPanel(locations[i]));
			detailsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		}
		
		//add(detailsPanel, BorderLayout.CENTER);
		JScrollPane scrollPane = new JScrollPane(detailsPanel);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		add(scrollPane, BorderLayout.CENTER);
		
	}//end constructor
	
	/**
	 * Method to populate the window with museum locations and info.
	 * @param location location(s) to add into panel
	 * @return locationPanel
	 */
	public JPanel createLocationsPanel(browsingDetails location) {
		
		String locStr = location.toString();
		
		JPanel locationPanel = new JPanel();
		locationPanel.setLayout(new BoxLayout(locationPanel, BoxLayout.X_AXIS));
		//locationPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		leftPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
		JLabel nameL = new JLabel("NAME: " + location.getName());
		JLabel locationL = new JLabel("LOCATION: " + locStr);
		JLabel descriptionL = new JLabel("<html><div style='width:250px;'>DESCRIPTION:  "+ location.getDescription()+"</div></html>");
		JLabel photoSource = new JLabel("PHOTO SOURCE: " + location.getPhotoSource());
		JButton exploreButton = new JButton("Explore " + location.getCity());
		
		exploreButton.addActionListener(e->{
			SearchPanel.setSearchFields(location.getCity(), location.getCountry(), location.getState(), "museum");
			this.cardLayout.show(mainPanel, "Search");
		});
		
		leftPanel.add(nameL);
		leftPanel.add(locationL);
		leftPanel.add(descriptionL);
		leftPanel.add(photoSource);
		leftPanel.add(Box.createRigidArea(new Dimension(0,10)));
		leftPanel.add(exploreButton);
		
		JLabel picture = location.displayImage();
		//JPanel rightPanel = location.displayImage();
		picture.add(location.displayImage());
		
		leftPanel.setAlignmentY(Component.TOP_ALIGNMENT);
		picture.setAlignmentY(Component.TOP_ALIGNMENT);
		
		locationPanel.add(leftPanel);
		locationPanel.add(Box.createRigidArea(new Dimension(0,10)));
		locationPanel.add(picture);
		
		return locationPanel;
	}
}
