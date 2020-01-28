package serverRdF;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.SocketException;
import java.sql.SQLException;
import java.time.Instant;
import java.util.HashMap;
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

import javafx.scene.control.Label;
import util.*;


/**
 * ServerThread is our thread that handles any single connection with a client
 * 
 * @author gruppo aelv
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
	 * Constructor
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


	static Integer id = /*new Random().nextInt()*/ 0;

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
	 * 
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
			case CHOSENMATCH: addMeToLobby((Lobby) in.readObject());
			break;
			case QUIT: quit();
			break;
			case ENDACTION : ServerListener.updateCurrentPlayerOfMatch(myLobby.getMatch());
			break;
			case INSERTSENTENCES:insertSentences();
			default:
				break;
			}
		}
		} catch (SocketException e) {
			ServerListener.addClient(me.getId(), null);
		}
	}


	private void addMeToLobby(Lobby lobby) {


		myLobby = lobby;

		myLobby.addThread(me.getId());

		myLobby.getMatch().addPlayer(me);

		ServerListener.putLobby(myLobby);
		
		/*
		//TODO RIMUOVERE LA ROBA QUI SOTTO
		if (myLobby.getThreads().size() == 1)
		try {
			out.writeObject(Commands.PLAY);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 */
	}


	public String getGameTableUpdate() throws ClassNotFoundException, IOException {

		return (String) in.readObject();

	}

	/**
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * 
	 */
	private void loginHandler() throws ClassNotFoundException, IOException {
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
			} else {
				out.writeObject(Commands.ALREADYON);
			}
			
			out.writeObject(temp);
			
		} else {
			out.writeObject(Commands.NO);
		}

	}



	/**
	 * 
	 * @throws IOException
	 */
	private void giveLobbyList() throws IOException {
		//questo reset forse serviva al refresh in selectLobby
		//mi dava problemi anche quando creavo una lobby, al client non risultava
		out.reset();
		out.writeObject(ServerListener.getLobbies()); 

	}

	/**
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws MessagingException
	 * 
	 */
	private void signup() throws ClassNotFoundException, IOException, MessagingException {
		User u = (User)in.readObject();

		/*
		if(ServerListener.getUsers().contains(u)) {			//TODO, provvisorio, bisogna controllare sul db
			out.writeObject(Commands.NO);
		} else {
			out.writeObject(Commands.OK);

		}
		 */

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
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws MessagingException 
	 * 
	 */
	private void resetPwd() throws ClassNotFoundException, IOException, MessagingException {
		String email = (String)in.readObject();

		if(!ServerListener.getDB().checkMailExistence(email)) {						//TODO controlla sul db se esiste l'utente

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
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	private void createLobby() throws IOException, ClassNotFoundException {
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
			
			
			//TODO metterlo nella lista di lobby
			addMeToLobby(newLobby);		//non so se funziona
			
			//TODO metterlo sul database
			
			ServerListener.getDB().createMatch(new Integer[] {newLobby.getCreator().getId()}, match.getName());
			
			System.out.println("Aggiungo :" + ServerListener.getLobbies().get(match.getName()).getLobbyName());
			
			out.writeObject(Commands.OK);
		}
		
	}

	/**
	 * 
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	private void modifyUser() throws ClassNotFoundException, IOException {

		//User u = new Player((User)in.readObject()); 

		User u = (User)in.readObject();	
		System.out.println(u.getId());

		//trovo sul database l'utente con lo stesso id, perché quello non cambia, e lo aggiorno
		ServerListener.getDB().modifyUser(u);

		me = u;
		System.out.println(me.getName() + me.getEmail());
		System.out.println(me.getPassword());
	}
	
	private void quit() throws IOException {
		out.writeObject(Commands.QUIT);
		
		//TODO togliere dalla lobby, chiudere tutte le lobby (?)
	}

	/**
	 * 
	 * @param usr
	 * @param pwd
	 * @param to
	 * @param subject
	 * @param body
	 * @throws MessagingException 
	 * @throws AddressException 
	 */
	private static void sendMail(String usr, String pwd, String to, String subject, String body) {
		String password=pwd;
		String username=usr;

		String host = "smtp.gmail.com";
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
	 * 
	 * @param to
	 * @throws MessagingException 
	 * @throws AddressException 
	 */
	private static void sendResetPasswordMail(String to, String newPwd) {

		String text = "Your new password is: '" + newPwd + "'.\n Please remember it or change it.";
		String subject = "Password reset";

		String from = "ruotadellafortuna321@gmail.com";		//i dati di un nostro account? Pero' non si dovrebbe fare, non so come
		String pwd = "Rdfaccount9";

		sendMail(from, pwd, to, subject, text);
	}

	/**
	 * 
	 * @param to
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
	 * 
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	private void activation() throws ClassNotFoundException, IOException {

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
	 * @param gameUpdate
	 * @param command
	 * @throws IOException
	 */
	public void sendUpdateToProxy(String gameUpdate, Commands command) throws IOException {
		out.writeObject(command);
		out.writeObject(gameUpdate);
	}
	
	/**
	 * 
	 * @param length
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

	public User getUser() {
		return me;
	}


	public void sendPlayToClient() {

		try {
			out.writeObject(Commands.PLAY);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public void insertSentences(){
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
}
