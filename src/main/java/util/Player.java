package util;

/**
 * Class representing player, used to differentiate between players and admins, subclass of User
 * @author Achille Lambrughi
 * @author Emanuele Drago
 * @author Lorenzo Ottaviani
 * @author Elisabeth Veronika Venturino
 *
 */
public class Player extends User {

	/**
	 * Constructor
	 * @param name
	 * @param surname
	 * @param email
	 * @param nickname
	 * @param password
	 */
	public Player(String name, String surname, String email, String nickname, String password) {
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
	public Player(String name, String surname, String email, String nickname, String password, Integer id) {
		super(name, surname, email, nickname, password, id);
	}

	/**
	 * Constructor
	 * @param email
	 * @param password
	 */
	public Player(String email, String password) {
		super(email, password);
	}

	/**
	 * Constructor
	 * @param u
	 */
	public Player(User u) {
		super(u);
	}

	/**
	 * Constructor
	 * @param id
	 */
	public Player(Integer id) {
		super(id);
	}
}
