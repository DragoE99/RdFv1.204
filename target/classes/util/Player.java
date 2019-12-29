package util;

public class Player extends User {

	public Player(String name, String surname, String email, String nickname, String password) {
		super(name, surname, email, nickname, password);
		// TODO Auto-generated constructor stub
	}

	public Player(String name, String surname, String email, String nickname, String password, Integer id) {
		super(name, surname, email, nickname, password, id);
		// TODO Auto-generated constructor stub
	}

	public Player(String email, String password) {
		super(email, password);
		// TODO Auto-generated constructor stub
	}

	public Player(User u) {
		super(u);
		// TODO Auto-generated constructor stub
	}

	public Player(String name, String surname, String email, String nickname, String password, String role, Integer id) {
		super(name, surname, email, nickname, password, role, id);
	}
}
