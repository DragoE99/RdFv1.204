package util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import gui.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.Axis;
import javafx.stage.Stage;
import serverRdF.DataBaseConnection;

/**
 * Class representing our game sentence, with methods to handle it
 * 
 * @author gruppo aelv
 *
 */
public class Sentence implements Serializable {

	final private String sentence;
	final private String hint;
	final private Integer id;
	final private Integer author_id;
	private List<Integer> seenBy;

	public Sentence(String sentence, String hint, Integer id, Integer author_id, List<Integer> seenBy) {
		this.sentence = sentence;
		this.hint = hint;
		this.id = id;
		this.author_id = author_id;
		this.seenBy = seenBy;
	}

	public Sentence(String sentence, String hint, Integer id, Integer author_id) {
		this.sentence = sentence;
		this.hint = hint;
		this.id = id;
		this.author_id = author_id;
	}

	public Sentence(String sentence, String hint) {
		this.sentence = sentence;
		this.hint = hint;
		this.id = null;
		this.author_id = null;
		this.seenBy = null;
	}

	/**
	 * 
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
	 * 
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

	public List<Integer> getSeenBy() {
		return seenBy;
	}

	public void setSeenBy(List<Integer> seenBy) {
		this.seenBy = seenBy;
	}

	public String getSentence() {
		return sentence;
	}

	public String getHint() {
		return hint;
	}

	public Integer getId() {
		return id;
	}

	public Integer getAuthor_id() {
		return author_id;
	}



	public void cvsSentenceReader(String filePath) {
		try {
			//TODO COLLEGARE CON CSVFILECONTROLLER
			CSVReader reader = new CSVReader(new FileReader(filePath));
			String[] nextLine = new String[2];
			List<Sentence> sentences = new ArrayList<Sentence>();
			while ((nextLine = reader.readNext()) != null) {
				Sentence sentence = new Sentence(nextLine[0], nextLine[1]);
				if(sentence.getSentence().length()<60 && sentence.getHint().length()<60){
					sentences.add(sentence);
				}
			}
			//TODO PASSARE LA LISTA A SERVER THREAD E POI A DATABASECONNECTION
			/*DataBaseConnection query =new DataBaseConnection();
			try {
				query.insertSentences(sentences);
			} catch (SQLException e) {
				e.printStackTrace();
			}*/

		} catch (IOException | CsvValidationException e) {
			e.printStackTrace();
		}
	}

}
