package zarr;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class parkPanel extends JPanel{
	private searchPanel SearchPanel;
	private CardLayout cardLayout;
	private JPanel mainPanel;
	
		/**
		 * Constructor method for creating the park panel
		 * @param sp search panel object
		 * @param cl card layout object
		 * @param mp JPanel object to create main panel
		 */
		public parkPanel(searchPanel sp, CardLayout cl, JPanel mp) {
			
			this.SearchPanel = sp;
			this.cardLayout = cl;
			this.mainPanel = mp;
			
			//create layout for panel
			setLayout(new BorderLayout());
			
			//header for panel
			JLabel headerLabel = new JLabel("Most Popular Amusement Parks to Visit", JLabel.CENTER); //centers this
			//headerLabel.setFont(new Font("Arial", Font.BOLD, 24)); //set the font of it
			headerLabel.setFont(new Font("Edwardian Script ITC", Font.BOLD, 50)); // Slightly larger, bold font
			
			
			//add to panel layout (what to add, where to add)
			add(headerLabel, BorderLayout.NORTH);
			
			
			//creates a JPanel and changes its layout to BoxLayout
			JPanel detailsPanel = new JPanel();
			//BoxLayout.Y_AXIS will make it so components are organized vertically
			detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
			//give panel some padding so its not merging into window
			detailsPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));	
			
			//public browsingDetails(String n, String city, String country, String st, String url, String desc)	
			// some images not showing up, possibly caused by race condition with loading images from the internet. 
			browsingDetails[] locations = new browsingDetails[5];
			locations[0] = new browsingDetails(
					"Walt Disney World",
					"Orlando", 
					"USA",
					"Florida",
					"https://upload.wikimedia.org/wikipedia/commons/thumb/c/c8/Cinderella_Castle%2C_Magic_Kingdom_Walt_Disney_World_2024_%28square_crop%29.jpg/1024px-Cinderella_Castle%2C_Magic_Kingdom_Walt_Disney_World_2024_%28square_crop%29.jpg",
					"Known as a place where fairy tales come to life, Walt Disney World is an entertainment resort complex housing the theme parks Magic Kingdom Park, EPCOT, Disney's Hollywood Studios, and Disney's Animal Kingdom Theme Park.",
					"Jedi94, Wikipedia"
					);
			
			locations[1] = new browsingDetails(
					"Ferrari World",
					"Abu Dhabi", 
					"United Arab Emirates",
					null,
					"https://upload.wikimedia.org/wikipedia/commons/4/45/Il_museo_Ferrari_-_Abu_Dhabi_-_panoramio.jpg",
					"The world's first Ferrari-themed park is located on Yas Island in the United Arab Emirates. It is a primarily indoor theme park spanning 86,000 square meters and offers many high speed Ferrari inspired attractions",
					"patano, Wikipedia"
					);
			
			locations[2] = new browsingDetails(
					"Disneyland Park",
					"Anaheim", 
					"USA",
					"California",
					"https://upload.wikimedia.org/wikipedia/commons/2/2d/Sleeping_Beauty_Castle_2019.jpg",
					"Known as \"The Happiest Place on Earth,\" this iconic theme park houses 8 themed lands: Main Street, U.S.A., Adventureland, New Orleans Square, Frontierland, Critter Country, Fantasyland, Mickey's Toontown, and Tomorrowland.",
					"CrispyCream27, Wikipedia"
					
					);
			
			locations[3] = new browsingDetails(
					"Asterix Park",
					"Paris", 
					"France",
					null,
					"https://www.parisdigest.com/photos/parc_asterix_fun.jpg",
					"Parc Asterix is a theme park in France based on the comic book series Asterix by Albert Uderzo and Rene Goscinny. It is renowned in France for its variety of roller coasters and water rides. ",
					"parisdigest.com"
					
					);
			
			locations[4] = new browsingDetails(
					"Tokyo DisneySea",
					"Tokyo", 
					"Japan",
					null,
					"https://thetouristchecklist.com/wp-content/uploads/2024/03/Tokyo-DisneySea-Japan.jpg",
					"Tokyo DisneySea is a one-of-a-kind theme park inspired by the legends and the myths of the sea. This park is known for its \"ports of call\", which include Mediterranean Harbor, Mysterious Island, Mermaid Lagoon, Arabin Coast, Lost River Delta, Port Discovery, and American Waterfront.",
					"Tokyo DisneySea, Facebook"
					
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
				SearchPanel.setSearchFields(location.getCity(), location.getCountry(), location.getState(), "parks");
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
