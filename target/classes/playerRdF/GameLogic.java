/**
 * 
 */
package playerRdF;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

import gui.GameController;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import util.Action;

/**
 * A class with methods that handle the main game logic
 * 
 * @author Achille Lambrughi
 * @author Emanuele Drago
 * @author Lorenzo Ottaviani
 * @author Elisabeth Veronika Venturino
 *
 */
public class GameLogic {

	
	/**
	 * Method that generates random values simulating the spinning wheel of the game
	 * @return the string with the value representing the result of the spin
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
	
	/**
	 * Method that counts the number of times a consonant is present in the sentence
	 * @param sentence
	 * @param insertConsonant
	 * @param labels
	 * @return the count of occurences of the given consonant in the given sentence
	 */
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

	/**
	 * Method that handles the result of the wheel
	 * @param spinResult
	 * @return true if the player keeps his turn
	 */
	public static Boolean handleSpinResult(String spinResult) {
		
		if (spinResult.equals("JOLLY")) {
			Client.getUser().setJolly(true);
		}
		if(spinResult.equals("PASSA")) {
			//TODO dare l'opzione di giocare il jolly se ce l'ha
			yieldTurn();
			return false;
		} else if(spinResult.equals("PERDI")) {
			yieldTurn();
			return false;
		} else {
			return true;
		}	
	}


	/**
	 * Method that sends an update to the server, signaling the current turn is over
	 */
	private static void yieldTurn() {

		try {
			Client.getProxy().endTurn();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Yields the turn if the inserted letter is not a consonant, or not a letter
	 * @param s
	 */
	public static boolean handleInsertedConsonant(String s) {
		
		
		
		HashSet<Character> consonants = new HashSet<Character>(Arrays.asList('b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'm',
                'n', 'p', 'q', 'r', 's', 't', 'v', 'w', 'x', 'z' ));
		
		Character insertedValue = s.charAt(0);
		
		if (!consonants.contains(insertedValue)) {
			
			//
			
			yieldTurn();
			
			
			
		}
		
		return consonants.contains(insertedValue);
		
		
	}
	
	
	
}
