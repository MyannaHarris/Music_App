/**
 * @author Myanna Harris
 * CPSC 224
 * Project
 * Cool Music Player
 * 
 * Main driving class
 */

import javax.swing.JFrame;
import javax.swing.SwingUtilities;


public class App {

	public static void main(String[] args) {
		
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new CoolMusicPlayerGUI();
				
			}
		});

	}

}
