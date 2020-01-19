package util;

import java.io.Serializable;

/**
 * Object representing a user and his data
 * 
 * @author gruppo aelv
 *
 */
public class User implements Serializable {

	private String role;
	private Integer id;
	private String name;
	private String surname;
	private String email;
	private String nickname;
	private String password;

	public User(String name, String surname, String email, String nickname, String password, String role, Integer id) {
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.nickname = nickname;
		this.password = password;
		this.role = role;
		this.id = id;
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

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	
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

	public String getEmail() {
		return email;
	}
	
	
}
