package zarr;

import javax.swing.*;
import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;


public class GUI extends JFrame {
	private JPanel mainPanel; //the main panel that holds all other panels
	private CardLayout cardLayout; //cardlayout is for switching between different views (the panels)
	
	
	//the constructor to set tup the frame and panels
	public GUI() {
		setTitle("Tourist Info - Home"); //setting up the title of the window
		setSize(600, 800); //size of the window
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //closes the application when the window is closed
		//setLayout(new BorderLayout());
		
		//initializes the card layout and the main panel that holds different panels
		cardLayout = new CardLayout();
		mainPanel = new JPanel(cardLayout);
		
		//add home to the main panel
		JPanel homePanel = createHomePanel(); //creates it
		mainPanel.add(homePanel, "Home"); //adds home panel to cardlayout with the name "home"
		
		//adding other panels (5 best ...)
		mainPanel.add(createPanelWithBackButton(new beachPanel(), "Beaches"), "Beaches");
	    mainPanel.add(createPanelWithBackButton(new parkPanel(), "Parks"), "Parks");
	    mainPanel.add(createPanelWithBackButton(new museumPanel(), "Museums"), "Museums");
	    mainPanel.add(createPanelWithBackButton(new landmarkPanel(), "Landmarks"), "Landmarks");
	    mainPanel.add(createPanelWithBackButton(new searchPanel(), "Search"), "Search");
		
		add(mainPanel, BorderLayout.CENTER); //adds the main panel to the center of the frame
		setVisible(true); //makes the window visible
		
		
		
	}
	
	
	
	private JPanel createHomePanel() {
		JPanel homePanel = new JPanel(); //panel to hold home content
		//homePanel.setLayout(new BorderLayout());
		homePanel.setLayout(new BoxLayout(homePanel, BoxLayout.Y_AXIS)); //vertical layout 
		homePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); //added padding
		
		
		//header 
		JLabel headerLabel = new JLabel("Time to take a trip!", JLabel.CENTER); //centers this
		headerLabel.setFont(new Font("Arial", Font.BOLD, 24)); //set the font of it
		headerLabel.setForeground(new Color(60, 90, 200)); //sets the color of it
		headerLabel.setAlignmentX(Component.CENTER_ALIGNMENT); //
		homePanel.add(headerLabel); //adds it to home
		
		//headerLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
		//homePanel.add(headerLabel, BorderLayout.NORTH);
		
		//add spacing between the elements
		homePanel.add(Box.createRigidArea(new Dimension(0, 20)));
		
		
		//search button
		JButton searchButton = new JButton("Search");
		searchButton.setFont(new Font("Arial", Font.PLAIN, 18)); //the font
		searchButton.setPreferredSize(new Dimension(250, 50)); //the size
		searchButton.setAlignmentX(Component.CENTER_ALIGNMENT); //center button
		searchButton.addActionListener(e -> showPanel("Search")); //navigate to the search panel
		homePanel.add(searchButton);
		
		//adding spacing again
		homePanel.add(Box.createRigidArea(new Dimension(0, 30)));
		
		//category buttons
		//JPanel categoryPanel = new JPanel(new GridLayout(2, 2, 10, 10));
		JPanel categoryPanel = new JPanel();
		categoryPanel.setLayout(new BoxLayout(categoryPanel, BoxLayout.Y_AXIS)); //vertical layout 
		categoryPanel.setAlignmentX(Component.CENTER_ALIGNMENT); //centers it 
		
		
		
		// Add extra space before the category panel
		categoryPanel.add(Box.createRigidArea(new Dimension(0, 60))); // Adds space between search button and category buttons

		// Row 1: Beaches and Parks
		JPanel row1Panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10)); // Row layout with spacing
		JButton beachesButton = new JButton("5 Best Beaches"); //button for beaches
		beachesButton.setPreferredSize(new Dimension(180, 40)); //button size
		styleButton(beachesButton); //style the button
		beachesButton.addActionListener(e -> showPanel("Beaches")); //action to switch to beaches panel

		JButton parksButton = new JButton("5 Best Parks"); //button for parks
		parksButton.setPreferredSize(new Dimension(180, 40)); //button size
		styleButton(parksButton);
		parksButton.addActionListener(e -> showPanel("Parks")); //action to switch

		row1Panel.add(beachesButton); //adds beaches button to row
		row1Panel.add(parksButton); //adds parks button to row

		// Row 2: Landmarks and Museums
		JPanel row2Panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5)); // Row layout with spacing
		JButton landmarksButton = new JButton("5 Best Landmarks"); //creates button
		landmarksButton.setPreferredSize(new Dimension(180, 40)); //button size
		styleButton(landmarksButton);
		landmarksButton.addActionListener(e -> showPanel("Landmarks")); //switches

		JButton museumsButton = new JButton("5 Best Museums"); //creates button
		museumsButton.setPreferredSize(new Dimension(180, 40)); //button size
		styleButton(museumsButton);
		museumsButton.addActionListener(e -> showPanel("Museums")); //switches

		row2Panel.add(landmarksButton); //adds landmarks button to row
		row2Panel.add(museumsButton); //add museums button to row

		// Add rows to the category panel
		categoryPanel.add(row1Panel);
		// Adjusted spacing between rows
		categoryPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Reducing space between row 1 and row 2
		categoryPanel.add(row2Panel);

		// Adds the category panel to the home panel
		homePanel.add(categoryPanel);
		
		return homePanel; //returns the completed home panel
	}
	
	private void styleButton(JButton button) {
		button.setFont(new Font("Arial", Font.PLAIN, 16)); //set the font style and size
		//button.setIcon(new ImageIcon(iconPath)); //replacing with actual image path
		button.setVerticalTextPosition(SwingConstants.BOTTOM); //aligning text at the bottom
		button.setHorizontalTextPosition(SwingConstants.CENTER); //made it center
	}
	
	//switches to the specified panel
	private void showPanel(String panelName) {
		cardLayout.show(mainPanel, panelName);
	}
	
	//wraps a given panel with a back button so the user can return to homepage
	private JPanel createPanelWithBackButton(JPanel panel, String panelName) {
		JPanel wrapperPanel = new JPanel(new BorderLayout()); //wrapper panel
		wrapperPanel.add(panel, BorderLayout.CENTER); //adds the given panel in the center
		
		//BACK BUTTON
		JButton backButton = new JButton("Back");
		backButton.setFont(new Font("Arial", Font.PLAIN, 14));
		backButton.addActionListener(e -> showPanel("Home")); //action to switch back to home
		JPanel backPanel = new JPanel(); //creates a panel for the back button
		backPanel.add(backButton); //adds the back button to the back panel
		wrapperPanel.add(backPanel, BorderLayout.SOUTH); //adds the back panel to the bottom of wrapper (screen)
		
		return wrapperPanel;
	}
	
	
	// Placeholder method to simulate page actions
    /*private void showPlaceholder(String pageName) {
        JOptionPane.showMessageDialog(this, "Opening: " + pageName, "Page Navigation", JOptionPane.INFORMATION_MESSAGE);
    }*/
    
	public static void main(String[] args){
		new GUI();
	}
}

