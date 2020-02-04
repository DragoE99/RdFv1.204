package serverRdF;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.SocketException;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import util.*;


/**
 * ServerThread is our thread that handles the connection with a client
 * 
 * @author Achille Lambrughi
 * @author Emanuele Drago
 * @author Lorenzo Ottaviani
 * @author Elisabeth Veronika Venturino
 *
 */
public class ServerThread extends Thread {

	//private ServerSocket serverSocket;
	//Socket s;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private User me = new Player(null, null);
	private Lobby myLobby;

	/**
	 * Constructor, it also calls start()
	 * 
	 * @param name, String representing the name we want to assign to this thread
	 * @throws IOException 
	 */
	public ServerThread(String name) throws IOException {
		super(name);
		//this.s = socket;
		in = new ObjectInputStream(ServerListener.getSocket().getInputStream());
		out = new ObjectOutputStream(ServerListener.getSocket().getOutputStream());
		start();
	}


	static Integer id = 0;

	@Override
	public void run() {
		try {

			Integer id = ServerListener.getTempId();

			me.setId(id);
			System.out.println(me.getId());

			ServerListener.addClient(id,this);

			out.writeObject("Hello, Server pronto!"); //Provvisorio

			listen();																//loop

			//out.writeObject("Done");

			//this.listen();

		} catch (IOException | ClassNotFoundException e) {
			System.out.println(Instant.now().toString());
			System.out.println("erroreServerThread");
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}


	/**
	 * Loop that "listens" for Commands and calls the right method to handle it
	 * @throws ClassNotFoundException
	 * @throws IOException
	 * @throws MessagingException 
	 */
	private void listen() throws ClassNotFoundException, IOException, MessagingException {
		Commands c;

		try {
			while(true) {
				c = (Commands) in.readObject();

				switch (c) {
				case LOGIN : loginHandler();
				break;
				case SIGNUP : signup();
				break;
				case RESET : resetPwd();
				break;
				case MODIFYUSER: modifyUser();
				break;
				case OK:
					break;
				case NEEDLOBBYLIST: giveLobbyList();
				break;
				case CREATELOBBY : createLobby();
				break;
				case ACTIVATIONCODE : activation();
				break;
				case UPDATEGAMETABLE: ServerListener.UpdateClientsOfThisMatch(ServerListener.getLobbies().get(myLobby.getMatch().getName()), Commands.UPDATEGAMETABLE, (Integer)in.readObject());
				break;
				case UPDATEGAMETEXT: ServerListener.UpdateClientsOfThisMatch(ServerListener.getLobbies().get(myLobby.getMatch().getName()), Commands.UPDATEGAMETEXT, (Integer)in.readObject());
				break;
				case UPDATEGAMEWHEEL: ServerListener.UpdateClientsOfThisMatch(ServerListener.getLobbies().get(myLobby.getMatch().getName()), Commands.UPDATEGAMEWHEEL, (Integer)in.readObject());
				break;
				case CHOSENMATCH: addMeToMatch((Lobby) in.readObject());
				break;
				case SPECTATE: addMeToLobby((Lobby) in.readObject());
				break;
				case QUIT: quit();
				break;
				case ENDTURN : {
					ServerListener.stopTask(ServerListener.getLobbies().get(myLobby.getMatch().getName()));
					ServerListener.updateCurrentPlayerOfMatch(ServerListener.getLobbies().get(myLobby.getMatch().getName()).getMatch());
				}
				break;
				case USTATS: userStats();
				break;
				case GSTATS: globalStats();
				break;
				case INSERTSENTENCES:insertSentences();
				break;
				case GETALLSENTENCES:getAllSentences();
				break;
				case MANCHEWON: {
					
					ServerListener.stopTask(ServerListener.getLobbies().get(myLobby.getMatch().getName()));
					ServerListener.UpdateClientsOfThisMatch(ServerListener.getLobbies().get(myLobby.getMatch().getName()), Commands.MANCHEWON, (Integer)in.readObject());
					ServerListener.updateManche(ServerListener.getLobbies().get(myLobby.getMatch().getName()));
					ServerListener.displayWinnerTimer(ServerListener.getLobbies().get(myLobby.getMatch().getName()));

				}
				break;
				case DEPOSIT: ServerListener.updateScores(ServerListener.getLobbies().get(myLobby.getMatch().getName()), getUser().getId(), (Integer)in.readObject());
				break;
				case MODIFYSENTENCE: modifySentence();
				break;
				case DELETESENTENCE: deleteSentence();
				break;
				case REMOVEME: {
					ServerListener.removeClient(this);
					out.writeObject(Commands.REMOVEME);
				}
				break;
				case SPINBUTTON: ServerListener.resetTimerTask(5, ServerListener.getLobbies().get(myLobby.getMatch().getName()));
				break;
				case SOLUTIONBUTTON: ServerListener.resetTimerTask(10, ServerListener.getLobbies().get(myLobby.getMatch().getName()));
				break;
				case CONSONANTOK: ServerListener.resetTimerTask(5, ServerListener.getLobbies().get(myLobby.getMatch().getName()));
				break;
				case ACTIVATEJOLLY: ServerListener.resetTimerTask(3, ServerListener.getLobbies().get(myLobby.getMatch().getName()));
				break;
				default: 
					break;
				}
			}
		} catch (SocketException e) {
			ServerListener.addClient(me.getId(), null);
		}
	}

	private void deleteSentence() throws ClassNotFoundException, IOException {
		Sentence sentence =(Sentence)in.readObject();
		ServerListener.getDB().deleteSentence(sentence);
	}


	private void modifySentence() throws IOException, ClassNotFoundException {
		Sentence sentence =(Sentence)in.readObject();
		ServerListener.getDB().modifySentence(sentence);
	}

	/**
	 * Methods that sends global statistics to client when asked. It writes an ArrayList of strings  on the output Stream
	 * @throws IOException 
	 * 
	 */
	private synchronized void globalStats() throws IOException {

		ArrayList<String> stats = new ArrayList<>(ServerListener.getDB().getGlobalStat());

		out.writeObject(stats);
	}


	/**
	 * Methods that sends personal statistics to client when asked. It writes an ArrayList of strings  on the output Stream
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * 
	 */
	private synchronized void userStats() throws ClassNotFoundException, IOException {

		Integer id = (Integer)in.readObject();


		ArrayList<String> stats = new ArrayList<>(ServerListener.getDB().getUserStat(id));

		out.writeObject(stats);
	}


	/**
	 * This method adds the current User to the match and lobby. It is called when a player creates or joins a match
	 * @param lobby its the Lobby that needs to be updated
	 */
	private void addMeToMatch(Lobby lobby) {


		myLobby = lobby;

		myLobby.addThread(me.getId());

		Match match = myLobby.getMatch();

		match.addPlayer(me);

		myLobby.setMatch(match);

		System.out.println("lunghezza della lista di players dopo essere entrato nella lobby" + myLobby.getMatch().getPlayers().size());

		ServerListener.putLobby(myLobby);
		//in fine controllo se sono il terzo giocatore e quindi il gioco puo' partire
		if (myLobby.getNPlayers() == 3) {

			myLobby.setStatus(true);

			ServerListener.startGame(myLobby);

			ServerListener.updateCurrentPlayerOfMatch(lobby.getMatch());

			match.setSentences(ServerListener.getDB().getMatchSentence(match.getPlayersId()));

		}

		myLobby.setMatch(match);

		System.out.println("lunghezza della lista di players dopo essere entrato nella lobby" + myLobby.getMatch().getPlayers().size());

		ServerListener.putLobby(myLobby);

	}



	/**
	 * This method adds the current User to the match and lobby. It is called when a spectator joins a match
	 * @param lobby its the Lobby that needs to be updated
	 */
	private void addMeToLobby(Lobby lobby) {


		myLobby = lobby;

		myLobby.addThread(me.getId());

		Match match = myLobby.getMatch();

		match.addSpectator(me);

		myLobby.setMatch(match);

		System.out.println("lunghezza della lista di players dopo essere entrato nella lobby" + myLobby.getMatch().getPlayers().size());

		ServerListener.putLobby(myLobby);

	}

	/**
	 * Method that reads the letter that needs to be shown in the GameTable of every player in a match
	 * @return returns the string representing the letter that a player has sent 
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public String getGameTableUpdate() throws ClassNotFoundException, IOException {

		return (String) in.readObject();

	}

	/**
	 * Method that checks the credentials at the moment of login on the Database, returning a response based on them through the output stream
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * 
	 */
	private synchronized void loginHandler() throws ClassNotFoundException, IOException {
		User u = (User)in.readObject();


		//se c'e l'utente nel db
		if(ServerListener.getDB().logInCheck(u.getEmail(), u.getPassword())) {

			//devo rimandare al client l'utente completo, serve altrimenti l'utente locale al client non ha ID
			User temp = ServerListener.getDB().getOneUser(u.getEmail(), u.getPassword());

			boolean notAlreadyOnline = true;

			//Cancello l'attuale thread dalla mappa nel ServerListener per reinserirlo con la chiave corrispondente al suo id
			if (temp != null) {
				notAlreadyOnline = ServerListener.replaceClient(me.getId(), temp.getId());
				me = temp;
				//TODO Segnalare all'utente se e' gia' online
			}

			if(notAlreadyOnline) {
				out.writeObject(Commands.OK);
				out.writeObject(temp);
			} else {
				out.writeObject(Commands.ALREADYON);
			}



		} else {
			out.writeObject(Commands.NO);
		}

	}



	/**
	 * Methods that writes the active lobbies preset on the server on the output stream
	 * @throws IOException
	 */
	private synchronized void giveLobbyList() throws IOException {
		//questo reset forse serviva al refresh in selectLobby
		//mi dava problemi anche quando creavo una lobby, al client non risultava
		out.reset();
		out.writeObject(ServerListener.getLobbies()); 

	}

	/**
	 * Method that checks credentials at the moment of sign up, and sends an email with a randomly generated activation code for that account
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws MessagingException
	 * 
	 */
	private synchronized void signup() throws ClassNotFoundException, IOException, MessagingException {
		User u = (User)in.readObject();

		//se c'e l'utente nel db
		if(ServerListener.getDB().checkMailExistence(u.getEmail())) {
			out.writeObject(Commands.EXISTMAIL);

		} else if(ServerListener.getDB().checkNicknameExistence(u.getNickname())) {

			out.writeObject(Commands.EXISTNICK);

		} else {
			out.writeObject(Commands.OK);

			//inserisco l'utente nel DB
			ServerListener.getDB().insertUser(u);
		}

		// crea codice attivazione e manda mail
		String code = randomString(5);
		ServerListener.getDB().insertVerificationCode(u, code);

		sendActivationCode(u.getEmail(), code);

	}

	/**
	 * Sends an email with a randomly generated password to reset it when a user forgets it, if the given address is correct and registered on the Database
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws MessagingException 
	 * 
	 */
	private synchronized void resetPwd() throws ClassNotFoundException, IOException, MessagingException {
		String email = (String)in.readObject();

		if(!ServerListener.getDB().checkMailExistence(email)) {						// controlla sul db se esiste l'utente

			out.writeObject(Commands.NO);
		} else {
			out.writeObject(Commands.OK);


			//modifica la password nel db
			//TODO crea una password random
			String newPassword = randomString(8);

			User u = ServerListener.getDB().getUserByMail(email);
			u.setPassword(newPassword);
			ServerListener.getDB().modifyUser(u);			

			//manda la mail
			sendResetPasswordMail(email, newPassword);

		}
	}

	/**
	 * Reads a new Lobby from the input stream created by a client, and adds it to the list of active ones, on the server and database
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private synchronized void createLobby() throws IOException, ClassNotFoundException {
		Lobby newLobby = (Lobby)in.readObject();

		//e' un casino, c'e il nome della lobby e quello del match, usiamo quello del match ma sul client creiamo quello della lobby... HELP
		Match match = new Match(newLobby.getLobbyName());
		newLobby.setMatch(match);

		//controlla sul db se esiste il nome della lobby
		if(ServerListener.getDB().matchNameCheck(match.getName())) {

			out.writeObject(Commands.NO);
		} else {

			//TODO aggiungere i campi mancanti a lobby:nPlayers, nSpecs.
			newLobby.setNPlayers(1);
			newLobby.setNSpectators(0);


//			// metterlo nella lista di lobby
//			addMeToMatch(newLobby);		

			//TODO metterlo sul database

			ServerListener.getDB().createMatch(new Integer[] {newLobby.getCreator().getId()}, match.getName());
			
			newLobby.setMatch(ServerListener.getDB().getMatchbyName(match));
			
			// metterlo nella lista di lobby
			addMeToMatch(newLobby);
			

			System.out.println("Aggiungo :" + ServerListener.getLobbies().get(match.getName()).getLobbyName());

			out.writeObject(Commands.OK);
		}

	}

	/**
	 * Reads a user from the input stream and updates it on the database
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	private synchronized void modifyUser() throws ClassNotFoundException, IOException {

		User u = (User)in.readObject();	
		System.out.println(u.getId());

		//trovo sul database l'utente con lo stesso id, perche' quello non cambia, e lo aggiorno
		ServerListener.getDB().modifyUser(u);

		me = u;
		System.out.println(me.getName() + me.getEmail());
		System.out.println(me.getPassword());
	}

	/**
	 * Method that removes the players from a lobby
	 * @throws IOException
	 */
	private synchronized void quit() throws IOException {
		out.writeObject(Commands.QUIT);

		//TODO togliere dalla lobby, chiudere tutte le lobby (?)
	}

	/**
	 * Template method to send an email
	 * @param usr username of the account that sends the email	
	 * @param pwd password of the account that sends the email
	 * @param to address of the recipient of the mail
	 * @param subject subject of the email
	 * @param body text of the email
	 * @throws MessagingException 
	 * @throws AddressException 
	 */
	private static void sendMail(String usr, String pwd, String to, String subject, String body) {
		String password=pwd;
		String username=usr;

		String host = "smtp.gmail.com"; //per il nostro account
		String from=username;

		Properties props = System.getProperties();
		props.put("mail.smtp.host",host);
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.port",587);

		Session session = Session.getInstance(props);

		try {
			Message msg = new MimeMessage(session);
			msg.setFrom(new InternetAddress(from));
			msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
			msg.setSubject(subject);
			msg.setText(body);

			Transport.send(msg,username,password);
		} catch (AddressException ex) {
			ex.printStackTrace();

		} catch (MessagingException e) {
			e.printStackTrace();
		}

		System.out.println("\nMail was sent successfully.");
	}

	/**
	 * Method that sends an email with a new password to the user when he asks for a password reset
	 * @param to address of the recipient of the mail
	 * @throws MessagingException 
	 * @throws AddressException 
	 */
	private synchronized static void sendResetPasswordMail(String to, String newPwd) {

		String text = "Your new password is: '" + newPwd + "'.\n Please remember it or change it.";
		String subject = "Password reset";

		String from = ServerListener.getEmail();		//i dati di un nostro account? Pero' non si dovrebbe fare, non so come
		String pwd = ServerListener.getEmailPwd();

		sendMail(from, pwd, to, subject, text);
	}

	/**
	 * Method that sends an email with the activation code to the user when he registers for the first time
	 * @param to address of the recipient of the mail
	 * @throws MessagingException 
	 * @throws AddressException 
	 */
	private static void sendActivationCode(String to, String code) {
		//TODO
		String from = "ruotadellafortuna321@gmail.com";		//i dati di un nostro account? Pero' non si dovrebbe fare, non so come
		String pwd = "Rdfaccount9";

		String activationCode = code; 						//TODO genereare un codice e metterlo nel db

		String subject = "Activation Code";
		String text = "This is your activation code : '" + activationCode + "'.";

		sendMail(from, pwd, to, subject, text);
	}

	/**
	 * Methods that checks if the code read from the input stream is correct for the corresponding user, at the time of activation
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	private synchronized void activation() throws ClassNotFoundException, IOException {

		User user = (User)in.readObject();	

		String code = (String)in.readObject();

		//verifica
		if(ServerListener.getDB().verificationCodeCheck(code, user.getEmail())) {

			out.writeObject(Commands.OK);

		} else {


			out.writeObject(Commands.NO);
		}
	}

	/**
	 * Method that sends a string representing an update to the game and the Command that explains how to handle it
	 * @param gameUpdate a string, which could be a letter or an action performed by a player
	 * @param command description of the update, needed to handle it on the client side
	 * @throws IOException
	 */
	public synchronized void sendUpdateToProxy(String gameUpdate, Commands command) throws IOException {
		out.writeObject(command);
		out.writeObject(gameUpdate);
	}

	/**
	 * Utility method that generates a random alphanumeric String of the specified length
	 * @param length the desired length of the string
	 * @return
	 */
	private String randomString(int length) {

		Random r = new Random();
		String s = "";

		for(int i= 0; i < length; i++) { 
			char c = (char) (r.nextInt(26) + 65); //Lettere maiuscole
			if(r.nextBoolean()) {
				c += 32; //minuscolo
			} else if(r.nextBoolean()) {
				c = (char)(r.nextInt(10) + 48);		//Numeri da 0 a 9
			}
			s += c;
		}

		return s;
	}

	/**
	 * Methods that makes the User connected through this thread available
	 * @return returns the User associated to this thread
	 */
	public User getUser() {
		return me;
	}

	/**
	 * Sends a notification to the players that their match is ready
	 */
	public synchronized void sendStartNotification() {
		try {
			out.writeObject(Commands.START);
			out.writeObject(ServerListener.getLobbies().get(myLobby.getMatch().getName()).getMatch());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	/**
	 * Sends an update to the client, telling him that he should play his turn
	 */
	public synchronized void sendPlayToClient() {

		try {
			out.writeObject(Commands.PLAY);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		ServerListener.updateCurrentPlayerLabel(ServerListener.getLobbies().get(myLobby.getMatch().getName()));

	}


	/**
	 * Sends the nickName of the player that is currently playing 
	 */
	public synchronized void sendCurrentPlayerLabel() {
		try {
			out.writeObject(Commands.CURRENTPLAYERLABEL);
			out.writeObject(ServerListener.getLobbies().get(myLobby.getMatch().getName()).getMatch().getCurrentPlayingUser().getNickname());
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public synchronized void insertSentences(){
		try {
			List<Sentence> sentences = (List<Sentence>) in.readObject();
			User u = (User) in.readObject();
			ServerListener.getDB().insertSentences(sentences,u);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private synchronized void getAllSentences() {
		ArrayList<Sentence> sentences = ServerListener.getDB().getAllSentence();
		try {
			out.writeObject(sentences);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public synchronized void sendTimer(int i) {
		try {
			out.writeObject(Commands.TIMER);
			out.writeObject(i + "");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	public synchronized void sendTimeOut() {

		try {
			out.writeObject(Commands.TIMEOUT);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		ServerListener.updateCurrentPlayerOfMatch(ServerListener.getLobbies().get(myLobby.getMatch().getName()).getMatch());

	}


	/**
	 * 
	 * @param manche
	 */
	public synchronized void sendNewManche(Integer manche) {

		try {
			out.writeObject(Commands.NEWMANCHE);
			out.writeObject(manche);
		} catch (IOException e) {
			// TODO: handle exception
		}

	}


	public synchronized void sendEndMatch(String winner) {

		
		try {
			out.writeObject(Commands.ENDMATCH);
			out.writeObject(winner);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}


	public synchronized void sendExitMatch() {

		try {
			out.writeObject(Commands.EXITMATCH);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
