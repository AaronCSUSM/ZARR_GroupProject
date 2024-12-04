package zarr;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class searchPanel extends JPanel {
    private JTextField cityField;
    private JTextField countryField;
    private JTextField stateField;
    private JTextField catField;
    private JButton searchButton;
    private JPanel resultsPanel;
    private APIClient ac;
    private DatabaseClient dc;

    public searchPanel(APIClient ac, DatabaseClient dc) {
        this.ac = ac;
        this.dc = dc;

        setLayout(new BorderLayout());
        
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        // Input Panel
        JPanel inputPanel = new JPanel(new BorderLayout());
        
        JPanel fieldsPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        
        cityField = new JTextField(15);
        countryField = new JTextField(15);
        stateField = new JTextField(15);
        catField = new JTextField(15);

        fieldsPanel.add(new JLabel("City:"));
        fieldsPanel.add(cityField);
        fieldsPanel.add(new JLabel("Country:"));
        fieldsPanel.add(countryField);
        fieldsPanel.add(new JLabel("State (if in USA):"));
        fieldsPanel.add(stateField);
        fieldsPanel.add(new JLabel("Category:"));
        fieldsPanel.add(catField);

        inputPanel.add(fieldsPanel, BorderLayout.CENTER);
        

        searchButton = new JButton("Search");
        inputPanel.add(searchButton, BorderLayout.EAST);
        add(inputPanel, BorderLayout.NORTH);

        // Results Panel
        resultsPanel = new JPanel();
        //resultsPanel.setBackground(Color.CYAN);
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        JScrollPane resultsScrollPane = new JScrollPane(resultsPanel);
        resultsScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        resultsScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(resultsScrollPane, BorderLayout.CENTER);

        // Search Button Action
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performSearchAndUpdateResults();
            }
        });
    }

    private void performSearchAndUpdateResults() {
        resultsPanel.removeAll();

        LocDetails[] lds = new LocDetails[20];
        if (cityField.getText().isEmpty() || countryField.getText().isEmpty() || catField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a city, country, and category.");
            return;
        }

        String city = cityField.getText();
        String country = countryField.getText();
        String category = catField.getText();
        String state = stateField.getText().isEmpty() ? null : stateField.getText();

        lds = performSearch(city, country, state, category);

        for (LocDetails ld : lds) {
            if (ld != null) {
                resultsPanel.add(createResultPanel(ld));
                resultsPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add spacing
            }
        }
        resultsPanel.revalidate();
        resultsPanel.repaint();
    }

    private LocDetails[] performSearch(String city, String country, String state, String category) {
    	boolean cityFound = false;
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

    private JPanel createResultPanel(LocDetails loc) {
        JPanel resultPanel = new JPanel(new BorderLayout());
        resultPanel.setBorder(BorderFactory.createLineBorder(Color.BLUE));
        resultPanel.setBackground(Color.BLUE);

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        textPanel.setBackground(Color.CYAN);

        JLabel nameLabel = new JLabel("Name: " + loc.getName());
        JLabel statusLabel = new JLabel("Operational Status: " + loc.getStatus());
        
        String ratingText = "";
        if(loc.getRating() != -999) {
        	ratingText = String.valueOf(loc.getRating());
        }else {
        	ratingText = "---";
        }
        
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
        
        JLabel ratingLabel = new JLabel("Rating: " + ratingText);
        JLabel priceLabel = new JLabel("Price Level: " + priceText);

        textPanel.add(nameLabel);
        textPanel.add(statusLabel);
        textPanel.add(ratingLabel);
        textPanel.add(priceLabel);

        resultPanel.add(textPanel, BorderLayout.CENTER);
        return resultPanel;
    }

    public void setSearchFields(String city, String country, String state, String category) {
        this.cityField.setText(city);
        this.countryField.setText(country);
        this.catField.setText(category);
        if(state != null) {
        	this.stateField.setText(state);
        }else {
        	this.stateField.setText("");
        }
        //this.stateField.setText(state != null ? state : "");
    }
}
