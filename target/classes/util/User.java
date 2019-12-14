package util;

import java.io.Serializable;

/**
 * Object representing a user and his data
 * 
 * @author gruppo aelv
 *
 */
public class User implements Serializable {

	private String name, surname, email, nickname, password;
	private Integer id;
	
	/**
	 * 
	 * @param name
	 * @param surname
	 * @param email
	 * @param nickname
	 * @param password
	 */
	public User(String name, String surname, String email, String nickname, String password) {
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.nickname = nickname;
		this.password = password;
		//this.id = id;
	}
	
	/**
	 * 
	 * @param email
	 * @param password
	 */
	public User(String email, String password) {
		this.email = email;
		this.password = password;
	}
	
	
	//=========================NON SO SE SERVE O SE È GIUSTO===============================
	/**
	 * 
	 * @param u
	 * @return
	 */
	public boolean equals(User u) {
		//FIXME
		if((this.email.equals(u.email))&&(this.password.equals(u.password)))	
			return true;
		else 
			return false;
	}

	public String getEmail() {
		return email;
	}
	
	
}
