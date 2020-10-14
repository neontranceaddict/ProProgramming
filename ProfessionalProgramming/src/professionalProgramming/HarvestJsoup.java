/**Ashton Nicholson 13 Oct 2020
 * 
 */
package professionalProgramming;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author Ashton Nicholson 13 Oct 2020 
 * @version 1.0 
 * Using JSoup 1.13.1 Java -  HTML Parser - https://jsoup.org/
 * 
 *         HarvestJsoup used to catch and retrieve data.
 */
public class HarvestJsoup {

	// Set up the information required
	public static String Title = "";
	public static String Links = "";
	public static URL TempURL;
	public static Boolean urlFormat;
	public static String RawExportData = ""; // Data used for saving links only for filtering.

	public static void SearchSoup(String URLtofind) {
		/**
		*@param URLtofind - (String) This is the URL for the program to find via JSoup connection.
		*/

		String urlToFind = URLtofind;

		/* Tell GUI that we havent got a page now until we check so we cannot export
		* data thats not available.
		*/
		Gui.foundPage = false;

		// Simple check if the URL search is long enough
		if (URLtofind.length() < 4) {
			PopUps.acknowledged("Malformed URL (Too short) please check the address");
			return;
		}

		/*
		 * Error handling on expectation that the URL is not correct. Send out a search
		 * on the query and see if the user has added HTTP or HTTPS
		 */
		checkURLIntegrity(urlToFind);

		// If check was ok then start the conncetion process on the converted URL.
		if (TempURL == null) {
			return;
		} else {
			urlToFind = TempURL.toString();
		}

		try {
			// Get URL
			Document URL = Jsoup.connect(urlToFind).timeout(6000).get();

			// Get title and all other information from the URL.
			Title = URL.title();

			// Check the page for the following links and references.
			Elements links = URL.select("a[href]");
			for (Element link : links) {
				Links = Links + " " + "Title: " + link.text() + " (" + link.attr("href") + ")" + "\n";
				RawExportData = RawExportData + link.attr("href") + "\n";
				Gui.foundPage = true;
			}
		} catch (IOException e) {
			PopUps.acknowledged("URL error not found please check the address.");
		}
	}

	public static String getTitle() {
		return Title;
	}

	public static String getLinks() {
		return Links;
	}

	public static String getRawExportData() {
		return RawExportData;
	}

	public static void checkURLIntegrity(String URLSearch) {

		/**
		 * Convert address to uppercase and roughly check has the user entered in WWW.
		 * or HTTP if not add these parts where applicable.
		 */

		String tempUppercase = URLSearch.toUpperCase();
		String tempBreakdown = tempUppercase.substring(0, 4);

		// if the case is WWW then add the HTTPS to the address first
		if (tempBreakdown.equals("WWW.")) {
			try {
				TempURL = new URL("https", URLSearch, "");
			} catch (MalformedURLException e) {
				PopUps.acknowledged("Malformed URL please check the address.");
			}
		}
		// Check if a HTTP is part this will also allow for the WWW. succesful results
		// to be rechecked.
		if (tempBreakdown.equals("HTTP")) {
			try {
				TempURL = new URL(URLSearch);
			} catch (MalformedURLException e) {
				PopUps.acknowledged("Malformed URL please check the address.");
			}
		}

		// If all else fails then send an acknowledge notice on no URL
		if (TempURL == null) {
			PopUps.acknowledged("Null URL returned please check the address.");
			return;
		}

	}

}
