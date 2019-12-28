package gui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import playerRdF.Client;
import playerRdF.ClientRMI;
import util.Commands;
import util.User;

/**
 * The controller for our login window
 * 
 * @author gruppo aelv
 *
 */
public class LoginController {
	
	
	

	@FXML
	private Label errorLabel;				//non c'e' piu' giusto?

	@FXML
	private TextField user;

	@FXML
	private PasswordField psw;

	@FXML
	private AnchorPane pane;
	
	
	/**
	 * 
	 * @param event
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public void login(ActionEvent event) throws IOException {
		if (psw.getText().equals(null) || user.getText().equals(null)) {
			// messaggio di errore
			System.err.println("Nome o password non inseriti");
		} else {
			//TODO
			User u = new User(user.getText(), psw.getText());
			//Commands reply = Client.getProxy().sendLoginData(u);
			ClientRMI check = new ClientRMI();
			
			if(check.loginCheck(u.getEmail(),u.getPassword())) {
				Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("Menu.fxml"))));
				Client.setUser(u);
				
			} else{
				//messaggio di errore
				System.err.println("Nome o password errati");}
				

		}
	}

	/**
	 * Method that sends you to the sign up window
	 * @param e event, clicking the link
	 * @throws IOException
	 */
	public void signUp(ActionEvent e) throws IOException {
		
		Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("SignUp.fxml"))));
	}
	
	/**
	 * Method that sends you to the password reset window
	 * @param e event, clicking the link
	 * @throws IOException
	 */
	public void passwordReset(ActionEvent e) throws IOException {
		
		Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("ResetPassword.fxml"))));
		
	}


}
