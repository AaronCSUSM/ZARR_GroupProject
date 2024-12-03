package zarr;

import javax.swing.JPanel;
import javax.swing.*;
import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;

public class beachPanel extends JPanel{
	
	private searchPanel SearchPanel;
    private CardLayout cardLayout;
    private JPanel mainPanel;
    
    
	public beachPanel(searchPanel sp, CardLayout cl, JPanel mp) {
		this.SearchPanel = sp;
        this.cardLayout = cl;
        this.mainPanel = mp;
        
        //create layout for panel
        setLayout(new BorderLayout());
        
        //header for panel
        JLabel headerLabel = new JLabel("Most Popular Beaches to Visit", JLabel.CENTER); //centers this
        headerLabel.setFont(new Font("Arial", Font.BOLD, 24)); //set the font of it

        
        add(headerLabel, BorderLayout.NORTH);
        
        
        //creates a JPanel and changes its layout to BoxLayout
        JPanel detailsPanel = new JPanel();
        
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        
        detailsPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));    
        
        //public browsingDetails(String n, String city, String country, String st, String url, String desc)  
        browsingDetails[] locations = new browsingDetails[5];
        locations[0] = new browsingDetails(
                "Bondi Beach",
                "Sydney", 
                "Australia",
                null,
                "https://upload.wikimedia.org/wikipedia/commons/7/79/Bondi_from_above.jpg",
                "One of the most famous beaches in the world, known for its golden sands and vibrant surf culture."
                );
        
        locations[1] = new browsingDetails(
                "Maya Bay",
                "Phi Phi Islands", 
                "Thailand",
                null,
                "https://upload.wikimedia.org/wikipedia/commons/3/34/Maya_Bay%2C_Thailand_by_Mike_Clegg_Photography.jpg",
                "A stunning beach surrounded by high limestone cliffs, made famous by the film 'The Beach'."
                );
        
        locations[2] = new browsingDetails(
                "Waikiki Beach",
                "Honolulu", 
                "Hawaii, USA",
                null,
                "https://upload.wikimedia.org/wikipedia/commons/7/74/Waikiki_Beach%2C_Honolulu.JPG",
                "A world-renowned beach known for its crystal-clear water, great surfing, and iconic views of Diamond Head."
                );
        
        locations[3] = new browsingDetails(
                "Playa del Carmen",
                "Riviera Maya", 
                "Mexico",
                null,
                "https://upload.wikimedia.org/wikipedia/commons/f/f3/Aerial_of_Playa_del_Carmen%2C_Mexico_%2828708057347%29.jpg",
                "Known for its soft white sand, clear blue water, and proximity to the ancient Mayan ruins."
                );
        
        locations[4] = new browsingDetails(
                "Pink Sands Beach",
                "Harbour Island", 
                "Bahamas",
                null,
                "https://upload.wikimedia.org/wikipedia/commons/8/8c/GovernmentDock.png",
                "Famous for its pink-hued sands and calm, turquoise waters, perfect for a relaxing vacation."
                );
                
        for(int i = 0; i < 5; i++) {
            detailsPanel.add(createLocationsPanel(locations[i]));
            detailsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }
        
        
        JScrollPane scrollPane = new JScrollPane(detailsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        add(scrollPane, BorderLayout.CENTER);
        
    }
    
    public JPanel createLocationsPanel(browsingDetails location) {
        
        String locStr = location.toString();
        
        JPanel locationPanel = new JPanel();
        locationPanel.setLayout(new BoxLayout(locationPanel, BoxLayout.X_AXIS));
        
        
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        
        JLabel nameL = new JLabel("NAME: " + location.getName());
        JLabel locationL = new JLabel("LOCATION: " + locStr);
        JLabel descriptionL = new JLabel("<html><div style='width:250px;'>DESCRIPTION:  "+ location.getDescription()+"</div></html>");
        JButton exploreButton = new JButton("Explore " + location.getCity());
        
        exploreButton.addActionListener(e->{
            SearchPanel.setSearchFields(location.getCity(), location.getCountry(), location.getState(), "beach");
            this.cardLayout.show(mainPanel, "Search");
        });
        
        leftPanel.add(nameL);
        leftPanel.add(locationL);
        leftPanel.add(descriptionL);
        leftPanel.add(Box.createRigidArea(new Dimension(0,10)));
        leftPanel.add(exploreButton);
        
        
        
        JLabel picture = location.displayImage();
        picture.add(location.displayImage());
        
        leftPanel.setAlignmentY(Component.TOP_ALIGNMENT);
        picture.setAlignmentY(Component.TOP_ALIGNMENT);
        
        locationPanel.add(leftPanel);
        locationPanel.add(Box.createRigidArea(new Dimension(0,10)));
        locationPanel.add(picture);
        
        return locationPanel;
    }

}
