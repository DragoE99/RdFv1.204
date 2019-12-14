package util;

import java.io.Serializable;
import java.util.List;
import java.util.StringTokenizer;

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
	private List<User> seenBy;

	public Sentence(String sentence, String hint, Integer id, Integer author_id, List<User> seenBy) {
		this.sentence = sentence;
		this.hint = hint;
		this.id = id;
		this.author_id = author_id;
		this.seenBy = seenBy;
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

		StringTokenizer stringTokenizer = new StringTokenizer(this.sentence, " ");
		int numberOfTokens = stringTokenizer.countTokens();
		String [] words = new String[numberOfTokens]; 
		String [] rows = new String[4];
		int tracker = 0;
		for (int i = 0; i < numberOfTokens; i++) {
			words[i] = stringTokenizer.nextToken();
		}
		for (int i = 0; i < rows.length; i++) {
			for (int j = tracker; j < words.length; j++) {
				if (!(rows[i] == null)) {
					if (rows[i].length() + words[j].length() + 1 < 15)
						rows[i] += words[j] + " ";
					else {
						rows[i] = rows[i].trim();
						tracker = j;
						break;
					}
				} else {
					rows[i] = words[j] + " ";
				}
			}
		}

		char [][] insertableMatrix = new char[4][15];


		for (int i = 0; i < rows.length; i++) {
			char [] a = rows[i].toCharArray();
			for (int j = 0; j < a.length; j++) {
				insertableMatrix[i][j] = a[j];
			}
		}

		return insertableMatrix;
	}
	
	

	public List<User> getSeenBy() {
		return seenBy;
	}

	public void setSeenBy(List<User> seenBy) {
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
}
