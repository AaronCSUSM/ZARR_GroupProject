package zarr;

import javax.swing.*;

import zarr.GUI.GradientButton;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

/**
 * 
 * @author Aaron Cambridge, Rebecca Hall, River Hallie, Zach Miller
 * @version 12/4/24
 */
public class searchPanel extends JPanel {
	//field for user input
    private JTextField cityField;
    private JTextField countryField;
    private JTextField stateField;
    private JTextField catField;
    //buttons
    private GradientButton searchButton;
    
    //members needed to jump between panels
    private JPanel resultsPanel;
    private APIClient ac;
    private DatabaseClient dc;

	 /**
	  * This method creates the search panel for user input.
	  * @param ac API client object
	  * @param dc Database client object
	  */
    public searchPanel(APIClient ac, DatabaseClient dc) {
        this.ac = ac;
        this.dc = dc;

        //creates a container seperated into North, East, South, West, and Center
        setLayout(new BorderLayout());
        
        //creats an empty border around entire panel so components are rnning into window edge
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        // Input Panel where text text fields and search button will go
        JPanel inputPanel = new JPanel(new BorderLayout());
        
        //left side of input panel for text fields
        //(4 rows, two columns, 5 horizont space between each, 5 vertical space)
        JPanel fieldsPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        
        //creats fields with width of 15 columns
        cityField = new JTextField(15);
        countryField = new JTextField(15);
        stateField = new JTextField(15);
        catField = new JTextField(15);

        //adds labels for each input field, thens adds them to the fieldsPanel
        fieldsPanel.add(new JLabel("City:"));
        fieldsPanel.add(cityField);
        fieldsPanel.add(new JLabel("Country:"));
        fieldsPanel.add(countryField);
        fieldsPanel.add(new JLabel("State (if in USA):"));
        fieldsPanel.add(stateField);
        fieldsPanel.add(new JLabel("Category:"));
        fieldsPanel.add(catField);

        //once fieldsPanel has everything on it, we add it to the inputPanel
        inputPanel.add(fieldsPanel, BorderLayout.CENTER);
        
        //change color of searchButton
        Color imessageBlue = new Color(8, 127, 254);
		Color shinyBlue = new Color(143, 197, 255);
		searchButton = new GradientButton("Search", shinyBlue, imessageBlue);
        // searchButton = new GradientButton("Search");
        inputPanel.add(searchButton, BorderLayout.EAST);//add the button to the east side of the inputPanel
        //add the inputpanel into the overall panel, North side
        add(inputPanel, BorderLayout.NORTH);

        // Results Panel
        resultsPanel = new JPanel();//create a new panel for the API results
        resultsPanel.setBackground(Color.BLUE);//change color of this panel to CYAN
        //set the layout of this panel so that components will stack on top of each other vertically
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        //add a scroll to the right side that pops up when necessary for vertical scrolling
        JScrollPane resultsScrollPane = new JScrollPane(resultsPanel);
        resultsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        resultsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        //scroll pane is added to center side of BorderLayout
        add(resultsScrollPane, BorderLayout.CENTER);

        // Search Button Action that calls the actionPerformed method
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performSearchAndUpdateResults();
            }
        });
    }

    //clicking the button will send the API request, create a panel for each button and add it to results panel
    private void performSearchAndUpdateResults() {
    	//clears results from previous search
        resultsPanel.removeAll();

        //the api category search will return an array of LocDetails
        LocDetails[] lds = new LocDetails[20];
        
        //Will not commence serach unless user has enter city, country, and category. State is optional
        if (cityField.getText().isEmpty() || countryField.getText().isEmpty() || catField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a city, country, and category.");
            return;
        }

        //collect the user input from the text fields
        String city = cityField.getText();
        String country = countryField.getText();
        String category = catField.getText();
        String state = stateField.getText().isEmpty() ? null : stateField.getText();

        //stroes api search results into lds
        lds = performSearch(city, country, state, category);

       
        
        //loops through the array and creates a new panel for each result
        for(int i = 0; i < lds.length; i++) {
        	if(lds[i] != null) {
        		//create new panel for each result and adds it to the resultsPanel
        		resultsPanel.add(createResultPanel(lds[i]));
        		//add space between each result panel
                resultsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        	}
        }
        
        //recalculate the spacing, components, and various panels after adding a bunch of new ones
        resultsPanel.revalidate();
        //make sure GUID is still being displayed as its suppose to
        resultsPanel.repaint();
    }

	/**
	 * This method takes in the passed arguments, and conducts the nearbysearch API call and returns the results in a local details array.
	 * @param city name of city
	 * @param country name of country 
	 * @param state name of state
	 * @param category name of category
	 * @return ld (local details array)
	 */
    
    private LocDetails[] performSearch(String city, String country, String state, String category) {
    	boolean cityFound = false;
		double[]c = new double[2];//double array for city coordinates
		LocDetails[] ld = new LocDetails[20];//array for the API request results
		
		
		try {
			//searches for city first in database, if its in database just perform search
			if(dc.findCity(city, country, state)) {
				//retrieve coordinates from database
				c = dc.getCoordinates(city, country, state);
				//dc.addCity(city, country, state, c[0],  c[1]);
				System.out.println("Latitude: " + c[0]);
				System.out.println("Longitude: " + c[1]);
				//perform the API request
				System.out.println("Performing search for " + city + ", " + country + " with " + category + " as keyword.");
				try {
					//perform category request and store results in ld array
					ld = ac.categoryRequest(c[0],  c[1],  category);
					LocDetails.printLocDetails(ld);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			//if the city was not in the database, have to get the coordinates first
			}else {
				try {
					//API geocoding request
					c = ac.geocodingRequest(city, country, state);
					System.out.println("Latitude: " + c[0]);
					System.out.println("Longitude: " + c[1]);
					//add the new city to the database
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

    /**
     * Method for creating a results panel which lists the results of a given search
     * @param loc local details object
     * @return resultPanel
     */
    //create the results panel where search results are displayed
    private JPanel createResultPanel(LocDetails loc) {
    	//create new JPanel with a BorderLayout(north, east, south, west, center)
        JPanel resultPanel = new JPanel(new BorderLayout());
        resultPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE));//change border color to blue
        resultPanel.setBackground(Color.BLUE);//set background color to blue

        //create a new panel for inserting the text panel results
        //each result goes on its own textPanel, and each textPanel is added to the results panel
        JPanel textPanel = new JPanel();
        //sets textPanel to have boxlayout so all component stack vertically
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        //seperates each textPanel with an empty border so they aren't running together
        textPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        //change color of the textPanels to CYAN
        textPanel.setBackground(Color.CYAN);

        //adds Name and Status labels to text box
        JLabel nameLabel = new JLabel("Name: " + loc.getName());
        JLabel statusLabel = new JLabel("Operational Status: " + loc.getStatus());
        
        //everything has had a rating so far, but give it an alternative just in case
        String ratingText = "";
        if(loc.getRating() != -999) {
        	ratingText = String.valueOf(loc.getRating());
        }else {
        	ratingText = "---";
        }
        
        
        //Replace regular ints with dollar signs to make it more visually appealing
        String priceText = "";
        if(loc.getPriceLevel() != -999) {
        	if(loc.getPriceLevel() <= 1) {
        		priceText = "$";
        	}else if(loc.getPriceLevel() == 2) {
        		priceText = "$$";
        	}else if(loc.getPriceLevel() == 3) {
        		priceText = "$$$";
        	}else {
        		priceText = "$$$$";
        	}
        }else {
        	priceText = "---";
        }
        
        
        //add remaining labels
        JLabel ratingLabel = new JLabel("Rating: " + ratingText);
        JLabel priceLabel = new JLabel("Price Level: " + priceText);
        //priceLabel.setForeground(Color.GREEN);
        
        //add all the labels to the textPanel
        textPanel.add(nameLabel);
        textPanel.add(statusLabel);
        textPanel.add(ratingLabel);
        textPanel.add(priceLabel);

        //add the textPanel to the resultsPanel in the center
        resultPanel.add(textPanel, BorderLayout.CENTER);
        return resultPanel;
    }

	/**
	 * Method that enables the explore button within the category panels. This populates the search field so that a user can further explore
	 * a particular area found in a category panel.
	 * @param city name of city
	 * @param country name of country
	 * @param state name of state
	 * @param category name of category
	 */
    //Used with the explore buttons, will prefill text fields for user if they click them
    public void setSearchFields(String city, String country, String state, String category) {
        this.cityField.setText(city);
        this.countryField.setText(country);
        this.catField.setText(category);
        if(state != null) {
        	this.stateField.setText(state);
        }else {
        	this.stateField.setText("");
        }

    }
}
