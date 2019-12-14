package gui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import playerRdF.Client;
import util.Commands;
import util.User;

/**
 * 
 * @author gruppo aelv
 *
 */
public class SignUpController {
	

	@FXML private Node pane;
	
	
	@FXML
	private TextField name;
	
	@FXML
	private TextField surname;
	
	@FXML
	private TextField nickname;
	
	@FXML
	private TextField email;
	
	@FXML
	private PasswordField password;

	@FXML
	private PasswordField passwordCheck;
	
	//altri label per messaggi per errori, operazione non riuscita...
	
	/**
	 * 
	 * @param e
	 * @throws IOException
	 * @throws ClassNotFoundException 
	 */
	public void register(ActionEvent e) throws IOException, ClassNotFoundException {
			
			if (!password.getText().equals(passwordCheck.getText())) {
				//messaggio di errore, bisogna creare una label o simile VERONIKA
				System.err.println("Password non sono identiche");
			} else {
		
				//errori se mail, nickame esistono gia',...
				
				
				User u = new User(name.getText(), surname.getText(), email.getText(), nickname.getText(), password.getText());
				
				Commands reply = Client.getProxy().signup(u);
				
				if(reply == Commands.OK) {
					//rimanda al login quando hai finito con successo
					Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("Login.fxml"))));
					
					
				} else if(reply == Commands.NO){
					//messaggio di errore
					System.err.println("Utente già esistente");
					
				}
				
				
			}
			
		}
}
