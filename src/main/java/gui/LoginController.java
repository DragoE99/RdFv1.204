package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import adminRdF.AdminClient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import playerRdF.Client;
import util.Admin;
import util.Commands;
import util.Player;
import util.User;


/**
 * The controller for login window
 * 
 * @author Achille Lambrughi
 * @author Emanuele Drago
 * @author Lorenzo Ottaviani
 * @author Elisabeth Veronika Venturino
 *
 */
public class LoginController implements Initializable{

	@FXML private Label errorLabel;
	@FXML private TextField user;
	@FXML private PasswordField psw;
	@FXML private AnchorPane pane;
	@FXML private Label title;


	/**
	 * Sets the label window title focus traversable. It's used to set the first prompt text field visible.
	 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		title.setFocusTraversable(true);
	}

	/**
	 * Does the login. Controls if email and password are right, if account is verified, if the account is admin or user.
	 * @param event Action on "Login" button.
	 * @throws IOException .
	 */
	public void login(ActionEvent event) throws IOException {
		//TODO implementare caso admin, togliere il booleano fasullo
		login();
	}

	/**
	 * Sends you to the sign up window.
	 * @param e Action on "Create an account" hypertext.
	 * @throws IOException .
	 */
	public void signUp(ActionEvent e) throws IOException {

		Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("SignUp.fxml"))));
	}

	/**
	 * Sends you to the password reset window.
	 * @param e Action on "Forgot your password?" hypertext.
	 * @throws IOException .
	 */
	public void passwordReset(ActionEvent e) throws IOException {

		Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("ResetPassword.fxml"))));

	}

	/**
	 * Utility method that does login.
	 * @throws IOException .
	 */
	private void login() throws IOException {
		boolean isAdmin = false;
		if(Main.getIsAdmin()) isAdmin=true;

		if (psw.getText().equals("") || user.getText().equals("")) {
			errorLabel.setText("insert email and password");
			errorLabel.setVisible(true);
			user.setBorder(new Border(new BorderStroke(Color.rgb(194, 24, 24), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(1))));
			psw.setBorder(new Border(new BorderStroke(Color.rgb(194, 24, 24), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(1))));
			System.err.println("Nome o password non inseriti");

		} else {
			User u = new Player(user.getText(), psw.getText());
			Commands reply = isAdmin? AdminClient.getProxy().sendLoginData(u):Client.getProxy().sendLoginData(u);
			//TODO if user go in Menu else if admin go in MenuAdmin
			if(reply == Commands.OK) {
				if(isAdmin) {
					u = new Admin(user.getText(), psw.getText());
					Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("MenuAdmin.fxml"))));
					//Client.setUser(u);
				} else {
					u = (Player)u;
					Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("Menu.fxml"))));
					//Client.setUser(u);
				}

			} else if(reply == Commands.NO){
				//displays error label + red textFiel border
				errorLabel.setText("wrong email or password");
				errorLabel.setVisible(true);
				user.setBorder(new Border(new BorderStroke(Color.rgb(194, 24, 24), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(1))));
				psw.setBorder(new Border(new BorderStroke(Color.rgb(194, 24, 24), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(1))));
				System.err.println("Nome o password errati");

			} else if(reply == Commands.NOTVERIFIED){
				errorLabel.setText("please validate your account");
				errorLabel.setVisible(true);
				user.setBorder(new Border(new BorderStroke(Color.rgb(194, 24, 24), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(1))));
				psw.setBorder(new Border(new BorderStroke(Color.rgb(194, 24, 24), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(1))));
				System.err.println("Account not verified");

				//setUser temporaneo
				Client.setUser((User)new Player(user.getText(), psw.getText()));
				//mando alla verifica
				Main.getStage().setScene(new Scene(FXMLLoader.load(getClass().getResource("ActivationCode.fxml"))));

			} else if (reply == Commands.ALREADYON) {

				errorLabel.setText("User already online");
				errorLabel.setVisible(true);
				user.setBorder(new Border(new BorderStroke(Color.rgb(194, 24, 24), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(1))));
				psw.setBorder(new Border(new BorderStroke(Color.rgb(194, 24, 24), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(1))));
				System.err.println("User already online");
			}else if(reply==Commands.NOTANADMIN){
				errorLabel.setText("This user is not an admin");
				errorLabel.setVisible(true);
				user.setBorder(new Border(new BorderStroke(Color.rgb(194, 24, 24), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(1))));
				psw.setBorder(new Border(new BorderStroke(Color.rgb(194, 24, 24), BorderStrokeStyle.SOLID, new CornerRadii(3), new BorderWidths(1))));

			}
		}
	}

	/**
	 * Allows to press confirm button by pressing ENTER.
	 * @param e the pressed key.
	 * @throws IOException .
	 */
	public void buttonPressed(KeyEvent e) throws IOException {
		if(e.getCode().toString().equals("ENTER")) {
			login();
		}
	}

}
