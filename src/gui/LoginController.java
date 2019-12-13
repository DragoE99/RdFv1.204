package gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import playerRdF.Client;
import util.Commands;


// Dobbiamo fare una nuova UI (non usare "provafx.fxml") quindi probabilmente rifare tutto

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
	

	@Deprecated
	/**
	 * Method that reads the user data fields and has the proxy send them to the server
	 * @param event reads the user input, pressing "enter"
	 * @throws IOException
	 * @Deprecated Use login() method 
	 */
	public void sendCredentials(ActionEvent event) throws IOException {			//credentialsChecker?
		if (psw.getText().equals(null) || user.getText().equals(null)) {
			errorLabel.setVisible(true);											//non c'e'
		} else {
			System.out.println(psw.getText() + user.getText());
			Client.getProxy().sendLoginData(user.getText(), psw.getText());
		}
		
		//after login, open menu window
		//Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("Menu.fxml"))));
		
		Stage primaryStage = new Stage();
		Parent root = FXMLLoader.load(getClass().getResource("Menu.fxml"));
		Scene scene = new Scene(root);
		primaryStage.setScene(scene);
		primaryStage.show();
		

	}
	
	
	/**
	 * 
	 * @param event
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public void login(ActionEvent event) throws IOException {
		if (psw.getText().equals(null) || user.getText().equals(null)) {
			//TODO messaggio di errore
		} else {
			//TODO
			Commands reply = Client.getProxy().sendLoginData(user.getText(), psw.getText());
			
			if(reply == Commands.OK) {
				Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("Menu.fxml"))));
				
				
			} else if(reply == Commands.NO){
				//messaggio di errore
				System.out.println("Nome o password errati");
				
			}
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
