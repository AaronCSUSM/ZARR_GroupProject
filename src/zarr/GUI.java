package zarr;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GUI extends JFrame {
	public GUI() {
		setTitle("Tourist Info - Home");
		setSize(600, 800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		
		//Header
		JLabel headerLabel = new JLabel("Time to take a trip!", JLabel.CENTER);
		headerLabel.setFont(new Font("Arial", Font.BOLD, 24));
		//headerLabel.setForeground(Color.BLUE);
		headerLabel.setForeground(new Color(60, 90, 200));
		headerLabel.setBorder(BorderFactory.createEmptyBorder(20,10,10,10));
		add(headerLabel, BorderLayout.NORTH);
		
		//Main Panel
		JPanel mainPanel = new JPanel();
		//mainPanel.setLayout(new GridLayout(7, 1, 10, 10)); //rows for header, the two main buttons, and then the category buttons
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
		
		//adding images
		JLabel imageLabel = new JLabel(new ImageIcon("header.jpg"));
		imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
		mainPanel.add(imageLabel);
		
		//panel for search and recommendation buttons side by side
		JPanel topButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 20));
		
		JButton searchButton = new JButton("Search");
		searchButton.setFont(new Font("Arial", Font.PLAIN, 18));
		searchButton.setPreferredSize(new Dimension(250, 60));
		searchButton.setIcon(new ImageIcon("search.png")); //replacing with search icon file
		
		JButton recommendationButton = new JButton("Random Recommendation");
		recommendationButton.setFont(new Font("Arial", Font.PLAIN, 18));
		recommendationButton.setPreferredSize(new Dimension(270,60));
		recommendationButton.setIcon(new ImageIcon("recommend.png"));
		
		topButtonsPanel.add(searchButton);
		topButtonsPanel.add(recommendationButton);
		
		mainPanel.add(topButtonsPanel);
		
		//spacing between sections
		mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));
		
		//panel for category buttons with a smaller size
		
		JPanel categoriesPanel = new JPanel(new GridLayout(2, 2, 15, 15));
		
		
		
		//Main buttons
		/*
		JLabel searchLabel = new JLabel("Already have something in mind?", JLabel.CENTER);
		JButton searchButton = new JButton("Search");
		JButton recommendationButton = new JButton("Random Recommendations");
		*/
		
		
		//Category buttons
		JButton beachesButton = new JButton("5 Best Beaches");
		JButton amusementParksButton = new JButton("5 Best Amusement Parks");
		JButton landmarksButton = new JButton("5 Best Landmarks");
		JButton museumsButton = new JButton("5 Best Museuems");
		
		//adding componets to the panel
		/*mainPanel.add(headerLabel);
		mainPanel.add(searchLabel);
		mainPanel.add(searchButton);
		mainPanel.add(recommendationButton);
		mainPanel.add(beachesButton);
		mainPanel.add(amusementParksButton);
		mainPanel.add(landmarksButton);
		mainPanel.add(museumsButton);*/
		
		styleCategoryButton(beachesButton, "beaches.png");
		styleCategoryButton(amusementParksButton, "amusement.png");
		styleCategoryButton(landmarksButton, "landmarks.png");
		styleCategoryButton(museumsButton, "museums.png");
		
		categoriesPanel.add(beachesButton);
		categoriesPanel.add(amusementParksButton);
		categoriesPanel.add(landmarksButton);
		categoriesPanel.add(museumsButton);
		
		mainPanel.add(categoriesPanel);
		
		//Button actions
		/*searchButton.addActionListener(e -> openSearchPage());
		recommendationButton.addActionListener(e -> openRecommendationPage());
		beachesButton.addActionListener(e -> showTopBeaches());
		amusementParksButton.addActionListener(e -> showTopAmusementParks());
		landmarksButton.addActionListener(e -> showTopLandmarks());
		museumsButton.addActionListner(e -> showTopMuseums());
		*/
		
		// Button Actions with Placeholders
        searchButton.addActionListener(e -> showPlaceholder("Search Page"));
        recommendationButton.addActionListener(e -> showPlaceholder("Random Recommendations Page"));
        beachesButton.addActionListener(e -> showPlaceholder("Top 5 Beaches"));
        amusementParksButton.addActionListener(e -> showPlaceholder("Top 5 Amusement Parks"));
        landmarksButton.addActionListener(e -> showPlaceholder("Top 5 Landmarks"));
        museumsButton.addActionListener(e -> showPlaceholder("Top 5 Museums"));

		
		add(mainPanel, BorderLayout.CENTER);
		setVisible(true);
		
	}
	
	private void styleCategoryButton(JButton button, String iconPath) {
		button.setFont(new Font("Arial", Font.PLAIN, 16));
		button.setIcon(new ImageIcon(iconPath)); //replaces with the correct image path
		button.setVerticalTextPosition(SwingConstants.BOTTOM);
		button.setHorizontalTextPosition(SwingConstants.CENTER);
	}
	
	// Placeholder method to simulate page actions
    private void showPlaceholder(String pageName) {
        JOptionPane.showMessageDialog(this, "Opening: " + pageName, "Page Navigation", JOptionPane.INFORMATION_MESSAGE);
    }
    
	public static void main(String[] args){
		new GUI();
	}
}

