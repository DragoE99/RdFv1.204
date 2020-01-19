package util;

import java.io.Serializable;

/**
 * Object representing a user and his data
 * 
 * @author gruppo aelv
 *
 */
public abstract class User implements Serializable {

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

	public User(String name, String surname, String email, String nickname, String password, Integer id) {
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.nickname = nickname;
		this.password = password;
		this.id = id;
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
	
	/**
	 * 
	 * @param u
	 */
	public User(User u) {
		this.name = u.name;
		this.surname = u.surname;
		this.email = u.email;
		this.nickname = u.nickname;
		this.password = u.password;
		this.id = u.id;
	}
	
	//=========================NON SO SE SERVE O SE E' GIUSTO===============================
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
