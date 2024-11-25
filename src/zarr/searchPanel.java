package zarr;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;


public class searchPanel extends JPanel{
	 private JTextField searchField;
	 private JButton searchButton;
	 private JTextArea resultsArea;
	 
	 //new changes
	 private JTextField cityField;
	 private JTextField countryField;
	 private JTextField stateField;
	 private JTextField catField;
	 private APIClient ac;
	 private DatabaseClient dc;
	
	public searchPanel(APIClient ac, DatabaseClient dc) {
		this.ac = ac;
		this.dc = dc;
		
		//BorderLayout divides entire panel into North, East, South, West, and Center
		setLayout(new BorderLayout());
		//convert panel into a grid (numRows, numCol, space between col, space between row)
		//new GridLayout(2, 1, 5, 5);
		
		//top search bar
		
		//input panel is top half of searchPanel
		JPanel inputPanel = new JPanel(new BorderLayout());
		//fieldsPanel is left (west) side of inputpanel where user will input data
        JPanel fieldsPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        // Add padding to the inputPanel by wrapping it in a separate panel
        // Right side (button) using FlowLayout
	    

		
		//searchField = new JTextField(20);
		//new changes
        
        //add in text fields for user input
		cityField = new JTextField(15);
		countryField = new JTextField(15);
		stateField = new JTextField(15);
		catField = new JTextField(15);
		
		//add each text field to the fields panel
		fieldsPanel.add(new JLabel("City:"));
		fieldsPanel.add(cityField);
		fieldsPanel.add(new JLabel("Country:"));
		fieldsPanel.add(countryField);
		fieldsPanel.add(new JLabel("State (if in USA):"));
		fieldsPanel.add(stateField);
		fieldsPanel.add(new JLabel("Category:"));
		fieldsPanel.add(catField);
		
		//fields panel now has necessary components, add it to inputPanel
		inputPanel.add(fieldsPanel, BorderLayout.CENTER);
		
		//buttonPanel is right (east) side of inputPanel, just holds search button
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		//create a new button
		searchButton = new JButton("Search");
		//add the button to buttonPanel
	    buttonPanel.add(searchButton);
	    //add buttonPanel to east side of inputPanel
		inputPanel.add(buttonPanel, BorderLayout.EAST);
		
	    //inputpanel was 
        JPanel NorthPadPanel = new JPanel(new BorderLayout());
        NorthPadPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // 10px padding
        NorthPadPanel.add(inputPanel, BorderLayout.WEST);
        NorthPadPanel.add(buttonPanel, BorderLayout.EAST);
        add(NorthPadPanel, BorderLayout.NORTH);
		//searchBar.add(searchField);
		//searchBar.add(cityField);
		//searchBar.add(countryField);
		//searchBar.add(stateField);
		//searchBar.add(catField);
		
		
		
		
		//results area
        JPanel SouthPadPanel = new JPanel(new BorderLayout());
        SouthPadPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));;
		resultsArea = new JTextArea(10, 30);
		resultsArea.setEditable(true);
		Font f = new Font( "Monospaced", Font.PLAIN, 12 ); 
		resultsArea.setFont(f);
		
		JScrollPane jsp = new JScrollPane(resultsArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		
		SouthPadPanel.add(jsp, BorderLayout.CENTER);
		add(SouthPadPanel, BorderLayout.CENTER);
		//add(new JScrollPane(resultsArea), BorderLayout.CENTER);
		
		//button action
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//String searchText = searchField.getText();
				LocDetails[] lds = new LocDetails[20];
				if(cityField.getText().isEmpty() || countryField.getText().isEmpty() || catField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(searchPanel.this, "Please enter a city, country, and category.");
				}else {
					String userCity = cityField.getText();
					String userCountry = countryField.getText();
					String userCat = catField.getText();
					if(stateField.getText().isEmpty()) {
						lds = performSearch(userCity, userCountry, null, userCat);
					}else {
						String userState = stateField.getText();
						lds = performSearch(userCity, userCountry, userState, userCat);
					}//end inner if
				}//end if
				
				//if(cityField.isEmpty()) {
					//performSearch(searchText);
				//}else {
					//JOptionPane.showMessageDialog(searchPanel.this, "Please enter a search term.");
				//}
				resultsArea.setText(LocDetails.LocDetailsToString(lds));
			}//end actionPerformed
		});//end addActionListener
		
	}//end searchPanel
	
	private LocDetails[] performSearch(String city, String country, String state, String category) {
		//placeholder for api call logic
		//resultsArea.setText("Searching for: " + searchText + "\nResults:\n1. Example Result 1\n2. Example Result 2");
		//boolean cityFound = false;
		double[]c = new double[2];
		LocDetails[] ld = new LocDetails[20];
		
		try {
			if(dc.findCity(city, country, state)) {
				//retrieve coordinates from database
				c = dc.getCoordinates(city, country, state);
				//dc.addCity(city, country, state, c[0],  c[1]);
				System.out.println("Latitude: " + c[0]);
				System.out.println("Longitude: " + c[1]);
				//perform the API request
				System.out.println("Performing search for " + city + ", " + country + " with " + category + " as keyword.");
				try {
					ld = ac.categoryRequest(c[0],  c[1],  category);
					LocDetails.printLocDetails(ld);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else {
				try {
					c = ac.geocodingRequest(city, country, state);
					System.out.println("Latitude: " + c[0]);
					System.out.println("Longitude: " + c[1]);
					dc.addCity(city, country, state, c[0],  c[1]);
					System.out.println("Performing search for " + city + ", " + country + " with " + category + " as keyword.");
					try {
						ld = ac.categoryRequest(c[0],  c[1],  category);
						LocDetails.printLocDetails(ld);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//System.out.println("Search results: " + cityFound);
		return ld;
	}//end performSearch
}//end searchPanel
