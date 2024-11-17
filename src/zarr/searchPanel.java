package zarr;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


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
		JPanel inputPanel = new JPanel(new BorderLayout());
        JPanel fieldsPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        // Add padding to the inputPanel by wrapping it in a separate panel
        // Right side (button) using FlowLayout
	    

		
		//searchField = new JTextField(20);
		//new changes
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
		
		
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
		searchButton = new JButton("Search");
	    buttonPanel.add(searchButton);
		inputPanel.add(buttonPanel, BorderLayout.EAST);
		
		
		
	    
        JPanel northPadPanel = new JPanel(new BorderLayout());
        northPadPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // 10px padding
        northPadPanel.add(inputPanel, BorderLayout.WEST);
        northPadPanel.add(buttonPanel, BorderLayout.EAST);
        add(northPadPanel, BorderLayout.NORTH);
		//searchBar.add(searchField);
		//searchBar.add(cityField);
		//searchBar.add(countryField);
		//searchBar.add(stateField);
		//searchBar.add(catField);
		
		
		
		
		//results area
		resultsArea = new JTextArea(10, 30);
		resultsArea.setEditable(false);
		add(new JScrollPane(resultsArea), BorderLayout.CENTER);
		
		//button action
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//String searchText = searchField.getText();
				
				if(cityField.getText().isEmpty() || countryField.getText().isEmpty() || catField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(searchPanel.this, "Please enter a city, country, and category.");
				}
				String userCity = cityField.getText();
				String userCountry = countryField.getText();
				String userState = stateField.getText();
				String userCat = catField.getText();
				
				
				//if(cityField.isEmpty()) {
					//performSearch(searchText);
				//}else {
					//JOptionPane.showMessageDialog(searchPanel.this, "Please enter a search term.");
				//}
			}
		});
	}
	
	private void performSearch(String searchText) {
		//placeholder for api call logic
		resultsArea.setText("Searching for: " + searchText + "\nResults:\n1. Example Result 1\n2. Example Result 2");
	}
}
