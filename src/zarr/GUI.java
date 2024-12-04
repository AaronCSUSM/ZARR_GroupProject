package zarr;

import javax.swing.*;

import zarr.GUI.GradientButton;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/*import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.net.URL;
import java.net.MalformedURLException;*/


/**
 * 
 * @author Aaron Cambridge, Rebecca Hall, River Hallie, Zach Miller
 * @version 12/4/24
 *
 */
public class GUI extends JFrame {
	private JPanel mainPanel; //the main panel that holds all other panels
	private CardLayout cardLayout; //cardlayout is for switching between different views (the panels)
	
	private APIClient ac;
	private DatabaseClient dc;
	
	/**
	 * Constructor to set up the frame and panels for GUI
	 * @param ac API client object
	 * @param dc Database client object
	 */
	public GUI(APIClient ac, DatabaseClient dc) {
		this.ac = ac;
		this.dc = dc;
		
		setTitle("DiscoverEase - Home"); //setting up the title of the window
		setSize(900, 650); //size of the window  //600, 800
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //closes the application when the window is closed
		//setLayout(new BorderLayout());
		
		//initializes the card layout and the main panel that holds different panels
		cardLayout = new CardLayout();
		mainPanel = new JPanel(cardLayout);
		
		//add home to the main panel
		JPanel homePanel = createHomePanel(); //creates it
		mainPanel.add(homePanel, "Home"); //adds home panel to cardlayout with the name "home"
		
		//adding other panels (5 best ...)
		searchPanel sp = new searchPanel(this.ac, this.dc);
		mainPanel.add(createPanelWithBackButton(sp, "Search"), "Search");
		mainPanel.add(createPanelWithBackButton(new beachPanel(sp, cardLayout, mainPanel), "Beaches"), "Beaches");
	    mainPanel.add(createPanelWithBackButton(new parkPanel(sp, cardLayout, mainPanel), "Parks"), "Parks");
	    mainPanel.add(createPanelWithBackButton(new museumPanel(sp, cardLayout, mainPanel), "Museums"), "Museums");
	    mainPanel.add(createPanelWithBackButton(new landmarkPanel(), "Landmarks"), "Landmarks");
	    //mainPanel.add(createPanelWithBackButton(new searchPanel(this.ac, this.dc), "Search"), "Search");
		
		add(mainPanel, BorderLayout.CENTER); //adds the main panel to the center of the frame
		setVisible(true); //makes the window visible
		
		
		
	}
	
	
	/**
	 * Method to create the home panel for the GUI
	 * @return homePanel
	 */
	private JPanel createHomePanel() {
		// Creates the home panel that has a modern gradient background
	    JPanel homePanel = new JPanel() {
	        @Override
	        protected void paintComponent(Graphics g) {
	            super.paintComponent(g);
	            Graphics2D g2d = (Graphics2D) g;

	            // Applying gradient background
	            Color color1 = new Color(34, 47, 62); // Darker tone
	            Color color2 = new Color(52, 152, 219); // Lighter tone to create contrast
	            GradientPaint gradient = new GradientPaint(0, 0, color1, getWidth(), getHeight(), color2);
	            g2d.setPaint(gradient);
	            g2d.fillRect(0, 0, getWidth(), getHeight());
	        }
	    };
	    
	    // Set layout and add padding
	    homePanel.setLayout(new BoxLayout(homePanel, BoxLayout.Y_AXIS)); // Vertical layout
	    homePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Padding around content
	    
	    // Create and customize the header label
	    JLabel headerLabel = new JLabel("Your Next Adventure, Discovered With Ease!", JLabel.CENTER);
	    headerLabel.setFont(new Font("Edwardian Script ITC", Font.BOLD, 50)); // Slightly larger, bold font
	    headerLabel.setForeground(new Color(255, 255, 255)); // White color for text
	    headerLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center the label
	    homePanel.add(headerLabel);
	    
	    // Add spacing between header and search button
	    homePanel.add(Box.createRigidArea(new Dimension(0, 40)));
	    
	    // Create and style the search button
	    // JButton searchButton = new JButton("Search");
	    Color imessageBlue = new Color(8, 127, 254);
		Color shinyBlue = new Color(143, 197, 255);
		GradientButton searchButton = new GradientButton("Search", shinyBlue, imessageBlue);
	    // searchButton.setFont(new Font("Segoe UI", Font.PLAIN, 20)); // Font for the button
	    searchButton.setPreferredSize(new Dimension(250, 50)); // Set the size
	    searchButton.setAlignmentX(Component.CENTER_ALIGNMENT); // Center button
	    // searchButton.setBackground(new Color(52, 152, 219)); // Matching blue background
	    // searchButton.setForeground(Color.WHITE); // White text
	    searchButton.setFocusPainted(false); // Removes focus border
	    // searchButton.setBorder(BorderFactory.createLineBorder(new Color(52, 152, 219), 2)); // Border to match the color
	    searchButton.addActionListener(e -> showPanel("Search")); // Navigate to the search panel
	    homePanel.add(searchButton);
	    
	    // Add spacing between the search button and category buttons
	    homePanel.add(Box.createRigidArea(new Dimension(0, 40)));

	    // Create and style the category panel for buttons
	    JPanel categoryPanel = new JPanel();
	    categoryPanel.setLayout(new BoxLayout(categoryPanel, BoxLayout.Y_AXIS)); // Vertical layout
	    categoryPanel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center panel
	    
	    // Add space before the category panel
	    categoryPanel.add(Box.createRigidArea(new Dimension(0, 60)));

	    // Create Row 1: Beaches and Parks buttons
	    JPanel row1Panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 80, 10)); // FlowLayout for center alignment
	    // JButton beachesButton = new JButton("View Popular Beaches");
		Color lightBlue = new Color(84, 170, 216);
        Color sandBrown = new Color(178, 131, 72);
		GradientButton beachesButton = new GradientButton("View Popular Beaches", lightBlue, sandBrown);
	    styleButton(beachesButton); // Applying styling to the button
	    beachesButton.addActionListener(e -> showPanel("Beaches"));
	    
	    
	    // JButton parksButton = new JButton("View Popular Parks");
	    Color forestGreen = new Color(116, 206, 90);
		Color barkBrown = new Color(115, 90, 54);
		GradientButton parksButton = new GradientButton("View Popular Amusement Parks", forestGreen, barkBrown);
	    styleButton(parksButton); // Apply styling to the button
	    parksButton.addActionListener(e -> showPanel("Parks"));
	    
	    row1Panel.add(beachesButton);
	    row1Panel.add(parksButton);
	    
	    // Create Row 2: Landmarks and Museums buttons
	    JPanel row2Panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 80, 10)); // Similar layout as row1
	    // JButton landmarksButton = new JButton("View Popular Landmarks");
	    Color concreteGray = new Color(168, 168, 168);
		Color red = new Color(222, 91, 91);
		GradientButton landmarksButton = new GradientButton("View Popular Landmarks", concreteGray, red);
	    styleButton(landmarksButton);
	    landmarksButton.addActionListener(e -> showPanel("Landmarks"));
	    
	    // JButton museumsButton = new JButton("View Popular Museums");
	    Color baroqueGold = new Color(220, 194, 53);
		Color offWhite = new Color(255, 253, 208);
		GradientButton museumsButton = new GradientButton("View Popular Museums", offWhite, baroqueGold);
	    styleButton(museumsButton);
	    museumsButton.addActionListener(e -> showPanel("Museums"));
	    
	    row2Panel.add(landmarksButton);
	    row2Panel.add(museumsButton);
	    
	    // Add rows to the category panel
	    categoryPanel.add(row1Panel);
	    categoryPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Spacing between rows
	    categoryPanel.add(row2Panel);
	    
	    // Add the category panel to the home panel
	    homePanel.add(categoryPanel);
	    
	    return homePanel; // Return the home panel
	}
		
	/**
	 * Method to style GUI buttons
	 * @param button JButton object
	 */
	private void styleButton(GradientButton button) {
		button.setFont(new Font("Segoe UI", Font.PLAIN, 16)); //set the font style and size
		//button.setIcon(new ImageIcon(iconPath)); //replacing with actual image path
		// button.setBackground(new Color(52, 152, 219));
		// button.setForeground(Color.WHITE);
		// button.setFocusPainted(false);
		// button.setBorderPainted(false);
		// button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
		button.setPreferredSize(new Dimension(270, 50));

		button.setCursor(new Cursor(Cursor.HAND_CURSOR));
		
		//hover effect
		button.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseEntered(java.awt.event.MouseEvent evt) {
			    // button.setBackground(new Color(41, 128, 185)); //darker blue on hover
				

			}
			public void mouseExited(java.awt.event.MouseEvent evt) {
				button.setBackground(new Color(52, 152, 219)); //reseting to original 
				
			}
		});
		//button.setVerticalTextPosition(SwingConstants.BOTTOM); //aligning text at the bottom
		//button.setHorizontalTextPosition(SwingConstants.CENTER); //made it center
	}
	
	/**
	 * 
	 * @author Zach Miller, River Hallie
	 * @version 12/4/24
	 */
	static class GradientButton extends JButton {
        private Color color1, color2;
        // Constructor to set gradient colors
        public GradientButton(String text, Color color1, Color color2) {
            super(text);
            this.color1 = color1;
            this.color2 = color2;
            setFocusPainted(false); // Remove focus border when clicked
            setContentAreaFilled(false); // To avoid default button background
            setFont(new Font("Arial", Font.BOLD, 16)); // Set the font
        }
        // Override paintComponent to draw the gradient
        @Override
        protected void paintComponent(Graphics g) {
            if (getModel().isPressed()) {
                // Darker colors when button is pressed
                g.setColor(color1.darker());
            } else {
                // Gradient fill for button
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gradient = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
            // Paint the text over the button
            super.paintComponent(g);
        }
    }
	
	/**
	 * Method to switch to the specified panel.
	 * @param panelName name of panel
	 */
	private void showPanel(String panelName) {
		cardLayout.show(mainPanel, panelName);
	}
	
	/**
	 * Method to wraps a given panel with a back button so the user can return to the homepage.
	 * @param panel JPanel object
	 * @param panelName name of panel
	 * @return wrapperPanel
	 */
	private JPanel createPanelWithBackButton(JPanel panel, String panelName) {
		JPanel wrapperPanel = new JPanel(new BorderLayout()); //wrapper panel
		wrapperPanel.add(panel, BorderLayout.CENTER); //adds the given panel in the center
		
		//BACK BUTTON
		// JButton backButton = new JButton("Back");
		Color lightRed = new Color(230, 77, 77);
		Color darkRed = new Color(204, 0, 0);
		GradientButton backButton = new GradientButton("Back", lightRed, darkRed);
		backButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
		backButton.setBackground(new Color(52, 152, 219));
		backButton.setForeground(Color.WHITE);
		backButton.setFocusPainted(false);
		backButton.setBorderPainted(false);
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
    
	/**
	 * Main Method to run GUI
	 * @param args
	 */
	public static void main(String[] args){
		APIClient ac = new APIClient();
		DatabaseClient dc = new DatabaseClient();
		new GUI(ac, dc);
	}
}