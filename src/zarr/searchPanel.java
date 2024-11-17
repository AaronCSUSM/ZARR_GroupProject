package zarr;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class searchPanel extends JPanel{
	 private JTextField searchField;
	 private JButton searchButton;
	 private JTextArea resultsArea;
	
	public searchPanel() {
		setLayout(new BorderLayout());
		
		//top search bar
		JPanel searchBar = new JPanel(new FlowLayout());
		searchField = new JTextField(20);
		searchButton = new JButton("Search");
		searchBar.add(searchField);
		searchBar.add(searchButton);
		
		add(searchBar, BorderLayout.NORTH);
		
		//results area
		resultsArea = new JTextArea(10, 30);
		resultsArea.setEditable(false);
		add(new JScrollPane(resultsArea), BorderLayout.CENTER);
		
		//button action
		searchButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String searchText = searchField.getText();
				if(!searchText.isEmpty()) {
					performSearch(searchText);
				}else {
					JOptionPane.showMessageDialog(searchPanel.this, "Please enter a search term.");
				}
			}
		});
	}
	
	private void performSearch(String searchText) {
		//placeholder for api call logic
		resultsArea.setText("Searching for: " + searchText + "\nResults:\n1. Example Result 1\n2. Example Result 2");
	}
}
