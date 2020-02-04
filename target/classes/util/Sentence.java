package util;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.StringTokenizer;

import gui.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.Axis;
import javafx.stage.Stage;

/**
 * Class representing our game sentence, with methods to handle it
 * 
 * @author Achille Lambrughi
 * @author Emanuele Drago
 * @author Lorenzo Ottaviani
 * @author Elisabeth Veronika Venturino
 *
 */
public class Sentence implements Serializable {

	 private String sentence;
	 private String hint;
	final private Integer id;
	final private Integer author_id;
	private List<Integer> seenBy;

	/**
	 * Constructor
	 * @param sentence
	 * @param hint
	 * @param id
	 * @param author_id
	 * @param seenBy
	 */
	public Sentence(String sentence, String hint, Integer id, Integer author_id, List<Integer> seenBy) {
		this.sentence = sentence;
		this.hint = hint;
		this.id = id;
		this.author_id = author_id;
		this.seenBy = seenBy;
	}

	/**
	 * Constructor
	 * @param sentence
	 * @param hint
	 */
	public Sentence(String sentence, String hint) {
		this.sentence = sentence;
		this.hint = hint;
		this.id = null;
		this.author_id = null;
		this.seenBy = null;
	}

	/**
	 * Constructor
	 * @param sentence
	 * @param hint
	 * @param id
	 * @param create_by_user
	 */
	public Sentence(String sentence, String hint, int id, int create_by_user) {
		this.sentence = sentence;
		this.hint = hint;
		this.id = id;
		this.author_id = create_by_user;
	}

	/**
	 * Method that turns the sentence into a two dimensional array of char, used to initialize the labels in the game window
	 * @return the char's 2D array to put into the game window
	 */
	public char[][] tokenize() {

		try {

			String [] rows = calculateRows(this.sentence, 15);
//			for (int i = 0; i < rows.length; i++) {
//				System.out.println(rows[i]);
//			}

			char [][] insertableMatrix = new char[4][15];


			for (int i = 0; i < rows.length; i++) {
				char [] a = rows[i].toCharArray();
				for (int j = 0; j < a.length; j++) {
					insertableMatrix[i][j] = a[j];
				}
			}


			return insertableMatrix;
		} catch (ArrayIndexOutOfBoundsException e){ 

			Stage stage = new Stage();

			stage.setTitle("Unexpected Error");

			Parent root;
			try {
				root = FXMLLoader.load(new Main().getClass().getResource("Error.fxml"));

				stage.setScene(new Scene(root));
			} catch (IOException e1) {
				System.out.println("Cannot find the fxml file");
			}

			stage.setResizable(false);
			stage.requestFocus();
			stage.show();

			return null;
		}
	}

	/**
	 * Methods that separates the rows that make up the sentence, based on the bounds of the game window
	 * @param input
	 * @param maxLineLength
	 * @return an array of String that represent the value of every row 
	 */
	private String[] calculateRows (String input, int maxLineLength) {
		
		StringTokenizer tok = new StringTokenizer(input, " ");
		
		StringBuilder sb = new StringBuilder();
		
		int lineLength = 0;
		
		while (tok.hasMoreTokens()) {
			
			String word = tok.nextToken();
			
			while (word.length() > maxLineLength) {
				sb.append(word.substring(0, maxLineLength - lineLength) + "\n");
				
				word = word.substring(maxLineLength - lineLength);
				
				lineLength = 0;
				
			}
			
			if (lineLength + word.length() > maxLineLength) {
				
				sb.append("\n");
				
				lineLength = 0;
			} 
				
				sb.append(word);
				
				lineLength += word.length();
				
				if (lineLength < maxLineLength) {
					
					sb.append(" "); 
					lineLength++;
					
				} else {
					sb.append("\n");
					lineLength = 0;
				}
				
					
			
		}
		
		return sb.toString().split("\n");
	}

	/**
	 * Getter
	 * @return
	 */
	public List<Integer> getSeenBy() {
		return seenBy;
	}

	/**
	 * Setter
	 * @param seenBy
	 */
	public void setSeenBy(List<Integer> seenBy) {
		this.seenBy = seenBy;
	}

	/**
	 * Getter
	 * @return
	 */
	public String getSentence() {
		return sentence;
	}

	/**
	 * Getter
	 * @return
	 */
	public String getHint() {
		return hint;
	}

	/**
	 * Getter
	 * @return
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Getter
	 * @return
	 */
	public Integer getAuthor_id() {
		return author_id;
	}

	public void setSentence(String newSentence){
		sentence=newSentence;
	}
	public void setHint(String newHint){
		hint= newHint;
	}

}
