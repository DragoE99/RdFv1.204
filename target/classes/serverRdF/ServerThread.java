package serverRdF;

import util.Commands;
import util.Lobby;
import util.Player;
import util.User;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Properties;


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
	private User test = new Player("A", "B");

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

	@Override
	public void run() {
		try {
			
			out.writeObject("Ciao, sono il server");													//Provvisorio
			
			listen();																//loop

			//out.writeObject("Done");
			
			//this.listen();

		} catch (IOException | ClassNotFoundException e) {
			System.out.println("erroreServerThread");
			e.printStackTrace();
		}


	}
	
	
	/**
	 * 
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	private void listen() throws ClassNotFoundException, IOException {
		Commands c;
		
		while(true) {
			c = (Commands) in.readObject();
			
			switch (c) {
			case LOGIN : loginHandler();
				break;
			case SIGNUP : signup();
				break;
			case RESET : resetPwd();	
			case NO:
				break;
			case OK:
				break;
			case NEEDLOBBYLIST: giveLobbyList();
				break;
			case CREATELOBBY : createLobby();
				break;
			default:
				break;
			}
		}											
	}
	
	
	/**
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * 
	 */
	private void loginHandler() throws ClassNotFoundException, IOException {
		User u = (User)in.readObject();		
		
		if(u.equals(test)) {																		//TODO provvisorio, bisogna controllare sul db
			out.writeObject(Commands.OK);
		} else {
			out.writeObject(Commands.NO);
		}
	}

	

	/**
	 * 
	 * @throws IOException
	 */
	private void giveLobbyList() throws IOException {
		out.writeObject(ServerListener.getLobbies()); 
		
	}
	
	/**
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * 
	 */
	private void signup() throws ClassNotFoundException, IOException {
		User u = new Player((User)in.readObject());
		
		if(ServerListener.getUsers().contains(u)) {																		//TODO, provvisorio, bisogna controllare sul db
			out.writeObject(Commands.NO);
		} else {
			out.writeObject(Commands.OK);
			
			
			//TODO inserirlo sul db
		}
	}
	
	/**
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * 
	 */
	private void resetPwd() throws ClassNotFoundException, IOException {
		String email = (String)in.readObject();
		
		if(!email.equals(test.getEmail())) {						//TODO controlla sul db se esiste l'utente
			
			out.writeObject(Commands.NO);
		} else {
			out.writeObject(Commands.OK);
			
			//manda la mail
			
			try {
				sendResetPasswordMail(email);
			} catch (MessagingException e) {
				System.err.println("SMTP SEND FAILED:");
			    System.err.println(e.getMessage());
			}
			
			//TODO modifica la password nel db
		}
	}
	
	private void createLobby() throws IOException, ClassNotFoundException {
		Lobby newLobby = (Lobby)in.readObject();
		
		if(newLobby.getLobbyName().equals("nome esistente")) {						//TODO controlla sul db se esiste il nome della lobby
			out.writeObject(Commands.NO);
		} else {
			out.writeObject(Commands.OK);
			
			System.out.println(newLobby.getCreator().getEmail());
			
			ServerListener.addLobby(newLobby);
		}
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
	private static void sendMail(String usr, String pwd, String to, String subject, String body) throws AddressException, MessagingException {
		String password=pwd;
		String username=usr;
	    	       
	    String host = "smtp.office365.com";
	    String from=username;
	   
		Properties props = System.getProperties();
	    props.put("mail.smtp.host",host);
	    props.put("mail.smtp.starttls.enable", "true");
	    props.put("mail.smtp.port",587);
	    
	    Session session = Session.getInstance(props);
	    
	    Message msg = new MimeMessage(session);
	    msg.setFrom(new InternetAddress(from));
	    msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to, false));
	    msg.setSubject(subject);
	    msg.setText(body);
	    
	    Transport.send(msg,username,password);
	    System.out.println("\nMail was sent successfully.");
	}
	
	/**
	 * 
	 * @param to
	 * @throws MessagingException 
	 * @throws AddressException 
	 */
	private static void sendResetPasswordMail(String to) throws AddressException, MessagingException {
		
		//devo creare una nuova password, non ho ancora deciso come
		String newPwd = "nuovaPassword";
		
		String text = "Your new password is " + newPwd + ". Please remember it.";
		String subject = "Password reset";
		
		String from = "";		//i dati di un nostro account? Pero' non si dovrebbe fare, non so come
		String pwd = "";
		
		sendMail(from, pwd, to, subject, text);
	}

}
