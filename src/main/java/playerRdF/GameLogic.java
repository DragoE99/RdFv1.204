/**
 * 
 */
package playerRdF;

import java.io.IOException;
import java.util.Random;

import gui.GameController;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * @author achil
 *
 */


public class GameLogic {

	
	/**
	 * 
	 * @return
	 */
	public static String spinWheel() {
		Random rand = new Random();
		int value = rand.nextInt(12) - 2;
		String result;
		switch (value) {
		case 0:
			result = "PASSA";
			break;
		case -1:
			result = "PERDI";
			break;
		case -2:
			result = "JOLLY";
			break;
		default:
			result = "" + value * 100;
			break;
		}
		
		return result;
	}
	
	
	public static int consonantOccurrences(String sentence, TextField insertConsonant, Label [][] labels) {
		
		int counter = 0;
		
		if (sentence.toLowerCase().contains(insertConsonant.getCharacters())) {
			
			for (int i = 0; i < 4; i++) {
				
				for (int j = 0; j < 15; j++) {
					
					if (insertConsonant.getText().equalsIgnoreCase(labels[i][j].getText())) {
						
						counter++;
					}
				}
			}
		}
		
		return counter;
	}


	public static String checkSpinResult(String spinResult) {
		
		if(spinResult.equals("PASSA")) {
			yieldTurn();
		} else if(spinResult.equals("PERDI")) {
			yieldTurn();
		}
		
		return spinResult;
		
	}


	private static void yieldTurn() {

		try {
			Client.getProxy().endAction();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
}
