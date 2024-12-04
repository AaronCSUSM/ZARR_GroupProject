package zarr;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.concurrent.TimeUnit;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class landmarkPanel extends JPanel{
	private searchPanel SearchPanel;
	private CardLayout cardLayout;
	private JPanel mainPanel;
	
	/**
	 * Constructor method for creating the landmarks panel
	 * @param sp search panel object
	 * @param cl card layout object
	 * @param mp JPanel object to create main panel
	 */
	public landmarkPanel(searchPanel sp, CardLayout cl, JPanel mp) {

		this.SearchPanel = sp;
		this.cardLayout = cl;
		this.mainPanel = mp;
		
		//create layout for panel
		setLayout(new BorderLayout());
		
		//header for panel
		JLabel headerLabel = new JLabel("Most Popular Landmarks to Visit", JLabel.CENTER); //centers this
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
				"Taj Mahal",
				"Agra",
				"India",
				null,
				"https://en.wikipedia.org/wiki/File:Taj_Mahal_(Edited).jpeg",
				"An ivory-white marble mausoleum on the right bank of the river Yamuna in Agra, Uttar Pradesh, India. The building complex incorporates the design traditions of Indo-Islamic and Mughal architecture.",
				"Yann Forget, Wikimedia Commons"
				);
		
		locations[1] = new browsingDetails(
				"The Colosseum",
				"Rome",
				"Italy",
				null,
				"https://en.wikipedia.org/wiki/File:Colosseo_2020.jpg",
				"The Colosseum is the largest ancient amphitheater ever built, and is still the largest standing amphitheater in the world, despite its age. It is still a renowned symbol of Imperial Rome and was listed as one of the New 7 Wonders of the World. It is one of Rome's most popular tourist attractions.",
				"FeaturedPics, Wikimedia Commons"
				);
		
		locations[2] = new browsingDetails(
				"Christ the Redeemer",
				"Rio de Janeiro",
				"Brazil",
				null,
				"https://en.wikipedia.org/wiki/File:Christ_on_Corcovado_mountain.JPG",
				"Christ the Redeemer is an Art Deco statue of Jesus in Rio de Janeiro, Brazil. This statue is the largest Art Deco–style sculpture in the world. A symbol of Christianity around the world, the statue has also become a cultural icon of both Rio de Janeiro and Brazil and was voted one of the New 7 Wonders of the World.",
				"Artyominc, Wikipedia"
				);
		
		locations[3] = new browsingDetails(
				"Sydney Opera House",
				"Sydney",
				"Australia",
				null,
				"https://en.wikipedia.org/wiki/File:Sydney_Australia._(21339175489).jpg",
				"The Sydney Opera House is a multi-venue performing arts centre in Sydney, New South Wales, Australia. Located on the foreshore of Sydney Harbour, it is widely regarded as one of the world's most famous and distinctive buildings and a masterpiece of 20th-century architecture.",
				"Bernard Spragg, Wikipedia"
				);
		
		locations[4] = new browsingDetails(
				"Great Sphinx of Giza",
				"Giza",
				"Egypt",
				null,
				"https://en.wikipedia.org/wiki/File:Sphinx_with_the_third_pyramid.jpg",
				"The Great Sphinx of Giza is a limestone statue of a reclining sphinx, a mythical creature with the head of a human and the body of a lion. The Sphinx is the oldest known monumental sculpture in Egypt and one of the most recognizable statues in the world.",
				"Hesham Ebaid, Wikipedia"
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
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
