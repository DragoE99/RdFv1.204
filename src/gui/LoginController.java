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
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import playerRdF.Client;
import util.Admin;
import util.Commands;
import util.Player;
import util.User;


/**
 * The controller for our login window
 * 
 * @author gruppo aelv
 *
 */
public class LoginController {

	@FXML
	private Label errorLabel;

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
		boolean validateAccount = true;
		boolean isAdmin = false;

		if (psw.getText().equals("") || user.getText().equals("")) {
			errorLabel.setText("insert email and password");
			errorLabel.setVisible(true);
			user.setBorder(new Border(new BorderStroke(Color.rgb(194, 24, 24), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(1))));
			psw.setBorder(new Border(new BorderStroke(Color.rgb(194, 24, 24), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(1))));
			System.err.println("Nome o password non inseriti");
		} else if(!validateAccount) { //TODO l'utente deve verificare la mail, nel caso non funzionasse fa vedere errorLabel
			errorLabel.setText("validate your account");
			errorLabel.setVisible(true);
			user.setBorder(new Border(new BorderStroke(Color.rgb(194, 24, 24), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(1))));
			psw.setBorder(new Border(new BorderStroke(Color.rgb(194, 24, 24), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(1))));
			System.err.println("Account non verificato");
		} else {
			//TODO
			User u = (User)new Player(user.getText(), psw.getText());
			Commands reply = Client.getProxy().sendLoginData(u);
			//TODO if user go in Menu else if admin go in MenuAdmin
			if(reply == Commands.OK) {
				if(isAdmin) {
					u = (Admin)u;
					Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("MenuAdmin.fxml"))));
					Client.setUser(u);
				} else {
					u = (Player)u;
					Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("Menu.fxml"))));
					Client.setUser(u);
				}

			} else if(reply == Commands.NO){
				//displays error label + red textFiel border
				errorLabel.setText("wrong email or password");
				errorLabel.setVisible(true);
				user.setBorder(new Border(new BorderStroke(Color.rgb(194, 24, 24), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(1))));
				psw.setBorder(new Border(new BorderStroke(Color.rgb(194, 24, 24), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(1))));
				System.err.println("Nome o password errati");

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
