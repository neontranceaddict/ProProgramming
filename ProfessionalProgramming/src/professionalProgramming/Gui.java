/**Ashton Nicholson 13 Oct 2020
 * 
 */
package professionalProgramming;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.*;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;

/**
 * @author Ashton Nicholson 13 Oct 2020 
 * @version 1.0 
 * Using JSoup 1.13.1 Java -  HTML Parser - https://jsoup.org/
 * 
 * GUI class used for the main Graphic interface.
 * 
 */

public class Gui implements ActionListener {

	static JTextField searchField = new JTextField(60);
	static JLabel titleField = new JLabel("");
	static JTextArea linkField = new JTextArea(20, 80);
	static JButton submitSearch = new JButton("Search");
	static String exportData;
	static String titleOfPage;
	static String webPageAddress;
	public static boolean foundPage = false; // Used to allow for the export of data no page will cause issues we must
												// always assume we havent got a page.

	public void guiLoad(String title, int sizeX, int sizeY) {
		
		/**
		 * @param title - (String) The name that is used on the window of the GUI. 
		 * @param sizeX - (Int) Value of the windows X size.
		 * @param sizeY - (Int) Value of the windows Y size.
		 * 	
		 * Sets up the GUI and all functionality items.	
		 */
		
		// New window for GUI set up the declarations
		JFrame frame = new JFrame(title);
		JPanel panel = new JPanel();
		JPanel panelTitle = new JPanel();
		JPanel panelLink = new JPanel();
		JPanel panelLinkScroller = new JPanel();
		JLabel searchLabel = new JLabel("URL to gather information on:");
		JLabel titleLabel = new JLabel("Site Title: ");
		JLabel linkLabel = new JLabel("Links found:");
		JLabel blankSpace = new JLabel("				");
		JScrollPane scrollLink = new JScrollPane(linkField);

		// Menu
		JMenu menu;
		JMenuItem menuItem1, menuItem2, menuItem3;

		// Frame layout
		BoxLayout boxLayout = new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS);
		frame.setLayout(boxLayout);
		panelTitle.setLayout(new FlowLayout(FlowLayout.LEADING, 10, 0));

		// Set up panel.
		frame.add(panel);
		frame.add(panelTitle);
		frame.add(panelLink);
		frame.add(panelLinkScroller);

		// Set Menu
		JMenuBar mb = new JMenuBar();
		menu = new JMenu("File");
		menuItem1 = new JMenuItem("Export to TXT");
		menuItem2 = new JMenuItem("About");
		menuItem3 = new JMenuItem("Exit");
		// Add menu items
		menu.add(menuItem1);
		menu.add(menuItem2);
		menu.add(menuItem3);
		// Listeners for the Menu
		menuItem1.addActionListener(this);
		menuItem2.addActionListener(this);
		menuItem3.addActionListener(this);
		// Add menu to become visible in frame
		mb.add(menu);
		frame.setJMenuBar(mb);

		// Set up rest of GUI
		panel.add(blankSpace);
		panel.add(searchLabel);
		panel.add(searchField);
		panel.add(submitSearch);
		panel.add(blankSpace);

		// Add in the information retrieval from JSoup.
		panelTitle.add(titleLabel);
		titleField.setForeground(Color.RED);
		panelTitle.add(titleField);

		// Show Links and scroll them
		panelLink.add(linkLabel);
		linkField.setLineWrap(true); // Set up Link field behaviours Line Wrap
		linkField.setAutoscrolls(true); // Set up Auto Scroll
		linkField.setForeground(Color.BLUE); // Set up Link field Text Colour.
		scrollLink.setSize(100, 100);
		panelLinkScroller.add(scrollLink);

		// Set close operation type.
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Set size using input from method
		frame.setSize(sizeX, sizeY);
		// Set frame in center.
		frame.setLocationRelativeTo(null);
		// Make frame sizeable.
		frame.setResizable(false);

		// Show frame.
		frame.pack();
		frame.setVisible(true);

		// Set listener for input to the search button.
		submitSearch.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		// Check if the submit button for search is active
		if (e.getSource() == submitSearch) {
			// Get the string to search from search field.
			String search = searchField.getText();
			webPageAddress = search;
			// Run Object method to search.
			HarvestJsoup.SearchSoup(search);
			titleField.setText(HarvestJsoup.getTitle());
			linkField.setText(HarvestJsoup.getLinks());
			exportData = HarvestJsoup.getRawExportData();
			titleOfPage = HarvestJsoup.getTitle();
		}

		/* Export the links data to TXT file check if the user has actually searched the
		* webPageAddress must not be null.
		*/	
		
		if (e.getActionCommand() == "Export to TXT" & webPageAddress != null & foundPage == true) {
			/** Saving command is triggered the following is used to calculate
			 * the file name from the data retrived @see HarvestJsoup.
			 */
			
			
			// Check we have a title first.
			if (titleOfPage.length() == 0) {
				titleOfPage = "Temp";
			}

			/**Convert Date for export & convert title to remove white spaces as well as bad
			* charactors for file saving and check there is at least 5 charactors to use
			*/ 			
			String dateConvert = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
			String fileName = titleOfPage.replaceAll("[^\\dA-Za-z ]", "").replaceAll("\\s+", "");

			/** Write in data to a file by unique name using title pages first 5 charactors
			* followed by date.If the file name is longer than 5 charactors cut the string length.
			*/
			if (fileName.length() > 5) {
				fileName = fileName.substring(0, 5);
			}
			
			// Make the file name from the parts.
			String exportFilename = "Export_" + fileName.substring(0, fileName.length()) + "_" + dateConvert + ".Txt";

			// Try to save a file now will create and overwrite existing files.
			try {
				FileWriter myWriter = new FileWriter(exportFilename);
				myWriter.write("Site: " + webPageAddress + "\n");
				myWriter.write("Title: " + titleOfPage + "\n");
				myWriter.write("[Links]" + "\n");
				myWriter.write(exportData);
				myWriter.close();

				// Notify user of success of export.
				PopUps.acknowledged("The file was succesfully exported as " + exportFilename);

			} catch (IOException ee) {
				PopUps.acknowledged("The file was not succesfully exported.");
				ee.printStackTrace();
				return;
			}

		}

		// Show "about" the program.
		if (e.getActionCommand() == "About") {
			// Set up a pop up window to show information
			PopUps.about("Webpage Link Informer \n \n This application is a 'use as is program' and utilises the JSoup java library to return the title and link detail of web pages entered into the search. \n \n For further information on Jsoup visit - https://jsoup.org/ \n \n (Ashton Nicholson 2020)");
		}

		// Exit the program if the exit menu item has been called.
		if (e.getActionCommand() == "Exit") {
			System.exit(0);
		}

	}

}
