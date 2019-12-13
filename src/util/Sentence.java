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
	
	
	public String[][] tokenize() {
		
		StringTokenizer stringTokenizer = new StringTokenizer(this.sentence, " ");
		int numberOfTokens = stringTokenizer.countTokens();
		String [] words = new String[numberOfTokens]; 
		for (int i = 0; i < numberOfTokens; i++) {
			words[i] = stringTokenizer.nextToken();
		}
		stringTokenizer.nextToken();
		
		return null;
	}

	private List<User> getSeenBy() {
		return seenBy;
	}

	private void setSeenBy(List<User> seenBy) {
		this.seenBy = seenBy;
	}

	private String getSentence() {
		return sentence;
	}

	private String getHint() {
		return hint;
	}

	private Integer getId() {
		return id;
	}

	private Integer getAuthor_id() {
		return author_id;
	}
}
