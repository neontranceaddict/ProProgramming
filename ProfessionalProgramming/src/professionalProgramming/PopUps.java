/**Ashton Nicholson 13 Oct 2020
 * 
 */
package professionalProgramming;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

/**
 * @author Ashton Nicholson 13 Oct 2020 
 * @version 1.0 
 * Using JSoup 1.13.1 Java -  HTML Parser - https://jsoup.org/
 * 
 *         PopUps Class - for the use of GUI windows for standard use.
 */
public class PopUps  {

	public static void about(String messageAbout) {

		// About windows
		JFrame frameAbout = new JFrame("About");
		JTextArea aboutText = new JTextArea();

		// Set up text settings
		aboutText.setLineWrap(true);
		aboutText.setEditable(false);
		aboutText.setText(messageAbout);

		// Set up frame defaults
		frameAbout.setSize(600, 200);
		frameAbout.setLocationRelativeTo(null);
		frameAbout.setResizable(false);
		frameAbout.setAlwaysOnTop(true);
		frameAbout.add(aboutText);
		frameAbout.setVisible(true);

	}

	public static void acknowledged(String message) {

		// Acknowledge window for pop ups
		JFrame frameAcknowledge = new JFrame("Acknowledge");
		JLabel acknowledgeText = new JLabel("", SwingConstants.CENTER);

		// Set up frame defaults
		frameAcknowledge.setSize(500, 100);
		frameAcknowledge.setLocationRelativeTo(null);
		frameAcknowledge.setResizable(false);
		frameAcknowledge.setAlwaysOnTop(true);
		frameAcknowledge.setVisible(true);
		frameAcknowledge.add(acknowledgeText);

		/* Input of text from message string called in methos using HTML to align 
		* if the text is too long for JLabel.
		*/		
		
		acknowledgeText.setAutoscrolls(true);
		acknowledgeText.setText(String.format("<html><body style=\"text-align: justify;\">%s</body></html>", message));

	}
	

}
