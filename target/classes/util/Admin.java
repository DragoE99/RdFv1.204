package util;

/**
 * Class representing an admin, used to differentiate between players and admins, subclass of User
 * @author Achille Lambrughi
 * @author Emanuele Drago
 * @author Lorenzo Ottaviani
 * @author Elisabeth Veronika Venturino
 *
 */
public class Admin extends User {

	/**
	 * Constructor
	 * @param name
	 * @param surname
	 * @param email
	 * @param nickname
	 * @param password
	 */
	public Admin(String name, String surname, String email, String nickname, String password) {
		super(name, surname, email, nickname, password);
	}

	/**
	 * Constructor
	 * @param name
	 * @param surname
	 * @param email
	 * @param nickname
	 * @param password
	 * @param id
	 */
	public Admin(String name, String surname, String email, String nickname, String password, Integer id) {
		super(name, surname, email, nickname, password, id);
	}

	/**
	 * Constructor
	 * @param email
	 * @param password
	 */
	public Admin(String email, String password) {
		super(email, password);
	}

	/**
	 * Constructor
	 * @param u
	 */
	public Admin(User u) {
		super(u);
	}

}
