/**Ashton Nicholson 13 Oct 2020
 * 
 */
package professionalProgramming;

/**
 * @author Ashton Nicholson 13 Oct 2020 
 * @version 1.0 
 * 
 * Using JSoup 1.13.1 Java -  HTML Parser - https://jsoup.org/
 *
 * Webpage Link Informer searchs user input to return title information as well as links used in the site. 
 * This data can be exported to a text file and used at the users wish.
 * 
 *  Program is as is and utilises the Jsoup HTML Parser.
 */
public class Main {

	/**
	 * @param args
	 * @throws IOException
	 */

	public static void main(String[] args) {

		/** Loads the GUI class to begin the program.
		 * 
		 */	
		Gui newGui = new Gui();
		newGui.guiLoad("Webpage Link Informer", 1000, 800);

	}

}
